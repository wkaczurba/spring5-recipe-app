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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@EqualsAndHashCode(exclude={"recipe"})
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne // NO CASCADING, AS THIS IS A CHILD OF Recipe.
	private Recipe recipe;
	
	private String description;

	private BigDecimal amount;

	@OneToOne(fetch = FetchType.EAGER)
	private UnitOfMeasure unitOfMeas;
	
    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeas = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeas = uom;
        this.recipe = recipe;
    }	
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
