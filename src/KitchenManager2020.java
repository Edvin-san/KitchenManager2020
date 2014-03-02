import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * THIS IS THE GUI
 * @author edvinlun
 *
 * Detta ska stödjas:
 * 
 * Lista food items
 * Lägga till
 * Ta bort
 * 
 * Kan (möjligtvis) göra recept?
 * Föreslå det som behövs
 * Välja flera recept -> generera minimal inköpslista
 * Kunna registrera att man gjort ett recept -> uppdatera inventory
 *
 */
public class KitchenManager2020 extends JFrame {

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
	private Checkbox ch;

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
		setSize(800,600);
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
		prodPanel.setLayout(new BoxLayout(prodPanel, BoxLayout.LINE_AXIS));
		JLabel prodLabel = new JLabel("Product name: ");
		JLabel amountLabel = new JLabel("Amount: ");
		JLabel unitLabel = new JLabel("Unit: ");
		JLabel resLabel = new JLabel("Result: ");

		prodName = new JTextArea("*product name here*");
		amount = new JTextArea("*amount here*");
		unit = new JTextArea("*unit*");
		res = new JTextArea("*Result of your action*");
		res.setEditable(false);
		
		prodPanel.add(prodLabel);
		prodPanel.add(prodName);
		prodPanel.add(amountLabel);
		prodPanel.add(amount);
		prodPanel.add(unitLabel);
		prodPanel.add(unit);
		prodPanel.add(resLabel);
		prodPanel.add(res);
		
		//Inventory button
		invButton = new JButton("Inventory");
		invButton.addActionListener(new invActionListener());
		prodPanel.add(invButton);

		//Add button
		addButton = new JButton("Add");
		addButton.addActionListener(new addActionListener());
		prodPanel.add(addButton);

		//Remove button
		remButton = new JButton("Remove");
		prodPanel.add(remButton);
		
		prodPanel.setSize(800,20);


	}

	/**
	 * Sets up the Recipe Panel and its various components.
	 */
	private void setupRecPanel() {
		recPanel = new JPanel();
		recPanel.setLayout(new FlowLayout());
		JLabel recLabel = new JLabel("Recipes: ");
		recPanel.add(recLabel);
		
		//All recipes get a checkbox
		ArrayList<Recipe> recipes = kitchen.getRecipes();
		
		if (recipes != null) {
			Iterator<Recipe> it = recipes.iterator();

			while (it.hasNext()) {
				recPanel.add(new Checkbox(it.next().getName()));
			}
		}

	}

	class invActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("HURRA");
		}
	}

	class addActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Product tmpProd = createProduct();
			if (tmpProd == null) {
				res.setText("Not a valid amount!");
			} else {
				tmpProd = kitchen.addProduct(tmpProd);
				if (tmpProd == null) {
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

		}
	}

	class dActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

		}
	}

	class eActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

		}
	}
	
	/**
	 * Read input and try to create a product.
	 * @return Null if invalid input.
	 */
	private Product createProduct() {
		return kitchen.createProduct(prodName.getText(), amount.getText(), unit.getText());
	}

}
