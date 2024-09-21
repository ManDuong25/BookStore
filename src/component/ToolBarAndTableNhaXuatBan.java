/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.NhaXuatBanBUS;
import bus.NhomQuyenBUS;
import component.nhaXuatBanComponent.AddNXB;
import component.nhaXuatBanComponent.UpdateNXB;
import dto.NhaXuatBanDTO;
import gui.MainView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
public class ToolBarAndTableNhaXuatBan extends javax.swing.JPanel {

    private MainView mv;
    private ArrayList<NhaXuatBanDTO> listNXB = NhaXuatBanBUS.getInstance().getAll();

    public ToolBarAndTableNhaXuatBan(MainView mv) {
        initComponents();
        this.mv = mv;
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mà NXB");
        filterConditionCbx.addItem("Theo SĐT");
        filterConditionCbx.addItem("Theo email");
        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        loadDataNXB(listNXB);

        if (!NhomQuyenBUS.getInstance().checkIfAllowToXoaQ(mv.getNv().getMaNhomQuyen(), "Q10")) {
            deleteBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToCapNhatQ(mv.getNv().getMaNhomQuyen(), "Q10")) {
            updateBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q10")) {
            addBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToExcelQ(mv.getNv().getMaNhomQuyen(), "Q10")) {
            exportExcelFileBtn.setEnabled(false);
            readExcelBtn.setEnabled(false);
        }
    }

    public void loadDataNXB(ArrayList<NhaXuatBanDTO> listNXB) {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Mã NXB");
        defaultTableModel.addColumn("Tên");
        defaultTableModel.addColumn("Địa chỉ");
        defaultTableModel.addColumn("Số điện thoại");
        defaultTableModel.addColumn("Email");

        for (NhaXuatBanDTO nxb : listNXB) {
            if (nxb.getTrangThai() == 1) {
                defaultTableModel.addRow(new Object[]{
                    nxb.getMaNXB(),
                    nxb.getTen(),
                    nxb.getDiaChi(),
                    nxb.getSoDienThoai(),
                    nxb.getEmail()});
            }
//            System.out.println(sach);
        }
        nxbTbl.setModel(defaultTableModel);
        nxbTbl.setRowHeight(50);
        nxbTbl.getTableHeader().setReorderingAllowed(false);
    }

