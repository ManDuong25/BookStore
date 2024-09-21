/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package component.taiKhoanComponent;

import bus.TaiKhoanBUS;
import component.ToolBarAndTableTaiKhoan;
import dto.TaiKhoanDTO;
import javax.swing.JOptionPane;

/**
 *
 * @author mansh
 */
public class UpdateTaiKhoan extends javax.swing.JDialog {

    private ToolBarAndTableTaiKhoan tblTK;
    private TaiKhoanDTO selectedTK;

    public UpdateTaiKhoan(java.awt.Frame parent, boolean modal, ToolBarAndTableTaiKhoan tblTK) {
        super(parent, modal);
        initComponents();
        this.tblTK = tblTK;
        showOldValue();
    }

    private void showOldValue() {
        String idTK = tblTK.getSelectedItemId();
        TaiKhoanDTO tk = new TaiKhoanDTO ();
        tk.setMaNV(idTK);
        this.selectedTK = TaiKhoanBUS.getInstance().getById(tk);
        matKhauTxt.setText(selectedTK.getMatKhau());
        emailTxt.setText(selectedTK.getEmail());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        matKhauTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sửa tài khoản");
        jLabel1.setPreferredSize(new java.awt.Dimension(118, 40));
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        jLabel2.setText("Mật khẩu");
        jPanel1.add(jLabel2);

        matKhauTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matKhauTxtActionPerformed(evt);
            }
        });
        jPanel1.add(matKhauTxt);

        jLabel3.setText("Email");
        jPanel1.add(jLabel3);

        emailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTxtActionPerformed(evt);
            }
        });
        jPanel1.add(emailTxt);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 50));

        jButton1.setText("Huỷ");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Cập nhật");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addGap(59, 59, 59)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addGap(85, 85, 85))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void matKhauTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matKhauTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matKhauTxtActionPerformed

    private void emailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTxtActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        String matKhau = matKhauTxt.getText();
        String email = emailTxt.getText();

        String errors = TaiKhoanBUS.getInstance().validateData(matKhau, email);
        if (errors == null || errors.isEmpty()) {
            TaiKhoanDTO tk = TaiKhoanBUS.getInstance().createUpdatedTK(this.selectedTK.getMaNV(), matKhau, email);
            int result = TaiKhoanBUS.getInstance().update(tk);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                tblTK.refreshDataListTK();
                tblTK.loadDataTK(tblTK.getListTK());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, errors, "Cập nhật tài khoản thất bại!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jButton1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField matKhauTxt;
    // End of variables declaration//GEN-END:variables

}
