package guru.springframework.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	
	@Getter @Setter 
	private String description;
	
	@Getter @Setter
	private Integer prepTime;
	
	@Getter @Setter
	private Integer cookTime;
	
	@Getter @Setter
	private Integer servings;

	@Getter @Setter
	private String source;
	
	@Getter @Setter
	private String url;
	
//	@Getter @Setter
//	private String directions;
	
	@Enumerated(value = EnumType.STRING)
	@Getter @Setter
	private Difficulty difficulty;

	@OneToOne(cascade = CascadeType.ALL)
	@Getter @Setter
	private Notes notes;

	@ManyToMany
	@Getter @Setter
	@JoinTable(name = "recipe_category",
			joinColumns = @JoinColumn(name="recipe_id"), 
			inverseJoinColumns=@JoinColumn(name="category_id"))
	private Set<Category> categories;

	// NOTE: We are using mappedBy -> to point to a property on a child's object
	@OneToMany(cascade = CascadeType.ALL, mappedBy="recipe")
	@Getter @Setter
	private Set<Ingredient> ingredients;
	
	@Lob
	@Getter @Setter
	private Byte[] image;
	
}
