/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.NhomQuyenBUS;
import bus.SachBUS;
import component.sachcomponent.AddSach1;
import component.sachcomponent.UpdateSach;
import dto.SachDTO;
import gui.MainView;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ExcelUtil;

/**
 *
 * @author mansh
 */
public class ToolBarAndTableSach extends javax.swing.JPanel {

    private MainView mv;
    private ArrayList<SachDTO> listS = SachBUS.getInstance().getAll();

    public void refreshDataListS() {
        this.listS = SachBUS.getInstance().getAll();
    }

    public ArrayList<SachDTO> getListS() {
        return this.listS;
    }

    /**
     * Creates new form ToolBarSach
     */
    public ToolBarAndTableSach(MainView mv) {
        initComponents();
        this.mv = mv;
//        txtFieldAllowNumberOnly(giaTuTxtFld);
//        txtFieldAllowNumberOnly(giaDenTxtFld);
        giaTuTxtFld.setEnabled(false);
        giaDenTxtFld.setEnabled(false);
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mã sách");
        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        filterByPrice.add(filterGiaBanRadio);
        filterByPrice.add(filterGiaNhapRadio);
        loadDataSach(listS);

        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q9")) {
            addBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXoaQ(mv.getNv().getMaNhomQuyen(), "Q9")) {
            deleteBtn.setEnabled(false);
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToExcelQ(mv.getNv().getMaNhomQuyen(), "Q9")) {
            readExcelFileBtn.setEnabled(false);
            exportExcelBtn.setEnabled(false);
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToCapNhatQ(mv.getNv().getMaNhomQuyen(), "Q9")) {
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

    public void loadDataSach(ArrayList<SachDTO> listS) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã sách");
        defaultTableModel.addColumn("Ảnh");
        defaultTableModel.addColumn("Tên sách");
        defaultTableModel.addColumn("Năm xuất bản");
        defaultTableModel.addColumn("Mã tác giả");
        defaultTableModel.addColumn("Mã NXB");
        defaultTableModel.addColumn("Số lượng");
        defaultTableModel.addColumn("Giá nhập");
        defaultTableModel.addColumn("Giá bán");
        defaultTableModel.addColumn("Thể loại");

        for (SachDTO sach : listS) {
            if (sach.getTrangThai() == 1) {
                System.out.println(sach);
                defaultTableModel.addRow(new Object[]{
                    sach.getMaSach(),
                    sach.getPhoto(),
                    sach.getTenSach(),
                    sach.getNamXuatBan(),
                    sach.getMaTacGia(),
                    sach.getMaNXB(),
                    sach.getSoLuong(),
                    sach.getGiaNhap(),
                    sach.getGiaBan(),
                    sach.getTheLoai(),});
            }
//            System.out.println(sach);
        }
        sachTbl.setModel(defaultTableModel);
        sachTbl.setRowHeight(100);
        sachTbl.getTableHeader().setReorderingAllowed(false);
        sachTbl.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
    }

    private class ImageRender extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setOpaque(true);

            if (value instanceof String) {
                String photoName = (String) value;
                File imageFile = new File("./src/icon/" + photoName);
                System.out.println(imageFile.getAbsolutePath());
                if (imageFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageFile.getAbsolutePath()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                    label.setIcon(imageIcon);
                } else {
                    label.setText("Ảnh không tồn tại!");
                }
            } else {
                label.setText("Ảnh chưa cập nhật!");
            }

