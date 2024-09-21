/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.ChucNangDAO;
import dto.ChucNangDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class ChucNangBUS implements BUSInterface<ChucNangDTO> {

    private ArrayList<ChucNangDTO> chucNangList = new ArrayList<ChucNangDTO>();
    private static ChucNangBUS instance;

    public static ChucNangBUS getInstance() {
        if (instance == null) {
            instance = new ChucNangBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<ChucNangDTO> getAll() {
        this.chucNangList = ChucNangDAO.getInstance().selectAll();
        return this.chucNangList;
    }

    @Override
    public void refreshData() {
        this.chucNangList = ChucNangDAO.getInstance().selectAll();
    }

    @Override
    public ChucNangDTO getById(ChucNangDTO t) {
        return ChucNangDAO.getInstance().selectById(t);
    }

    @Override
    public int add(ChucNangDTO t) {
        int result = ChucNangDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(ChucNangDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChucNangDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.chucNangList.isEmpty()) {
            ArrayList<Integer> cnIndex = new ArrayList<Integer>();
            for (ChucNangDTO cn : chucNangList) {
                String[] getIndex = cn.getMaChucNang().split("CN");
                cnIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(cnIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public boolean checkMaChucNangExisted(String maChucNang) {
        boolean exist = false;
        for (ChucNangDTO nq : this.chucNangList) {
            if (nq.getMaChucNang().equals(maChucNang)) {
                exist = true;
            }
        }
        return exist;
    }

    public String validateData() {

        return null;
    }

    @Override
    public int delete(ChucNangDTO t) {
        return ChucNangDAO.getInstance().delete(t);
    }

}
