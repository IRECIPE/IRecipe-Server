package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.IRECIPE_Server.common.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DislikedFood extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 싫어하는 음식(재료) 이름
    private String name;
}
