/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLOBAL;

import DBCLS.User;
import java.awt.Color;
import java.text.DecimalFormat;

/**
 *
 * @author feinz
 */
public class Varibles {
    
    public static User.UserInfo CURRENT_USER;    
    public static Integer CURRENT_CASHRECEIPT_ID;
    
    public static boolean ENABLE_REPORT_LOG = true;
    public static boolean REFILL_STOCK_IGNORE_INIT_CODE = false;
    
    public static final Integer MARGIN_SMALL = 20;
    public static final Integer MARGIN_MEDIUM = 40;
    public static final Integer MARGIN_LARGE = 80;
    
    public static final Color DASHBOARD_ADMIN_COLOR = new Color(51, 51, 51);
    public static final Color DASHBOARD_MANAGER_COLOR = new Color(128, 0, 0);
    public static final Color DASHBOARD_SUPERVISOR_COLOR = new Color(0, 51, 102);
    public static final Color DASHBOARD_STAFF_COLOR = new Color(255, 165, 0);
    
    public static final String IDPF_USER = "USR";
    public static final String IDPF_CATEGORY = "CAT";
    public static final String IDPF_PRODUCT = "PD";
    public static final String IDPF_STOCK = "STCK";
    public static final String IDPF_RECEIPT = "RCPT";
    
    public static final DecimalFormat IDFMT_USER = new DecimalFormat("00000");
    public static final DecimalFormat IDFMT_CATEGORY = new DecimalFormat("000");
    public static final DecimalFormat IDFMT_PRODUCT = new DecimalFormat("0000000");
    public static final DecimalFormat IDFMT_STOCK = new DecimalFormat("0000000");
    public static final DecimalFormat IDFMT_RECEIPT = new DecimalFormat("00000000");
    
    public static final DecimalFormat DFMT_QUANTITY = new DecimalFormat("#,##0");
    public static final DecimalFormat DFMT_PRICE = new DecimalFormat("#,##0.00");
    public static final DecimalFormat DFMT_QUANTITY_NC = new DecimalFormat("0");
    public static final DecimalFormat DFMT_PRICE_NC = new DecimalFormat("0.00");
    
}
