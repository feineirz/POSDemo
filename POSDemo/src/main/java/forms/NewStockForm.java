/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Log.LogInfo;
import DBCLS.Product;
import DBCLS.Stock;
import DBCLS.Stock.StockInfo;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.*;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author feine
 */
public class NewStockForm extends javax.swing.JInternalFrame {

    public static DefaultTableModel modelContentList;    
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    
    /**
     * Creates new form NewStockForm
     */
    public NewStockForm() {
        
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        getContentPane().setBackground(BG_DARK);
        
        modelContentList = (DefaultTableModel)tblContentList.getModel();
        tblContentList.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblContentList.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblContentList.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblContentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        lblRealID.setVisible(false);
        
        tblContentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int curRow = tblContentList.getSelectedRow();
                if(curRow > -1){
                    tbxCode.setText(tblContentList.getValueAt(curRow, 0).toString());
                    lblName.setText(tblContentList.getValueAt(curRow, 1).toString());
                    lblRealID.setText(tblContentList.getValueAt(curRow, 4).toString());
                }
            }            
        });
        
        listCategory();
        listContentWithFilter();
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
            case "listCategory" -> listCategory();
            case "listContentWithFilter" -> listContentWithFilter();
        }
    }
    
    enum frameState {
        INIT, POST_EDIT
    }
    
    private void initForm() {
        setFrameState(frameState.INIT);
    }
    
    private void setFrameState(frameState framState) {
        switch(framState) {
            case INIT -> {
                listCategory();
                listContentWithFilter();
        
                tbxCode.setText("");
                lblName.setText("");
                tbxCost.setText("");
                tbxQuantity.setText("");
                tbxRemark.setText("");
                
                lblRealID.setText("");
            }                
            case POST_EDIT -> {                
                setFrameState(frameState.INIT);                            
                tbxCode.requestFocus();
                
                callUpdateStockData();
            }
        }
    }
    
    /*==== Helper Functions ====*/    
    public void popupWarning(ValidationResult vr) {        
        JOptionPane.showMessageDialog(this, vr.message, "VALIDATION WARNING", JOptionPane.WARNING_MESSAGE);
    }
    
    public String getFilterString() {
        
        String filter = "";
        String categoryName;
      
        categoryName = String.valueOf(cmbCategory.getSelectedItem());
        
        if (categoryName.equals("All")) {
            filter += "category LIKE '%'";            
        } else {
            Category category = Category.NameToCategory(categoryName);
            if (category != null) {
                filter += "category = " + category.getId();
            } else {
                filter += "category LIKE '%'";
            }
        }
        return filter;
        
    }
    
    /*==== Required Functions ====*/
    
    private void listCategory(){
        
        cmbCategory.removeAllItems();
        cmbCategory.addItem("All");
        for (Category cat : Category.listCategory()) {
            cmbCategory.addItem(cat.getName());
        }
        
    }
    
    public void listContentWithFilter(){
        String filter = getFilterString();
        listContent(filter);
    }
    
    // List all user
    public void listContent(){
        listContent("");
    }
    
    // List user by filter
    public void listContent(String filter){
        
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        try {
            // Clear Data
            int lastRow = tblContentList.getRowCount() - 1;
            while (lastRow > -1) {
                modelContentList.removeRow(lastRow);
                lastRow--;
            }
            
            // Add Userinfo
            int curRow = 0;
            String idx, fprice;
            Stock stock;
            for (Product product : Product.listProduct(filter, "category, name")) {
                stock = Stock.isExist("product = "+product.getId());
                try{
                    modelContentList.addRow(new  Object[0]);

                    modelContentList.setValueAt(product.getCode(), curRow, 0);
                    modelContentList.setValueAt(product.getName(), curRow, 1);             

                    fprice = df.format((double) product.getPrice());
                    modelContentList.setValueAt(DFMT_PRICE.format(product.getCost()), curRow, 2);
                    modelContentList.setValueAt(fprice, curRow, 3);
                    
                    modelContentList.setValueAt(product.getId(), curRow, 4);

                    curRow++;
                }catch(Exception e){
                    
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        jLabel8 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblContentList = new javax.swing.JTable();
        pnlContentInfo = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblRealID = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tbxCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tbxQuantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbxRemark = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        tbxCost = new javax.swing.JTextField();
        pnlHeader = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblTitle = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setResizable(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlTable.setBackground(new java.awt.Color(102, 102, 102));

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 2, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Category: ");
        jLabel8.setPreferredSize(new java.awt.Dimension(45, 20));

        cmbCategory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCategory.setPreferredSize(new java.awt.Dimension(57, 24));
        cmbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoryActionPerformed(evt);
            }
        });

        tblContentList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblContentList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Cost", "Price", "RealID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblContentList.setRowHeight(20);
        tblContentList.setShowVerticalLines(false);
        tblContentList.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblContentList);
        if (tblContentList.getColumnModel().getColumnCount() > 0) {
            tblContentList.getColumnModel().getColumn(0).setMinWidth(120);
            tblContentList.getColumnModel().getColumn(0).setPreferredWidth(120);
            tblContentList.getColumnModel().getColumn(0).setMaxWidth(120);
            tblContentList.getColumnModel().getColumn(2).setMinWidth(70);
            tblContentList.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblContentList.getColumnModel().getColumn(2).setMaxWidth(70);
            tblContentList.getColumnModel().getColumn(3).setMinWidth(70);
            tblContentList.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblContentList.getColumnModel().getColumn(3).setMaxWidth(70);
            tblContentList.getColumnModel().getColumn(4).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        pnlContentInfo.setBackground(new java.awt.Color(102, 102, 102));

        lblRealID.setBackground(new java.awt.Color(204, 204, 204));
        lblRealID.setOpaque(true);

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Code: ");
        jLabel7.setPreferredSize(new java.awt.Dimension(45, 20));

        tbxCode.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tbxCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tbxCode.setMaximumSize(new java.awt.Dimension(1000, 25));
        tbxCode.setMinimumSize(new java.awt.Dimension(260, 25));
        tbxCode.setPreferredSize(new java.awt.Dimension(260, 25));
        tbxCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCodeFocusLost(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Name: ");
        jLabel9.setPreferredSize(new java.awt.Dimension(45, 20));

        lblName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setMaximumSize(new java.awt.Dimension(43, 24));
        lblName.setMinimumSize(new java.awt.Dimension(43, 24));
        lblName.setPreferredSize(new java.awt.Dimension(43, 24));

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Quantity: ");
        jLabel6.setPreferredSize(new java.awt.Dimension(45, 20));

        tbxQuantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxQuantity.setMaximumSize(new java.awt.Dimension(1000, 25));
        tbxQuantity.setMinimumSize(new java.awt.Dimension(260, 25));
        tbxQuantity.setPreferredSize(new java.awt.Dimension(260, 25));
        tbxQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Remark: ");

        tbxRemark.setColumns(20);
        tbxRemark.setRows(5);
        tbxRemark.setMaximumSize(new java.awt.Dimension(1000, 70));
        tbxRemark.setMinimumSize(new java.awt.Dimension(260, 70));
        tbxRemark.setPreferredSize(new java.awt.Dimension(260, 70));
        jScrollPane1.setViewportView(tbxRemark);

        btnAdd.setBackground(new java.awt.Color(0, 153, 204));
        btnAdd.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/refill.png"))); // NOI18N
        btnAdd.setText("Refill");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 204, 0));
        btnCancel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Cost: ");
        jLabel10.setPreferredSize(new java.awt.Dimension(45, 20));

        tbxCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCost.setMaximumSize(new java.awt.Dimension(1000, 25));
        tbxCost.setMinimumSize(new java.awt.Dimension(260, 25));
        tbxCost.setPreferredSize(new java.awt.Dimension(260, 25));
        tbxCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCostFocusLost(evt);
            }
        });

        javax.swing.GroupLayout pnlContentInfoLayout = new javax.swing.GroupLayout(pnlContentInfo);
        pnlContentInfo.setLayout(pnlContentInfoLayout);
        pnlContentInfoLayout.setHorizontalGroup(
            pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentInfoLayout.createSequentialGroup()
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbxCost, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbxQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbxCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlContentInfoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRealID, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContentInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );
        pnlContentInfoLayout.setVerticalGroup(
            pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentInfoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCode, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRealID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlHeader.setBackground(new java.awt.Color(153, 51, 0));

        lblTitle.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("REFILL STOCK");

        lblIcon.setForeground(new java.awt.Color(204, 204, 204));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stock64.png"))); // NOI18N
        lblIcon.setPreferredSize(new java.awt.Dimension(68, 68));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 1206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addGap(18, 18, 18)
                .addComponent(pnlContentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE))
                    .addComponent(pnlContentInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        String productID = lblRealID.getText();
        String cost = tbxCost.getText().trim();
        String quantity = tbxQuantity.getText().trim();
        String remark = tbxRemark.getText().trim();

        // Validation Phase
        ValidationResult vr = new ValidationResult();
        String errMessage;

        if (productID.equals("")) {
            vr.result = false;
            vr.message = "Please select Product or input Product Code first!";
            popupWarning(vr);
            return;
        }
        
        vr = validateCost(cost);
        if (!vr.result) {
            popupWarning(vr);
            tbxCost.requestFocus();
            return;
        }
        
        errMessage = "Invalid 'Quantity' format!";
        vr = validateStringIsInteger(quantity, errMessage);
        if (!vr.result) {
            popupWarning(vr);
            tbxQuantity.requestFocus();
            return;
        }
        
        StockInfo si = new StockInfo();
        si.id = 0;
        si.product = Integer.parseInt(productID);
        si.quantity = Integer.parseInt(quantity);
        si.remark = remark;
        
        // Check if product is exist
        Stock stock = Stock.isExist("product='"+productID+"'");
        if (stock != null) {
            // Adjust average cost
            Product product = new Product(si.product);
            Double curSumCost = stock.getQuantity()*product.getCost();
            Double newItemSumCost = Double.parseDouble(cost) * Integer.parseInt(quantity);            
            Double newCost = (curSumCost+newItemSumCost) / (stock.getQuantity() + Integer.parseInt(quantity));
            Double curCost = product.getCost();
            product.setCost(newCost);
            
            stock.setQuantity(stock.getQuantity() + Integer.parseInt(quantity));
            if (!"".equals(stock.getRemark())) stock.setRemark(remark);
            
            Category category = new Category(product.getCategory());
            
            LogInfo li = new LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "APPLICATION LOG";
            li.event = "REFILL STOCK";
            
            li.details = String.format(
                    """
                    {
                        "APPLICATION LOG":{
                            "LogDate":"%s",
                            "Event":"REFILL STOCK",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Data":{
                                "Stock":{
                                    "ID":%d,
                                    "Quantity":%d
                                },
                                "Product":{
                                    "ID":%d,
                                    "Category":{
                                        "ID":%d,
                                        "Name":"%s"
                                    },
                                    "Code":"%s",
                                    "Name":"%s",
                                    "Cost":%s
                                },
                                "Quantity":%d,
                                "Cost":%s,
                                "NewQuantity":%d,
                                "NewCost":%s
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
                            getUserLevel(CURRENT_USER.level),
                            stock.getId(),
                            stock.getQuantity()-si.quantity,
                            product.getId(),
                            category.getId(),
                            category.getName(),
                            product.getCode(),
                            product.getName(),
                            DFMT_PRICE.format(curCost),
                            si.quantity,
                            DFMT_PRICE_NC.format(Double.parseDouble(cost)),
                            stock.getQuantity(),
                            DFMT_PRICE_NC.format(product.getCost())
                    )
            );            
            Log.addLog(li);
            
            JOptionPane.showMessageDialog(this, "Add product to Stock successful.","SUCCESSFUL.",JOptionPane.INFORMATION_MESSAGE);
            
            setFrameState(frameState.POST_EDIT);
            
        } else {
            if(Stock.addStock(si) != null){
                
                Product product = new Product(si.product);
                Category category = new Category(product.getCategory());
                
                LogInfo li = new LogInfo();
                li.id = 0;
                li.log_date = getCurrentDateTimeFormatted();
                li.user = CURRENT_USER.username;
                li.category = "APPLICATION LOG";
                li.event = "REFILL STOCK";
                
                li.details = String.format(
                    """
                    {
                        "APPLICATION LOG":{
                            "LogDate":"%s",
                            "Event":"REFILL STOCK",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Data":{
                                "Stock":{
                                    "ID":%d,
                                    "Quantity":%d
                                },
                                "Product":{
                                    "ID":%d,
                                    "Category":{
                                        "ID":%d,
                                        "Name":"%s"
                                    },
                                    "Code":"%s",
                                    "Name":"%s",
                                    "Cost":%s
                                },
                                "Quantity":%d,
                                "Cost":%s,
                                "NewQuantity":%d,
                                "NewCost":%s
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
                            getUserLevel(CURRENT_USER.level),
                            stock.getId(),
                            stock.getQuantity()-si.quantity,
                            product.getId(),
                            category.getId(),
                            category.getName(),
                            product.getCode(),
                            product.getName(),
                            DFMT_PRICE.format(product.getCost()),
                            si.quantity,
                            DFMT_PRICE_NC.format(product.getCost()),
                            stock.getQuantity(),
                            DFMT_PRICE_NC.format(product.getCost())
                    )
                );            
                Log.addLog(li);
                
                JOptionPane.showMessageDialog(this, "Add product to Stock successful.","SUCCESSFUL.",JOptionPane.INFORMATION_MESSAGE);  
                
                setFrameState(frameState.POST_EDIT); 
                tbxCode.requestFocus();
            }else{
                JOptionPane.showMessageDialog(this, "Add product to Stock failed.", "ERROR", JOptionPane.ERROR_MESSAGE);            
            }
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        setFrameState(frameState.INIT);
        this.hide();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tbxQuantityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxQuantityFocusGained

    private void tbxQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxQuantityFocusLost

    private void tbxCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusGained
        
        tbxCode.selectAll();
        
    }//GEN-LAST:event_tbxCodeFocusGained

    private void tbxCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusLost
        
        String code = tbxCode.getText().trim();
        
        if (!"".equals(code)) {
            if (Product.isExist("code='"+code+"'") == null) {
                JOptionPane.showMessageDialog(this, "Product code '"+code+"' does not exists!", "ERROR", JOptionPane.ERROR_MESSAGE);
                setFrameState(frameState.POST_EDIT);
                tbxCode.requestFocus();                
            } else {
                cmbCategory.setSelectedIndex(0);
                String pcode;
                for (int i = 0; i < tblContentList.getRowCount(); i++) {                
                    pcode = tblContentList.getValueAt(i, 0).toString();
                    if (pcode.equals(code)) {
                        tblContentList.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        }
        
    }//GEN-LAST:event_tbxCodeFocusLost

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        
        listContentWithFilter();
        
    }//GEN-LAST:event_cmbCategoryActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown

    private void tbxCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCostFocusGained
        
        tbxCost.selectAll();
        
    }//GEN-LAST:event_tbxCostFocusGained

    private void tbxCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCostFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxCostFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRealID;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlContentInfo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblContentList;
    private javax.swing.JTextField tbxCode;
    private javax.swing.JTextField tbxCost;
    private javax.swing.JTextField tbxQuantity;
    private javax.swing.JTextArea tbxRemark;
    // End of variables declaration//GEN-END:variables

}
