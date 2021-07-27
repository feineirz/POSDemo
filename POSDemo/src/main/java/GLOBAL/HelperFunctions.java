/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLOBAL;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Product;
import DBCLS.Receipt;
import DBCLS.ReceiptDetail;
import DBCLS.ReceiptDetail.TopBestSellingInfo;
import DBCLS.Stock;
import static GLOBAL.Settings.MAIN_FONT;
import static GLOBAL.Varibles.*;
import forms.ReportSaleSummaryForm;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import static pos.Apps.*;

/**
 *
 * @author feine
 */
public class HelperFunctions {
    
    public static void setLAF() {
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HelperFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(HelperFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HelperFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HelperFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setUIFont(new FontUIResource(MAIN_FONT));
        
    }
    
    public static void setUIFont (javax.swing.plaf.FontUIResource f){        
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get (key);
                if (value instanceof javax.swing.plaf.FontUIResource)
                        UIManager.put (key, f);
        }
    }
    
    public static void openURL(String url) {
        
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    /*---- Call Functions ----*/
    
    public static void callUpdateUserData() {
        if (manageUserForm.isVisible()) manageUserForm.callFunction("listContentWithFilter");
    }
    
    public static void callUpdateCategoryData() {
        if (dashboardForm.isVisible()) dashboardForm.callFunction("loadDashboardInfo");  
        if (manageCategoryForm.isVisible()) manageCategoryForm.callFunction("listContentWithFilter");        
        if (newStockForm.isVisible()) newStockForm.callFunction("listCategory");        
        if (newProductForm.isVisible()) newProductForm.callFunction("listCategory"); 
        if (manageProductForm.isVisible()) manageProductForm.callFunction("listCategory");        
        if (posForm.isVisible()) posForm.callFunction("listCategory");
        
        ENABLE_REPORT_LOG = false;
        if (reportStockForm.isVisible()) reportStockForm.callFunction("loadReport");        
        if (reportSaleDailyForm.isVisible()) reportSaleDailyForm.callFunction("loadReport");
        ENABLE_REPORT_LOG = true;
    }
    
    public static void callUpdateProductData() {
        if (dashboardForm.isVisible()) dashboardForm.callFunction("loadDashboardInfo");        
        if (manageProductForm.isVisible()) manageProductForm.callFunction("listContentWithFilter");        
        if (newStockForm.isVisible()) newStockForm.callFunction("listContentWithFilter");        
        if (manageStockForm.isVisible()) manageStockForm.callFunction("listContentWithFilter");
        if (posForm.isVisible()) posForm.callFunction("listContentWithFilter");
        
        ENABLE_REPORT_LOG = false;
        if (reportStockForm.isVisible()) reportStockForm.callFunction("loadReport");
        if (reportSaleDailyForm.isVisible()) reportSaleDailyForm.callFunction("loadReport");  
        ENABLE_REPORT_LOG = true;              
    }
    
    public static void callUpdateStockData() {
        if (dashboardForm.isVisible()) dashboardForm.callFunction("loadDashboardInfo");
        if (manageStockForm.isVisible())  manageStockForm.callFunction("listContentWithFilter");
        
        ENABLE_REPORT_LOG = false;
        if (reportStockForm.isVisible()) reportStockForm.callFunction("loadReport");
        if (reportSaleDailyForm.isVisible()) reportSaleDailyForm.callFunction("loadReport");
        ENABLE_REPORT_LOG = true;
    }
    
    public static void callUpdateProductAndStockData() {
        if (dashboardForm.isVisible()) dashboardForm.callFunction("loadDashboardInfo");
        if (manageProductForm.isVisible()) manageProductForm.callFunction("listContentWithFilter");
        if (newStockForm.isVisible()) newStockForm.callFunction("listContentWithFilter");
        if (manageStockForm.isVisible()) manageStockForm.callFunction("listContentWithFilter");
        if (posForm.isVisible()) posForm.callFunction("listContentWithFilter");
        
        ENABLE_REPORT_LOG = false;
        if (reportStockForm.isVisible()) reportStockForm.callFunction("loadReport");
        if (reportSaleDailyForm.isVisible()) reportSaleDailyForm.callFunction("loadReport");  
        ENABLE_REPORT_LOG = true;              
    }
    
    public static void callUpdateReceiptData() {
        if (manageDailyReceiptForm.isVisible()) manageDailyReceiptForm.callFunction("listReceipt");
    }
    
    /*---- End Call Functions ----*/
    
    
    /*---- Frame size and position ----*/
    
    public static void pullCenter(JDesktopPane parent, JInternalFrame child) {
        
        Point centerLocation = new Point((parent.getSize().width - child.getSize().width) /2, (parent.getSize().height - child.getSize().height) /2);
        child.setLocation(centerLocation);
        
    }
    
    public static void pullCenter(Dimension parent, JInternalFrame child) {
        
        Point centerLocation = new Point((parent.getSize().width - child.getSize().width) /2, (parent.getSize().height - child.getSize().height) /2);
        child.setLocation(centerLocation);
        
    }
    
    public static void setlMarginWidth(JDesktopPane parent, JInternalFrame child, Integer margin) {        
        Dimension frameSize = new Dimension(parent.getWidth() - margin*2, child.getHeight());
        child.setSize(frameSize);        
    }
    
    public static void setlMarginWidth(Dimension parent, JInternalFrame child, Integer margin) {        
        Dimension frameSize = new Dimension(parent.getSize().width - margin*2, child.getHeight());
        child.setSize(frameSize);        
    }
    
    public static void setMarginHeight(JDesktopPane parent, JInternalFrame child, Integer margin) {        
        Dimension frameSize = new Dimension(child.getWidth(), parent.getHeight() - margin*2);
        child.setSize(frameSize);        
    }
    
    public static void setMarginHeight(Dimension parent, JInternalFrame child, Integer margin) {        
        Dimension frameSize = new Dimension(child.getWidth(), parent.getSize().height - margin*2);
        child.setSize(frameSize);        
    }
    
    public static void setMarginSize(JDesktopPane parent, JInternalFrame child, Integer margin) {        
        Dimension frameSize = new Dimension(parent.getWidth() - margin*2, parent.getHeight() - margin*2);
        child.setSize(frameSize);        
    }
    
    public static void setMarginSize(Dimension parent, JInternalFrame child, Integer margin) {        
        Dimension frameSize = new Dimension(parent.getSize().width - margin*2, parent.getSize().height - margin*2);
        child.setSize(frameSize);        
    }
    
    /*---- Converter ----*/
    public static String getUserLevel(Integer level) {
        if (level >= 1000) {
            return "SYSTEM";
        } else if (level >=90) {
            return "Administrator";
        } else if (level >= 60) {
            return "Manager";
        } if (level >= 30) {
            return "Supervisor";
        } else {
            return "Staff";
        }
    }
    
    
    /*---- Datetime ----*/
    
    public static Date getCurrentDate() {
        
        Date curDate = null;
        try {
            curDate = new SimpleDateFormat("yyyy-MM-dd").parse(getCurrentDateFormatted());
        } catch (ParseException ex) {
            Logger.getLogger(ReportSaleSummaryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return curDate;
    }
    
    public static String getDateFormatted(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, formatter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fDate = ldt.format(dtf);
        return fDate;
    }
    
    public static String getDateFullFormatted(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, formatter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String fDate = ldt.format(dtf);
        return fDate;
    }
    
    public static String getDateFormatted(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String fDate = formatter.format(date);
        return fDate;
    }
    
    public static String getDateTimeFormatted(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fDate = formatter.format(date);
        return fDate;
    }
    
    public static String getCurrentDateFormatted() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fDate = ldt.format(dtf);
        return fDate;
    }
    
    public static String getCurrentDateFullFormatted() {        
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String fDate = ldt.format(dtf);
        return fDate;
    }
    
    public static String getCurrentDateTimeFormatted() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fDate = ldt.format(dtf);
        return fDate;
    }
    
    public static String getCurrentDateTimeFullFormatted() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
        String fDate = ldt.format(dtf);
        return fDate;
    }
    
