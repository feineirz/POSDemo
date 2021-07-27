/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;


import DBCLS.Log;
import DBCLS.User;
import GLOBAL.*;
import static GLOBAL.HelperFunctions.*;
import static GLOBAL.Settings.*;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.*;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import static pos.Apps.*;

/**
 *
 * @author feine
 */
public class ManageUserForm extends javax.swing.JInternalFrame {
    
    public static DefaultTableModel contentModel;

    /**
     * Creates new form UserInfoForm
     */
    public ManageUserForm() {        
        
        initComponents();
        getContentPane().setBackground(BG_DARK_ALT);
        
        contentModel = (DefaultTableModel)tblContentList.getModel();
        tblContentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Hide buffer labels
        lblRealUserID.setVisible(false);
        lblEmail.setVisible(false);
        lblUserLevel.setVisible(false);
        
        tblContentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                
                int curRow = tblContentList.getSelectedRow();
                if(curRow > -1){
                    lblUserID.setText(tblContentList.getValueAt(curRow, 0).toString());
                    lblUsername.setText(tblContentList.getValueAt(curRow, 1).toString());
                    tbxEmail.setText(tblContentList.getValueAt(curRow, 2).toString());
                    tbxPhone.setText(tblContentList.getValueAt(curRow, 3).toString());
                    lblRealUserID.setText(tblContentList.getValueAt(curRow, 4).toString());
                    lblUserLevel.setText(tblContentList.getValueAt(curRow, 5).toString());
                    
                    lblEmail.setText(tbxEmail.getText());
                    
                    Integer level = Integer.parseInt(tblContentList.getValueAt(curRow, 5).toString());
                    cmbUserLevel.setSelectedIndex((level-1) / 30);
                    
                    // Check user level before allow edit.
                    
                    User selUser = new User(Integer.parseInt(lblRealUserID.getText()));
                    // Verify Account Owner and User Level
                    if (CURRENT_USER.username.equals("admin")) {
                        setFrameState(frameState.SELECTED);
                    } else if (CURRENT_USER.id.equals(selUser.getId()) || CURRENT_USER.level > selUser.getLevel()) {
                        setFrameState(frameState.SELECTED);
                        if (CURRENT_USER.id.equals(selUser.getId())) {
                            btnDelete.setEnabled(false);
                        }
                    }                    
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

                resetInfoPanel();
            }
                
            case SELECTED -> {
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
                
            case EDIT -> {
                tbxFilter.setEnabled(false);
                tblContentList.setEnabled(false);
                btnReload.setEnabled(false);

                tbxPassword.setEnabled(true);
                tbxEmail.setEnabled(true);
                tbxPhone.setEnabled(true);
                
                cmbUserLevel.setEnabled(true);
                
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
                
                if (cmbUserLevel.getItemCount() == 2) {
                    cmbUserLevel.addItem("Manager");
                    cmbUserLevel.addItem("Administrator");                    
                }

                resetInfoPanel();
                listContentWithFilter();
                
                CURRENT_USER = new User(CURRENT_USER.id).toUserInfo();
                dashboardForm.callFunction("loadUserInfo");
                
            }
        }
    }
    
    /*==== Helper Functions ====*/	
    private void resetInfoPanel() {        
        
        lblUserID.setText("");
        lblUsername.setText("");
        tbxPassword.setText("");
        tbxPassword.setEnabled(false);
        tbxEmail.setText("");
        tbxEmail.setEnabled(false);
        tbxPhone.setText("");
        tbxPhone.setEnabled(false);

        cmbUserLevel.setSelectedIndex(0);
        cmbUserLevel.setEnabled(false);

        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(false);

        lblRealUserID.setText("");
        lblEmail.setText("");
        lblUserLevel.setText("");
        System.out.println("UM:Infopanel resetted.");
            
    }
    
