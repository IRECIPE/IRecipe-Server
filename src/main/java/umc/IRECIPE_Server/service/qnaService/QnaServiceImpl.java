package umc.IRECIPE_Server.service.qnaService;

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

        // 데이터 저장
        Qna qna = QnaConverter.toQna(member.get(), post, request, imageUrl, fileName);
        return qnaRepository.save(qna);
    }

    // Qna 조회
    @Override
    public List<QnaResponseDTO.getQnaDTO> getQna(Long postId) {

        // Qna 조회 (질문)
        List<Qna> questionList = qnaRepository.findAllByPost_IdAndParentId(postId, 0L);

        // Qna 데이터 세팅
        List<QnaResponseDTO.getQnaDTO> qnaList = new ArrayList<>();
        for (Qna question : questionList) {

            // Qna 조회 (답변)
            List<Qna> answerList = qnaRepository.findAllByPost_IdAndParentId(postId, question.getId());
            List<QnaResponseDTO.getQnaChildrenDTO> qnaChildrenList = new ArrayList<>();
            for (Qna answer : answerList) {
                qnaChildrenList.add(QnaConverter.toQnaChildrenList(answer));
            }

            qnaList.add(QnaConverter.getQnaResult(question, qnaChildrenList));
        }

        return qnaList;
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

        // Qna 조회
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_QNA_NOT_FOUND));

        // 작성자만 삭제 가능
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if (member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        if (member.get().getId() != qna.getMember().getId()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // S3 버킷에 저장된 게시글 Qna 사진 삭제
        if (qna.getImageUrl() != null) {
            s3Service.deleteImage(qna.getFileName(), "images");
        }

        // 질문 Qna 일 경우 답변 Qna 지우기
        if (qna.getParentId() == 0L) {
            qnaRepository.deleteAllByParentId(qnaId);
        }

        qnaRepository.deleteById(qnaId);
    }
}
