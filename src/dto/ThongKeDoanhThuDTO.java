/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author mansh
 */
public class ThongKeDoanhThuDTO {
    String ngayBanHang;
    long tongSoHoaDon;
    long tongSoSachBanRa;
    double tongDoanhThu;
    
    public void ThongKeDoanhThuDTO() {
        
    }

    public ThongKeDoanhThuDTO(String ngayBanHang, long tongSoHoaDon, long tongSoSachBanRa, double tongDoanhThu) {
        this.ngayBanHang = ngayBanHang;
        this.tongSoHoaDon = tongSoHoaDon;
        this.tongSoSachBanRa = tongSoSachBanRa;
        this.tongDoanhThu = tongDoanhThu;
    }

    public String getNgayBanHang() {
        return ngayBanHang;
    }

    public void setNgayBanHang(String ngayBanHang) {
        this.ngayBanHang = ngayBanHang;
    }

    public long getTongSoHoaDon() {
        return tongSoHoaDon;
    }

    public void setTongSoHoaDon(long tongSoHoaDon) {
        this.tongSoHoaDon = tongSoHoaDon;
    }

    public long getTongSoSachBanRa() {
        return tongSoSachBanRa;
    }

    public void setTongSoSachBanRa(long tongSoSachBanRa) {
        this.tongSoSachBanRa = tongSoSachBanRa;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(long tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    @Override
    public String toString() {
        return "ThongKeDoanhThuDTO{" + "ngayBanHang=" + ngayBanHang + ", tongSoHoaDon=" + tongSoHoaDon + ", tongSoSachBanRa=" + tongSoSachBanRa + ", tongDoanhThu=" + tongDoanhThu + '}';
    }
    
    
}
