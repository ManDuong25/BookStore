/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.PhieuNhapDAO;
import dto.PhieuNhapDTO;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class PhieuNhapBUS implements BUSInterface<PhieuNhapDTO> {

    private ArrayList<PhieuNhapDTO> phieuNhapList = new ArrayList<PhieuNhapDTO>();
    private static PhieuNhapBUS instance;

    public static PhieuNhapBUS getInstance() {
        if (instance == null) {
            instance = new PhieuNhapBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<PhieuNhapDTO> getAll() {
        this.phieuNhapList = PhieuNhapDAO.getInstance().selectAll();
        return this.phieuNhapList;
    }

    @Override
    public void refreshData() {
        this.phieuNhapList = PhieuNhapDAO.getInstance().selectAll();
    }

    @Override
    public PhieuNhapDTO getById(PhieuNhapDTO t) {
        return PhieuNhapDAO.getInstance().selectById(t);
    }

    @Override
    public int add(PhieuNhapDTO t) {
        int result = PhieuNhapDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(PhieuNhapDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PhieuNhapDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.phieuNhapList.isEmpty()) {
            ArrayList<Integer> pnIndex = new ArrayList<Integer>();
            for (PhieuNhapDTO pn : phieuNhapList) {
                String[] getIndex = pn.getMaPhieu().split("PN");
                pnIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(pnIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }
    
    public boolean checkMaPhieuNhapExist(String maPhieuNhap) {
        boolean exist = false;
        for (PhieuNhapDTO pn : this.phieuNhapList) {
            if (pn.getMaPhieu().equals(maPhieuNhap)) {
                exist = true;
            }
        }
        return exist;
    }

    public String validateData(String maPhieuNhap, String ngayNhap, String maNV, String maNCC, String tongTien) {
        // Kiểm tra mã phiếu nhập
        if (maPhieuNhap == null || maPhieuNhap.isEmpty()) {
            return "Mã phiếu nhập không được để trống";
        } else if (!maPhieuNhap.matches("^PN\\d+$")) {
            return "Mã phiếu nhập không hợp lệ";
        }

        // Kiểm tra ngày nhập
        if (ngayNhap == null || ngayNhap.isEmpty()) {
            return "Ngày nhập không được để trống";
        }

        // Kiểm tra mã nhân viên
        if (maNV == null || maNV.isEmpty()) {
            return "Mã nhân viên không được để trống";
        } else if (!maNV.matches("^NV\\d+$")) {
            return "Mã nhân viên không hợp lệ";
        }
        
        if (maNCC == null || maNCC.isEmpty()) {
            return "Mã nhà cung cấp không được để trống";
        } else if (!maNCC.matches("^NCC\\d+$")) {
            return "Mã nhà cung cấp không hợp lệ";
        }

        // Kiểm tra tổng tiền
        if (tongTien == null || tongTien.isEmpty()) {
            return "Tổng tiền không được để trống";
        } else {
            try {
                double tien = Double.parseDouble(tongTien);
                if (tien <= 0) {
                    return "Tổng tiền phải là một số lớn hơn 0";
                }
            } catch (NumberFormatException e) {
                return "Tổng tiền không hợp lệ";
            }
        }

        // Nếu không có lỗi, trả về null để chỉ ra dữ liệu hợp lệ
        return null;
    }

    public PhieuNhapDTO createNewPN(String maPhieuNhap, String ngayNhap, String maNV, String maNCC, String tongTien) {
        PhieuNhapDTO pn = new PhieuNhapDTO();
        pn.setMaPhieu(maPhieuNhap);
        pn.setMaNV(maNV);
        pn.setMaNCC(maNCC);
        System.out.println(tongTien);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = dateFormat.parse(ngayNhap);
            Date sqlDate = new Date(date.getTime());
            pn.setNgayNhap(sqlDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi khi có lỗi xảy ra trong quá trình chuyển đổi ngày
        }
        pn.setTongTien(Double.parseDouble(tongTien));
        return pn;
    }

    @Override
    public int delete(PhieuNhapDTO t) {
        return PhieuNhapDAO.getInstance().delete(t);
    }

    public PhieuNhapDTO createNewPN(PhieuNhapDTO pnOld) {
        PhieuNhapDTO pn = new PhieuNhapDTO(pnOld);
        int lastElementId = PhieuNhapBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            pn.setMaPhieu("PN" + (lastElementId + 1));
        } else {
            pn.setMaPhieu("PN" + 1);
        }
        return pn;
    }

    public PhieuNhapDTO createUpdatePn(String maPhieuNhap, String ngayNhap, String maNV, String maNCC, String tongTien) {
        PhieuNhapDTO pn = new PhieuNhapDTO();
        pn.setMaPhieu(maPhieuNhap);
        pn.setMaNV(maNV);
        pn.setMaNCC(maNCC);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = dateFormat.parse(ngayNhap);
            Date sqlDate = new Date(date.getTime());
            pn.setNgayNhap(sqlDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi khi có lỗi xảy ra trong quá trình chuyển đổi ngày
        }
        pn.setTongTien(Double.parseDouble(tongTien));
        return pn;
    }
}
