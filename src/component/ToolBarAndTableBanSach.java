/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.ChiTietHoaDonBUS;
import bus.HoaDonBUS;
import bus.KhachHangBUS;
import bus.SachBUS;
import component.muaSachComponent.ThanhToanMuaSach;
import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;
import dto.KhachHangDTO;
import dto.SachDTO;
import gui.MainView;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author mansh
 */
public class ToolBarAndTableBanSach extends javax.swing.JPanel {

    private MainView mv;
    private ArrayList<SachDTO> listS = SachBUS.getInstance().getAll();
    private ArrayList<SachDTO> listSelectedSach = new ArrayList<SachDTO>();
    private ArrayList<KhachHangDTO> listKh = KhachHangBUS.getInstance().getAll();

    public void refreshDataListS() {
        this.listS = SachBUS.getInstance().getAll();
    }

    public ArrayList<SachDTO> getListS() {
        return this.listS;
    }

    public JTabbedPane getJTab() {
        return this.jTabbedPane1;
    }

    public ArrayList<SachDTO> getListSelectedSach() {
        return this.listSelectedSach;
    }

    public void refrestTableData() {
        this.listS = SachBUS.getInstance().getAll();
        this.listSelectedSach.clear();
        loadDataSach(listS, sachTbl3);
        loadDataSach(listSelectedSach, selectedSachTbl3);
        soTienPhaiTraLbl.setText("");
    }

    /**
     * Creates new form ToolBarAndTableBanSach
     */
    public ToolBarAndTableBanSach(MainView mv) {
        initComponents();
        this.mv = mv;
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mã sách");
        filterConditionCbx.addItem("Theo mã NXB");
        filterConditionCbx.addItem("Theo thể loại");
        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        filterByPrice.add(filterGiaBanRadio);
        filterByPrice.add(filterGiaNhapRadio);
        loadDataSach(listS, sachTbl3);
        loadDataSach(listSelectedSach, selectedSachTbl3);
        jTabbedPane1.setEnabledAt(2, false);
        jTabbedPane1.setEnabledAt(1, false);
        cardAddShow.removeAll();
    }

    public void loadDataSach(ArrayList<SachDTO> listS, JTable tbl) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã sách");
        defaultTableModel.addColumn("Ảnh");
        defaultTableModel.addColumn("Tên sách");
        defaultTableModel.addColumn("Số lượng");
        defaultTableModel.addColumn("Giá bán");
        defaultTableModel.addColumn("Thể loại");

