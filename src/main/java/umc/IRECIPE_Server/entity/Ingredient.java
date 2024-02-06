package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.BaseEntity;
import umc.IRECIPE_Server.common.enums.IngredientCategory;
import umc.IRECIPE_Server.common.enums.Type;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 음식 이름
    private String name;

    // 음식 카테고리
    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

    // 보관 방법
    @Enumerated(EnumType.STRING)
    private Type type;

    // 유통 기한
    private LocalDate expiry_date;

    private String memo;

    private String imageUrl;

    private int remainingDays;

    public void updateIngredient(String name, IngredientCategory category, Type type, LocalDate expiry_date, String memo) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.expiry_date = expiry_date;
        this.memo = memo;
        this.remainingDays = calculateRemainingDay(this.expiry_date);
    }

    static public int calculateRemainingDay(LocalDate expiry_date) {
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(currentDate, expiry_date);
        return (int) daysBetween;
    }
}
