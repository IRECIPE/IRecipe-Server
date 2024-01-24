package umc.IRECIPE_Server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {

    private String title;

    private String content;

    private List<String> imageUrl;
}
