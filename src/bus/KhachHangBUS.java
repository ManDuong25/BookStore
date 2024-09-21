    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.KhachHangDAO;
import dto.KhachHangDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class KhachHangBUS implements BUSInterface<KhachHangDTO> {

    private ArrayList<KhachHangDTO> khachHangList = new ArrayList<KhachHangDTO>();
    private static KhachHangBUS instance;

    public static KhachHangBUS getInstance() {
        if (instance == null) {
            instance = new KhachHangBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<KhachHangDTO> getAll() {
        this.khachHangList = KhachHangDAO.getInstance().selectAll();
        return this.khachHangList;
    }

    @Override
    public void refreshData() {
        this.khachHangList = KhachHangDAO.getInstance().selectAll();
    }

    @Override
    public KhachHangDTO getById(KhachHangDTO t) {
        return KhachHangDAO.getInstance().selectById(t);
    }

    @Override
    public int add(KhachHangDTO t) {
        int result = KhachHangDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(KhachHangDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(KhachHangDTO t) {
        return KhachHangDAO.getInstance().delete(t);
    }

    @Override
    public KhachHangDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.khachHangList.isEmpty()) {
            ArrayList<Integer> khIndex = new ArrayList<Integer>();
            for (KhachHangDTO kh : khachHangList) {
                String[] getIndex = kh.getMaKH().split("KH");
                khIndex.add(Integer.valueOf(getIndex[1]));
            }
            int lastIndex = Collections.max(khIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public KhachHangDTO getKhachHangByPhone(String phone) {
        if (!this.khachHangList.isEmpty()) {
            for (KhachHangDTO kh : khachHangList) {
                if (kh.getSdt().equals(phone)) {
                    return kh;
                }
            }
        }
        return null;
    }

    public String validateData(String tenKH, String diaChi, String sdt) {
        if (tenKH.isEmpty() || tenKH == null) {
            return "Tên khách hàng không được bỏ trống!";
        }

        if (sdt == null || sdt.isEmpty()) {
            return "Số điện thoại không được bỏ trống!";
        } else {
            if (!sdt.matches("^[0-9]{10}$")) {
                return "Số điện thoại phải là 10 chữ số";
            }
        }
        return null;
    }

    public String validateData(String maKH, String tenKH, String diaChi, String sdt) {
        if (maKH == null || maKH.isEmpty()) {
            return "Mã KH không được để trống!";
        } else {
            String pattern = "KH\\d+";
            if (!maKH.matches(pattern)) {
                return "Mã KH phải có định dạng 'NXB' tiếp theo là số.";
            }
        }

        if (tenKH.isEmpty() || tenKH == null) {
            return "Tên khách hàng không được bỏ trống!";
        }

        if (sdt == null || sdt.isEmpty()) {
            return "Số điện thoại không được bỏ trống!";
        } else {
            if (!sdt.matches("^[0-9]{10}$")) {
                return "Số điện thoại phải là 10 chữ số";
            }
        }
        return null;
    }

    public KhachHangDTO createNewKH(String tenKH, String diaChi, String sdt) {
        KhachHangDTO kh = new KhachHangDTO();
        int lastElementId = KhachHangBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            kh.setMaKH("KH" + (lastElementId + 1));
        } else {
            kh.setMaKH("KH" + 1);
        }
        kh.setTenKH(tenKH);
        kh.setDiaChi(diaChi);
        kh.setSdt(sdt);
        return kh;
    }
}
