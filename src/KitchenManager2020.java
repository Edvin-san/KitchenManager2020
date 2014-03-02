import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	

	private JPanel recPanel;

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
		prodPanel.setLayout(new BoxLayout(prodPanel, BoxLayout.X_AXIS));

		//Inventory button
		invButton = new JButton("Inventory");
		invButton.addActionListener(new invActionListener());
		prodPanel.add(invButton);

		//Add button
		addButton = new JButton("Add");
		prodPanel.add(addButton);

		//Remove button
		remButton = new JButton("Remove");
		prodPanel.add(remButton);


	}

	/**
	 * Sets up the Recipe Panel and its various components.
	 */
	private void setupRecPanel() {
		recPanel = new JPanel();

	}

	class invActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("HURRA");
		}
	}

	class bActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

		}
	}

	class cActionListener implements ActionListener {
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

}
