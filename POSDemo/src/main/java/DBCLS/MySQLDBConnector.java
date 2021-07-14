package DBCLS;

import java.sql.*;

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
			e.printStackTrace();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return conn;
	}

}