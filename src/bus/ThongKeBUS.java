/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.ThongKeDAO;
import dto.ThongKeDoanhThuDTO;
import dto.ThongKeHieuSuatNhanVienDTO;
import dto.ThongKeSachBanDuocDTO;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class ThongKeBUS {

    private static ThongKeBUS instance;

    public static ThongKeBUS getInstance() {
        if (instance == null) {
            instance = new ThongKeBUS();
        }
        return instance;
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeTheoNgay() {
        return ThongKeDAO.getInstance().thongKeTheoNgay();
    }
    
    public ArrayList<ThongKeDoanhThuDTO> thongKeTheoQuy(String year) {
        return ThongKeDAO.getInstance().thongKeTheoQuyTrongNam(year);
    }
    
    public ArrayList<ThongKeDoanhThuDTO> thongKeTheoNam(String year) {
        return ThongKeDAO.getInstance().thongKeTheoNam(year);
    }
    
    public ArrayList<String> getAllHoaDonYear() {
        return ThongKeDAO.getInstance().getAllYearHoaDon();
    }

    public ArrayList<ThongKeHieuSuatNhanVienDTO> thongKeHieuSuatNVTheoNgay() {
        return ThongKeDAO.getInstance().thongKeHieuSuatNVTheoNgay();
    }
    
    public ArrayList<ThongKeHieuSuatNhanVienDTO> thongKeHieuSuatNVTheoQuyTrongNam(String year, String quy) {
        return ThongKeDAO.getInstance().thongKeHieuSuatNVTheoQuyTrongNam(year, quy);
    }
    
    public ArrayList<ThongKeHieuSuatNhanVienDTO> thongKeHieuSuatNVTheoNam(String year) {
        return ThongKeDAO.getInstance().thongKeHieuSuatNVTheoNam(year);
    }
    
    public ArrayList<ThongKeSachBanDuocDTO> thongKeSachBanDuocTheoQuy(String year, String quy) {
        return ThongKeDAO.getInstance().thongKeSachBanDuocTheoQuy(year, quy);
    }
    
    public ArrayList<ThongKeSachBanDuocDTO> thongKeSachBanDuocTheoNam(String year) {
        return ThongKeDAO.getInstance().thongKeSachBanDuocTheoNam(year);
    }
}
