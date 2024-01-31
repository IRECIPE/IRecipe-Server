package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.IRECIPE_Server.common.BaseEntity;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.common.enums.Role;
import umc.IRECIPE_Server.common.enums.Type;

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

    // 중요 알림 온오프여부
    private Boolean important;

    // 활동 알림 온오프여부
    private Boolean activity;

    // 이벤트 알림 온오프여부
    private Boolean event;

    // 프로필 사진
    private String profileImage;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ingredient> ingredientList = new ArrayList<>();

    public void updateMember(String name, String nickname, Integer gender, Integer age, Boolean important, Boolean event, Boolean activity, List<MemberAllergy> memberAllergyList) {
        this.name = name;
        this.nickname = nickname;
        Age newAge = switch (age) {
            case 1 -> Age.TEN;
            case 2 -> Age.TWENTY;
            case 3 -> Age.THIRTY;
            case 4 -> Age.FORTY;
            case 5 -> Age.FIFTY;
            case 6 -> Age.SIXTY;
            default -> this.age;
        };
        this.age = newAge;

        Gender newGender = switch (gender) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            default -> this.gender;
        };
        this.gender = newGender;

        this.important = important;
        this.event = event;
        this.activity = activity;
        this.memberAllergyList = memberAllergyList;
    }
}