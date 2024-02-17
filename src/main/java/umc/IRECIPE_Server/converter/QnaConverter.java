package umc.IRECIPE_Server.converter;

import jakarta.validation.Valid;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.dto.response.QnaResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Qna;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QnaConverter {

    // 객체 생성
    public static Qna toQna(Member member, Post post, QnaRequestDTO.@Valid addQna request, String imageUrl, String fileName) {
        return Qna.builder()
                .member(member)
                .post(post)
                .parentId(request.getParentId())
                .content(request.getContent())
                .imageUrl(imageUrl)
                .fileName(fileName)
                .build();
    }

    // Qna 작성
    public static QnaResponseDTO.addQnaDTO addQnaResult(Qna qna) {
        return QnaResponseDTO.addQnaDTO.builder()
                .qnaId(qna.getId())
                .build();
    }

    // Qna 조회
    public static QnaResponseDTO.getQnaDTO getQnaResult(Qna qna, List<QnaResponseDTO.getQnaChildrenDTO> childrenList) {
        return QnaResponseDTO.getQnaDTO.builder()
                .qnaId(qna.getId())
                .memberId(qna.getMember().getPersonalId())
                .memberNickName(qna.getMember().getNickname())
                .memberImage(qna.getMember().getProfileImage())
                .createdAt(qna.getCreatedAt())
                .content(qna.getContent())
                .imageUrl(qna.getImageUrl())
                .children(childrenList)
                .build();
    }

    // Qna childrenList 생성
    public static QnaResponseDTO.getQnaChildrenDTO toQnaChildrenList(Qna qna) {
        return QnaResponseDTO.getQnaChildrenDTO.builder()
                .qnaId(qna.getId())
                .memberId(qna.getMember().getPersonalId())
                .memberNickName(qna.getMember().getNickname())
                .memberImage(qna.getMember().getProfileImage())
                .createdAt(qna.getCreatedAt())
                .content(qna.getContent())
                .imageUrl(qna.getImageUrl())
                .build();
    }

    // Qna 수정
    public static QnaResponseDTO.updateQnaDTO updateQnaResult(Long qnaId) {
        return QnaResponseDTO.updateQnaDTO.builder()
                .qnaId(qnaId)
                .message("Qna updated successfully")
                .build();
    }

    // Qna 삭제
    public static QnaResponseDTO.deleteQnaDTO deleteQnaResult() {
        return QnaResponseDTO.deleteQnaDTO.builder()
                .message("Qna deleted successfully")
                .build();
    }
}
