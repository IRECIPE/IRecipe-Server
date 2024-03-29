package umc.IRECIPE_Server.converter;


import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.dto.response.PostResponseDTO;

import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;

import java.util.List;
import java.util.Map;
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
                .createdAt(post.getCreatedAt().toLocalDate())
                .build();
    }



    public static PostResponseDTO.getDTO toGetResponseDTO(Post post, Member member, boolean likeOrNot, boolean myPost){
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
                .createdAt(post.getCreatedAt().toLocalDate())
                .reviewsCount(post.getReviewList().size())
                .likeOrNot(likeOrNot)
                .myPost(myPost)
                .build();
    }

    public static PostResponseDTO.updateDTO toUpdateResponseDTO(Post post){
        return PostResponseDTO.updateDTO.builder()
                .postId(post.getId())
                .build();
    }

    public static List<PostResponseDTO.getDTO> toGetAllPostListDTO(Member member, List<Post> postPage, Map<Long, Boolean> likeMap){
        return postPage.stream()
                .map(m -> PostResponseDTO.getDTO.builder()
                        .postId(m.getId())
                        .title(m.getTitle())
                        .subhead(m.getSubhead())
                        .imageUrl(m.getImageUrl())
                        .likes(m.getLikes())
                        .score(m.getScore())
                        .reviewsCount(m.getReviewList().size())
                        .writerImage(m.getMember().getProfileImage())
                        .writerNickName(m.getMember().getNickname())
                        .createdAt(m.getCreatedAt().toLocalDate())
                        .likeOrNot(likeMap.get(m.getId()))
                        .category(m.getCategory())
                        .content(m.getContent())
                        .myPost(m.getMember().equals(member))
                        .build())
                .collect(Collectors.toList());
    }

    public static List<PostResponseDTO.getAllPostDTO> toGetAllPostDTO(Page<Post> postPage, Map<Long, Boolean> likeMap){
        return postPage.stream()
                .map(m -> PostResponseDTO.getAllPostDTO.builder()
                        .postId(m.getId())
                        .title(m.getTitle())
                        .subhead(m.getSubhead())
                        .imageUrl(m.getImageUrl())
                        .likes(m.getLikes())
                        .score(m.getScore())
                        .reviewsCount(m.getReviewList().size())
                        .nickName(m.getMember().getNickname())
                        .memberImage(m.getMember().getProfileImage())
                        .createdAt(m.getCreatedAt().toLocalDate())
                        .likeOrNot(likeMap.get(m.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    public static PostResponseDTO.LikePostDTO toLikePostDTO(Post post) {
        return PostResponseDTO.LikePostDTO.builder()
                .postId(post.getId())
                .likes(post.getLikes())
                .build();
    }

    public static PostResponseDTO.getRankedPostDTO toGetRankedPostDTO(Post post) {

        return PostResponseDTO.getRankedPostDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .scores(post.getScore())
                .likes(post.getLikes())
                .imageUrl(post.getImageUrl())
                .scoresInOneMonth(post.getScoreInOneMonth())
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
