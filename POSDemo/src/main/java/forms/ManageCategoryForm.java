/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Log;
import DBCLS.Product;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.*;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author feine
 */
public class ManageCategoryForm extends javax.swing.JInternalFrame {
    
    public static DefaultTableModel modelContentList;

    /**
     * Creates new form CategoryManageForm
     */
    public ManageCategoryForm() {
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        getContentPane().setBackground(BG_DARK_ALT);
        
        modelContentList = (DefaultTableModel)tblContentList.getModel();
        tblContentList.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        tblContentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Hide buffer labels
        lblRealID.setVisible(false);
        lblCategoryName.setVisible(false);
        lblDescription.setVisible(false);
        
        tblContentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int curRow = tblContentList.getSelectedRow();
                if(curRow > -1){
                    lblCategoryID.setText(tblContentList.getValueAt(curRow, 0).toString());
                    tbxCategoryName.setText(tblContentList.getValueAt(curRow, 1).toString());
                    tbxDescription.setText(tblContentList.getValueAt(curRow, 2).toString());
                    lblRealID.setText(tblContentList.getValueAt(curRow, 3).toString());

                    lblCategoryName.setText(tbxCategoryName.getText());
                    lblDescription.setText(tbxDescription.getText());

                    setFrameState(frameState.SELECTED);
                }
            }
            
        });
        
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
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

                btnReload.setEnabled(true);

                tblContentList.setEnabled(true);
                tblContentList.clearSelection();

                listContent();
                resetInfoPanel();
            }
            
            case FILTERED -> {
                tblContentList.setEnabled(true);
                tblContentList.clearSelection();
            }
            
            case SELECTED -> {
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
            
            case EDIT -> {
                tbxFilter.setEnabled(false);
                tblContentList.setEnabled(false);
                btnReload.setEnabled(false);

                tbxCategoryName.setEnabled(true);
                tbxDescription.setEnabled(true);

                btnEdit.setText("Update");
                btnDelete.setEnabled(false);
                btnCancel.setEnabled(true);
            }
                
            case POST_EDIT -> {
                tbxFilter.setEnabled(true);
                tbxFilter.requestFocus();
                btnReload.setEnabled(true);

                tblContentList.setEnabled(true);
                tblContentList.clearSelection();

                resetInfoPanel();

                listContentWithFilter();
                
                callUpdateCategoryData();
            }
        }
    }
    
    /*==== Helper Functions ====*/
    private void resetInfoPanel() {
        lblCategoryID.setText("");
        tbxCategoryName.setText("");
        tbxCategoryName.setEnabled(false);
        tbxDescription.setText("");
        tbxDescription.setEnabled(false);

        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(false);
        
        lblRealID.setText("");
        lblCategoryName.setText("");
        lblDescription.setText("");
    }
    
    
    // Generate filter string
    public static String getFilterString(String cond) {
        if (!"".equals(cond)) {
            String filter = "id LIKE '%"+cond+"%'"
                    + " OR name LIKE '%"+cond+"%'"
                    + " OR description LIKE '%"+cond+"%'";
            return filter;
        } else {
            return "";
        }        
    }
    
    /*==== Required Functions ====*/
    
    public void listContentWithFilter(){        
        String cond = tbxFilter.getText();
        String filter = getFilterString(cond);
        listContent(filter);        
    }
    
    public void listContent(){
        listContent("");
    }

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
            for (Category category : Category.listCategory(filter)) {
                modelContentList.addRow(new  Object[0]);
                idx = IDPF_CATEGORY + IDFMT_CATEGORY.format(category.getId());
                modelContentList.setValueAt(idx, curRow, 0);
                modelContentList.setValueAt(category.getName(), curRow, 1);
                modelContentList.setValueAt(category.getDescription(), curRow, 2);
                modelContentList.setValueAt(category.getId(), curRow, 3);
                curRow++;
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
            // BODY
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContentList = new javax.swing.JTable();
        pnlSearch = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel1 = new javax.swing.JLabel();
        tbxFilter = new javax.swing.JTextField();
        btnReload = new javax.swing.JButton();
        pnlUserInfo = new javax.swing.JPanel(){
            // BODY
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tbxCategoryName = new javax.swing.JTextField();
        lblCategoryID = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        lblRealID = new javax.swing.JLabel();
        lblCategoryName = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbxDescription = new javax.swing.JTextArea();
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
        setTitle("PRODUCT CATEGORY MANAGEMENT PANEL");
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
                "CategoryID", "Name", "Description", "RealID"
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
        tblContentList.setRowHeight(24);
        tblContentList.setShowVerticalLines(false);
        tblContentList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblContentList);
        if (tblContentList.getColumnModel().getColumnCount() > 0) {
            tblContentList.getColumnModel().getColumn(0).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(0).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(1).setMinWidth(200);
            tblContentList.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblContentList.getColumnModel().getColumn(1).setMaxWidth(200);
            tblContentList.getColumnModel().getColumn(3).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        pnlSearch.setBackground(new java.awt.Color(102, 102, 102));
        pnlSearch.setMaximumSize(new java.awt.Dimension(32767, 29));
        pnlSearch.setMinimumSize(new java.awt.Dimension(0, 29));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search");

        tbxFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxFilter.setPreferredSize(new java.awt.Dimension(7, 27));
        tbxFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbxFilterKeyReleased(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(255, 204, 0));
        btnReload.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/refresh.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
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
                .addComponent(tbxFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tbxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
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

        pnlUserInfo.setBackground(new java.awt.Color(102, 102, 102));
        pnlUserInfo.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CategoryID: ");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Name: ");

        tbxCategoryName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCategoryName.setEnabled(false);
        tbxCategoryName.setMinimumSize(new java.awt.Dimension(7, 27));
        tbxCategoryName.setPreferredSize(new java.awt.Dimension(9, 27));
        tbxCategoryName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCategoryNameFocusGained(evt);
            }
        });

        lblCategoryID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblCategoryID.setForeground(new java.awt.Color(255, 255, 255));
        lblCategoryID.setPreferredSize(new java.awt.Dimension(24, 29));

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

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Description: ");

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

        lblRealID.setBackground(new java.awt.Color(204, 204, 255));
        lblRealID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRealID.setEnabled(false);
        lblRealID.setFocusable(false);
        lblRealID.setOpaque(true);

        lblCategoryName.setBackground(new java.awt.Color(204, 204, 255));
        lblCategoryName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCategoryName.setEnabled(false);
        lblCategoryName.setFocusable(false);
        lblCategoryName.setOpaque(true);

        lblDescription.setBackground(new java.awt.Color(204, 204, 255));
        lblDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescription.setEnabled(false);
        lblDescription.setFocusable(false);
        lblDescription.setOpaque(true);

        tbxDescription.setColumns(20);
        tbxDescription.setRows(5);
        jScrollPane2.setViewportView(tbxDescription);

        pnlHeader.setBackground(new java.awt.Color(0, 102, 102));

        lblTitle.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle.setFont(new java.awt.Font("Tw Cen MT", 0, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("MODIFY CATEGORY INFORMATION");

        lblIcon.setForeground(new java.awt.Color(204, 204, 204));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/category32.png"))); // NOI18N
        lblIcon.setPreferredSize(new java.awt.Dimension(36, 36));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlUserInfoLayout = new javax.swing.GroupLayout(pnlUserInfo);
        pnlUserInfo.setLayout(pnlUserInfoLayout);
        pnlUserInfoLayout.setHorizontalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserInfoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRealID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCategoryName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbxCategoryName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addComponent(lblCategoryID, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlUserInfoLayout.setVerticalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserInfoLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCategoryID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(lblRealID, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void tbxCategoryNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCategoryNameFocusGained
        tbxCategoryName.selectAll();
    }//GEN-LAST:event_tbxCategoryNameFocusGained

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

        if ("Edit".equals(btnEdit.getText())) {
            setFrameState(frameState.EDIT);
            tbxCategoryName.requestFocus();
        }else{
            // Validate data
            ValidationResult vr = new ValidationResult();

            String categoryName = tbxCategoryName.getText().trim();
            String description = tbxDescription.getText().trim();

            vr = validateCategoryName(categoryName);
            if (!vr.result) {
                JOptionPane.showMessageDialog(this, vr.message, "Error", JOptionPane.ERROR_MESSAGE);
                tbxCategoryName.setText(lblCategoryName.getText());
                tbxCategoryName.requestFocus();
                return;
            }

            Category category = new Category(Integer.parseInt(lblRealID.getText()));            
            category.setName(categoryName);
            category.setDescription(description);
            
            Log.LogInfo li = new Log.LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "APPLICATION LOG";
            li.event = "MODIFY CATEGORY";            
            li.details = "User '"+CURRENT_USER.username+"' (User Level: "+getUserLevel(CURRENT_USER.level)+") \n"
                    + "MODIFY CATEGORY\n"
                    + " Category[\n"
                    + "  ID: "+category.getId()+",\n"
                    + "  CategoryName: "+category.getName()+",\n"
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
            String categoryName = lblCategoryName.getText();
            
            if (Product.isExist("category = "+Category.NameToCategory(categoryName).getId()) != null) {
                JOptionPane.showMessageDialog(this, "CANNOT delete this Category!\nCategory '"+categoryName+"' already has some Product assigned to it.", "PROHIBIT", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (JOptionPane.showConfirmDialog(this, "Are you sure to permanently DELETE category '"+categoryName+"'?", "DELETE CONFIRMATION", JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
                
                int categoryID = Integer.parseInt(lblRealID.getText());
                Category category = new Category(categoryID);
                if (Category.deleteCategory(categoryID)) {
                    Log.LogInfo li = new Log.LogInfo();
                    li.id = 0;
                    li.log_date = getCurrentDateTimeFormatted();
                    li.user = CURRENT_USER.username;
                    li.category = "APPLICATION LOG";
                    li.event = "ADD NEW CATEGORY";            
                    li.details = "User '"+CURRENT_USER.username+"' (User Level: "+getUserLevel(CURRENT_USER.level)+") \n"
                            + "ADD CATEGORY\n"
                            + " Category[\n"
                            + "  ID: "+category.getId()+",\n"
                            + "  CategoryName: "+category.getName()+",\n"
                            + "  Result: SUCCESS\n"
                            + " ]";
                    Log.addLog(li);
                    
                    JOptionPane.showMessageDialog(this, "Category '"+category.getName()+"' has been DELETED.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Category '"+category.getName()+"' deletion failed!.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                setFrameState(frameState.POST_EDIT);
                
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        String cond = tbxFilter.getText();
        String filter = getFilterString(cond);
        resetInfoPanel();
        listContent(filter);
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lblCategoryID;
    private javax.swing.JLabel lblCategoryName;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblRealID;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JPanel pnlUserInfo;
    public static javax.swing.JTable tblContentList;
    public javax.swing.JTextField tbxCategoryName;
    private javax.swing.JTextArea tbxDescription;
    public static javax.swing.JTextField tbxFilter;
    // End of variables declaration//GEN-END:variables
}
