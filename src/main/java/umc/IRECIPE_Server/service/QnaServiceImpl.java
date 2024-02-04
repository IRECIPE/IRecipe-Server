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
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Qna;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;
import umc.IRECIPE_Server.repository.QnaRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QnaServiceImpl implements QnaService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final QnaRepository qnaRepository;
    private final S3Service s3Service;

    // 작성
    @Override
    public Qna addQna(String memberId, Long postId, QnaRequestDTO.addQna request, MultipartFile file) throws IOException {

        // 작성자
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if(member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        // 해당 커뮤니티 게시글
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new GeneralException(ErrorStatus.POST_NOT_FOUND);
        }

        // 사진
        String imageUrl = null;
        String fileName = null;
        if (file != null) {
            imageUrl = s3Service.saveFile(file, "images");
            fileName = file.getOriginalFilename();
        }

        // 저장할 데이터 세팅
        Qna qna = QnaConverter.toQna(member.get(), post.get(), request, imageUrl, fileName);

        // 자식 댓글이면, 부모 댓글 업데이트
        Qna parentQna;
        if (request.getParentId() != 0) {
            parentQna = qnaRepository.findById(request.getParentId()).orElseThrow(() -> new GeneralException(ErrorStatus.POST_QNA_NOT_FOUND));
            qna.updateParent(parentQna);
        }

        return qnaRepository.save(qna);
    }
}
