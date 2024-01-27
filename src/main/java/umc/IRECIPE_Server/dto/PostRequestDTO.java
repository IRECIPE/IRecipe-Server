package umc.IRECIPE_Server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;

import java.util.List;

@Getter
@Builder
public class PostRequestDTO {

    private String title;

    private String subhead;

    private Category category;

    private Level level;

    private Status status;

    private String content;

}
