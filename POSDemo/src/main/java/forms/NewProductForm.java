/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Product;
import DBCLS.Product.ProductInfo;
import DBCLS.Stock;
import DBCLS.Stock.StockInfo;
import GLOBAL.APIData;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.*;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author feine
 */
public class NewProductForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewProductForm
     */
    public NewProductForm() {
        
        initComponents();
        getContentPane().setBackground(BG_DARK);
        
        listCategory();
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
            case "listCategory" -> listCategory();
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
                setFrameState(frameState.POST_EDIT);
            }
                
            case POST_EDIT -> {                
                for (Component comp : pnlMainInfo.getComponents()) {
                    if (comp instanceof JTextField) {
                        ((JTextField) comp).setText("");
                    }
                }
                cmbCategory.setSelectedIndex(0);
                cmbStatus.setSelectedIndex(0);
                tbxDescription.setText("");
                tbxQuantity.setText("100");
                
                callUpdateProductAndStockData();
            }
        }
    }
    
    /*==== Helper Functions ====*/    
    public void popupWarning(ValidationResult vr) {
        JOptionPane.showMessageDialog(this, vr.message, "VALIDATION WARNING", JOptionPane.WARNING_MESSAGE);
    }
    
    private void calcProfitRate() {
        
        String cost = tbxCost.getText();
        
        if (validateStringIsDouble(cost, "").result) {
            tbxPrice.setText(DFMT_PRICE_NC.format(Double.parseDouble(cost) + (Double.parseDouble(cost)*sldProfit.getValue()/100)));
        }
        
    }
    
    /*==== Required Functions ====*/  
    private void listCategory() {
        
        cmbCategory.removeAllItems();
        for (Category cat : Category.listCategory()) {
            cmbCategory.addItem(cat.getName());
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

        pnlMainInfo = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY_PORTRAIT, 0, 0, getWidth(), getHeight(), this);
            }
        };
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tbxCode = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbxDescription = new javax.swing.JTextArea();
        tbxName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        tbxPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblTitle1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tbxQuantity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tbxCost = new javax.swing.JTextField();
        pnlHeader = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblTitle2 = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        sldProfit = new javax.swing.JSlider();
        lblProfitValue = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setResizable(true);
        setMaximumSize(new java.awt.Dimension(750, 800));
        setMinimumSize(new java.awt.Dimension(650, 800));
        setPreferredSize(new java.awt.Dimension(650, 800));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlMainInfo.setBackground(new java.awt.Color(102, 102, 102));

        btnAdd.setBackground(new java.awt.Color(0, 153, 204));
        btnAdd.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/add.png"))); // NOI18N
        btnAdd.setText("Add");
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

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Code: ");
        jLabel2.setPreferredSize(new java.awt.Dimension(45, 20));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Description: ");

        tbxCode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCode.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxCode.setMinimumSize(new java.awt.Dimension(260, 29));
        tbxCode.setPreferredSize(new java.awt.Dimension(260, 29));
        tbxCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCodeFocusLost(evt);
            }
        });

        tbxDescription.setColumns(20);
        tbxDescription.setRows(5);
        tbxDescription.setMaximumSize(new java.awt.Dimension(260, 260));
        tbxDescription.setMinimumSize(new java.awt.Dimension(260, 70));
        tbxDescription.setPreferredSize(new java.awt.Dimension(260, 70));
        jScrollPane1.setViewportView(tbxDescription);

        tbxName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxName.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxName.setMinimumSize(new java.awt.Dimension(260, 29));
        tbxName.setPreferredSize(new java.awt.Dimension(260, 29));
        tbxName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxNameFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Name: ");
        jLabel4.setPreferredSize(new java.awt.Dimension(45, 20));

        cmbCategory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCategory.setMaximumSize(new java.awt.Dimension(400, 29));
        cmbCategory.setMinimumSize(new java.awt.Dimension(87, 29));
        cmbCategory.setPreferredSize(new java.awt.Dimension(57, 29));

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Category: ");
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 20));

        tbxPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxPrice.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxPrice.setMinimumSize(new java.awt.Dimension(260, 29));
        tbxPrice.setPreferredSize(new java.awt.Dimension(260, 29));
        tbxPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxPriceFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Price: ");
        jLabel6.setPreferredSize(new java.awt.Dimension(45, 20));

        lblTitle1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setText("STOCK INITIALIZE");

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Quantity: ");
        jLabel7.setPreferredSize(new java.awt.Dimension(45, 20));

        tbxQuantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxQuantity.setText("100");
        tbxQuantity.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxQuantity.setMinimumSize(new java.awt.Dimension(260, 29));
        tbxQuantity.setPreferredSize(new java.awt.Dimension(260, 29));
        tbxQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxQuantityFocusLost(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Cost: ");
        jLabel8.setPreferredSize(new java.awt.Dimension(45, 20));

        tbxCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCost.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxCost.setMinimumSize(new java.awt.Dimension(260, 29));
        tbxCost.setPreferredSize(new java.awt.Dimension(260, 29));
        tbxCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCostFocusLost(evt);
            }
        });

        pnlHeader.setBackground(new java.awt.Color(0, 102, 0));

        lblTitle2.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle2.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle2.setText("NEW PRODUCT");

        lblIcon.setForeground(new java.awt.Color(204, 204, 204));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/product64.png"))); // NOI18N
        lblIcon.setPreferredSize(new java.awt.Dimension(68, 68));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVE", "INACTIVE" }));
        cmbStatus.setMaximumSize(new java.awt.Dimension(400, 29));
        cmbStatus.setMinimumSize(new java.awt.Dimension(87, 29));
        cmbStatus.setPreferredSize(new java.awt.Dimension(57, 29));

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status: ");
        jLabel9.setPreferredSize(new java.awt.Dimension(45, 20));

        sldProfit.setMajorTickSpacing(5);
        sldProfit.setMinorTickSpacing(5);
        sldProfit.setValue(30);
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

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Profit: ");
        jLabel10.setPreferredSize(new java.awt.Dimension(45, 20));

        javax.swing.GroupLayout pnlMainInfoLayout = new javax.swing.GroupLayout(pnlMainInfo);
        pnlMainInfo.setLayout(pnlMainInfoLayout);
        pnlMainInfoLayout.setHorizontalGroup(
            pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainInfoLayout.createSequentialGroup()
                        .addComponent(lblTitle1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainInfoLayout.createSequentialGroup()
                        .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlMainInfoLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tbxQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainInfoLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMainInfoLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(tbxCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tbxName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainInfoLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tbxCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlMainInfoLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainInfoLayout.createSequentialGroup()
                                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMainInfoLayout.createSequentialGroup()
                                        .addComponent(sldProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblProfitValue, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(tbxPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1))))
                        .addGap(40, 40, 40))))
        );
        pnlMainInfoLayout.setVerticalGroup(
            pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainInfoLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sldProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProfitValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(lblTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMainInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMainInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        String code = tbxCode.getText().trim();
        String name = tbxName.getText().trim();
        
        String categoryname = String.valueOf(cmbCategory.getSelectedItem());
        Category category = Category.NameToCategory(categoryname);
        
        String cost = tbxCost.getText().trim().replace(",", "");
        String price = tbxPrice.getText().trim().replace(",", "");
        String description = tbxDescription.getText();    
        String quantity = tbxQuantity.getText().trim().replace(",", "");
        
        // Validation Phase
        ValidationResult vr = new ValidationResult();
        String errMessage;
        
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
        
        errMessage = "Invalid 'Quantity' format!";
        vr=validateStringIsInteger(quantity, errMessage);
        if (!vr.result) {
            popupWarning(vr);
            return;
        }
            
        ProductInfo pi = new ProductInfo();
        pi.id = 0;
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

        Product product = Product.addProduct(pi);
        if(product != null){
            StockInfo si = new StockInfo();
            si.id = 0;
            si.product = product.getId();
            si.quantity = Integer.parseInt(quantity);
            si.remark = "";
            
            Stock stock = Stock.addStock(si);
            
            Log.LogInfo li = new Log.LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "APPLICATION LOG";
            li.event = "ADD PRODUCT";
            
            li.details = String.format(
                    """
                    {
                        "APPLICATION LOG":{
                            "LogDate":"%s",
                            "Event":"ADD PRODUCT",
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
                                "InitQuantity":%d,
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
                            si.quantity,
                            stock.getId(),
                            stock.getQuantity()
                    )
            );            
            Log.addLog(li);
            
            JOptionPane.showMessageDialog(this, "Add new Product successful.","SUCCESSFUL.",JOptionPane.INFORMATION_MESSAGE);
            
            setFrameState(frameState.POST_EDIT);
            tbxCode.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "Add new Product failed.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        setFrameState(frameState.INIT);
        this.hide();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tbxCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusGained
        tbxCode.selectAll();
    }//GEN-LAST:event_tbxCodeFocusGained

    private void tbxCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCodeFocusLost

        String code = tbxCode.getText().trim();
        if (code.length() >= 3) {
            if(Product.isExist("code='"+code+"'") != null) {
                JOptionPane.showMessageDialog(this, "Product Code '"+code+"' is already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                tbxCode.setText("");
                tbxCode.requestFocus();
            } else {
                if(code.length() >= 7 && code.length() <= 14) {
                    APIData.getAPIData(code);
                }
            }
        }
    }//GEN-LAST:event_tbxCodeFocusLost

    private void tbxNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxNameFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxNameFocusGained

    private void tbxNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxNameFocusLost

    private void tbxPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxPriceFocusGained
        
        tbxPrice.selectAll();
        
    }//GEN-LAST:event_tbxPriceFocusGained

    private void tbxPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxPriceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxPriceFocusLost

    private void tbxQuantityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusGained
        tbxQuantity.selectAll();
    }//GEN-LAST:event_tbxQuantityFocusGained

    private void tbxQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxQuantityFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbxQuantityFocusLost

    private void tbxCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCostFocusGained
        tbxCost.selectAll();
    }//GEN-LAST:event_tbxCostFocusGained

    private void tbxCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCostFocusLost
        
        calcProfitRate();
        
    }//GEN-LAST:event_tbxCostFocusLost

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown

    private void sldProfitStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldProfitStateChanged
        
        lblProfitValue.setText(String.valueOf(sldProfit.getValue())+"%");
        
        calcProfitRate();
        
    }//GEN-LAST:event_sldProfitStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblProfitValue;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlMainInfo;
    private javax.swing.JSlider sldProfit;
    private javax.swing.JTextField tbxCode;
    private javax.swing.JTextField tbxCost;
    private javax.swing.JTextArea tbxDescription;
    private javax.swing.JTextField tbxName;
    private javax.swing.JTextField tbxPrice;
    private javax.swing.JTextField tbxQuantity;
    // End of variables declaration//GEN-END:variables
}
