/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DBCLS.*;
import static GLOBAL.EncDec.*;
import static GLOBAL.HelperFunctions.*;
import GLOBAL.Settings;
import static GLOBAL.Settings.*;
import static GLOBAL.Varibles.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author feine
 */
public class LoginForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form LoginForm
     */
    public LoginForm() {
        initComponents();
        
        this.setName("LOGIN_FORM");
    }
    
    private void performLogIn() {
        
        boolean isValid = false;

        String username = tbxUser.getText();
        String password = tbxPass.getText();
        String encPassword = encrypt(password, SECRET_KEY);

        User user = User.LogIn(username, encPassword);
        
        if (user != null) {
            CURRENT_USER = user.toUserInfoWithDefault();
        }        
        
        Log.LogInfo li = new Log.LogInfo();
        li.id = 0;
        li.log_date = getCurrentDateTimeFormatted();
        li.user = CURRENT_USER.username;
        li.category = "SYSTEM LOG";
        li.event = "LOGIN";    

        if(user != null){
            
            li.details = String.format(
                    """
                    {
                        "SYSTEM LOG":{
                            "LogDate":"%s",
                            "Event":"LOGIN",
                            "Account":{
                                "ID":%d,
                                "Username":"%s",
                                "Password":"<***SECRET***>",
                                "Email":"%s",
                                "Phone":"%s",
                                "Level":"%s"
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
                            getUserLevel(CURRENT_USER.level)
                    )
            );            
            Log.addLog(li);
            
            this.dispose();
        }else{
            li.details = String.format(
                    """
                    {
                        "SYSTEM LOG":{
                            "LogDate":"%s",
                            "Event":"LOGIN",
                            "Account":{
                                "Username":"%s",
                                "Password":"<***SECRET***>"
                            },
                            "Result":"FAILED",
                            "Reason":"Invalid Username and/or Password!"
                        }
                    }
                    """.formatted(
                            li.log_date,
                            username
                    )
            );            
            Log.addLog(li);
            
            JOptionPane.showMessageDialog(this, "Invalid username and/or password!", "Error", JOptionPane.ERROR_MESSAGE);
            tbxUser.setText("");
            tbxPass.setText("");
            tbxUser.requestFocus();
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

        pnlBody = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_BODY, 0, 0, getWidth(), getHeight(), this);
            }
        };
        tbxUser = new javax.swing.JTextField();
        tbxPass = new javax.swing.JPasswordField();
        btnExit = new javax.swing.JButton();
        btnLogIn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblLogInHint = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(new Settings().BACKGROUND_IMAGE_HEADER, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jLabel3 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setFrameIcon(null);
        setVisible(true);

        pnlBody.setBackground(new java.awt.Color(102, 102, 102));

        tbxUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxUser.setText("demo");
        tbxUser.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxUser.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxUser.setPreferredSize(new java.awt.Dimension(100, 29));
        tbxUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxUserFocusGained(evt);
            }
        });
        tbxUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbxUserKeyPressed(evt);
            }
        });

        tbxPass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbxPass.setText("demo");
        tbxPass.setMaximumSize(new java.awt.Dimension(1000, 29));
        tbxPass.setMinimumSize(new java.awt.Dimension(100, 29));
        tbxPass.setPreferredSize(new java.awt.Dimension(100, 29));
        tbxPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbxPassFocusGained(evt);
            }
        });
        tbxPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbxPassKeyPressed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(255, 102, 102));
        btnExit.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        btnExit.setForeground(new java.awt.Color(51, 51, 51));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/exit.png"))); // NOI18N
        btnExit.setText("EXIT");
        btnExit.setMaximumSize(new java.awt.Dimension(55, 32));
        btnExit.setMinimumSize(new java.awt.Dimension(55, 32));
        btnExit.setPreferredSize(new java.awt.Dimension(55, 32));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnLogIn.setBackground(new java.awt.Color(153, 255, 0));
        btnLogIn.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        btnLogIn.setForeground(new java.awt.Color(51, 51, 51));
        btnLogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button/login.png"))); // NOI18N
        btnLogIn.setText("LOGIN");
        btnLogIn.setMaximumSize(new java.awt.Dimension(71, 32));
        btnLogIn.setMinimumSize(new java.awt.Dimension(71, 32));
        btnLogIn.setName("LogIn"); // NOI18N
        btnLogIn.setPreferredSize(new java.awt.Dimension(71, 32));
        btnLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });
        btnLogIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLogInKeyPressed(evt);
            }
        });

        lblLogInHint.setFont(new java.awt.Font("Tw Cen MT", 2, 14)); // NOI18N
        lblLogInHint.setForeground(new java.awt.Color(204, 255, 0));
        lblLogInHint.setText("* user: demo / pass: demo");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 2, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Username ");
        jLabel2.setPreferredSize(new java.awt.Dimension(45, 20));

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 2, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Password ");
        jLabel4.setPreferredSize(new java.awt.Dimension(45, 20));

        pnlHeader.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/secure_blue64.png"))); // NOI18N

        lblTitle.setBackground(new java.awt.Color(51, 51, 51));
        lblTitle.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 153, 255));
        lblTitle.setText("SYSTEM LOGIN");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBodyLayout = new javax.swing.GroupLayout(pnlBody);
        pnlBody.setLayout(pnlBodyLayout);
        pnlBodyLayout.setHorizontalGroup(
            pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBodyLayout.createSequentialGroup()
                .addGroup(pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBodyLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1))
                    .addGroup(pnlBodyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBodyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlBodyLayout.createSequentialGroup()
                        .addComponent(btnLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                    .addComponent(lblLogInHint)
                    .addComponent(tbxUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbxPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
            .addComponent(pnlHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBodyLayout.setVerticalGroup(
            pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBodyLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBodyLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(135, 135, 135))
                    .addGroup(pnlBodyLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbxPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(lblLogInHint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(48, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogInActionPerformed

        performLogIn();

    }//GEN-LAST:event_btnLogInActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed

        System.exit(0);

    }//GEN-LAST:event_btnExitActionPerformed

    private void tbxPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxPassFocusGained
        tbxPass.selectAll();
    }//GEN-LAST:event_tbxPassFocusGained

    private void tbxUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbxUserFocusGained
        tbxUser.selectAll();
    }//GEN-LAST:event_tbxUserFocusGained

    private void tbxUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxUserKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tbxPass.requestFocus();
        }
        
    }//GEN-LAST:event_tbxUserKeyPressed

    private void tbxPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxPassKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnLogIn.requestFocus();
        }
        
    }//GEN-LAST:event_tbxPassKeyPressed

    private void btnLogInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLogInKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            performLogIn();
        }
        
    }//GEN-LAST:event_btnLogInKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel lblLogInHint;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlHeader;
    public javax.swing.JPasswordField tbxPass;
    public javax.swing.JTextField tbxUser;
    // End of variables declaration//GEN-END:variables
}