    public static String getFirstDateOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public static String getLastDateOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    /*---- HTML Reports ----*/
    
    public static String generateCashReceiptHTML(int receiptID) {
        
        String htmlTemplate, htmlContent;
        htmlTemplate = """
                    <!doctype html>
                    <html>
                        <body style="font-family: tahoma;">
                            <center>
                                <h2>POSDEMO SHOP</h2><br/>
                                9/9 Moo 9, Bang Phut, Pak Kret<br/>
                                Nonthaburi 11120<br/>
                                Tel. 02 504 7788<br/>
                                ***********************************<br/>
                                TaxID. 1123345667789<br/>
                                ***********************************<br/>
                                <b>CASH RECEIPT</b><br/>
                                -----------------------------------<br/>
                                <font size='-1'>VAT INCLUDED</font><br/>
                                -----------------------------------<br/>
                                @receipt_id_prefix@@receipt_id@<br/>
                                ***********************************<br/>
                                @receipt_detail@<br/>
                                ***********************************<br/>
                                THANK YOU<br/>
                                -----------------------------------<br/>
                                Cashier: @cashier@<br/>
                                -----------------------------------<br/>
                                @time_stamp@<br/>
                                ***********************************<br/>                                
                            </center>
                        </body>
                    </html>
                      """;
        htmlContent = htmlTemplate.replace("@receipt_id_prefix@", IDPF_RECEIPT);
        htmlContent = htmlContent.replace("@receipt_id@", IDFMT_RECEIPT.format(receiptID));
        
        String htmlReceiptDetailTemplate = """
                                        <table width='80%' border=0>
                                            <tr>
                                                <td><b>Items</b></td>
                                                <td  align='right'><b>Price</b></td>
                                            </tr>
                                            @table_content@
                                            <tr><td colspan=2 align='center'>***********************************</td></tr>
                                            @end_table_content@
                                        </table>
                                        """;
        String htmlReceiptDetail = "", tableContent = "", endTableContent = "";
        
        Receipt receipt = new Receipt(receiptID);
        Product product;
        for (ReceiptDetail receiptDetail : ReceiptDetail.listReceiptDetail("receipt='"+receiptID+"'")) {
            product = new Product(receiptDetail.getProduct());
            tableContent += "<tr><td width='70%' valign='top'>"+product.getName()+" [x"+receiptDetail.getQuantity()+"]</td>"
                    + "<td width='30%' align='right' valign='top'>"+DFMT_PRICE.format(receiptDetail.getQuantity()*receiptDetail.getCurrent_price())+"</td></tr>";            
        }
        
        htmlReceiptDetail = htmlReceiptDetailTemplate.replace("@table_content@", tableContent);
        
        endTableContent = "<tr><td><b>Total</b></td><td align='right'><b>"+DFMT_PRICE.format(receipt.getTotal())+"</b></td></tr>";
        endTableContent += "<tr><td>Cash</td><td align='right'>"+DFMT_PRICE.format(receipt.getCash())+"</td></tr>";
        endTableContent += "<tr><td>Exchange</td><td align='right'>"+DFMT_PRICE.format(receipt.getExchange())+"</td></tr>";
        htmlReceiptDetail = htmlReceiptDetail.replace("@end_table_content@", endTableContent);
        
        htmlContent = htmlContent.replace("@receipt_detail@", htmlReceiptDetail); 
        htmlContent = htmlContent.replace("@cashier@", receipt.getCashier());
        htmlContent = htmlContent.replace("@time_stamp@", receipt.getReceipt_date());
        
        return htmlContent;
        
    }
    
