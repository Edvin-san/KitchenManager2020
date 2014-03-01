import java.sql.* ;  // for standard JDBC programs

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * GUI typ
 * @author Edvin Lundberg
 *
 */
public class Kitchen {
	
	private Connection conn = null;
	private Inventory inventory;
	
	public Kitchen(Connection inConn) throws SQLException {
		this.conn = inConn;
		this.inventory = new Inventory(inConn);
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
