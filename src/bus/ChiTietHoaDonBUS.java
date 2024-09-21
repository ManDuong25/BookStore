/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.ChiTietHoaDonDAO;
import dto.ChiTietHoaDonDTO;
import dto.SachDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class ChiTietHoaDonBUS implements BUSInterface<ChiTietHoaDonDTO> {

    private ArrayList<ChiTietHoaDonDTO> chiTietHoaDonList = new ArrayList<ChiTietHoaDonDTO>();
    private static ChiTietHoaDonBUS instance;

    public static ChiTietHoaDonBUS getInstance() {
        if (instance == null) {
            instance = new ChiTietHoaDonBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<ChiTietHoaDonDTO> getAll() {
        this.chiTietHoaDonList = ChiTietHoaDonDAO.getInstance().selectAll();
        return this.chiTietHoaDonList;
    }

    @Override
    public void refreshData() {
        this.chiTietHoaDonList = ChiTietHoaDonDAO.getInstance().selectAll();
    }

    @Override
    public ChiTietHoaDonDTO getById(ChiTietHoaDonDTO t) {
        return ChiTietHoaDonDAO.getInstance().selectById(t);
    }

    @Override
    public int add(ChiTietHoaDonDTO t) {
        int result = ChiTietHoaDonDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(ChiTietHoaDonDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChiTietHoaDonDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.chiTietHoaDonList.isEmpty()) {
            ArrayList<Integer> cthdIndex = new ArrayList<Integer>();
            for (ChiTietHoaDonDTO cthd : chiTietHoaDonList) {
                String[] getIndex = cthd.getMaSach().split("HD");
                cthdIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(cthdIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    @Override
    public int delete(ChiTietHoaDonDTO t) {
        return ChiTietHoaDonDAO.getInstance().delete(t);
    }

    public String validateData(String maHoaDon, ArrayList<SachDTO> list) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            return "Mã hóa đơn không được để trống.";
        }

        for (SachDTO sach : list) {
            // Check if the quantity is greater than 0
            if (sach.getSoLuong() <= 0) {
                return "Số lượng sách phải lớn hơn 0.";
            }

            // Check if the selling price is greater than 0
            if (sach.getGiaBan() <= 0) {
                return "Giá bán sách phải lớn hơn 0.";
            }

            // Check if the book code is not empty and follows the format "S" + number
            if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
                return "Mã sách không được để trống.";
            } else {
                if (!sach.getMaSach().matches("S\\d+")) {
                    return "Mã sách phải bắt đầu bằng 'S' theo sau là số thứ tự.";
                }
            }
        }

        // If all checks are passed, return null indicating no errors
        return null;
    }

    public String validateData(String maHoaDon, String maSach, String giaSach, String soLuongMua) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            return "Mã hóa đơn không được để trống.";
        } else {
            if (!maHoaDon.matches("HD\\d+")) {
                return "Mã hoá đơn phải bắt đầu bằng 'PN' theo sau là số thứ tự.";
            }
        }

        // Check if the quantity is greater than 0
        if (Integer.parseInt(soLuongMua) <= 0) {
            return "Số lượng sách phải lớn hơn 0.";
        }

        // Check if the selling price is greater than 0
        if (Double.parseDouble(giaSach) <= 0) {
            return "Giá bán sách phải lớn hơn 0.";
        }

        // Check if the book code is not empty and follows the format "S" + number
        if (maSach == null || maSach.trim().isEmpty()) {
            return "Mã sách không được để trống.";
        } else {
            if (!maSach.matches("S\\d+")) {
                return "Mã sách phải bắt đầu bằng 'S' theo sau là số thứ tự.";
            }
        }

        // If all checks are passed, return null indicating no errors
        return null;
    }

    public ArrayList<ChiTietHoaDonDTO> createNewCTHD(String maHoaDon, ArrayList<SachDTO> list) {
        ArrayList<ChiTietHoaDonDTO> listCTHD = new ArrayList<ChiTietHoaDonDTO>();

        for (SachDTO s : list) {
            ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO();
            cthd.setMaHoaDon(maHoaDon);
            cthd.setMaSach(s.getMaSach());
            cthd.setSoLuongMua(s.getSoLuong());
            cthd.setGiaSach(s.getGiaBan());
            listCTHD.add(cthd);
        }

        return listCTHD;
    }

    public ChiTietHoaDonDTO createNewCTHD(String maHoaDon, String maSach, String giaSach, String soLuongMua) {
        ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO();
        cthd.setMaHoaDon(maHoaDon);
        cthd.setMaSach(maSach);
        cthd.setSoLuongMua(Integer.parseInt(soLuongMua));
        cthd.setGiaSach(Double.parseDouble(giaSach));
        return cthd;
    }
}
