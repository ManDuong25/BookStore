/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mansh
 */
public class NhaCungCapDTO {
    private String maNCC;
    private String ten;
    private String diaChi;
    private String sdt;
    private String email;
    private int trangThai;

    public NhaCungCapDTO() {
        this.trangThai = 1;
    }

    public NhaCungCapDTO(String maNCC, String ten, String diaChi, String sdt, String email) {
        this.maNCC = maNCC;
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = 1;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return sdt;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.sdt = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhaXuatBan [maNCC=" + maNCC + ", ten=" + ten + ", diaChi=" + diaChi + ", soDienThoai=" + sdt
                + ", email=" + email + "]";
    }
}