        for (SachDTO sach : listS) {
            if (sach.getTrangThai() == 1) {
                defaultTableModel.addRow(new Object[]{
                    sach.getMaSach(),
                    sach.getPhoto(),
                    sach.getTenSach(),
                    sach.getSoLuong(),
                    sach.getGiaBan(),
                    sach.getTheLoai(),});
            }
//            System.out.println(sach);
        }
        tbl.setModel(defaultTableModel);
        tbl.setRowHeight(100);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
    }

    private class ImageRender extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setOpaque(true);

            if (value instanceof String) {
                String photoName = (String) value;
                // Kiểm tra tệp ảnh tồn tại
                File imageFile = new File("D:\\Work Space\\doAn\\doAnJava\\quanlycuahangsach\\src\\main\\java\\icon\\" + photoName);
                if (imageFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageFile.getAbsolutePath()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                    label.setIcon(imageIcon);
                } else {
                    // Xử lý nếu tệp không tồn tại
                    label.setText("Ảnh không tồn tại!");
                }
            } else {
                // Xử lý nếu giá trị không phải là chuỗi (tên tệp ảnh)
                label.setText("Ảnh chưa cập nhật!");
            }

            return label;
        }
    }

    public String getSelectedItemIdKhoSach() {
        DefaultTableModel dtm = (DefaultTableModel) sachTbl3.getModel();
        int selectedRowIndex = sachTbl3.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public String getSelectedItemSachMua() {
        DefaultTableModel dtm = (DefaultTableModel) selectedSachTbl3.getModel();
        int selectedRowIndex = selectedSachTbl3.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public SachDTO getSachById(ArrayList<SachDTO> list, String id) {
        for (SachDTO s : list) {
            if (s.getMaSach().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public double tinhTongTien(ArrayList<SachDTO> list) {
        double tongTien = 0;
        for (SachDTO s : list) {
            tongTien += s.getGiaBan() * s.getSoLuong();
        }
        return tongTien;
    }

    public void showDetailHoaDon(KhachHangDTO kh) {
        jTabbedPane1.setSelectedIndex(2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date today = new java.util.Date();
        Date dateNeedFormat = new Date(today.getTime());
        String maHoaDon = "";
        int lastElementId = HoaDonBUS.getInstance().getLastElementId();
        if (lastElementId == -1) {
            maHoaDon = "HD" + 1;
        } else {
            maHoaDon = "HD" + (lastElementId + 1);
        }
        String maNV = mv.getNv().getMaNV();
        String tenNV = mv.getNv().getTenNV();
        String maKH = "";
        if (kh.getMaKH() == null || kh.getMaKH() == "" || kh.getMaKH().isEmpty()) {
            maKH = "Vãng lai";
        } else {
            maKH = kh.getMaKH();
        }
        String ngayLap = dateFormat.format(dateNeedFormat);
        String thongTinUuDai = "Có áp dụng";
        String tongTien = tinhTongTien(listSelectedSach) + "";

        maHoaDonLbl.setText(maHoaDon);
        ngayLapLbl.setText(ngayLap);
        maKhachHangLbl.setText(maKH);
        maNhanVienLbl.setText(maNV);
        tenNhanVienLbl.setText(tenNV);
        tongGiaTriLbl.setText(tongTien);
        giamGiaLbl.setText(thongTinUuDai);
        soTienCanTraLbl.setText(Double.parseDouble(tongTien) + "");

        loadDataSach(listSelectedSach, sachMuonMuaTbl);
    }

    public HoaDonDTO createHoaDon() {
        String maNV = maNhanVienLbl.getText();
        String maKH = maKhachHangLbl.getText();
        String ngayLap = ngayLapLbl.getText();
        String thongTinUuDai = giamGiaLbl.getText();
        String tongTien = soTienCanTraLbl.getText();

        String errors = HoaDonBUS.getInstance().validateData(maNV, maKH, ngayLap, thongTinUuDai, tongTien);
        if (errors == null || errors.isEmpty()) {
            if (maKH.equals("Vãng lai")) maKH = null;
            HoaDonDTO hd = HoaDonBUS.getInstance().createNewHD(maNV, maKH, ngayLap, thongTinUuDai, tongTien);
            return hd;
        } else {
            JOptionPane.showMessageDialog(this, errors, "Thêm hoá đơn thất bại!", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    public ArrayList<ChiTietHoaDonDTO> createChiTietHoaDon() {
        String maHoaDon = maHoaDonLbl.getText();
        
        String errors = ChiTietHoaDonBUS.getInstance().validateData(maHoaDon, listSelectedSach);

        if (errors == null || errors.isEmpty()) {
            ArrayList<ChiTietHoaDonDTO> cthdList = ChiTietHoaDonBUS.getInstance().createNewCTHD(maHoaDon, listSelectedSach);
            return cthdList;
        } else {
            JOptionPane.showMessageDialog(this, errors, "Thêm hoá đơn thất bại!", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        filterByPrice = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        toolBar2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        searchIcon8 = new javax.swing.JLabel();
        filterValueTxtFld2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filterConditionCbx = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        sortCbx = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        filterGiaNhapRadio = new javax.swing.JRadioButton();
        filterGiaBanRadio = new javax.swing.JRadioButton();
        filterByPricePanel = new javax.swing.JPanel();
        giaTuTxtFld = new javax.swing.JTextField();
        giaDenTxtFld = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        sachTbl3 = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        selectedSachTbl3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        huyMuaBtn = new javax.swing.JButton();
        muaBtn = new javax.swing.JButton();
        soTienPhaiTraLbl = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        khachHangSdtTxt = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        searchKhBtn = new javax.swing.JButton();
        addKhBtn = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        cardAddShow = new javax.swing.JPanel();
        addKhPane = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        addKhTenTxt = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        addKhAddressTxt = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        addKhSdtTxt = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        cancelAddKhBtn = new javax.swing.JButton();
        addKhAndContinueBtn = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        in4KhPane = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        in4TenTxt = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        in4MaTxt = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        in4AddressTxt = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        in4PhoneTxt = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        in4TichDiemTxt = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        maHoaDonLbl = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        ngayLapLbl = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        maKhachHangLbl = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        maNhanVienLbl = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tenNhanVienLbl = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        tongGiaTriLbl = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        giamGiaLbl = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        soTienCanTraLbl = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sachMuonMuaTbl = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        huyMuaTab3Btn = new javax.swing.JButton();
        thanhToanBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setToolTipText("");
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1135, 100));

        toolBar2.setBackground(new java.awt.Color(51, 102, 255));
        toolBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        toolBar2.setLayout(new java.awt.GridBagLayout());

        searchIcon8.setIcon(new javax.swing.ImageIcon("D:\\Work Space\\doAn\\doAnJava\\quanlycuahangsach\\src\\main\\java\\icon\\search_40.png")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(searchIcon8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterValueTxtFld2, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchIcon8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(filterValueTxtFld2)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        toolBar2.add(jPanel9, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(118, 50));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Điều kiện lọc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filterConditionCbx, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(filterConditionCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        toolBar2.add(jPanel1, new java.awt.GridBagConstraints());

        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Sắp xếp");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(sortCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        toolBar2.add(jPanel2, new java.awt.GridBagConstraints());

        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 70));

        filterGiaNhapRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterGiaNhapRadio.setText("Lọc theo giá nhập");
        filterGiaNhapRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterGiaNhapRadioMouseClicked(evt);
            }
        });

        filterGiaBanRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterGiaBanRadio.setText("Lọc theo giá bán");
        filterGiaBanRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterGiaBanRadioMouseClicked(evt);
            }
        });
        filterGiaBanRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterGiaBanRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filterGiaNhapRadio)
                    .addComponent(filterGiaBanRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterGiaNhapRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(filterGiaBanRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        toolBar2.add(jPanel10, new java.awt.GridBagConstraints());

        jLabel4.setText("  Đến: ");

        jLabel5.setText("  Từ: ");

        javax.swing.GroupLayout filterByPricePanelLayout = new javax.swing.GroupLayout(filterByPricePanel);
        filterByPricePanel.setLayout(filterByPricePanelLayout);
        filterByPricePanelLayout.setHorizontalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterByPricePanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filterByPricePanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(giaDenTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                    .addGroup(filterByPricePanelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(giaTuTxtFld)))
                .addContainerGap())
        );
        filterByPricePanelLayout.setVerticalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterByPricePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(giaTuTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(giaDenTxtFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        toolBar2.add(filterByPricePanel, new java.awt.GridBagConstraints());

        jPanel3.setOpaque(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton1.setText("Lọc");
        jButton1.setPreferredSize(new java.awt.Dimension(75, 50));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        toolBar2.add(jPanel3, new java.awt.GridBagConstraints());

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(320, 58));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 8, 6));

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/book-add-icon.png"))); // NOI18N
        addBtn.setText("Thêm");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        jPanel4.add(addBtn);

        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil-icon.png"))); // NOI18N
        updateBtn.setText("Sửa");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        jPanel4.add(updateBtn);

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        deleteBtn.setText("Xoá");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });
        jPanel4.add(deleteBtn);

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton6.setText("Huỷ lọc");
        jPanel4.add(jButton6);

        toolBar2.add(jPanel4, new java.awt.GridBagConstraints());

        jScrollPane1.setViewportView(toolBar2);

        add(jScrollPane1, java.awt.BorderLayout.NORTH);

        sachTbl3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        sachTbl3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sachTbl3MouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(sachTbl3);

        selectedSachTbl3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(selectedSachTbl3);

        jPanel7.setPreferredSize(new java.awt.Dimension(1135, 60));

        huyMuaBtn.setText("Huỷ mua");
        huyMuaBtn.setPreferredSize(new java.awt.Dimension(100, 42));
        huyMuaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                huyMuaBtnMouseClicked(evt);
            }
        });

        muaBtn.setText("Mua");
        muaBtn.setPreferredSize(new java.awt.Dimension(100, 42));
        muaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                muaBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(huyMuaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(muaBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(soTienPhaiTraLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addGap(407, 407, 407))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(soTienPhaiTraLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(huyMuaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(muaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(2, 2, 2)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(646, 646, 646))
        );

        jScrollPane11.setViewportView(jPanel11);

        jTabbedPane1.addTab("tab1", jScrollPane11);

        jPanel5.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel33.setLayout(new java.awt.GridLayout(1, 3));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Nhập số điện thoại");
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel33.add(jLabel19);
        jPanel33.add(khachHangSdtTxt);

        searchKhBtn.setText("Xác nhận");
        searchKhBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchKhBtnMouseClicked(evt);
            }
        });
        jPanel6.add(searchKhBtn);

        addKhBtn.setText("Thêm khách hàng");
        addKhBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addKhBtnMouseClicked(evt);
            }
        });
        jPanel6.add(addKhBtn);

        jButton12.setText("Bỏ qua");
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        jPanel6.add(jButton12);

        jPanel33.add(jPanel6);

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1156, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel49Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel49Layout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(34, 34, 34)))
        );

        jPanel5.add(jPanel49, java.awt.BorderLayout.NORTH);

        cardAddShow.setLayout(new java.awt.CardLayout());

        addKhPane.setPreferredSize(new java.awt.Dimension(1147, 300));

        jPanel8.setPreferredSize(new java.awt.Dimension(1146, 305));
        jPanel8.setLayout(new java.awt.GridLayout(5, 3, 0, 10));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel17);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Thêm khách hàng");
        jPanel8.add(jLabel3);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel13);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Tên khách hàng");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel8.add(jLabel6);

        addKhTenTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addKhTenTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addKhTenTxt)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(addKhTenTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel25);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel22);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Địa chỉ");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel8.add(jLabel7);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(addKhAddressTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(addKhAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel28);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel26);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Số điện thoại");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel8.add(jLabel8);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addComponent(addKhSdtTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(addKhSdtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel31);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel24);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel27);

        cancelAddKhBtn.setText("Huỷ bỏ");
        cancelAddKhBtn.setMaximumSize(new java.awt.Dimension(80, 40));
        cancelAddKhBtn.setMinimumSize(new java.awt.Dimension(80, 40));
        cancelAddKhBtn.setPreferredSize(new java.awt.Dimension(80, 40));
        cancelAddKhBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelAddKhBtnMouseClicked(evt);
            }
        });

        addKhAndContinueBtn.setText("Tiếp tục");
        addKhAndContinueBtn.setMaximumSize(new java.awt.Dimension(80, 40));
        addKhAndContinueBtn.setMinimumSize(new java.awt.Dimension(80, 40));
        addKhAndContinueBtn.setPreferredSize(new java.awt.Dimension(80, 40));
        addKhAndContinueBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addKhAndContinueBtnMouseClicked(evt);
            }
        });
        addKhAndContinueBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addKhAndContinueBtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(cancelAddKhBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(111, 111, 111)
                .addComponent(addKhAndContinueBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addKhAndContinueBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelAddKhBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        jPanel8.add(jPanel29);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel30);

        javax.swing.GroupLayout addKhPaneLayout = new javax.swing.GroupLayout(addKhPane);
        addKhPane.setLayout(addKhPaneLayout);
        addKhPaneLayout.setHorizontalGroup(
            addKhPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addKhPaneLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        addKhPaneLayout.setVerticalGroup(
            addKhPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );

        cardAddShow.add(addKhPane, "card2");

        in4KhPane.setPreferredSize(new java.awt.Dimension(1147, 200));

        jPanel14.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel14.setLayout(new java.awt.GridLayout(7, 3));

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel50);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Thông tin khách hàng");
        jPanel14.add(jLabel10);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel16);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tên khách hàng");
        jLabel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel14.add(jLabel11);

        in4TenTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        in4TenTxt.setText("Dương Công Mãn");
        jPanel14.add(in4TenTxt);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel20);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Mã khách hàng");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel14.add(jLabel13);

        in4MaTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        in4MaTxt.setText("KH1");
        jPanel14.add(in4MaTxt);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel23);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Địa chỉ");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel14.add(jLabel15);

        in4AddressTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        in4AddressTxt.setText("123123");
        jPanel14.add(in4AddressTxt);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel38);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Số điện thoại");
        jLabel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel14.add(jLabel17);

        in4PhoneTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        in4PhoneTxt.setText("0913885625");
        jPanel14.add(in4PhoneTxt);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel41);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Tích điểm");
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel14.add(jLabel20);

        in4TichDiemTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        in4TichDiemTxt.setText("0");
        jPanel14.add(in4TichDiemTxt);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel44);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel45);

        jButton8.setText("Huỷ");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jPanel46.add(jButton8);

        jButton9.setText("Tiếp tục");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jPanel46.add(jButton9);

        jPanel14.add(jPanel46);

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel47);

        javax.swing.GroupLayout in4KhPaneLayout = new javax.swing.GroupLayout(in4KhPane);
        in4KhPane.setLayout(in4KhPaneLayout);
        in4KhPaneLayout.setHorizontalGroup(
            in4KhPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
        );
        in4KhPaneLayout.setVerticalGroup(
            in4KhPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );

        cardAddShow.add(in4KhPane, "card3");

        jPanel5.add(cardAddShow, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab2", jPanel5);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel15.setLayout(new java.awt.GridLayout(8, 2, 10, 0));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Mã hoá đơn:");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel14);

        maHoaDonLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maHoaDonLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(maHoaDonLbl);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("Ngày lập: ");
        jLabel32.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel32);

        ngayLapLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ngayLapLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(ngayLapLbl);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("Mã khách hàng");
        jLabel30.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel30);

        maKhachHangLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maKhachHangLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(maKhachHangLbl);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("Mã nhân viên: ");
        jLabel28.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel28);

        maNhanVienLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maNhanVienLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(maNhanVienLbl);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Tên nhân viên: ");
        jLabel26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel26);

        tenNhanVienLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tenNhanVienLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(tenNhanVienLbl);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Tổng giá trị hoá đơn: ");
        jLabel24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel24);

        tongGiaTriLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tongGiaTriLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(tongGiaTriLbl);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Thông tin giảm giá:");
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel22);

        giamGiaLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        giamGiaLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(giamGiaLbl);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Số tiền cần phải trả:");
        jLabel18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(jLabel18);

        soTienCanTraLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        soTienCanTraLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        jPanel15.add(soTienCanTraLbl);

        jPanel12.add(jPanel15, java.awt.BorderLayout.WEST);

        sachMuonMuaTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(sachMuonMuaTbl);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Chi tiết hoá đơn");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel18, java.awt.BorderLayout.CENTER);

        huyMuaTab3Btn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        huyMuaTab3Btn.setText("Huỷ bỏ");
        huyMuaTab3Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                huyMuaTab3BtnMouseClicked(evt);
            }
        });
        jPanel19.add(huyMuaTab3Btn);

        thanhToanBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        thanhToanBtn.setText("Thanh toán");
        thanhToanBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thanhToanBtnMouseClicked(evt);
            }
        });
        jPanel19.add(thanhToanBtn);

        jPanel12.add(jPanel19, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("tab3", jPanel12);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void filterGiaNhapRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterGiaNhapRadioMouseClicked
        giaTuTxtFld.setEnabled(true);
        giaDenTxtFld.setEnabled(true);
    }//GEN-LAST:event_filterGiaNhapRadioMouseClicked

    private void filterGiaBanRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterGiaBanRadioMouseClicked
        giaTuTxtFld.setEnabled(true);
        giaDenTxtFld.setEnabled(true);
    }//GEN-LAST:event_filterGiaBanRadioMouseClicked

    private void filterGiaBanRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterGiaBanRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterGiaBanRadioActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        // Lấy ra mã sách của quyển sách được chọn từ sachTbl3
        String selectedBookId = getSelectedItemIdKhoSach();
        if (selectedBookId.isEmpty() || selectedBookId == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách muốn mua ở bảng bên trái!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            for (SachDTO sach : listS) {
                if (sach.getMaSach().equals(selectedBookId)) {
                    int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn mua sách " + sach.getTenSach() + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String input = JOptionPane.showInputDialog(mv, "Nhập số lượng:");
                        if (input != null && !input.isEmpty()) {
                            try {
                                int quantity = Integer.parseInt(input);
                                if (quantity > 0 && quantity <= sach.getSoLuong() && sach.getSoLuong() > 0) {
                                    boolean found = false;
                                    for (SachDTO selectedBookIn : listSelectedSach) {
                                        if (selectedBookIn.getMaSach().equals(sach.getMaSach())) {
                                            selectedBookIn.setSoLuong((int) (selectedBookIn.getSoLuong() + quantity));
                                            found = true;
                                        }
                                    }
                                    if (!found) {
                                        SachDTO selectedBook = new SachDTO(sach);
                                        selectedBook.setSoLuong(quantity);
                                        listSelectedSach.add(selectedBook);
                                    }

                                    double tongTien = tinhTongTien(listSelectedSach);
                                    DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
                                    String formattedTongTien = decimalFormat.format(tongTien);
                                    sach.setSoLuong((int) (sach.getSoLuong() - quantity));
                                    soTienPhaiTraLbl.setText("Số tiền phải trả là: " + formattedTongTien);
                                    loadDataSach(listS, sachTbl3);
                                    loadDataSach(listSelectedSach, selectedSachTbl3);
                                } else {
                                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    break;
                }
            }
        }
    }//GEN-LAST:event_addBtnMouseClicked

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        String selectedBookId = getSelectedItemSachMua();
        if (selectedBookId.isEmpty() || selectedBookId == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách muốn sửa ở bảng bên phải!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            for (SachDTO sach : listSelectedSach) {
                if (sach.getMaSach().equals(selectedBookId)) {
                    String input = JOptionPane.showInputDialog(mv, "Thay đổi số lượng sách muốn mua:", sach.getSoLuong());
                    SachDTO sachInList = getSachById(listS, sach.getMaSach());
                    try {
                        int newQuantity = Integer.parseInt(input);
                        if (newQuantity > 0 && newQuantity <= sachInList.getSoLuong()) {
                            sachInList.setSoLuong((int) (sachInList.getSoLuong() + sach.getSoLuong() - newQuantity));
                            sach.setSoLuong(newQuantity);
                            double tongTien = tinhTongTien(listSelectedSach);
                            if (tongTien == 0) {
                                soTienPhaiTraLbl.setText("");
                            } else {
                                DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
                                String formattedTongTien = decimalFormat.format(tongTien);
                                soTienPhaiTraLbl.setText("Số tiền phải trả là: " + formattedTongTien);
                            }
                            loadDataSach(listS, sachTbl3);
                            loadDataSach(listSelectedSach, selectedSachTbl3);
                        } else {
                            JOptionPane.showMessageDialog(mv, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mv, "Vui lòng nhập số lượng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_updateBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        String selectedBookId = getSelectedItemSachMua();
        if (selectedBookId.isEmpty() || selectedBookId == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách muốn xoá ở bảng bên phải!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            for (SachDTO sach : listSelectedSach) {
                if (sach.getMaSach().equals(selectedBookId)) {
                    int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xoá sách " + sach.getTenSach() + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        SachDTO sachInList = getSachById(listS, sach.getMaSach());
                        listSelectedSach.remove(sach);
                        sachInList.setSoLuong((int) (sachInList.getSoLuong() + sach.getSoLuong()));
                        double tongTien = tinhTongTien(listSelectedSach);
                        if (tongTien == 0) {
                            soTienPhaiTraLbl.setText("");
                        } else {
                            DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
                            String formattedTongTien = decimalFormat.format(tongTien);
                            soTienPhaiTraLbl.setText("Số tiền phải trả là: " + formattedTongTien);
                        }
                        loadDataSach(listS, sachTbl3);
                        loadDataSach(listSelectedSach, selectedSachTbl3);
                    }
                }
            }
        }
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void huyMuaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_huyMuaBtnMouseClicked
        for (SachDTO sach : listSelectedSach) {
            int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chăn muốn huỷ mua hàng không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                for (SachDTO s : listS) {
                    if (s.getMaSach().equals(sach.getMaSach())) {
                        s.setSoLuong((int) (s.getSoLuong() + sach.getSoLuong()));
                        break;
                    }
                }
            }
        }
        listSelectedSach.clear();
        loadDataSach(listS, sachTbl3);
        loadDataSach(listSelectedSach, selectedSachTbl3);
    }//GEN-LAST:event_huyMuaBtnMouseClicked

    private void addKhTenTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addKhTenTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addKhTenTxtActionPerformed

    private void searchKhBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchKhBtnMouseClicked
        KhachHangDTO kh = KhachHangBUS.getInstance().getKhachHangByPhone(khachHangSdtTxt.getText());
        if (kh != null) {
            cardAddShow.removeAll();
            cardAddShow.add(in4KhPane);
            cardAddShow.repaint();
            cardAddShow.revalidate();
            in4TenTxt.setText(kh.getTenKH());
            in4MaTxt.setText(kh.getMaKH());
            in4PhoneTxt.setText(kh.getSdt());
        } else {
            JOptionPane.showMessageDialog(mv, "Không tồn tại khách hàng có số điện thoại là " + khachHangSdtTxt.getText(), "Thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchKhBtnMouseClicked

    private void muaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_muaBtnMouseClicked
        if (listSelectedSach.isEmpty()) {
            JOptionPane.showMessageDialog(mv, "Bạn cần chọn sách muốn mua!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_muaBtnMouseClicked

    private void addKhBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addKhBtnMouseClicked
        cardAddShow.removeAll();
        cardAddShow.add(addKhPane);
        cardAddShow.repaint();
        cardAddShow.revalidate();
        khachHangSdtTxt.setText("");
    }//GEN-LAST:event_addKhBtnMouseClicked

    private void addKhAndContinueBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addKhAndContinueBtnKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_addKhAndContinueBtnKeyPressed

    private void addKhAndContinueBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addKhAndContinueBtnMouseClicked
        String tenKH = addKhTenTxt.getText();
        String diaChi = addKhAddressTxt.getText();
        String sdt = addKhSdtTxt.getText();
        String errors = KhachHangBUS.getInstance().validateData(tenKH, diaChi, sdt);
        if (errors == null || errors.isEmpty()) {
            KhachHangDTO kh = KhachHangBUS.getInstance().createNewKH(tenKH, diaChi, sdt);
            int result = KhachHangBUS.getInstance().add(kh);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                showDetailHoaDon(kh);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách thất bại, lỗi hệ thống!", "Thất bại!", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mv, errors, "Thêm khách hàng thất bại", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_addKhAndContinueBtnMouseClicked

    private void cancelAddKhBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelAddKhBtnMouseClicked
        int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn huỷ mua sách?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            cardAddShow.removeAll();
            jTabbedPane1.setSelectedIndex(0);
            refrestTableData();
        }
    }//GEN-LAST:event_cancelAddKhBtnMouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        KhachHangDTO kh = KhachHangBUS.getInstance().getKhachHangByPhone(in4PhoneTxt.getText());
        showDetailHoaDon(kh);
    }//GEN-LAST:event_jButton9MouseClicked

    private void thanhToanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thanhToanBtnMouseClicked
        HoaDonDTO hd = createHoaDon();
        ArrayList<ChiTietHoaDonDTO> cthdList = createChiTietHoaDon();
        if (hd == null || cthdList == null) {
            JOptionPane.showMessageDialog(this, "Thêm mới hoá đơn/ chi tiết hoá đơn thất bại!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ThanhToanMuaSach muaSachFrame = new ThanhToanMuaSach(mv, true, hd, cthdList, this);
        muaSachFrame.setLocationRelativeTo(mv);
        muaSachFrame.setVisible(true);
    }//GEN-LAST:event_thanhToanBtnMouseClicked

    private void huyMuaTab3BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_huyMuaTab3BtnMouseClicked
        int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn huỷ mua sách?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            jTabbedPane1.setSelectedIndex(0);
            refrestTableData();
        }
    }//GEN-LAST:event_huyMuaTab3BtnMouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn huỷ mua sách?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            jTabbedPane1.setSelectedIndex(0);
            refrestTableData();
        }
    }//GEN-LAST:event_jButton8MouseClicked

    private void sachTbl3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sachTbl3MouseClicked
        
    }//GEN-LAST:event_sachTbl3MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        KhachHangDTO kh = new KhachHangDTO();
        showDetailHoaDon(kh);
    }//GEN-LAST:event_jButton12MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField addKhAddressTxt;
    private javax.swing.JButton addKhAndContinueBtn;
    private javax.swing.JButton addKhBtn;
    private javax.swing.JPanel addKhPane;
    private javax.swing.JTextField addKhSdtTxt;
    private javax.swing.JTextField addKhTenTxt;
    private javax.swing.JButton cancelAddKhBtn;
    private javax.swing.JPanel cardAddShow;
    private javax.swing.JButton deleteBtn;
    private javax.swing.ButtonGroup filterByPrice;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JRadioButton filterGiaBanRadio;
    private javax.swing.JRadioButton filterGiaNhapRadio;
    private javax.swing.JTextField filterValueTxtFld2;
    private javax.swing.JTextField giaDenTxtFld;
    private javax.swing.JTextField giaTuTxtFld;
    private javax.swing.JLabel giamGiaLbl;
    private javax.swing.JButton huyMuaBtn;
    private javax.swing.JButton huyMuaTab3Btn;
    private javax.swing.JLabel in4AddressTxt;
    private javax.swing.JPanel in4KhPane;
    private javax.swing.JLabel in4MaTxt;
    private javax.swing.JLabel in4PhoneTxt;
    private javax.swing.JLabel in4TenTxt;
    private javax.swing.JLabel in4TichDiemTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField khachHangSdtTxt;
    private javax.swing.JLabel maHoaDonLbl;
    private javax.swing.JLabel maKhachHangLbl;
    private javax.swing.JLabel maNhanVienLbl;
    private javax.swing.JButton muaBtn;
    private javax.swing.JLabel ngayLapLbl;
    private javax.swing.JTable sachMuonMuaTbl;
    private javax.swing.JTable sachTbl3;
    private javax.swing.JLabel searchIcon8;
    private javax.swing.JButton searchKhBtn;
    private javax.swing.JTable selectedSachTbl3;
    private javax.swing.JLabel soTienCanTraLbl;
    private javax.swing.JLabel soTienPhaiTraLbl;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JLabel tenNhanVienLbl;
    private javax.swing.JButton thanhToanBtn;
    private javax.swing.JLabel tongGiaTriLbl;
    private javax.swing.JPanel toolBar2;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
