package umc.IRECIPE_Server.converter;


import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.dto.IngredientResponse;
import umc.IRECIPE_Server.dto.response.PostResponseDTO;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponseDTO.postDTO toPostResponseDTO(Post post){
        return PostResponseDTO.postDTO.builder()
                    .postId(post.getId())
                    .build();
        }

    public static PostResponseDTO.getTempDTO toTempResponseDTO(Post post){
        return PostResponseDTO.getTempDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .category(post.getCategory())
                .content(post.getContent())
                .level(post.getLevel())
                .imageUrl(post.getImageUrl())
                .status(post.getStatus())
                .build();
    }



    public static PostResponseDTO.getDTO toGetResponseDTO(Post post, Member member){
        return PostResponseDTO.getDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .content(post.getContent())
                .category(post.getCategory())
                .status(post.getStatus())
                .likes(post.getLikes())
                .level(post.getLevel())
                .score(post.getScore())
                .imageUrl(post.getImageUrl())
                .writerNickName(member.getNickname())
                .writerImage(member.getProfileImage())
                .build();
    }

    public static PostResponseDTO.updateDTO toUpdateResponseDTO(Post post){
        return PostResponseDTO.updateDTO.builder()
                .postId(post.getId())
                .build();
    }

    public static PostResponseDTO.getRankedPostDTO toGetRankedPostDTO(Post post) {
        return PostResponseDTO.getRankedPostDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .scores(post.getScore())
                .likes(post.getLikes())
                .imageUrl(post.getImageUrl())
                .build();

    }
    public static PostResponseDTO.findAllResultListDTO toFindAllResultListDTO(Page<Post> postList) {
        List<PostResponseDTO.getRankedPostDTO> postResultDTOList = postList.getContent().stream()
                .map(PostConverter::toGetRankedPostDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.findAllResultListDTO.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalPage(postList.getTotalPages())
                .totalElements(postList.getTotalElements())
                .listSize(postResultDTOList.size())
                .postList(postResultDTOList)
                .build();
    }
}
