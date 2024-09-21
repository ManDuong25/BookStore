/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.ChiTietQuyenDAO;
import dto.ChiTietQuyenDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class ChiTietQuyenBUS implements BUSInterface<ChiTietQuyenDTO>{

    private ArrayList<ChiTietQuyenDTO> chiTietQuyenList = new ArrayList<ChiTietQuyenDTO>();
    private static ChiTietQuyenBUS instance;

    public static ChiTietQuyenBUS getInstance() {
        if (instance == null) {
            instance = new ChiTietQuyenBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<ChiTietQuyenDTO> getAll() {
        this.chiTietQuyenList = ChiTietQuyenDAO.getInstance().selectAll();
        return this.chiTietQuyenList;
    }

    @Override
    public void refreshData() {
        this.chiTietQuyenList = ChiTietQuyenDAO.getInstance().selectAll();
    }

    @Override
    public ChiTietQuyenDTO getById(ChiTietQuyenDTO t) {
        return ChiTietQuyenDAO.getInstance().selectById(t);
    }

    @Override
    public int add(ChiTietQuyenDTO t) {
        int result = ChiTietQuyenDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(ChiTietQuyenDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChiTietQuyenDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.chiTietQuyenList.isEmpty()) {
            ArrayList<Integer> ctqIndex = new ArrayList<Integer>();
            for (ChiTietQuyenDTO ctq : chiTietQuyenList) {
                String[] getIndex = ctq.getMaNhomQuyen().split("NQ");
                ctqIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(ctqIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public String validateData(String tenNhomQuyen) {
        
        return null;
    }

    @Override
    public int delete(ChiTietQuyenDTO t) {
        return ChiTietQuyenDAO.getInstance().delete(t);
    }
    
    public ArrayList<ChiTietQuyenDTO> getByMaNhomQuyen(ChiTietQuyenDTO t) {
        return ChiTietQuyenDAO.getInstance().selectByMaNhomQuyen(t);
    }

    public ChiTietQuyenDTO createNewChiTietQuyen(String maNhomQuyen, String maChucNang, String maQuyen) {
        return new ChiTietQuyenDTO(maNhomQuyen, maChucNang, maQuyen);
    }
}