            return label;
        }
    }

    public String getSelectedItemId() {
        DefaultTableModel dtm = (DefaultTableModel) sachTbl.getModel();
        int selectedRowIndex = sachTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public void deleteItem() {
        String id = getSelectedItemId();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn sách muốn xoá!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xóa sách này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            // Kiểm tra người dùng nhấn Yes hay không
            if (confirm == JOptionPane.YES_OPTION) {
                SachDTO s = new SachDTO();
                s.setMaSach(id);
                int result = SachBUS.getInstance().delete(s);

                if (result > 0) {
                    JOptionPane.showMessageDialog(mv, "Xoá sách thành công!", "Thành công", JOptionPane.ERROR_MESSAGE);
                    refreshDataListS();
                    loadDataSach(this.listS);
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
                    String maSach = excelRow.getCell(0).getStringCellValue().trim();

                    Cell cellTenSach = excelRow.getCell(2);
                    String tenSach;
                    if (cellTenSach.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellTenSach.getNumericCellValue();
                        tenSach = String.valueOf(numericValue);
                    } else {
                        tenSach = cellTenSach.getStringCellValue().trim();
                    }

                    Cell cellNamXuatBan = excelRow.getCell(3);
                    String namXuatBan;
                    if (cellNamXuatBan.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellNamXuatBan.getNumericCellValue();
                        namXuatBan = String.valueOf(numericValue);
                    } else {
                        namXuatBan = cellNamXuatBan.getStringCellValue().trim();
                    }

                    Cell cellMaTacGia = excelRow.getCell(4);
                    String maTacGia;
                    if (cellMaTacGia.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellMaTacGia.getNumericCellValue();
                        maTacGia = String.valueOf(numericValue);
                    } else {
                        maTacGia = cellMaTacGia.getStringCellValue().trim();
                    }

                    Cell cellMaNXB = excelRow.getCell(5);
                    String maNXB;
                    if (cellMaNXB.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellMaNXB.getNumericCellValue();
                        maNXB = String.valueOf(numericValue);
                    } else {
                        maNXB = cellMaNXB.getStringCellValue().trim();
                    }

                    Cell cellSoLuong = excelRow.getCell(6);
                    String soLuong;
                    if (cellSoLuong.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellSoLuong.getNumericCellValue();
                        soLuong = String.valueOf(numericValue);
                    } else {
                        soLuong = cellSoLuong.getStringCellValue().trim();
                    }

                    Cell cellGiaNhap = excelRow.getCell(7);
                    String giaNhap;
                    if (cellGiaNhap.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellGiaNhap.getNumericCellValue();
                        giaNhap = String.valueOf(numericValue);
                    } else {
                        giaNhap = cellGiaNhap.getStringCellValue().trim().replaceAll("[^0-9.]+", "");
                    }

                    Cell cellGiaBan = excelRow.getCell(8);
                    String giaBan;
                    if (cellGiaBan.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellGiaBan.getNumericCellValue();
                        giaBan = String.valueOf(numericValue);
                    } else {
                        giaBan = cellGiaBan.getStringCellValue().trim().replaceAll("[^0-9.]+", "");
                    }

                    Cell cellTheLoai = excelRow.getCell(9);
                    String theLoai;
                    if (cellTheLoai.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellTheLoai.getNumericCellValue();
                        theLoai = String.valueOf(numericValue);
                    } else {
                        theLoai = cellTheLoai.getStringCellValue().trim();
                    }

                    Cell cellPhoto = excelRow.getCell(1);
                    String photo;
                    if (cellPhoto.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellPhoto.getNumericCellValue();
                        photo = String.valueOf(numericValue);
                    } else {
                        photo = cellPhoto.getStringCellValue().trim();
                    }

                    String errors = SachBUS.getInstance().validateData(maSach, tenSach, namXuatBan, maTacGia, maNXB, soLuong, giaNhap, giaBan, theLoai, photo);
                    if (errors == null || errors.isEmpty()) {
                        int flag = 0;
                        for (SachDTO sInDB : this.listS) {
                            if (sInDB.getTenSach().equals(tenSach) && sInDB.getNamXuatBan() == Integer.parseInt(namXuatBan) && sInDB.getMaTacGia().equals(maTacGia) && sInDB.getMaNXB().equals(maNXB) && sInDB.getGiaNhap() == Double.parseDouble(giaNhap) && sInDB.getGiaBan() == Double.parseDouble(giaBan) && sInDB.getTheLoai().equals(theLoai)) {
                                sInDB.setSoLuong((int) (sInDB.getSoLuong() + Long.parseLong(soLuong)));
                                SachBUS.getInstance().update(sInDB);
                                refreshDataListS();
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            SachDTO s = SachBUS.getInstance().createNewSach(tenSach, namXuatBan, maTacGia, maNXB, soLuong, giaNhap, giaBan, theLoai, photo);
                            SachBUS.getInstance().add(s);
                            refreshDataListS();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi ở dòng " + (row + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                loadDataSach(listS);
                wb.close();
                file.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filterByPrice = new javax.swing.ButtonGroup();
        jPanel9 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        exportExcelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        readExcelFileBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        searchIcon6 = new javax.swing.JLabel();
        filterValueTxtFld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        filterConditionCbx = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        sortCbx = new javax.swing.JComboBox<>();
        filterByPricePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        giaTuTxtFld = new javax.swing.JTextField();
        giaDenTxtFld = new javax.swing.JTextField();
        filterGiaNhapRadio = new javax.swing.JRadioButton();
        filterGiaBanRadio = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        sachTbPane = new javax.swing.JTabbedPane();
        sachScrollPane = new javax.swing.JScrollPane();
        sachTbl = new javax.swing.JTable();

        jPanel9.setBackground(new java.awt.Color(0, 102, 255));

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Plus__Orange.png"))); // NOI18N
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

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil-icon.png"))); // NOI18N
        updateBtn.setText("Sửa");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        exportExcelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/excel.png"))); // NOI18N
        exportExcelBtn.setText("Export");
        exportExcelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportExcelBtnMouseClicked(evt);
            }
        });
        exportExcelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportExcelBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        deleteBtn.setText("Xoá");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        readExcelFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/excel.png"))); // NOI18N
        readExcelFileBtn.setText("Import");
        readExcelFileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                readExcelFileBtnMouseClicked(evt);
            }
        });
        readExcelFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readExcelFileBtnActionPerformed(evt);
            }
        });

        searchIcon6.setIcon(new javax.swing.ImageIcon("D:\\Work Space\\doAn\\doAnJava\\quanlycuahangsach\\src\\main\\java\\icon\\search_40.png")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(searchIcon6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterValueTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchIcon6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(filterValueTxtFld)
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Điều kiện lọc");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Sắp xếp");

        jLabel3.setText("Từ: ");

        jLabel4.setText("Đến: ");

        javax.swing.GroupLayout filterByPricePanelLayout = new javax.swing.GroupLayout(filterByPricePanel);
        filterByPricePanel.setLayout(filterByPricePanelLayout);
        filterByPricePanelLayout.setHorizontalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterByPricePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filterByPricePanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(giaDenTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                    .addGroup(filterByPricePanelLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(giaTuTxtFld)))
                .addContainerGap())
        );
        filterByPricePanelLayout.setVerticalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterByPricePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(giaTuTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(giaDenTxtFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        filterGiaNhapRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterGiaNhapRadio.setText("Lọc theo giá nhập");
        filterGiaNhapRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterGiaNhapRadioMouseClicked(evt);
            }
        });
        filterGiaNhapRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterGiaNhapRadioActionPerformed(evt);
            }
        });

        filterGiaBanRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterGiaBanRadio.setText("Lọc theo giá bán");
        filterGiaBanRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterGiaBanRadioMouseClicked(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton3.setText("Lọc");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton2.setText("Huỷ lọc");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(filterConditionCbx, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortCbx, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(filterGiaBanRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filterGiaNhapRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterByPricePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(readExcelFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(exportExcelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(filterGiaNhapRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filterConditionCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sortCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filterGiaBanRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(filterByPricePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(readExcelFileBtn)
                                .addComponent(addBtn)
                                .addComponent(deleteBtn))
                            .addGap(16, 16, 16)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(exportExcelBtn)
                                .addComponent(updateBtn)
                                .addComponent(jButton2))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );

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
        sachScrollPane.setViewportView(sachTbl);

        sachTbPane.addTab("tab1", sachScrollPane);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sachTbPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1073, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sachTbPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateBtnActionPerformed

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        if (!updateBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = this.getSelectedItemId();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách muốn cập nhật!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            UpdateSach updateSach = new UpdateSach(mv, true, this);
            updateSach.setLocationRelativeTo(mv);
            updateSach.setVisible(true);
        }
    }//GEN-LAST:event_updateBtnMouseClicked

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        if (!addBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AddSach1 addSach = new AddSach1(mv, true, this);
        addSach.setLocationRelativeTo(mv);
        addSach.setVisible(true);
    }//GEN-LAST:event_addBtnMouseClicked

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        if (!deleteBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.deleteItem();
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void exportExcelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportExcelBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportExcelBtnActionPerformed

    private void exportExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportExcelBtnMouseClicked
        if (!exportExcelBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ExcelUtil.saveFile(mv, sachTbl, "sach");
    }//GEN-LAST:event_exportExcelBtnMouseClicked

    private void readExcelFileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readExcelFileBtnMouseClicked
        if (!readExcelFileBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] expectedHeaders = {"Mã sách", "Ảnh", "Tên sách", "Năm xuất bản", "Mã tác giả", "Mã NXB", "Số lượng", "Giá nhập", "Giá bán", "Thể loại"};
        this.readFile(mv, expectedHeaders);
    }//GEN-LAST:event_readExcelFileBtnMouseClicked

    private void readExcelFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readExcelFileBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_readExcelFileBtnActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if (filterGiaNhapRadio.isSelected() && sortCbx.getSelectedIndex() == 1) {
            loadDataSach(SachBUS.getInstance().filterByGiaNhapAndMaSachASC(filterValueTxtFld.getText(), giaTuTxtFld.getText(), giaDenTxtFld.getText()));
            return;
        } else if (filterGiaNhapRadio.isSelected() && sortCbx.getSelectedIndex() == 2) {
            loadDataSach(SachBUS.getInstance().filterByGiaNhapAndMaSachDESC(filterValueTxtFld.getText(), giaTuTxtFld.getText(), giaDenTxtFld.getText()));
            return;
        } else if (filterGiaBanRadio.isSelected() && sortCbx.getSelectedIndex() == 1) {
            loadDataSach(SachBUS.getInstance().filterByGiaBanAndMaSachASC(filterValueTxtFld.getText(), giaTuTxtFld.getText(), giaDenTxtFld.getText()));
            return;
        } else if (filterGiaBanRadio.isSelected() && sortCbx.getSelectedIndex() == 2) {
            loadDataSach(SachBUS.getInstance().filterByGiaBanAndMaSachDESC(filterValueTxtFld.getText(), giaTuTxtFld.getText(), giaDenTxtFld.getText()));
            return;
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void filterGiaNhapRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterGiaNhapRadioMouseClicked
        giaTuTxtFld.setEnabled(true);
        giaDenTxtFld.setEnabled(true);
    }//GEN-LAST:event_filterGiaNhapRadioMouseClicked

    private void filterGiaBanRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterGiaBanRadioMouseClicked
        giaTuTxtFld.setEnabled(true);
        giaDenTxtFld.setEnabled(true);
    }//GEN-LAST:event_filterGiaBanRadioMouseClicked

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        sortCbx.setSelectedIndex(0);
        filterConditionCbx.setSelectedIndex(0);
        filterValueTxtFld.setText("");
        filterGiaBanRadio.setSelected(false);
        filterGiaNhapRadio.setSelected(false);
        giaTuTxtFld.setText("");
        giaDenTxtFld.setText("");
        giaTuTxtFld.setEnabled(false);
        giaDenTxtFld.setEnabled(false);
        loadDataSach(listS);
    }//GEN-LAST:event_jButton2MouseClicked

    private void filterGiaNhapRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterGiaNhapRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterGiaNhapRadioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton exportExcelBtn;
    private javax.swing.ButtonGroup filterByPrice;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JRadioButton filterGiaBanRadio;
    private javax.swing.JRadioButton filterGiaNhapRadio;
    private javax.swing.JTextField filterValueTxtFld;
    private javax.swing.JTextField giaDenTxtFld;
    private javax.swing.JTextField giaTuTxtFld;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton readExcelFileBtn;
    private javax.swing.JScrollPane sachScrollPane;
    private javax.swing.JTabbedPane sachTbPane;
    private javax.swing.JTable sachTbl;
    private javax.swing.JLabel searchIcon6;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
