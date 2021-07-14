/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Product;
import DBCLS.Receipt;
import DBCLS.Receipt.ReceiptInfo;
import DBCLS.ReceiptDetail;
import DBCLS.ReceiptDetail.ReceiptDetailInfo;
import DBCLS.Stock;
import static GLOBAL.HelperFunctions.*;
import static GLOBAL.Settings.BG_DARK_ALT;
import static GLOBAL.Settings.MAIN_FONT;
import GLOBAL.Validator;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static pos.Apps.cashReceiptForm;

/**
 *
 * @author feinz
 */
public class POSForm extends javax.swing.JInternalFrame {

    public static DefaultTableModel modelContentList, modelCartList;
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    
    /**
     * Creates new form POSForm
     */
    public POSForm() {
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        getContentPane().setBackground(BG_DARK_ALT);
        
        modelContentList = (DefaultTableModel)tblContentList.getModel();
        modelCartList = (DefaultTableModel)tblCartList.getModel();
        tblContentList.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        tblCartList.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblContentList.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // price
        tblContentList.getColumnModel().getColumn(6).setCellRenderer(rightRenderer); // cost
        
        tblCartList.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        tblCartList.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblCartList.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblCartList.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        
        tblContentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        lblCurrentProductID.setVisible(false);
        lblCartCurrentRow.setVisible(false);
        
        tblContentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int curRow = tblContentList.getSelectedRow();
                if(curRow > -1){
                    tbxCode.setText(tblContentList.getValueAt(curRow, 0).toString());
                    String curProductID = String.valueOf(tblContentList.getValueAt(curRow, 5).toString());
                    lblCurrentProductID.setText(curProductID);
                }
                setFrameState(frameState.SELECT_PRODUCT);
                
            }            
        });
        
        tblCartList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int curRow = tblCartList.getSelectedRow();
                if(curRow > -1){
                    autoSelectProductList(tbxCode.getText());
                    
                    tbxCode.setText(tblCartList.getValueAt(curRow, 5).toString());
                    tbxQuantity.setText(tblCartList.getValueAt(curRow, 1).toString());
                    btnAddToCart.setText("Update");

                    lblCurrentProductID.setText(tblCartList.getValueAt(curRow, 4).toString());
                    lblCartCurrentRow.setText(String.valueOf(curRow));                    
                }

                tbxQuantity.requestFocus();
                tbxQuantity.selectAll();
            }            
        });
        
        listCategory();
        listContentWithFilter();
        
        updateReceiptID();
        tbxCode.requestFocus();
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
        INIT, FILTERED, SELECT_PRODUCT, EDIT, POST_EDIT, POST_CHECKOUT
    }
    
    private void initForm() {
        setFrameState(frameState.INIT);
    }
    
    private void setFrameState(frameState framState) {
        switch(framState) {
            case INIT -> {
                tbxFilter.setText("");
            
                tblContentList.clearSelection();

                tbxCode.setText("");
                tbxQuantity.setText("1");
                btnAddToCart.setText("Add To Cart");
                clearTable(tblCartList);
                lblCartCurrentRow.setText("");
                lblCurrentProductID.setText("");

                lblTotalPrice.setText("0.00");
                tbxCashIn.setText("0.00");
                tbxExchange.setText("0.00");

                btnCheckout.setEnabled(false);

                listContent();
                resetInfoPanel();   
            }
            
            case SELECT_PRODUCT -> {
                btnAddToCart.setText("Add To Cart");
                tbxQuantity.setText("1");
                tbxQuantity.selectAll();

                if (!tbxFilter.isFocusOwner()) tbxQuantity.requestFocus();            

                tbxExchange.setText("0.00");
                btnCheckout.setEnabled(false);
            }
                
            case POST_EDIT -> {
                
            }
            
            case POST_CHECKOUT -> {
                setFrameState(frameState.INIT);
                
                callUpdateStockData();
                callUpdateReceiptData();
                
            }
        }
    }
    
    /*==== Helper Functions ====*/	
    private void clearTable(JTable tbl) {
        
        int lastRow = tbl.getRowCount() - 1;
        while (lastRow > -1) {
            ((DefaultTableModel)tbl.getModel()).removeRow(lastRow);
            lastRow--;
        }
        
    }
    
    private void autoSelectProductList(String code) {
        
        cmbCategoryFilter.setSelectedIndex(0);
        String pcode;
        for (int i = 0; i < tblContentList.getRowCount(); i++) {                
            pcode = tblContentList.getValueAt(i, 0).toString();
            if (pcode.equals(code)) {
                tblContentList.setRowSelectionInterval(i, i);
                break;
            }
        }
                
    }
    
    private void resetInfoPanel() {
        
        tblContentList.clearSelection();        
        
    }
    
    public void popupWarning(Validator.InputValidation.ValidationResult vr) {
        JOptionPane.showMessageDialog(this, vr.message, "VALIDATION WARNING", JOptionPane.WARNING_MESSAGE);
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
            Category category = Category.NameToID(categoryName);
            if (category != null) {
                filter += "category = " + category.getId();
            } else {
                filter += "category LIKE '%'";
            }
        }
        
        return filter;
        
    }
    
    private void resetCartPanel() {
        
        tbxCode.setText("");
        tbxQuantity.setText("1");
        btnAddToCart.setText("Add To Cart");
        tblCartList.clearSelection();
        
        lblCurrentProductID.setText("");
        lblCartCurrentRow.setText("");
        
    }
    
    private void resetForm() {
        
        listCategory();
        listContentWithFilter();
        resetInfoPanel();
        
    }
    
    private int getExistProductIDRowNo(String productID) {
        
        String existedID;
        for (int i = 0; i < tblCartList.getRowCount(); i++) {
            existedID = tblCartList.getValueAt(i, 4).toString();
            if (existedID.equals(productID)) {
                return i;
            }
        }
        
        return -1;
        
    }
    
    /*==== Required Functions ====*/
    private void updateReceiptID() {
        lblReceiptID.setText(IDPF_RECEIPT+IDFMT_RECEIPT.format(Receipt.getNextID()));
        
    }
    
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
    
    public void listContent(){
        listContent("");
    }
    
    public void listContent(String filter){
        Category category;
        
        try {
            clearTable(tblContentList);
            
            int curRow = 0;
            for (Product product : Product.listProduct(filter, "name")) {
                try{
                    modelContentList.addRow(new  Object[0]);

                    modelContentList.setValueAt(product.getCode(), curRow, 0);
                    modelContentList.setValueAt(product.getName(), curRow, 1);
                    
                    category = new Category((int) product.getCategory());
                    modelContentList.setValueAt(category.getName(), curRow, 2);

                    modelContentList.setValueAt(DFMT_PRICE.format(product.getPrice()), curRow, 3);
                    
                    modelContentList.setValueAt(product.getDescription(), curRow, 4);
                    modelContentList.setValueAt(product.getId(), curRow, 5);
                    modelContentList.setValueAt(DFMT_PRICE.format(product.getCost()), curRow, 6);

                    curRow++;
                }catch(Exception e){
                    
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }    
    
    private void calculateCartTotal() {
        
        Double grandTotal = 0.0;
        
        // Remove if Qty < 1        
        for (int i = 0; i < tblCartList.getRowCount(); i++) {
            
            Integer quantity = Integer.parseInt(tblCartList.getValueAt(i, 1).toString());
            Double price = Double.parseDouble(tblCartList.getValueAt(i, 2).toString());
            Double total = quantity*price;
            
            grandTotal += total;
            modelCartList.setValueAt(DFMT_PRICE.format(total), i, 3);
        }
        
        lblTotalPrice.setText(DFMT_PRICE.format(grandTotal));
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
        pnlCheckout = new javax.swing.JPanel();
        lblTotalPrice = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tbxCashIn = new javax.swing.JTextField();
        tbxExchange = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnCheckout = new javax.swing.JButton();
        lblCurrentProductID = new javax.swing.JLabel();
        lblCartCurrentRow = new javax.swing.JLabel();
        btnCalcExchange = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        pnlCart = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCartList = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        tbxCode = new javax.swing.JTextField();
        btnAddToCart = new javax.swing.JButton();
        tbxQuantity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnRemoe = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblReceiptID = new javax.swing.JLabel();
        btnClearCart = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setMaximumSize(new java.awt.Dimension(1800, 920));
        setMinimumSize(new java.awt.Dimension(1600, 820));
        setPreferredSize(new java.awt.Dimension(1590, 820));
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
        tbxFilter.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        tbxFilter.setMinimumSize(new java.awt.Dimension(100, 23));
        tbxFilter.setPreferredSize(new java.awt.Dimension(7, 27));
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
        btnReload.setText("Reload");
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setMaximumSize(new java.awt.Dimension(73, 23));
        btnReload.setMinimumSize(new java.awt.Dimension(73, 23));
        btnReload.setPreferredSize(new java.awt.Dimension(73, 26));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Category");

        cmbCategoryFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCategoryFilter.setMaximumSize(new java.awt.Dimension(1000, 25));
        cmbCategoryFilter.setMinimumSize(new java.awt.Dimension(254, 25));
        cmbCategoryFilter.setPreferredSize(new java.awt.Dimension(254, 25));
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
                .addComponent(tbxFilter, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCategoryFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tbxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(cmbCategoryFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlTable.setBackground(new java.awt.Color(102, 102, 102));

        tblContentList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblContentList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Category", "UnitPrice", "Description", "RealID", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblContentList.setFocusable(false);
        tblContentList.setRowHeight(24);
        tblContentList.setShowVerticalLines(false);
        tblContentList.getTableHeader().setReorderingAllowed(false);
        tblContentList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblContentListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblContentList);
        if (tblContentList.getColumnModel().getColumnCount() > 0) {
            tblContentList.getColumnModel().getColumn(0).setMinWidth(120);
            tblContentList.getColumnModel().getColumn(0).setPreferredWidth(120);
            tblContentList.getColumnModel().getColumn(0).setMaxWidth(120);
            tblContentList.getColumnModel().getColumn(2).setMinWidth(90);
            tblContentList.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblContentList.getColumnModel().getColumn(2).setMaxWidth(90);
            tblContentList.getColumnModel().getColumn(3).setMinWidth(70);
            tblContentList.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblContentList.getColumnModel().getColumn(3).setMaxWidth(70);
            tblContentList.getColumnModel().getColumn(4).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(4).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(5).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(5).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(6).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(6).setMaxWidth(0);
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCheckout.setBackground(new java.awt.Color(102, 102, 102));
        pnlCheckout.setForeground(new java.awt.Color(255, 255, 255));

        lblTotalPrice.setBackground(new java.awt.Color(51, 51, 51));
        lblTotalPrice.setFont(new java.awt.Font("Digital-7 Mono", 0, 72)); // NOI18N
        lblTotalPrice.setForeground(new java.awt.Color(153, 255, 102));
        lblTotalPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPrice.setText("0.00");
        lblTotalPrice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTotalPrice.setOpaque(true);
        lblTotalPrice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblTotalPricePropertyChange(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Cash: ");

        tbxCashIn.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        tbxCashIn.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tbxCashIn.setText("0.00");
        tbxCashIn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCashInFocusGained(evt);
            }
        });
        tbxCashIn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbxCashInPropertyChange(evt);
            }
        });
        tbxCashIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbxCashInKeyPressed(evt);
            }
        });

        tbxExchange.setEditable(false);
        tbxExchange.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        tbxExchange.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tbxExchange.setText("0.00");

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Exchange: ");

        btnCheckout.setBackground(new java.awt.Color(51, 204, 0));
        btnCheckout.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        btnCheckout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pos16.png"))); // NOI18N
        btnCheckout.setText("Checkout");
        btnCheckout.setEnabled(false);
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        lblCurrentProductID.setBackground(new java.awt.Color(204, 204, 204));
        lblCurrentProductID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCurrentProductID.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCurrentProductID.setOpaque(true);

        lblCartCurrentRow.setBackground(new java.awt.Color(204, 204, 204));
        lblCartCurrentRow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCartCurrentRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCartCurrentRow.setOpaque(true);

        btnCalcExchange.setBackground(new java.awt.Color(51, 204, 255));
        btnCalcExchange.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCalcExchange.setText("Calc Exchange");
        btnCalcExchange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcExchangeActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 153, 0));

        jLabel19.setBackground(new java.awt.Color(0, 102, 153));
        jLabel19.setFont(new java.awt.Font("Tw Cen MT", 1, 28)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("CHECKOUT");

        jLabel20.setBackground(new java.awt.Color(204, 204, 204));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pos32.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pnlCheckoutLayout = new javax.swing.GroupLayout(pnlCheckout);
        pnlCheckout.setLayout(pnlCheckoutLayout);
        pnlCheckoutLayout.setHorizontalGroup(
            pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCheckoutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCheckoutLayout.createSequentialGroup()
                        .addGroup(pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tbxCashIn)
                            .addComponent(lblCurrentProductID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCalcExchange, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(tbxExchange))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCheckoutLayout.createSequentialGroup()
                        .addComponent(lblCartCurrentRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlCheckoutLayout.setVerticalGroup(
            pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCheckoutLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCashIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxExchange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCalcExchange, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(413, 413, 413)
                .addComponent(lblCartCurrentRow, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(lblCurrentProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlCart.setBackground(new java.awt.Color(102, 102, 102));

        tblCartList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Qty", "Price", "Total", "RealID", "Code", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCartList.setFocusable(false);
        tblCartList.setRowHeight(24);
        tblCartList.setShowVerticalLines(false);
        tblCartList.getTableHeader().setReorderingAllowed(false);
        tblCartList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCartListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCartList);
        if (tblCartList.getColumnModel().getColumnCount() > 0) {
            tblCartList.getColumnModel().getColumn(1).setMinWidth(50);
            tblCartList.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblCartList.getColumnModel().getColumn(1).setMaxWidth(50);
            tblCartList.getColumnModel().getColumn(2).setMinWidth(70);
            tblCartList.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblCartList.getColumnModel().getColumn(2).setMaxWidth(70);
            tblCartList.getColumnModel().getColumn(3).setMinWidth(90);
            tblCartList.getColumnModel().getColumn(3).setPreferredWidth(90);
            tblCartList.getColumnModel().getColumn(3).setMaxWidth(90);
            tblCartList.getColumnModel().getColumn(4).setMinWidth(0);
            tblCartList.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblCartList.getColumnModel().getColumn(4).setMaxWidth(0);
            tblCartList.getColumnModel().getColumn(5).setMinWidth(0);
            tblCartList.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblCartList.getColumnModel().getColumn(5).setMaxWidth(0);
            tblCartList.getColumnModel().getColumn(6).setMinWidth(0);
            tblCartList.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblCartList.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Code: ");

        tbxCode.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        tbxCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tbxCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCodeFocusLost(evt);
            }
        });
        tbxCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbxCodeKeyReleased(evt);
            }
        });

        btnAddToCart.setBackground(new java.awt.Color(102, 204, 255));
        btnAddToCart.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        btnAddToCart.setText("Add to cart");
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        tbxQuantity.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        tbxQuantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tbxQuantity.setText("1");
        tbxQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusLost(evt);
            }
        });
        tbxQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbxQuantityKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("X");

        btnRemoe.setBackground(new java.awt.Color(255, 51, 51));
        btnRemoe.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnRemoe.setText("Remove");
        btnRemoe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoeActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 2, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("* Click on item to Edit or Remove");

        lblReceiptID.setBackground(new java.awt.Color(130, 130, 130));
        lblReceiptID.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        lblReceiptID.setForeground(new java.awt.Color(255, 255, 255));
        lblReceiptID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReceiptID.setOpaque(true);

        btnClearCart.setBackground(new java.awt.Color(255, 204, 0));
        btnClearCart.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnClearCart.setText("Clear");
        btnClearCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCartActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cart32.png"))); // NOI18N

        jLabel3.setBackground(new java.awt.Color(0, 102, 153));
        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SHOPPING CART");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout pnlCartLayout = new javax.swing.GroupLayout(pnlCart);
        pnlCart.setLayout(pnlCartLayout);
        pnlCartLayout.setHorizontalGroup(
            pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblReceiptID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCartLayout.createSequentialGroup()
                        .addGap(0, 17, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbxCode, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCartLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(89, 89, 89)
                        .addComponent(btnClearCart, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoe)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlCartLayout.setVerticalGroup(
            pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCartLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReceiptID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tbxCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(tbxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRemoe, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClearCart, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGap(20, 20, 20))
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
                .addComponent(pnlCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

    private void tbxCashInFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCashInFocusGained
        
        tbxCashIn.selectAll();
        
    }//GEN-LAST:event_tbxCashInFocusGained

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
                
        Double total = Double.parseDouble(lblTotalPrice.getText());
        Double cash = Double.parseDouble(tbxCashIn.getText());
        Double exchange = Double.parseDouble(tbxExchange.getText());
        
        ReceiptInfo ri = new ReceiptInfo();
        ri.id = 0;
        ri.receipt_date = getCurrentDateTimeFormatted();
        ri.cost = 0.0;
        ri.total = total;
        ri.cash = cash;
        ri.exchange = exchange;
        ri.cashier = CURRENT_USER.username;
        ri.remark = "";
        
        Receipt receipt = Receipt.addReceipt(ri);
        if (receipt != null) {
            ReceiptDetailInfo rdi = new ReceiptDetailInfo();
            Double totalCost = 0.0;
            for (int i = 0; i < tblCartList.getRowCount(); i++) {
                rdi.id = 0;
                rdi.receipt = receipt.getId();
                rdi.product = Integer.parseInt(modelCartList.getValueAt(i, 4).toString());
                rdi.current_cost = Double.parseDouble(modelCartList.getValueAt(i, 6).toString());
                rdi.current_price = Double.parseDouble(modelCartList.getValueAt(i, 2).toString());
                rdi.quantity = Integer.parseInt(modelCartList.getValueAt(i, 1).toString());
                ReceiptDetail receiptDetail = ReceiptDetail.addReceiptDetail(rdi);
                
                // Update Stock
                if (receiptDetail != null) {
                    Stock stock = Stock.isExist("product='"+rdi.product+"'");
                    stock.setQuantity(stock.getQuantity() - rdi.quantity);
                }
                
                totalCost += Double.parseDouble(modelCartList.getValueAt(i, 6).toString()) * rdi.quantity;
            
            }
            receipt.setCost(totalCost);
            CURRENT_CASHRECEIPT_ID = receipt.getId();
            
            Log.LogInfo li = new Log.LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "APPLICATION LOG";
            li.event = "ADD NEW RECEIPT";            
            li.details = "User '"+CURRENT_USER.username+"' (User Level: "+getUserLevel(CURRENT_USER.level)+") \n"
                    + "ADD RECEIPT\n"
                    + " Receipt[\n"
                    + "  ID: "+receipt.getId()+",\n"
                    + "  Recept Date: "+receipt.getReceipt_date()+",\n"
                    + "  Cost: "+DFMT_PRICE.format(receipt.getCost())+",\n"
                    + "  Total: "+DFMT_PRICE.format(receipt.getTotal())+",\n"
                    + "  Cash: "+DFMT_PRICE.format(receipt.getCash())+",\n"
                    + "  Exchange: "+DFMT_PRICE.format(receipt.getExchange())+",\n"
                    + "  Cashier: "+receipt.getCashier()+",\n"
                    + "  Result: SUCCESS\n"
                    + " ]";
            Log.addLog(li);
            
            setMarginHeight(getToolkit().getScreenSize(), cashReceiptForm, MARGIN_LARGE);
            cashReceiptForm.show();
            cashReceiptForm.toFront();
            cashReceiptForm.callFunction("loadCashReceipt");
            
            setFrameState(frameState.POST_CHECKOUT);
            updateReceiptID();
            
        } else {
            JOptionPane.showMessageDialog(this, "Unable to add new receipt data!", "ERROR.", JOptionPane.ERROR);
        }           
        
        
        
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void tblContentListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblContentListMouseClicked
        
    }//GEN-LAST:event_tblContentListMouseClicked

    private void btnCalcExchangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcExchangeActionPerformed
                
        tbxExchange.setText("0.00");
        
        String grandTotal = lblTotalPrice.getText();
        String cash = tbxCashIn.getText().trim();

        ValidationResult vr = new ValidationResult();
        String errMessage;

        errMessage = "Invalid 'Cash' format!";
        vr = validateStringIsDouble(cash, errMessage);
        if (!vr.result) {
            popupWarning(vr);
            tbxCashIn.setText("0.00");
            tbxCashIn.requestFocus();
            return;
        }

        Double dGrandTotal = Double.parseDouble(grandTotal);
        Double dCash = Double.parseDouble(cash);
        
        if (dGrandTotal <= 0) return;

        if (dCash < dGrandTotal) {
            vr.result = false;
            vr.message = "Not enough CASH!";
            popupWarning(vr);
            tbxCashIn.setText("0.00");
            tbxCashIn.requestFocus();
            return;
        }

        Double exchange = dCash-dGrandTotal;

        tbxCashIn.setText(DFMT_PRICE.format(dCash));

        String dfExchange = DFMT_PRICE.format(exchange);
        tbxExchange.setText(dfExchange);

        btnCheckout.setEnabled(true);
        btnCheckout.requestFocus();
        
    }//GEN-LAST:event_btnCalcExchangeActionPerformed

    private void lblTotalPricePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblTotalPricePropertyChange

    }//GEN-LAST:event_lblTotalPricePropertyChange

    private void tbxCashInPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbxCashInPropertyChange
        
    }//GEN-LAST:event_tbxCashInPropertyChange

    private void tbxCashInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxCashInKeyPressed
               
        tbxExchange.setText("0.00");
        btnCheckout.setEnabled(false);
        
    }//GEN-LAST:event_tbxCashInKeyPressed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown

    private void btnClearCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCartActionPerformed

        clearTable(tblCartList);
        setFrameState(frameState.INIT);
        calculateCartTotal();

    }//GEN-LAST:event_btnClearCartActionPerformed

    private void btnRemoeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoeActionPerformed

        int curRow = tblCartList.getSelectedRow();
        if(curRow > -1){
            modelCartList.removeRow(curRow);

            resetCartPanel();
            calculateCartTotal();
            tblContentList.clearSelection();
        }

    }//GEN-LAST:event_btnRemoeActionPerformed

    private void tbxQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxQuantityKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxQuantityKeyReleased

    private void tbxQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusLost

        String quantity = tbxQuantity.getText().trim();

        ValidationResult vr = new ValidationResult();
        vr = validateStringIsInteger(quantity, "");
        if (vr.result) {
            int iQuantity = Integer.parseInt(quantity);
            if (iQuantity < 1) {
                tbxQuantity.setText("1");
            }
        }

    }//GEN-LAST:event_tbxQuantityFocusLost

    private void tbxQuantityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusGained

        tbxQuantity.selectAll();

    }//GEN-LAST:event_tbxQuantityFocusGained

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed

        tblCartList.clearSelection();

        String code = tbxCode.getText().trim();
        String quantity = tbxQuantity.getText().trim();
        String productID = lblCurrentProductID.getText();

        Double sumPrice = Double.parseDouble(lblTotalPrice.getText());

        ValidationResult vr = new ValidationResult();
        String errMessage;

        vr = validateProductCode(code);
        if (!vr.result) {
            popupWarning(vr);
            tbxCode.setText("");
            tbxCode.requestFocus();
            return;
        }

        errMessage = "Invalid 'Quantity' format!";
        vr = validateStringIsInteger(quantity, errMessage);
        if (!vr.result) {
            popupWarning(vr);
            tbxQuantity.setText("1");
            tbxQuantity.requestFocus();
            return;
        }

        double dPrice, dTotal;
        String dfQuantity, dfPrice;

        int row = getExistProductIDRowNo(productID);
        if (row > -1) { // Item already in cart
            int curQty = Integer.parseInt(tblCartList.getValueAt(row, 1).toString());
            int addQty = Integer.parseInt(quantity);

            if (btnAddToCart.getText().equals("Add To Cart")) {
                dfQuantity = DFMT_QUANTITY.format(curQty + addQty);
                modelCartList.setValueAt(dfQuantity, row, 1);
            } else {
                dfQuantity = DFMT_QUANTITY.format(addQty);
                modelCartList.setValueAt(dfQuantity, row, 1);
                btnAddToCart.setText("Add To Cart");
            }

            tblContentList.clearSelection();
            tbxCode.setText("");
            tbxCode.requestFocus();
            tbxQuantity.setText("1");
            lblCurrentProductID.setText("");

            tbxCashIn.setText("0.00");
            tbxExchange.setText("0.00");

            calculateCartTotal();

        } else { // Add new item  to cart
            Product product = new Product((Integer.parseInt(productID)));
            row = tblCartList.getRowCount();
            try {
                modelCartList.addRow(new  Object[0]);
                modelCartList.setValueAt(product.getName(), row, 0);

                int iQuantity = Integer.parseInt(quantity);

                dPrice = product.getPrice();
                dfQuantity = DFMT_QUANTITY.format(iQuantity);
                dfPrice = DFMT_PRICE.format(dPrice);
                dTotal = dPrice * iQuantity;
                sumPrice += dTotal;

                modelCartList.setValueAt(dfQuantity, row, 1);
                modelCartList.setValueAt(dfPrice, row, 2);
                modelCartList.setValueAt(product.getId(), row, 4);
                modelCartList.setValueAt(product.getCode(), row, 5);
                modelCartList.setValueAt(product.getCost(), row, 6);

                tblContentList.clearSelection();
                tbxCode.setText("");
                tbxCode.requestFocus();
                tbxQuantity.setText("1");

                tbxCashIn.setText("0.00");
                tbxExchange.setText("0.00");

                lblCurrentProductID.setText("");

                calculateCartTotal();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void tbxCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxCodeKeyReleased

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tbxQuantity.requestFocus();
            tbxQuantity.selectAll();
        }

    }//GEN-LAST:event_tbxCodeKeyReleased

    private void tbxCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusLost

        String code = tbxCode.getText().trim();

        if (!"".equals(code)) {
            if (Product.isExist("code='"+code+"'") == null) {
                JOptionPane.showMessageDialog(this, "Product code '"+code+"' does not exists!", "ERROR", JOptionPane.ERROR_MESSAGE);
                resetInfoPanel();
                tbxCode.setText("");
                tbxCode.requestFocus();
            } else {
                autoSelectProductList(code);
            }
        }

    }//GEN-LAST:event_tbxCodeFocusLost

    private void tbxCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusGained

        tbxCode.selectAll();

    }//GEN-LAST:event_tbxCodeFocusGained

    private void tblCartListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCartListMouseClicked

    }//GEN-LAST:event_tblCartListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnCalcExchange;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnClearCart;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnRemoe;
    public static javax.swing.JComboBox<String> cmbCategoryFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCartCurrentRow;
    private javax.swing.JLabel lblCurrentProductID;
    private javax.swing.JLabel lblReceiptID;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JPanel pnlCart;
    private javax.swing.JPanel pnlCheckout;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblCartList;
    public static javax.swing.JTable tblContentList;
    private javax.swing.JTextField tbxCashIn;
    private javax.swing.JTextField tbxCode;
    private javax.swing.JTextField tbxExchange;
    public static javax.swing.JTextField tbxFilter;
    public javax.swing.JTextField tbxQuantity;
    // End of variables declaration//GEN-END:variables
}
