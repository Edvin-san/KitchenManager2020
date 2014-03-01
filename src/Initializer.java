import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is just the initializer
 * @author edvinlun
 *
 */
public class Initializer {

	public static void main(String [] args) {


		try {
			Class.forName("org.postgresql.Driver");

			String URL = "jdbc:postgresql://nestor2.csc.kth.se:5432/edvinlun";
			String USER = "edvinlun";
			String PASS = "rUGf2QEB";
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			new Kitchen(conn);
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}


	}
	
}
