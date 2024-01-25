package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.domain.mapping.Member;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Page<Ingredient> findAllByMember(Member member, PageRequest pageRequest);
}
