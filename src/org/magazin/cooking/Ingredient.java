package org.magazin.cooking;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="Ingredient")
@Table(name="Ingredients", uniqueConstraints = {
		@UniqueConstraint(columnNames = "NAME")})

@NamedQueries({ 
	@NamedQuery(name = "Ingredient.findByName", query = "SELECT r FROM Recipe r WHERE r.name = :ingredientName")})
public class Ingredient implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
	@Column(name = "PRICE")
	private Integer price;
	@Column(name = "NAME")
	private String name;
	//@ManyToMany(mappedBy = "ingredientList")
	@ManyToMany(fetch=FetchType.EAGER,
			cascade = { 
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	@JoinTable(name = "recipe_ingredient",
    	joinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"),
    	inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id")
	)
	
	
	private Set<Recipe> recipeList = new HashSet<>();
	
	@Transient
	private int quantity;
	
	
	public Ingredient(){}
	
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Set<Recipe> getRecipeList(){
		return recipeList;
	}
	
	public int getQuantity() {
		return price;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
}
