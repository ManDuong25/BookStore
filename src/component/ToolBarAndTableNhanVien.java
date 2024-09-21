/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.NhanVienBUS;
import bus.NhomQuyenBUS;
import bus.TaiKhoanBUS;
import component.nhanVienComponent.AddNhanVien;
import component.nhanVienComponent.UpdateNhanVien;
import dto.NhanVienDTO;
import dto.TaiKhoanDTO;
import gui.MainView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ExcelUtil;
import javax.swing.ImageIcon;

/**
 *
 * @author mansh
 */
public class ToolBarAndTableNhanVien extends javax.swing.JPanel {

    private MainView mv;
    private ArrayList<NhanVienDTO> listNV = NhanVienBUS.getInstance().getAll();

    public void refreshDataListNV() {
        this.listNV = NhanVienBUS.getInstance().getAll();
    }

    public ArrayList<NhanVienDTO> getListNV() {
        return this.listNV;
    }

    /**
     * Creates new form ToolBarAndTableNhanVien
     */
    public ToolBarAndTableNhanVien(MainView mv) {
        initComponents();
        this.mv = mv;
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mã");
        filterConditionCbx.addItem("Theo tên");
        filterConditionCbx.addItem("Theo mã NQ");
        filterConditionCbx.addItem("Theo SĐT");

        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        loadDataNhanVien(this.listNV);

        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q6")) {
            addBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXoaQ(mv.getNv().getMaNhomQuyen(), "Q6")) {
            deleteBtn.setEnabled(false);
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToExcelQ(mv.getNv().getMaNhomQuyen(), "Q6")) {
            readFileExcelBtn.setEnabled(false);
            xuatExcelBtn.setEnabled(false);
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToCapNhatQ(mv.getNv().getMaNhomQuyen(), "Q6")) {
            updateBtn.setEnabled(false);
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

    public void loadDataNhanVien(ArrayList<NhanVienDTO> listNV) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã nhân viên");
        defaultTableModel.addColumn("Tên nhân viên");
        defaultTableModel.addColumn("Địa chỉ");
        defaultTableModel.addColumn("Email");
        defaultTableModel.addColumn("Số điện thoại");
        defaultTableModel.addColumn("Lương");
        defaultTableModel.addColumn("Mã nhóm quyền");

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        for (NhanVienDTO nv : listNV) {
            if (nv.getTrangThai() == 1) {
                defaultTableModel.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getDiaChi(),
                    nv.getEmail(),
                    nv.getSoDienThoai(),
                    decimalFormat.format(nv.getLuong()),
                    nv.getMaNhomQuyen()});
            }
        }
        nhanVienTbl.setModel(defaultTableModel);
        nhanVienTbl.setRowHeight(50);
    }

    public String getSelectedItemId() {
        DefaultTableModel dtm = (DefaultTableModel) nhanVienTbl.getModel();
        int selectedRowIndex = nhanVienTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public void deleteItem() {
        String id = getSelectedItemId();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn nhân viên muốn xoá!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xóa nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            // Kiểm tra người dùng nhấn Yes hay không
            if (confirm == JOptionPane.YES_OPTION) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(id);
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setMaNV(id);
                int result1 = TaiKhoanBUS.getInstance().delete(tk);
                if (result1 > 0) {
                    int result = NhanVienBUS.getInstance().delete(nv);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(mv, "Xoá nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        refreshDataListNV();
                        loadDataNhanVien(listNV);
                    }
                }
            }
        }
    }

    public void readFile(JFrame main, String[] expectedHeaders) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILE", "xls", "xlsx", "xlsm");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showOpenDialog(main);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                FileInputStream file = new FileInputStream(filePath);
                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);
                FormulaEvaluator formula = wb.getCreationHelper().createFormulaEvaluator();

                // Kiểm tra tiêu đề hàng đầu
                Row headerRow = sheet.getRow(0);
                boolean headerMatches = true;
                if (headerRow != null) {
                    for (int i = 0; i < expectedHeaders.length; i++) {
                        Cell cell = headerRow.getCell(i);
                        if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedHeaders[i])) {
                            headerMatches = false;
                            break;
                        }
                    }
                } else {
                    headerMatches = false;
                }

                if (!headerMatches) {
                    JOptionPane.showMessageDialog(main, "File không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    wb.close();
                    file.close();
                    return;
                }

                // Tiếp tục xử lý file nếu header khớp
                for (int row = 1; row <= sheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = sheet.getRow(row);
                    String maNV = "";
                    Cell maNVCell = excelRow.getCell(0);
                    if (maNVCell.getCellType() == CellType.NUMERIC) {
                        // Chuyển đổi số thành chuỗi
                        maNV = String.valueOf((long) maNVCell.getNumericCellValue());
                    } else {
                        maNV = maNVCell.getStringCellValue().trim();
                    }

                    String tenNV = "";
                    Cell tenNVCell = excelRow.getCell(1);
                    if (tenNVCell.getCellType() == CellType.NUMERIC) {
                        // Chuyển đổi số thành chuỗi
                        tenNV = String.valueOf((long) tenNVCell.getNumericCellValue());
                    } else {
                        tenNV = tenNVCell.getStringCellValue().trim();
                    }

                    String diaChi = "";
                    Cell diaChiCell = excelRow.getCell(2);
                    if (diaChiCell.getCellType() == CellType.NUMERIC) {
                        // Chuyển đổi số thành chuỗi
                        diaChi = String.valueOf((long) diaChiCell.getNumericCellValue());
                    } else {
                        diaChi = diaChiCell.getStringCellValue().trim();
                    }
                    String email = excelRow.getCell(3).getStringCellValue().trim();

                    String soDienThoai = "";
                    Cell soDienThoaiCell = excelRow.getCell(4);
                    if (soDienThoaiCell.getCellType() == CellType.NUMERIC) {
                        // Chuyển đổi số thành chuỗi
                        soDienThoai = String.valueOf((long) soDienThoaiCell.getNumericCellValue());
                    } else {
                        soDienThoai = soDienThoaiCell.getStringCellValue().trim();
                    }
                    if (!soDienThoai.startsWith("0")) {
                        soDienThoai = "0" + soDienThoai;
                    }

                    String luong = "";
                    Cell luongCell = excelRow.getCell(5);
                    if (luongCell.getCellType() == CellType.NUMERIC) {
                        luong = String.valueOf(luongCell.getNumericCellValue());
                    } else {
                        luong = luongCell.getStringCellValue().trim().replace(",", "");
                    }

                    String maNhomQuyen = "";
                    Cell maNhomQuyenCell = excelRow.getCell(6);
                    if (maNhomQuyenCell.getCellType() == CellType.NUMERIC) {
                        maNhomQuyen = String.valueOf((long) maNhomQuyenCell.getNumericCellValue());
                    } else {
                        maNhomQuyen = maNhomQuyenCell.getStringCellValue().trim();
                    }

                    String errors = NhanVienBUS.getInstance().validateData(maNV, tenNV, diaChi, email, soDienThoai, luong, maNhomQuyen);
                    if (errors == null || errors.isEmpty()) {
                        if (NhanVienBUS.getInstance().isPhoneNumberExist(soDienThoai)) {
                            JOptionPane.showMessageDialog(this, "SĐT đã tồn tại trong hệ thống: " + soDienThoai, "Thất bại", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        int flag = 0;
                        for (NhanVienDTO nvInDB : this.listNV) {
                            if (nvInDB.getTenNV().equals(tenNV) && nvInDB.getDiaChi().equals(diaChi) && nvInDB.getEmail().equals(email) && nvInDB.getSoDienThoai().equals(soDienThoai) && nvInDB.getLuong() == Double.parseDouble(luong) && nvInDB.getMaNhomQuyen().equals(maNhomQuyen)) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            NhanVienDTO nv = NhanVienBUS.getInstance().createNewNV(tenNV, diaChi, email, soDienThoai, luong, maNhomQuyen);
                            int resultaddNv = NhanVienBUS.getInstance().add(nv);
                            if (resultaddNv > 0) {
                                TaiKhoanDTO tk = new TaiKhoanDTO();
                                tk.setMaNV(nv.getMaNV());
                                tk.setMatKhau("12345678");
                                tk.setEmail(nv.getEmail());
                                int result = TaiKhoanBUS.getInstance().add(tk);
                                if (result > 0) {
                                    JOptionPane.showMessageDialog(main, "Thêm mới thành công nhân viên có mã là: " + nv.getMaNV(), "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(main, "Thêm mới nhân viên thất bại, lỗi hệ thống!", "Có lỗi xảy ra!", JOptionPane.ERROR_MESSAGE);
                            }
                            refreshDataListNV();
                        }
                    } else {
                        JOptionPane.showMessageDialog(main, "Dữ liệu bị lỗi ở dòng " + (row + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                loadDataNhanVien(listNV);
                wb.close();
                file.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public ArrayList<NhanVienDTO> filterByMaNhanVien(ArrayList<NhanVienDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 1) {
            ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhanVienDTO nv : list) {
                    if (nv.getMaNV().contains(filterValueTxtFld2.getText())) {
                        result.add(nv);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<NhanVienDTO> filterByName(ArrayList<NhanVienDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 2) {
            ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhanVienDTO nv : list) {
                    if (nv.getTenNV().contains(filterValueTxtFld2.getText())) {
                        result.add(nv);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<NhanVienDTO> filterByEmail(ArrayList<NhanVienDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 3) {
            ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhanVienDTO nv : list) {
                    if (nv.getEmail().contains(filterValueTxtFld2.getText())) {
                        result.add(nv);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<NhanVienDTO> filterBySDT(ArrayList<NhanVienDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 4) {
            ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhanVienDTO nv : list) {
                    if (nv.getSoDienThoai().contains(filterValueTxtFld2.getText())) {
                        result.add(nv);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public int getSoThuTu(String ma, String startWith) {
        String[] id = ma.split(startWith);
        int stt = Integer.parseInt(id[1]);
        return stt;
    }

    public ArrayList<NhanVienDTO> ascendingSort(ArrayList<NhanVienDTO> list) {
        if (sortCbx.getSelectedIndex() == 1 && filterConditionCbx.getSelectedIndex() == 1) {
            Collections.sort(list, Comparator.comparing(nv -> Integer.parseInt(nv.getMaNV().substring(2))));
        } else if (sortCbx.getSelectedIndex() == 1 && filterConditionCbx.getSelectedIndex() == 2) {
            Collections.sort(list, Comparator.comparing(NhanVienDTO::getTenNV));
        }
        return list;
    }

    public ArrayList<NhanVienDTO> descendingSort(ArrayList<NhanVienDTO> list) {
        if (sortCbx.getSelectedIndex() == 2 && filterConditionCbx.getSelectedIndex() == 1) {
            Collections.sort(list, Comparator.comparingInt(nv -> Integer.parseInt(nv.getMaNV().substring(2))));
            Collections.reverse(list);
        } else if (sortCbx.getSelectedIndex() == 2 && filterConditionCbx.getSelectedIndex() == 2) {
            Collections.sort(list, Comparator.comparing(NhanVienDTO::getTenNV));
            Collections.reverse(list);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
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
        filterByPricePanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        readFileExcelBtn = new javax.swing.JButton();
        xuatExcelBtn = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        nhanVienTbPane = new javax.swing.JTabbedPane();
        nhanVienScrollPane = new javax.swing.JScrollPane();
        nhanVienTbl = new javax.swing.JTable();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(1135, 602));
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

        toolBar2.add(jPanel9, new java.awt.GridBagConstraints());

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
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filterConditionCbx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(sortCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        toolBar2.add(jPanel2, new java.awt.GridBagConstraints());

        filterByPricePanel.setOpaque(false);
        filterByPricePanel.setPreferredSize(new java.awt.Dimension(200, 62));

        javax.swing.GroupLayout filterByPricePanelLayout = new javax.swing.GroupLayout(filterByPricePanel);
        filterByPricePanel.setLayout(filterByPricePanelLayout);
        filterByPricePanelLayout.setHorizontalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        filterByPricePanelLayout.setVerticalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        toolBar2.add(filterByPricePanel, new java.awt.GridBagConstraints());

        jPanel3.setOpaque(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton1.setText("Lọc");
        jButton1.setPreferredSize(new java.awt.Dimension(75, 50));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel6.setOpaque(false);

        jButton2.setText("Huỷ lọc");
        jButton2.setPreferredSize(new java.awt.Dimension(75, 50));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2);

        jPanel3.add(jPanel6);

        toolBar2.add(jPanel3, new java.awt.GridBagConstraints());

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(320, 58));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 8, 6));

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Plus__Orange.png"))); // NOI18N
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

        readFileExcelBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        readFileExcelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/excel.png"))); // NOI18N
        readFileExcelBtn.setText("Import");
        readFileExcelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                readFileExcelBtnMouseClicked(evt);
            }
        });
        jPanel4.add(readFileExcelBtn);

        xuatExcelBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xuatExcelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/excel.png"))); // NOI18N
        xuatExcelBtn.setText("Export");
        xuatExcelBtn.setActionCommand("Export");
        xuatExcelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xuatExcelBtnMouseClicked(evt);
            }
        });
        jPanel4.add(xuatExcelBtn);

        toolBar2.add(jPanel4, new java.awt.GridBagConstraints());

        jScrollPane1.setViewportView(toolBar2);

        add(jScrollPane1, java.awt.BorderLayout.PAGE_START);

        nhanVienTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        nhanVienScrollPane.setViewportView(nhanVienTbl);

        nhanVienTbPane.addTab("tab1", nhanVienScrollPane);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(nhanVienTbPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(nhanVienTbPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
        );

        add(jPanel10, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        if (!deleteBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.deleteItem();
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        if (!updateBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = this.getSelectedItemId();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên muốn cập nhật!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            UpdateNhanVien updateNhanVien = new UpdateNhanVien(mv, true, this);
            updateNhanVien.setLocationRelativeTo(this);
            updateNhanVien.setVisible(true);
        }
    }//GEN-LAST:event_updateBtnMouseClicked

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        if (!addBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AddNhanVien addNhanVienFrame = new AddNhanVien(mv, true, this);
        addNhanVienFrame.setLocationRelativeTo(mv);
        addNhanVienFrame.setVisible(true);
    }//GEN-LAST:event_addBtnMouseClicked

    private void xuatExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xuatExcelBtnMouseClicked
        if (!xuatExcelBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ExcelUtil.saveFile(mv, nhanVienTbl, "nhanvien");
    }//GEN-LAST:event_xuatExcelBtnMouseClicked

    private void readFileExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readFileExcelBtnMouseClicked
        if (!readFileExcelBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] expectedHeaders = {"Mã nhân viên", "Tên nhân viên", "Địa chỉ", "Email", "Số điện thoại", "Lương", "Mã nhóm quyền"};
        this.readFile(mv, expectedHeaders);
    }//GEN-LAST:event_readFileExcelBtnMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        sortCbx.setSelectedIndex(0);
        filterConditionCbx.setSelectedIndex(0);
        filterValueTxtFld2.setText("");
        loadDataNhanVien(listNV);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        loadDataNhanVien(filterByMaNhanVien(filterByEmail(filterByName(filterBySDT(ascendingSort(descendingSort(listNV)))))));
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JTextField filterValueTxtFld2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane nhanVienScrollPane;
    private javax.swing.JTabbedPane nhanVienTbPane;
    private javax.swing.JTable nhanVienTbl;
    private javax.swing.JButton readFileExcelBtn;
    private javax.swing.JLabel searchIcon8;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JPanel toolBar2;
    private javax.swing.JButton updateBtn;
    private javax.swing.JButton xuatExcelBtn;
    // End of variables declaration//GEN-END:variables
}
