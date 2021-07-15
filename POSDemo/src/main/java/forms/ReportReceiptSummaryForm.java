/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Log;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.BG_DARK;
import static GLOBAL.Settings.MAIN_FONT;
import static GLOBAL.Varibles.CURRENT_USER;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Graphics;
import java.awt.print.PrinterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author feine
 */
public class ReportReceiptSummaryForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReportReceiptSummaryForm
     */
    public ReportReceiptSummaryForm() {
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        getContentPane().setBackground(BG_DARK);
        
        Date curDate = null;
        try {
            curDate = new SimpleDateFormat("yyyy-MM-dd").parse(getCurrentDateFormatted());
        } catch (ParseException ex) {
            Logger.getLogger(ReportSaleSummaryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        jdcReportStart.setDate(curDate);
        jdcReportEnd.setDate(curDate);
        JTextFieldDateEditor editor;
        editor = (JTextFieldDateEditor) jdcReportStart.getDateEditor();
        editor.setEditable(false);
        editor = (JTextFieldDateEditor) jdcReportEnd.getDateEditor();
        editor.setEditable(false);
        
        edpReport.setEditable(false);
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
            case "loadReport" -> loadReport();
        }
    }
    
    enum frameState {
        INIT
    }
    
    private void initForm() {
        setFrameState(frameState.INIT);
    }
    
    private void setFrameState(frameState framState) {
        switch(framState) {
            case INIT -> {
                
            }
        }
    }
    
    /*==== Helper Functions ====*/
	
    
    /*==== Required Functions ====*/
    private void loadReport() {      
        
        edpReport.setText(generateReceiptReportHTML(jdcReportStart.getDate(), jdcReportEnd.getDate(), "RECEIPT SUMMARY REPORT"));   
        edpReport.setSelectionStart(0);
        edpReport.setSelectionEnd(0);
        
        Log.LogInfo li = new Log.LogInfo();
        li.id = 0;
        li.log_date = getCurrentDateTimeFormatted();
        li.user = CURRENT_USER.username;
        li.category = "APPLICATION LOG";
        li.event = "GENERATE REPORT";            
        li.details = "User '"+CURRENT_USER.username+"' (User Level: "+getUserLevel(CURRENT_USER.level)+") \n"
                + "GENERATE REPORT\n"
                + " Report[\n"
                + "  Type: Receipt Summary Report,\n"
                + "  Start Date: "+getDateFormatted(jdcReportStart.getDate())+",\n"
                + "  End Date: "+getDateFormatted(jdcReportEnd.getDate())+",\n"
                + "  Result: SUCCESS\n"
                + " ]";
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

        pnlHeader = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel4 = new javax.swing.JLabel();
        pnlDateFilter = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jdcReportStart = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jdcReportEnd = new com.toedter.calendar.JDateChooser();
        btnReport = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        edpReport = new javax.swing.JEditorPane();
        btnPrint = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(950, 2400));
        setMinimumSize(new java.awt.Dimension(950, 850));
        setPreferredSize(new java.awt.Dimension(950, 850));

        pnlHeader.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("REPORT");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        pnlDateFilter.setBackground(new java.awt.Color(102, 102, 102));

        jdcReportStart.setBackground(new java.awt.Color(102, 102, 102));
        jdcReportStart.setDateFormatString("yyyy-MM-dd");
        jdcReportStart.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jdcReportStart.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("To");

        jdcReportEnd.setBackground(new java.awt.Color(102, 102, 102));
        jdcReportEnd.setDateFormatString("yyyy-MM-dd");
        jdcReportEnd.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jdcReportEnd.setOpaque(false);

        btnReport.setBackground(new java.awt.Color(153, 204, 255));
        btnReport.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/report16.png"))); // NOI18N
        btnReport.setText("Report");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Report from");

        javax.swing.GroupLayout pnlDateFilterLayout = new javax.swing.GroupLayout(pnlDateFilter);
        pnlDateFilter.setLayout(pnlDateFilterLayout);
        pnlDateFilterLayout.setHorizontalGroup(
            pnlDateFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDateFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdcReportStart, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdcReportEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDateFilterLayout.setVerticalGroup(
            pnlDateFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDateFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDateFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jdcReportStart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jdcReportEnd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        edpReport.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(edpReport);

        btnPrint.setBackground(new java.awt.Color(255, 204, 0));
        btnPrint.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer16.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(pnlDateFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDateFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrint)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed

        loadReport();        

    }//GEN-LAST:event_btnReportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed

        try {
            edpReport.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ManageDailyReceiptForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReport;
    private javax.swing.JEditorPane edpReport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcReportEnd;
    private com.toedter.calendar.JDateChooser jdcReportStart;
    private javax.swing.JPanel pnlDateFilter;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}
