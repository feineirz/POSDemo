////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------------------------------------------------------------//
//------------------------------------- THIS CLASS GENERATED BY ----------------------------------//
//------------------------------------- MySQL2JavaCLS Generator ----------------------------------//
//------------------------------------------------------------------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////
//************************************************************************************************//
//****************************** Created by feinz(feineirz@live.com) *****************************//
//************************************************************************************************//
////////////////////////////////////////////////////////////////////////////////////////////////////

package DBCLS;

import java.lang.*;
import java.sql.*;
import java.util.*;

/****************************************{{{ CLASS START }}}***************************************/
/**
 * Stock class to manage Stock in the Database.
 */
public class Stock {

/*================================================================================================*/
/*================================================================================================*/
/*========================================[ CLASS HEADER ]========================================*/
/*================================================================================================*/
/*================================================================================================*/

	
	/// PRIVATE PROPERTIES ///
	private Integer id;
	private Integer product;
	private Integer quantity;
	private String remark;
	
	/// PUBLIC PROPERTIES ///
	public final String relName = "stock";
	public final String columnNames = ""
		+ " id,"
		+ " product,"
		+ " quantity,"
		+ " remark";

	/// CLASS STRUCTURE ///
	public static class StockInfo {
		public Integer id;
		public Integer product;
		public Integer quantity;
		public String remark;
	}

/*======================================[ END CLASS HEADER ]======================================*/



/*================================================================================================*/
/*================================================================================================*/
/*=====================================[ CLASS CONSTRUCTOR ]======================================*/
/*================================================================================================*/
/*================================================================================================*/


/*------------------------------------------------------------------------------------------------*/
/*----------------------------------------[ CONSTRUCTOR ]-----------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*--------------------------- Create a Stock object from the given id. ---------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// DEFAULT CONSTRUCTOR ///
	/**
	 * Default Stock class to use a private helpful methods.
	 */
	public Stock() {}

	/// OVERLOAD CONSTRUCTOR ///
	/**
	 * 
	 * @param Stock_id A Primary key of Stock to be instances.
	 */       
	public Stock(Integer Stock_id) {		
		this(Stock_id, new MySQLDBConnector().getDBConnection(), true);		
	}
	
	/**
	 * 
	 * @param Stock_id A Primary key of Stock to be instances.
	 *
	 * @param conn MySQL Connection to be connected to the database.
	 */
	public Stock(Integer Stock_id, Connection conn) {
		this(Stock_id, conn, false);
	}
	
	/// MAIN CONSTRUCTOR ///
	/**
	 * 
	 * @param Stock_id A Primary key of Stock to be instances.
	 *
	 * @param conn MySQL Connection to be connected to the database.
	 *
	 * @param autoCloseConnection Auto close the given connection after query successful.
	 */
	public Stock(Integer Stock_id, Connection conn, boolean autoCloseConnection) {
		
		try {
			String qry = ""
				+ "SELECT *"
				+ " FROM " + relName
				+ " WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setInt(1, Stock_id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				this.id = rs.getInt("id");
				this.product = rs.getInt("product");
				this.quantity = rs.getInt("quantity");
				this.remark = rs.getString("remark");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(autoCloseConnection) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
	}

/*===================================[ END CLASS CONSTRUCTOR ]====================================*/



/*================================================================================================*/
/*================================================================================================*/
/*======================================[ CLASS PROPERTIES ]======================================*/
/*================================================================================================*/
/*================================================================================================*/


	/// GET ///
	public Integer getId() { 
		return this.id;
	}

	public Integer getProduct() { 
		return this.product;
	}

	public Integer getQuantity() { 
		return this.quantity;
	}

	public String getRemark() { 
		return this.remark;
	}


	/// SET ///
	public boolean setProduct(Integer value) {
		if (updateStockProperty("product", value)) {
			this.product = value;
			return true;
		} else { 
			return false;
		}
	}

	public boolean setQuantity(Integer value) {
		if (updateStockProperty("quantity", value)) {
			this.quantity = value;
			return true;
		} else { 
			return false;
		}
	}

	public boolean setRemark(String value) {
		if (updateStockProperty("remark", value)) {
			this.remark = value;
			return true;
		} else { 
			return false;
		}
	}


/*====================================[ END CLASS PROPERTIES ]====================================*/



/*================================================================================================*/
/*================================================================================================*/
/*=====================================[ REQUIRED FUNCTIONS ]=====================================*/
/*================================================================================================*/
/*================================================================================================*/

/*------------------------------------------------------------------------------------------------*/
/*--------------------------------------------[ LIST ]--------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*--------------------------- List Stock in database as Stock objects. ---------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// Overload ///
	/**
	 * 
	 * Default List method to list all record of Stock in the database.
	 * 
	 * @return ArrayList of Stock objects.
	 * 
	 */
	public static ArrayList<Stock> listStock(){
		return listStock("","");
	}

	/**
	 * 
	 * @param condition The condition to specify the record to be return.
	 * 
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"
	 * 
	 * @return ArrayList of Stock objects matches to the given condition.
	 * 
	 */
	public static ArrayList<Stock> listStock(String condition){
		return listStock(condition,"");
	}
	
	/// Main ///
	/**
	 * 
	 * @param condition The condition to specify the record to be return.
	 * 
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"
	 * 
	 * @param order The order to sort the returned list.
	 * 
	 *  Ex. "name desc"
	 * 
	 * @return ArrayList of Stock objects matches to the given condition.
	 * 
	 */
	public static ArrayList<Stock> listStock(String condition, String order) {
		
		ArrayList<Stock> buff = new ArrayList<Stock>();
		
		if(condition != "") condition = " WHERE " + condition;
		if(order != "") order = " ORDER BY " + order;
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "SELECT *"
				+ " FROM stock"
				+ condition
				+ order;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()) {
				buff.add(new Stock(rs.getInt("id"), conn));
			}
			
			conn.close();
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return buff;
		
	}
	

/*------------------------------------------------------------------------------------------------*/
/*-----------------------------------------[ ADD (RAW) ]------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*---------------------- Add Stock to database by giving a raw information. ----------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @return Stock object of new created Stock.
	 */
	public static Stock addStock(
		Integer id, 
		Integer product, 
		Integer quantity, 
		String remark) {
		
		StockInfo stockInfo = new StockInfo();
		stockInfo.id = id;
		stockInfo.product = product;
		stockInfo.quantity = quantity;
		stockInfo.remark = remark;
		
		return addStock(stockInfo);
		
	}
	
/*------------------------------------------------------------------------------------------------*/
/*--------------------------------------[ ADD (STRUCTURED) ]--------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------ Add Stock to database by giving a structured information. -------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @param stockInfo Stock information structure that contains all Stock's information.
	 * @return Stock object of new created Stock.
	 */
	public static Stock addStock(StockInfo stockInfo) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "INSERT INTO stock"
				+ " (id, product, quantity, remark)"
				+ " VALUES(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, stockInfo.id);
			stmt.setInt(2, stockInfo.product);
			stmt.setInt(3, stockInfo.quantity);
			stmt.setString(4, stockInfo.remark);
			
