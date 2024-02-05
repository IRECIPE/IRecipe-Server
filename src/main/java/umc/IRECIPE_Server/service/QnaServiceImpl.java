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

        // 사진
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
}
