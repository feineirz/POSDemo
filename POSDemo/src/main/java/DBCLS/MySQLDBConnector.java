package DBCLS;

import java.sql.*;

/**
 * MySQL connector class to provide a MySQL database connection.
 */

public class MySQLDBConnector {
	
	public Connection conn = null;

	/**
	 * <pre>
	 * Default Class constructor.
	 * Usage: MySQLDBConnector mysqlDBConnector = new MySQLDBConnector();
	 * </pre>
	 */
	public MySQLDBConnector() {}
	
	/**
	 * <pre>
	 * Get MySQL connection instance.
	 * Usage: Connection conn = MySQLDBConnector.getDBConnection();
	 * </pre>
	 * 
	 * @return MySQL connection.
	 */
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
			e.printStackTrace();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return conn;
	}

}