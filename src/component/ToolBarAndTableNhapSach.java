/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.ChiTietPhieuNhapBUS;
import bus.KhachHangBUS;
import bus.NhomQuyenBUS;
import bus.PhieuNhapBUS;
import bus.SachBUS;
import component.nhapSachComponent.AddNewSach;
import component.nhapSachComponent.ChooseNCC;
import dto.ChiTietPhieuNhapDTO;
import dto.KhachHangDTO;
import dto.PhieuNhapDTO;
import dto.SachDTO;
import gui.MainView;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;

/**
 *
 * @author mansh
 */
public class ToolBarAndTableNhapSach extends javax.swing.JPanel {

    private MainView mv;
    private String maNCC = "";
    private ArrayList<SachDTO> listS = SachBUS.getInstance().getAll();
    private ArrayList<SachDTO> listSelectedSach = new ArrayList<SachDTO>();

    /**
     * Creates new form ToolBarAndTableNhapSach
     */
    public ToolBarAndTableNhapSach(MainView mv) {
        initComponents();
        this.mv = mv;
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mã sách");
        filterConditionCbx.addItem("Theo mã NXB");
        filterConditionCbx.addItem("Theo thể loại");
        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        loadDataSach(listS, sachTbl);
        loadDataSach(listSelectedSach, sachChonTbl);
        jTabbedPane1.setEnabledAt(1, false);
        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q5")) {
            addNewSachBtn.setEnabled(false);
        }
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
        defaultTableModel.addColumn("Giá nhập");
        defaultTableModel.addColumn("Thể loại");

