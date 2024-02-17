package umc.IRECIPE_Server.service;

import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.dto.response.QnaResponseDTO;
import umc.IRECIPE_Server.entity.Qna;

import java.io.IOException;
import java.util.List;

public interface QnaService {

    // Qna 작성
    public Qna addQna(String memberId, Long postId, QnaRequestDTO.addQna request, MultipartFile file) throws IOException;

    // Qna 조회
    public List<QnaResponseDTO.getQnaDTO> getQna(Long postId);

    // Qna 수정
    public void updateQna(String memberId, Long qnaId, QnaRequestDTO.updateQna request, MultipartFile file) throws IOException;

//    // Qna 삭제
//    public void deleteQna(String memberId, Long qnaId);
}
