package umc.IRECIPE_Server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.StoredRecipe;


public interface StoredRecipeRepository extends JpaRepository<StoredRecipe, Long> {
    List<StoredRecipe> findAllByMember(Member member);
}