        for (SachDTO sach : listS) {
            if (sach.getTrangThai() == 1) {
                defaultTableModel.addRow(new Object[]{
                    sach.getMaSach(),
                    sach.getPhoto(),
                    sach.getTenSach(),
                    sach.getSoLuong(),
                    sach.getGiaNhap(),
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
        DefaultTableModel dtm = (DefaultTableModel) sachTbl.getModel();
        int selectedRowIndex = sachTbl.getSelectedRow();
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

    public void refreshDataListS() {
        listS = SachBUS.getInstance().getAll();
        loadDataSach(listS, sachTbl);
    }

    public void refreshData() {
        listS = SachBUS.getInstance().getAll();
        listSelectedSach.clear();
        loadDataSach(listS, sachTbl);
        loadDataSach(listSelectedSach, sachChonTbl);
    }

    public String getSelectedItemSachMua() {
        DefaultTableModel dtm = (DefaultTableModel) sachChonTbl.getModel();
        int selectedRowIndex = sachChonTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public void showIn4ChiTietPhieuNhap(String maNCC) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date today = new java.util.Date();
        Date dateNeedFormat = new Date(today.getTime());
        String maPhieuNhap = "";
        int lastElementId = PhieuNhapBUS.getInstance().getLastElementId();
        if (lastElementId == -1) {
            maPhieuNhap = "PN" + 1;
        } else {
            maPhieuNhap = "PN" + (lastElementId + 1);
        }
        String ngayNhap = dateFormat.format(dateNeedFormat);
        String maNV = "NV1";
        String tenNV = "Dương Công Mãn";
        String tongTien = tinhTongTien(listSelectedSach) + "";

        maPhieuNhapLbl.setText(maPhieuNhap);
        ngayNhapLbl.setText(ngayNhap);
        maNhanVienLbl.setText(maNV);
        tenNhanVienLbl.setText(tenNV);
        tongTienPhieuNhapLbl.setText(tongTien);
        maNCCLbl.setText(maNCC);

        loadDataSach(listSelectedSach, chiTietPhieuNhapTbl);
    }

    public PhieuNhapDTO createPhieuNhap() {
        String maPhieuNhap = maPhieuNhapLbl.getText();
        String ngayNhap = ngayNhapLbl.getText();
        String maNV = maNhanVienLbl.getText();
        String maNCC = maNCCLbl.getText();
        String tongTien = tongTienPhieuNhapLbl.getText();

        String errors = PhieuNhapBUS.getInstance().validateData(maPhieuNhap, ngayNhap, maNV, maNCC, tongTien);
        if (errors == null || errors.isEmpty()) {
            PhieuNhapDTO pn = PhieuNhapBUS.getInstance().createNewPN(maPhieuNhap, ngayNhap, maNV, maNCC, tongTien);
            return pn;
        } else {
            JOptionPane.showMessageDialog(this, errors, "Thêm phiếu nhập thất bại!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public ArrayList<ChiTietPhieuNhapDTO> createChiTietPhieuNhap() {
        String maPhieu = maPhieuNhapLbl.getText();
        String errors = ChiTietPhieuNhapBUS.getInstance().validateData(maPhieu, listSelectedSach);

        if (errors == null || errors.isEmpty()) {
            ArrayList<ChiTietPhieuNhapDTO> ctpnList = ChiTietPhieuNhapBUS.getInstance().createNewCTPN(maPhieu, listSelectedSach);
            return ctpnList;
        } else {
            JOptionPane.showMessageDialog(this, errors, "Thêm phiếu nhập thất bại!", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    private int addCTPN(ArrayList<ChiTietPhieuNhapDTO> ctpnList) {
        for (ChiTietPhieuNhapDTO ctpn : ctpnList) {
            int result = ChiTietPhieuNhapBUS.getInstance().add(ctpn);
            if (result <= 0) {
                return 0;
            }
        }
        return 1;
    }

    private void updateSachInDB() {
        for (SachDTO s : listSelectedSach) {
            for (SachDTO s1 : listS) {
                if (s.getMaSach().equals(s1.getMaSach())) {
                    s1.setSoLuong(s1.getSoLuong() + s.getSoLuong());
                    break;
                }
            }
        }
        for (SachDTO s : listS) {
            SachBUS.getInstance().update(s);
        }
    }

    public void thanhToanNhapHang(PhieuNhapDTO pn, ArrayList<ChiTietPhieuNhapDTO> ctpnList) {
        int resultAddPhieuNhap = PhieuNhapBUS.getInstance().add(pn);
        if (resultAddPhieuNhap > 0) {
            int resultAddCtpn = addCTPN(ctpnList);
            if (resultAddCtpn == 1) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                updateSachInDB();
                refreshData();
                soTienPhaiTraLbl1.setText("");
                jTabbedPane1.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
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
        java.awt.GridBagConstraints gridBagConstraints;

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
        addNewSachBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        changeQuantityBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        boChonBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        sachTbl = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        sachChonTbl = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        canceBtn1 = new javax.swing.JButton();
        continueBtn = new javax.swing.JButton();
        soTienPhaiTraLbl1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        maPhieuNhapLbl = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ngayNhapLbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        maNhanVienLbl = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tenNhanVienLbl = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tongTienPhieuNhapLbl = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        maNCCLbl = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cancelNhapHangBtn = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        thanhToanNhapHangBtn = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        chiTietPhieuNhapTbl = new javax.swing.JTable();

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
        jPanel4.setPreferredSize(new java.awt.Dimension(340, 58));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 8, 6));

        addNewSachBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addNewSachBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/book-add-icon.png"))); // NOI18N
        addNewSachBtn.setText("Thêm");
        addNewSachBtn.setPreferredSize(new java.awt.Dimension(150, 27));
        addNewSachBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewSachBtnMouseClicked(evt);
            }
        });
        jPanel4.add(addNewSachBtn);

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/book-add-icon.png"))); // NOI18N
        addBtn.setText("Chọn");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        jPanel4.add(addBtn);

        changeQuantityBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        changeQuantityBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil-icon.png"))); // NOI18N
        changeQuantityBtn.setText("Đổi SL");
        changeQuantityBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeQuantityBtnMouseClicked(evt);
            }
        });
        jPanel4.add(changeQuantityBtn);

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton6.setText("Huỷ lọc");
        jPanel4.add(jButton6);

        boChonBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boChonBtn.setText("Bỏ chọn");
        boChonBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boChonBtnMouseClicked(evt);
            }
        });
        jPanel4.add(boChonBtn);

        toolBar2.add(jPanel4, new java.awt.GridBagConstraints());

        jScrollPane1.setViewportView(toolBar2);

        add(jScrollPane1, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(590, 402));

        sachTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(sachTbl);

