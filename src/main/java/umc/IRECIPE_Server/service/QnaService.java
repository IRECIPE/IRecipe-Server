package umc.IRECIPE_Server.service;

import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.entity.Qna;

import java.io.IOException;

public interface QnaService {

    // 작성
    public Qna addQna(String memberId, Long postId, QnaRequestDTO.addQna request, MultipartFile file) throws IOException;
}
