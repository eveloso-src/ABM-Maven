package abmPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminBD {

	public static Connection obtenerConexion() {
		
		Connection con = null; 
		
		  
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/ada","root","");
			} 
			
			catch (ClassNotFoundException |SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		
	
	return con;
	

}
}