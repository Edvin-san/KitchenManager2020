import java.util.ArrayList;


/**
 * A recipe containing
 * 	name,
 * 	type(?),
 * 	description,
 * 	products needed.
 * 
 * @author edvinlun
 *
 */
public class Recipe {

	private String name;
	private String type;
	private String description;
	private ArrayList<Product> prodsNeeded;
	
	
	public Recipe(String name, String type, String description, ArrayList<Product> productsNeeded) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.prodsNeeded = productsNeeded;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public ArrayList<Product> getProdsNeeded() {
		return prodsNeeded;
	}


	public void setProdsNeeded(ArrayList<Product> prodsNeeded) {
		this.prodsNeeded = prodsNeeded;
	}
	public String toString(){
		return name + " type: " + type + " description: " + description + " needed ingredients: " + prodsNeeded;
	}
	
}
