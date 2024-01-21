package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.domain.mapping.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
