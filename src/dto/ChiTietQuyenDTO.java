/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

public class ChiTietQuyenDTO {

    private String maNhomQuyen;
    private String maChucNang;
    private String maQuyen;

    public ChiTietQuyenDTO() {

    }

    public ChiTietQuyenDTO(String maNhomQuyen, String maChucNang, String maQuyen) {
        this.maNhomQuyen = maNhomQuyen;
        this.maChucNang = maChucNang;
        this.maQuyen = maQuyen;
    }

    public String getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(String maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    @Override
    public String toString() {
        return "ChiTietQuyenDTO{" + "maNhomQuyen=" + maNhomQuyen + ", maChucNang=" + maChucNang + ", maQuyen=" + maQuyen + '}';
    }

}
