/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Product;
import DBCLS.ReceiptDetail;
import DBCLS.Stock;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.BG_DARK_ALT;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static pos.Apps.posForm;

/**
 *
 * @author feine
 */
public class ManageProductForm extends javax.swing.JInternalFrame {
       
    public static DefaultTableModel modelContentList;
    
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    
    
    /**
     * Creates new form ManageProductForm
     */
    public ManageProductForm() {
        
        initComponents();
        getContentPane().setBackground(BG_DARK_ALT);
        
        listCategory();
        
        modelContentList = (DefaultTableModel)tblContentList.getModel();
        
        // Align numeric column to the right
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblContentList.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblContentList.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        tblContentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        lblRealID.setVisible(false);
        
        tblContentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int curRow = tblContentList.getSelectedRow();
                if(curRow > -1){
                    lblProductID.setText(tblContentList.getValueAt(curRow, 0).toString());
                    tbxCode.setText(tblContentList.getValueAt(curRow, 1).toString());
                    tbxName.setText(tblContentList.getValueAt(curRow, 2).toString());
                    
                    // Set combobox selection
                    String category = tblContentList.getValueAt(curRow, 3).toString();
                    for (int i = 0; i < cmbCategory.getItemCount(); i++) {
                        if (cmbCategory.getItemAt(i).equals(category)) {
                            cmbCategory.setSelectedIndex(i);
                            break;
                        }
                    }
                    tbxCost.setText(tblContentList.getValueAt(curRow, 4).toString().replace(",", ""));
                    tbxPrice.setText(tblContentList.getValueAt(curRow, 5).toString().replace(",", ""));
                    tbxDescription.setText(tblContentList.getValueAt(curRow, 6).toString());
                    
                    lblRealID.setText(tblContentList.getValueAt(curRow, 7).toString());
                    cmbStatus.setSelectedItem(tblContentList.getValueAt(curRow, 8).toString());

                    setFrameState(frameState.SELECTED);
                }
            }            
        });
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
            case "listCategory" -> listCategory();
            case "listContentWithFilter" -> listContentWithFilter();
            case "resetInfoPanel" -> resetInfoPanel();
        }
    }
    
    enum frameState {
        INIT, FILTERED, SELECTED, EDIT, POST_EDIT
    }
    
    private void initForm() {
        setFrameState(frameState.INIT);
    }
    
    private void setFrameState(frameState framState) {
        switch(framState) {
            case INIT -> {
                tbxFilter.setText("");
                tbxFilter.setEnabled(true);
                tbxFilter.requestFocus();

                cmbCategoryFilter.removeAllItems();
                cmbCategoryFilter.setEnabled(true);

                btnReload.setEnabled(true);

                tblContentList.setEnabled(true);
                tblContentList.clearSelection();

                listContent();
                listCategory();
                resetInfoPanel();
            }
            
            case FILTERED -> {
                tblContentList.setEnabled(true);
                tblContentList.clearSelection();

                resetInfoPanel();
            }
            
            case SELECTED -> {
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true); 
            }
            
            case EDIT -> {
                tbxFilter.setEnabled(false);
                cmbCategoryFilter.setEnabled(false);
                btnReload.setEnabled(false);
                tblContentList.setEnabled(false);

                tbxCode.setEnabled(true);
                tbxName.setEnabled(true);
                cmbCategory.setEnabled(true);
                tbxCost.setEnabled(true);
                sldProfit.setEnabled(true);
                tbxPrice.setEnabled(true);
                cmbStatus.setEnabled(true);
                tbxDescription.setEnabled(true);

                btnEdit.setText("Update");
                btnDelete.setEnabled(false);
                btnCancel.setEnabled(true);
            }
                
            case POST_EDIT -> {
                tbxFilter.setEnabled(true);
                tbxFilter.requestFocus();
                cmbCategoryFilter.setEnabled(true);
                btnReload.setEnabled(true);
                tblContentList.setEnabled(true);
                tblContentList.clearSelection();

                resetInfoPanel();
                //listContentWithFilter();
                callUpdateProductData();
            }
        }
    }
    
    /*==== Helper Functions ====*/
    public void popupWarning(ValidationResult vr) {
        JOptionPane.showMessageDialog(this, vr.message, "VALIDATION WARNING", JOptionPane.WARNING_MESSAGE);
    }
    
    private void resetInfoPanel() {
        
        if (btnEdit.isEnabled()) {
            lblProductID.setText("");
        
            for (Component comp : pnlContentInfo.getComponents()) {
                
                if (comp instanceof JTextField) {
                    ((JTextField) comp).setText("");
                    ((JTextField) comp).setEnabled(false);
                }
                
            }
            
            sldProfit.setValue(30);
            sldProfit.setEnabled(false);

            cmbCategory.setSelectedIndex(0);
            cmbCategory.setEnabled(false);
            
            cmbStatus.setSelectedIndex(0);
            cmbStatus.setEnabled(false);

            tbxDescription.setText("");
            tbxDescription.setEnabled(false);

            btnEdit.setText("Edit");
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
            btnCancel.setEnabled(false);
            
            lblRealID.setText("");
        }
        
    }
    
    public String getFilterString(String cond) {
        
        String filter = "";
        String categoryName;
        
        if (!"".equals(cond)) {
            filter = "(code LIKE '%"+cond+"%'"
                    + " OR name LIKE '%"+cond+"%'"
                    + " OR description LIKE '%"+cond+"%') AND ";
        }
        
        categoryName = String.valueOf(cmbCategoryFilter.getSelectedItem());
        
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
    
    private void calcProfitRate() {
        
        String cost = tbxCost.getText();
        
        if (validateStringIsDouble(cost, "").result) {
            tbxPrice.setText(DFMT_PRICE_NC.format(Double.parseDouble(cost) + (Double.parseDouble(cost)*sldProfit.getValue()/100)));
        }
        
    }
    
    
    /*==== Required Functions ====*/
    private void listCategory() {
        
        cmbCategoryFilter.removeAllItems();
        cmbCategory.removeAllItems();
        
        cmbCategoryFilter.addItem("All");
        for (Category cat : Category.listCategory()) {
            cmbCategoryFilter.addItem(cat.getName());
            cmbCategory.addItem(cat.getName());
        }
        
    }
    
    public void listContentWithFilter(){        
        String cond = tbxFilter.getText();
        String filter = getFilterString(cond);
        listContent(filter);
    }
    
    // List all user
    public void listContent(){
        listContent("");
    }
    
    // List user by filter
    public void listContent(String filter){
        
        try {
            // Clear Data
            int lastRow = tblContentList.getRowCount() - 1;
            while (lastRow > -1) {
                modelContentList.removeRow(lastRow);
                lastRow--;
            }
            
            // Add Userinfo
            int curRow = 0;
            String idx;
            for (Product product : Product.listProduct(filter, "category, name")) {
                
                try{
                    modelContentList.addRow(new  Object[0]);
                    idx = IDPF_PRODUCT + IDFMT_PRODUCT.format(product.getId());
                    modelContentList.setValueAt(idx, curRow, 0);

                    modelContentList.setValueAt(product.getCode(), curRow, 1);
                    modelContentList.setValueAt(product.getName(), curRow, 2);

                    Category category = new Category(product.getCategory());                
                    modelContentList.setValueAt(category.getName(), curRow, 3);                

                    modelContentList.setValueAt(DFMT_PRICE.format(product.getCost()), curRow, 4);
                    modelContentList.setValueAt(DFMT_PRICE.format(product.getPrice()), curRow, 5);

                    modelContentList.setValueAt(product.getDescription(), curRow, 6);
                    modelContentList.setValueAt(product.getId(), curRow, 7);
                    modelContentList.setValueAt(product.getStatus(), curRow, 8);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContentList = new javax.swing.JTable();
        pnlSearch = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel1 = new javax.swing.JLabel();
        tbxFilter = new javax.swing.JTextField();
        btnReload = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbCategoryFilter = new javax.swing.JComboBox<>();
        pnlContentInfo = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY_PORTRAIT, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tbxName = new javax.swing.JTextField();
        lblProductID = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbxDescription = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        tbxCode = new javax.swing.JTextField();
        cmbCategory = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tbxPrice = new javax.swing.JTextField();
        lblRealID = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tbxCost = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblTitle1 = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        sldProfit = new javax.swing.JSlider();
        lblProfitValue = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setTitle("PRODUCT MANAGEMENT PANEL");
        setMaximumSize(new java.awt.Dimension(2000, 1200));
        setMinimumSize(new java.awt.Dimension(1600, 750));
        setPreferredSize(new java.awt.Dimension(1600, 750));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlTable.setBackground(new java.awt.Color(102, 102, 102));

        tblContentList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblContentList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductID", "Code", "Name", "Category", "Cost", "Price", "Description", "RealID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblContentList.setRowHeight(24);
        tblContentList.setShowVerticalLines(false);
        tblContentList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblContentList);
        if (tblContentList.getColumnModel().getColumnCount() > 0) {
            tblContentList.getColumnModel().getColumn(0).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(0).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(1).setMinWidth(150);
            tblContentList.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContentList.getColumnModel().getColumn(1).setMaxWidth(150);
            tblContentList.getColumnModel().getColumn(3).setMinWidth(150);
            tblContentList.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblContentList.getColumnModel().getColumn(3).setMaxWidth(150);
            tblContentList.getColumnModel().getColumn(4).setMinWidth(100);
            tblContentList.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblContentList.getColumnModel().getColumn(4).setMaxWidth(100);
            tblContentList.getColumnModel().getColumn(5).setMinWidth(100);
            tblContentList.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblContentList.getColumnModel().getColumn(5).setMaxWidth(100);
            tblContentList.getColumnModel().getColumn(6).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(6).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(7).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(7).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(8).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        pnlSearch.setBackground(new java.awt.Color(102, 102, 102));
        pnlSearch.setMaximumSize(new java.awt.Dimension(32767, 29));
        pnlSearch.setMinimumSize(new java.awt.Dimension(0, 29));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search");

        tbxFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxFilter.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxFilter.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxFilter.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxFilter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxFilterFocusGained(evt);
            }
        });
        tbxFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbxFilterKeyReleased(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(255, 204, 0));
        btnReload.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/refresh.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.setMaximumSize(new java.awt.Dimension(100, 29));
        btnReload.setMinimumSize(new java.awt.Dimension(100, 29));
        btnReload.setPreferredSize(new java.awt.Dimension(93, 29));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Category");

        cmbCategoryFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCategoryFilter.setMaximumSize(new java.awt.Dimension(400, 29));
        cmbCategoryFilter.setMinimumSize(new java.awt.Dimension(100, 29));
        cmbCategoryFilter.setPreferredSize(new java.awt.Dimension(57, 29));
        cmbCategoryFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoryFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCategoryFilter, 0, 363, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tbxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(cmbCategoryFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addComponent(pnlSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pnlContentInfo.setBackground(new java.awt.Color(102, 102, 102));
        pnlContentInfo.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Product ID: ");

        jLabel12.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Name: ");

        tbxName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxName.setEnabled(false);
        tbxName.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxName.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxName.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxNameFocusGained(evt);
            }
        });

        lblProductID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblProductID.setForeground(new java.awt.Color(255, 255, 255));
        lblProductID.setPreferredSize(new java.awt.Dimension(24, 29));

        btnEdit.setBackground(new java.awt.Color(255, 204, 0));
        btnEdit.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(0, 204, 204));
        btnCancel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Description: ");

        btnDelete.setBackground(new java.awt.Color(255, 51, 51));
        btnDelete.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tbxDescription.setColumns(20);
        tbxDescription.setRows(5);
        tbxDescription.setEnabled(false);
        jScrollPane4.setViewportView(tbxDescription);

        jLabel14.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Code: ");

        tbxCode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCode.setEnabled(false);
        tbxCode.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxCode.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxCode.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCodeFocusGained(evt);
            }
        });

        cmbCategory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCategory.setEnabled(false);
        cmbCategory.setMaximumSize(new java.awt.Dimension(400, 29));
        cmbCategory.setMinimumSize(new java.awt.Dimension(100, 29));
        cmbCategory.setPreferredSize(new java.awt.Dimension(57, 29));

        jLabel15.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Category: ");

        jLabel16.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Price: ");

        tbxPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxPrice.setEnabled(false);
        tbxPrice.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxPrice.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxPrice.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxPriceFocusGained(evt);
            }
        });

        lblRealID.setBackground(new java.awt.Color(204, 204, 204));
        lblRealID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRealID.setEnabled(false);
        lblRealID.setFocusable(false);
        lblRealID.setName("BUFF_RealID"); // NOI18N
        lblRealID.setOpaque(true);

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 204, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Cost: ");

        tbxCost.setBackground(new java.awt.Color(255, 204, 51));
        tbxCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCost.setEnabled(false);
        tbxCost.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxCost.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxCost.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCostFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 204, 0));
        jLabel3.setText("* Sensitive data, please edit with caution!");

        pnlHeader.setBackground(new java.awt.Color(0, 102, 0));

        lblTitle1.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle1.setFont(new java.awt.Font("Tw Cen MT", 0, 28)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setText("MODIFY PRODUCT INFORMATION");

        lblIcon.setForeground(new java.awt.Color(204, 204, 204));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/product32.png"))); // NOI18N
        lblIcon.setPreferredSize(new java.awt.Dimension(36, 36));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status: ");
        jLabel9.setPreferredSize(new java.awt.Dimension(45, 20));

        cmbStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVE", "INACTIVE" }));
        cmbStatus.setEnabled(false);
        cmbStatus.setMaximumSize(new java.awt.Dimension(400, 29));
        cmbStatus.setMinimumSize(new java.awt.Dimension(100, 29));
        cmbStatus.setPreferredSize(new java.awt.Dimension(57, 29));

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Profit: ");
        jLabel10.setPreferredSize(new java.awt.Dimension(45, 20));

        sldProfit.setMajorTickSpacing(5);
        sldProfit.setMinorTickSpacing(5);
        sldProfit.setValue(30);
        sldProfit.setEnabled(false);
        sldProfit.setMaximumSize(new java.awt.Dimension(400, 29));
        sldProfit.setMinimumSize(new java.awt.Dimension(36, 29));
        sldProfit.setOpaque(false);
        sldProfit.setPreferredSize(new java.awt.Dimension(200, 29));
        sldProfit.setValueIsAdjusting(true);
        sldProfit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldProfitStateChanged(evt);
            }
        });

        lblProfitValue.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        lblProfitValue.setForeground(new java.awt.Color(255, 255, 255));
        lblProfitValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblProfitValue.setText("30%");

        javax.swing.GroupLayout pnlContentInfoLayout = new javax.swing.GroupLayout(pnlContentInfo);
        pnlContentInfo.setLayout(pnlContentInfoLayout);
        pnlContentInfoLayout.setHorizontalGroup(
            pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContentInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRealID, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContentInfoLayout.createSequentialGroup()
                        .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tbxCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbxName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbxCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4)
                            .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbxPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlContentInfoLayout.createSequentialGroup()
                                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 51, Short.MAX_VALUE))
                            .addGroup(pnlContentInfoLayout.createSequentialGroup()
                                .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContentInfoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(sldProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProfitValue, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(60, 60, 60))
        );
        pnlContentInfoLayout.setVerticalGroup(
            pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentInfoLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCost, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sldProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProfitValue, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(lblRealID, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblRealID.getAccessibleContext().setAccessibleName("BUFF_RealID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContentInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbxFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxFilterKeyReleased

        String cond = tbxFilter.getText();
        String filter = getFilterString(cond);
        resetInfoPanel();
        listContent(filter);
        
    }//GEN-LAST:event_tbxFilterKeyReleased

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed

        listContentWithFilter();
        
    }//GEN-LAST:event_btnReloadActionPerformed

    private void tbxNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxNameFocusGained
        tbxName.selectAll();
    }//GEN-LAST:event_tbxNameFocusGained

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

        if ("Edit".equals(btnEdit.getText())) {
            setFrameState(frameState.EDIT);
            tbxCode.requestFocus();
        }else{
            String code = tbxCode.getText().trim();
            String name = tbxName.getText().trim().replace("\"", "'");

            String categoryname = String.valueOf(cmbCategory.getSelectedItem());
            Category category = Category.NameToCategory(categoryname);

            String cost = tbxCost.getText().trim().replace(",", "");
            String price = tbxPrice.getText().trim().replace(",", "");
            String description = tbxDescription.getText();    

            // Validation Phase
            ValidationResult vr = new ValidationResult();

            if (category == null) {
                vr.result = false;
                vr.message = "Category cannot be EMPTY!";
                popupWarning(vr);
                return;
            }

            vr = validateProductCode(code);
            if (!vr.result) {
                popupWarning(vr);
                return;
            }

            vr = validateProductName(name);
            if (!vr.result) {
                popupWarning(vr);
                return;
            }
            
            vr = validateCost(cost);
            if (!vr.result) {
                popupWarning(vr);
                return;
            }
            
            vr = validatePrice(price);
            if (!vr.result) {
                popupWarning(vr);
                return;
            }

            Product.ProductInfo pi = new Product.ProductInfo();
            pi.id = Integer.parseInt(lblRealID.getText());
            pi.code = code;
            pi.name = name;
            pi.category = category.getId();
            pi.cost = Double.parseDouble(cost);
            pi.price = Double.parseDouble(price);
            pi.status = cmbStatus.getSelectedItem().toString();
            pi.image = "";
            pi.description = description;
            
            if (pi.price < pi.cost) {
                vr.message = "Price is LOWER than Cost!";
                vr.result = false;
                popupWarning(vr);
                return;
            }

            Product.updateProductInfo(pi);
            
            Product product = new Product(pi.id);
            
            Log.LogInfo li = new Log.LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "APPLICATION LOG";
            li.event = "MODIFY PRODUCT";            
            
            li.details = String.format(
                    """
                    {
                        "APPLICATION LOG":{
                            "LogDate":"%s",
                            "Event":"MODIFY PRODUCT",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Data":{
                                "Product":{
                                    "ID":%d,
                                    "Category":{
                                        "ID":%d,
                                        "Name":"%s"
                                    },
                                    "Code":"%s",
                                    "Name":"%s",
                                    "Cost":%s,
                                    "Price":%s,
                                    "Status":"%s"
                                }
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
                            
                            product.getId(),
                            category.getId(),
                            category.getName(),
                            product.getCode(),
                            product.getName(),
                            DFMT_PRICE_NC.format(product.getCost()),
                            DFMT_PRICE_NC.format(product.getPrice()),
                            product.getStatus()
                    )
            );            
            Log.addLog(li);

            JOptionPane.showMessageDialog(this, "Product information has been updated.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
            posForm.callFunction("listContentWithFilter");
            
            setFrameState(frameState.POST_EDIT);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        setFrameState(frameState.POST_EDIT);
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!lblRealID.getText().equals("")) {
            String name = tbxName.getText().trim();
            int productID = Integer.parseInt(lblRealID.getText());
            
            if (ReceiptDetail.isExist("product = "+productID) != null) {
                JOptionPane.showMessageDialog(this, "CANNOT delete this Product!\nProduct '"+name+"' already has some Receipt assigned to it.", "PROHIBIT", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (JOptionPane.showConfirmDialog(this, "Are you sure to permanently DELETE Product '"+name+"'?", "DELETE CONFIRMATION", JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {                
                
                Product product = new Product(productID);
                Category category = new Category(product.getCategory());
                Stock stock = Stock.isExist("product = "+product.getId());
                if (Product.deleteProduct(productID)) {                
                    
                    Log.LogInfo li = new Log.LogInfo();
                    li.id = 0;
                    li.log_date = getCurrentDateTimeFormatted();
                    li.user = CURRENT_USER.username;
                    li.category = "APPLICATION LOG";
                    li.event = "DELETE PRODUCT";
                    
                    li.details = String.format(
                    """
                    {
                        "APPLICATION LOG":{
                            "LogDate":"%s",
                            "Event":"DELETE PRODUCT",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Data":{
                                "Product":{
                                    "ID":%d,
                                    "Category":{
                                        "ID":%d,
                                        "Name":"%s"
                                    },
                                    "Code":"%s",
                                    "Name":"%s",
                                    "Cost":%s,
                                    "Price":%s,
                                    "Status":"%s"
                                },                  
                                "Stock":{
                                    "ID":%d,
                                    "Quantity":%d
                                }
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
                            
                            product.getId(),
                            category.getId(),
                            category.getName(),
                            product.getCode(),
                            product.getName(),
                            DFMT_PRICE_NC.format(product.getCost()),
                            DFMT_PRICE_NC.format(product.getPrice()),
                            product.getStatus(),
                            stock.getId(),
                            stock.getQuantity()
                    )
                    );            
                    Log.addLog(li);
                    
                    JOptionPane.showMessageDialog(this, "Product 'Name: "+product.getName()+"' has been DELETED.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Product 'Name: "+product.getName()+"' deletion failed!.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                setFrameState(frameState.POST_EDIT);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbxCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusGained
        tbxCode.selectAll();
    }//GEN-LAST:event_tbxCodeFocusGained

    private void tbxPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxPriceFocusGained
        tbxPrice.selectAll();
    }//GEN-LAST:event_tbxPriceFocusGained

    private void cmbCategoryFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryFilterActionPerformed
        
        resetInfoPanel();
        listContentWithFilter();
        
    }//GEN-LAST:event_cmbCategoryFilterActionPerformed

    private void tbxFilterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxFilterFocusGained
        
        tbxFilter.selectAll();
        
    }//GEN-LAST:event_tbxFilterFocusGained

    private void tbxCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCostFocusGained
        tbxCost.selectAll();
    }//GEN-LAST:event_tbxCostFocusGained

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown

    private void sldProfitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldProfitStateChanged

        lblProfitValue.setText(String.valueOf(sldProfit.getValue())+"%");

        calcProfitRate();

    }//GEN-LAST:event_sldProfitStateChanged

    private void tbxCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCostFocusLost
        
        calcProfitRate();
        
    }//GEN-LAST:event_tbxCostFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.JComboBox<String> cmbCategory;
    public static javax.swing.JComboBox<String> cmbCategoryFilter;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblIcon;
    public javax.swing.JLabel lblProductID;
    private javax.swing.JLabel lblProfitValue;
    private javax.swing.JLabel lblRealID;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JPanel pnlContentInfo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JSlider sldProfit;
    public static javax.swing.JTable tblContentList;
    public javax.swing.JTextField tbxCode;
    public javax.swing.JTextField tbxCost;
    private javax.swing.JTextArea tbxDescription;
    public static javax.swing.JTextField tbxFilter;
    public javax.swing.JTextField tbxName;
    public javax.swing.JTextField tbxPrice;
    // End of variables declaration//GEN-END:variables
}
