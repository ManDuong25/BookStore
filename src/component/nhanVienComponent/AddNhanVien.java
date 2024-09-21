/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package component.nhanVienComponent;

import bus.NhanVienBUS;
import bus.NhomQuyenBUS;
import bus.TaiKhoanBUS;
import component.ToolBarAndTableNhanVien;
import dto.NhanVienDTO;
import dto.NhomQuyenDTO;
import dto.TaiKhoanDTO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author mansh
 */
public class AddNhanVien extends javax.swing.JDialog {

    private ToolBarAndTableNhanVien tblNv;

    /**
     * Creates new form AddNhanVien
     */
    public AddNhanVien(java.awt.Frame parent, boolean modal, ToolBarAndTableNhanVien tblNv) {
        super(parent, modal);
        this.tblNv = tblNv;
        initComponents();

        ArrayList<NhomQuyenDTO> nqList = NhomQuyenBUS.getInstance().getAll();
        for (NhomQuyenDTO nq : nqList) {
            if (nq.getTrangThai() == 1) {
                nhomQuyenCbx.addItem(nq.getMaNhomQuyen() + ": " + nq.getTenNhomQuyen());
            }
        }
    }

    public void txtFieldAllowNumberOnly(JTextField txtField) {
        txtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    txtField.setEditable(true);
                } else {
                    txtField.setEditable(false);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tenNhanVienTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        diaChiTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sdtTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        luongTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nhomQuyenCbx = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel3.setLayout(new java.awt.GridLayout(7, 2, 0, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tên nhân viên");
        jPanel3.add(jLabel1);
        jPanel3.add(tenNhanVienTxt);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Địa chỉ");
        jPanel3.add(jLabel2);
        jPanel3.add(diaChiTxt);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Email");
        jPanel3.add(jLabel3);
        jPanel3.add(emailTxt);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Số điện thoại");
        jPanel3.add(jLabel4);
        jPanel3.add(sdtTxt);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Lương");
        jPanel3.add(jLabel5);

        luongTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luongTxtActionPerformed(evt);
            }
        });
        jPanel3.add(luongTxt);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Chức vụ");
        jPanel3.add(jLabel6);

        nhomQuyenCbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định" }));
        jPanel3.add(nhomQuyenCbx);

        cancelBtn.setText("Huỷ");
        cancelBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cancelBtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cancelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel1);

        addBtn.setText("Thêm");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void luongTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luongTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_luongTxtActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        String tenNV = tenNhanVienTxt.getText();
        String diaChi = diaChiTxt.getText();
        String email = emailTxt.getText();
        String soDienThoai = sdtTxt.getText();
        String luong = luongTxt.getText();
        String maNhomQuyen = "";
        if (nhomQuyenCbx.getSelectedIndex() > 0) {
            String nqTxt = (String) nhomQuyenCbx.getSelectedItem();
            String[] maNqArray = nqTxt.split(":");
            maNhomQuyen = maNqArray[0];
        }

        String errors = NhanVienBUS.getInstance().validateData(tenNV, diaChi, email, soDienThoai, luong, maNhomQuyen);
        if (errors == null || errors.isEmpty()) {
            if (NhanVienBUS.getInstance().isPhoneNumberExist(soDienThoai)) {
                JOptionPane.showMessageDialog(this, "SĐT đã tồn tại trong hệ thống: " + soDienThoai, "Thất bại", JOptionPane.ERROR_MESSAGE);
                return;
            }
            NhanVienDTO nv = NhanVienBUS.getInstance().createNewNV(tenNV, diaChi, email, soDienThoai, luong, maNhomQuyen);
            int result = NhanVienBUS.getInstance().add(nv);
            if (result > 0) {
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setMaNV(nv.getMaNV());
                tk.setMatKhau("12345678");
                tk.setEmail(nv.getEmail());
                int result1 = TaiKhoanBUS.getInstance().add(tk);
                if (result1 > 0) {
                    JOptionPane.showMessageDialog(tblNv, "Thêm mới thành công nhân viên có mã là: " + nv.getMaNV(), "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                    tblNv.refreshDataListNV();
                    tblNv.loadDataNhanVien(tblNv.getListNV());
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, errors, "Thêm sách thất bại", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_addBtnMouseClicked

    private void cancelBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cancelBtnKeyPressed
        this.dispose();
    }//GEN-LAST:event_cancelBtnKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField diaChiTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField luongTxt;
    private javax.swing.JComboBox<String> nhomQuyenCbx;
    private javax.swing.JTextField sdtTxt;
    private javax.swing.JTextField tenNhanVienTxt;
    // End of variables declaration//GEN-END:variables
}
