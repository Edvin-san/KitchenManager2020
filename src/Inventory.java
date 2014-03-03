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
	private PreparedStatement getAllRecipes;
	private PreparedStatement getNeededIng;


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
			getProduct = conn.prepareStatement("SELECT * FROM product WHERE name = ?");
			getAllProducts = conn.prepareStatement("SELECT * FROM product ORDER BY name");
			setAmountTo = conn.prepareStatement("UPDATE product SET quantity = ?, uncertain = ? WHERE name = ?");
			addAmountTo = conn.prepareStatement("UPDATE product SET quantity = quantity + ? WHERE name = ? AND unit = ?; INSERT INTO product (name, quantity, unit, uncertain) SELECT ?, ?, ? ,? WHERE NOT EXISTS (SELECT * FROM product WHERE name = ?)");
			getAllRecipes = conn.prepareStatement("SELECT * FROM recipe ORDER BY recID");
			getNeededIng = conn.prepareStatement("SELECT * FROM need");
			removeAmountFrom = conn.prepareStatement("UPDATE product SET quantity = CASE WHEN quantity - ? >= 0 THEN quantity - ?"
					+ "ELSE 0"
					+ "END "
					+ "WHERE name = ? AND unit = ?");

		} catch (SQLException e) {
			System.out.println("Error: unable to prepare SQL statements.");
			e.printStackTrace();
		}
//		ArrayList<Product> test = new ArrayList<Product>();
//		ArrayList<Recipe> test2 = new ArrayList<Recipe>();
//		Product testProduct = new Product("testProduct", 6600, "g", false);
//		remove(testProduct);
//		test = getProducts();
//		test2 = getRecipes();
//		System.out.println(test);
//		ArrayList<String> testrecept = new ArrayList<String>();
//		testrecept.add("Mexican Fried Rice");
//		testrecept.add("Mushroom Quesadillas");
//		testrecept.add("Broccoli Stir Fry");
//		System.out.println(shoppingList(testrecept));
		//System.out.println(canMake(testrecept));
		//System.exit(1);
		
