package umc.IRECIPE_Server.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.common.S3.S3Service;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {

        String url = s3Service.saveFile(file, "images"); // S3 버킷의 "images" 디렉토리에 저장.

        return ApiResponse.onSuccess(url);
    }
}
