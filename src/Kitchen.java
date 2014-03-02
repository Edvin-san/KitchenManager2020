

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
	 * @return Null if process failed, else same product entered. 
	 */
	public Product addProduct(Product p) {
		return inventory.add(p);
	}
}
