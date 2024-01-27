package umc.IRECIPE_Server.converter;

import umc.IRECIPE_Server.dto.PostResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.PostImage;

public class PostConverter {

    public static PostResponseDTO.postResponseDTO toPostResponseDTO(Post post){
        return PostResponseDTO.postResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .category(post.getCategory())
                .content(post.getContent())
                .level(post.getLevel())
                .status(post.getStatus())
                .build();
    }

    public static PostResponseDTO.getResponseDTO toGetResponseDTO(Post post, Member member, String postImage){
        return PostResponseDTO.getResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .content(post.getContent())
                .category(post.getCategory())
                .status(post.getStatus())
                .likes(post.getLikes())
                .score(post.getScore())
                .imageUrl(postImage)
                .writerNickName(member.getNickname())
                .writerImage(member.getProfileImage())
                .build();
    }

    public static PostResponseDTO.updateResponseDTO toUpdateResponseDTO(Post post, PostImage postImage){
        return PostResponseDTO.updateResponseDTO.builder()
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
