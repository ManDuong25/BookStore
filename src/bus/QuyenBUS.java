/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.QuyenDAO;
import dto.QuyenDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class QuyenBUS implements BUSInterface<QuyenDTO> {

    private ArrayList<QuyenDTO> quyenList = new ArrayList<QuyenDTO>();
    private static QuyenBUS instance;

    public static QuyenBUS getInstance() {
        if (instance == null) {
            instance = new QuyenBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<QuyenDTO> getAll() {
        this.quyenList = QuyenDAO.getInstance().selectAll();
        return this.quyenList;
    }

    @Override
    public void refreshData() {
        this.quyenList = QuyenDAO.getInstance().selectAll();
    }

    @Override
    public QuyenDTO getById(QuyenDTO t) {
        return QuyenDAO.getInstance().selectById(t);
    }

    @Override
    public int add(QuyenDTO t) {
        int result = QuyenDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(QuyenDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public QuyenDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.quyenList.isEmpty()) {
            ArrayList<Integer> quyenIndex = new ArrayList<Integer>();
            for (QuyenDTO quyen: quyenList) {
                String[] getIndex = quyen.getMaQuyen().split("Q");
                quyenIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(quyenIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public boolean checkMaChucNangExisted(String maQuyen) {
        boolean exist = false;
        for (QuyenDTO nq : this.quyenList) {
            if (nq.getMaQuyen().equals(maQuyen)) {
                exist = true;
            }
        }
        return exist;
    }

    public String validateData() {

        return null;
    }

    @Override
    public int delete(QuyenDTO t) {
        return QuyenDAO.getInstance().delete(t);
    }

}
