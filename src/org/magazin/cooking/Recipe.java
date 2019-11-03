package org.magazin.cooking;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import java.util.Base64;
import java.util.HashSet;

@Entity(name="Recipe")
@Table(name="Recipes", uniqueConstraints = {
		@UniqueConstraint(columnNames = "NAME"),
		@UniqueConstraint(columnNames = "DESCRIPTION") })
	@NamedQueries({
		@NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r"), 
		@NamedQuery(name = "Recipe.findByName", query = "SELECT r FROM Recipe r WHERE r.name = :recipeName")})
public class Recipe{
	static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
	private String name;
    @Column(name="description")
	private String description;
	@Column(name="image")
	@Lob
	private byte[] image;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade = { 
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	@JoinTable(name = "recipe_ingredient",
    	joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
    	inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
	)
	private Set<Ingredient> ingredientList = new HashSet<>();
	
	@Transient
	private String base64Image;
	
	public Recipe(){}
	
	
	public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
        ingredient.getRecipeList().add(this);
    }
 
	public void removeIngredient(Ingredient ingredient) {
        ingredientList.remove(ingredient);
        ingredient.getRecipeList().remove(this);
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public Set<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Set<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
    
    @Transient
    public String getBase64Image() {
		base64Image = Base64.getEncoder().encodeToString(this.image);
	    return base64Image;
    }
    
    @Transient
	public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
