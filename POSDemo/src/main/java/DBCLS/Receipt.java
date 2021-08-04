////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------------------------------------------------------------//
//----------------------------------- THIS CLASS GENERATED BY ------------------------------------//
//----------------------------------- MySQL2JavaCLS Generator ------------------------------------//
//------------------------------------------------------------------------------------------------//
//------------------------------------- < version 1.1.0.5 > --------------------------------------//
//------------------------------------------------------------------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////
//************************************************************************************************//
//****************************** Created by feinz(feineirz@live.com) *****************************//
//************************************************************************************************//
////////////////////////////////////////////////////////////////////////////////////////////////////


package DBCLS;

import java.lang.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;


/*--------------------------------------{{{ CLASS START }}}---------------------------------------*/

/**
 * Receipt class to manage Receipt in a Database.
 */
public class Receipt {

/*================================================================================================*/
/*================================================================================================*/
/*========================================[ CLASS HEADER ]========================================*/
/*================================================================================================*/
/*================================================================================================*/

	
	/// PRIVATE PROPERTIES ///
	private Integer id;
	private String receiptDate;
	private Double cost;
	private Double total;
	private Double cash;
	private Double exchange;
	private String cashier;
	private String remark;
	
	/// PUBLIC PROPERTIES ///
	public final String relName = "receipt";
	public final String columnNames = ""
		+ " id,"
		+ " receipt_date,"
		+ " cost,"
		+ " total,"
		+ " cash,"
		+ " exchange,"
		+ " cashier,"
		+ " remark";

	/// CLASS STRUCTURE ///
	/**
	 * <pre>
	 * Receipt structure class to collect or prepare Receipt information.
	 *
	 * Usage:
	 *  ReceiptInfo receiptInfo = new ReceiptInfo();
	 * </pre>
	 */
	public static class ReceiptInfo {
		public Integer id;
		public String receiptDate;
		public Double cost;
		public Double total;
		public Double cash;
		public Double exchange;
		public String cashier;
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
/*-------------------------- Create a Receipt object from the given id. --------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// DEFAULT CONSTRUCTOR ///
	/**
	 * <pre>
	 * Default constructor for Receipt class to use a private helpful methods.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt();
	 * </pre>
	 */
	public Receipt() {}

	/// OVERLOAD CONSTRUCTOR ///
	/**
	 * <pre>
	 * Class constructor for Receipt class by giving a Primary key.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt(id);
	 * </pre>
	 *
	 * @param id A Primary key of Receipt to be instances.
	 */       
	public Receipt(Integer id) {		
		this(id, new MySQLDBConnector().getDBConnection(), true);		
	}
	
	/**
	 * <pre>
	 * Class constructor for Receipt class by giving a Primary key and a MySQL connector.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt(id, conn);
	 * </pre>
	 *
	 * @param id A Primary key of Receipt to be instances.
	 * @param conn MySQL Connection use to connect to the database.
	 */
	public Receipt(Integer id, Connection conn) {
		this(id, conn, false);
	}
	
