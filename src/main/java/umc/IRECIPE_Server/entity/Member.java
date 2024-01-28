package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.IRECIPE_Server.common.BaseEntity;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.common.enums.Role;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //권한
    @Enumerated(EnumType.STRING)
    private Role role;

    // 이름
    private String name;

    // 닉네임
    private String nickname;

    // 성별
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 나이
    @Enumerated(EnumType.STRING)
    private Age age;

    // 회원 고유 id
    @Column(updatable = false, unique = true, nullable = false)
    private String personalId;

    // 프로필 사진
    private String profileImage;

    // 중요 알림 온오프여부
    private Boolean important;

    // 활동 알림 온오프여부
    private Boolean activity;

    // 이벤트 알림 온오프여부
    private Boolean event;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberAllergy> memberAllergyList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberLikes> memberLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Answer> AnswerList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StoredRecipe> recipeList = new ArrayList<>();
}