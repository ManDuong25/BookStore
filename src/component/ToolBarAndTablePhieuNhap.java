/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.ChiTietPhieuNhapBUS;
import bus.NhomQuyenBUS;
import bus.PhieuNhapBUS;
import dto.ChiTietHoaDonDTO;
import dto.ChiTietPhieuNhapDTO;
import dto.PhieuNhapDTO;
import gui.MainView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.ImageIcon;

/**
 *
 * @author mansh
 */
public class ToolBarAndTablePhieuNhap extends javax.swing.JPanel {

    private MainView mv;
    private ArrayList<PhieuNhapDTO> listPn = PhieuNhapBUS.getInstance().getAll();
    private ArrayList<ChiTietPhieuNhapDTO> listCtpn = ChiTietPhieuNhapBUS.getInstance().getAll();
    private ArrayList<ChiTietPhieuNhapDTO> ctpnOfSelectedPn = new ArrayList<ChiTietPhieuNhapDTO>();

    /**
     * Creates new form ToolBarAndTablePhieuNhap
     */
    public ToolBarAndTablePhieuNhap(MainView mv) {
        initComponents();
        this.mv = mv;
        loadDataPhieuNhap(listPn, phieuNhapTbl);
        loadDataCTPN(ctpnOfSelectedPn, chiTietPhieuNhapTbl);
        dateFromChooser.setEnabled(false);
        dateToChooser.setEnabled(false);
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mã PN");
        filterConditionCbx.addItem("Theo ngày");
        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q7")) {
            addBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXoaQ(mv.getNv().getMaNhomQuyen(), "Q7")) {
            deleteBtn.setEnabled(false);
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToExcelQ(mv.getNv().getMaNhomQuyen(), "Q7")) {
            readExcelBtn.setEnabled(false);
            exportExcelFileBtn.setEnabled(false);
        }
    }

    public void refreshDataPnAndCtpn() {
        listPn = PhieuNhapBUS.getInstance().getAll();
        listCtpn = ChiTietPhieuNhapBUS.getInstance().getAll();
        ctpnOfSelectedPn.clear();
    }

    public void loadDataPhieuNhap(ArrayList<PhieuNhapDTO> listHd, JTable tbl) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã phiếu");
        defaultTableModel.addColumn("Mã nhân viên");
        defaultTableModel.addColumn("Mã NCC");
        defaultTableModel.addColumn("Ngày nhập");
        defaultTableModel.addColumn("Tổng tiền");

        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");

        for (PhieuNhapDTO pn : listHd) {
            if (pn.getTrangThai() == 1) {
                // Định dạng giá trị của cột tongTien
                String tongTienFormatted = decimalFormat.format(pn.getTongTien());
                defaultTableModel.addRow(new Object[]{
                    pn.getMaPhieu(),
                    pn.getMaNV(),
                    pn.getMaNCC(),
                    pn.getNgayNhap(),
                    tongTienFormatted}); // Thêm giá trị đã được định dạng vào bảng
            }
        }

        tbl.setModel(defaultTableModel);
        tbl.setRowHeight(100);
        tbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataCTPN(ArrayList<ChiTietPhieuNhapDTO> listCtpn, JTable tbl) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã phiếu");
        defaultTableModel.addColumn("Mã sách");
        defaultTableModel.addColumn("Giá sách");
        defaultTableModel.addColumn("Số lượng");

        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");

        for (ChiTietPhieuNhapDTO ctpn : listCtpn) {
            // Định dạng giá trị của cột giaSach
            String giaSachFormatted = decimalFormat.format(ctpn.getGiaSach());

            defaultTableModel.addRow(new Object[]{
                ctpn.getMaPhieu(),
                ctpn.getMaSach(),
                giaSachFormatted, // Thêm giá trị đã được định dạng vào bảng
                ctpn.getSoLuongNhap()});
        }
        tbl.setModel(defaultTableModel);
        tbl.setRowHeight(100);
        tbl.getTableHeader().setReorderingAllowed(false);
    }

    public String getSelectedItemIdPhieuNhap() {
        DefaultTableModel dtm = (DefaultTableModel) phieuNhapTbl.getModel();
        int selectedRowIndex = phieuNhapTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    private int deleteCTPN() {
        for (ChiTietPhieuNhapDTO ctpn : ctpnOfSelectedPn) {
            int result = ChiTietPhieuNhapBUS.getInstance().delete(ctpn);
            if (result <= 0) {
                return 0;
            }
        }
        return 1;
    }

    public void deleteItem() {
        String id = getSelectedItemIdPhieuNhap();
        if (id == null || id.isEmpty()) { // Kiểm tra xem id có rỗng không
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn phiếu nhập muốn xoá!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xóa phiếu nhập này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
//                PhieuNhapDTO pn = new PhieuNhapDTO();
//                pn.setMaPhieu(id);
//                int resultDeleteCTPN = deleteCTPN();
//                System.out.println(resultDeleteCTPN);
//                if (resultDeleteCTPN == 1) {
//                    int resultDeletePn = PhieuNhapBUS.getInstance().delete(pn);
//                    if (resultDeletePn > 0) {
//                        refreshDataPnAndCtpn();
//                        loadDataPhieuNhap(listPn, phieuNhapTbl);
//                        loadDataCTPN(ctpnOfSelectedPn, chiTietPhieuNhapTbl);
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Xoá phiếu nhập thất bại!", "Thất bại", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else if (resultDeleteCTPN == 0) {
//                    JOptionPane.showMessageDialog(this, "Xoá chi tiết phiếu nhập thất bại!", "Thất bại", JOptionPane.ERROR_MESSAGE);
//                }
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaPhieu(id);
                int resultDeletePN = PhieuNhapBUS.getInstance().delete(pn);
                if (resultDeletePN > 0) {
                    for (ChiTietPhieuNhapDTO ctpn : listCtpn) {
                        if (ctpn.getMaPhieu().equals(id)) {
                            ChiTietPhieuNhapBUS.getInstance().delete(ctpn);
                        }
                    }
                    refreshDataPnAndCtpn();
                    loadDataPhieuNhap(listPn, phieuNhapTbl);
                    loadDataCTPN(ctpnOfSelectedPn, chiTietPhieuNhapTbl);
                } else {
                    JOptionPane.showMessageDialog(mv, "Xoá hoá đơn không thành công!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void saveFile(JFrame main) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(main);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                String filePath = saveFile.toString();
                if (!filePath.endsWith(".xlsx")) {
                    saveFile = new File(filePath + ".xlsx");
                }
                if (saveFile.exists()) {
                    int result = JOptionPane.showConfirmDialog(main, "Tệp đã tồn tại. Bạn có muốn ghi đè không?", "Xác nhận ghi đè", JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return; // Không ghi đè, kết thúc phương thức
                    }
                }
                Workbook wb = new XSSFWorkbook();
                Sheet sheetHoaDon = wb.createSheet("phieunhap");
                Sheet sheetChiTietHoaDon = wb.createSheet("chitietphieunhap");

                // Đối với sheetHoaDon
                Row headerRowHoaDon = sheetHoaDon.createRow(0);
                for (int i = 0; i < phieuNhapTbl.getColumnCount(); i++) {
                    headerRowHoaDon.createCell(i).setCellValue(phieuNhapTbl.getColumnName(i));
                }

                for (int j = 0; j < phieuNhapTbl.getRowCount(); j++) {
                    Row row = sheetHoaDon.createRow(j + 1); // Bắt đầu từ hàng thứ 2
                    for (int k = 0; k < phieuNhapTbl.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (phieuNhapTbl.getValueAt(j, k) != null) {
                            cell.setCellValue(phieuNhapTbl.getValueAt(j, k).toString());
                        }
                    }
                }

                // Đối với sheetChiTietHoaDon
                Row headerRowChiTietHoaDon = sheetChiTietHoaDon.createRow(0);
                headerRowChiTietHoaDon.createCell(0).setCellValue("Mã phiếu");
                headerRowChiTietHoaDon.createCell(1).setCellValue("Mã sách");
                headerRowChiTietHoaDon.createCell(2).setCellValue("Giá sách");
                headerRowChiTietHoaDon.createCell(3).setCellValue("Số lượng");

                for (int j = 0; j < listCtpn.size(); j++) {
                    ChiTietPhieuNhapDTO ctpn = listCtpn.get(j);
                    Row row = sheetChiTietHoaDon.createRow(j + 1); // Bắt đầu từ hàng thứ 2
                    row.createCell(0).setCellValue(ctpn.getMaPhieu());
                    row.createCell(1).setCellValue(ctpn.getMaSach());
                    row.createCell(2).setCellValue(ctpn.getGiaSach());
                    row.createCell(3).setCellValue(ctpn.getSoLuongNhap());
                }

                FileOutputStream out = new FileOutputStream(saveFile);
                wb.write(out);
                wb.close();
                out.close();
            } else {
                JOptionPane.showMessageDialog(main, "Huỷ tạo file excel!");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    public void changeCTPNToFitPN(ArrayList<ChiTietPhieuNhapDTO> list, String maPnOld, String maPnNew) {
        for (ChiTietPhieuNhapDTO ctpn : list) {
            if (ctpn.getMaPhieu().equals(maPnOld)) {
                ctpn.setMaPhieu(maPnNew);
            }
        }
    }

    public void readFile(JFrame main, String[] expectedHeadersPhieuNhap, String[] expectedHeadersChiTietPhieuNhap) {
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
                XSSFSheet sheetPhieuNhap = wb.getSheet("phieunhap");
                XSSFSheet sheetChiTietPhieuNhap = wb.getSheet("chitietphieunhap");

                // Đối với headers ở cả 2 sheet hoadon và chitiethoadon
                Row headerRowPhieuNhap = sheetPhieuNhap.getRow(0);
                Row headerRowChiTietPhieuNhap = sheetChiTietPhieuNhap.getRow(0);

                boolean headerMatchesPhieuNhap = true;
                boolean headerMatchesChiTietPhieuNhap = true;
                if (headerRowPhieuNhap != null) {
                    for (int i = 0; i < expectedHeadersPhieuNhap.length; i++) {
                        Cell cell = headerRowPhieuNhap.getCell(i);
                        if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedHeadersPhieuNhap[i])) {
                            headerMatchesPhieuNhap = false;
                            break;
                        }
                    }

                    for (int i = 0; i < expectedHeadersChiTietPhieuNhap.length; i++) {
                        Cell cell = headerRowChiTietPhieuNhap.getCell(i);
                        if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedHeadersChiTietPhieuNhap[i])) {
                            headerMatchesChiTietPhieuNhap = false;
                            break;
                        }
                    }
                } else {
                    headerMatchesPhieuNhap = false;
                    headerMatchesChiTietPhieuNhap = false;
                }

                if (!headerMatchesPhieuNhap || !headerMatchesChiTietPhieuNhap) {
                    JOptionPane.showMessageDialog(main, "File không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    wb.close();
                    file.close();
                    return;
                }

                ArrayList<PhieuNhapDTO> pnList = new ArrayList<PhieuNhapDTO>();
                ArrayList<ChiTietPhieuNhapDTO> ctpnList = new ArrayList<ChiTietPhieuNhapDTO>();

                // đối với chi tiết phiếu nhập
                for (int rowChiTietHoaDon = 1; rowChiTietHoaDon <= sheetChiTietPhieuNhap.getLastRowNum(); rowChiTietHoaDon++) {
                    XSSFRow excelRowCTHD = sheetChiTietPhieuNhap.getRow(rowChiTietHoaDon);
                    Cell cellMaPhieu = excelRowCTHD.getCell(0);
                    String maPhieu;
                    if (cellMaPhieu.getCellType() == CellType.NUMERIC) {
                        double numericValue = cellMaPhieu.getNumericCellValue();
                        maPhieu = String.valueOf(numericValue);
                    } else {
                        maPhieu = cellMaPhieu.getStringCellValue().trim();
                    }

                    Cell cellMaSach = excelRowCTHD.getCell(1);
                    String maSach;
                    if (cellMaSach.getCellType() == CellType.NUMERIC) {
                        double numericValue = cellMaSach.getNumericCellValue();
                        maSach = String.valueOf(numericValue);
                    } else {
                        maSach = cellMaSach.getStringCellValue().trim();
                    }

                    Cell cellGiaSach = excelRowCTHD.getCell(2);
                    String giaSach;
                    if (cellGiaSach.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellGiaSach.getNumericCellValue();
                        giaSach = String.valueOf(numericValue);
                    } else {
                        giaSach = cellGiaSach.getStringCellValue().trim().replaceAll("[^0-9.]+", "");
                    }

                    Cell cellSoLuongNhap = excelRowCTHD.getCell(3);
                    String soLuongNhap;
                    if (cellSoLuongNhap.getCellType() == CellType.NUMERIC) {
                        double numericValue = cellSoLuongNhap.getNumericCellValue();
                        int intValue = (int) numericValue;
                        soLuongNhap = String.valueOf(intValue);
                    } else {
                        soLuongNhap = cellSoLuongNhap.getStringCellValue().trim();
                    }

                    String errors = ChiTietPhieuNhapBUS.getInstance().validateData(maPhieu, maSach, giaSach, soLuongNhap);
                    if (errors == null || errors.isEmpty()) {
                        ChiTietPhieuNhapDTO ctpnNew = ChiTietPhieuNhapBUS.getInstance().createNewCTPN(maPhieu, maSach, giaSach, soLuongNhap);
                        ctpnList.add(ctpnNew);
                    } else {
                        JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi ở dòng " + (rowChiTietHoaDon + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        wb.close();
                        file.close();
                        return;
                    }
                }

                // đối với phiếu nhập
                for (int row = 1; row <= sheetPhieuNhap.getLastRowNum(); row++) {
                    XSSFRow excelRow = sheetPhieuNhap.getRow(row);
                    if (excelRow != null) { // Kiểm tra dòng có tồn tại không
                        String maPhieu = excelRow.getCell(0) != null ? excelRow.getCell(0).getStringCellValue().trim() : "";
                        String maNV = excelRow.getCell(1) != null ? excelRow.getCell(1).getStringCellValue().trim() : "";
                        String maNCC = excelRow.getCell(2) != null ? excelRow.getCell(2).getStringCellValue().trim() : "";
                        Cell cellNgayNhap = excelRow.getCell(3);
                        String ngayNhap;
                        if (cellNgayNhap != null) {
                            if (cellNgayNhap.getCellType() == CellType.NUMERIC) {
                                // Kiểm tra xem ô có định dạng ngày tháng không
                                if (DateUtil.isCellDateFormatted(cellNgayNhap)) {
                                    Date date = cellNgayNhap.getDateCellValue();
                                    // Chuyển đổi ngày thành chuỗi theo định dạng mong muốn
                                    ngayNhap = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                } else {
                                    // Trường hợp ô là số nhưng không phải là ngày tháng
                                    // Xử lý theo nhu cầu của bạn, ví dụ:
                                    double numericValue = cellNgayNhap.getNumericCellValue();
                                    ngayNhap = String.valueOf(numericValue);
                                }
                            } else if (cellNgayNhap.getCellType() == CellType.STRING) {
                                // Trường hợp ô chứa chuỗi
                                ngayNhap = cellNgayNhap.getStringCellValue().trim();
                            } else {
                                ngayNhap = "";
                            }
                        } else {
                            ngayNhap = "";
                        }
                        Cell cellTongTien = excelRow.getCell(4);
                        String tongTien;
                        if (cellTongTien.getCellType() == CellType.NUMERIC) {
                            // Handle numeric value, such as formatting it to string
                            double numericValue = cellTongTien.getNumericCellValue();
                            tongTien = String.valueOf(numericValue);
                        } else {
                            tongTien = cellTongTien.getStringCellValue().trim().replaceAll("[^0-9.]+", "");
                        }

                        String errors = PhieuNhapBUS.getInstance().validateData(maPhieu, ngayNhap, maNV, maNCC, tongTien);
                        if (errors == null || errors.isEmpty()) {
                            PhieuNhapDTO pnNew = PhieuNhapBUS.getInstance().createUpdatePn(maPhieu, ngayNhap, maNV, maNCC, tongTien);
                            pnList.add(pnNew);
                        } else {
                            JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi ở dòng " + (row + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }

                // Check file excel phải có dữ liệu đúng
                for (ChiTietPhieuNhapDTO ctpn : ctpnList) {
                    int flag = 0;
                    for (PhieuNhapDTO pn : pnList) {
                        if (pn.getMaPhieu().equals(ctpn.getMaPhieu())) {
                            flag = 1;
                            continue;
                        }
                    }
                    if (flag == 0) {
                        JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi: Không tồn tại hoá đơn có mã là: " + ctpn.getMaPhieu(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        wb.close();
                        file.close();
                        return;
                    }
                }

                // Check chi tiết phiếu nhập và phiếu nhập có khớp về giá sách và số lượng không
                for (PhieuNhapDTO pn : pnList) {
                    int sum = 0;
                    for (ChiTietPhieuNhapDTO ctpn : ctpnList) {
                        if (pn.getMaPhieu().equals(ctpn.getMaPhieu())) {
                            sum += ctpn.getSoLuongNhap() * ctpn.getGiaSach();
                        }
                    }
                    if (!(sum == pn.getTongTien())) {
                        JOptionPane.showMessageDialog(this, "Sai dữ liệu (lỗi tổng tiền) ở phiếu có mã là: " + pn.getMaPhieu(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        wb.close();
                        file.close();
                        return;
                    }
                }

                // Check phiếu nhập trùng lặp mã
                for (int i = 0; i < pnList.size() - 1; i++) {
                    for (int j = i + 1; j < pnList.size(); j++) {
                        if (pnList.get(i).getMaPhieu().equals(pnList.get(j).getMaPhieu())) {
                            JOptionPane.showMessageDialog(this, "Trùng lặp mã phiếu có mã là: " + pnList.get(i).getMaPhieu(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }

                // check chi tiết phiếu nhập trùng lặp mã sách và mã phiếu cùng lúc
                for (int i = 0; i < ctpnList.size() - 1; i++) {
                    for (int j = i + 1; j < ctpnList.size(); j++) {
                        if (ctpnList.get(i).getMaPhieu().equals(ctpnList.get(j).getMaPhieu()) && ctpnList.get(i).getMaSach().equals(ctpnList.get(j).getMaSach())) {
                            JOptionPane.showMessageDialog(this, "Trùng lặp mã phiếu, mã sách tại mã phiếu: " + ctpnList.get(i).getMaPhieu() + ", và mã sách: " + ctpnList.get(i).getMaSach(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }

                int countHdRemove = 0;
                // Kiểm tra và loại bỏ các hoá đơn không có chi tiết hoá đơn tương ứng
                for (Iterator<PhieuNhapDTO> iterator = pnList.iterator(); iterator.hasNext();) {
                    PhieuNhapDTO pn = iterator.next();
                    boolean hasDetail = false;
                    for (ChiTietPhieuNhapDTO ctpn : ctpnList) {
                        if (pn.getMaPhieu().equals(ctpn.getMaPhieu())) {
                            hasDetail = true;
                            break;
                        }
                    }
                    // Nếu không có chi tiết hoá đơn tương ứng, loại bỏ hoá đơn khỏi danh sách
                    if (!hasDetail) {
                        countHdRemove++;
                        iterator.remove();
                    }
                }
                JOptionPane.showMessageDialog(mv, "Đã loại bỏ " + countHdRemove + " hoá đơn không tồn tại thực sự (không có chi tiết hoá đơn): ", "Cảnh báo!", JOptionPane.INFORMATION_MESSAGE);

                // Nếu đã tồn tại hoá đơn thì không tạo mới hoá đơn, Nếu chưa tồn tại thì tạo mới và chuyển mã thuộc chi tiết hoá đơn của hoá đơn đó thành mã hoá đơn của hoá đơn đó
                for (PhieuNhapDTO pn : pnList) {
                    int flag = 0;
                    for (PhieuNhapDTO pnInDB : listPn) {
                        if (pn.getMaNV().equals(pnInDB.getMaNV()) && pn.getNgayNhap().equals(pnInDB.getNgayNhap()) && pn.getTongTien() == pnInDB.getTongTien()) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        PhieuNhapDTO newPn = PhieuNhapBUS.getInstance().createNewPN(pn);
                        System.out.println(newPn);
                        PhieuNhapBUS.getInstance().add(newPn);
                        changeCTPNToFitPN(ctpnList, pn.getMaPhieu(), newPn.getMaPhieu());
                    }
                }
                PhieuNhapBUS.getInstance().getAll();
                // Nếu chi tiết hoá đơn đã tồn tại mã hoá đơn và mã sách trong DB thì khỏi ghi đè, ngược lại thì tạo mới đối tượng
                for (ChiTietPhieuNhapDTO ctpn : ctpnList) {
                    int flag = 0;
                    for (ChiTietPhieuNhapDTO ctpnInDB : listCtpn) {
                        if (ctpn.getMaPhieu().equals(ctpnInDB.getMaPhieu()) && ctpn.getMaSach().equals(ctpnInDB.getMaSach())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        if (PhieuNhapBUS.getInstance().checkMaPhieuNhapExist(ctpn.getMaPhieu())) {
                            int result = ChiTietPhieuNhapBUS.getInstance().add(ctpn);
                        } else {
                            JOptionPane.showMessageDialog(mv, "Dữ liệu bị lỗi: Không tồn tại hoá đơn có mã là: " + ctpn.getMaPhieu(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }
                ChiTietPhieuNhapBUS.getInstance().getAll();

                // Nếu đã thông qua tất cả các test trên thì thưc hiện reload lại listHd và listCthd
                refreshDataPnAndCtpn();
                loadDataPhieuNhap(listPn, phieuNhapTbl);
                loadDataCTPN(ctpnOfSelectedPn, chiTietPhieuNhapTbl);

                wb.close();
                file.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    public ArrayList<PhieuNhapDTO> filterByDate(ArrayList<PhieuNhapDTO> list) {
        if (filterByDateRadio.isSelected()) {
            Date dateFrom = dateFromChooser.getDate();
            Date dateTo = dateToChooser.getDate();
            if (dateFrom == null && dateTo == null) {
                return list;
            }
            ArrayList<PhieuNhapDTO> filteredList = new ArrayList<PhieuNhapDTO>();
            for (PhieuNhapDTO pn : listPn) {
                Date ngayLap = pn.getNgayNhap();

                if (dateFrom != null && dateTo == null) {
                    if (!ngayLap.before(dateFrom)) {
                        filteredList.add(pn);
                    }
                } else if (dateFrom == null && dateTo != null) {
                    if (!ngayLap.after(dateTo)) {
                        filteredList.add(pn);
                    }
                } else if (dateFrom != null && dateTo != null) {
                    if (!ngayLap.before(dateFrom) && !ngayLap.after(dateTo)) {
                        filteredList.add(pn);
                    }
                }
            }
            return filteredList;
        }
        return list;
    }

    public ArrayList<PhieuNhapDTO> filterByMaPN(ArrayList<PhieuNhapDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 1) {
            ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (PhieuNhapDTO pn : list) {
                    if (pn.getMaPhieu().contains(filterValueTxtFld2.getText())) {
                        result.add(pn);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<PhieuNhapDTO> ascendingSort(ArrayList<PhieuNhapDTO> list) {
        int sortIndex = sortCbx.getSelectedIndex();
        int filterIndex = filterConditionCbx.getSelectedIndex();

        Comparator<PhieuNhapDTO> comparator = null;

        if (sortIndex == 1 && filterIndex == 1) {
            comparator = Comparator.comparing(PhieuNhapDTO::getMaPhieu);
        } else if (sortIndex == 1 && filterIndex == 2) {
            comparator = Comparator.comparing(PhieuNhapDTO::getNgayNhap);
        }

        if (comparator != null) {
            Collections.sort(list, comparator);
        }

        return list;
    }

    public ArrayList<PhieuNhapDTO> descendingSort(ArrayList<PhieuNhapDTO> list) {
        int sortIndex = sortCbx.getSelectedIndex();
        int filterIndex = filterConditionCbx.getSelectedIndex();

        Comparator<PhieuNhapDTO> comparator = null;

        if (sortIndex == 2 && filterIndex == 1) {
            comparator = Comparator.comparing(PhieuNhapDTO::getMaPhieu).reversed();
        } else if (sortIndex == 2 && filterIndex == 2) {
            comparator = Comparator.comparing(PhieuNhapDTO::getNgayNhap).reversed();
        }

        if (comparator != null) {
            Collections.sort(list, comparator);
        }

        return list;
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
        filterByDateRadio = new javax.swing.JRadioButton();
        filterByPricePanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dateFromChooser = new com.toedter.calendar.JDateChooser();
        dateToChooser = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        exportExcelFileBtn = new javax.swing.JButton();
        readExcelBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        phieuNhapTbl = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        chiTietPhieuNhapTbl = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setToolTipText("");
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1135, 100));

        toolBar2.setBackground(new java.awt.Color(51, 102, 255));
        toolBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        toolBar2.setLayout(new java.awt.GridBagLayout());

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

        filterByDateRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterByDateRadio.setText("Lọc theo ngày");
        filterByDateRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterByDateRadioMouseClicked(evt);
            }
        });
        filterByDateRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterByDateRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filterByDateRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(filterByDateRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
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
                        .addComponent(dateToChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                    .addGroup(filterByPricePanelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFromChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
                .addContainerGap())
        );
        filterByPricePanelLayout.setVerticalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterByPricePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dateFromChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateToChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        toolBar2.add(jPanel3, new java.awt.GridBagConstraints());

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(320, 58));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 8, 6));

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill2.png"))); // NOI18N
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

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton6.setText("Huỷ lọc");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
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

        jPanel5.setLayout(new java.awt.BorderLayout());

        phieuNhapTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        phieuNhapTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phieuNhapTblMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(phieuNhapTbl);

        jPanel5.add(jScrollPane3, java.awt.BorderLayout.CENTER);

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
        jScrollPane4.setViewportView(chiTietPhieuNhapTbl);

        jPanel5.add(jScrollPane4, java.awt.BorderLayout.EAST);

        jScrollPane2.setViewportView(jPanel5);

        jTabbedPane1.addTab("tab1", jScrollPane2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void filterByDateRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterByDateRadioMouseClicked
        dateFromChooser.setEnabled(true);
        dateToChooser.setEnabled(true);
    }//GEN-LAST:event_filterByDateRadioMouseClicked

    private void filterByDateRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterByDateRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterByDateRadioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        if (!addBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        mv.doNhapHangAction();
    }//GEN-LAST:event_addBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        if (!deleteBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        deleteItem();
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void exportExcelFileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportExcelFileBtnMouseClicked
        if (!exportExcelFileBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveFile(mv);
    }//GEN-LAST:event_exportExcelFileBtnMouseClicked

    private void readExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readExcelBtnMouseClicked
        if (!readExcelBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] expectedHeadersPhieuNhap = {"Mã phiếu", "Mã nhân viên", "Mã NCC", "Ngày nhập", "Tổng tiền"};
        String[] expectedHeadersChiTietPhieuNhap = {"Mã phiếu", "Mã sách", "Giá sách", "Số lượng"};
        readFile(mv, expectedHeadersPhieuNhap, expectedHeadersChiTietPhieuNhap);
    }//GEN-LAST:event_readExcelBtnMouseClicked

    private void readExcelBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_readExcelBtnKeyPressed
        
    }//GEN-LAST:event_readExcelBtnKeyPressed

    private void phieuNhapTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phieuNhapTblMouseClicked
        ctpnOfSelectedPn.clear();
        String idPhieuNhap = getSelectedItemIdPhieuNhap();
        for (ChiTietPhieuNhapDTO ctpn : listCtpn) {

            if (ctpn.getMaPhieu().equals(idPhieuNhap)) {
                ctpnOfSelectedPn.add(ctpn);
            }
        }
        loadDataCTPN(ctpnOfSelectedPn, chiTietPhieuNhapTbl);
    }//GEN-LAST:event_phieuNhapTblMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        loadDataPhieuNhap(filterByDate(filterByMaPN(ascendingSort(descendingSort(listPn)))), phieuNhapTbl);
        loadDataCTPN(new ArrayList<ChiTietPhieuNhapDTO>(), chiTietPhieuNhapTbl);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        sortCbx.setSelectedIndex(0);
        filterConditionCbx.setSelectedIndex(0);
        filterValueTxtFld2.setText("");
        filterByDateRadio.setSelected(false);
        dateFromChooser.setDate(null);
        dateToChooser.setDate(null);
        dateFromChooser.setEnabled(false);
        dateToChooser.setEnabled(false);
        loadDataPhieuNhap(listPn, phieuNhapTbl);
        loadDataCTPN(new ArrayList<ChiTietPhieuNhapDTO>(), chiTietPhieuNhapTbl);
    }//GEN-LAST:event_jButton6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTable chiTietPhieuNhapTbl;
    private com.toedter.calendar.JDateChooser dateFromChooser;
    private com.toedter.calendar.JDateChooser dateToChooser;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton exportExcelFileBtn;
    private javax.swing.JRadioButton filterByDateRadio;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JTextField filterValueTxtFld2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable phieuNhapTbl;
    private javax.swing.JButton readExcelBtn;
    private javax.swing.JLabel searchIcon8;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JPanel toolBar2;
    // End of variables declaration//GEN-END:variables
}
