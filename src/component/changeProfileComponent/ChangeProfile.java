/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package component.changeProfileComponent;

import bus.TaiKhoanBUS;
import dto.TaiKhoanDTO;
import gui.MainView;
import javax.swing.JOptionPane;

/**
 *
 * @author mansh
 */
public class ChangeProfile extends javax.swing.JDialog {

    private MainView mv;

    public ChangeProfile(java.awt.Frame parent, boolean modal, MainView mv) {
        super(parent, modal);
        System.out.println(modal);
        this.mv = mv;
//        this.setModal(true);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        oldPasswordFld = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        newPasswordFld = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        confirmPasswordFld = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thay Đổi Mật Khẩu");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel2.setLayout(new java.awt.GridLayout(4, 2, 0, 10));

        jLabel2.setText("Mật khẩu cũ");
        jPanel2.add(jLabel2);
        jPanel2.add(oldPasswordFld);

        jLabel3.setText("Mật khẩu mới");
        jPanel2.add(jLabel3);
        jPanel2.add(newPasswordFld);

        jLabel4.setText("Nhập lại mật khẩu");
        jPanel2.add(jLabel4);
        jPanel2.add(confirmPasswordFld);

        jButton1.setText("Huỷ");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3);

        jButton2.setText("Xác nhận");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        char[] oldPassChar = oldPasswordFld.getPassword();
        String oldPass = new String(oldPassChar);
        
        char[] newPassChar = newPasswordFld.getPassword();
        String newPass = new String(newPassChar);
        
        char[] confirmPassChar = confirmPasswordFld.getPassword();
        String confirmPass = new String(confirmPassChar);

        if (oldPass == null || oldPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu cũ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (newPass == null || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (confirmPass == null || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TaiKhoanDTO tkTemp = new TaiKhoanDTO();
        tkTemp.setMaNV(mv.getNv().getMaNV());
        TaiKhoanDTO tk = TaiKhoanBUS.getInstance().getById(tkTemp);
        if (tk != null) {
            if (tk.getMatKhau().equals(oldPass)) {
                String errors = TaiKhoanBUS.getInstance().validateData(newPass, tk.getEmail());
                if (errors == null || errors.isEmpty()) {
                    if (newPass.equals(confirmPass)) {
                        tk.setMatKhau(newPass);
                        int result = TaiKhoanBUS.getInstance().update(tk);
                        if (result > 0) {
                            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại, lỗi hệ thống!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
                        }                       
                    } else {
                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại: nhập lại mật khẩu sai!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại: " + errors, "Thất bại!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại: mật khẩu cũ nhập vào sai!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField confirmPasswordFld;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField newPasswordFld;
    private javax.swing.JPasswordField oldPasswordFld;
    // End of variables declaration//GEN-END:variables
}