			int afr = stmt.executeUpdate();
			if (afr > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					Stock stock = new Stock(rs.getInt(1), conn);			
					conn.close();
					return stock;
				}
			}
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
		
	}
	
	
/*------------------------------------------------------------------------------------------------*/
/*----------------------------------------[ UPDATE (RAW) ]----------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*-------------- Update Stock information in database by giving a raw information. ---------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @return True if update successful.
	 */
	public static boolean updateStockInfo(
		Integer id, 
		Integer product, 
		Integer quantity, 
		String remark) {
		
		StockInfo stockInfo = new StockInfo();
		stockInfo.id = id;
		stockInfo.product = product;
		stockInfo.quantity = quantity;
		stockInfo.remark = remark;
		
		return updateStockInfo(stockInfo);
		
	}
	
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------[ UPDATE (STRUCTURED) ]-------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*----------- Update Stock information in database by giving a structured information. -----------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @param stockInfo Stock information structure contains all Stock's information.
	 *
	 * @return True if update successful.
	 */
	public static boolean updateStockInfo(StockInfo stockInfo) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE stock"
				+ " SET"
				+ " product = ?,"
				+ " quantity = ?,"
				+ " remark = ?"
				+ " WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setInt(1, stockInfo.product);
			stmt.setInt(2, stockInfo.quantity);
			stmt.setString(3, stockInfo.remark);
			stmt.setInt(4, stockInfo.id);

			
			stmt.execute();			
			conn.close();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	

/*------------------------------------------------------------------------------------------------*/
/*--------------------------------------[ UPDATE PROPERTY ]---------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*----------- Update a single property in database by the given ColumnName and Value. ------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, String value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE stock"
				+ " SET " + columnName + " = ?"
				+ " WHERE id = '" + this.id + "'";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setString(1, value);
			
			stmt.execute();			
			conn.close();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/// Overload Integer ///
	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, byte value) {
		return updateStockProperty(columnName, new Long(value));
	}
	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, Short value) {
		return updateStockProperty(columnName, new Long(value));
	}
	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, int value) {
		return updateStockProperty(columnName, new Long(value));
	}

	/// Main Integer ///
	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, long value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE stock"
				+ " SET " + columnName + " = ?"
				+ " WHERE id = " + this.id;
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setLong(1, value);
			
			stmt.execute();			
			conn.close();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/// Overload Decimal ///
	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, Float value) {
		return updateStockProperty(columnName, new Double(value));
	}
	/// Main Decimal ///
	/**
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateStockProperty(String columnName, Double value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE stock"
				+ " SET " + columnName + " = ?"
				+ " WHERE id = " + this.id;
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setDouble(1, value);
			
			stmt.execute();			
			conn.close();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	

/*------------------------------------------------------------------------------------------------*/
/*-------------------------------------------[ DELETE ]-------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*--------------------------------- Delete Stock from database. ----------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/// Private ///
	/**
	 * 
	 * @return True if deletion successful.
	 */
	private boolean deleteStock() {
		
		return deleteStock(this.id);
		
	}

	/// Static ///
	/**
	 * 
	 * @param id Stock id to be delete.
	 *
	 * @return  True if deletion successful.
	 */
	public static boolean deleteStock(Integer id) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "DELETE FROM stock"
				+ " WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setInt(1, id);
			
			stmt.execute();			
			conn.close();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	

