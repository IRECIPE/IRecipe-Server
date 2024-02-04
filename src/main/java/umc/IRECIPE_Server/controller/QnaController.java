package umc.IRECIPE_Server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.QnaConverter;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.dto.response.QnaResponseDTO;
import umc.IRECIPE_Server.entity.Qna;
import umc.IRECIPE_Server.service.QnaService;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    // 작성
    @PostMapping(value = "/{postId}/question", consumes = "multipart/form-data")
    public ApiResponse<QnaResponseDTO.addQnaDTO> addQna(@PathVariable("postId") Long postId,
                                                             @RequestPart(required = false) QnaRequestDTO.addQna request,
                                                             @RequestPart(required = false) MultipartFile file) throws IOException {
        // 작성자
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        Qna qna = qnaService.addQna(memberId, postId, request, file);
        return ApiResponse.onSuccess(QnaConverter.addQnaResult(qna));
    }
}