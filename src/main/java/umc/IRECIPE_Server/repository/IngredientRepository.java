package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
