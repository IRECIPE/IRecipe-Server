package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 사용자가 가지고 있는 재료 이름 조회
    List<Ingredient> findNamesByMember_PersonalId(String memberId);

    Page<Ingredient> findAllByMember(Member member, PageRequest pageRequest);
}