    public static String generateStockReportHTML() {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <head></head>
                                <body width=870 style="font-family: tahoma;">
                                    <h1>STOCK REPORT<hr height=4/></h1>
                                    @report_content@
                                    <br/><br/>
                                    <table>
                                        <tr>
                                            <td width=470><font size='+1'><i>Total Cost</i></font></td>
                                            <td width=400 align='right'><font size='+1'><b><i>@grand_cost@</b></i></font></td>
                                        </tr>
                                        <tr>
                                            <td width=470><font size='+1'><i>Total Profit</i></font></td>
                                            <td width=400 align='right'><font size='+1'><b><i>@grand_profit@</b></i></font></td>
                                        </tr>
                                        <tr><td colspan=2><hr height=4></td></tr>
                                        <tr>
                                            <td width=470><h2><b><i>GRAND TOTAL</b></i></h2></td>
                                            <td width=400 align='right'><h2><b><i>@grand_total@</b></i></h2></td>
                                        </tr>
                                        <tr><td colspan=2><hr height=4></td></tr>
                                        <tr><td colspan=2 align='right'><p><font size='-1'><i>Report date: @current_datetime@</i></font></p></td></tr>
                                    </table>
                                    <br/><br/>
                                </body>
                            </html>
                              """;
        
        String tableTemplte = """
                            <br/><br/>
                            <table>
                                <tr>
                                    <td colspan=6 width=870><h2>@category@</h2></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                                <tr>
                                    <td width=120><h4>Code</h4></td>
                                    <td width=440><h4>Name</h4></td>
                                    <td width=70 align='right'><h4>Cost</h4></td>
                                    <td width=70 align='right'><h4>Price</h4></td>
                                    <td width=70 align='right'><h4>Quantity</h4></td>
                                    <td width=100 align='right'><h4>SubTotal</h4></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                                @all_row_content@
                                <tr><td colspan=6><hr></td></tr>
                                <tr>
                                    <td colspan=4 align='right'><i><font size='-1'>COST</font></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total_cost@</font></b></i></td>
                                </tr>
                                <tr>
                                    <td colspan=4 align='right'><i><font size='-1'>PROFIT</font></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total_profit@</font></b></i></td>
                                </tr>
                                <tr>
                                    <td colspan=4 align='right'><b><i><font size='-1'>TOTAL</font></b></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total@</font></b></i></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                            </table>
                              
                              """;
        
        String rowTemplate = """
                            <tr>
                                <td valign='top' height=30"><font size='-1'>@code@</font></td>
                                <td valign='top'><font size='-1'>@name@</font></td>
                                <td align='right' valign='top'><font size='-1'>@cost@</font></td>
                                <td align='right' valign='top'><font size='-1'>@price@</font></td>
                                <td align='right' valign='top'><font size='-1'>@quantity@</font></td>
                                <td align='right' valign='top'><font size='-1'>@subtotal@</font></td>
                            </tr>
                             """;
        String htmlContent = htmlTemplate.replace("@current_datetime@", getCurrentDateTimeFormatted());
        String reportContent = "", tableContent, rowContent = "", allRowContent = "";
        
        Double totalCost, totalProfit, total, grandCost = 0.0, grandProfit = 0.0, grandTotal = 0.0;
        for (Category category : Category.listCategory()) {
            tableContent = tableTemplte.replace("@category@", category.getName().toUpperCase());
            allRowContent = "";
            
            totalCost = 0.0;
            totalProfit = 0.0;
            total = 0.0;
            for (Stock stock : Stock.listStockJoinProduct("category="+category.getId())) {
                Product product = new Product(stock.getProduct());
                rowContent = rowTemplate.replace("@code@", product.getCode());
                rowContent = rowContent.replace("@name@", product.getName());
                rowContent = rowContent.replace("@cost@", DFMT_PRICE.format(product.getCost()));
                rowContent = rowContent.replace("@price@", DFMT_PRICE.format(product.getPrice()));
                rowContent = rowContent.replace("@quantity@", DFMT_QUANTITY.format(stock.getQuantity()));
                rowContent = rowContent.replace("@subtotal@", DFMT_PRICE.format(stock.getQuantity()*product.getPrice()));
                
                totalCost += stock.getQuantity()*product.getCost();
                total += stock.getQuantity()*product.getPrice();
                allRowContent += rowContent;
            }
            totalProfit = total-totalCost;
            tableContent = tableContent.replace("@all_row_content@", allRowContent);
            tableContent = tableContent.replace("@total_cost@", DFMT_PRICE.format(totalCost));
            tableContent = tableContent.replace("@total_profit@", DFMT_PRICE.format(totalProfit));
            tableContent = tableContent.replace("@total@", DFMT_PRICE.format(total));
            
            grandCost += totalCost;            
            grandTotal += total;
            
            reportContent += tableContent;
        }
        
        grandProfit = grandTotal-grandCost;
        
        htmlContent = htmlContent.replace("@report_content@", reportContent);
        
        htmlContent = htmlContent.replace("@grand_cost@", DFMT_PRICE.format(grandCost));
        htmlContent = htmlContent.replace("@grand_profit@", DFMT_PRICE.format(grandProfit));
        htmlContent = htmlContent.replace("@grand_total@", DFMT_PRICE.format(grandTotal));
        return htmlContent;
        
    }
    
    public static String generateSaleReportHTML(Date startDate, Date endDate, String reportTitle) {
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = formatter.format(startDate);
        String eDate = formatter.format(endDate);
        
        return generateSaleReportHTML(sDate, eDate, reportTitle);
        
    }
    
    public static String generateSaleReportHTML(String startDate, String endDate, String reportTitle) {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <body width=870 style="font-family: tahoma;">
                                    <h1>@report_title@<hr height=4/></h1>                               
                                    <span><font size='-1'>Report from &nbsp;<b>@start_date@</b>&nbsp; to &nbsp;<b>@end_date@</b></font></span><hr/>
                                    @report_content@
                                    <br/><br/>
                                    <table>
                                        <tr>
                                            <td width=470><font size='+1'><i>Total Cost</i></font></td>
                                            <td width=400 align='right'><font size='+1'><b><i>@grand_cost@</b></i></font></td>
                                        </tr>
                                        <tr>
                                            <td width=470><font size='+1'><i>Total Profit</i></font></td>
                                            <td width=400 align='right'><font size='+1'><b><i>@grand_profit@</b></i></font></td>
                                        </tr>
                                        <tr><td colspan=2><hr height=4></td></tr>
                                        <tr>
                                            <td width=470><h2><b><i>GRAND TOTAL</b></i></h2></td>
                                            <td width=400 align='right'><h2><b><i>@grand_total@</b></i></h2></td>
                                        </tr>
                                        <tr><td colspan=2><hr height=4></td></tr>
                                        <tr><td colspan=2 align='right'><p><font size='-1'><i>Report date: @current_datetime@</i></font></p></td></tr>
                                    </table>
                                    <br/><br/>
                                </body>
                            </html>
                              """;
        
        String tableTemplte = """
                            <br/>
                            <table>
                                <tr>
                                    <td colspan=6 width=870><h2>@category@</h2></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                                <tr>
                                    <td width=120><h4>Code</h4></td>
                                    <td width=440><h4>Name</h4></td>
                                    <td width=70 align='right'><h4>Cost</h4></td>
                                    <td width=70 align='right'><h4>Price</h4></td>
                                    <td width=70 align='right'><h4>Quantity</h4></td>
                                    <td width=100 align='right'><h4>SubTotal</h4></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                                @all_row_content@
                                <tr><td colspan=6><hr></td></tr>
                                <tr>
                                    <td colspan=4 align='right'><i><font size='-1'>COST</font></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total_cost@</font></b></i></td>
                                </tr>
                                <tr>
                                    <td colspan=4 align='right'><i><font size='-1'>PROFIT</font></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total_profit@</font></b></i></td>
                                </tr>
                                <tr>
                                    <td colspan=4 align='right'><b><i><font size='-1'>TOTAL</font></b></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total@</font></b></i></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                            </table>                              
                              """;
        
        String rowTemplate = """
                            <tr>
                                <td valign='top' height=30"><font size='-1'>@code@</font></td>
                                <td valign='top'><font size='-1'>@name@</font></td>
                                <td align='right' valign='top'><font size='-1'>@cost@</font></td>
                                <td align='right' valign='top'><font size='-1'>@price@</font></td>
                                <td align='right' valign='top'><font size='-1'>@quantity@</font></td>
                                <td align='right' valign='top'><font size='-1'>@subtotal@</font></td>
                            </tr>
                             """;
        
        String htmlContent = htmlTemplate.replace("@report_title@", reportTitle);
        htmlContent = htmlContent.replace("@start_date@", getDateFullFormatted(startDate));
        htmlContent = htmlContent.replace("@end_date@", getDateFullFormatted(endDate));
        htmlContent = htmlContent.replace("@current_datetime@", getCurrentDateTimeFormatted());
        String reportContent = "", tableContent, rowContent = "", allRowContent = "";
        
        Double totalCost, totalProfit, total, grandCost = 0.0, grandProfit = 0.0, grandTotal = 0.0;
        for (Category category : Category.listCategory()) {
            tableContent = tableTemplte.replace("@category@", category.getName().toUpperCase());
            allRowContent = "";
            
            totalCost = 0.0;
            totalProfit = 0.0;
            total = 0.0;
            String filter = "category = "+category.getId()+" and receipt_date between '"+getDateFormatted(startDate)+" 00:00:00' and '"+getDateFormatted(endDate)+" 23:59:59'";
            for (ReceiptDetail.ReceiptDetailGroupInfo rdgi : ReceiptDetail.listReceiptDetailGroupJoinReceipt(filter)) {
                Product product = new Product(rdgi.receiptDetail.getProduct());
                rowContent = rowTemplate.replace("@code@", product.getCode());
                rowContent = rowContent.replace("@name@", product.getName());
                rowContent = rowContent.replace("@cost@", DFMT_PRICE.format(rdgi.receiptDetail.getCurrent_cost()));
                rowContent = rowContent.replace("@price@", DFMT_PRICE.format(rdgi.receiptDetail.getCurrent_price()));
                rowContent = rowContent.replace("@quantity@", DFMT_QUANTITY.format(rdgi.sumQuantity));
                rowContent = rowContent.replace("@subtotal@", DFMT_PRICE.format(rdgi.sumQuantity*rdgi.receiptDetail.getCurrent_price()));
                
                totalCost += rdgi.sumQuantity * rdgi.receiptDetail.getCurrent_cost();
                total += rdgi.sumQuantity * rdgi.receiptDetail.getCurrent_price();
                allRowContent += rowContent;
            }
            totalProfit = total-totalCost;
            tableContent = tableContent.replace("@all_row_content@", allRowContent);
            tableContent = tableContent.replace("@total_cost@", DFMT_PRICE.format(totalCost));
            tableContent = tableContent.replace("@total_profit@", DFMT_PRICE.format(totalProfit));
            tableContent = tableContent.replace("@total@", DFMT_PRICE.format(total));
            
            grandCost += totalCost;            
            grandTotal += total;
            
            reportContent += tableContent;
        }
        
        grandProfit = grandTotal-grandCost;
        
        htmlContent = htmlContent.replace("@report_content@", reportContent);
        
        htmlContent = htmlContent.replace("@grand_cost@", DFMT_PRICE.format(grandCost));
        htmlContent = htmlContent.replace("@grand_profit@", DFMT_PRICE.format(grandProfit));
        htmlContent = htmlContent.replace("@grand_total@", DFMT_PRICE.format(grandTotal));
        return htmlContent;
        
    }
    
