package DBCLS;

import java.sql.*;
import javax.swing.JOptionPane;

public class MySQLDBConnector {
	
	public Connection conn = null;
	
	public MySQLDBConnector() {}
	
	public Connection getDBConnection() {
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/posdemo"
					+ "?useJDBCCompliantTimezoneShift=true"
					+ "&useLegacyDatetimeCode=false"
					+ "&useSSL=false"
					+ "&autoReconnect=true"
					+ "&useUnicode=true"
					+ "&characterEncoding=utf-8"
					+ "&serverTimezone=UTC",
					"dbadmin",
					"fakepassword" );

		}catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return conn;
	}

}