    public String getSelectedItemId() {
        DefaultTableModel dtm = (DefaultTableModel) nxbTbl.getModel();
        int selectedRowIndex = nxbTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public void deleteItem() {
        String id = getSelectedItemId();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn NXB muốn xoá!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xóa NXB này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            // Kiểm tra người dùng nhấn Yes hay không
            if (confirm == JOptionPane.YES_OPTION) {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaNXB(id);
                int result = NhaXuatBanBUS.getInstance().delete(nxb);
                if (result > 0) {
                    JOptionPane.showMessageDialog(mv, "Xoá NXB thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    this.listNXB = NhaXuatBanBUS.getInstance().getAll();
                    loadDataNXB(listNXB);
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
                    Cell cellMaNXB = excelRow.getCell(0);
                    String maNXB;
                    if (cellMaNXB.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellMaNXB.getNumericCellValue();
                        maNXB = String.valueOf(numericValue);
                    } else {
                        maNXB = cellMaNXB.getStringCellValue().trim();
                    }

                    Cell cellTen = excelRow.getCell(1);
                    String ten;
                    if (cellTen.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellTen.getNumericCellValue();
                        ten = String.valueOf(numericValue);
                    } else {
                        ten = cellTen.getStringCellValue().trim();
                    }

                    Cell cellDiaChi = excelRow.getCell(2);
                    String diaChi;
                    if (cellDiaChi.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellDiaChi.getNumericCellValue();
                        diaChi = String.valueOf(numericValue);
                    } else {
                        diaChi = cellDiaChi.getStringCellValue().trim();
                    }

                    String sdt = "";
                    Cell cellSdt = excelRow.getCell(3);
                    if (cellSdt.getCellType() == CellType.NUMERIC) {
                        // Chuyển đổi số thành chuỗi
                        sdt = String.valueOf((long) cellSdt.getNumericCellValue());
                    } else {
                        sdt = cellSdt.getStringCellValue().trim();
                    }
                    if (!sdt.startsWith("0")) {
                        if (sdt.startsWith("(") || sdt.startsWith("+")) {

                        } else {
                            sdt = "0" + sdt;
                        }
                    }

                    Cell cellEmail = excelRow.getCell(4);
                    String email;
                    if (cellEmail.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellEmail.getNumericCellValue();
                        email = String.valueOf(numericValue);
                    } else {
                        email = cellEmail.getStringCellValue().trim();
                    }

                    String errors = NhaXuatBanBUS.getInstance().validateData(maNXB, ten, diaChi, sdt, email);
                    if (errors == null || errors.isEmpty()) {
                        int flag = 0;
                        for (NhaXuatBanDTO nxbInDB : this.listNXB) {
                            if (nxbInDB.getTen().equals(ten) && nxbInDB.getDiaChi().equals(diaChi) && nxbInDB.getSoDienThoai().equals(sdt) && nxbInDB.getEmail().equals(email)) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            NhaXuatBanDTO nxb = NhaXuatBanBUS.getInstance().createNewNXB(ten, diaChi, sdt, email);
                            int result = NhaXuatBanBUS.getInstance().add(nxb);
                            if (result > 0) {
                                JOptionPane.showMessageDialog(this, "Đã thêm thành công NXB có mã là: " + nxb.getMaNXB(), "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                                this.listNXB = NhaXuatBanBUS.getInstance().getAll();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi ở dòng " + (row + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                loadDataNXB(listNXB);
                wb.close();
                file.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    public ArrayList<NhaXuatBanDTO> filterByMaNXB(ArrayList<NhaXuatBanDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 1) {
            ArrayList<NhaXuatBanDTO> result = new ArrayList<NhaXuatBanDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhaXuatBanDTO nxb : list) {
                    if (nxb.getMaNXB().contains(filterValueTxtFld2.getText())) {
                        result.add(nxb);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<NhaXuatBanDTO> filterBySDT(ArrayList<NhaXuatBanDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 2) {
            ArrayList<NhaXuatBanDTO> result = new ArrayList<NhaXuatBanDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhaXuatBanDTO nxb : list) {
                    if (nxb.getSoDienThoai().contains(filterValueTxtFld2.getText())) {
                        result.add(nxb);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<NhaXuatBanDTO> filterByEmail(ArrayList<NhaXuatBanDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 3) {
            ArrayList<NhaXuatBanDTO> result = new ArrayList<NhaXuatBanDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (NhaXuatBanDTO nxb : list) {
                    if (nxb.getEmail().contains(filterValueTxtFld2.getText())) {
                        result.add(nxb);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<NhaXuatBanDTO> ascendingSort(ArrayList<NhaXuatBanDTO> list) {
        int sortIndex = sortCbx.getSelectedIndex();
        int filterIndex = filterConditionCbx.getSelectedIndex();

        Comparator<NhaXuatBanDTO> comparator = null;

        if (sortIndex == 1 && filterIndex == 1) {
            comparator = Comparator.comparing(NhaXuatBanDTO::getMaNXB);
        } else if (sortIndex == 1 && filterIndex == 2) {
            comparator = Comparator.comparing(NhaXuatBanDTO::getSoDienThoai);
        } else if (sortIndex == 1 && filterIndex == 3) {
            comparator = Comparator.comparing(NhaXuatBanDTO::getEmail);
        }

        if (comparator != null) {
            Collections.sort(list, comparator);
        }

        return list;
    }

    public ArrayList<NhaXuatBanDTO> descendingSort(ArrayList<NhaXuatBanDTO> list) {
        int sortIndex = sortCbx.getSelectedIndex();
        int filterIndex = filterConditionCbx.getSelectedIndex();

        Comparator<NhaXuatBanDTO> comparator = null;

        if (sortIndex == 2 && filterIndex == 1) {
            comparator = Comparator.comparing(NhaXuatBanDTO::getMaNXB).reversed();
        } else if (sortIndex == 2 && filterIndex == 2) {
            comparator = Comparator.comparing(NhaXuatBanDTO::getSoDienThoai).reversed();
        } else if (sortIndex == 2 && filterIndex == 3) {
            comparator = Comparator.comparing(NhaXuatBanDTO::getEmail).reversed();
        }

        if (comparator != null) {
            Collections.sort(list, comparator);
        }

        return list;
    }

    public void refreshDataListNXB() {
        this.listNXB = NhaXuatBanBUS.getInstance().getAll();
    }

    public ArrayList<NhaXuatBanDTO> getListNXB() {
        return this.listNXB;
    }

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
        filterByPricePanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        exportExcelFileBtn = new javax.swing.JButton();
        readExcelBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        nxbTbl = new javax.swing.JTable();

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        toolBar2.add(jPanel10, new java.awt.GridBagConstraints());

        filterByPricePanel.setOpaque(false);

        jButton1.setText("Huỷ lọc");
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

        jButton2.setText("Lọc");
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

        javax.swing.GroupLayout filterByPricePanelLayout = new javax.swing.GroupLayout(filterByPricePanel);
        filterByPricePanel.setLayout(filterByPricePanelLayout);
        filterByPricePanelLayout.setHorizontalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterByPricePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );
        filterByPricePanelLayout.setVerticalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filterByPricePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        toolBar2.add(filterByPricePanel, new java.awt.GridBagConstraints());

        jPanel3.setOpaque(false);
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

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        deleteBtn.setText("Xoá");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });
        jPanel4.add(deleteBtn);

        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil-icon.png"))); // NOI18N
        updateBtn.setText("Sửa");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        jPanel4.add(updateBtn);

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton6.setText("Huỷ lọc");
        jPanel4.add(jButton6);

        exportExcelFileBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exportExcelFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/excel.png"))); // NOI18N
        exportExcelFileBtn.setText("Export");
        exportExcelFileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportExcelFileBtnMouseClicked(evt);
            }
        });
        jPanel4.add(exportExcelFileBtn);

        readExcelBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        readExcelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/excel.png"))); // NOI18N
        readExcelBtn.setText("Import");
        readExcelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                readExcelBtnMouseClicked(evt);
            }
        });
        readExcelBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                readExcelBtnKeyPressed(evt);
            }
        });
        jPanel4.add(readExcelBtn);

        toolBar2.add(jPanel4, new java.awt.GridBagConstraints());

        jScrollPane1.setViewportView(toolBar2);

        add(jScrollPane1, java.awt.BorderLayout.NORTH);

        nxbTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(nxbTbl);

        jTabbedPane1.addTab("Danh Sách NXB", jScrollPane2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        if (!addBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AddNXB addNXB = new AddNXB(mv, true, this);
        addNXB.setLocationRelativeTo(mv);
        addNXB.setVisible(true);
    }//GEN-LAST:event_addBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        if (!deleteBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.deleteItem();
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void exportExcelFileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportExcelFileBtnMouseClicked
        if (!exportExcelFileBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ExcelUtil.saveFile(mv, nxbTbl, "nxb");
    }//GEN-LAST:event_exportExcelFileBtnMouseClicked

    private void readExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readExcelBtnMouseClicked
        if (!readExcelBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] expectedHeaders = {"Mã NXB", "Tên", "Địa chỉ", "Số điện thoại", "Email"};
        this.readFile(mv, expectedHeaders);
    }//GEN-LAST:event_readExcelBtnMouseClicked

    private void readExcelBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_readExcelBtnKeyPressed

    }//GEN-LAST:event_readExcelBtnKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        loadDataNXB(filterByEmail(filterByMaNXB(filterBySDT(ascendingSort(descendingSort(listNXB))))));
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        sortCbx.setSelectedIndex(0);
        filterConditionCbx.setSelectedIndex(0);
        filterValueTxtFld2.setText("");
        loadDataNXB(listNXB);
    }//GEN-LAST:event_jButton1MouseClicked

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        if (!updateBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = this.getSelectedItemId();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn NXB muốn cập nhật!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            UpdateNXB updateNXB = new UpdateNXB(mv, true, this);
            updateNXB.setLocationRelativeTo(mv);
            updateNXB.setVisible(true);
        }
    }//GEN-LAST:event_updateBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton exportExcelFileBtn;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JTextField filterValueTxtFld2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable nxbTbl;
    private javax.swing.JButton readExcelBtn;
    private javax.swing.JLabel searchIcon8;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JPanel toolBar2;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
