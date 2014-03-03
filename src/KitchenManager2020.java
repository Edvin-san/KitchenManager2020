import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * THIS IS THE GUI
 * @author edvinlun
 *
 * Detta ska st�djas:
 * 
 * Lista food items X
 * L�gga till		X
 * Ta bort			X
 * 
 * Kan (m�jligtvis) g�ra recept?
 * F�resl� det som beh�vs
 * V�lja flera recept -> generera minimal ink�pslista
 * Kunna registrera att man gjort ett recept -> uppdatera inventory
 * 
 */
public class KitchenManager2020 extends JFrame {

	//GUI programming is awesome -_-
	private Kitchen kitchen;
	private JPanel prodPanel;
	private JButton invButton;
	private JButton addButton;
	private JButton remButton;
	private JTextArea prodName;
	private JTextArea amount;
	private JTextArea unit;
	private JTextArea res;

	private JPanel recPanel;
	private ArrayList<JCheckBox> chkboxes;
	private JButton canMake;
	private JButton make;
	private JButton shopList;

	/**
	 * Initializer 
	 */
	public static void main(String [] args) {
		new KitchenManager2020();
	}

	/**
	 * Constructor for KitchenManager2020.
	 */
	public KitchenManager2020() {
		super("KitchenManager2020");
		this.kitchen = new Kitchen();

		setupFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,600);
		setLocation(600, 200);

