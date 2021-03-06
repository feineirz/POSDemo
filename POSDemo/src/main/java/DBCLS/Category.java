////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------------------------------------------------------------//
//----------------------------------- THIS CLASS GENERATED BY ------------------------------------//
//----------------------------------- MySQL2JavaCLS Generator ------------------------------------//
//------------------------------------------------------------------------------------------------//
//------------------------------------- < version 1.1.0.6 > --------------------------------------//
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


/*--------------------------------------{{{ CLASS START }}}---------------------------------------*/

/**
 * Category class to manage Category in a Database.
 */
public class Category {

/*================================================================================================*/
/*================================================================================================*/
/*========================================[ CLASS HEADER ]========================================*/
/*================================================================================================*/
/*================================================================================================*/

	
	/// PRIVATE PROPERTIES ///
	private Integer id;
	private String name;
	private String description;
	
	/// PUBLIC PROPERTIES ///
	public final String relName = "category";
	public final String columnNames = ""
		+ " id,"
		+ " name,"
		+ " description";

	/// CLASS STRUCTURE ///
	/**
	 * <pre>
	 * Category structure class to collect or prepare Category information.
	 *
	 * Usage:
	 *  CategoryInfo categoryInfo = new CategoryInfo();
	 * </pre>
	 */
	public static class CategoryInfo {
		public Integer id;
		public String name;
		public String description;
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
/*------------------------- Create a Category object from the given id. --------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// DEFAULT CONSTRUCTOR ///
	/**
	 * <pre>
	 * Default constructor for Category class to use a private helpful methods.
	 *
	 * Usage:
	 *  Category category = new Category();
	 * </pre>
	 */
	public Category() {}

	/// OVERLOAD CONSTRUCTOR ///
	/**
	 * <pre>
	 * Class constructor for Category class by giving a Primary key.
	 *
	 * Usage:
	 *  Category category = new Category(id);
	 * </pre>
	 *
	 * @param id A Primary key of Category to be instances.
	 */       
	public Category(Integer id) {		
		this(id, new MySQLDBConnector().getDBConnection(), true);		
	}
	
	/**
	 * <pre>
	 * Class constructor for Category class by giving a Primary key and a MySQL connector.
	 *
	 * Usage:
	 *  Category category = new Category(id, conn);
	 * </pre>
	 *
	 * @param id A Primary key of Category to be instances.
	 * @param conn MySQL Connection use to connect to the database.
	 */
	public Category(Integer id, Connection conn) {
		this(id, conn, false);
	}
	
