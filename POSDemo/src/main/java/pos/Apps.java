/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

//import initforms.Dashboard;
import forms.*;

/**
 *
 * @author feinz
 */
public class Apps {
    
    public static MainFrame mainFrame;
    public static LoginForm loginForm = new LoginForm();
    
    public static Dashboard dashboardForm = new Dashboard();
    
    public static NewUserForm newUserForm = new NewUserForm();
    public static ManageUserForm manageUserForm = new ManageUserForm();
    
    public static NewCategoryForm newCategoryForm = new NewCategoryForm();
    public static ManageCategoryForm manageCategoryForm = new ManageCategoryForm();
    
    public static NewProductForm newProductForm = new NewProductForm();
    public static ManageProductForm manageProductForm = new ManageProductForm();
    
    public static NewStockForm newStockForm = new NewStockForm();
    public static ManageStockForm manageStockForm = new ManageStockForm();
    
    public static POSForm posForm = new POSForm();
    public static ManageDailyReceiptForm manageDailyReceiptForm = new ManageDailyReceiptForm();
    
    public static CashReceiptForm cashReceiptForm = new CashReceiptForm();
    
    public static ReportStockForm reportStockForm = new ReportStockForm();
    public static ReportSaleDailyForm reportSaleDailyForm = new ReportSaleDailyForm();
    public static ReportSaleSummaryForm reportSaleSummaryForm = new ReportSaleSummaryForm();
    public static ReportReceiptSummaryForm reportReceiptSummaryForm = new ReportReceiptSummaryForm();
    public static ReportSystemLogForm reportSystemLogForm = new ReportSystemLogForm();
    
}
