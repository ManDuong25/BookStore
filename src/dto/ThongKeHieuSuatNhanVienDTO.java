/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mansh
 */
public class ThongKeHieuSuatNhanVienDTO {
    String maNV;
    String tenNV;
    String ngayLap;
    long soLuongHoaDon;
    double tongDoanhThu;
    long soSachBanDuoc;

    public ThongKeHieuSuatNhanVienDTO() {
    }

    public ThongKeHieuSuatNhanVienDTO(String maNV, String tenNV, String ngayLap, long soLuongHoaDon, double tongDoanhThu, long soSachBanDuoc) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngayLap = ngayLap;
        this.soLuongHoaDon = soLuongHoaDon;
        this.tongDoanhThu = tongDoanhThu;
        this.soSachBanDuoc = soSachBanDuoc;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public long getSoLuongHoaDon() {
        return soLuongHoaDon;
    }

    public void setSoLuongHoaDon(long soLuongHoaDon) {
        this.soLuongHoaDon = soLuongHoaDon;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(long tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public long getSoSachBanDuoc() {
        return soSachBanDuoc;
    }

    public void setSoSachBanDuoc(long soSachBanDuoc) {
        this.soSachBanDuoc = soSachBanDuoc;
    }

    @Override
    public String toString() {
        return "ThongKeHieuSuatNhanVienDTO{" + "maNV=" + maNV + ", tenNV=" + tenNV + ", ngayLap=" + ngayLap + ", soLuongHoaDon=" + soLuongHoaDon + ", tongDoanhThu=" + tongDoanhThu + ", soSachBanDuoc=" + soSachBanDuoc + '}';
    }
}
