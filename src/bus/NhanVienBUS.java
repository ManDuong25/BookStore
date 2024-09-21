/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.NhanVienDAO;
import dto.NhanVienDTO;
import dto.NhomQuyenDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 *
 * @author mansh
 */
public class NhanVienBUS implements BUSInterface<NhanVienDTO> {

    private ArrayList<NhanVienDTO> nhanVienList = new ArrayList<NhanVienDTO>();
    private static NhanVienBUS instance;

    public static NhanVienBUS getInstance() {
        if (instance == null) {
            instance = new NhanVienBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<NhanVienDTO> getAll() {
        this.nhanVienList = NhanVienDAO.getInstance().selectAll();
        return this.nhanVienList;
    }

    @Override
    public void refreshData() {
        this.nhanVienList = NhanVienDAO.getInstance().selectAll();
    }

    @Override
    public NhanVienDTO getById(NhanVienDTO t) {
        return NhanVienDAO.getInstance().selectById(t);
    }

    @Override
    public int add(NhanVienDTO t) {
        for (NhanVienDTO nv : nhanVienList) {
            if (nv.getSoDienThoai().equals(t.getSoDienThoai())) {
                return 0;
            }
        }
        int result = NhanVienDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(NhanVienDTO t) {
        return NhanVienDAO.getInstance().update(t);
    }

    @Override
    public int delete(NhanVienDTO t) {
        return NhanVienDAO.getInstance().delete(t);
    }

    @Override
    public NhanVienDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.nhanVienList.isEmpty()) {
            ArrayList<Integer> nvIndex = new ArrayList<Integer>();
            for (NhanVienDTO nv : nhanVienList) {
                String[] getIndex = nv.getMaNV().split("NV");
                nvIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(nvIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public boolean isPhoneNumberExist(String soDienThoai) {
        for (NhanVienDTO nv : nhanVienList) {
            if (nv.getSoDienThoai().equals(soDienThoai)) {
                return true; // Số điện thoại đã tồn tại trong danh sách nhân viên
            }
        }
        return false; // Số điện thoại chưa tồn tại trong danh sách nhân viên
    }
    
    public boolean isNhomQuyenExist(String maNQ) {
        ArrayList<NhomQuyenDTO> listNQ = NhomQuyenBUS.getInstance().getAll();
        for (NhomQuyenDTO nq : listNQ) {
            if (nq.getMaNhomQuyen().equals(maNQ)) {
                return true;
            }
        }
        return false;
    }

    public String validateData(String tenNV, String diaChi, String email, String soDienThoai, String luong, String maNhomQuyen) {
        // Kiểm tra tên nhân viên không được để trống hoặc null
        if (tenNV == null || tenNV.isEmpty()) {
            return "Tên nhân viên không được bỏ trống.";
        }

//        // Kiểm tra địa chỉ không được để trống hoặc null
//        if (diaChi == null || diaChi.isEmpty()) {
//            return "Địa chỉ không được bỏ trống.";
//        }
        // Kiểm tra email không được để trống hoặc null
        if (email == null || email.isEmpty()) {
            return "Email không được bỏ trống.";
        } else {
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$", email)) {
                return "Sai định dạng gmail (VD: mansocho@gmail.com)";
            }
        }

        // Kiểm tra số điện thoại không được để trống hoặc null
        if (soDienThoai == null || soDienThoai.isEmpty()) {
            return "Số điện thoại không được bỏ trống.";
        } else {
            if (!Pattern.matches("^0\\d{9}$", soDienThoai)) {
                return "Số điện thoại không hợp lệ. Vui lòng nhập lại. (VD: 0913885625)";
            }
        }

        // Kiểm tra lương không được để trống hoặc null
        if (luong == null || luong.isEmpty()) {
            return "Lương không được bỏ trống.";
        } else {
            try {
                double luongValue = Double.parseDouble(luong);
                if (luongValue < 0) {
                    return "Lương phải lớn hơn hoặc bằng 0.";
                }
            } catch (NumberFormatException e) {
                return "Lương không hợp lệ. Vui lòng nhập lại.";
            }
        }

        if (maNhomQuyen == null || maNhomQuyen.isEmpty()) {
            return "Nhóm quyền không được bỏ trống.";
        } else {
            if (!maNhomQuyen.matches("NQ\\d+")) {
                return "Mã nhóm quyền phải có định dạng NQ + số";
            }
            if (!isNhomQuyenExist(maNhomQuyen)) {
                return "Mã nhóm quyền: " + maNhomQuyen + "không tồn tại!";
            }
        }

        return null;

    }
    public String validateData(String maNV, String tenNV, String diaChi, String email, String soDienThoai, String luong, String maNhomQuyen) {
        String maNVPattern = "NV\\d+";
        if (maNV == null || maNV.isEmpty()) {
            return "Mã nhân viên không được để trống!";
        } else if (!maNV.matches(maNVPattern)) {
            return "Mã nhân viên phải có định dạng 'NV' tiếp theo là số.";
        }

        // Kiểm tra tên nhân viên không được để trống hoặc null
        if (tenNV == null || tenNV.isEmpty()) {
            return "Tên nhân viên không được bỏ trống.";
        }

//        // Kiểm tra địa chỉ không được để trống hoặc null
//        if (diaChi == null || diaChi.isEmpty()) {
//            return "Địa chỉ không được bỏ trống.";
//        }
        // Kiểm tra email không được để trống hoặc null
        if (email == null || email.isEmpty()) {
            return "Email không được bỏ trống.";
        } else {
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$", email)) {
                return "Sai định dạng gmail (VD: mansocho@gmail.com)";
            }
        }

        // Kiểm tra số điện thoại không được để trống hoặc null
        if (soDienThoai == null || soDienThoai.isEmpty()) {
            return "Số điện thoại không được bỏ trống.";
        } else {
            if (!Pattern.matches("^0\\d{9}$", soDienThoai)) {
                return "Số điện thoại không hợp lệ. Vui lòng nhập lại. (VD: 0913885625)";
            }
        }

        // Kiểm tra lương không được để trống hoặc null
        if (luong == null || luong.isEmpty()) {
            return "Lương không được bỏ trống.";
        } else {
            try {
                double luongValue = Double.parseDouble(luong);
                if (luongValue < 0) {
                    return "Lương phải lớn hơn hoặc bằng 0.";
                }
            } catch (NumberFormatException e) {
                return "Lương không hợp lệ. Vui lòng nhập lại.";
            }
        }

        if (maNhomQuyen == null || maNhomQuyen.isEmpty()) {
            return "Nhóm quyền không được bỏ trống.";
        } else {
            if (!maNhomQuyen.matches("NQ\\d+")) {
                return "Mã nhóm quyền phải có định dạng NQ + số";
            }
            if (!isNhomQuyenExist(maNhomQuyen)) {
                return "Mã nhóm quyền: " + maNhomQuyen + "không tồn tại!";
            }
        }

        return null;

    }

    public NhanVienDTO createNewNV(String tenNV, String diaChi, String email, String soDienThoai, String luong, String maNhomQuyen) {
        NhanVienDTO nv = new NhanVienDTO();
        int lastElementId = NhanVienBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            nv.setMaNV("NV" + (lastElementId + 1));
        } else {
            nv.setMaNV("NV" + 1);
        }
        nv.setTenNV(tenNV);
        nv.setDiaChi(diaChi);
        nv.setEmail(email);
        nv.setSoDienThoai(soDienThoai);
        nv.setLuong(Double.parseDouble(luong));
        nv.setMaNhomQuyen(maNhomQuyen);
        return nv;
    }

    public NhanVienDTO createUpdatedNV(String maNV, String tenNV, String diaChi, String email, String soDienThoai, String luong, String maNhomQuyen) {
        NhanVienDTO nv = new NhanVienDTO(maNV, tenNV, diaChi, email, soDienThoai, Double.parseDouble(luong), maNhomQuyen);
        return nv;
    }
}
