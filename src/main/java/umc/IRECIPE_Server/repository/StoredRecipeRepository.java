package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.StoredRecipe;

public interface StoredRecipeRepository extends JpaRepository<StoredRecipe, Long> {
}
