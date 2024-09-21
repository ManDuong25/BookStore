/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.TacGiaDAO;
import dto.TacGiaDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class TacGiaBUS implements BUSInterface<TacGiaDTO> {

    private ArrayList<TacGiaDTO> tacGiaList = new ArrayList<TacGiaDTO>();
    private static TacGiaBUS instance;

    public static TacGiaBUS getInstance() {
        if (instance == null) {
            instance = new TacGiaBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<TacGiaDTO> getAll() {
        this.tacGiaList = TacGiaDAO.getInstance().selectAll();
        return this.tacGiaList;
    }

    @Override
    public void refreshData() {
        this.tacGiaList = TacGiaDAO.getInstance().selectAll();
    }

    @Override
    public TacGiaDTO getById(TacGiaDTO t) {
        return TacGiaDAO.getInstance().selectById(t);
    }

    @Override
    public int add(TacGiaDTO t) {
        int result = TacGiaDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(TacGiaDTO t) {
        return TacGiaDAO.getInstance().update(t);
    }

    @Override
    public int delete(TacGiaDTO t) {
        return TacGiaDAO.getInstance().delete(t);
    }

    @Override
    public TacGiaDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        if (!this.tacGiaList.isEmpty()) {
            ArrayList<Integer> tgIndex = new ArrayList<Integer>();
            for (TacGiaDTO tg : tacGiaList) {
                String[] getIndex = tg.getMaTacGia().split("TG");
                tgIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(tgIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public String validateData(String maTG, String ten, String diaChi, String sdt, String email) {
        if (maTG == null || maTG.isEmpty()) {
            return "Mã NXB không được để trống!";
        } else {
            String pattern = "TG\\d+";
            if (!maTG.matches(pattern)) {
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

    public TacGiaDTO createNewTG(String ten, String diaChi, String sdt, String email) {
        TacGiaDTO tg = new TacGiaDTO();
        int lastElementId = TacGiaBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            tg.setMaTacGia("TG" + (lastElementId + 1));
        } else {
            tg.setMaTacGia("TG" + 1);
        }
        tg.setTen(ten);
        tg.setDiaChi(diaChi);
        tg.setSdt(sdt);
        tg.setEmail(email);
        return tg;
    }

    public TacGiaDTO createUpdatedTG(String maTG, String ten, String diaChi, String sdt, String email) {
        TacGiaDTO tg = new TacGiaDTO(maTG, ten, diaChi, sdt, email);
        return tg;
    }
}
