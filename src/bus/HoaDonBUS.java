/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.HoaDonDAO;
import dto.HoaDonDTO;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class HoaDonBUS implements BUSInterface<HoaDonDTO> {

    private ArrayList<HoaDonDTO> hoaDonList = new ArrayList<HoaDonDTO>();
    private static HoaDonBUS instance;

    public static HoaDonBUS getInstance() {
        if (instance == null) {
            instance = new HoaDonBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<HoaDonDTO> getAll() {
        this.hoaDonList = HoaDonDAO.getInstance().selectAll();
        return this.hoaDonList;
    }

    @Override
    public void refreshData() {
        this.hoaDonList = HoaDonDAO.getInstance().selectAll();
    }

    @Override
    public HoaDonDTO getById(HoaDonDTO t) {
        return HoaDonDAO.getInstance().selectById(t);
    }

    @Override
    public int add(HoaDonDTO t) {
        int result = HoaDonDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(HoaDonDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(HoaDonDTO t) {
        return HoaDonDAO.getInstance().delete(t);
    }

    @Override
    public HoaDonDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.hoaDonList.isEmpty()) {
            ArrayList<Integer> hdIndex = new ArrayList<Integer>();
            for (HoaDonDTO hd : hoaDonList) {
                String[] getIndex = hd.getMaHoaDon().split("HD");
                hdIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(hdIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public boolean checkMaHoaDonExist(String maHoaDon) {
        boolean exist = false;
        for (HoaDonDTO hd : this.hoaDonList) {
            if (hd.getMaHoaDon().equals(maHoaDon)) {
                exist = true;
            }
        }
        return exist;
    }

    public String validateData(String maNV, String maKH, String ngayLap, String thongTinUuDai, String tongTien) {
        if (maNV == null || maNV.isEmpty()) {
            return "Mã nhân viên không được rỗng";
        } else {
            if (!maNV.matches("^NV\\d+$")) {
                return "Mã nhân viên không đúng định dạng";
            }
        }
        if (maKH == null || maKH.isEmpty()) {
            return "Mã khách hàng không được rỗng";
        } else {
            if (maKH.equals("Vãng lai")) {
                
            }
            else if (!maKH.matches("^KH\\d+$")) {
                return "Mã khách hàng không đúng định dạng";
            }
        }
        if (ngayLap == null || ngayLap.isEmpty()) {
            return "Ngày lập không được rỗng";
        }
//        if (thongTinUuDai == null || thongTinUuDai.isEmpty()) {
//            return "Thông tin ưu đãi không được rỗng";
//        }
        if (tongTien == null || tongTien.isEmpty()) {
            return "Tổng tiền không được rỗng";
        } else {
            try {
                double tien = Double.parseDouble(tongTien);
                if (tien <= 0) {
                    return "Tổng tiền phải là số dương";
                }
            } catch (NumberFormatException e) {
                return "Tổng tiền không đúng định dạng số";
            }
        }

        // Nếu không có dữ liệu nào rỗng, trả về null để cho biết dữ liệu hợp lệ
        return null;
    }


    public HoaDonDTO createNewHD(String maNV, String maKH, String ngayLap, String thongTinUuDai, String tongTien) {
        HoaDonDTO hd = new HoaDonDTO();
        int lastElementId = HoaDonBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            hd.setMaHoaDon("HD" + (lastElementId + 1));
        } else {
            hd.setMaHoaDon("HD" + 1);
        }
        hd.setMaNV(maNV);
        hd.setMaKH(maKH);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = dateFormat.parse(ngayLap);
            Date sqlDate = new Date(date.getTime());
            hd.setNgayLap(sqlDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi khi có lỗi xảy ra trong quá trình chuyển đổi ngày
        }
        hd.setThongTinUuDai(thongTinUuDai);
        hd.setTongTien(Double.parseDouble(tongTien));
        return hd;
    }

    public HoaDonDTO createNewHD(HoaDonDTO hdOld) {
        HoaDonDTO hd = new HoaDonDTO(hdOld);
        int lastElementId = HoaDonBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            hd.setMaHoaDon("HD" + (lastElementId + 1));
        } else {
            hd.setMaHoaDon("HD" + 1);
        }
        return hd;
    }

    public HoaDonDTO createUpdateHd(String maHoaDon, String maNV, String maKH, String ngayLap, String thongTinUuDai, String tongTien) {
        HoaDonDTO hd = new HoaDonDTO();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaNV(maNV);
        hd.setMaKH(maKH);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = dateFormat.parse(ngayLap);
            Date sqlDate = new Date(date.getTime());
            hd.setNgayLap(sqlDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi khi có lỗi xảy ra trong quá trình chuyển đổi ngày
        }
        hd.setThongTinUuDai(thongTinUuDai);
        hd.setTongTien(Double.parseDouble(tongTien));
        return hd;
    }
}