	/// MAIN CONSTRUCTOR ///
	/**
	 * <pre>
	 * Class constructor for Receipt class by giving a Primary key, MySQL connector
	 *  and Auto close connection option.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt(id, conn, true);
	 * </pre>
	 * 
	 * @param id A Primary key of Receipt to be instances.
	 * @param conn MySQL Connection to be connect to the database.
	 * @param autoCloseConnection Auto close the given connection after query successful.
	 */
	public Receipt(Integer id, Connection conn, boolean autoCloseConnection) {
		
		try {
			String qry = ""
				+ "SELECT *"
				+ " FROM " + relName
				+ " WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				this.id = rs.getInt("id");
				this.receiptDate = rs.getString("receipt_date");
				this.cost = rs.getDouble("cost");
				this.total = rs.getDouble("total");
				this.cash = rs.getDouble("cash");
				this.exchange = rs.getDouble("exchange");
				this.cashier = rs.getString("cashier");
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

	/**
	 * <pre>
	 * Get Receipt id from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  Integer id = receipt.getId();
	 * </pre>
	 * 
	 * @return Receipt id.
	 */
	public Integer getId() { 
		return this.id;
	}


	/**
	 * <pre>
	 * Get Receipt receiptDate from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  String receiptDate = receipt.getReceiptDate();
	 * </pre>
	 * 
	 * @return Receipt receiptDate.
	 */
	public String getReceiptDate() { 
		return this.receiptDate;
	}


	/**
	 * <pre>
	 * Get Receipt cost from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  Double cost = receipt.getCost();
	 * </pre>
	 * 
	 * @return Receipt cost.
	 */
	public Double getCost() { 
		return this.cost;
	}


	/**
	 * <pre>
	 * Get Receipt total from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  Double total = receipt.getTotal();
	 * </pre>
	 * 
	 * @return Receipt total.
	 */
	public Double getTotal() { 
		return this.total;
	}


	/**
	 * <pre>
	 * Get Receipt cash from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  Double cash = receipt.getCash();
	 * </pre>
	 * 
	 * @return Receipt cash.
	 */
	public Double getCash() { 
		return this.cash;
	}


	/**
	 * <pre>
	 * Get Receipt exchange from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  Double exchange = receipt.getExchange();
	 * </pre>
	 * 
	 * @return Receipt exchange.
	 */
	public Double getExchange() { 
		return this.exchange;
	}


	/**
	 * <pre>
	 * Get Receipt cashier from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  String cashier = receipt.getCashier();
	 * </pre>
	 * 
	 * @return Receipt cashier.
	 */
	public String getCashier() { 
		return this.cashier;
	}


	/**
	 * <pre>
	 * Get Receipt remark from a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  String remark = receipt.getRemark();
	 * </pre>
	 * 
	 * @return Receipt remark.
	 */
	public String getRemark() { 
		return this.remark;
	}


	/// SET ///

	/**
	 * <pre>
	 * Update Receipt receiptDate to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setReceiptDate(value);
	 * </pre>
     * 
     * @param value Receipt receiptDate to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setReceiptDate(String value) {
		if (updateReceiptProperty("receipt_date", value)) {
			this.receiptDate = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Receipt cost to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setCost(value);
	 * </pre>
     * 
     * @param value Receipt cost to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setCost(Double value) {
		if (updateReceiptProperty("cost", value)) {
			this.cost = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Receipt total to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setTotal(value);
	 * </pre>
     * 
     * @param value Receipt total to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setTotal(Double value) {
		if (updateReceiptProperty("total", value)) {
			this.total = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Receipt cash to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setCash(value);
	 * </pre>
     * 
     * @param value Receipt cash to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setCash(Double value) {
		if (updateReceiptProperty("cash", value)) {
			this.cash = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Receipt exchange to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setExchange(value);
	 * </pre>
     * 
     * @param value Receipt exchange to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setExchange(Double value) {
		if (updateReceiptProperty("exchange", value)) {
			this.exchange = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Receipt cashier to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setCashier(value);
	 * </pre>
     * 
     * @param value Receipt cashier to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setCashier(String value) {
		if (updateReceiptProperty("cashier", value)) {
			this.cashier = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Receipt remark to a database.
	 * 
	 * Usage:
	 *  Receipt receipt = New Receipt(id);
	 *  boolean result = receipt.setRemark(value);
	 * </pre>
     * 
     * @param value Receipt remark to update to the database.
     * 
     * @return True if successful.
     */
	public boolean setRemark(String value) {
		if (updateReceiptProperty("remark", value)) {
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
/*------------------------ List Receipt in a database as Receipt objects. ------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// Overload ///
	/**
	 * <pre>
	 * Default List method to list all records of Receipt in a database.
	 *
	 * Usage:
	 *  ArrayList&lt;Receipt&gt; receiptList = listReceipt();
	 * </pre>
	 * 
	 * @return ArrayList of Receipt objects.
	 * 
	 */
	public static ArrayList<Receipt> listReceipt(){
		return listReceipt("","");
	}

	/**
	 * <pre>
	 * List record(s) of Receipt in a database matches the given condition.
	 *
	 * Usage:
	 *  ArrayList&lt;Receipt&gt; receiptList = listReceipt(condition);
	 * </pre>
	 * 
	 * @param condition The condition to specify the record to be return.<pre>
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"</pre>
	 * 
	 * @return ArrayList of Receipt objects match the given condition.
	 * 
	 */
	public static ArrayList<Receipt> listReceipt(String condition){
		return listReceipt(condition,"");
	}
	
	/// Main ///
	/**
	 * <pre>
	 * List record(s) of Receipt in a database matches the given condition
	 *  and sorted by the given order.
	 *
	 * Usage:
	 *  ArrayList&lt;Receipt&gt; receiptList = listReceipt(condition, order);
	 * </pre>
	 * 
	 * @param condition The condition to specify the record to be return.<pre>
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"</pre>
	 * 
	 * @param order The order to sort the returned list.<pre>
	 *  Ex. "name desc"</pre>
	 * 
	 * @return ArrayList of Receipt objects match the given condition.
	 * 
	 */
	public static ArrayList<Receipt> listReceipt(String condition, String order) {
		
		ArrayList<Receipt> buff = new ArrayList<Receipt>();
		
		if(condition != "") condition = " WHERE " + condition;
		if(order != "") order = " ORDER BY " + order;
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "SELECT *"
				+ " FROM receipt"
				+ condition
				+ order;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()) {
				buff.add(new Receipt(rs.getInt("id"), conn));
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
/*--------------------- Add Receipt to database by giving a raw information. ---------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Add Receipt to database by giving a raw information.
	 *
	 * Usage:
	 *  Receipt receipt = addReceipt(...);
	 * </pre>
	 * 
	 * @return Receipt object of new created Receipt.
	 */
	public static Receipt addReceipt(
		Integer id, 
		String receipt_date, 
		Double cost, 
		Double total, 
		Double cash, 
		Double exchange, 
		String cashier, 
		String remark) {
		
		ReceiptInfo receiptInfo = new ReceiptInfo();
		receiptInfo.id = id;
		receiptInfo.receiptDate = receipt_date;
		receiptInfo.cost = cost;
		receiptInfo.total = total;
		receiptInfo.cash = cash;
		receiptInfo.exchange = exchange;
		receiptInfo.cashier = cashier;
		receiptInfo.remark = remark;
		
		return addReceipt(receiptInfo);
		
	}
	
/*------------------------------------------------------------------------------------------------*/
/*--------------------------------------[ ADD (STRUCTURED) ]--------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*----------------- Add Receipt to database by giving a structured information. ------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Add Receipt to database by giving a structured information.
	 *
	 * Usage:
	 *  ReceiptInfo receiptInfo = new ReceiptInfo();
	 *  receiptInfo.id = id;
	 *  ...
	 *
	 *  Receipt receipt = addReceipt(receiptInfo);
	 * </pre>
	 * 
	 * @param receiptInfo Receipt information structure that contains all Receipt's information.
	 *
	 * @return Receipt object of new created Receipt.
	 */
	public static Receipt addReceipt(ReceiptInfo receiptInfo) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "INSERT INTO receipt"
				+ " (id, receipt_date, cost, total, cash, exchange, cashier, remark)"
				+ " VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, receiptInfo.id);
			stmt.setString(2, receiptInfo.receiptDate);
			stmt.setDouble(3, receiptInfo.cost);
			stmt.setDouble(4, receiptInfo.total);
			stmt.setDouble(5, receiptInfo.cash);
			stmt.setDouble(6, receiptInfo.exchange);
			stmt.setString(7, receiptInfo.cashier);
			stmt.setString(8, receiptInfo.remark);
			
			int afr = stmt.executeUpdate();
			if (afr > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					Receipt receipt = new Receipt(rs.getInt(1), conn);			
					conn.close();
					return receipt;
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
/*------------ Update Receipt information in a database by giving a raw information. -------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Update Receipt information in a database by giving a raw information.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptInfo(...);
	 * </pre>
	 * 
	 * @return True if update successful.
	 */
	public static boolean updateReceiptInfo(
		Integer id, 
		String receipt_date, 
		Double cost, 
		Double total, 
		Double cash, 
		Double exchange, 
		String cashier, 
		String remark) {
		
		ReceiptInfo receiptInfo = new ReceiptInfo();
		receiptInfo.id = id;
		receiptInfo.receiptDate = receipt_date;
		receiptInfo.cost = cost;
		receiptInfo.total = total;
		receiptInfo.cash = cash;
		receiptInfo.exchange = exchange;
		receiptInfo.cashier = cashier;
		receiptInfo.remark = remark;
		
		return updateReceiptInfo(receiptInfo);
		
	}
	
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------[ UPDATE (STRUCTURED) ]-------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*--------- Update Receipt information in a database by giving a structured information. ---------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Update Receipt information in a database by giving a structured information.
	 *
	 * Usage:
	 *  ReceiptInfo receiptInfo = new ReceiptInfo();
	 *  receiptInfo.id = id;
	 *  ...
	 *
	 *  boolean result = Receipt.updateReceiptInfo(receiptInfo);
	 * </pre>
	 * 
	 * @param receiptInfo Receipt information structure contains all Receipt's information.
	 *
	 * @return True if update successful.
	 */
	public static boolean updateReceiptInfo(ReceiptInfo receiptInfo) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE receipt"
				+ " SET"
				+ " receipt_date = ?,"
				+ " cost = ?,"
				+ " total = ?,"
				+ " cash = ?,"
				+ " exchange = ?,"
				+ " cashier = ?,"
				+ " remark = ?"
				+ " WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setString(1, receiptInfo.receiptDate);
			stmt.setDouble(2, receiptInfo.cost);
			stmt.setDouble(3, receiptInfo.total);
			stmt.setDouble(4, receiptInfo.cash);
			stmt.setDouble(5, receiptInfo.exchange);
			stmt.setString(6, receiptInfo.cashier);
			stmt.setString(7, receiptInfo.remark);
			stmt.setInt(8, receiptInfo.id);

			
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
/*---------- Update a single property in a database by the given ColumnName and Value. -----------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, String value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE receipt"
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
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, byte value) {
		return updateReceiptProperty(columnName, new Long(value));
	}

	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, Short value) {
		return updateReceiptProperty(columnName, new Long(value));
	}

	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, int value) {
		return updateReceiptProperty(columnName, new Long(value));
	}

	/// Main Integer ///
	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, long value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE receipt"
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
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, Float value) {
		return updateReceiptProperty(columnName, new Double(value));
	}

	/// Main Decimal ///
	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Receipt.updateReceiptProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateReceiptProperty(String columnName, Double value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE receipt"
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
/*------------------------------- Delete Receipt from a database. --------------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// Local ///
	/**
	 * <pre>
	 * Delete Receipt from a database.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt(id);
	 *  ...
	 *  
	 *  boolean result = receipt.deleteReceipt();
	 * </pre>
	 * 
	 * @return True if deletion successful.
	 */
	public boolean deleteReceipt() {
		
		return deleteReceipt(this.id);
		
	}

	/// Static ///
	/**
	 * <pre>
	 * Delete Receipt from a database.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt();
	 *  ...
	 *  
	 *  boolean result = receipt.deleteReceipt(id);
	 * </pre>
	 * 
	 * @param id Receipt id to be delete.
	 *
	 * @return  True if deletion successful.
	 */
	public static boolean deleteReceipt(Integer id) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "DELETE FROM receipt"
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
	 * <pre>
	 * Check if record(s) from the given condition is exists in a database.
	 *
	 * Usage:
	 *  Receipt receipt = Receipt.isExist(condition);
	 * </pre>
	 * 
	 * @param condition The condition to specify the record to be return.<pre>
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"</pre>
	 * 
	 * @return The first Receipt object matches the given condition.
	 */
	public static Receipt isExist(String condition) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
		if(condition != "") condition = " WHERE " + condition;
		try {
			String qry = ""
				+ "SELECT *" 
				+ " FROM receipt"
				+ condition;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Receipt receipt = new Receipt(rs.getInt("id"), conn);
				conn.close();
				return receipt;
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
/*------------------------ Convert Receipt class to a ReceiptInfo class. -------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Convert Receipt class to a ReceiptInfo class.
	 *
	 * Usage:
	 *  Receipt receipt = new Receipt();
	 *  ...
	 *  
	 *  ReceiptInfo receiptInfo = receipt.toReceiptInfo();
	 * </pre>
	 *
	 * @return ReceiptInfo object.
	 */
	public ReceiptInfo toReceiptInfo() {
        
		ReceiptInfo ci = new ReceiptInfo();
		ci.id = this.id;
		ci.receiptDate = this.receiptDate;
		ci.cost = this.cost;
		ci.total = this.total;
		ci.cash = this.cash;
		ci.exchange = this.exchange;
		ci.cashier = this.cashier;
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

    
	public static Integer getNextID() {
            
        Connection conn = new MySQLDBConnector().getDBConnection();

        try {
            String qry = ""
                + "SELECT auto_increment as id"
                + " FROM information_schema.TABLES"
                + " WHERE TABLE_SCHEMA = 'posdemo'"
                + " AND TABLE_NAME = 'receipt'";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(qry);
            while(rs.next()) {
                return rs.getInt("id");
            }
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
		
	}
        

	public static Double getSumCost(String condition) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
        if(condition != "") condition = " WHERE " + condition;
		try {
			String qry = ""
                + "SELECT sum(cost) as sumcost" 
                + " FROM receipt"
                                        + condition;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Double sumCost = rs.getDouble("sumcost");
				conn.close();
				return sumCost; 
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

	}
        

	public static Double getSumTotal(String condition) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
        if(condition != "") condition = " WHERE " + condition;
		try {
			String qry = ""
                + "SELECT sum(total) as sumtotal" 
                + " FROM receipt"
                                        + condition;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Double sumTotal = rs.getDouble("sumtotal");
				conn.close();
				return sumTotal; 
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

	}

        
    public static class MonthlyIncomeInfo {
        public Integer date_id;
        public String receiptDate;
        public Double cost;
        public Double income;
        public Double profit;
    }
        
    
	public static ArrayList<MonthlyIncomeInfo> getMonthlyIncome(int year, int month) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
                
        DecimalFormat df = new DecimalFormat("00");
        String dfMonth = df.format(month);

        ArrayList<MonthlyIncomeInfo> buff = new ArrayList<>();
        MonthlyIncomeInfo mii;
		
		try {
			String qry = ""
                + "SELECT DATE_FORMAT(receipt_date ,'%d') as date_id, "
                + "DATE_FORMAT(receipt_date ,'%Y-%m-%d') as receipt_date, "
                + "sum(cost) as cost, sum(total) as income, (sum(total)-sum(cost)) as profit" 
                + " FROM receipt"
                + " WHERE receipt_date LIKE '"+year+"-"+dfMonth+"-%'"
                + " GROUP BY YEAR(receipt_date), MONTH(receipt_date), DAY(receipt_date)";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
                mii = new MonthlyIncomeInfo();
                mii.date_id = rs.getInt("date_id");
                mii.receiptDate = rs.getString("receipt_date");
				mii.cost = rs.getDouble("cost");
                mii.income = rs.getDouble("income");
                mii.profit = rs.getDouble("profit");
                buff.add(mii);
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

/*=================================[ END USER CUSTOM FUNCTIONS ]==================================*/



}


/*--------------------------------------{{{ CLASS END }}}---------------------------------------*/

////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------------------------------------------------------------//
//------------------------|| Feel free to use but please keep the credit. ||----------------------//
//------------------------------------------------------------------------------------------------//
//------------------------|| 01100110 01100101 01101001 01101110 01111010 ||----------------------//
//------------------------------------------------------------------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////