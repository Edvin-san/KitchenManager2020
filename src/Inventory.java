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
	private PreparedStatement setAmountTo;
	private PreparedStatement getProduct;
	private PreparedStatement addAmountTo;
	private PreparedStatement removeAmountFrom;

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
		//Setting up the prepared SQL-queries we will need.
		try {
			getAllProducts = conn.prepareStatement("SELECT * FROM product ORDER BY name");
			setAmountTo = conn.prepareStatement("UPDATE product SET quantity = ?, uncertain = ? WHERE name = ?");
			addAmountTo = conn.prepareStatement("UPDATE product SET quantity = quantity + ? WHERE name = ?");
			removeAmountFrom = conn.prepareStatement("UPDATE product SET quantity = CASE WHEN quantity - ? >= 0 THEN quantity - ?"
					+ "ELSE 0"
					+ "END "
					+ "WHERE name = ?");
			
		} catch (SQLException e) {
			System.out.println("Error: unable to prepare SQL statements.");
			e.printStackTrace();
		}
		ArrayList<Product> test = new ArrayList<Product>();
		Product testProduct = new Product("long grain rice", 500, "g", false);
		remove(testProduct);
		test = getProducts();
		System.out.println(test);
		System.exit(1);
	}


	/**
	 * Add an amount of a product to the kitchen's inventory.
	 * If there is no instance of the product it will simply be added.
	 * 
	 * @return True if the amount was added, False if the process failed.
	 */
	public Product add(Product prod) {
		String name = prod.getName();
		float amount = prod.getAmount();
		String unit = prod.getUnit();
		if (amount < 0) return null;
		try {
			addAmountTo.setFloat(1, amount);
			addAmountTo.setString(2, name);
			addAmountTo.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Remove an amount from a product in the inventory.
	 * If NULL amount is removed, all that will remain is the exact known quantity.
	 * 
	 * @return False if the amount exceeded the current quantity of given product,
	 * 			or the product does not even exist in the database.
	 */
	public Product remove(Product prod) {
		String name = prod.getName();
		float amount = prod.getAmount();
		String unit = prod.getUnit();
		if (amount < 0) return null;
		try {
			removeAmountFrom.setFloat(1, amount);
			removeAmountFrom.setFloat(2, amount);
			removeAmountFrom.setString(3, name);
			removeAmountFrom.executeUpdate();
} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

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
		String name = p.getName();
		float amount = p.getAmount();
		if (amount < 0) return false;
		try {
			setAmountTo.setString(3, name);
			setAmountTo.setFloat(1, amount);
			setAmountTo.setBoolean(2, false);
			setAmountTo.executeUpdate();

			} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
				currentProduct = new Product(allProducts.getString(1), allProducts.getInt(2), allProducts.getString(3), allProducts.getBoolean(4));
				listOfAllProducts.add(currentProduct);
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
