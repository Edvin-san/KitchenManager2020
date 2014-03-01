import java.sql.*;

/**
 * The inventory encapsulated
 * @author Edvin Lundberg
 *
 */

public class Inventory {

	private Connection conn;
	//TODO
	//List of prepared statements?
	
	
	public Inventory(Connection inConn) {
		this.conn = inConn; // Connection to database
		
	}
	
	
	/**
	 * Add an amount of a product to the kitchen's inventory.
	 * If there is no instance of the product it will simply be added.
	 * 
	 * @return True if the amount was added, False if the process failed.
	 */
	public boolean add(String prodName, Integer amount, String unit) {
		//TODO
		return false;
	}
	
	/**
	 * Remove an amount from a product in the inventory.
	 * If NULL amount is removed, all that will remain is the exact known quantity.
	 * 
	 * @return False if the amount exceeded the current quantity of given product,
	 * 			or the product does not even exist in the database.
	 */
	public boolean remove(String prodName, Integer amount) {
		//TODO //We must do this
		return false;
	}
	
	
	/**
	 * Get the current (known) amount of requested product.
	 * 
	 * @return 
	 */
	public int getAmount(String prodName) {
		//TODO		
		return 0;
	}
	
	/**
	 * @return True if the amount of given product is exactly known,
	 * 			otherwise false.
	 */
	public boolean amountKnown(String prodName) {
		//TODO
		return false;
	}
	
	/**
	 * 
	 * @return Product names in database.
	 */
	public String [] getProducts() {
		//TODO
		return null;
	}
	
	/**
	 * @return Recipe names in database.
	 */
	public String [] getRecipes(){
		//TODO
		return null;
	}
	
	/**
	 * 
	 * @param recipe
	 * @return List of product followed by amount,
	 * 			example: ["Rice", "200", "Milk", "1"].
	 */
	public String [] getIngredients(String recipe) {
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
