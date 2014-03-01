import java.sql.* ;  // for standard JDBC programs

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Middleman mellan gui och inventory
 * @author Edvin Lundberg
 *
 */
public class Kitchen {
	
	private Inventory inventory;
	
	public Kitchen(Connection inConn) throws SQLException {
		this.inventory = new Inventory(inConn);
	}

	
}
