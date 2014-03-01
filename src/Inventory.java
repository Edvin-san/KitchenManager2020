import java.sql.*;
import java.util.ArrayList;

/**
 * The inventory encapsulated
 * @author Edvin Lundberg
 *
 */

public class Inventory {

	private Connection conn;
	//TODO
	
	public Inventory(Connection inConn) {
		this.conn = inConn; // Connection to database.
	}
	
	
	/**
	 * Add an amount of a product to the kitchen's inventory.
	 * If there is no instance of the product it will simply be added.
	 * 
	 * @return True if the amount was added, False if the process failed.
	 */
	public Product add(Product prod) {
		//TODO
		return null;
	}
	
	/**
	 * Remove an amount from a product in the inventory.
	 * If NULL amount is removed, all that will remain is the exact known quantity.
	 * 
	 * @return False if the amount exceeded the current quantity of given product,
	 * 			or the product does not even exist in the database.
	 */
	public Product remove(Product prod, float amount) {
		//TODO
		return null;
	}
	
	/**
	 * Set amount of a product in the inventory and remove any 
	 * uncertainty regarding the amount of the product.
	 * The human is probably wrong, but must be led to 
	 * believe it is in charge.
	 *  
	 * @return False if amount less than zero.
	 */
	public boolean setAmount(Product p){
		
		return false;
	}

	/**
	 * 
	 * @return Product info for all products in the database with more than 0 in the amount field.
	 */
	public ArrayList<Product> getProducts() {
		//TODO
		return null;
	}
	
	/**
	 * @return Recipe names in database.
	 */
	public ArrayList<Recipe> getRecipes(){
		//TODO
		return null;
	}
	
	/**
	 * 
	 * @param recipe
	 * @return List of product followed by amount,
	 * 			example: ["Rice", "200", "Milk", "1"].
	 */
	public ArrayList<Product> getIngredients(String recipe) {
		//TODO
		return null;
	}
	
//	Statement stat = conn.createStatement();
//	
////	ResultSet countries = stat.executeQuery("SELECT name FROM country ORDER BY name");
////	
////	while (countries.next()) {
////		String myString = countries.getString(1);
////		System.out.println(myString);
////	}
//	
//	PreparedStatement stat2 = conn.prepareStatement("SELECT * FROM product"); //För optimera typ.
//	
//	ResultSet foodstuffs = stat.executeQuery("SELECT * FROM product");
//	
//	while (foodstuffs.next()) {
//		String myString = foodstuffs.getString(1) + " " + foodstuffs.getInt(2) + foodstuffs.getString(3) + " " + foodstuffs.getBoolean(4);
//		System.out.println(myString);
//	}
	
}
