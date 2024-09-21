package gui;

import bus.ChiTietQuyenBUS;
import bus.NhanVienBUS;
import bus.NhomQuyenBUS;
import component.ToolBarAndTableBanSach;
import component.ToolBarAndTableHoaDon;
import component.ToolBarAndTableKhachHang;
import component.ToolBarAndTableNCC;
import component.ToolBarAndTableNhaXuatBan;
import component.ToolBarAndTableNhanVien;
import component.ToolBarAndTableNhapSach;
import component.ToolBarAndTablePhanQuyen;
import component.ToolBarAndTablePhieuNhap;
import component.ToolBarAndTableSach;
import component.ToolBarAndTableTacGia;
import component.ToolBarAndTableTaiKhoan;
import component.ToolBarAndTableThongKe;
import component.changeProfileComponent.ChangeProfile;
import component.sachcomponent.UpdateSach;
import dto.ChiTietQuyenDTO;
import dto.NhanVienDTO;
import dto.TaiKhoanDTO;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import services.ExcelUtil;
import java.awt.SystemColor;
import java.awt.Dimension;

public class MainView extends javax.swing.JFrame {

    private JLabel current = null;
    private Color primaryColor = new Color(0,153,153);
    private Color selecttedColor = new Color(0, 206, 209);
    private boolean isLogin = true;
    private TaiKhoanDTO tk;
    private NhanVienDTO nv;

    public NhanVienDTO getNv() {
        return this.nv;
    }

