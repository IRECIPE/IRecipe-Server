package umc.IRECIPE_Server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.QnaConverter;
import umc.IRECIPE_Server.dto.request.QnaRequestDTO;
import umc.IRECIPE_Server.dto.response.QnaResponseDTO;
import umc.IRECIPE_Server.entity.Qna;
import umc.IRECIPE_Server.service.qnaService.QnaService;

import java.io.IOException;
import java.util.List;

@Tag(name = "Qna", description = "Qna 관련 API")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    // Qna 작성
    @Operation(
            summary = "Qna 신규 등록하기 API",
            description = "게시글Id(PathVariable) 와 Qna(DTO, MultipartFile) 받아서 신규 등록"
    )
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
    @Operation(
            summary = "Qna 조회하기 API",
            description = "게시글 Id(PathVariable) 받아서 해당 게시글의 Qna 조회"
    )
    @GetMapping("/{postId}/qna")
    public ApiResponse<List<QnaResponseDTO.getQnaDTO>> getQna(@PathVariable("postId") Long postId) {

        List<QnaResponseDTO.getQnaDTO> qnaList = qnaService.getQna(postId);
        return ApiResponse.onSuccess(qnaList);
    }

    // Qna 수정
    @Operation(
            summary = "Qna 수정하기 API",
            description = "QnaId(PathVariable) 와 Qna(DTo, MultipartFile) 받아서 수정"
    )
    @PatchMapping(value = "/qna/{qnaId}", consumes = "multipart/form-data")
    public ApiResponse<QnaResponseDTO.updateQnaDTO> updateQna(@PathVariable("qnaId") Long qnaId,
                                                              @RequestPart(name = "QnaRequestDTO", required = false) QnaRequestDTO.updateQna request,
                                                              @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        qnaService.updateQna(qnaId, request, file);
        return ApiResponse.onSuccess(QnaConverter.updateQnaResult(qnaId));
    }

    // Qna 삭제
    @Operation(
            summary = "Qna 삭제하기 API",
            description = "QnaId(PathVariable) 받아서 해당 Qna 삭제"
    )
    @DeleteMapping("/qna/{qnaId}")
    public ApiResponse<QnaResponseDTO.deleteQnaDTO> deleteQna(@PathVariable("qnaId") Long qnaId) {
        qnaService.deleteQna(qnaId);
        return ApiResponse.onSuccess(QnaConverter.deleteQnaResult());
    }
}