		setVisible(true);
	}


	/**
	 * Basically creates the GUI.
	 */
	private void setupFrame() {
		//Setup contentPane;
		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		setupProdPanel();
		add(prodPanel);
		setupRecPanel();
		add(recPanel);
	}

	/**
	 * Sets up the Product Panel and its various components.
	 */
	private void setupProdPanel() {
		prodPanel = new JPanel();
		prodPanel.setLayout(new BoxLayout(prodPanel, BoxLayout.X_AXIS));

		//Labels for text fields
		JLabel prodLabel = new JLabel("Product name: ");
		JLabel amountLabel = new JLabel("Amount: ");
		JLabel unitLabel = new JLabel("Unit: ");
		JLabel resLabel = new JLabel("Result: ");

		//Text fields
		prodName = new JTextArea("*write product name here*");
		amount = new JTextArea("*write amount here*");
		unit = new JTextArea("*write unit here*");
		res = new JTextArea("*Result of your action*");
		res.setEditable(false);

		//Add above to prodPanel
		JPanel p1 = new JPanel(); JPanel p2 = new JPanel();
		JPanel p3 = new JPanel(); JPanel p4 = new JPanel(); p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		p1.add(prodLabel); p1.add(prodName);
		p2.add(amountLabel); p2.add(amount);
		p3.add(unitLabel); p3.add(unit);
		p4.add(resLabel); p4.add(res);
		prodPanel.add(p1); prodPanel.add(p2);
		prodPanel.add(p3); prodPanel.add(p4);

		JPanel bPanel = new JPanel();
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));

		//Add button
		addButton = new JButton("Add");
		addButton.addActionListener(new addActionListener());
		bPanel.add(addButton);

		//Remove button
		remButton = new JButton("Remove");
		remButton.addActionListener(new remActionListener());
		bPanel.add(remButton);

		//Inventory button
		invButton = new JButton("Inventory");
		invButton.addActionListener(new invActionListener());
		bPanel.add(invButton);

		prodPanel.add(bPanel);

	}

	/**
	 * Sets up the Recipe Panel and its various components.
	 */
	private void setupRecPanel() {
		recPanel = new JPanel();
		recPanel.setLayout(new BoxLayout(recPanel,BoxLayout.X_AXIS));
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		JLabel recLabel = new JLabel("Recipes: ");


		//All recipes get a checkbox
		ArrayList<Recipe> recipes = kitchen.getRecipes();
		if (recipes != null) {
			Iterator<Recipe> it = recipes.iterator();
			chkboxes = new ArrayList<JCheckBox>();
			Recipe tmprec;
			JCheckBox box;
			while (it.hasNext()) {
				tmprec = it.next();
				box = new JCheckBox(tmprec.getName());
				chkboxes.add(box);
				boxPanel.add(box);
			}
		}


		// can make button
		canMake = new JButton("Can I make this?");
		canMake.addActionListener(new canMakeActionListener());

		// shopList button
		shopList = new JButton("Generate shopping list.");
		shopList.addActionListener(new shopListActionListener());

		// make button
		make = new JButton("I have now made this.");
		make.addActionListener(new makeActionListener());


		recPanel.add(recLabel);
		recPanel.add(boxPanel);
		recPanel.add(canMake);
		recPanel.add(shopList);
		recPanel.add(make);

	}

	class invActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			ArrayList<Product> prods = kitchen.getProducts();

			if (prods == null) {
				res.setText("You have no products in your inventory.");
				return;
			} else {
				Iterator<Product> it = prods.iterator();
				StringBuilder sb = new StringBuilder();

				Product tmp;
				// Build string of all product names
				while (it.hasNext()) {
					tmp = it.next();
					sb.append(tmp.getName() + " " + tmp.getAmount() + tmp.getUnit() + "\n");
				}

				// update result text field
				res.setText(sb.toString());
			}

		}
	}

	class addActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Product tmpProd = createProduct();
			if (tmpProd == null) {
				res.setText("Invalid input!");
			} else {
				if ( !(kitchen.addProduct(tmpProd)) ) {
					res.setText("Something went wrong when trying to add this product :S");
				} else {
					res.setText(tmpProd.getAmount() + tmpProd.getUnit() + " of " 
							+ tmpProd.getName() + " was successfully added!");
				}
			}
		}
	}

	class remActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Product tp = createProduct();
			if (tp == null) {
				res.setText("Invalid input!");
			} else {
				if ( !(kitchen.removeProduct(tp)) ) {
					res.setText("Something went wrong when trying to remove this product :S");
				} else {
					res.setText(tp.getAmount() + tp.getUnit() + " of " 
							+ tp.getName() + " was successfully removed!");
				}
			}
		}
	}

	class canMakeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// stringbuilder to build our result message
			StringBuilder sb = new StringBuilder();

			ArrayList<String> selected = getSelected();
			ArrayList<Recipe> recips = kitchen.canMake(selected);

			// Append all recipe-names that can definitely be made
			for (Recipe r : recips) {
				if (r.getProdsNeeded().isEmpty()) {
					sb.append(r.getName() + " can definitely be made!\n");
				}
			}

			// Append all recipe-name that can definitely not be made
			for (String s : selected) {
				boolean isOK = false;
				for (Recipe r : recips) {
					if (r.getName().equals(s)) {
						isOK = true;
						break;
					}
				}
				// If isOK is false now, the recipe can definitely not be made
				if (!isOK) {
					sb.append(s + " can definitely not be made.\n");
				}
			}

			// Append all recipe-names (which can possibly be made) followed by
			// a list of suggestions of quantities needed.
			for (Recipe r : recips) {
				ArrayList<Product> pr = r.getProdsNeeded();
				if (!pr.isEmpty()) {
					sb.append(r.getName() + " can possibly be made,\nyou must ensure that you have atleast: \n");
					for (Product p : pr) {
						sb.append("- " + p.getAmount() + p.getUnit() + " of " + p.getName() + "\n");
					}
				}
			}

			// Show result
			res.setText(sb.toString());
		}
	}

	class makeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			//removal status
			boolean okrem = kitchen.make(getSelected());

			if (!okrem) {
				res.setText("Something went wrong. Please try again.\nPlease select atleast one recipe");
			} else {
				res.setText("You have made your recipe(s)\nand the system is up-to-date!");
			}
		}
	}

	class shopListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			StringBuilder sb = new StringBuilder();
			ArrayList<String> selected = getSelected();

			if (selected.isEmpty()) {
				sb.append("Please select atleast one recipe.");
			} else {


				ArrayList<Product> needed = kitchen.genShopList(selected);
				if (needed.isEmpty()) {
					sb.append("You have all the products \nneeded to make the selected recipes!");
				} else {
					sb.append("To make the selected recipes\nYou will need to buy: \n");
					for (Product p : needed) {
						sb.append("- " + p.getAmount() + p.getUnit() + " of " + p.getName() + "\n");
					}
				}
			}
			
			res.setText(sb.toString());
		}
	}

	/**
	 * Read input and try to create a product.
	 * @return Null if invalid input.
	 */
	private Product createProduct() {
		return kitchen.createProduct(prodName.getText(), amount.getText(), unit.getText());
	}

	/**
	 * Read status on checkboxes and return selected recipe-names, never null.
	 * @return
	 */
	private ArrayList<String> getSelected() {
		ArrayList<String> selected = new ArrayList<String>(); 
		Iterator<JCheckBox> it = chkboxes.iterator();
		JCheckBox tmp;
		while (it.hasNext()) {
			tmp = it.next();
			if (tmp.isSelected()) {
				selected.add(tmp.getText());
			}
		}
		return selected;		
	}

}
