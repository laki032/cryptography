package main;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author laki
 */
public class Form extends javax.swing.JFrame {

    private final int ENCRYPTION = 1;
    private final int DECRYPTION = 2;
    int operation;

    private File fileToEncrypt;
    private File fileToDecrypt;

    public Form() {
        initComponents();
        btnPerform.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEncr = new javax.swing.JButton();
        lblMessage = new javax.swing.JLabel();
        btnDecr = new javax.swing.JButton();
        btnPerform = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnEncr.setText("select file to encrypt");
        btnEncr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncrActionPerformed(evt);
            }
        });

        btnDecr.setText("select file to decrypt");
        btnDecr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDecrActionPerformed(evt);
            }
        });

        btnPerform.setText("btnlbl");
        btnPerform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerformActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEncr, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDecr, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPerform, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDecr)
                        .addComponent(btnPerform))
                    .addComponent(btnEncr))
                .addGap(18, 18, 18)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEncrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncrActionPerformed
        final JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        fileToEncrypt = chooser.getSelectedFile();
        if (fileToEncrypt == null) {
            lblMessage.setText("File not choosen");
            btnPerform.setVisible(false);
            return;
        }
        lblMessage.setText("File choosen for encryption   " + fileToEncrypt.getAbsolutePath());
        btnPerform.setText("Encrypt file");
        btnPerform.setVisible(true);
        operation = ENCRYPTION;
    }//GEN-LAST:event_btnEncrActionPerformed

    private void btnDecrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDecrActionPerformed
        final JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        fileToDecrypt = chooser.getSelectedFile();
        if (fileToDecrypt == null) {
            lblMessage.setText("File not choosen");
            btnPerform.setVisible(false);
            return;
        }
        lblMessage.setText("File choosen for decryption   " + fileToDecrypt.getAbsolutePath());
        btnPerform.setText("Decrypt file");
        btnPerform.setVisible(true);
        operation = DECRYPTION;
    }//GEN-LAST:event_btnDecrActionPerformed

    private void btnPerformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerformActionPerformed
        switch (operation) {
            case ENCRYPTION:
                encrypt();
                break;
            case DECRYPTION:
                decrypt();
                break;
            default:
                lblMessage.setText("operation unknown");
        }
    }//GEN-LAST:event_btnPerformActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDecr;
    private javax.swing.JButton btnEncr;
    private javax.swing.JButton btnPerform;
    private javax.swing.JLabel lblMessage;
    // End of variables declaration//GEN-END:variables

    private void encrypt() {
        String password = JOptionPane.showInputDialog("Encryption password: ");
        try {
            Encryptor.encrypt(fileToEncrypt, password);
        } catch (Exception ex) {
            lblMessage.setText("Error encryptig file");
        }
        lblMessage.setText("encrypted file: " + fileToEncrypt.getAbsolutePath() + "_encrypted");
    }

    private void decrypt() {
        String password = JOptionPane.showInputDialog("Decryption password: ");
        try {
            Encryptor.decrypt(fileToDecrypt, password);
        } catch (Exception ex) {
            lblMessage.setText("Error decryptig file");
        }
        lblMessage.setText("decrypted file: " + fileToDecrypt.getAbsolutePath() + "_decrypted");
    }
}