        jPanel5.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(580, 402));

        sachChonTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(sachChonTbl);

        jPanel5.add(jScrollPane4, java.awt.BorderLayout.EAST);

        canceBtn1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        canceBtn1.setText("Huỷ");
        canceBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                canceBtn1MouseClicked(evt);
            }
        });

        continueBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        continueBtn.setText("Tiếp tục");
        continueBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                continueBtnMouseClicked(evt);
            }
        });

        soTienPhaiTraLbl1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(canceBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(continueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(soTienPhaiTraLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(soTienPhaiTraLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(canceBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(continueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );

        jPanel5.add(jPanel14, java.awt.BorderLayout.SOUTH);

        jScrollPane2.setViewportView(jPanel5);

        jTabbedPane1.addTab("tab1", jScrollPane2);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Chi tiết phiếu nhập");
        jPanel7.add(jLabel6, java.awt.BorderLayout.NORTH);

        jPanel8.setLayout(new java.awt.GridLayout(7, 2));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã phiếu nhập");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(jLabel3);

        maPhieuNhapLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maPhieuNhapLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(maPhieuNhapLbl);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Ngày nhập");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(jLabel8);

        ngayNhapLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ngayNhapLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(ngayNhapLbl);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Mã nhân viên");
        jLabel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(jLabel10);

        maNhanVienLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maNhanVienLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(maNhanVienLbl);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Tên nhân viên");
        jLabel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(jLabel12);

        tenNhanVienLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tenNhanVienLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(tenNhanVienLbl);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Tổng tiền phiếu nhập");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(jLabel14);

        tongTienPhieuNhapLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tongTienPhieuNhapLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(tongTienPhieuNhapLbl);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Mã nhà cung cấp");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(jLabel7);

        maNCCLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maNCCLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel8.add(maNCCLbl);

        cancelNhapHangBtn.setText("Huỷ");
        cancelNhapHangBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelNhapHangBtnMouseClicked(evt);
            }
        });
        cancelNhapHangBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNhapHangBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(cancelNhapHangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cancelNhapHangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel12);

        thanhToanNhapHangBtn.setText("Thanh toán");
        thanhToanNhapHangBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thanhToanNhapHangBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(thanhToanNhapHangBtn)
                .addGap(54, 54, 54))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(thanhToanNhapHangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel13);

        jPanel7.add(jPanel8, java.awt.BorderLayout.WEST);

        jPanel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel16.setLayout(new java.awt.BorderLayout());

        chiTietPhieuNhapTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(chiTietPhieuNhapTbl);

        jPanel16.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel16, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab2", jPanel7);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        String selectedBookId = getSelectedItemIdKhoSach();
        if (selectedBookId.isEmpty() || selectedBookId == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách muốn nhập ở bảng bên trái!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            for (SachDTO sach : listS) {
                if (sach.getMaSach().equals(selectedBookId)) {
                    int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn mua sách " + sach.getTenSach() + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String input = JOptionPane.showInputDialog(mv, "Nhập số lượng:");
                        if (input != null && !input.isEmpty()) {
                            try {
                                int quantity = Integer.parseInt(input);
                                if (quantity > 0) {
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

                                    soTienPhaiTraLbl1.setText("Số tiền phải trả là: " + formattedTongTien);
                                    loadDataSach(listSelectedSach, sachChonTbl);
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

    private void changeQuantityBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeQuantityBtnMouseClicked
        String selectedBookId = getSelectedItemSachMua();
        if (selectedBookId.isEmpty() || selectedBookId == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách đổi số lượng ở bảng bên phải!", "Thất bại", JOptionPane.ERROR_MESSAGE);
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
                                soTienPhaiTraLbl1.setText("");
                            } else {
                                DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
                                String formattedTongTien = decimalFormat.format(tongTien);
                                soTienPhaiTraLbl1.setText("Số tiền phải trả là: " + formattedTongTien);
                            }
                            loadDataSach(listS, sachTbl);
                            loadDataSach(listSelectedSach, sachChonTbl);
                        } else {
                            JOptionPane.showMessageDialog(mv, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mv, "Vui lòng nhập số lượng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_changeQuantityBtnMouseClicked

    private void boChonBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boChonBtnMouseClicked
        String selectedBookId = getSelectedItemSachMua();
        if (selectedBookId.isEmpty() || selectedBookId == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách muốn xoá ở bảng bên phải!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            for (SachDTO sach : listSelectedSach) {
                if (sach.getMaSach().equals(selectedBookId)) {
                    int option = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn bỏ chọn sách " + sach.getTenSach() + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        SachDTO sachInList = getSachById(listS, sach.getMaSach());
                        listSelectedSach.remove(sach);
                        double tongTien = tinhTongTien(listSelectedSach);
                        if (tongTien == 0) {
                            soTienPhaiTraLbl1.setText("");
                        } else {
                            DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
                            String formattedTongTien = decimalFormat.format(tongTien);
                            soTienPhaiTraLbl1.setText("Số tiền phải trả là: " + formattedTongTien);
                        }
                        loadDataSach(listS, sachTbl);
                        loadDataSach(listSelectedSach, sachChonTbl);
                        break;
                    }
                }
            }
        }
    }//GEN-LAST:event_boChonBtnMouseClicked

    private void addNewSachBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewSachBtnMouseClicked
        if (!addNewSachBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AddNewSach addSach = new AddNewSach(mv, true, this);
        addSach.setLocationRelativeTo(mv);
        addSach.setVisible(true);
    }//GEN-LAST:event_addNewSachBtnMouseClicked

    private void continueBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_continueBtnMouseClicked
        if (listSelectedSach.isEmpty()) {
            JOptionPane.showMessageDialog(mv, "Bạn cần chọn sách muốn mua!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            ChooseNCC chooseNcc = new ChooseNCC(mv, true, this, jTabbedPane1);
            chooseNcc.setLocationRelativeTo(mv);
            chooseNcc.setVisible(true);
        }
    }//GEN-LAST:event_continueBtnMouseClicked

    private void thanhToanNhapHangBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thanhToanNhapHangBtnMouseClicked
        PhieuNhapDTO pn = createPhieuNhap();
        ArrayList<ChiTietPhieuNhapDTO> ctpnList = createChiTietPhieuNhap();

        if (pn == null || ctpnList == null) {
            JOptionPane.showMessageDialog(this, "Thêm mới hoá đơn/ chi tiết hoá đơn thất bại!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            thanhToanNhapHang(pn, ctpnList);
        }
    }//GEN-LAST:event_thanhToanNhapHangBtnMouseClicked

    private void canceBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canceBtn1MouseClicked
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn huỷ nhập hàng?", "Lựa chọn!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            refreshData();
        }
    }//GEN-LAST:event_canceBtn1MouseClicked

    private void cancelNhapHangBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelNhapHangBtnMouseClicked
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn huỷ nhập hàng?", "Lựa chọn!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            refreshData();
            jTabbedPane1.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cancelNhapHangBtnMouseClicked

    private void cancelNhapHangBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNhapHangBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelNhapHangBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addNewSachBtn;
    private javax.swing.JButton boChonBtn;
    private javax.swing.JButton canceBtn1;
    private javax.swing.JButton cancelNhapHangBtn;
    private javax.swing.JButton changeQuantityBtn;
    private javax.swing.JTable chiTietPhieuNhapTbl;
    private javax.swing.JButton continueBtn;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JRadioButton filterGiaBanRadio;
    private javax.swing.JRadioButton filterGiaNhapRadio;
    private javax.swing.JTextField filterValueTxtFld2;
    private javax.swing.JTextField giaDenTxtFld;
    private javax.swing.JTextField giaTuTxtFld;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel maNCCLbl;
    private javax.swing.JLabel maNhanVienLbl;
    private javax.swing.JLabel maPhieuNhapLbl;
    private javax.swing.JLabel ngayNhapLbl;
    private javax.swing.JTable sachChonTbl;
    private javax.swing.JTable sachTbl;
    private javax.swing.JLabel searchIcon8;
    private javax.swing.JLabel soTienPhaiTraLbl1;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JLabel tenNhanVienLbl;
    private javax.swing.JButton thanhToanNhapHangBtn;
    private javax.swing.JLabel tongTienPhieuNhapLbl;
    private javax.swing.JPanel toolBar2;
    // End of variables declaration//GEN-END:variables
}
