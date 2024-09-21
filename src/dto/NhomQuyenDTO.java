/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mansh
 */
public class NhomQuyenDTO {

    private String maNhomQuyen;
    private String tenNhomQuyen;
    private int trangThai;

    public NhomQuyenDTO() {
        this.trangThai = 1;
    }

    public NhomQuyenDTO(String maNhomQuyen, String tenNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
        this.tenNhomQuyen = tenNhomQuyen;
        this.trangThai = 1;
    }

    public String getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(String maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getTenNhomQuyen() {
        return tenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenNhomQuyen) {
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhomQuyenDTO{" + "maNhomQuyen=" + maNhomQuyen + ", tenNhomQuyen=" + tenNhomQuyen + ", trangThai=" + trangThai + '}';
    }
}
