package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.IRECIPE_Server.common.enums.Type;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 사용자가 가지고 있는 재료 이름 조회
    @Query("SELECT i.name FROM Ingredient i JOIN i.member m WHERE m.id = :memberId")
    List<String> findNamesByMemberId(@Param("memberId") String memberId);

    // 유통기한으로 정렬된 사용자의 재료 리스트 조회
    @Query("SELECT i.name FROM Ingredient i JOIN i.member m WHERE m.id = :memberId ORDER BY i.expiry_date ASC")
    List<String> findIngredientsNameOrderByExpiryDate(@Param("memberId") String memberId);

    Page<Ingredient> findAllByMember(Member member, PageRequest pageRequest);

    Page<Ingredient> findAllByMemberAndType(Member member, Type type, PageRequest pageRequest);

    @Query("SELECT i FROM Ingredient i JOIN i.member m WHERE m.personalId = :memberId AND lower(i.name) LIKE lower(concat('%', :name, '%'))")
    Page<Ingredient> findAllByMemberAndName(@Param("memberId") String memberId, @Param("name") String name, PageRequest pageRequest);
}
