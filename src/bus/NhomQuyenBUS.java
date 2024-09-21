/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.NhomQuyenDAO;
import dto.ChiTietQuyenDTO;
import dto.NhomQuyenDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mansh
 */
public class NhomQuyenBUS implements BUSInterface<NhomQuyenDTO> {

    private ArrayList<NhomQuyenDTO> nhomQuyenList = new ArrayList<NhomQuyenDTO>();
    private static NhomQuyenBUS instance;

    public static NhomQuyenBUS getInstance() {
        if (instance == null) {
            instance = new NhomQuyenBUS();
        }
        return instance;
    }

    @Override
    public ArrayList<NhomQuyenDTO> getAll() {
        this.nhomQuyenList = NhomQuyenDAO.getInstance().selectAll();
        return this.nhomQuyenList;
    }

    @Override
    public void refreshData() {
        this.nhomQuyenList = NhomQuyenDAO.getInstance().selectAll();
    }

    @Override
    public NhomQuyenDTO getById(NhomQuyenDTO t) {
        return NhomQuyenDAO.getInstance().selectById(t);
    }

    @Override
    public int add(NhomQuyenDTO t) {
        int result = NhomQuyenDAO.getInstance().insert(t);
        return result;
    }

    @Override
    public int update(NhomQuyenDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public NhomQuyenDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.nhomQuyenList.isEmpty()) {
            ArrayList<Integer> nqIndex = new ArrayList<Integer>();
            for (NhomQuyenDTO nq : nhomQuyenList) {
                String[] getIndex = nq.getMaNhomQuyen().split("NQ");
                nqIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(nqIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    public boolean checkMaNhomQuyenExisted(String maNhomQuyen) {
        boolean exist = false;
        for (NhomQuyenDTO nq : this.nhomQuyenList) {
            if (nq.getMaNhomQuyen().equals(maNhomQuyen)) {
                exist = true;
            }
        }
        return exist;
    }

    public String validateData(String tenNhomQuyen) {
        getAll();
        for (NhomQuyenDTO nq : nhomQuyenList) {
            if (nq.getTenNhomQuyen().equals(tenNhomQuyen)) {
                return "Tên nhóm quyền đã tồn tại, vui lòng nhập tên khác!";
            }
        }
        return null;
    }

    @Override
    public int delete(NhomQuyenDTO t) {
        return NhomQuyenDAO.getInstance().delete(t);
    }
    
    
    public NhomQuyenDTO createNewNhomQuyen(String tenNhomQuyen) {
        NhomQuyenDTO nq = new NhomQuyenDTO();
        int lastElementId = NhomQuyenBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            nq.setMaNhomQuyen("NQ" + (lastElementId + 1));
        } else {
            nq.setMaNhomQuyen("NQ" + 1);
        }
        nq.setTenNhomQuyen(tenNhomQuyen);
        return nq;
    }
    
    public boolean checkIfAllowToXemQ(String maNhomQuyen, String maQuyen) {
        ChiTietQuyenDTO ctqTemp = new ChiTietQuyenDTO();
        ctqTemp.setMaNhomQuyen(maNhomQuyen);
        ArrayList<ChiTietQuyenDTO> ctqList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctqTemp);
        
        for (ChiTietQuyenDTO ctq : ctqList) {
            if (ctq.getMaQuyen().equals(maQuyen)) {
                if (ctq.getMaChucNang().equals("CN1")) return true;
            }
        }
        
        return false;
    }
    
    public boolean checkIfAllowToTaoMoiQ(String maNhomQuyen, String maQuyen) {
        ChiTietQuyenDTO ctqTemp = new ChiTietQuyenDTO();
        ctqTemp.setMaNhomQuyen(maNhomQuyen);
        ArrayList<ChiTietQuyenDTO> ctqList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctqTemp);
        
        for (ChiTietQuyenDTO ctq: ctqList) {
            if(ctq.getMaQuyen().equals(maQuyen)) {
                if (ctq.getMaChucNang().equals("CN2")) return true;
            }
        }
        
        return false;
    }
    
    public boolean checkIfAllowToCapNhatQ(String maNhomQuyen, String maQuyen) {
        ChiTietQuyenDTO ctqTemp = new ChiTietQuyenDTO();
        ctqTemp.setMaNhomQuyen(maNhomQuyen);
        ArrayList<ChiTietQuyenDTO> ctqList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctqTemp);
        
        for (ChiTietQuyenDTO ctq: ctqList) {
            if(ctq.getMaQuyen().equals(maQuyen)) {
                if (ctq.getMaChucNang().equals("CN3")) return true;
            }
        }
        
        return false;
    }

    public boolean checkIfAllowToXoaQ(String maNhomQuyen, String maQuyen) {
        ChiTietQuyenDTO ctqTemp = new ChiTietQuyenDTO();
        ctqTemp.setMaNhomQuyen(maNhomQuyen);
        ArrayList<ChiTietQuyenDTO> ctqList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctqTemp);
        
        for (ChiTietQuyenDTO ctq: ctqList) {
            if(ctq.getMaQuyen().equals(maQuyen)) {
                if (ctq.getMaChucNang().equals("CN4")) return true;
            }
        }
        
        return false;
    }
    
    
    public boolean checkIfAllowToExcelQ(String maNhomQuyen, String maQuyen) {
        ChiTietQuyenDTO ctqTemp = new ChiTietQuyenDTO();
        ctqTemp.setMaNhomQuyen(maNhomQuyen);
        ArrayList<ChiTietQuyenDTO> ctqList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctqTemp);
        
        for (ChiTietQuyenDTO ctq: ctqList) {
            if(ctq.getMaQuyen().equals(maQuyen)) {
                if (ctq.getMaChucNang().equals("CN5")) return true;
            }
        }
        
        return false;
    }
    
}
