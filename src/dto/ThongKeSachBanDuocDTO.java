/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mansh
 */
public class ThongKeSachBanDuocDTO {
    String maSach;
    String tenSach;
    long soLuongBanDuoc;
    double tongTienMangLai;

    public ThongKeSachBanDuocDTO() {
    }

    public ThongKeSachBanDuocDTO(String maSach, String tenSach, long soLuongBanDuoc, double tongTienMangLai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuongBanDuoc = soLuongBanDuoc;
        this.tongTienMangLai = tongTienMangLai;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public long getSoLuongBanDuoc() {
        return soLuongBanDuoc;
    }

    public void setSoLuongBanDuoc(long soLuongBanDuoc) {
        this.soLuongBanDuoc = soLuongBanDuoc;
    }

    public double getTongTienMangLai() {
        return tongTienMangLai;
    }

    public void setTongTienMangLai(double tongTienMangLai) {
        this.tongTienMangLai = tongTienMangLai;
    }

    @Override
    public String toString() {
        return "ThongKeSachBanDuocDTO{" + "maSach=" + maSach + ", tenSach=" + tenSach + ", soLuongBanDuoc=" + soLuongBanDuoc + ", tongTienMangLai=" + tongTienMangLai + '}';
    }
    
    
    
}