    public static String generateReceiptReportHTML(Date startDate, Date endDate, String reportTitle) {
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = formatter.format(startDate);
        String eDate = formatter.format(endDate);
        
        return generateReceiptReportHTML(sDate, eDate, reportTitle);
        
    }
    
    public static String generateReceiptReportHTML(String startDate, String endDate, String reportTitle) {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <body width=870 style="font-family: tahoma;">
                                    <h1>@report_title@<hr height=4/></h1>                               
                                    <span><font size='-1'>Report from &nbsp;<b>@start_date@</b>&nbsp; to &nbsp;<b>@end_date@</b></font></span><hr/>
                                    @report_content@
                                    <br/><br/>
                                    <table>
                                        <tr>
                                            <td width=470><font size='+1'><i>Total Cost</i></font></td>
                                            <td width=400 align='right'><font size='+1'><b><i>@grand_cost@</b></i></font></td>
                                        </tr>
                                        <tr>
                                            <td width=470><font size='+1'><i>Total Profit</i></font></td>
                                            <td width=400 align='right'><font size='+1'><b><i>@grand_profit@</b></i></font></td>
                                        </tr>
                                        <tr><td colspan=2><hr height=4></td></tr>
                                        <tr>
                                            <td width=470><h2><b><i>GRAND TOTAL</b></i></h2></td>
                                            <td width=400 align='right'><h2><b><i>@grand_total@</b></i></h2></td>
                                        </tr>
                                        <tr><td colspan=2><hr height=4></td></tr>
                                        <tr><td colspan=2 align='right'><p><font size='-1'><i>Report date: @current_datetime@</i></font></p></td></tr>
                                    </table>
                                    <br/><br/>
                                </body>
                            </html>
                              """;
        
        String tableTemplte = """
                            <br/>
                            <table>
                                <tr>
                                    <td colspan=6 width=870><h2>@receipt_id@</h2></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                                <tr>
                                    <td width=120><h4>Code</h4></td>
                                    <td width=440><h4>Name</h4></td>
                                    <td width=70 align='right'><h4>Cost</h4></td>
                                    <td width=70 align='right'><h4>Price</h4></td>
                                    <td width=70 align='right'><h4>Quantity</h4></td>
                                    <td width=100 align='right'><h4>SubTotal</h4></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                                @all_row_content@
                                <tr><td colspan=6><hr></td></tr>
                                <tr>
                                    <td colspan=4 align='right'><i><font size='-1'>COST</font></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total_cost@</font></b></i></td>
                                </tr>
                                <tr>
                                    <td colspan=4 align='right'><i><font size='-1'>PROFIT</font></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total_profit@</font></b></i></td>
                                </tr>
                                <tr>
                                    <td colspan=4 align='right'><b><i><font size='-1'>TOTAL</font></b></i></td>
                                    <td colspan=2 align='right'><b><i><font size='-1'>@total@</font></b></i></td>
                                </tr>
                                <tr><td colspan=6><hr></td></tr>
                            </table>                              
                              """;
        
        String rowTemplate = """
                            <tr>
                                <td valign='top' height=30"><font size='-1'>@code@</font></td>
                                <td valign='top'><font size='-1'>@name@</font></td>
                                <td align='right' valign='top'><font size='-1'>@cost@</font></td>
                                <td align='right' valign='top'><font size='-1'>@price@</font></td>
                                <td align='right' valign='top'><font size='-1'>@quantity@</font></td>
                                <td align='right' valign='top'><font size='-1'>@subtotal@</font></td>
                            </tr>
                             """;
        
        String htmlContent = htmlTemplate.replace("@report_title@", reportTitle);
        htmlContent = htmlContent.replace("@start_date@", getDateFullFormatted(startDate));
        htmlContent = htmlContent.replace("@end_date@", getDateFullFormatted(endDate));
        htmlContent = htmlContent.replace("@current_datetime@", getCurrentDateTimeFormatted());
        
        String reportContent = "", tableContent, rowContent = "", allRowContent = "";
        
        Double totalCost, totalProfit, total, grandCost = 0.0, grandProfit = 0.0, grandTotal = 0.0;
        
        for (Receipt receipt : Receipt.listReceipt("receipt_date between '"+getDateFormatted(startDate)+" 00:00:00' and '"+getDateFormatted(endDate)+" 23:59:59'")) {
            tableContent = tableTemplte.replace("@receipt_id@", IDPF_RECEIPT+IDFMT_RECEIPT.format(receipt.getId()));
            allRowContent = "";
            
            totalCost = 0.0;
            totalProfit = 0.0;
            total = 0.0;
            
            String filter = "receipt = "+receipt.getId();
            
            for (ReceiptDetail rd : ReceiptDetail.listReceiptDetailJoinReceipt(filter)) {
                Product product = new Product(rd.getProduct());
                rowContent = rowTemplate.replace("@code@", product.getCode());
                rowContent = rowContent.replace("@name@", product.getName());
                rowContent = rowContent.replace("@cost@", DFMT_PRICE.format(rd.getCurrent_cost()));
                rowContent = rowContent.replace("@price@", DFMT_PRICE.format(rd.getCurrent_price()));
                rowContent = rowContent.replace("@quantity@", DFMT_QUANTITY.format(rd.getQuantity()));
                rowContent = rowContent.replace("@subtotal@", DFMT_PRICE.format(rd.getQuantity()*rd.getCurrent_price()));
                
                totalCost += rd.getQuantity() * rd.getCurrent_cost();
                total += rd.getQuantity() * rd.getCurrent_price();
                allRowContent += rowContent;
            }
            
            totalProfit = total-totalCost;
            tableContent = tableContent.replace("@all_row_content@", allRowContent);
            tableContent = tableContent.replace("@total_cost@", DFMT_PRICE.format(totalCost));
            tableContent = tableContent.replace("@total_profit@", DFMT_PRICE.format(totalProfit));
            tableContent = tableContent.replace("@total@", DFMT_PRICE.format(total));
            
            grandCost += totalCost;            
            grandTotal += total;
            
            reportContent += tableContent;
        }
        
        grandProfit = grandTotal-grandCost;
        
        htmlContent = htmlContent.replace("@report_content@", reportContent);
        
        htmlContent = htmlContent.replace("@grand_cost@", DFMT_PRICE.format(grandCost));
        htmlContent = htmlContent.replace("@grand_profit@", DFMT_PRICE.format(grandProfit));
        htmlContent = htmlContent.replace("@grand_total@", DFMT_PRICE.format(grandTotal));
        return htmlContent;
        
    }
    
    public static String generateSystemLogReportHTML(Date startDate, Date endDate, String reportTitle) {
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = formatter.format(startDate);
        String eDate = formatter.format(endDate);
        
        return generateSystemLogReportHTML(sDate, eDate, reportTitle);
        
    }
    
    public static String generateSystemLogReportHTML(String startDate, String endDate, String reportTitle) {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <body width=950 style="font-family: tahoma;">
                                    <h1>@report_title@<hr height=4/></h1>                               
                                    <span><font size='-1'>Report from &nbsp;<b>@start_date@</b>&nbsp; to &nbsp;<b>@end_date@</b></font></span><hr/>
                                    @report_content@
                                    <br/><br/>
                                </body>
                            </html>
                              """;
        
        String tableTemplte = """
                            <br/>
                            <table>
                                <tr>
                                    <td colspan=5 width=950><h2>@info_header@</h2></td>
                                </tr>
                                <tr><td colspan=5><hr></td></tr>
                                @all_row_content@
                                <tr><td colspan=5 align='center'><font size='+1'>END OF LOG</font></td></tr>
                                <tr><td colspan=5><hr></td></tr>
                                <tr><td colspan=5 align='right'><p><font size='-1'><i>Report date: @current_datetime@</i></font></p></td></tr>
                            </table>                              
                              """;
        
        String rowTemplate = """
                            <tr>
                                <td width=300 valign='top'>
                                    <p><b><i>Category</i></b></p>
                                    <p>&nbsp;&nbsp;&nbsp;&nbsp;<font size=-1>@category@</font></p><br/>
                                    <p><b><i>Log Date</i></b></p>
                                    <p>&nbsp;&nbsp;&nbsp;&nbsp;<font size=-1>@log_date@</font></p><br/>
                                    <p><b><i>Event</i></b></p>
                                    <p>&nbsp;&nbsp;&nbsp;&nbsp;<font size=-1>@event@</font></p><br/>
                                    <p><b><i>Account</i></b></p>
                                    <p>&nbsp;&nbsp;&nbsp;&nbsp;<font size=-1>@user@</font></p>
                                </td>
                                <td width=650 valign='top'>
                                    <p><b><i>Details</i></b></p>
                                    <font size=-1>@details@</font>
                                </td>
                            </tr>
                            <tr><td colspan=5><hr></td></tr>
                             """;
        
        String htmlContent = htmlTemplate.replace("@report_title@", reportTitle);
        htmlContent = htmlContent.replace("@start_date@", getDateFullFormatted(startDate));
        htmlContent = htmlContent.replace("@end_date@", getDateFullFormatted(endDate));        
        
        String reportContent = "", tableContent, rowContent = "", allRowContent = "";        
        tableContent = tableTemplte.replace("@info_header@", "LOG INFORMATION");
        
        allRowContent = "";
        for (Log log : Log.listLog("log_date between '"+getDateFormatted(startDate)+" 00:00:00' and '"+getDateFormatted(endDate)+" 23:59:59'", "log_date desc")) {
            
            rowContent = rowTemplate;
            rowContent = rowContent.replace("@log_date@", log.getLog_date());
            rowContent = rowContent.replace("@category@", log.getCategory());
            rowContent = rowContent.replace("@user@", log.getUser());
            rowContent = rowContent.replace("@event@", log.getEvent());
            rowContent = rowContent.replace("@details@", log.getDetails().replace("<", "&lt;").replace(" ", "&nbsp;").replace("\n", "<br/>"));
            
            allRowContent += rowContent;
            
        }
        
        tableContent = tableContent.replace("@all_row_content@", allRowContent);
        tableContent = tableContent.replace("@current_datetime@", getCurrentDateTimeFormatted());
        reportContent += tableContent;
        
        htmlContent = htmlContent.replace("@report_content@", reportContent);
        return htmlContent;
        
    }
    