    public MainView(TaiKhoanDTO tk) {
        setBackground(Color.WHITE);
        this.tk = tk;

        NhanVienDTO nvLoggedIn = new NhanVienDTO();
        nvLoggedIn.setMaNV(tk.getMaNV());
        nv = NhanVienBUS.getInstance().getById(nvLoggedIn);

        initComponents();
        this.setTitle("Cửa hàng sách");

        // Sau khi đăng nhập
        userNameLbl.setText(tk.getMaNV());

        toolBarAndTableWrapper.removeAll();
        if (this.current == null) {
            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(defaultToolBarAndTable);
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q1")) {
            banHangLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q2")) {
            hoaDonLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q3")) {
            khachHangLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q4")) {
            nhaCungCapLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q5")) {
            nhapHangLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q6")) {
            nhanVienLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q7")) {
            phieuNhapLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q8")) {
            phanQuyenLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q9")) {
            khoSachLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q10")) {
            taiKhoanLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q11")) {
            thongKeLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q12")) {
            tacGiaLbl.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q13")) {
            nhaXuatBanLbl.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        borderPanel1 = new gui.BorderPanel();
        headerPnl = new javax.swing.JPanel();
        headingLbl = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        userNameLbl = new javax.swing.JLabel();
        logOutLbl = new javax.swing.JLabel();
        toolBarAndTableWrapper = new javax.swing.JPanel();
        defaultToolBarAndTable = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        khoSachLbl = new javax.swing.JLabel();
        hoaDonLbl = new javax.swing.JLabel();
        phieuNhapLbl = new javax.swing.JLabel();
        phanQuyenLbl = new javax.swing.JLabel();
        thongKeLbl = new javax.swing.JLabel();
        nhaXuatBanLbl = new javax.swing.JLabel();
        khachHangLbl = new javax.swing.JLabel();
        nhanVienLbl = new javax.swing.JLabel();
        taiKhoanLbl = new javax.swing.JLabel();
        tacGiaLbl = new javax.swing.JLabel();
        banHangLbl = new javax.swing.JLabel();
        nhapHangLbl = new javax.swing.JLabel();
        nhaCungCapLbl = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        borderPanel1.setBackground(new java.awt.Color(153, 153, 153));
        borderPanel1.setForeground(new java.awt.Color(255, 255, 255));

        headerPnl.setBackground(new java.awt.Color(255, 255, 102));
        headerPnl.setLayout(new java.awt.BorderLayout());

        headingLbl.setBackground(new java.awt.Color(51, 102, 255));
        headingLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        headingLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headingLbl.setText("HỆ THỐNG QUẢN LÍ CỬA HÀNG SÁCH");
        headingLbl.setPreferredSize(new java.awt.Dimension(800, 32));
        headerPnl.add(headingLbl, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 20));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 92));
        jPanel3.setLayout(new java.awt.BorderLayout());

        userNameLbl.setBackground(new java.awt.Color(51, 102, 255));
        userNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userNameLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png"))); // NOI18N
        userNameLbl.setText("Duong Cong Man");
        userNameLbl.setPreferredSize(new java.awt.Dimension(140, 42));
        userNameLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userNameLblMouseClicked(evt);
            }
        });
        jPanel3.add(userNameLbl, java.awt.BorderLayout.CENTER);

        logOutLbl.setBackground(new java.awt.Color(51, 102, 255));
        logOutLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        logOutLbl.setIcon(new javax.swing.ImageIcon("D:\\Work Space\\doAn\\doAnJava\\quanlycuahangsach\\src\\main\\java\\icon\\logOut.png")); // NOI18N
        logOutLbl.setText("Đăng xuất");
        logOutLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutLblMouseClicked(evt);
            }
        });
        jPanel3.add(logOutLbl, java.awt.BorderLayout.LINE_END);

        headerPnl.add(jPanel3, java.awt.BorderLayout.EAST);

        toolBarAndTableWrapper.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout defaultToolBarAndTableLayout = new javax.swing.GroupLayout(defaultToolBarAndTable);
        defaultToolBarAndTable.setLayout(defaultToolBarAndTableLayout);
        defaultToolBarAndTableLayout.setHorizontalGroup(
            defaultToolBarAndTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1147, Short.MAX_VALUE)
        );
        defaultToolBarAndTableLayout.setVerticalGroup(
            defaultToolBarAndTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
        );

        toolBarAndTableWrapper.add(defaultToolBarAndTable, "card3");

        jLabel10.setBackground(new java.awt.Color(0, 153, 153));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo.png"))); // NOI18N
        jLabel10.setOpaque(true);

        jScrollPane1.setBackground(new java.awt.Color(0, 153, 153));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setPreferredSize(new java.awt.Dimension(207, 546));
        jPanel2.setLayout(new java.awt.GridLayout(13, 1));

        khoSachLbl.setBackground(new java.awt.Color(0, 153, 153));
        khoSachLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        khoSachLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        khoSachLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/wareHouse.png"))); // NOI18N
        khoSachLbl.setText("Kho Sách");
        khoSachLbl.setMinimumSize(new java.awt.Dimension(125, 40));
        khoSachLbl.setOpaque(true);
        khoSachLbl.setPreferredSize(new java.awt.Dimension(200, 40));
        khoSachLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                khoSachLblMouseClicked(evt);
            }
        });
        jPanel2.add(khoSachLbl);

        hoaDonLbl.setBackground(new java.awt.Color(0, 153, 153));
        hoaDonLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        hoaDonLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hoaDonLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill.png"))); // NOI18N
        hoaDonLbl.setText("Hoá Đơn");
        hoaDonLbl.setOpaque(true);
        hoaDonLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        hoaDonLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoaDonLblMouseClicked(evt);
            }
        });
        jPanel2.add(hoaDonLbl);

        phieuNhapLbl.setBackground(new java.awt.Color(0, 153, 153));
        phieuNhapLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        phieuNhapLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        phieuNhapLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/phieuNhap.png"))); // NOI18N
        phieuNhapLbl.setText("Phiếu Nhập");
        phieuNhapLbl.setOpaque(true);
        phieuNhapLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        phieuNhapLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phieuNhapLblMouseClicked(evt);
            }
        });
        jPanel2.add(phieuNhapLbl);

        phanQuyenLbl.setBackground(new java.awt.Color(0, 153, 153));
        phanQuyenLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        phanQuyenLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        phanQuyenLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png"))); // NOI18N
        phanQuyenLbl.setText("Phân Quyền");
        phanQuyenLbl.setOpaque(true);
        phanQuyenLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        phanQuyenLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phanQuyenLblMouseClicked(evt);
            }
        });
        jPanel2.add(phanQuyenLbl);

        thongKeLbl.setBackground(new java.awt.Color(0, 153, 153));
        thongKeLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        thongKeLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        thongKeLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/statistics.png"))); // NOI18N
        thongKeLbl.setText("Thống Kê");
        thongKeLbl.setOpaque(true);
        thongKeLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        thongKeLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongKeLblMouseClicked(evt);
            }
        });
        jPanel2.add(thongKeLbl);

        nhaXuatBanLbl.setBackground(new java.awt.Color(0, 153, 153));
        nhaXuatBanLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nhaXuatBanLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nhaXuatBanLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/publisher.png"))); // NOI18N
        nhaXuatBanLbl.setText("Nhà Xuất Bản");
        nhaXuatBanLbl.setOpaque(true);
        nhaXuatBanLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        nhaXuatBanLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhaXuatBanLblMouseClicked(evt);
            }
        });
        jPanel2.add(nhaXuatBanLbl);

        khachHangLbl.setBackground(new java.awt.Color(0, 153, 153));
        khachHangLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        khachHangLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        khachHangLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png"))); // NOI18N
        khachHangLbl.setText("Khách Hàng");
        khachHangLbl.setOpaque(true);
        khachHangLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        khachHangLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                khachHangLblMouseClicked(evt);
            }
        });
        jPanel2.add(khachHangLbl);

        nhanVienLbl.setBackground(new java.awt.Color(0, 153, 153));
        nhanVienLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nhanVienLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nhanVienLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        nhanVienLbl.setText("Nhân Viên");
        nhanVienLbl.setOpaque(true);
        nhanVienLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        nhanVienLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienLblMouseClicked(evt);
            }
        });
        jPanel2.add(nhanVienLbl);

        taiKhoanLbl.setBackground(new java.awt.Color(0, 153, 153));
        taiKhoanLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        taiKhoanLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        taiKhoanLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/userLogin.png"))); // NOI18N
        taiKhoanLbl.setText("Tài khoản");
        taiKhoanLbl.setOpaque(true);
        taiKhoanLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        taiKhoanLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taiKhoanLblMouseClicked(evt);
            }
        });
        jPanel2.add(taiKhoanLbl);

        tacGiaLbl.setBackground(new java.awt.Color(0, 153, 153));
        tacGiaLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tacGiaLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tacGiaLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png"))); // NOI18N
        tacGiaLbl.setText("Tác Giả");
        tacGiaLbl.setOpaque(true);
        tacGiaLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        tacGiaLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tacGiaLblMouseClicked(evt);
            }
        });
        jPanel2.add(tacGiaLbl);

        banHangLbl.setBackground(new java.awt.Color(0, 153, 153));
        banHangLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        banHangLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        banHangLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sell.png"))); // NOI18N
        banHangLbl.setText("Bán Hàng");
        banHangLbl.setOpaque(true);
        banHangLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        banHangLbl.setRequestFocusEnabled(false);
        banHangLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                banHangLblMouseClicked(evt);
            }
        });
        jPanel2.add(banHangLbl);

        nhapHangLbl.setBackground(new java.awt.Color(0, 153, 153));
        nhapHangLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nhapHangLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nhapHangLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/import.png"))); // NOI18N
        nhapHangLbl.setText("Nhập hàng");
        nhapHangLbl.setOpaque(true);
        nhapHangLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        nhapHangLbl.setRequestFocusEnabled(false);
        nhapHangLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhapHangLblMouseClicked(evt);
            }
        });
        jPanel2.add(nhapHangLbl);

        nhaCungCapLbl.setBackground(new java.awt.Color(0, 153, 153));
        nhaCungCapLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nhaCungCapLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nhaCungCapLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/wareHouse.png"))); // NOI18N
        nhaCungCapLbl.setText("Nhà Cung Cấp");
        nhaCungCapLbl.setOpaque(true);
        nhaCungCapLbl.setPreferredSize(new java.awt.Dimension(200, 42));
        nhaCungCapLbl.setRequestFocusEnabled(false);
        nhaCungCapLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhaCungCapLblMouseClicked(evt);
            }
        });
        jPanel2.add(nhaCungCapLbl);

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout borderPanel1Layout = new javax.swing.GroupLayout(borderPanel1);
        borderPanel1.setLayout(borderPanel1Layout);
        borderPanel1Layout.setHorizontalGroup(
            borderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderPanel1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(borderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(toolBarAndTableWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        borderPanel1Layout.setVerticalGroup(
            borderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(borderPanel1Layout.createSequentialGroup()
                .addComponent(headerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolBarAndTableWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(borderPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(borderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void khoSachLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khoSachLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q9")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != khoSachLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = khoSachLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableSach(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_khoSachLblMouseClicked

    private void hoaDonLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q2")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != hoaDonLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = hoaDonLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableHoaDon(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_hoaDonLblMouseClicked

    private void phieuNhapLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phieuNhapLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q7")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != phieuNhapLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = phieuNhapLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTablePhieuNhap(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_phieuNhapLblMouseClicked

    private void phanQuyenLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phanQuyenLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q8")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != phanQuyenLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = phanQuyenLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTablePhanQuyen(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_phanQuyenLblMouseClicked

    private void thongKeLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongKeLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q11")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != thongKeLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = thongKeLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableThongKe(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_thongKeLblMouseClicked

    private void nhaXuatBanLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhaXuatBanLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q13")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != nhaXuatBanLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = nhaXuatBanLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableNhaXuatBan(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_nhaXuatBanLblMouseClicked

    private void khachHangLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khachHangLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q3")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != khachHangLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = khachHangLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableKhachHang(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_khachHangLblMouseClicked

    private void nhanVienLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q6")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != nhanVienLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = nhanVienLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableNhanVien(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_nhanVienLblMouseClicked

    private void tacGiaLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tacGiaLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q12")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (current != tacGiaLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = tacGiaLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableTacGia(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_tacGiaLblMouseClicked

    private void banHangLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banHangLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q1")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != banHangLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = banHangLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableBanSach(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_banHangLblMouseClicked

    private void nhapHangLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhapHangLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q5")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != nhapHangLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = nhapHangLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableNhapSach(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_nhapHangLblMouseClicked

    private void nhaCungCapLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhaCungCapLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q4")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (current != nhaCungCapLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = nhaCungCapLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableNCC(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_nhaCungCapLblMouseClicked

    private void taiKhoanLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taiKhoanLblMouseClicked
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q10")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (current != taiKhoanLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = taiKhoanLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableTaiKhoan(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }//GEN-LAST:event_taiKhoanLblMouseClicked

    private void logOutLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutLblMouseClicked
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Thông báo!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            newLoginForm lgForm = new newLoginForm();
            lgForm.setLocationRelativeTo(null);
            lgForm.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logOutLblMouseClicked

    private void userNameLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userNameLblMouseClicked
        ChangeProfile cp = new ChangeProfile(this, isLogin, this);
        cp.setLocationRelativeTo(this);
        cp.setVisible(true);
    }//GEN-LAST:event_userNameLblMouseClicked

    public void doBanHangAction() {
        if (current != banHangLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = banHangLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableBanSach(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }

    public void doNhapHangAction() {
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXemQ(nv.getMaNhomQuyen(), "Q5")) {
            JOptionPane.showMessageDialog(this, "Bạn không có thẩm quyền truy cập!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (current != nhapHangLbl) {
            if (this.current != null) {
                this.current.setBackground(primaryColor);
            }
            this.current = nhapHangLbl;
            this.current.setBackground(selecttedColor);

            toolBarAndTableWrapper.removeAll();
            toolBarAndTableWrapper.add(new ToolBarAndTableNhapSach(this));
            toolBarAndTableWrapper.repaint();
            toolBarAndTableWrapper.revalidate();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel banHangLbl;
    private gui.BorderPanel borderPanel1;
    private javax.swing.JPanel defaultToolBarAndTable;
    private javax.swing.JPanel headerPnl;
    private javax.swing.JLabel headingLbl;
    private javax.swing.JLabel hoaDonLbl;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel khachHangLbl;
    private javax.swing.JLabel khoSachLbl;
    private javax.swing.JLabel logOutLbl;
    private javax.swing.JLabel nhaCungCapLbl;
    private javax.swing.JLabel nhaXuatBanLbl;
    private javax.swing.JLabel nhanVienLbl;
    private javax.swing.JLabel nhapHangLbl;
    private javax.swing.JLabel phanQuyenLbl;
    private javax.swing.JLabel phieuNhapLbl;
    private javax.swing.JLabel tacGiaLbl;
    private javax.swing.JLabel taiKhoanLbl;
    private javax.swing.JLabel thongKeLbl;
    private javax.swing.JPanel toolBarAndTableWrapper;
    private javax.swing.JLabel userNameLbl;
    // End of variables declaration//GEN-END:variables
}
