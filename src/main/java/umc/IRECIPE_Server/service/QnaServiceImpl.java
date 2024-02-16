package umc.IRECIPE_Server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.converter.QnaConverter;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.dto.response.QnaResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Qna;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;
import umc.IRECIPE_Server.repository.QnaCustomRepository;
import umc.IRECIPE_Server.repository.QnaRepository;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QnaServiceImpl implements QnaService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final QnaRepository qnaRepository;
    private final QnaCustomRepository qnaCustomRepository;
    private final S3Service s3Service;

    // Qna 작성
    @Override
    public Qna addQna(String memberId, Long postId, QnaRequestDTO.addQna request, MultipartFile file) throws IOException {

        // 작성자
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if(member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        // 해당 커뮤니티 게시글
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        // 사진 존재 시 S3 업로드 & 사진 이름 저장
        String imageUrl = null;
        String fileName = null;
        if (file != null) {
            imageUrl = s3Service.saveFile(file, "images");
            fileName = file.getOriginalFilename();
        }

        // 저장할 데이터 세팅
        Qna qna = QnaConverter.toQna(member.get(), post, request, imageUrl, fileName);

        // 자식 댓글이면, 부모 댓글 업데이트
        Qna parentQna;
        if (request.getParentId() != 0) {
            parentQna = qnaRepository.findById(request.getParentId()).orElseThrow(() -> new GeneralException(ErrorStatus.POST_QNA_NOT_FOUND));
            qna.updateParent(parentQna);
        }

        return qnaRepository.save(qna);
    }

    // Qna 조회
    @Override
    public List<QnaResponseDTO.getQnaDTO> getQna(Long postId) {

        // 해당 커뮤니티 게시글
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        // QueryDSL 을 사용하여 게시글 Qna 전체 조회 (정렬 : 부모 댓글 순, 작성시간 순)
        List<Qna> qnaList = qnaCustomRepository.findAllByPost(post);

        // 대댓글 중첩구조 세팅
        List<QnaResponseDTO.getQnaDTO> qnaResponseDTOList = new ArrayList<>();
        Map<Long, QnaResponseDTO.getQnaDTO> map = new HashMap<>();

        qnaList.forEach(q -> {
                QnaResponseDTO.getQnaDTO qdto = QnaConverter.getQnaResult(q);
                map.put(qdto.getQnaId(), qdto);
                if (q.getParent() != null) map.get(q.getParent().getId()).getChildren().add(qdto);
                else qnaResponseDTOList.add(qdto);
                }
        );

        return qnaResponseDTOList;
    }

    // Qna 수정
    @Override
    public void updateQna(String memberId, Long qnaId, QnaRequestDTO.updateQna request, MultipartFile file) throws IOException {

        // 해당 Qna 조회
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_QNA_NOT_FOUND));

        // 작성자만 수정 가능
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if (member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        if (member.get().getId() != qna.getMember().getId()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 기존에 저장된 사진 존재 시 S3 에서 삭제
        String oldUrl = qna.getImageUrl();
        if (oldUrl != null) {
            s3Service.deleteImage(qna.getFileName(), "images");
        }

        // 새로 저장되는 사진 존재 시 S3 업로드
        String newUrl = null;
        String newFileName = null;
        if (file != null) {
            newUrl = s3Service.saveFile(file, "images");
            newFileName = file.getOriginalFilename();
        }

        qna.updateQna(request, newUrl, newFileName);
    }

    // Qna 삭제
    @Override
    public void deleteQna(String memberId, Long qnaId) {

        // 작성자만 삭제 가능
        Qna qnaWriter = qnaRepository.findById(qnaId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_QNA_NOT_FOUND));
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if (member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        if (member.get().getId() != qnaWriter.getMember().getId()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 부모 댓글과 함께 조회
        Qna qna = qnaCustomRepository.findQnaByIdWithParent(qnaId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_QNA_NOT_FOUND));

        if (!qna.getChildren().isEmpty()) { // 자식이 있을 때
            // isDeleted 상태만 변경
            qna.changeIsDeleted(true);

            // S3 사진 삭제
            if (qna.getImageUrl() != null) {
                s3Service.deleteImage(qna.getFileName(), "images");
            }

        } else { // 자식 댓글이 없을 때

            // 삭제 가능한 조상 댓글 삭제
            qnaRepository.delete(getDeletableAncestorQna(qna));

            // S3 사진 삭제
            if (qna.getImageUrl() != null) {
                s3Service.deleteImage(qna.getFileName(), "images");
            }
        }
    }

    private Qna getDeletableAncestorQna(Qna qna) {

        // 현재 댓글의 부모
        Qna parent = qna.getParent();

        // 재귀 : 부모 댓글이 있음 & 부모의 자식 댓글이 1개 & 부모의 삭제 상태가 True
        if (parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted()) {
            return getDeletableAncestorQna(parent);
        }

        // 삭제해야 하는 댓글 반환
        return qna;
    }
}
