package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// @OneToOne will allow moving backk to the Recipe, but:
	// NO CASCADING, as we dont want to delete Notes on deletion of recipe;
	@OneToOne 
	private Recipe recipe; // parent recipe
	
	@Lob
	private String recipeNotes;
	
	
}

// MISTAKES: forgot private; 
// MISTAKES: often forgot: @GeneratedValue(strategy = GenerationType.IDENTITY) 
// PAY ATTENTION: to long Strings; need to annotate them with @Lob;
// 