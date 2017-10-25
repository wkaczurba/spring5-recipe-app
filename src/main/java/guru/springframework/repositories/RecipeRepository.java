package guru.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	//public Optional<Recipe> findByDescription(String description);
}
