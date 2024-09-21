/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.TaiKhoanDAO;
import dto.TaiKhoanDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class TaiKhoanBUS implements BUSInterface<TaiKhoanDTO> {

    private ArrayList<TaiKhoanDTO> taiKhoanList = new ArrayList<TaiKhoanDTO>();
    private static TaiKhoanBUS instance;

    public static TaiKhoanBUS getInstance() {
        if (instance == null) {
            instance = new TaiKhoanBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<TaiKhoanDTO> getAll() {
        this.taiKhoanList = TaiKhoanDAO.getInstance().selectAll();
        return this.taiKhoanList;
    }

    @Override
    public void refreshData() {
        this.taiKhoanList = TaiKhoanDAO.getInstance().selectAll();
    }

    @Override
    public TaiKhoanDTO getById(TaiKhoanDTO t) {
        return TaiKhoanDAO.getInstance().selectById(t);
    }

    @Override
    public int add(TaiKhoanDTO t) {
        int result = TaiKhoanDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(TaiKhoanDTO t) {
        return TaiKhoanDAO.getInstance().update(t);
    }

    @Override
    public int delete(TaiKhoanDTO t) {
        return TaiKhoanDAO.getInstance().delete(t);
    }

    @Override
    public TaiKhoanDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        if (!this.taiKhoanList.isEmpty()) {
            ArrayList<Integer> tkIndex = new ArrayList<Integer>();
            for (TaiKhoanDTO tk : taiKhoanList) {
                String[] getIndex = tk.getMaNV().split("S");
                tkIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(tkIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public String validateData(String maNV, String matKhau, String email) {
        if (maNV == null || maNV.isEmpty()) {
            return "Mã NV không được để trống!";
        } else {
            String pattern = "NV\\d+";
            if (!maNV.matches(pattern)) {
                return "Mã NV phải có định dạng 'NV' tiếp theo là số.";
            }
        }

        if (matKhau == null || matKhau.isEmpty()) {
            return "Mật khẩu không được để trống";
        } else if (matKhau.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 kí tự";
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

    public String validateData(String matKhau, String email) {
        if (matKhau == null || matKhau.isEmpty()) {
            return "Mật khẩu không được để trống";
        } else if (matKhau.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 kí tự";
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

    public TaiKhoanDTO createNewTK(String maNV, String matKhau, String email) {
        TaiKhoanDTO tk = new TaiKhoanDTO();
        tk.setMaNV(maNV);
        tk.setMatKhau(matKhau);
        tk.setEmail(email);
        return tk;
    }

    public TaiKhoanDTO createUpdatedTK(String maNV, String matKhau, String email) {
        TaiKhoanDTO nxb = new TaiKhoanDTO(maNV, matKhau, email);
        return nxb;
    }
}