//		Statement stat;
//		try {
//			stat = conn.createStatement();
//			ResultSet countries = stat.executeQuery(""); 
//			} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	

	}


	/**
	 * Add an amount of a product to the kitchen's inventory.
	 * If there is no instance of the product it will simply be added.
	 * 
	 * @return True if the amount was added, False if the process failed.
	 */
	public boolean add(Product prod) {

		String name = prod.getName();
		float amount = prod.getAmount();
		String unit = prod.getUnit();
		boolean uncertain = prod.isKnown();

		ResultSet retrievedProduct = null;
		try {			
			//Do this check first to ensure that the same unit measurement is used
			getProduct.setString(1, name);
			retrievedProduct = getProduct.executeQuery();
		} catch (SQLException e1) {
			//fanns ingen product med detta namn, inget problem.
		}
		try {
			if (retrievedProduct.next()) {
				if(!unit.equals(retrievedProduct.getString(3))){
					return false; //slänga error istället?
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		if (amount < 0) return false;
		try {

			addAmountTo.setFloat(1, amount);
			addAmountTo.setString(2, name);
			addAmountTo.setString(3, unit);
			
			addAmountTo.setString(4, name);
			addAmountTo.setFloat(5, amount);
			addAmountTo.setString(6, unit);
			addAmountTo.setBoolean(7, uncertain);
			addAmountTo.setString(8, name);
			addAmountTo.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Remove an amount from a product in the inventory.
	 * If the amount to be removed is more than in stock the new amount in stock will be 0.
	 * This method does not affect the uncertain field in the database.
	 * 
	 * @return False if the amount exceeded the current quantity of given product,
	 * 			or the product does not even exist in the database.
	 */
	public boolean remove(Product prod) {
		String name = prod.getName();
		float amount = prod.getAmount();
		String unit = prod.getUnit();
		
		ResultSet retrievedProduct = null;
		try {			
			//Do this check first to ensure that the same unit measurement is used
			getProduct.setString(1, name);
			retrievedProduct = getProduct.executeQuery();
		} catch (SQLException e1) {
			//fanns ingen product med detta namn, inget problem.
		}
		try {
			if (retrievedProduct.next()) {
				if(!unit.equals(retrievedProduct.getString(3))){
					return false; //slänga error istället?
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (amount < 0) return false;
		try {
			removeAmountFrom.setFloat(1, amount);
			removeAmountFrom.setFloat(2, amount);
			removeAmountFrom.setString(3, name);
			removeAmountFrom.setString(4, unit);
			removeAmountFrom.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}


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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

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
			while (allProducts.next()) {
				currentProduct = new Product(allProducts.getString(1), allProducts.getFloat(2), allProducts.getString(3), allProducts.getBoolean(4));
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
		ArrayList<Recipe> listOfAllRecipes = new ArrayList<Recipe>();
		Recipe currentRecipe;
		try {
			ResultSet allRecipes = getAllRecipes.executeQuery();
			//System.out.println(allRecipes);
			while (allRecipes.next()) {
				currentRecipe = new Recipe(allRecipes.getString(2), allRecipes.getString(3), allRecipes.getString(4), getIngredients(allRecipes.getInt(1)));
				listOfAllRecipes.add(currentRecipe);
			}
			return listOfAllRecipes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Recipe> getRecipes(ArrayList<String> recipes){
		ArrayList<Recipe> listOfAllRecipes = new ArrayList<Recipe>();
		Recipe currentRecipe;
		try {
			ResultSet allRecipes = getAllRecipes.executeQuery();
			//System.out.println(allRecipes);
			while (allRecipes.next()) {
				currentRecipe = new Recipe(allRecipes.getString(2), allRecipes.getString(3), allRecipes.getString(4), getIngredients(allRecipes.getInt(1)));
				if(recipes.contains(allRecipes.getString(2))){
					listOfAllRecipes.add(currentRecipe);
				}	
			}
			return listOfAllRecipes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param recipe
	 * @return List of product followed by amount,
	 * 			example: ["Rice", "200", "Milk", "1"].
	 */
	public ArrayList<Product> getIngredients(int recipeID) {
		//getNeededIng = conn.prepareStatement("SELECT prodName, quantityNeeded, unit FROM need ORDER BY prodName");
		ArrayList<Product> listOfAllNeededProducts = new ArrayList<Product>();
		Product currentProduct;
		try {
			ResultSet allProducts = getNeededIng.executeQuery();

			while (allProducts.next()) {
				currentProduct = new Product(allProducts.getString(2), allProducts.getFloat(3), allProducts.getString(4), false);
				listOfAllNeededProducts.add(currentProduct);
			}
			return listOfAllNeededProducts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Recipe> canMake(ArrayList<String> recipesToMake) {
		String recipeName;
		ArrayList<Recipe> returnList = new ArrayList<Recipe>();
		Recipe tempRecipe;
		ResultSet temp;
		for(int i = 0; i < recipesToMake.size(); i++){
			recipeName = recipesToMake.get(i);
			try {
				PreparedStatement getFail = conn.prepareStatement("SELECT prodname, quantity, quantityNeeded, uncertain, unitToDisplay FROM (SELECT *, temp3.unit AS unitToDisplay FROM (recipe JOIN need ON recipe.recID = need.recID AND recipe.name = ?) AS temp3 LEFT JOIN product ON product.name = temp3.prodname) AS temp1 WHERE quantity < quantityNeeded OR quantity IS NULL");
				getFail.setString(1, recipeName);
				temp = getFail.executeQuery();

				ArrayList<Product> tempList = new ArrayList<Product>();
				boolean canMakeRecipe = true;
				while (temp.next()) {
					try{
						boolean uncertain = temp.getBoolean(4);
						if(uncertain){
							//osäkerhet om hur mycket av denna product vi har.
							Product tempProd = new Product(temp.getString(1), temp.getInt(3), temp.getString(5), uncertain);
							tempList.add(tempProd);
						} else {
							canMakeRecipe = false;
							break; //ingen osäkerhet och vi kan inte göra denna produkt.
						}
					} catch (Exception e){
						//null värde i databasen dvs saknade denna ingrediens helt och kan inte göra detta recept alls.
						canMakeRecipe = false;
						break;
					}


				}
				if(canMakeRecipe){
					tempRecipe = new Recipe(recipeName, "", "test", tempList);
					returnList.add(tempRecipe);
				}

			} catch (SQLException e) {
				//Allting fanns för receptet.
				e.printStackTrace();
				ArrayList<Product> tempList = new ArrayList<Product>();
				tempRecipe = new Recipe(recipeName, "test", "", tempList);
				returnList.add(tempRecipe);
			}

		}

		return returnList;

	}
	
	public ArrayList<Product> shoppingList(ArrayList<String> recipesToBuyFor){
		StringBuilder recipes = new StringBuilder();
		recipes.append("'");
		recipes.append(recipesToBuyFor.get(0));
		recipes.append("'");
		ArrayList<Product> returnList = new ArrayList<Product>();
		for(int i = 1; i < recipesToBuyFor.size(); i++){
			recipes.append(" OR recipe.name = '");
			recipes.append(recipesToBuyFor.get(i));
			recipes.append("'");
		}
		
		String allRecipes = recipes.toString();
		System.out.println(allRecipes);
		try {
			PreparedStatement getMissing = conn.prepareStatement("SELECT prodname,  sum(quantityNeeded)- max(coalesce(quantity, 0)) AS needToBuy, MAX(unitToDisplay) " +
																	"FROM (SELECT prodname, quantity, quantityNeeded, unitToDisplay " +
																					"FROM (SELECT temp2.name, prodName, quantity, quantityNeeded, temp2.unit AS unitToDisplay " +
																							"FROM (recipe JOIN need ON recipe.recID = need.recID AND (recipe.name = " + allRecipes + ")) as temp2 " +
																									"LEFT JOIN product ON product.name = temp2.prodname) AS temp1) AS temp32 " +
																	"GROUP BY prodname HAVING sum(quantityNeeded)- max(COALESCE(quantity,0)) > 0"); // 
			//getMissing.setString(1, allRecipes);
			ResultSet neededProds = getMissing.executeQuery();

			while (neededProds.next()) {
				Product currentProduct = new Product(neededProds.getString(1), neededProds.getFloat(2), neededProds.getString(3), false);
				returnList.add(currentProduct);
			}
			System.out.println(returnList.size());
			return returnList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//getMissing.setString(1, );
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
	//	PreparedStatement stat2 = conn.prepareStatement("SELECT * FROM product"); //FÃ¶r optimera typ.
	//	
	//	ResultSet foodstuffs = stat.executeQuery("SELECT * FROM product");
	//	
	//	while (foodstuffs.next()) {
	//		String myString = foodstuffs.getString(1) + " " + foodstuffs.getInt(2) + foodstuffs.getString(3) + " " + foodstuffs.getBoolean(4);
	//		System.out.println(myString);
	//	}

}
