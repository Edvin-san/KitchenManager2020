import java.sql.*;
import java.util.ArrayList;

/**
 * The inventory encapsulated
 * @author Edvin Lundberg
 *
 */

public class Inventory {

	private Connection conn;
	private PreparedStatement getAllProducts;

	public Inventory() {
		try {
			Class.forName("org.postgresql.Driver");

			String URL = "jdbc:postgresql://nestor2.csc.kth.se:5432/edvinlun";
			String USER = "edvinlun";
			String PASS = "rUGf2QEB";
			this.conn = DriverManager.getConnection(URL, USER, PASS);
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			getAllProducts = conn.prepareStatement("SELECT * FROM product");
		} catch (SQLException e) {
			System.out.println("Error: unable to prepare SQL statement.");
			e.printStackTrace();
		}
		ArrayList<Product> test = new ArrayList<Product>();
		test = getProducts();
		System.out.println(test);
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
		ArrayList<Product> listOfAllProducts = new ArrayList<Product>();
		Product currentProduct;
		try {
			ResultSet allProducts = getAllProducts.executeQuery();
			//Statement stat = conn.createStatement();
			//ResultSet allProducts = stat.executeQuery("SELECT * FROM product");
			while (allProducts.next()) {
				//System.out.println("Kommer vi hit?");
				currentProduct = new Product(allProducts.getString(1), allProducts.getInt(2), allProducts.getString(3), allProducts.getBoolean(4));
				listOfAllProducts.add(currentProduct);
				//System.out.println(currentProduct);
			}
			return listOfAllProducts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
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
