/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import DBCLS.Log;
import DBCLS.User;
import DBCLS.User.UserInfo;
import GLOBAL.EncDec;
import static GLOBAL.HelperFunctions.*;
import static GLOBAL.Settings.*;
import static GLOBAL.Varibles.*;
import forms.LoginForm;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import static pos.Apps.*;

/**
 *
 * @author feine
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        setCurrentToDefaultUser();        
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/images/pos.png")).getImage());
        
        mnuMain.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/images/appbg.jpg"));
        
        try {
            LoadForms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void setCurrentToDefaultUser() {
        UserInfo ui = new UserInfo();
        ui.id = 0;
        ui.username = "SYSTEM";
        ui.password = "SYSTEM";
        ui.email = "system.system.net";
        ui.phone = "099 999 9999";
        ui.level = 1000;      
        
        CURRENT_USER = ui;
    }
    
    private void LoadForms() {
        mainDesktop.add(loginForm);
        mainDesktop.add(systemLogViewerForm);
        
        mainDesktop.add(dashboardForm);
        mainDesktop.add(newUserForm);
        mainDesktop.add(newCategoryForm);
        mainDesktop.add(newProductForm);
        mainDesktop.add(newStockForm);
        
        mainDesktop.add(manageUserForm);
        mainDesktop.add(manageCategoryForm);
        mainDesktop.add(manageProductForm);
        mainDesktop.add(manageStockForm);
        
        mainDesktop.add(posForm);
        
        // Load as hidden
        setMarginHeight(mainDesktop, cashReceiptForm,MARGIN_SMALL);
        pullCenter(mainDesktop, cashReceiptForm);
        cashReceiptForm.setVisible(false);
        mainDesktop.add(cashReceiptForm);
        
        mainDesktop.add(manageDailyReceiptForm);
        
        mainDesktop.add(reportStockForm);
        mainDesktop.add(reportSaleDailyForm);
        mainDesktop.add(reportSaleSummaryForm);
        mainDesktop.add(reportReceiptSummaryForm); 
        mainDesktop.add(reportSystemLogForm);
        
        mainDesktop.add(creditsForm);
        
        System.out.println("All forms Loaded.");
    }
    
    public void callFunction(String functionName) {
        // Pass
    }
    
    private void logUserLogOut() {
        
        Log.LogInfo li = new Log.LogInfo();
        li.id = 0;
        li.log_date = getCurrentDateTimeFormatted();
        li.user = CURRENT_USER.username;
        li.category = "SYSTEM LOG";
        li.event = "LOGOUT";
        li.details = String.format(
                    """
                    {
                        "SYSTEM LOG":{
                            "LogDate":"%s",
                            "Event":"LOGOUT",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Result":"SUCCESS"
                        }
                    }
                    """.formatted(
                            li.log_date,
                            CURRENT_USER.id,
                            CURRENT_USER.username,
                            CURRENT_USER.email,
                            CURRENT_USER.phone,
                            getUserLevel(CURRENT_USER.level)
                    )
            );            
            Log.addLog(li);
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/appbg.jpg"));
        Image image = imageIcon.getImage();
        mainDesktop = new javax.swing.JDesktopPane(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mnuMain = new javax.swing.JMenuBar();
        mnuMainSystem = new javax.swing.JMenu();
        mnuDashboard = new javax.swing.JMenuItem();
        mnuSystemLogViewer = new javax.swing.JMenuItem();
        mnuSystemLogReport = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuCredits = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuLogOut = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();
        mnuUserMain = new javax.swing.JMenu();
        mnuUserInfo = new javax.swing.JMenuItem();
        mnuAddUser = new javax.swing.JMenuItem();
        mnuProductMain = new javax.swing.JMenu();
        mnuProductInfo = new javax.swing.JMenuItem();
        mnuAddProduct = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuStockInfo = new javax.swing.JMenuItem();
        mnuAddStock = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuCategoryInfo = new javax.swing.JMenuItem();
        mnuAddCategory = new javax.swing.JMenuItem();
        mnuReportMain = new javax.swing.JMenu();
        mnuStockReport = new javax.swing.JMenuItem();
        mnuSaleReport = new javax.swing.JMenuItem();
        mnuSaleSummaryReport = new javax.swing.JMenuItem();
        mnuReceiptSummaryReport = new javax.swing.JMenuItem();
        mnuPOSMain = new javax.swing.JMenu();
        mnuPOS = new javax.swing.JMenuItem();
        mnuReceipt = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POS Management System");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        mainDesktop.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mainDesktop.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                mainDesktopComponentRemoved(evt);
            }
        });

        javax.swing.GroupLayout mainDesktopLayout = new javax.swing.GroupLayout(mainDesktop);
        mainDesktop.setLayout(mainDesktopLayout);
        mainDesktopLayout.setHorizontalGroup(
            mainDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        mainDesktopLayout.setVerticalGroup(
            mainDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );

        mnuMain.setBackground(new java.awt.Color(204, 204, 204));
        mnuMain.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnuMain.setMaximumSize(new java.awt.Dimension(521, 30));
        mnuMain.setMinimumSize(new java.awt.Dimension(166, 30));
        mnuMain.setPreferredSize(new java.awt.Dimension(521, 35));

        mnuMainSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/system-main-color.png"))); // NOI18N
        mnuMainSystem.setText("System");
        mnuMainSystem.setEnabled(false);
        mnuMainSystem.setFocusPainted(true);
        mnuMainSystem.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N

        mnuDashboard.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/dashboard.png"))); // NOI18N
        mnuDashboard.setText("Dashboard");
        mnuDashboard.setEnabled(false);
        mnuDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDashboardActionPerformed(evt);
            }
        });
        mnuMainSystem.add(mnuDashboard);

        mnuSystemLogViewer.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuSystemLogViewer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/view-logs.png"))); // NOI18N
        mnuSystemLogViewer.setText("Log Viewer");
        mnuSystemLogViewer.setEnabled(false);
        mnuSystemLogViewer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSystemLogViewerActionPerformed(evt);
            }
        });
        mnuMainSystem.add(mnuSystemLogViewer);

        mnuSystemLogReport.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuSystemLogReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/log.png"))); // NOI18N
        mnuSystemLogReport.setText("Log Report");
        mnuSystemLogReport.setEnabled(false);
        mnuSystemLogReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSystemLogReportActionPerformed(evt);
            }
        });
        mnuMainSystem.add(mnuSystemLogReport);
        mnuMainSystem.add(jSeparator1);

        mnuCredits.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuCredits.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/credits.png"))); // NOI18N
        mnuCredits.setText("Credits");
        mnuCredits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCreditsActionPerformed(evt);
            }
        });
        mnuMainSystem.add(mnuCredits);
        mnuMainSystem.add(jSeparator4);

        mnuLogOut.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/logout.png"))); // NOI18N
        mnuLogOut.setText("LogOut");
        mnuLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLogOutActionPerformed(evt);
            }
        });
        mnuMainSystem.add(mnuLogOut);

        mnuExit.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/power-off.png"))); // NOI18N
        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuMainSystem.add(mnuExit);

        mnuMain.add(mnuMainSystem);

        mnuUserMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/user-main-color.png"))); // NOI18N
        mnuUserMain.setText("User Management");
        mnuUserMain.setEnabled(false);
        mnuUserMain.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N

        mnuUserInfo.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuUserInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/user-info.png"))); // NOI18N
        mnuUserInfo.setText("User Information");
        mnuUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUserInfoActionPerformed(evt);
            }
        });
        mnuUserMain.add(mnuUserInfo);

        mnuAddUser.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/new-user.png"))); // NOI18N
        mnuAddUser.setText("New User");
        mnuAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddUserActionPerformed(evt);
            }
        });
        mnuUserMain.add(mnuAddUser);

        mnuMain.add(mnuUserMain);

        mnuProductMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/product-main-color.png"))); // NOI18N
        mnuProductMain.setText("Product Management");
        mnuProductMain.setEnabled(false);
        mnuProductMain.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N

        mnuProductInfo.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuProductInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/product-info.png"))); // NOI18N
        mnuProductInfo.setText("Product Information");
        mnuProductInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProductInfoActionPerformed(evt);
            }
        });
        mnuProductMain.add(mnuProductInfo);

        mnuAddProduct.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/new-product.png"))); // NOI18N
        mnuAddProduct.setText("New Product");
        mnuAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddProductActionPerformed(evt);
            }
        });
        mnuProductMain.add(mnuAddProduct);
        mnuProductMain.add(jSeparator2);

        mnuStockInfo.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuStockInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/stock-info.png"))); // NOI18N
        mnuStockInfo.setText("Stock Information");
        mnuStockInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockInfoActionPerformed(evt);
            }
        });
        mnuProductMain.add(mnuStockInfo);

        mnuAddStock.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuAddStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/refill-stock.png"))); // NOI18N
        mnuAddStock.setText("Refill Stock");
        mnuAddStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddStockActionPerformed(evt);
            }
        });
        mnuProductMain.add(mnuAddStock);
        mnuProductMain.add(jSeparator3);

        mnuCategoryInfo.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuCategoryInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/category-info.png"))); // NOI18N
        mnuCategoryInfo.setText("Product Category");
        mnuCategoryInfo.setToolTipText("");
        mnuCategoryInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCategoryInfoActionPerformed(evt);
            }
        });
        mnuProductMain.add(mnuCategoryInfo);

        mnuAddCategory.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/new-category.png"))); // NOI18N
        mnuAddCategory.setText("New Category");
        mnuAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddCategoryActionPerformed(evt);
            }
        });
        mnuProductMain.add(mnuAddCategory);

        mnuMain.add(mnuProductMain);

        mnuReportMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/report-main-color.png"))); // NOI18N
        mnuReportMain.setText("Report System");
        mnuReportMain.setEnabled(false);
        mnuReportMain.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N

        mnuStockReport.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuStockReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/stock-report.png"))); // NOI18N
        mnuStockReport.setText("Stock Report");
        mnuStockReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockReportActionPerformed(evt);
            }
        });
        mnuReportMain.add(mnuStockReport);

        mnuSaleReport.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuSaleReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/sale-daily-report.png"))); // NOI18N
        mnuSaleReport.setText("Sales Daily Report");
        mnuSaleReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaleReportActionPerformed(evt);
            }
        });
        mnuReportMain.add(mnuSaleReport);

        mnuSaleSummaryReport.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuSaleSummaryReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/sale-summary-report.png"))); // NOI18N
        mnuSaleSummaryReport.setText("Sales Summary Report");
        mnuSaleSummaryReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaleSummaryReportActionPerformed(evt);
            }
        });
        mnuReportMain.add(mnuSaleSummaryReport);

        mnuReceiptSummaryReport.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuReceiptSummaryReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/receipt-summary-report.png"))); // NOI18N
        mnuReceiptSummaryReport.setText("Receipt Summary Report");
        mnuReceiptSummaryReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuReceiptSummaryReportActionPerformed(evt);
            }
        });
        mnuReportMain.add(mnuReceiptSummaryReport);

        mnuMain.add(mnuReportMain);

        mnuPOSMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/pos-main-color.png"))); // NOI18N
        mnuPOSMain.setText("POS System");
        mnuPOSMain.setEnabled(false);
        mnuPOSMain.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N

        mnuPOS.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuPOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/pos.png"))); // NOI18N
        mnuPOS.setText("POS Screen");
        mnuPOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPOSActionPerformed(evt);
            }
        });
        mnuPOSMain.add(mnuPOS);

        mnuReceipt.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        mnuReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu/receipt.png"))); // NOI18N
        mnuReceipt.setText("Daily Receipt");
        mnuReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuReceiptActionPerformed(evt);
            }
        });
        mnuPOSMain.add(mnuReceipt);

        mnuMain.add(mnuPOSMain);

        setJMenuBar(mnuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainDesktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainDesktop)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        
        logUserLogOut();
        System.exit(0);
        
    }//GEN-LAST:event_mnuExitActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        // Prepare admin account (Admin level)
        if (User.isExist("username = 'admin'") == null) {
            UserInfo ui = new UserInfo();
            ui.id = 0;
            ui.username = "admin";
            ui.password = EncDec.encrypt("admin", SECRET_KEY);
            ui.email = "admin@system.net";
            ui.phone = "099 999 9999";
            ui.level = 99;
            User.addUser(ui);
            
            // Demo account (Supervisor level)
            ui = new UserInfo();
            ui.id = 0;
            ui.username = "demo";
            ui.password = EncDec.encrypt("demo", SECRET_KEY);
            ui.email = "demo@system.net";
            ui.phone = "033 333 3333";
            ui.level = 31;
            User.addUser(ui);
        }
        
        pullCenter(mainDesktop, loginForm);
        loginForm.show();
        loginForm.requestFocus();
        
        User demo = User.isExist("username = 'demo'");
        String decPass = EncDec.decrypt(demo.getPassword(), SECRET_KEY);
        if (demo != null) {
            loginForm.tbxUser.setText("demo");
            loginForm.tbxPass.setText(decPass);
            loginForm.lblLogInHint.setText("*User: demo / Pass: "+decPass);
        } else {
            loginForm.tbxUser.setText("");
            loginForm.tbxPass.setText("");
            loginForm.lblLogInHint.setText("");            
        }
    
        loginForm.tbxUser.requestFocus();        
        
    }//GEN-LAST:event_formWindowOpened

    private void mainDesktopComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_mainDesktopComponentRemoved
        
        System.out.println("Start LogIn process...");
        System.out.println("Current User: "+CURRENT_USER.username);
        try {
            if (evt.getChild().getName().equals("LOGIN_FORM")) {
                
                pullCenter(mainDesktop, dashboardForm);
                dashboardForm.setMaximum(true);

                /*---- User filtering ----*/
                Integer level = CURRENT_USER.level;

                if (level >= 90) { // Admin
                    System.out.println("Apply Administrator rule.");
                    dashboardForm.setMaximum(true);
                    dashboardForm.show();
                    dashboardForm.callFunction("loadUserInfo");
                    
                    dashboardForm.pnlUserInfo.setBackground(DASHBOARD_ADMIN_COLOR);

                    mnuMainSystem.setEnabled(true);
                    mnuDashboard.setEnabled(true);
                    mnuSystemLogViewer.setEnabled(true);
                    mnuSystemLogReport.setEnabled(true);

                    mnuUserMain.setEnabled(true);
                    mnuProductMain.setEnabled(true);
                    mnuPOSMain.setEnabled(true);
                    mnuReportMain.setEnabled(true);
                    
                } else if (level >= 60) { // Manager
                    System.out.println("Apply Manager rule.");
                    dashboardForm.setMaximum(true);
                    dashboardForm.show();
                    dashboardForm.callFunction("loadUserInfo");
                    
                    dashboardForm.pnlUserInfo.setBackground(DASHBOARD_MANAGER_COLOR);

                    mnuMainSystem.setEnabled(true);
                    mnuUserMain.setEnabled(true);
                    mnuDashboard.setEnabled(true);
                    mnuReportMain.setEnabled(true);
                    
                } else if (level >= 30) { // Supervisor
                    System.out.println("Apply Supervisor rule.");
                    dashboardForm.setMaximum(true);
                    dashboardForm.show();
                    dashboardForm.callFunction("loadUserInfo");
                    
                    dashboardForm.pnlUserInfo.setBackground(DASHBOARD_SUPERVISOR_COLOR);

                    mnuMainSystem.setEnabled(true);
                    mnuDashboard.setEnabled(true);
                    mnuProductMain.setEnabled(true);
                    mnuPOSMain.setEnabled(true);
                    mnuReportMain.setEnabled(true);
                    
                } else { // Staff
                    System.out.println("Applyi Staff rule.");
                    pullCenter(mainDesktop, posForm);
                    posForm.setMaximum(true);
                    posForm.show();
                    posForm.tbxCode.requestFocus();
                    
                    dashboardForm.pnlUserInfo.setBackground(DASHBOARD_STAFF_COLOR);

                    mnuMainSystem.setEnabled(true);                
                    mnuPOSMain.setEnabled(true);
                }                

            }
        } catch (Exception e) {
            // pass
        }
        
    }//GEN-LAST:event_mainDesktopComponentRemoved

    private void mnuDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDashboardActionPerformed
        
        pullCenter(mainDesktop, dashboardForm);
        try {
            dashboardForm.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        dashboardForm.show();
        dashboardForm.toFront();
        
    }//GEN-LAST:event_mnuDashboardActionPerformed

    private void mnuAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddUserActionPerformed
        
        pullCenter(mainDesktop, newUserForm);
        newUserForm.show();
        newUserForm.toFront();
        
    }//GEN-LAST:event_mnuAddUserActionPerformed

    private void mnuUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUserInfoActionPerformed
        
        pullCenter(mainDesktop, manageUserForm);
        manageUserForm.show();
        manageUserForm.toFront();
        
    }//GEN-LAST:event_mnuUserInfoActionPerformed

    private void mnuCategoryInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCategoryInfoActionPerformed
        
        pullCenter(mainDesktop, manageCategoryForm);
        manageCategoryForm.show();
        manageCategoryForm.toFront();
        
    }//GEN-LAST:event_mnuCategoryInfoActionPerformed

    private void mnuAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddCategoryActionPerformed
        
        pullCenter(mainDesktop, newCategoryForm);
        newCategoryForm.show();
        newCategoryForm.toFront();
        
    }//GEN-LAST:event_mnuAddCategoryActionPerformed

    private void mnuAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddProductActionPerformed
        
        pullCenter(mainDesktop, newProductForm);
        newProductForm.show();
        newProductForm.toFront();
        
    }//GEN-LAST:event_mnuAddProductActionPerformed

    private void mnuProductInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProductInfoActionPerformed
        
        setMarginHeight(mainDesktop, manageProductForm, MARGIN_LARGE);
        pullCenter(mainDesktop, manageProductForm);
        manageProductForm.show();
        manageProductForm.toFront();
        
    }//GEN-LAST:event_mnuProductInfoActionPerformed

    private void mnuAddStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddStockActionPerformed
        
        pullCenter(mainDesktop, newStockForm);
        newStockForm.show();
        newStockForm.toFront();
        
    }//GEN-LAST:event_mnuAddStockActionPerformed

    private void mnuStockInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockInfoActionPerformed
        
        setMarginHeight(mainDesktop, manageStockForm, MARGIN_LARGE);
        pullCenter(mainDesktop, manageStockForm);
        manageStockForm.show();
        manageStockForm.toFront();
        
    }//GEN-LAST:event_mnuStockInfoActionPerformed

    private void mnuPOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPOSActionPerformed
        
        pullCenter(mainDesktop, posForm);
        try {
            posForm.setMaximum(true);
        }
        catch (PropertyVetoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        posForm.show();
        posForm.toFront();
        posForm.tbxCode.requestFocus();
        
    }//GEN-LAST:event_mnuPOSActionPerformed

    private void mnuReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuReceiptActionPerformed
        
        setMarginHeight(mainDesktop, manageDailyReceiptForm, MARGIN_MEDIUM);
        pullCenter(mainDesktop, manageDailyReceiptForm);
        manageDailyReceiptForm.show();
        manageDailyReceiptForm.toFront();
        
    }//GEN-LAST:event_mnuReceiptActionPerformed

    private void mnuStockReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockReportActionPerformed
        
        setMarginHeight(mainDesktop, reportStockForm, MARGIN_SMALL);
        pullCenter(mainDesktop, reportStockForm);
        reportStockForm.show();
        reportStockForm.toFront();
        
    }//GEN-LAST:event_mnuStockReportActionPerformed

    private void mnuSaleReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaleReportActionPerformed
        
        setMarginHeight(mainDesktop, reportSaleDailyForm, MARGIN_SMALL);
        pullCenter(mainDesktop, reportSaleDailyForm);
        reportSaleDailyForm.show();
        reportSaleDailyForm.toFront();
        
    }//GEN-LAST:event_mnuSaleReportActionPerformed

    private void mnuSaleSummaryReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaleSummaryReportActionPerformed
        
        setMarginHeight(mainDesktop, reportSaleSummaryForm, MARGIN_SMALL);
        pullCenter(mainDesktop, reportSaleSummaryForm);
        reportSaleSummaryForm.show();
        reportSaleSummaryForm.toFront();
        
    }//GEN-LAST:event_mnuSaleSummaryReportActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
    }//GEN-LAST:event_formComponentResized

    private void mnuReceiptSummaryReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuReceiptSummaryReportActionPerformed
        
        setMarginHeight(mainDesktop, reportReceiptSummaryForm, MARGIN_SMALL);
        pullCenter(mainDesktop, reportReceiptSummaryForm);
        reportReceiptSummaryForm.show();
        reportReceiptSummaryForm.toFront();
        
    }//GEN-LAST:event_mnuReceiptSummaryReportActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        logUserLogOut();
        
    }//GEN-LAST:event_formWindowClosing

    private void mnuSystemLogReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSystemLogReportActionPerformed
        
        setMarginHeight(mainDesktop, reportSystemLogForm, MARGIN_SMALL);
        pullCenter(mainDesktop, reportSystemLogForm);
        reportSystemLogForm.show();
        reportSystemLogForm.toFront();
        
    }//GEN-LAST:event_mnuSystemLogReportActionPerformed

    private void mnuLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLogOutActionPerformed
        
        mnuMainSystem.setEnabled(false);
        mnuDashboard.setEnabled(false);
        mnuSystemLogReport.setEnabled(false);
        mnuUserMain.setEnabled(false);
        mnuProductMain.setEnabled(false);
        mnuPOSMain.setEnabled(false);
        mnuReportMain.setEnabled(false);
        
        logUserLogOut();
        setCurrentToDefaultUser();
        
        for (Component comp : mainDesktop.getComponents()) {
            if (comp instanceof JInternalFrame) {
                ((JInternalFrame) comp).hide();
            }
        }        

        loginForm = new LoginForm();        
        pullCenter(mainDesktop, loginForm);
        
        User demo = User.isExist("username = 'demo'");
        String decPass = EncDec.decrypt(demo.getPassword(), SECRET_KEY);
        if (demo != null) {
            loginForm.tbxUser.setText("demo");
            loginForm.tbxPass.setText(decPass);
            loginForm.lblLogInHint.setText("*User: demo / Pass: "+decPass);
        } else {
            loginForm.tbxUser.setText("");
            loginForm.tbxPass.setText("");
            loginForm.lblLogInHint.setText("");            
        }
        
        mainDesktop.add(loginForm);
        loginForm.show();
        loginForm.toFront();
        
        System.out.println("Logged Out.");
        System.out.println("Current User: "+CURRENT_USER.username);
        
    }//GEN-LAST:event_mnuLogOutActionPerformed

    private void mnuCreditsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCreditsActionPerformed
        
        pullCenter(mainDesktop, creditsForm);
        creditsForm.show();
        creditsForm.toFront();
        
    }//GEN-LAST:event_mnuCreditsActionPerformed

    private void mnuSystemLogViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSystemLogViewerActionPerformed
        
        pullCenter(mainDesktop, systemLogViewerForm);
        systemLogViewerForm.show();
        systemLogViewerForm.toFront();
        
    }//GEN-LAST:event_mnuSystemLogViewerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame f = new MainFrame();
                f.setVisible(true);
                f.setExtendedState(JFrame.MAXIMIZED_BOTH);
                f.setResizable(false);
                mainFrame = f;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    public javax.swing.JDesktopPane mainDesktop;
    private javax.swing.JMenuItem mnuAddCategory;
    private javax.swing.JMenuItem mnuAddProduct;
    private javax.swing.JMenuItem mnuAddStock;
    private javax.swing.JMenuItem mnuAddUser;
    private javax.swing.JMenuItem mnuCategoryInfo;
    private javax.swing.JMenuItem mnuCredits;
    private javax.swing.JMenuItem mnuDashboard;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuLogOut;
    public javax.swing.JMenuBar mnuMain;
    public javax.swing.JMenu mnuMainSystem;
    private javax.swing.JMenuItem mnuPOS;
    private javax.swing.JMenu mnuPOSMain;
    private javax.swing.JMenuItem mnuProductInfo;
    public javax.swing.JMenu mnuProductMain;
    private javax.swing.JMenuItem mnuReceipt;
    private javax.swing.JMenuItem mnuReceiptSummaryReport;
    private javax.swing.JMenu mnuReportMain;
    private javax.swing.JMenuItem mnuSaleReport;
    private javax.swing.JMenuItem mnuSaleSummaryReport;
    private javax.swing.JMenuItem mnuStockInfo;
    private javax.swing.JMenuItem mnuStockReport;
    private javax.swing.JMenuItem mnuSystemLogReport;
    private javax.swing.JMenuItem mnuSystemLogViewer;
    private javax.swing.JMenuItem mnuUserInfo;
    public javax.swing.JMenu mnuUserMain;
    // End of variables declaration//GEN-END:variables
}
