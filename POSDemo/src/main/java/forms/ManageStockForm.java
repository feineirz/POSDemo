/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Product;
import DBCLS.Stock;
import static GLOBAL.HelperFunctions.*;
import static GLOBAL.Settings.BG_DARK_ALT;
import static GLOBAL.Settings.MAIN_FONT;
import GLOBAL.Validator;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
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
public class ManageStockForm extends javax.swing.JInternalFrame {

    public static DefaultTableModel modelContentList;
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    
    /**
     * Creates new form ManageStockForm
     */
    public ManageStockForm() {
        
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        getContentPane().setBackground(BG_DARK_ALT);
        
        modelContentList = (DefaultTableModel)tblContentList.getModel();
        tblContentList.getTableHeader().setFont(MAIN_FONT);
        
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblContentList.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblContentList.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        tblContentList.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        
        tblContentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        lblRealID.setVisible(false);
        
        tblContentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int curRow = tblContentList.getSelectedRow();
                if(curRow > -1){
                    lblStockID.setText(tblContentList.getValueAt(curRow, 0).toString());
                    lblCode.setText(tblContentList.getValueAt(curRow, 1).toString());
                    lblPrice.setText(tblContentList.getValueAt(curRow, 4).toString());
                    tbxQuantity.setText(tblContentList.getValueAt(curRow, 5).toString());
                    tbxRemark.setText(tblContentList.getValueAt(curRow, 7).toString());
                    lblRealID.setText(tblContentList.getValueAt(curRow, 8).toString());
                    setFrameState(frameState.SELECTED);
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

                listCategory();
                listContent();
                resetInfoPanel();			
            }
            
            case FILTERED -> {
                tblContentList.setEnabled(true);
                tblContentList.clearSelection();

                resetInfoPanel();
            }
            
            case EDIT -> {
                tbxFilter.setEnabled(false);
                cmbCategoryFilter.setEnabled(false);
                btnReload.setEnabled(false);

                tblContentList.setEnabled(false);

                tbxQuantity.setEnabled(true);
                tbxRemark.setEnabled(true);

                btnEdit.setText("Update");
                btnDelete.setEnabled(false);
                btnCancel.setEnabled(true);
            }
            
            case SELECTED -> {
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
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
                callUpdateStockData();
            }
        }
    }
    
    /*==== Helper Functions ====*/
    public void popupWarning(Validator.InputValidation.ValidationResult vr) {
        JOptionPane.showMessageDialog(this, vr.message, "VALIDATION WARNING", JOptionPane.WARNING_MESSAGE);
    }	
    
    private void resetInfoPanel() {
        
        if (btnEdit.isEnabled()) {
            lblStockID.setText("");
            lblCode.setText("");
            lblPrice.setText("");
            tbxQuantity.setEnabled(false);
            tbxQuantity.setText("");
            tbxRemark.setEnabled(false);
            tbxRemark.setText("");            
            
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
            filter = "(product.code LIKE '%"+cond+"%'"
                    + " OR product.name LIKE '%"+cond+"%') AND ";
        }
        
        categoryName = String.valueOf(cmbCategoryFilter.getSelectedItem());
        
        if (categoryName.equals("All")) {
            filter += "product.category LIKE '%'";            
        } else {
            Category category = Category.NameToID(categoryName);
            if (category != null) {
                filter += "product.category = " + category.getId();
            } else {
                filter += "product.category LIKE '%'";
            }
        }
        
        return filter;
        
    }
    
    /*==== Required Functions ====*/    
    private void listCategory(){
        
        cmbCategoryFilter.removeAllItems();
        cmbCategoryFilter.addItem("All");
        Category.listCategory().forEach(cat -> {
            cmbCategoryFilter.addItem(cat.getName());
        });
        
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
        
        Product product;
        Category category;
        
        try {
            // Clear Data
            int lastRow = tblContentList.getRowCount() - 1;
            while (lastRow > -1) {
                modelContentList.removeRow(lastRow);
                lastRow--;
            }
            
            // Add Info
            int curRow = 0;
            Double price, total;
            int quantity;
            String idx;
            for (Stock stock : Stock.listStockJoinProduct(filter, "product.category, product.name")) {
                try{
                    modelContentList.addRow(new  Object[0]);

                    idx = IDPF_STOCK + IDFMT_STOCK.format(stock.getId());
                    modelContentList.setValueAt(idx, curRow, 0);
                    
                    product = new Product(stock.getProduct());
                    category = new Category(product.getCategory());
                    
                    modelContentList.setValueAt(product.getCode(), curRow, 1);
                    modelContentList.setValueAt(product.getName(), curRow, 2);
                    modelContentList.setValueAt(category.getName(), curRow, 3);
                    
                    price = (double) product.getPrice();
                    quantity = (int) stock.getQuantity();
                    total = price*quantity;
                    
                    modelContentList.setValueAt(DFMT_PRICE.format(price), curRow, 4);
                    modelContentList.setValueAt(DFMT_QUANTITY.format(quantity), curRow, 5);
                    
                    modelContentList.setValueAt(DFMT_PRICE.format(total), curRow, 6);
                    modelContentList.setValueAt(stock.getRemark(), curRow, 7);
                    
                    modelContentList.setValueAt(stock.getId(), curRow, 8);

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

        pnlSearch = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tbxFilter = new javax.swing.JTextField();
        btnReload = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbCategoryFilter = new javax.swing.JComboBox<>();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContentList = new javax.swing.JTable();
        pnlInfoPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblStockID = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        lblRealID = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbxRemark = new javax.swing.JTextArea();
        lblCode = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tbxQuantity = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel();
        lblTitle1 = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setResizable(true);
        setTitle("STOCK MANAGEMENT PANEL");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlSearch.setBackground(new java.awt.Color(102, 102, 102));
        pnlSearch.setMaximumSize(new java.awt.Dimension(32767, 29));
        pnlSearch.setMinimumSize(new java.awt.Dimension(0, 29));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search");

        tbxFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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

        btnReload.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Category");

        cmbCategoryFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCategoryFilter.setMaximumSize(new java.awt.Dimension(32767, 23));
        cmbCategoryFilter.setMinimumSize(new java.awt.Dimension(34, 23));
        cmbCategoryFilter.setPreferredSize(new java.awt.Dimension(57, 23));
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
                .addComponent(tbxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCategoryFilter, 0, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReload)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cmbCategoryFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTable.setBackground(new java.awt.Color(102, 102, 102));

        tblContentList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblContentList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "StockID", "Code", "Name", "Category", "UnitPrice", "Quantity", "Total", "Description", "RealID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
            tblContentList.getColumnModel().getColumn(1).setMinWidth(120);
            tblContentList.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblContentList.getColumnModel().getColumn(1).setMaxWidth(120);
            tblContentList.getColumnModel().getColumn(3).setMinWidth(120);
            tblContentList.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblContentList.getColumnModel().getColumn(3).setMaxWidth(120);
            tblContentList.getColumnModel().getColumn(4).setMinWidth(80);
            tblContentList.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblContentList.getColumnModel().getColumn(4).setMaxWidth(80);
            tblContentList.getColumnModel().getColumn(5).setMinWidth(70);
            tblContentList.getColumnModel().getColumn(5).setPreferredWidth(70);
            tblContentList.getColumnModel().getColumn(5).setMaxWidth(70);
            tblContentList.getColumnModel().getColumn(6).setMinWidth(100);
            tblContentList.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblContentList.getColumnModel().getColumn(6).setMaxWidth(100);
            tblContentList.getColumnModel().getColumn(7).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(7).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(8).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlInfoPanel.setBackground(new java.awt.Color(102, 102, 102));
        pnlInfoPanel.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("StockD: ");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Code: ");

        lblStockID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblStockID.setForeground(new java.awt.Color(255, 255, 255));
        lblStockID.setPreferredSize(new java.awt.Dimension(24, 29));

        btnEdit.setBackground(new java.awt.Color(255, 204, 0));
        btnEdit.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(0, 204, 204));
        btnCancel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Remark: ");

        btnDelete.setBackground(new java.awt.Color(255, 51, 51));
        btnDelete.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblRealID.setBackground(new java.awt.Color(204, 204, 204));
        lblRealID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRealID.setEnabled(false);
        lblRealID.setFocusable(false);
        lblRealID.setOpaque(true);

        tbxRemark.setColumns(20);
        tbxRemark.setRows(5);
        tbxRemark.setEnabled(false);
        jScrollPane2.setViewportView(tbxRemark);

        lblCode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCode.setForeground(new java.awt.Color(255, 255, 255));
        lblCode.setPreferredSize(new java.awt.Dimension(24, 29));

        lblPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPrice.setPreferredSize(new java.awt.Dimension(24, 29));

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("UnitPrice: ");

        tbxQuantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxQuantity.setEnabled(false);
        tbxQuantity.setMaximumSize(new java.awt.Dimension(2147483647, 27));
        tbxQuantity.setMinimumSize(new java.awt.Dimension(7, 27));
        tbxQuantity.setPreferredSize(new java.awt.Dimension(73, 27));
        tbxQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusGained(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Quantity: ");

        pnlHeader.setBackground(new java.awt.Color(153, 51, 0));

        lblTitle1.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle1.setFont(new java.awt.Font("Tw Cen MT", 0, 28)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setText("MODIFY STOCK INFORMATION");

        lblIcon.setForeground(new java.awt.Color(204, 204, 204));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stock32.png"))); // NOI18N
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

        javax.swing.GroupLayout pnlInfoPanelLayout = new javax.swing.GroupLayout(pnlInfoPanel);
        pnlInfoPanel.setLayout(pnlInfoPanelLayout);
        pnlInfoPanelLayout.setHorizontalGroup(
            pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                        .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                        .addComponent(lblRealID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(24, 24, 24))
                    .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                        .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStockID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbxQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                        .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                                .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancel))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 32, Short.MAX_VALUE))))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlInfoPanelLayout.setVerticalGroup(
            pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoPanelLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCode, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(31, 31, 31)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRealID, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbxFilterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxFilterFocusGained

        tbxFilter.selectAll();

    }//GEN-LAST:event_tbxFilterFocusGained

    private void tbxFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxFilterKeyReleased

        String cond = tbxFilter.getText();
        String filter = getFilterString(cond);
        resetInfoPanel();
        listContent(filter);

    }//GEN-LAST:event_tbxFilterKeyReleased

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed

        listContentWithFilter();

    }//GEN-LAST:event_btnReloadActionPerformed

    private void cmbCategoryFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryFilterActionPerformed

        resetInfoPanel();
        listContentWithFilter();

    }//GEN-LAST:event_cmbCategoryFilterActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

        if ("Edit".equals(btnEdit.getText())) {
            setFrameState(frameState.EDIT);
            tbxQuantity.requestFocus();
        }else{
            
            String id = lblRealID.getText();
            String quantity = tbxQuantity.getText().trim();
            String remark = tbxRemark.getText().trim();
            
            // Validate data
            ValidationResult vr = new ValidationResult();
            String errMessage;
            
            errMessage = "Wrong 'Quantity' format!";
            vr = validateStringIsInteger(quantity, errMessage);
            if (!vr.result) {
                popupWarning(vr);
                return;
            }

            Stock stock = new Stock(Integer.parseInt(lblRealID.getText()));
            stock.setQuantity(Integer.parseInt(quantity));
            stock.setRemark(remark);
            
            Product product = new Product(stock.getProduct());
            Category category = new Category(product.getCategory());
            
            Log.LogInfo li = new Log.LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "APPLICATION LOG";
            li.event = "MODIFY STOCK";
            li.details = "User '"+CURRENT_USER.username+"' (User Level: "+getUserLevel(CURRENT_USER.level)+") \n"
                    + "MODIFY STOCK\n"
                    + " Product[\n"
                    + "  ID: "+product.getId()+",\n"
                    + "  Category: ["+category.getId()+"]"+category.getName()+",\n"            
                    + "  Code: "+product.getCode()+",\n"
                    + "  Name: "+product.getName()+",\n"
                    + "  Quantity: "+quantity+",\n"
                    + "  New Quantity: "+stock.getQuantity()+",\n"
                    + "  Result: SUCCESS\n"
                    + " ]";
            Log.addLog(li);

            JOptionPane.showMessageDialog(this, "Category information has been updated.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
            setFrameState(frameState.POST_EDIT);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        setFrameState(frameState.POST_EDIT);
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!lblRealID.getText().equals("")) {
            String code = lblCode.getText();
            if (JOptionPane.showConfirmDialog(this, "Are you sure to permanently DELETE '"+code+"' from stock?", "DELETE CONFIRMATION", JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
                
                int stockID = Integer.parseInt(lblRealID.getText());
                Stock stock = new Stock(stockID);
                if (Stock.deleteStock(stockID)) {
                    
                    Product product = new Product(stock.getProduct());
                    Category category = new Category(product.getCategory());
            
                    Log.LogInfo li = new Log.LogInfo();
                    li.id = 0;
                    li.log_date = getCurrentDateTimeFormatted();
                    li.user = CURRENT_USER.username;
                    li.category = "APPLICATION LOG";
                    li.event = "DELETE STOCK";
                    li.details = "User '"+CURRENT_USER.username+"' (User Level: "+getUserLevel(CURRENT_USER.level)+") \n"
                            + "DELETE STOCK\n"
                            + " Stock[\n"
                            + "  ID: "+stock.getId()+"\n"
                            + " ]\n"
                            + " Product[\n"
                            + "  ID: "+product.getId()+",\n"
                            + "  Category: ["+category.getId()+"]"+category.getName()+",\n"            
                            + "  Code: "+product.getCode()+",\n"
                            + "  Name: "+product.getName()+",\n"
                            + "  Quantity: "+stock.getQuantity()+",\n"
                            + "  Result: SUCCESS\n"
                            + " ]";
                    Log.addLog(li);
                    
                    JOptionPane.showMessageDialog(this, "Stock 'StockID: "+stock.getId()+"' has been DELETED.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Stock 'StockID: "+stock.getId()+"' deletion failed!.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                setFrameState(frameState.POST_EDIT);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbxQuantityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusGained
        
        tbxQuantity.selectAll();
        
    }//GEN-LAST:event_tbxQuantityFocusGained

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    public static javax.swing.JComboBox<String> cmbCategoryFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblIcon;
    public javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblRealID;
    public javax.swing.JLabel lblStockID;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlInfoPanel;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTable;
    public static javax.swing.JTable tblContentList;
    public static javax.swing.JTextField tbxFilter;
    private javax.swing.JTextField tbxQuantity;
    private javax.swing.JTextArea tbxRemark;
    // End of variables declaration//GEN-END:variables
}
