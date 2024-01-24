package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "query_id")
    private Long id;

    // 질문
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
