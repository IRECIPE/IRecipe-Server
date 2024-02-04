package umc.IRECIPE_Server.converter;

import jakarta.validation.Valid;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.dto.response.QnaResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Qna;

import java.util.Optional;

public class QnaConverter {

    // 작성
    public static Qna toQna(Member member, Post post, QnaRequestDTO.@Valid addQna request, String imageUrl, String fileName) {
        return Qna.builder()
                .member(member)
                .post(post)
                .isDeleted(Boolean.FALSE)
                .content(request.getContent())
                .imageUrl(imageUrl)
                .fileName(fileName)
                .build();
    }

    public static QnaResponseDTO.addQnaDTO addQnaResult(Qna qna) {
        return QnaResponseDTO.addQnaDTO.builder()
                .qnaId(qna.getId())
                .build();
    }
}
