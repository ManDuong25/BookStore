/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.NhaCungCapDAO;
import dto.NhaCungCapDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class NhaCungCapBUS implements BUSInterface<NhaCungCapDTO> {

    private ArrayList<NhaCungCapDTO> nhaCungCapList = new ArrayList<NhaCungCapDTO>();
    private static NhaCungCapBUS instance;

    public static NhaCungCapBUS getInstance() {
        if (instance == null) {
            instance = new NhaCungCapBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<NhaCungCapDTO> getAll() {
        this.nhaCungCapList = NhaCungCapDAO.getInstance().selectAll();
        return this.nhaCungCapList;
    }

    @Override
    public void refreshData() {
        this.nhaCungCapList = NhaCungCapDAO.getInstance().selectAll();
    }

    @Override
    public NhaCungCapDTO getById(NhaCungCapDTO t) {
        return NhaCungCapDAO.getInstance().selectById(t);
    }

    @Override
    public int add(NhaCungCapDTO t) {
        int result = NhaCungCapDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(NhaCungCapDTO t) {
        int result = NhaCungCapDAO.getInstance().update(t);
        return result;
    }

    @Override
    public int delete(NhaCungCapDTO t) {
        return NhaCungCapDAO.getInstance().delete(t);
    }

    @Override
    public NhaCungCapDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        if (!this.nhaCungCapList.isEmpty()) {
            ArrayList<Integer> nxbIndex = new ArrayList<Integer>();
            for (NhaCungCapDTO ncc : nhaCungCapList) {
                String[] getIndex = ncc.getMaNCC().split("NCC");
                nxbIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(nxbIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public String validateData(String maNCC, String ten, String diaChi, String sdt, String email) {
        if (maNCC == null || maNCC.isEmpty()) {
            return "Mã NCC không được để trống!";
        } else {
            // Kiểm tra xem maNXB có đúng định dạng NXB + số không
            String pattern = "NCC\\d+";
            if (!maNCC.matches(pattern)) {
                return "Mã NCC phải có định dạng 'NCC' tiếp theo là số.";
            }
        }

        if (ten == null || ten.isEmpty()) {
            return "Tên không được để trống";
        }

        if (diaChi == null || diaChi.isEmpty()) {
            return "Địa chỉ không được để trống";
        }

        if (sdt == null || sdt.isEmpty()) {
            return "Số điện thoại không được để trống";
        } else {
            if (!sdt.matches("(03[2-9]|05[689]|07[06-9]|08[1-689]|09[0-46-9])\\d{7}")) {
                return "Số điện thoại không hợp lệ. Số điện thoại di động Việt Nam phải bắt đầu bằng các đầu số như 03x, 05x, 07x, 08x, 09x và có 10 chữ số.";
            }
        }

        if (email == null || email.isEmpty()) {
            return "Email không được để trống";
        } else {
            if (!sdt.matches("^[0-9]{10}$")) {
                return "Số điện thoại phải là 10 chữ số";
            }
        }

        return null;
    }

    public String validateData(String ten, String diaChi, String sdt, String email) {
        if (ten == null || ten.isEmpty()) {
            return "Tên không được để trống";
        }

        if (diaChi == null || diaChi.isEmpty()) {
            return "Địa chỉ không được để trống";
        }

        if (sdt == null || sdt.isEmpty()) {
            return "Số điện thoại không được để trống";
        } else {
            if (!sdt.matches("^[0-9]{10}$")) {
                return "Số điện thoại phải là 10 chữ số";
            }
        }

        if (email == null || email.isEmpty()) {
            return "Email không được để trống";
        } else {
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return "Định dạng email không hợp lệ";
            }
        }

        return null;
    }

    public NhaCungCapDTO createNewNCC(String ten, String diaChi, String sdt, String email) {
        NhaCungCapDTO ncc = new NhaCungCapDTO();
        int lastElementId = NhaCungCapBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            ncc.setMaNCC("NCC" + (lastElementId + 1));
        } else {
            ncc.setMaNCC("NCC" + 1);
        }
        ncc.setTen(ten);
        ncc.setDiaChi(diaChi);
        ncc.setSoDienThoai(sdt);
        ncc.setEmail(email);
        return ncc;
    }

    public NhaCungCapDTO createUpdatedNCC(String maNCC, String ten, String diaChi, String sdt, String email) {
        NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, ten, diaChi, sdt, email);
        return ncc;
    }
}
