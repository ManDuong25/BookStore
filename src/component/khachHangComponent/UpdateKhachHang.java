/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package component.khachHangComponent;

import bus.KhachHangBUS;
import component.ToolBarAndTableKhachHang;
import dto.KhachHangDTO;
import javax.swing.JOptionPane;

/**
 *
 * @author mansh
 */
public class UpdateKhachHang extends javax.swing.JDialog {

    private ToolBarAndTableKhachHang tblKH;
    private KhachHangDTO selectedKH;

    public UpdateKhachHang(java.awt.Frame parent, boolean modal, ToolBarAndTableKhachHang tblKH) {
        super(parent, modal);
        initComponents();
        this.tblKH = tblKH;
        showOldValue();
    }

    private void showOldValue() {
        String idKH = tblKH.getSelectedItemId();
        KhachHangDTO kh = new KhachHangDTO();
        kh.setMaKH(idKH);
        this.selectedKH = KhachHangBUS.getInstance().getById(kh);
        tenKhTxt.setText(selectedKH.getTenKH());
        diaChiKhTxt.setText(selectedKH.getDiaChi());
        sdtKhTxt.setText(selectedKH.getSdt());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tenKhTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        diaChiKhTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sdtKhTxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thêm khách hàng");
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.GridLayout(3, 2, 10, 20));

        jLabel2.setText("Tên");
        jPanel1.add(jLabel2);

        tenKhTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenKhTxtActionPerformed(evt);
            }
        });
        jPanel1.add(tenKhTxt);

        jLabel3.setText("Địa chỉ");
        jPanel1.add(jLabel3);

        diaChiKhTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaChiKhTxtActionPerformed(evt);
            }
        });
        jPanel1.add(diaChiKhTxt);

        jLabel4.setText("Số điện thoại");
        jPanel1.add(jLabel4);

        sdtKhTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdtKhTxtActionPerformed(evt);
            }
        });
        jPanel1.add(sdtKhTxt);

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
                .addGap(81, 81, 81)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addGap(65, 65, 65)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(82, 82, 82))
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

    private void tenKhTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenKhTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenKhTxtActionPerformed

    private void diaChiKhTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaChiKhTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaChiKhTxtActionPerformed

    private void sdtKhTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdtKhTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sdtKhTxtActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        String ten = tenKhTxt.getText();
        String diaChi = diaChiKhTxt.getText();
        String sdt = sdtKhTxt.getText();

        String errors = KhachHangBUS.getInstance().validateData(ten, diaChi, sdt);
        if (errors == null || errors.isEmpty()) {
            KhachHangDTO kh = KhachHangBUS.getInstance().createNewKH(ten, diaChi, sdt);
            int result = KhachHangBUS.getInstance().update(kh);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                tblKH.refreshDataListKH();
                tblKH.loadDataKH(tblKH.getListKH());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, errors, "Cập nhật khách hàng thất bại!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jButton1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField diaChiKhTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField sdtKhTxt;
    private javax.swing.JTextField tenKhTxt;
    // End of variables declaration//GEN-END:variables

}
