package guru.springframework.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	
	
	@Getter
	@Setter
	private String description;
	
//	@Getter
//	@Setter
//	private BigDecimal amount;
	
	@Getter
	@Setter
	@ManyToMany(mappedBy="categories")
	private Set<Recipe> recipe;

	
	
}