    public static String getFilterString(String cond) {
        if (!"".equals(cond)) {
            String filter = "id LIKE '%"+cond+"%'"
                    + " OR username LIKE '%"+cond+"%'"
                    + " OR email LIKE '%"+cond+"%'"
                    + " OR phone LIKE '%"+cond+"%'";
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
                contentModel.removeRow(lastRow);
                lastRow--;
            }
            
            // Add Userinfo
            int curRow = 0;
            String idx;
            for (User user : User.listUser(filter, "level desc")) {                
                contentModel.addRow(new  Object[0]);
                idx = IDPF_USER + IDFMT_USER.format(user.getId());
                contentModel.setValueAt(idx, curRow, 0);
                contentModel.setValueAt(user.getUsername(), curRow, 1);
                contentModel.setValueAt(user.getEmail(), curRow, 2);
                contentModel.setValueAt(user.getPhone(), curRow, 3);
                contentModel.setValueAt(user.getId(), curRow, 4);
                contentModel.setValueAt(user.getLevel(), curRow, 5);
                curRow++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlUserInfo = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tbxEmail = new javax.swing.JTextField();
        lblUserID = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tbxPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        lblRealUserID = new javax.swing.JLabel();
        tbxPassword = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        cmbUserLevel = new javax.swing.JComboBox<>();
        lblEmail = new javax.swing.JLabel();
        lblUserLevel = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblTitle = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        pnlTable = new javax.swing.JPanel(){
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

        setBackground(new java.awt.Color(250, 250, 250));
        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("USER MANAGEMENT PANEL");
        setMaximumSize(new java.awt.Dimension(1280, 2000));
        setMinimumSize(new java.awt.Dimension(1280, 570));
        setPreferredSize(new java.awt.Dimension(1280, 570));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlUserInfo.setBackground(new java.awt.Color(102, 102, 102));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setLabelFor(lblUserID);
        jLabel3.setText("UserID: ");

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Username: ");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setLabelFor(tbxEmail);
        jLabel5.setText("Email: ");

        tbxEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxEmail.setEnabled(false);
        tbxEmail.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxEmail.setMinimumSize(new java.awt.Dimension(7, 29));
        tbxEmail.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxEmailFocusGained(evt);
            }
        });

        lblUserID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblUserID.setForeground(new java.awt.Color(255, 255, 255));
        lblUserID.setPreferredSize(new java.awt.Dimension(24, 29));

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

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setLabelFor(tbxEmail);
        jLabel6.setText("Password: ");

