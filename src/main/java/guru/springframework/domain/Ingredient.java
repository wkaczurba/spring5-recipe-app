package guru.springframework.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	
	@ManyToOne // NO CASCADING, AS THIS IS A CHILD OF Recipe.
	@Getter @Setter
	private Recipe recipe;
	
	@Getter @Setter
	private String description;

	@Getter @Setter
	private BigDecimal amount;

	@Getter @Setter
	@OneToOne(fetch = FetchType.EAGER)
	private UnitOfMeasure unitOfMeas;	
}


// ** NOTE: One-To-Many **
//
//    On the parent's (Recipe) side it should be:
//      + @OneToMany(cascade = CascadeType.ALL; mappedBy="recipe")
//      + Set<Ingredients> Ingredients
//    NOTE: mappedBy="" refers to the child field's name.
//
//    On the child's (Ingredient) side it should be:
//      + @ManyToOne
//      + Recipe recipe;
//    And NO CASCADING ON THE CHILD'S SIDE.
//
//   ----
