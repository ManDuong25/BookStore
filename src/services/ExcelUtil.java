/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author mansh
 */
public class ExcelUtil {

    public static void saveFile(JFrame main, JTable table, String sheetName) {
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
                Sheet sheet = wb.createSheet(sheetName);

                // Thêm header là tên các cột
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    headerRow.createCell(i).setCellValue(table.getColumnName(i));
                }

                // Thêm dữ liệu từ bảng vào file Excel
                for (int j = 0; j < table.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1); // Bắt đầu từ hàng thứ 2
                    for (int k = 0; k < table.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (table.getValueAt(j, k) != null) {
                            cell.setCellValue(table.getValueAt(j, k).toString());
                        }
                    }
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

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue().trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                // Xử lý các trường hợp công thức nếu cần
                return cell.getCellFormula();
            case BLANK:
                // Xử lý trường hợp ô trống
                return "";
            default:
                // Xử lý các trường hợp khác nếu cần
                return null;
        }
    }
}