    /*---- HTML Dashboard Info ----*/
    
    public static String getTopBestSellingHTML(Integer limit) {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <body width=380 style="font-family: tahoma;">
                                    <table>
                                        @all_row_content@
                                    </table>
                                </body>
                            </html>
                              """;
        
        String htmlContent = htmlTemplate;
        
        String rowContent, allRowContent = "";
        int no = 1;
        for (TopBestSellingInfo tbsi : ReceiptDetail.listTopBestSelling(limit)) {
            rowContent = ""
                    + "<tr>"
                    + "<td rowspan=2 align='center' valign='top'><h1>"+no+"</h1></td>"
                    + "<td><b>"+tbsi.product.getCode()+"</b></td>"
                    + "<td rowspan=2 align='right' valign='top'><h2>"+tbsi.sumQuantity+"</h2></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td><font size='-1'>"+tbsi.product.getName()+"</font></td>"
                    + "</tr>"
                    + "<tr><td colspan=3><hr/></td></tr>";
            allRowContent += rowContent;
            no++;
        }
        
        htmlContent = htmlContent.replace("@all_row_content@", allRowContent);
        
        return htmlContent;
    }
    
    public static String getLowStockNotificationHTML(Integer minimum) {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <body width=380 style="font-family: tahoma;">
                                    <table>
                                        @all_row_content@
                                    </table>
                                </body>
                            </html>
                              """;
        
        String htmlContent = htmlTemplate;
        
        String rowContent, allRowContent = "";
        Product product;
        for (Stock stock : Stock.listStock("quantity < "+minimum, "quantity")) {
            product = new Product(stock.getProduct());
            rowContent = ""
                    + "<tr>"
                    + "<td width=320><b>"+product.getCode()+"</b></td>"
                    + "<td width=60 rowspan=2 align='center'><h3>"+DFMT_QUANTITY.format(stock.getQuantity())+"</h3></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td><font size='-1'>"+product.getName()+"</font></td>"
                    + "</tr>"
                    + "<tr><td colspan=2><hr/></td></tr>";
            allRowContent += rowContent;
        }
        
        htmlContent = htmlContent.replace("@all_row_content@", allRowContent);
        
        return htmlContent;
    }
    
    public static String getApplicationLogHTML() {
        
        String htmlTemplate = """
                            <!doctype html>
                            <html>
                                <body width=100% style="font-family: tahoma;">
                                    <table>
                                        @all_row_content@
                                    </table>
                                </body>
                            </html>
                              """;
        
        String htmlContent = htmlTemplate;
        
        String rowContent, allRowContent = "";
        String filter = "category = 'APPLICATION LOG' and log_date between '"+getCurrentDateFormatted()+" 00:00:00' and '"+getCurrentDateFormatted()+" 23:59:59'";
        String order = "log_date desc";
        for (Log log : Log.listLog(filter, order)) {
            rowContent = ""
                    + "<tr>"
                    + "<td>Log Date:"+log.getLog_date()+" &nbsp;&nbsp; User: "+log.getUser()+"</td>"
                    + "<td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td><font size='-1'>"+log.getDetails()+"</font></td>"
                    + "</tr>"
                    + "<tr><td><hr/></td></tr>";
            allRowContent += rowContent;
        }
        
        htmlContent = htmlContent.replace("@all_row_content@", allRowContent);
        
        return htmlContent;
    }

    
}
