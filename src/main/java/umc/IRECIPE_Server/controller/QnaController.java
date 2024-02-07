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
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    // Qna 작성
    @PostMapping(value = "/{postId}/qna", consumes = "multipart/form-data")
    public ApiResponse<QnaResponseDTO.addQnaDTO> addQna(@PathVariable("postId") Long postId,
                                                             @RequestPart(name = "QnaRequestDTO", required = false) QnaRequestDTO.addQna request,
                                                             @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        // 작성자
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        Qna qna = qnaService.addQna(memberId, postId, request, file);
        return ApiResponse.onSuccess(QnaConverter.addQnaResult(qna));
    }

    // Qna 조회
    @GetMapping("/{postId}/qna")
    public ApiResponse<List<QnaResponseDTO.getQnaDTO>> getQna(@PathVariable("postId") Long postId) {

        List<QnaResponseDTO.getQnaDTO> qnaList = qnaService.getQna(postId);
        return ApiResponse.onSuccess(qnaList);
    }

    // Qna 수정
    @PatchMapping(value = "/qna/{qnaId}", consumes = "multipart/form-data")
    public ApiResponse<QnaResponseDTO.updateQnaDTO> updateQna(@PathVariable("qnaId") Long qnaId,
                                                              @RequestPart(name = "QnaRequestDTO", required = false) QnaRequestDTO.updateQna request,
                                                              @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        qnaService.updateQna(qnaId, request, file);
        return ApiResponse.onSuccess(QnaConverter.updateQnaResult(qnaId));
    }

    // Qna 삭제
    @DeleteMapping("/qna/{qnaId}")
    public ApiResponse<QnaResponseDTO.deleteQnaDTO> deleteQna(@PathVariable("qnaId") Long qnaId) {
        qnaService.deleteQna(qnaId);
        return ApiResponse.onSuccess(QnaConverter.deleteQnaResult());
    }
}