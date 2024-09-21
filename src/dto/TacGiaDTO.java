/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mansh
 */
public class TacGiaDTO {
    private String maTacGia;
    private String ten;
    private String diaChi;
    private String sdt;
    private String email;
    private int trangThai;

    public TacGiaDTO() {
        this.trangThai = 1;
    }

    public TacGiaDTO(String maTacGia, String ten, String diaChi, String sdt, String email) {
        this.maTacGia = maTacGia;
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = 1;
    }

    public String getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        this.maTacGia = maTacGia;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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
        return "TacGiaDTO{" + "maTacGia=" + maTacGia + ", ten=" + ten + ", diaChi=" + diaChi + ", sdt=" + sdt + ", email=" + email + ", trangThai=" + trangThai + '}';
    }
}