	/// MAIN CONSTRUCTOR ///
	/**
	 * <pre>
	 * Class constructor for Category class by giving a Primary key, MySQL connector
	 *  and Auto close connection option.
	 *
	 * Usage:
	 *  Category category = new Category(id, conn, true);
	 * </pre>
	 * 
	 * @param id A Primary key of Category to be instances.
	 * @param conn MySQL Connection to be connect to the database.
	 * @param autoCloseConnection Auto close the given connection after query successful.
	 */
	public Category(Integer id, Connection conn, boolean autoCloseConnection) {
		
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
				this.name = rs.getString("name");
				this.description = rs.getString("description");
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
	 * Get Category.id from a database.
	 * 
	 * Usage:
	 *  Category category = New Category(id);
	 *  Integer id = category.getId();
	 * </pre>
	 * 
	 * @return Category id.
	 */
	public Integer getId() { 
		return this.id;
	}


	/**
	 * <pre>
	 * Get Category.name from a database.
	 * 
	 * Usage:
	 *  Category category = New Category(id);
	 *  String name = category.getName();
	 * </pre>
	 * 
	 * @return Category name.
	 */
	public String getName() { 
		return this.name;
	}


	/**
	 * <pre>
	 * Get Category.description from a database.
	 * 
	 * Usage:
	 *  Category category = New Category(id);
	 *  String description = category.getDescription();
	 * </pre>
	 * 
	 * @return Category description.
	 */
	public String getDescription() { 
		return this.description;
	}


	/// SET ///

	/**
	 * <pre>
	 * Update Category.name in a database.
	 * 
	 * Usage:
	 *  Category category = New Category(id);
	 *  boolean result = category.setName(value);
	 * </pre>
	 * 
	 * @param value Category name to update to the database.
	 * 
	 * @return True if update successful.
	 */
	public boolean setName(String value) {
		if (updateCategoryProperty("name", value)) {
			this.name = value;
			return true;
		} else { 
			return false;
		}
	}


	/**
	 * <pre>
	 * Update Category.description in a database.
	 * 
	 * Usage:
	 *  Category category = New Category(id);
	 *  boolean result = category.setDescription(value);
	 * </pre>
	 * 
	 * @param value Category description to update to the database.
	 * 
	 * @return True if update successful.
	 */
	public boolean setDescription(String value) {
		if (updateCategoryProperty("description", value)) {
			this.description = value;
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
/*----------------------- List Category in a database as Category objects. -----------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// Overload ///
	/**
	 * <pre>
	 * Default List method to list all records of Category in a database.
	 *
	 * Usage:
	 *  ArrayList&lt;Category&gt; categoryList = listCategory();
	 * </pre>
	 * 
	 * @return ArrayList of Category objects.
	 * 
	 */
	public static ArrayList<Category> listCategory(){
		return listCategory("","");
	}

	/**
	 * <pre>
	 * List record(s) of Category in a database match the given condition.
	 *
	 * Usage:
	 *  ArrayList&lt;Category&gt; categoryList = listCategory(condition);
	 * </pre>
	 * 
	 * @param condition The condition to specify the record to be return.<pre>
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"</pre>
	 * 
	 * @return ArrayList of Category objects match the given condition.
	 * 
	 */
	public static ArrayList<Category> listCategory(String condition){
		return listCategory(condition,"");
	}
	
	/// Main ///
	/**
	 * <pre>
	 * List record(s) of Category in a database match the given condition
	 *  and sorted by the given order.
	 *
	 * Usage:
	 *  ArrayList&lt;Category&gt; categoryList = listCategory(condition, order);
	 * </pre>
	 * 
	 * @param condition The condition to specify the record to be return.<pre>
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"</pre>
	 * 
	 * @param order The order to sort the returned list.<pre>
	 *  Ex. "name desc"</pre>
	 * 
	 * @return ArrayList of Category objects match the given condition.
	 * 
	 */
	public static ArrayList<Category> listCategory(String condition, String order) {
		
		ArrayList<Category> buff = new ArrayList<Category>();
		
		if(condition != "") condition = " WHERE " + condition;
		if(order != "") order = " ORDER BY " + order;
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "SELECT *"
				+ " FROM category"
				+ condition
				+ order;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()) {
				buff.add(new Category(rs.getInt("id"), conn));
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
/*-------------------- Add Category to database by giving a raw information. ---------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Add Category to database by giving a raw information.
	 *
	 * Usage:
	 *  Category category = addCategory(...);
	 * </pre>
	 * 
	 * @return Category object of new created Category.
	 */
	public static Category addCategory(
		Integer id, 
		String name, 
		String description) {
		
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.id = id;
		categoryInfo.name = name;
		categoryInfo.description = description;
		
		return addCategory(categoryInfo);
		
	}
	
/*------------------------------------------------------------------------------------------------*/
/*--------------------------------------[ ADD (STRUCTURED) ]--------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*----------------- Add Category to database by giving a structured information. -----------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Add Category to database by giving a structured information.
	 *
	 * Usage:
	 *  CategoryInfo categoryInfo = new CategoryInfo();
	 *  categoryInfo.id = id;
	 *  ...
	 *
	 *  Category category = addCategory(categoryInfo);
	 * </pre>
	 * 
	 * @param categoryInfo Category information structure that contains all Category's information.
	 *
	 * @return Category object of new created Category.
	 */
	public static Category addCategory(CategoryInfo categoryInfo) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "INSERT INTO category"
				+ " (id, name, description)"
				+ " VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, categoryInfo.id);
			stmt.setString(2, categoryInfo.name);
			stmt.setString(3, categoryInfo.description);
			
			int afr = stmt.executeUpdate();
			if (afr > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					Category category = new Category(rs.getInt(1), conn);			
					conn.close();
					return category;
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
/*------------ Update Category information in a database by giving a raw information. ------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Update Category information in a database by giving a raw information.
	 *
	 * Usage:
	 *  boolean result = Category.updateCategoryInfo(...);
	 * </pre>
	 * 
	 * @return True if update successful.
	 */
	public static boolean updateCategoryInfo(
		Integer id, 
		String name, 
		String description) {
		
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.id = id;
		categoryInfo.name = name;
		categoryInfo.description = description;
		
		return updateCategoryInfo(categoryInfo);
		
	}
	
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------[ UPDATE (STRUCTURED) ]-------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*-------- Update Category information in a database by giving a structured information. ---------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Update Category information in a database by giving a structured information.
	 *
	 * Usage:
	 *  CategoryInfo categoryInfo = new CategoryInfo();
	 *  categoryInfo.id = id;
	 *  ...
	 *
	 *  boolean result = Category.updateCategoryInfo(categoryInfo);
	 * </pre>
	 * 
	 * @param categoryInfo Category information structure contains all Category's information.
	 *
	 * @return True if update successful.
	 */
	public static boolean updateCategoryInfo(CategoryInfo categoryInfo) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE category"
				+ " SET"
				+ " name = ?,"
				+ " description = ?"
				+ " WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(qry);
			stmt.setString(1, categoryInfo.name);
			stmt.setString(2, categoryInfo.description);
			stmt.setInt(3, categoryInfo.id);

			
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
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, String value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE category"
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
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, byte value) {
		return updateCategoryProperty(columnName, new Long(value));
	}

	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, Short value) {
		return updateCategoryProperty(columnName, new Long(value));
	}

	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, int value) {
		return updateCategoryProperty(columnName, new Long(value));
	}

	/// Main Integer ///
	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, long value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE category"
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
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, Float value) {
		return updateCategoryProperty(columnName, new Double(value));
	}

	/// Main Decimal ///
	/**
	 * <pre>
	 * Update a single property in a database by the given ColumnName and Value.
	 *
	 * Usage:
	 *  boolean result = Category.updateCategoryProperty(columnName, value);
	 * </pre>
	 * 
	 * @param columnName Column name in database's table to be update.
	 * @param value The value to be update to the given column name.
	 * 
	 * @return True if update successful.
	 */
	public boolean updateCategoryProperty(String columnName, Double value) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "UPDATE category"
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
/*------------------------------- Delete Category from a database. -------------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/// Local ///
	/**
	 * <pre>
	 * Delete Category from a database.
	 *
	 * Usage:
	 *  Category category = new Category(id);
	 *  ...
	 *  
	 *  boolean result = category.deleteCategory();
	 * </pre>
	 * 
	 * @return True if deletion successful.
	 */
	public boolean deleteCategory() {
		
		return deleteCategory(this.id);
		
	}

	/// Static ///
	/**
	 * <pre>
	 * Delete Category from a database.
	 *
	 * Usage:
	 *  Category category = new Category();
	 *  ...
	 *  
	 *  boolean result = category.deleteCategory(id);
	 * </pre>
	 * 
	 * @param id Category id to be delete.
	 *
	 * @return  True if deletion successful.
	 */
	public static boolean deleteCategory(Integer id) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		try {
			String qry = ""
				+ "DELETE FROM category"
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
	 *  Category category = Category.isExist(condition);
	 * </pre>
	 * 
	 * @param condition The condition to specify the record to be return.<pre>
	 *  Ex. "name = 'Foo' AND date BETWEEN '2021-01-01' AND '2021-12-31'"</pre>
	 * 
	 * @return The first Category object matches the given condition.
	 */
	public static Category isExist(String condition) {
		
		Connection conn = new MySQLDBConnector().getDBConnection();
		
		if(condition != "") condition = " WHERE " + condition;
		try {
			String qry = ""
				+ "SELECT *" 
				+ " FROM category"
				+ condition;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Category category = new Category(rs.getInt("id"), conn);
				conn.close();
				return category;
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
/*----------------------- Convert Category class to a CategoryInfo class. ------------------------*/
/*------------------------------------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Convert Category class to a CategoryInfo class.
	 *
	 * Usage:
	 *  Category category = new Category();
	 *  ...
	 *  
	 *  CategoryInfo categoryInfo = category.toCategoryInfo();
	 * </pre>
	 *
	 * @return CategoryInfo object.
	 */
	public CategoryInfo toCategoryInfo() {
        
		CategoryInfo ci = new CategoryInfo();
		ci.id = this.id;
		ci.name = this.name;
		ci.description = this.description;

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


    public static Category NameToCategory(String categoryName) {

        Connection conn = new MySQLDBConnector().getDBConnection();
        Category category = null;
        try {
            String qry = "SELECT * FROM category WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(qry);
            stmt.setString(1, categoryName);

            ResultSet rs = stmt.executeQuery();            
            while (rs.next()) {
                category = new Category(rs.getInt("id"));                                    
                return category;
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
        return category;

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
