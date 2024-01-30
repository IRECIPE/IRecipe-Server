package umc.IRECIPE_Server.converter;


import umc.IRECIPE_Server.dto.response.PostResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.PostImage;

import java.util.List;

public class PostConverter {

    public static PostResponseDTO.postDTO toPostResponseDTO(Post post){
        return PostResponseDTO.postDTO.builder()
                    .postId(post.getId())
                    .build();
        }

    public static PostResponseDTO.getTempDTO toTempResponseDTO(Post post, List<String> urls){
        return PostResponseDTO.getTempDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .category(post.getCategory())
                .content(post.getContent())
                .level(post.getLevel())
                .imageUrl(urls)
                .status(post.getStatus())
                .build();
    }



    public static PostResponseDTO.getDTO toGetResponseDTO(Post post, Member member, List<String> urls){
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
                .urls(urls)
                .writerNickName(member.getNickname())
                .writerImage(member.getProfileImage())
                .build();
    }

    public static PostResponseDTO.updateDTO toUpdateResponseDTO(Post post, PostImage postImage){
        return PostResponseDTO.updateDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .content(post.getContent())
                .category(post.getCategory())
                .level(post.getLevel())
                .status(post.getStatus())
                .score(post.getScore())
                .likes(post.getLikes())
                .imageUrl(postImage.getImageUrl())
                .build();

    }
}
