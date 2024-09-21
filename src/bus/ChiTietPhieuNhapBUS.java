/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.ChiTietPhieuNhapDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.SachDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class ChiTietPhieuNhapBUS implements BUSInterface<ChiTietPhieuNhapDTO> {

    private ArrayList<ChiTietPhieuNhapDTO> chiTietPhieuNhapList = new ArrayList<ChiTietPhieuNhapDTO>();
    private static ChiTietPhieuNhapBUS instance;

    public static ChiTietPhieuNhapBUS getInstance() {
        if (instance == null) {
            instance = new ChiTietPhieuNhapBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> getAll() {
        this.chiTietPhieuNhapList = ChiTietPhieuNhapDAO.getInstance().selectAll();
        return this.chiTietPhieuNhapList;
    }

    @Override
    public void refreshData() {
        this.chiTietPhieuNhapList = ChiTietPhieuNhapDAO.getInstance().selectAll();
    }

    @Override
    public ChiTietPhieuNhapDTO getById(ChiTietPhieuNhapDTO t) {
        return ChiTietPhieuNhapDAO.getInstance().selectById(t);
    }

    @Override
    public int add(ChiTietPhieuNhapDTO t) {
        int result = ChiTietPhieuNhapDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(ChiTietPhieuNhapDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(ChiTietPhieuNhapDTO t) {
        int result = ChiTietPhieuNhapDAO.getInstance().delete(t);
        return result;
    }

    @Override
    public ChiTietPhieuNhapDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        if (!this.chiTietPhieuNhapList.isEmpty()) {
            ArrayList<Integer> ctpnIndex = new ArrayList<Integer>();
            for (ChiTietPhieuNhapDTO cthd : chiTietPhieuNhapList) {
                String[] getIndex = cthd.getMaPhieu().split("PN");
                ctpnIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(ctpnIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public String validateData(String maPhieu, ArrayList<SachDTO> list) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            return "Mã phiếu không được để trống.";
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

    public String validateData(String maPhieu, String maSach, String giaSach, String soLuongNhap) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            return "Mã phiếu nhập không được để trống.";
        } else {
            if (!maPhieu.matches("PN\\d+")) {
                return "Mã phiếu nhập phải bắt đầu bằng 'PN' theo sau là số thứ tự.";
            }
        }

        // Check if the quantity is greater than 0
        if (Integer.parseInt(soLuongNhap) <= 0) {
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

    public ArrayList<ChiTietPhieuNhapDTO> createNewCTPN(String maPhieu, ArrayList<SachDTO> list) {
        ArrayList<ChiTietPhieuNhapDTO> listCTPN = new ArrayList<ChiTietPhieuNhapDTO>();

        for (SachDTO s : list) {
            ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
            ctpn.setMaPhieu(maPhieu);
            ctpn.setMaSach(s.getMaSach());
            ctpn.setSoLuongNhap(s.getSoLuong());
            ctpn.setGiaSach(s.getGiaBan());
            listCTPN.add(ctpn);
        }

        return listCTPN;
    }
    
    public ChiTietPhieuNhapDTO createNewCTPN(String maPhieu, String maSach, String giaSach, String soLuongNhap) {
        ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
        ctpn.setMaPhieu(maPhieu);
        ctpn.setMaSach(maSach);
        ctpn.setSoLuongNhap(Integer.parseInt(soLuongNhap));
        ctpn.setGiaSach(Double.parseDouble(giaSach));
        return ctpn;
    }
}
