/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.ChiTietHoaDonBUS;
import bus.HoaDonBUS;
import bus.NhomQuyenBUS;
import db.JDBCUtil;
import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;
import gui.MainView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ExcelUtil;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRStyledTextParser;
import javax.swing.ImageIcon;

/**
 *
 * @author mansh
 */
public class ToolBarAndTableHoaDon extends javax.swing.JPanel {

    private MainView mv;
    private ArrayList<HoaDonDTO> listHd = HoaDonBUS.getInstance().getAll();
    private ArrayList<ChiTietHoaDonDTO> listCthd = ChiTietHoaDonBUS.getInstance().getAll();
    private ArrayList<ChiTietHoaDonDTO> cthdOfSelectedHd = new ArrayList<ChiTietHoaDonDTO>();

    public void refreshDataHoaDonAndCTHD() {
        listHd = HoaDonBUS.getInstance().getAll();
        listCthd = ChiTietHoaDonBUS.getInstance().getAll();
        cthdOfSelectedHd.clear();
    }

    public ToolBarAndTableHoaDon(MainView mv) {
        initComponents();
        this.mv = mv;
        loadDataHoaDon(listHd, hoaDonTbl);
        loadDataCTHD(cthdOfSelectedHd, chiTietHoaDonTbl);
        dateFromChooser.setEnabled(false);
        dateToChooser.setEnabled(false);
        filterConditionCbx.addItem("Mặc định");
        filterConditionCbx.addItem("Theo mã HĐ");
        filterConditionCbx.addItem("Theo ngày");
        sortCbx.addItem("Mặc định");
        sortCbx.addItem("Tăng dần");
        sortCbx.addItem("Giảm dần");
        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q2")) {
            addBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXoaQ(mv.getNv().getMaNhomQuyen(), "Q2")) {
            deleteBtn.setEnabled(false);
        }

