/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.KhachHangDAO;
import dao.NhaXuatBanDAO;
import dto.KhachHangDTO;
import dto.NhaXuatBanDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class NhaXuatBanBUS implements BUSInterface<NhaXuatBanDTO> {

    private ArrayList<NhaXuatBanDTO> nhaXuatBanList = new ArrayList<NhaXuatBanDTO>();
    private static NhaXuatBanBUS instance;

    public static NhaXuatBanBUS getInstance() {
        if (instance == null) {
            instance = new NhaXuatBanBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<NhaXuatBanDTO> getAll() {
        this.nhaXuatBanList = NhaXuatBanDAO.getInstance().selectAll();
        return this.nhaXuatBanList;
    }

    @Override
    public void refreshData() {
        this.nhaXuatBanList = NhaXuatBanDAO.getInstance().selectAll();
    }

    @Override
    public NhaXuatBanDTO getById(NhaXuatBanDTO t) {
        return NhaXuatBanDAO.getInstance().selectById(t);
    }

    @Override
    public int add(NhaXuatBanDTO t) {
        int result = NhaXuatBanDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(NhaXuatBanDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(NhaXuatBanDTO t) {
        return NhaXuatBanDAO.getInstance().delete(t);
    }

    @Override
    public NhaXuatBanDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        if (!this.nhaXuatBanList.isEmpty()) {
            ArrayList<Integer> nxbIndex = new ArrayList<Integer>();
            for (NhaXuatBanDTO nxb : nhaXuatBanList) {
                String[] getIndex = nxb.getMaNXB().split("NXB");
                nxbIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(nxbIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public String validateData(String maNXB, String ten, String diaChi, String sdt, String email) {
        if (maNXB == null || maNXB.isEmpty()) {
            return "Mã NXB không được để trống!";
        } else {
            String pattern = "NXB\\d+";
            if (!maNXB.matches(pattern)) {
                return "Mã NXB phải có định dạng 'NXB' tiếp theo là số.";
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
            // Kiểm tra xem sdt có đúng định dạng số điện thoại có 10 số không

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
            // Kiểm tra xem sdt có đúng định dạng số điện thoại có 10 số không

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

    public NhaXuatBanDTO createNewNXB(String ten, String diaChi, String sdt, String email) {
        NhaXuatBanDTO nxb = new NhaXuatBanDTO();
        int lastElementId = NhaXuatBanBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            nxb.setMaNXB("NXB" + (lastElementId + 1));
        } else {
            nxb.setMaNXB("NXB" + 1);
        }
        nxb.setTen(ten);
        nxb.setDiaChi(diaChi);
        nxb.setSoDienThoai(sdt);
        nxb.setEmail(email);
        return nxb;
    }

    public NhaXuatBanDTO createUpdatedNXB(String maNXB, String ten, String diaChi, String sdt, String email) {
        NhaXuatBanDTO nxb = new NhaXuatBanDTO(maNXB, ten, diaChi, sdt, email);
        return nxb;
    }
}
