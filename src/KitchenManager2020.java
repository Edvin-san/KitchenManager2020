import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * THIS IS THE GUI
 * @author edvinlun
 *
 */
public class KitchenManager2020 {
	
	private Kitchen kitchen;
	
	
	public KitchenManager2020() {
		this.kitchen = new Kitchen();
		createAndRunGUI();
	}
	
	public void createAndRunGUI() {
		// Skapa frame
		JFrame frame = new JFrame("KitchenManager2020");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(800, 600);
		
		
		JPanel panel = new JPanel();
		frame.add(panel);
		//
		
		

		
	}
	
}
