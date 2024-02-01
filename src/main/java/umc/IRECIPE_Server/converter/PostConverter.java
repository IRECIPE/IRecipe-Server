package umc.IRECIPE_Server.converter;


import umc.IRECIPE_Server.dto.response.PostResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;

import java.util.List;

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
                .imageUrl(post.getImgaeUrl())
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
                .imageUrl(post.getImgaeUrl())
                .writerNickName(member.getNickname())
                .writerImage(member.getProfileImage())
                .build();
    }

    public static PostResponseDTO.updateDTO toUpdateResponseDTO(Post post){
        return PostResponseDTO.updateDTO.builder()
                .postId(post.getId())
                .build();
    }
}
