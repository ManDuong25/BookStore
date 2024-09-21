/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package component.nhaXuatBanComponent;

import bus.NhaXuatBanBUS;
import component.ToolBarAndTableNhaXuatBan;
import dto.NhaXuatBanDTO;
import javax.swing.JOptionPane;

/**
 *
 * @author mansh
 */
public class UpdateNXB extends javax.swing.JDialog {

    private ToolBarAndTableNhaXuatBan tblNXB;
    private NhaXuatBanDTO selectedNXB;

    public UpdateNXB(java.awt.Frame parent, boolean modal, ToolBarAndTableNhaXuatBan tblNXB) {
        super(parent, modal);
        initComponents();
        this.tblNXB = tblNXB;
        showOldValue();
    }

    private void showOldValue() {
        String idNXB = tblNXB.getSelectedItemId();
        NhaXuatBanDTO nxb = new NhaXuatBanDTO();
        nxb.setMaNXB(idNXB);
        this.selectedNXB = NhaXuatBanBUS.getInstance().getById(nxb);
        tenTxt.setText(selectedNXB.getTen());
        diaChiTxt.setText(selectedNXB.getDiaChi());
        sdtTxt.setText(selectedNXB.getSoDienThoai());
        emailTxt.setText(selectedNXB.getEmail());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tenTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        diaChiTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sdtTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sửa nhà xuất bản");
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.GridLayout(4, 4, 10, 10));

        jLabel2.setText("Tên");
        jPanel1.add(jLabel2);

        tenTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenTxtActionPerformed(evt);
            }
        });
        jPanel1.add(tenTxt);

        jLabel3.setText("Địa chỉ");
        jPanel1.add(jLabel3);

        diaChiTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaChiTxtActionPerformed(evt);
            }
        });
        jPanel1.add(diaChiTxt);

        jLabel4.setText("Số điện thoại");
        jPanel1.add(jLabel4);

        sdtTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdtTxtActionPerformed(evt);
            }
        });
        jPanel1.add(sdtTxt);

        jLabel5.setText("Email");
        jPanel1.add(jLabel5);
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

    private void tenTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenTxtActionPerformed

    private void diaChiTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaChiTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaChiTxtActionPerformed

    private void sdtTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdtTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sdtTxtActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        String ten = tenTxt.getText();
        String diaChi = diaChiTxt.getText();
        String sdt = sdtTxt.getText();
        String email = emailTxt.getText();

        String errors = NhaXuatBanBUS.getInstance().validateData(ten, diaChi, sdt, email);
        if (errors == null || errors.isEmpty()) {
            NhaXuatBanDTO nxb = NhaXuatBanBUS.getInstance().createUpdatedNXB(this.selectedNXB.getMaNXB(), ten, diaChi, sdt, email);
            int result = NhaXuatBanBUS.getInstance().update(nxb);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật NXB thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                tblNXB.refreshDataListNXB();
                tblNXB.loadDataNXB(tblNXB.getListNXB());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật NXB thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, errors, "Cập nhật NXB thất bại!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jButton1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField diaChiTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField sdtTxt;
    private javax.swing.JTextField tenTxt;
    // End of variables declaration//GEN-END:variables

}
