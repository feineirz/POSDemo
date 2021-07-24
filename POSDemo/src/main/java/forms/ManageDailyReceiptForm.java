/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Product;
import DBCLS.Receipt;
import DBCLS.ReceiptDetail;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.BG_DARK_ALT;
import static GLOBAL.Settings.MAIN_FONT;
import static GLOBAL.Varibles.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author feinz
 */
public class ManageDailyReceiptForm extends javax.swing.JInternalFrame {

    public static DefaultTableModel modelReceiptList;
    public static DefaultTableModel modelReceiptDetailList;    
    
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();    
    
    /**
     * Creates new form ManageDailyReceipt
     */
    public ManageDailyReceiptForm() {
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        getContentPane().setBackground(BG_DARK_ALT);
        
        modelReceiptList = (DefaultTableModel)tblReceiptList.getModel();
        tblReceiptList.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblReceiptList.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);        
        tblReceiptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        modelReceiptDetailList = (DefaultTableModel)tblReceiptDetail.getModel();
        tblReceiptDetail.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblReceiptDetail.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);  
        tblReceiptDetail.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblReceiptDetail.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblReceiptDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        edtDisplay.setContentType("text/html");
        edtDisplay.setEditable(false);
        
        tblReceiptList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                int curRow = tblReceiptList.getSelectedRow();
                if(curRow > -1){
                    String receiptID = tblReceiptList.getValueAt(curRow, 3).toString();
                    Integer dReceiptID = Integer.parseInt(receiptID);
                    Double totalPrice = Double.parseDouble(tblReceiptList.getValueAt(curRow, 2).toString());
                    
                    edtDisplay.setText(generateCashReceiptHTML(dReceiptID));
                    
                    listReceiptDetail(receiptID);
                    lblReceiptID.setText(IDPF_RECEIPT + IDFMT_RECEIPT.format(dReceiptID));
                    lblTotalPrice.setText(DFMT_PRICE.format(totalPrice));
                }
                
            }            
        });        
        
        listReceipt();
        
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
            case "listReceipt" -> listReceipt();
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
		listReceipt();
                tblReceiptList.clearSelection();
                lblReceiptID.setText("");
                lblTotalPrice.setText("0.00");
                modelReceiptDetailList.setRowCount(0);
                edtDisplay.setText("");
            }
        }
    }
    
    /*==== Helper Functions ====*/
    
    
    /*==== Required Functions ====*/
    private void listReceipt() {
        
        modelReceiptList.setRowCount(0);        
        int row = 0;
        Double totalIncome = 0.0;
        for (Receipt receipt : Receipt.listReceipt("receipt_date between '"+getCurrentDateFormatted()+" 00:00:00' and '"+getCurrentDateFormatted()+" 23:59:59'", "receipt_date desc")) {
            
            totalIncome += receipt.getTotal();
            modelReceiptList.addRow(new Object[0]);
            
            modelReceiptList.setValueAt(receipt.getReceipt_date(), row, 0);
            modelReceiptList.setValueAt(IDPF_RECEIPT+IDFMT_RECEIPT.format(receipt.getId()), row, 1);            
            modelReceiptList.setValueAt(DFMT_PRICE.format(receipt.getTotal()) , row, 2);
            modelReceiptList.setValueAt(receipt.getId(), row, 3); //RealID            
            row++;
        }
        
        lblTotalIncome.setText(DFMT_PRICE.format(totalIncome));
        
    }
    
    private void listReceiptDetail(String receiptID) {      
        
        Product product;
        Receipt receipt = new Receipt(Integer.parseInt(receiptID));
        
        modelReceiptDetailList.setRowCount(0);
        int row = 0;
        for (ReceiptDetail receiptDetail : ReceiptDetail.listReceiptDetail("receipt='"+receiptID+"'")) {
            modelReceiptDetailList.addRow(new Object[0]);
            
            product = new Product(receiptDetail.getProduct());
            modelReceiptDetailList.setValueAt(product.getName(), row, 0);
            modelReceiptDetailList.setValueAt(DFMT_QUANTITY.format(receiptDetail.getQuantity()), row, 1);
            modelReceiptDetailList.setValueAt(DFMT_PRICE.format(receiptDetail.getCurrent_price()), row, 2);
            modelReceiptDetailList.setValueAt(DFMT_PRICE.format(receiptDetail.getQuantity()*receiptDetail.getCurrent_price()), row, 3);
            row++;
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTable = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReceiptList = new javax.swing.JTable();
        pnlHeader = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblTitle = new javax.swing.JLabel();
        pnlDetails = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReceiptDetail = new javax.swing.JTable();
        lblTotalPrice = new javax.swing.JLabel();
        lblReceiptID = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalIncome = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        edtDisplay = new javax.swing.JEditorPane();
        btnPrint = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setTitle("DAILY RECEIPT MANAGEMENT PANEL");
        setMaximumSize(new java.awt.Dimension(4000, 1200));
        setMinimumSize(new java.awt.Dimension(1440, 700));
        setPreferredSize(new java.awt.Dimension(1440, 900));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlTable.setBackground(new java.awt.Color(102, 102, 102));

        tblReceiptList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblReceiptList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "ReceiptID", "Total", "RealID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReceiptList.setRowHeight(24);
        tblReceiptList.setShowVerticalLines(false);
        tblReceiptList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblReceiptList);
        if (tblReceiptList.getColumnModel().getColumnCount() > 0) {
            tblReceiptList.getColumnModel().getColumn(0).setMinWidth(150);
            tblReceiptList.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblReceiptList.getColumnModel().getColumn(0).setMaxWidth(150);
            tblReceiptList.getColumnModel().getColumn(2).setMinWidth(100);
            tblReceiptList.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblReceiptList.getColumnModel().getColumn(2).setMaxWidth(100);
            tblReceiptList.getColumnModel().getColumn(3).setMinWidth(0);
            tblReceiptList.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblReceiptList.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        pnlHeader.setBackground(new java.awt.Color(102, 0, 255));

        lblTitle.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle.setFont(new java.awt.Font("Tw Cen MT", 0, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("DAILY RECEIPT INFORMATION");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pnlDetails.setBackground(new java.awt.Color(102, 102, 102));
        pnlDetails.setMaximumSize(new java.awt.Dimension(4000, 900));
        pnlDetails.setMinimumSize(new java.awt.Dimension(500, 300));

        tblReceiptDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Qty", "Price", "Total", "RealID", "Code"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReceiptDetail.setFocusable(false);
        tblReceiptDetail.setRowHeight(24);
        tblReceiptDetail.setShowVerticalLines(false);
        tblReceiptDetail.getTableHeader().setReorderingAllowed(false);
        tblReceiptDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblReceiptDetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblReceiptDetail);
        if (tblReceiptDetail.getColumnModel().getColumnCount() > 0) {
            tblReceiptDetail.getColumnModel().getColumn(1).setMinWidth(50);
            tblReceiptDetail.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblReceiptDetail.getColumnModel().getColumn(1).setMaxWidth(50);
            tblReceiptDetail.getColumnModel().getColumn(2).setMinWidth(70);
            tblReceiptDetail.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblReceiptDetail.getColumnModel().getColumn(2).setMaxWidth(70);
            tblReceiptDetail.getColumnModel().getColumn(3).setMinWidth(90);
            tblReceiptDetail.getColumnModel().getColumn(3).setPreferredWidth(90);
            tblReceiptDetail.getColumnModel().getColumn(3).setMaxWidth(90);
            tblReceiptDetail.getColumnModel().getColumn(4).setMinWidth(0);
            tblReceiptDetail.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblReceiptDetail.getColumnModel().getColumn(4).setMaxWidth(0);
            tblReceiptDetail.getColumnModel().getColumn(5).setMinWidth(0);
            tblReceiptDetail.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblReceiptDetail.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        lblTotalPrice.setBackground(new java.awt.Color(25, 25, 25));
        lblTotalPrice.setFont(new java.awt.Font("Digital-7 Mono", 0, 52)); // NOI18N
        lblTotalPrice.setForeground(new java.awt.Color(0, 153, 255));
        lblTotalPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalPrice.setText("0.00");
        lblTotalPrice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTotalPrice.setMaximumSize(new java.awt.Dimension(94, 60));
        lblTotalPrice.setMinimumSize(new java.awt.Dimension(94, 60));
        lblTotalPrice.setPreferredSize(new java.awt.Dimension(94, 60));
        lblTotalPrice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblTotalPricePropertyChange(evt);
            }
        });

        lblReceiptID.setBackground(new java.awt.Color(102, 102, 102));
        lblReceiptID.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        lblReceiptID.setForeground(new java.awt.Color(255, 255, 255));
        lblReceiptID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblReceiptID.setMaximumSize(new java.awt.Dimension(400, 27));
        lblReceiptID.setMinimumSize(new java.awt.Dimension(100, 27));
        lblReceiptID.setPreferredSize(new java.awt.Dimension(100, 27));

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblReceiptID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );
        pnlDetailsLayout.setVerticalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailsLayout.createSequentialGroup()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReceiptID, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
        );

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total income");

        lblTotalIncome.setBackground(new java.awt.Color(51, 51, 51));
        lblTotalIncome.setFont(new java.awt.Font("Digital-7 Mono", 0, 52)); // NOI18N
        lblTotalIncome.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalIncome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalIncome.setText("0.00");
        lblTotalIncome.setMaximumSize(new java.awt.Dimension(96, 72));
        lblTotalIncome.setMinimumSize(new java.awt.Dimension(96, 72));
        lblTotalIncome.setPreferredSize(new java.awt.Dimension(96, 72));

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
                    .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(331, 900));
        jPanel2.setMinimumSize(new java.awt.Dimension(331, 300));

        edtDisplay.setContentType("text/html"); // NOI18N
        jScrollPane3.setViewportView(edtDisplay);

        btnPrint.setBackground(new java.awt.Color(255, 204, 0));
        btnPrint.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/print.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrint)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblReceiptDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReceiptDetailMouseClicked

    }//GEN-LAST:event_tblReceiptDetailMouseClicked

    private void lblTotalPricePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblTotalPricePropertyChange

    }//GEN-LAST:event_lblTotalPricePropertyChange

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        
        try {
            edtDisplay.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ManageDailyReceiptForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnPrintActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JEditorPane edtDisplay;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblReceiptID;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalIncome;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblReceiptDetail;
    public static javax.swing.JTable tblReceiptList;
    // End of variables declaration//GEN-END:variables
}
