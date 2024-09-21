
package component;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author mansh
 */
public class MenuPanel extends javax.swing.JPanel {
    
    private JLabel current = null;
    private Color primaryColor = new Color(214, 217, 223);
    private Color selecttedColor = new Color(153, 153, 153);

    public JLabel getCurrent() {
        return current;
    }
    
    public void setCurrent(JLabel current) {
        this.current = current;
    }
    
    public MenuPanel() {
        initComponents();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        khoSachLbl1 = new javax.swing.JLabel();
        hoaDonLbl1 = new javax.swing.JLabel();
        phieuNhapLbl1 = new javax.swing.JLabel();
        phanQuyenLbl1 = new javax.swing.JLabel();
        thongKeLbl1 = new javax.swing.JLabel();
        nhaXuatBanLbl1 = new javax.swing.JLabel();
        khachHangLbl1 = new javax.swing.JLabel();
        nhanVienLbl1 = new javax.swing.JLabel();

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo.png"))); // NOI18N

        jPanel1.setLayout(new java.awt.GridLayout(8, 1));

        khoSachLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        khoSachLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        khoSachLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/wareHouse.png"))); // NOI18N
        khoSachLbl1.setText("Kho Sách");
        khoSachLbl1.setOpaque(true);
        jPanel1.add(khoSachLbl1);

        hoaDonLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hoaDonLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hoaDonLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill.png"))); // NOI18N
        hoaDonLbl1.setText("Hoá Đơn");
        hoaDonLbl1.setOpaque(true);
        jPanel1.add(hoaDonLbl1);

        phieuNhapLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        phieuNhapLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        phieuNhapLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/phieuNhap.png"))); // NOI18N
        phieuNhapLbl1.setText("Phiếu Nhập");
        phieuNhapLbl1.setOpaque(true);
        jPanel1.add(phieuNhapLbl1);

        phanQuyenLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        phanQuyenLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        phanQuyenLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png"))); // NOI18N
        phanQuyenLbl1.setText("Phân Quyền");
        phanQuyenLbl1.setOpaque(true);
        jPanel1.add(phanQuyenLbl1);

        thongKeLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        thongKeLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        thongKeLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/statistics.png"))); // NOI18N
        thongKeLbl1.setText("Thống Kê");
        thongKeLbl1.setOpaque(true);
        jPanel1.add(thongKeLbl1);

        nhaXuatBanLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nhaXuatBanLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nhaXuatBanLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/publisher.png"))); // NOI18N
        nhaXuatBanLbl1.setText("Nhà Xuất Bản");
        nhaXuatBanLbl1.setOpaque(true);
        jPanel1.add(nhaXuatBanLbl1);

        khachHangLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        khachHangLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        khachHangLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png"))); // NOI18N
        khachHangLbl1.setText("Khách Hàng");
        khachHangLbl1.setOpaque(true);
        jPanel1.add(khachHangLbl1);

        nhanVienLbl1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nhanVienLbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nhanVienLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        nhanVienLbl1.setText("Nhân Viên");
        nhanVienLbl1.setOpaque(true);
        jPanel1.add(nhanVienLbl1);

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hoaDonLbl1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel khachHangLbl1;
    private javax.swing.JLabel khoSachLbl1;
    private javax.swing.JLabel nhaXuatBanLbl1;
    private javax.swing.JLabel nhanVienLbl1;
    private javax.swing.JLabel phanQuyenLbl1;
    private javax.swing.JLabel phieuNhapLbl1;
    private javax.swing.JLabel thongKeLbl1;
    // End of variables declaration//GEN-END:variables
}
