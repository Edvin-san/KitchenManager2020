

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Middleman mellan gui och inventory
 * @author Edvin Lundberg
 *
 */
public class Kitchen {

	private Inventory inventory;

	public Kitchen() {
		this.inventory = new Inventory();
	}

	/**
	 * @return ArrayList of the recipes in kitchen. 
	 */
	public ArrayList<Recipe> getRecipes() {
		return inventory.getRecipes();
	}

	/**
	 * @param prodName
	 * @param amount
	 * @param unit
	 * @return A product created. Null if amount invalid.
	 */
	public Product createProduct(String prodName, String amount, String unit) {
		float a = toAmount(amount);
		if (a < 0) {
			return null;
		} else {
			Product prod = new Product(prodName, a, unit, true);
			return prod;
		}
	}

	/**
	 * @param amount
	 * @return -1 if string not valid float. 
	 * 			Otherwise the string parsed as float. 
	 */
	private float toAmount(String amount) {
		try {
			float a = Float.valueOf(amount);
			return a;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 
	 * @param p Product to be added
	 * @return False if process failed, else true. 
	 */
	public boolean addProduct(Product p) {
		return inventory.add(p);
	}
	
	public boolean removeProduct(Product p) {
		return inventory.remove(p);
	}
	
	/**
	 * @return ArrayList of products in inventory.
	 */
	public ArrayList<Product> getProducts() {
		return inventory.getProducts();		
	}

	/**
	 * 
	 * @param selected List of selected recipe-names
	 * @return ArrayList of recipe-object defined:
	 * 			recipe not present = definitely can't make recipe
	 * 			recipe has 0 products in prodsNeeded = definitely can make
	 * 			recipe has some Products in prodsNeeded = 
	 * 				these products will need to be checked that their
	 * 				amount is present in the kitchen. 
	 */
	public ArrayList<Recipe> canMake(ArrayList<String> selected) {
		return inventory.canMake(selected);		
	}

	/**
	 * Generate a shopping list of products. 
	 * Each product element has an amount needed. 
	 * @return
	 */
	public ArrayList<Product> genShopList(ArrayList<String> recipes) {
		return inventory.shoppingList(recipes);
	}

	/**
	 * Make a set of recipes, ignore any values in database.
	 */
	public boolean make(ArrayList<String> selected) {
		ArrayList<Recipe> toBeRemoved = inventory.getRecipes(selected);
		if(toBeRemoved == null){
			return false;
		}
		ArrayList<Product> temp = new ArrayList<Product>();
		for(int i = 0; i < toBeRemoved.size(); i++){
			temp = toBeRemoved.get(i).getProdsNeeded();
			
			for(int j = 0; j < temp.size(); j++){
				inventory.remove(temp.get(j));
			}
		}
		return true;
		//TODO
		//return inventory.make(selected);		
	}
}