        if (!NhomQuyenBUS.getInstance().checkIfAllowToExcelQ(mv.getNv().getMaNhomQuyen(), "Q2")) {
            readExcelBtn.setEnabled(false);
            exportExcelFileBtn.setEnabled(false);
        }
    }

    public void loadDataHoaDon(ArrayList<HoaDonDTO> listHd, JTable tbl) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã hoá đơn");
        defaultTableModel.addColumn("Mã nhân viên");
        defaultTableModel.addColumn("Mã khách hàng");
        defaultTableModel.addColumn("Ngày lập");
        defaultTableModel.addColumn("Thông tin ưu đãi");
        defaultTableModel.addColumn("Tổng tiền");

        for (HoaDonDTO hd : listHd) {
            if (hd.getTrangThai() == 1) {
                defaultTableModel.addRow(new Object[]{
                    hd.getMaHoaDon(),
                    hd.getMaNV(),
                    hd.getMaKH(),
                    hd.getNgayLap(),
                    hd.getThongTinUuDai(),
                    hd.getTongTien(),});
            }
//            System.out.println(sach);
        }
        tbl.setModel(defaultTableModel);
        tbl.setRowHeight(100);
        tbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataCTHD(ArrayList<ChiTietHoaDonDTO> listCthd, JTable tbl) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã hoá đơn");
        defaultTableModel.addColumn("Mã sách");
        defaultTableModel.addColumn("Giá sách");
        defaultTableModel.addColumn("Số lượng");

        for (ChiTietHoaDonDTO cthd : listCthd) {
            defaultTableModel.addRow(new Object[]{
                cthd.getMaHoaDon(),
                cthd.getMaSach(),
                cthd.getGiaSach(),
                cthd.getSoLuongMua()});
        }
        tbl.setModel(defaultTableModel);
        tbl.setRowHeight(100);
        tbl.getTableHeader().setReorderingAllowed(false);
    }

    public String getSelectedItemIdHoaDon() {
        DefaultTableModel dtm = (DefaultTableModel) hoaDonTbl.getModel();
        int selectedRowIndex = hoaDonTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public void deleteItem() {
        String id = getSelectedItemIdHoaDon();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn hoá đơn muốn xoá!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xóa hoá đơn này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                HoaDonDTO hd = new HoaDonDTO();
                hd.setMaHoaDon(id);
                int resultDeleteHd = HoaDonBUS.getInstance().delete(hd);
                if (resultDeleteHd > 0) {
                    for (ChiTietHoaDonDTO cthd : listCthd) {
                        if (cthd.getMaHoaDon().equals(id)) {
                            ChiTietHoaDonBUS.getInstance().delete(cthd);
                        }
                    }
                    refreshDataHoaDonAndCTHD();
                    loadDataHoaDon(listHd, hoaDonTbl);
                    loadDataCTHD(cthdOfSelectedHd, chiTietHoaDonTbl);
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
                Sheet sheetHoaDon = wb.createSheet("hoadon");
                Sheet sheetChiTietHoaDon = wb.createSheet("chitiethoadon");

                // Đối với sheetHoaDon
                Row headerRowHoaDon = sheetHoaDon.createRow(0);
                for (int i = 0; i < hoaDonTbl.getColumnCount(); i++) {
                    headerRowHoaDon.createCell(i).setCellValue(hoaDonTbl.getColumnName(i));
                }

                for (int j = 0; j < hoaDonTbl.getRowCount(); j++) {
                    Row row = sheetHoaDon.createRow(j + 1); // Bắt đầu từ hàng thứ 2
                    for (int k = 0; k < hoaDonTbl.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        Object value = hoaDonTbl.getValueAt(j, k);

                        // Kiểm tra nếu là cột "Mã khách hàng" và giá trị là rỗng
                        if (k == 2 && (value == null || value.toString().trim().isEmpty())) {
                            cell.setCellValue("Vãng lai");
                        } else if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                // Đối với sheetChiTietHoaDon
                Row headerRowChiTietHoaDon = sheetChiTietHoaDon.createRow(0);
                headerRowChiTietHoaDon.createCell(0).setCellValue("Mã hoá đơn");
                headerRowChiTietHoaDon.createCell(1).setCellValue("Mã sách");
                headerRowChiTietHoaDon.createCell(2).setCellValue("Giá sách");
                headerRowChiTietHoaDon.createCell(3).setCellValue("Số lượng");

                for (int j = 0; j < listCthd.size(); j++) {
                    ChiTietHoaDonDTO cthd = listCthd.get(j);
                    Row row = sheetChiTietHoaDon.createRow(j + 1); // Bắt đầu từ hàng thứ 2
                    row.createCell(0).setCellValue(cthd.getMaHoaDon());
                    row.createCell(1).setCellValue(cthd.getMaSach());
                    row.createCell(2).setCellValue(cthd.getGiaSach());
                    row.createCell(3).setCellValue(cthd.getSoLuongMua());
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

    public void changeCTHDToFitHD(ArrayList<ChiTietHoaDonDTO> list, String maHdOld, String maHdNew) {
        for (ChiTietHoaDonDTO cthd : list) {
            if (cthd.getMaHoaDon().equals(maHdOld)) {
                cthd.setMaHoaDon(maHdNew);
            }
        }
    }

    public void readFile(JFrame main, String[] expectedHeadersHoaDon, String[] expectedHeadersChiTietHoaDon) {
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
                XSSFSheet sheetHoaDon = wb.getSheet("hoadon");
                XSSFSheet sheetChiTietHoaDon = wb.getSheet("chitiethoadon");

                // Đối với headers ở cả 2 sheet hoadon và chitiethoadon
                Row headerRowHoaDon = sheetHoaDon.getRow(0);
                Row headerRowChiTietHoaDon = sheetChiTietHoaDon.getRow(0);

                boolean headerMatchesHoaDon = true;
                boolean headerMatchesChiTietHoaDon = true;
                if (headerRowHoaDon != null) {
                    for (int i = 0; i < expectedHeadersHoaDon.length; i++) {
                        Cell cell = headerRowHoaDon.getCell(i);
                        if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedHeadersHoaDon[i])) {
                            headerMatchesHoaDon = false;
                            break;
                        }
                    }

                    for (int i = 0; i < expectedHeadersChiTietHoaDon.length; i++) {
                        Cell cell = headerRowChiTietHoaDon.getCell(i);
                        if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedHeadersChiTietHoaDon[i])) {
                            headerMatchesChiTietHoaDon = false;
                            break;
                        }
                    }
                } else {
                    headerMatchesHoaDon = false;
                    headerMatchesChiTietHoaDon = false;
                }

                if (!headerMatchesHoaDon || !headerMatchesChiTietHoaDon) {
                    JOptionPane.showMessageDialog(main, "File không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    wb.close();
                    file.close();
                    return;
                }
                ArrayList<HoaDonDTO> hdList = new ArrayList<HoaDonDTO>();
                ArrayList<ChiTietHoaDonDTO> cthdList = new ArrayList<ChiTietHoaDonDTO>();

                // đối với chi tiết hoá đơn
                for (int rowChiTietHoaDon = 1; rowChiTietHoaDon <= sheetChiTietHoaDon.getLastRowNum(); rowChiTietHoaDon++) {
                    XSSFRow excelRowCTHD = sheetChiTietHoaDon.getRow(rowChiTietHoaDon);
                    Cell cellMaHoaDon = excelRowCTHD.getCell(0);
                    String maHoaDon;
                    if (cellMaHoaDon.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellMaHoaDon.getNumericCellValue();
                        maHoaDon = String.valueOf(numericValue);
                    } else {
                        maHoaDon = cellMaHoaDon.getStringCellValue().trim();
                    }

                    Cell cellMaSach = excelRowCTHD.getCell(1);
                    String maSach;
                    if (cellMaSach.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellMaSach.getNumericCellValue();
                        maSach = String.valueOf(numericValue);
                    } else {
                        maSach = cellMaSach.getStringCellValue().trim();
                    }

//                    System.out.println(maSach);
                    Cell cellGiaSach = excelRowCTHD.getCell(2);
                    String giaSach;
                    if (cellGiaSach.getCellType() == CellType.NUMERIC) {
                        // Handle numeric value, such as formatting it to string
                        double numericValue = cellGiaSach.getNumericCellValue();
                        giaSach = String.valueOf(numericValue);
                    } else {
                        giaSach = cellGiaSach.getStringCellValue().trim();
                    }

                    Cell cellSoLuongMua = excelRowCTHD.getCell(3);
                    String soLuongMua;
                    if (cellSoLuongMua.getCellType() == CellType.NUMERIC) {
                        double numericValue = cellSoLuongMua.getNumericCellValue();
                        int intValue = (int) numericValue;
                        soLuongMua = String.valueOf(intValue);
                    } else {
                        soLuongMua = cellSoLuongMua.getStringCellValue().trim();
                    }

                    String errors = ChiTietHoaDonBUS.getInstance().validateData(maHoaDon, maSach, giaSach, soLuongMua);
                    if (errors == null || errors.isEmpty()) {
                        ChiTietHoaDonDTO cthdNew = ChiTietHoaDonBUS.getInstance().createNewCTHD(maHoaDon, maSach, giaSach, soLuongMua);
                        cthdList.add(cthdNew);
                    } else {
                        JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi ở dòng " + (rowChiTietHoaDon + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        wb.close();
                        file.close();
                        return;
                    }
                }

                // Tiếp tục xử lý file nếu header khớp đối với hoadon
                for (int row = 1; row <= sheetHoaDon.getLastRowNum(); row++) {
                    XSSFRow excelRow = sheetHoaDon.getRow(row);
                    if (excelRow != null) { // Kiểm tra dòng có tồn tại không
                        String maHoaDon = excelRow.getCell(0) != null ? excelRow.getCell(0).getStringCellValue().trim() : "";
                        String maNV = excelRow.getCell(1) != null ? excelRow.getCell(1).getStringCellValue().trim() : "";
                        String maKH = excelRow.getCell(2) != null ? excelRow.getCell(2).getStringCellValue().trim() : "";
                        Cell cellNgayLap = excelRow.getCell(3);
                        String ngayLap;
                        if (cellNgayLap != null) {
                            if (cellNgayLap.getCellType() == CellType.NUMERIC) {
                                // Kiểm tra xem ô có định dạng ngày tháng không
                                if (DateUtil.isCellDateFormatted(cellNgayLap)) {
                                    Date date = cellNgayLap.getDateCellValue();
                                    // Chuyển đổi ngày thành chuỗi theo định dạng mong muốn
                                    ngayLap = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                } else {
                                    // Trường hợp ô là số nhưng không phải là ngày tháng
                                    // Xử lý theo nhu cầu của bạn, ví dụ:
                                    double numericValue = cellNgayLap.getNumericCellValue();
                                    ngayLap = String.valueOf(numericValue);
                                }
                            } else if (cellNgayLap.getCellType() == CellType.STRING) {
                                // Trường hợp ô chứa chuỗi
                                ngayLap = cellNgayLap.getStringCellValue().trim();
                            } else {
                                ngayLap = "";
                            }
                        } else {
                            ngayLap = "";
                        }
                        String thongTinUuDai = excelRow.getCell(4) != null ? excelRow.getCell(4).getStringCellValue().trim() : "";

                        Cell cellTongTien = excelRow.getCell(5);
                        String tongTien;
                        if (cellTongTien.getCellType() == CellType.NUMERIC) {
                            // Handle numeric value, such as formatting it to string
                            double numericValue = cellTongTien.getNumericCellValue();
                            tongTien = String.valueOf(numericValue);
                        } else {
                            tongTien = cellTongTien.getStringCellValue().trim().replaceAll("[^0-9.]+", "");
                        }

                        String errors = HoaDonBUS.getInstance().validateData(maNV, maKH, ngayLap, thongTinUuDai, tongTien);
                        if (errors == null || errors.isEmpty()) {
                            if (maKH.equals("Vãng lai")) {
                                maKH = null;
                            }
                            HoaDonDTO hdNew = HoaDonBUS.getInstance().createUpdateHd(maHoaDon, maNV, maKH, ngayLap, thongTinUuDai, tongTien);
                            hdList.add(hdNew);
                        } else {
                            JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi ở dòng " + (row + 1) + ": " + errors, "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }

                // Check file excel phải có dữ liệu đúng
                for (ChiTietHoaDonDTO cthd : cthdList) {
                    int flag = 0;
                    for (HoaDonDTO hd : hdList) {
                        if (hd.getMaHoaDon().equals(cthd.getMaHoaDon())) {
                            flag = 1;
                            continue;
                        }
                    }
                    if (flag == 0) {
                        JOptionPane.showMessageDialog(this, "Dữ liệu bị lỗi: Không tồn tại hoá đơn có mã là: " + cthd.getMaHoaDon(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        wb.close();
                        file.close();
                        return;
                    }
                }

                // Check chi tiết hoá đơn và hoá đơn có khớp về giá sách và số lượng không
                for (HoaDonDTO hd : hdList) {
                    int sum = 0;
                    for (ChiTietHoaDonDTO cthd : cthdList) {
                        if (hd.getMaHoaDon().equals(cthd.getMaHoaDon())) {
                            sum += cthd.getSoLuongMua() * cthd.getGiaSach();
                        }
                    }
                    if (!(sum == hd.getTongTien())) {
                        JOptionPane.showMessageDialog(this, "Sai dữ liệu (lỗi tổng tiền) ở hoá đơn có mã là: " + hd.getMaHoaDon(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                        wb.close();
                        file.close();
                        return;
                    }
                }

                // Check hoá đơn trùng lặp mã
                for (int i = 0; i < hdList.size() - 1; i++) {
                    for (int j = i + 1; j < hdList.size(); j++) {
                        if (hdList.get(i).getMaHoaDon().equals(hdList.get(j).getMaHoaDon())) {
                            JOptionPane.showMessageDialog(this, "Trùng lặp mã hoá đơn có mã là: " + hdList.get(i).getMaHoaDon(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }

                // check chi tiết hoá đơn trùng lặp mã sách và mã hoá đơn cùng lúc
                for (int i = 0; i < cthdList.size() - 1; i++) {
                    for (int j = i + 1; j < cthdList.size(); j++) {
                        if (cthdList.get(i).getMaHoaDon().equals(cthdList.get(j).getMaHoaDon()) && cthdList.get(i).getMaSach().equals(cthdList.get(j).getMaSach())) {
                            JOptionPane.showMessageDialog(this, "Trùng lặp mã phiếu, mã sách tại mã phiếu: " + cthdList.get(i).getMaHoaDon() + ", và mã sách: " + cthdList.get(i).getMaSach(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }

                int countHdRemove = 0;
                // Kiểm tra và loại bỏ các hoá đơn không có chi tiết hoá đơn tương ứng
                for (Iterator<HoaDonDTO> iterator = hdList.iterator(); iterator.hasNext();) {
                    HoaDonDTO hd = iterator.next();
                    boolean hasDetail = false;
                    for (ChiTietHoaDonDTO cthd : cthdList) {
                        if (hd.getMaHoaDon().equals(cthd.getMaHoaDon())) {
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
                for (HoaDonDTO hd : hdList) {
                    int flag = 0;
                    for (HoaDonDTO hdInDB : listHd) {
                        if (hd.getMaKH() != null && hdInDB != null) {
                            if (hd.getMaNV().equals(hdInDB.getMaNV()) && hd.getMaKH().equals(hdInDB.getMaKH()) && hd.getNgayLap().equals(hdInDB.getNgayLap()) && hd.getTongTien() == hdInDB.getTongTien()) {
                                flag = 1;
                            }
                        } else if (hd.getMaKH() == null && hdInDB.getMaKH() == null) {
                            if (hd.getMaNV().equals(hdInDB.getMaNV()) && hd.getNgayLap().equals(hdInDB.getNgayLap()) && hd.getTongTien() == hdInDB.getTongTien()) {
                                flag = 1;
                            }
                        }
                    }
                    if (flag == 0) {
                        HoaDonDTO newHd = HoaDonBUS.getInstance().createNewHD(hd);
                        System.out.println(newHd);
                        HoaDonBUS.getInstance().add(newHd);
                        changeCTHDToFitHD(cthdList, hd.getMaHoaDon(), newHd.getMaHoaDon());
                    }
                }
                HoaDonBUS.getInstance().getAll();
                // Nếu chi tiết hoá đơn đã tồn tại mã hoá đơn và mã sách trong DB thì khỏi ghi đè, ngược lại thì tạo mới đối tượng
                for (ChiTietHoaDonDTO cthd : cthdList) {
//                    System.out.println(cthd);
                    int flag = 0;
                    for (ChiTietHoaDonDTO cthdInDB : listCthd) {
                        if (cthd.getMaHoaDon().equals(cthdInDB.getMaHoaDon()) && cthd.getMaSach().equals(cthdInDB.getMaSach())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        if (HoaDonBUS.getInstance().checkMaHoaDonExist(cthd.getMaHoaDon())) {
                            int result = ChiTietHoaDonBUS.getInstance().add(cthd);
                        } else {
                            JOptionPane.showMessageDialog(mv, "Dữ liệu bị lỗi: Không tồn tại hoá đơn có mã là: " + cthd.getMaHoaDon(), "Có lỗi xảy ra!", JOptionPane.INFORMATION_MESSAGE);
                            wb.close();
                            file.close();
                            return;
                        }
                    }
                }
                ChiTietHoaDonBUS.getInstance().getAll();

//                 Nếu đã thông qua tất cả các test trên thì thưc hiện reload lại listHd và listCthd
                refreshDataHoaDonAndCTHD();
                loadDataHoaDon(listHd, hoaDonTbl);
                loadDataCTHD(cthdOfSelectedHd, chiTietHoaDonTbl);
                wb.close();
                file.close();
                return;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    public ArrayList<HoaDonDTO> filterByDate(ArrayList<HoaDonDTO> list) {
        if (filterByDateRadio.isSelected()) {
            Date dateFrom = dateFromChooser.getDate();
            Date dateTo = dateToChooser.getDate();
            if (dateFrom == null && dateTo == null) {
                return list;
            }
            ArrayList<HoaDonDTO> filteredList = new ArrayList<HoaDonDTO>();
            for (HoaDonDTO hd : listHd) {
                Date ngayLap = hd.getNgayLap();

                if (dateFrom != null && dateTo == null) {
                    if (!ngayLap.before(dateFrom)) {
                        filteredList.add(hd);
                    }
                } else if (dateFrom == null && dateTo != null) {
                    if (!ngayLap.after(dateTo)) {
                        filteredList.add(hd);
                    }
                } else if (dateFrom != null && dateTo != null) {
                    if (!ngayLap.before(dateFrom) && !ngayLap.after(dateTo)) {
                        filteredList.add(hd);
                    }
                }
            }
            return filteredList;
        }
        return list;
    }

    public ArrayList<HoaDonDTO> filterByMaHD(ArrayList<HoaDonDTO> list) {
        if (filterConditionCbx.getSelectedIndex() == 1) {
            ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();
            if (!filterValueTxtFld2.getText().isEmpty() && filterValueTxtFld2.getText() != null) {
                for (HoaDonDTO hd : list) {
                    if (hd.getMaHoaDon().contains(filterValueTxtFld2.getText())) {
                        result.add(hd);
                    }
                }
                return result;
            }
        }
        return list;
    }

    public ArrayList<HoaDonDTO> ascendingSort(ArrayList<HoaDonDTO> list) {
        int sortIndex = sortCbx.getSelectedIndex();
        int filterIndex = filterConditionCbx.getSelectedIndex();

        Comparator<HoaDonDTO> comparator = null;

        if (sortIndex == 1 && filterIndex == 1) {
            comparator = Comparator.comparing(HoaDonDTO::getMaHoaDon);
        } else if (sortIndex == 1 && filterIndex == 2) {
            comparator = Comparator.comparing(HoaDonDTO::getNgayLap);
        }

        if (comparator != null) {
            Collections.sort(list, comparator);
        }

        return list;
    }

    public ArrayList<HoaDonDTO> descendingSort(ArrayList<HoaDonDTO> list) {
        int sortIndex = sortCbx.getSelectedIndex();
        int filterIndex = filterConditionCbx.getSelectedIndex();

        Comparator<HoaDonDTO> comparator = null;

        if (sortIndex == 2 && filterIndex == 1) {
            comparator = Comparator.comparing(HoaDonDTO::getMaHoaDon).reversed();
        } else if (sortIndex == 2 && filterIndex == 2) {
            comparator = Comparator.comparing(HoaDonDTO::getNgayLap).reversed();
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
        xuatPdfBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        exportExcelFileBtn = new javax.swing.JButton();
        readExcelBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        hoaDonTbl = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        chiTietHoaDonTbl = new javax.swing.JTable();

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
                        .addComponent(dateFromChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        xuatPdfBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xuatPdfBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pdf-icon.png"))); // NOI18N
        xuatPdfBtn.setText("PDF");
        xuatPdfBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xuatPdfBtnMouseClicked(evt);
            }
        });
        jPanel4.add(xuatPdfBtn);

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

        jPanel11.setLayout(new java.awt.BorderLayout());

        hoaDonTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        hoaDonTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoaDonTblMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(hoaDonTbl);

        jPanel11.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        chiTietHoaDonTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(chiTietHoaDonTbl);

        jPanel11.add(jScrollPane13, java.awt.BorderLayout.EAST);

        jScrollPane11.setViewportView(jPanel11);

        jTabbedPane1.addTab("tab1", jScrollPane11);

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
        mv.doBanHangAction();
    }//GEN-LAST:event_addBtnMouseClicked


    private void xuatPdfBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xuatPdfBtnMouseClicked
        try {
            String id = getSelectedItemIdHoaDon();
            if (id.isEmpty() || id == null) {
                JOptionPane.showMessageDialog(mv, "Vui lòng chọn hoá đơn muốn xuất PDF!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            } else {
                Connection c = JDBCUtil.getConnection();
                String reportPath = "./pdf/HoaDon.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(reportPath);

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("maHoaDon", id); // Đặt giá trị của tham số

                JasperPrint jp = JasperFillManager.fillReport(jr, parameters, c);

                JasperViewer.viewReport(jp, false);

                JDBCUtil.closeConnection(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mv, e);
        }
    }//GEN-LAST:event_xuatPdfBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        if (!deleteBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.deleteItem();
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void hoaDonTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonTblMouseClicked
        cthdOfSelectedHd.clear();
        String idHoaDon = getSelectedItemIdHoaDon();
        for (ChiTietHoaDonDTO cthd : listCthd) {
            if (cthd.getMaHoaDon().equals(idHoaDon)) {
                cthdOfSelectedHd.add(cthd);
            }
        }
        loadDataCTHD(cthdOfSelectedHd, chiTietHoaDonTbl);
    }//GEN-LAST:event_hoaDonTblMouseClicked

    private void exportExcelFileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportExcelFileBtnMouseClicked
        if (!exportExcelFileBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveFile(mv);
    }//GEN-LAST:event_exportExcelFileBtnMouseClicked

    private void readExcelBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_readExcelBtnKeyPressed

    }//GEN-LAST:event_readExcelBtnKeyPressed

    private void readExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readExcelBtnMouseClicked
        if (!readExcelBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] expectedHeadersHoaDon = {"Mã hoá đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Thông tin ưu đãi", "Tổng tiền"};
        String[] expectedHeadersChiTietHoaDon = {"Mã hoá đơn", "Mã sách", "Giá sách", "Số lượng"};
        readFile(mv, expectedHeadersHoaDon, expectedHeadersChiTietHoaDon);
    }//GEN-LAST:event_readExcelBtnMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        loadDataHoaDon(filterByDate(filterByMaHD(ascendingSort(descendingSort(listHd)))), hoaDonTbl);
        loadDataCTHD(new ArrayList<ChiTietHoaDonDTO>(), chiTietHoaDonTbl);
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
        loadDataHoaDon(listHd, hoaDonTbl);
        loadDataCTHD(new ArrayList<ChiTietHoaDonDTO>(), chiTietHoaDonTbl);
    }//GEN-LAST:event_jButton6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTable chiTietHoaDonTbl;
    private com.toedter.calendar.JDateChooser dateFromChooser;
    private com.toedter.calendar.JDateChooser dateToChooser;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton exportExcelFileBtn;
    private javax.swing.JRadioButton filterByDateRadio;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JComboBox<String> filterConditionCbx;
    private javax.swing.JTextField filterValueTxtFld2;
    private javax.swing.JTable hoaDonTbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton readExcelBtn;
    private javax.swing.JLabel searchIcon8;
    private javax.swing.JComboBox<String> sortCbx;
    private javax.swing.JPanel toolBar2;
    private javax.swing.JButton xuatPdfBtn;
    // End of variables declaration//GEN-END:variables
}
