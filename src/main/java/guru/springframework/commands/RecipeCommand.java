package guru.springframework.commands;

import java.util.HashSet;
import java.util.Set;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeCommand {
	private Long id;
	private String directions;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;

	private Difficulty difficulty;
	private NotesCommand notes; // Note: Not Note, but NotesCommand.
	private Set<CategoryCommand> categories = new HashSet<>();
	private Set<IngredientCommand> ingredients = new HashSet<>();
	
	//private Byte[] image;
}


