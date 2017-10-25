package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	
	// @OneToOne will allow moving backk to the Recipe, but:
	// NO CASCADING, as we dont want to delete Notes on deletion of recipe;
	@OneToOne 
	@Getter @Setter
	private Recipe recipe; // parent recipe
	
	@Lob
	@Getter @Setter
	private String recipeNotes;
	
	
}

// MISTAKES: forgot private; 
// MISTAKES: often forgot: @GeneratedValue(strategy = GenerationType.IDENTITY) 
// PAY ATTENTION: to long Strings; need to annotate them with @Lob;
// 