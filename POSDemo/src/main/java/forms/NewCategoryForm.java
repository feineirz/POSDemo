/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.Category;
import DBCLS.Category.CategoryInfo;
import DBCLS.Log;
import static GLOBAL.HelperFunctions.*;
import static GLOBAL.Settings.MAIN_FONT;
import static GLOBAL.Validator.InputValidation.*;
import GLOBAL.Validator.InputValidation.ValidationResult;
import static GLOBAL.Varibles.CURRENT_USER;
import javax.swing.JOptionPane;
import javax.swing.plaf.FontUIResource;
import static pos.Apps.*;

/**
 *
 * @author feine
 */
public class NewCategoryForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewCategoryForm
     */
    public NewCategoryForm() {
        initComponents();
        setUIFont(new FontUIResource(MAIN_FONT));
        
    }
    
    /*==== Required Code Structure ====*/
    public void callFunction(String functionName) {
        switch(functionName) {
            case "initForm" -> initForm();
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
                tbxCategoryName.setText("");
                tbxDescription.setText("");
            }
                
            case POST_EDIT -> {                
                reportStockForm.callFunction("loadReport");
                reportSaleDailyForm.callFunction("loadReport");
                
                setFrameState(frameState.INIT);                
                callUpdateCategoryData();
            }
        }
    }
    
    /*==== Helper Functions ====*/    
    public void popupWarning(ValidationResult vr) {        
        JOptionPane.showMessageDialog(this, vr.message, "VALIDATION WARNING", JOptionPane.WARNING_MESSAGE);
    }
    
    /*==== Required Functions ====*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMainInfo = new javax.swing.JPanel();
        btnAddUser = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tbxCategoryName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbxDescription = new javax.swing.JTextArea();
        lblTitle = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(570, 380));
        setMinimumSize(new java.awt.Dimension(570, 380));
        setPreferredSize(new java.awt.Dimension(570, 380));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlMainInfo.setBackground(new java.awt.Color(102, 102, 102));

        btnAddUser.setBackground(new java.awt.Color(0, 153, 204));
        btnAddUser.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnAddUser.setText("Add");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 204, 0));
        btnCancel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Category Name: ");
        jLabel2.setPreferredSize(new java.awt.Dimension(45, 20));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Description: ");

        tbxCategoryName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxCategoryName.setMaximumSize(new java.awt.Dimension(1000, 25));
        tbxCategoryName.setMinimumSize(new java.awt.Dimension(7, 25));
        tbxCategoryName.setPreferredSize(new java.awt.Dimension(7, 25));
        tbxCategoryName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxCategoryNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbxCategoryNameFocusLost(evt);
            }
        });

        tbxDescription.setColumns(20);
        tbxDescription.setRows(5);
        jScrollPane1.setViewportView(tbxDescription);

        lblTitle.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText(" NEW CATEGORY");
        lblTitle.setOpaque(true);

        javax.swing.GroupLayout pnlMainInfoLayout = new javax.swing.GroupLayout(pnlMainInfo);
        pnlMainInfo.setLayout(pnlMainInfoLayout);
        pnlMainInfoLayout.setHorizontalGroup(
            pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMainInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainInfoLayout.createSequentialGroup()
                        .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tbxCategoryName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))))
                .addGap(40, 40, 40))
        );
        pnlMainInfoLayout.setVerticalGroup(
            pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainInfoLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbxCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMainInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMainInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed

        String name = tbxCategoryName.getText().trim();
        String description = tbxDescription.getText();
        
        CategoryInfo ci = new CategoryInfo();
        ci.id = 0;
        ci.name = name;
        ci.description = description;

        ValidationResult vr;
        vr = validateCategoryName(name);
        if (!vr.result) {
            popupWarning(vr);
            return;
        }
        
        Category category = Category.addCategory(ci);
        if(category != null){
            
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
            
            JOptionPane.showMessageDialog(this, "Add new Category successful.","SUCCESSFUL.",JOptionPane.INFORMATION_MESSAGE);

            setFrameState(frameState.POST_EDIT);
            tbxCategoryName.requestFocus();
        }else{
            JOptionPane.showMessageDialog(this, "Add new Category failed.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }        

    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        setFrameState(frameState.INIT);
        this.hide();

    }//GEN-LAST:event_btnCancelActionPerformed

    private void tbxCategoryNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCategoryNameFocusGained
        tbxCategoryName.selectAll();
    }//GEN-LAST:event_tbxCategoryNameFocusGained

    private void tbxCategoryNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxCategoryNameFocusLost

        String catName = tbxCategoryName.getText().trim();
        if (catName.length() >= 3) {
            if(Category.isExist("catname='"+catName+"'") != null) {
                JOptionPane.showMessageDialog(this, "Category name '"+catName+"' is already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                tbxCategoryName.setText("");
                tbxCategoryName.requestFocus();
            }
        }

    }//GEN-LAST:event_tbxCategoryNameFocusLost

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        setFrameState(frameState.INIT);
        
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlMainInfo;
    private javax.swing.JTextField tbxCategoryName;
    private javax.swing.JTextArea tbxDescription;
    // End of variables declaration//GEN-END:variables
}