/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------[ IsEXIST ]-------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------- Check if record(s) from the given condition is exists in a database. -------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @param condition The condition to specify the record to be return.
	 * 
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"
	 * 
	 * @return The first Stock object matches the given condition.
	 */
	public static Stock isExist(String condition) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
		if(condition != "") condition = " WHERE " + condition;
		try {
			String qry = ""
				+ "SELECT *" 
				+ " FROM stock"
				+ condition;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Stock stock = new Stock(rs.getInt("id"), conn);
				conn.close();
				return stock;
			}			
			conn.close();
			return null;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}

	}
	

/*------------------------------------------------------------------------------------------------*/
/*----------------------------------------[ ToCLASSINFO ]-----------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------- Convert Stock class to the StockInfo class. --------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @return StockInfo object.
	 */
	public StockInfo toStockInfo() {
        
		StockInfo ci = new StockInfo();
		ci.id = this.id;
		ci.product = this.product;
		ci.quantity = this.quantity;
		ci.remark = this.remark;

		return ci;

	}

/*===================================[ END REQUIRED FUNCTIONS ]===================================*/



/*================================================================================================*/
/*================================================================================================*/
/*===================================[ USER CUSTOM FUNCTIONS ]====================================*/
/*================================================================================================*/
/*================================================================================================*/


/*------------------------------------------------------------------------------------------------*/
/*-----------------------------------[ USER CUSTOM FUNCTIONS ]------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------- PLACE YOUR CUSTOM FUNCTIONS HERE -------------------------------*/
/*------------------------------------------------------------------------------------------------*/

        //////////////////////////////[ List ]//////////////////////////////
	// List Stock in database as a Stock objects. //
	////////////////////////////////////////////////////////////////////
	/// Overload ///
	public static ArrayList<Stock> listStockJoinProduct(){
		return listStockJoinProduct("","");
	}
	public static ArrayList<Stock> listStockJoinProduct(String condition){
		return listStockJoinProduct(condition,"");
	}	
	/// Main ///
	public static ArrayList<Stock> listStockJoinProduct(String condition, String order) {
		
		ArrayList<Stock> buff = new ArrayList<Stock>();
		
		if(condition != "") condition = " WHERE " + condition;
		if(order != "") order = " ORDER BY " + order;
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = "SELECT *"
					+ " FROM stock INNER JOIN product"
                                        + " ON stock.product = product.id"
					+ condition
					+ order;
			Statement stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()) {
				buff.add(new Stock(rs.getInt("id"), conn));
			}
			
			conn.close();
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return buff;
		
	} //////////////////////////////////////////////////////////////////
        
        ////////////////////////////[ Get Stock Cost ]///////////////////////////
	// Check if record(s) from the given condition is exist in a database. //
	///////////////////////////////////////////////////////////////////////
	public static Double getStockCost() {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
		try {
			String qry = "SELECT sum(s.quantity*p.cost) as stockcost" 
					+ " FROM stock s INNER JOIN product p"
                                        + " ON s.product = p.id ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Double stockCost = rs.getDouble("stockcost");
				conn.close();
				return stockCost;
			}			
			conn.close();
			return 0.0;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return 0.0;
		}

	} /////////////////////////////////////////////////////////////////////
        
        ////////////////////////////[ Get Stock Cost ]///////////////////////////
	// Check if record(s) from the given condition is exist in a database. //
	///////////////////////////////////////////////////////////////////////
	public static Double getStockProfit() {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
		try {
			String qry = "SELECT sum(s.quantity*(p.price-p.cost)) as stockprofit" 
					+ " FROM stock s INNER JOIN product p"
                                        + " ON s.product = p.id ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Double stockProfit = rs.getDouble("stockprofit");
				conn.close();
				return stockProfit;
			}			
			conn.close();
			return 0.0;
			
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return 0.0;
		}

	} /////////////////////////////////////////////////////////////////////

/*=================================[ END USER CUSTOM FUNCTIONS ]==================================*/



}


/*****************************************{{{ CLASS END }}}****************************************/

////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------------------------------------------------------------//
//-------------------------< Feel free to use but please keep the credit. >-----------------------//
//------------------------------------------------------------------------------------------------//
//////////////////////////// 01100110 01100101 01101001 01101110 01111010 //////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////