        lblUsername.setBackground(new java.awt.Color(204, 204, 255));
        lblUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 2, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 204, 0));
        jLabel7.setLabelFor(tbxEmail);
        jLabel7.setText("* Leave blank if no change.");

        tbxPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxPhone.setEnabled(false);
        tbxPhone.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxPhone.setMinimumSize(new java.awt.Dimension(7, 29));
        tbxPhone.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxPhoneFocusGained(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setLabelFor(tbxEmail);
        jLabel8.setText("Phone: ");

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

        lblRealUserID.setBackground(new java.awt.Color(204, 204, 204));
        lblRealUserID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRealUserID.setEnabled(false);
        lblRealUserID.setFocusable(false);
        lblRealUserID.setOpaque(true);

        tbxPassword.setBackground(new java.awt.Color(255, 204, 0));
        tbxPassword.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxPassword.setMinimumSize(new java.awt.Dimension(7, 29));
        tbxPassword.setPreferredSize(new java.awt.Dimension(7, 29));

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 2, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("User Level: ");

        cmbUserLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Staff", "Supervisor", "Manager", "Administrator" }));
        cmbUserLevel.setEnabled(false);
        cmbUserLevel.setMaximumSize(new java.awt.Dimension(200, 25));
        cmbUserLevel.setMinimumSize(new java.awt.Dimension(57, 25));
        cmbUserLevel.setPreferredSize(new java.awt.Dimension(57, 25));

        lblEmail.setBackground(new java.awt.Color(204, 204, 204));
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmail.setEnabled(false);
        lblEmail.setFocusable(false);
        lblEmail.setOpaque(true);

        lblUserLevel.setBackground(new java.awt.Color(204, 204, 204));
        lblUserLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserLevel.setEnabled(false);
        lblUserLevel.setFocusable(false);
        lblUserLevel.setOpaque(true);

        pnlHeader.setBackground(new java.awt.Color(0, 102, 153));

        lblTitle.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle.setFont(new java.awt.Font("Tw Cen MT", 0, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("MODIFY USER INFORMATION");

        lblIcon.setForeground(new java.awt.Color(204, 204, 204));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user32.png"))); // NOI18N
        lblIcon.setPreferredSize(new java.awt.Dimension(36, 36));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
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
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserInfoLayout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblUserID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRealUserID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbxEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbxPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbUserLevel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbxPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlUserInfoLayout.setVerticalGroup(
            pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserInfoLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tbxPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbUserLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(lblUserLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRealUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlTable.setBackground(new java.awt.Color(102, 102, 102));

        tblContentList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblContentList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Username", "Email", "Phone", "RealUserID", "UserLevel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
            tblContentList.getColumnModel().getColumn(3).setMinWidth(200);
            tblContentList.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblContentList.getColumnModel().getColumn(3).setMaxWidth(200);
            tblContentList.getColumnModel().getColumn(4).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(4).setMaxWidth(0);
            tblContentList.getColumnModel().getColumn(5).setMinWidth(0);
            tblContentList.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblContentList.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        pnlSearch.setBackground(new java.awt.Color(102, 102, 102));
        pnlSearch.setMaximumSize(new java.awt.Dimension(32767, 57));
        pnlSearch.setMinimumSize(new java.awt.Dimension(0, 57));
        pnlSearch.setPreferredSize(new java.awt.Dimension(159, 57));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search");

        tbxFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxFilter.setMaximumSize(new java.awt.Dimension(400, 29));
        tbxFilter.setMinimumSize(new java.awt.Dimension(7, 29));
        tbxFilter.setPreferredSize(new java.awt.Dimension(7, 29));
        tbxFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbxFilterKeyReleased(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(255, 204, 0));
        btnReload.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/refresh.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.setMaximumSize(new java.awt.Dimension(93, 29));
        btnReload.setMinimumSize(new java.awt.Dimension(93, 29));
        btnReload.setPreferredSize(new java.awt.Dimension(93, 29));
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
                    .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tbxFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))))
        );

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pnlSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        
        if ("Edit".equals(btnEdit.getText())) { // Start Edit
            User selUser = new User(Integer.parseInt(lblRealUserID.getText()));
            setFrameState(frameState.EDIT);
            tbxEmail.requestFocus();
            
            if (!CURRENT_USER.username.equals("admin") || selUser.getUsername().equals("admin")) {
                cmbUserLevel.setEnabled(false);
            }
                        
        }else{ // Update
            // Validate data
            ValidationResult vr = new ValidationResult();
            
            String password = tbxPassword.getText().trim();
            String email = tbxEmail.getText().trim();
            String phone = tbxPhone.getText();
            
            if (!password.equals("")) {
                vr = validatePassword(password);
                if (!vr.result) {
                    JOptionPane.showMessageDialog(this, vr.message, "Error", JOptionPane.ERROR_MESSAGE);
                    tbxPassword.setText("");
                    tbxPassword.requestFocus();
                    return;
                }
                password = EncDec.encrypt(password, SECRET_KEY);
            }
            
            vr = validateEmail(email);
            if (!vr.result) {
                JOptionPane.showMessageDialog(this, vr.message, "Error", JOptionPane.ERROR_MESSAGE);
                tbxEmail.setText(lblEmail.getText());
                tbxEmail.requestFocus();
                return;
            }
            
            User user = new User(Integer.parseInt(lblRealUserID.getText()));
            String modifyPasswordStatus = "<***NO CHANGE***>";
            if (!password.equals("")) {
                user.setPassword(password);
                modifyPasswordStatus = "<***MODIFIED***>";
            }                
            user.setEmail(email);
            user.setPhone(phone);
            
            Integer level = (cmbUserLevel.getSelectedIndex() * 30) + 1;
            user.setLevel(level);
            
            Log.LogInfo li = new Log.LogInfo();
            li.id = 0;
            li.log_date = getCurrentDateTimeFormatted();
            li.user = CURRENT_USER.username;
            li.category = "SYSTEM LOG";
            li.event = "MODIFY USER";
            
            li.details = String.format(
                    """
                    {
                        "SYSTEM LOG":{
                            "LogDate":"%s",
                            "Event":"MODIFY USER",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Data":{
                                "Account":{
                                    "ID":%d,
                                    "Username":"%s",
                                    "Email":"%s",
                                    "Phone":"%s",
                                    "Level":"%s"
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
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPhone(),
                            getUserLevel(user.getLevel())
                    )
            );            
            Log.addLog(li);

            JOptionPane.showMessageDialog(this, "User information has been updated.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);    
            
            setFrameState(frameState.POST_EDIT);
        }
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        setFrameState(frameState.POST_EDIT);
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tbxEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxEmailFocusGained
        tbxEmail.selectAll();
    }//GEN-LAST:event_tbxEmailFocusGained

    private void tbxPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxPhoneFocusGained
        tbxPhone.selectAll();
    }//GEN-LAST:event_tbxPhoneFocusGained

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!lblRealUserID.getText().equals("")) {
            String username = lblUsername.getText();
            Integer userLevel = Integer.parseInt(lblUserLevel.getText());
            if (username.equals("admin")) {
                JOptionPane.showMessageDialog(this, "This account is PROTECTED and cannot be delete!", "PROHIBIT", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (username.equals(CURRENT_USER.username)) {
                JOptionPane.showMessageDialog(this, "You cannot delete your own account. Please contact System Administrator.", "PROHIBIT", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (userLevel >= 60 && CURRENT_USER.level < 90) {
                JOptionPane.showMessageDialog(this, "You cannot delete other Manager account. Please contact System Administrator.", "PROHIBIT", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (JOptionPane.showConfirmDialog(this, "Are you sure to permanently DELETE account '"+username+"'?", "DELETE CONFIRMATION", JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
                int userID = Integer.parseInt(lblRealUserID.getText());
                User delUser = new User(userID);
                
                if (User.deleteUser(userID)) {
                    Log.LogInfo li = new Log.LogInfo();
                    li.id = 0;
                    li.log_date = getCurrentDateTimeFormatted();
                    li.user = CURRENT_USER.username;
                    li.category = "SYSTEM LOG";
                    li.event = "DELETE USER";       
                                        
                    li.details = String.format(
                    """
                    {
                        "SYSTEM LOG":{
                            "LogDate":"%s",
                            "Event":"DELETE USER",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
                            },
                            "Data":{
                                "Account":{
                                    "ID":%d,
                                    "Username":"%s",
                                    "Email":"%s",
                                    "Phone":"%s",
                                    "Level":"%s"
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
                            delUser.getId(),
                            delUser.getUsername(),
                            delUser.getEmail(),
                            delUser.getPhone(),
                            getUserLevel(delUser.getLevel())
                    )
                );            
                Log.addLog(li);
                    
                    JOptionPane.showMessageDialog(this, "Account '"+delUser.getUsername()+"' has been DELETED.", "SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Account '"+delUser.getUsername()+"' deletion failed!.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }                    
                
                setFrameState(frameState.POST_EDIT);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        
        listContentWithFilter();
        
    }//GEN-LAST:event_btnReloadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.JComboBox<String> cmbUserLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblRealUserID;
    private javax.swing.JLabel lblTitle;
    public javax.swing.JLabel lblUserID;
    private javax.swing.JLabel lblUserLevel;
    public javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JPanel pnlUserInfo;
    public static javax.swing.JTable tblContentList;
    public javax.swing.JTextField tbxEmail;
    public static javax.swing.JTextField tbxFilter;
    private javax.swing.JPasswordField tbxPassword;
    public javax.swing.JTextField tbxPhone;
    // End of variables declaration//GEN-END:variables
}
