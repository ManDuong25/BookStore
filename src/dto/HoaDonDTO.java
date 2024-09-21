package dto;

import java.sql.Date;

public class HoaDonDTO {

    private String maHoaDon;
    private String maNV;
    private String maKH;
    private Date ngayLap;
    private String thongTinUuDai;
    private double tongTien;
    private int trangThai;

    public HoaDonDTO() {
        this.trangThai = 1;
    }

    public HoaDonDTO(String maHoaDon, String maNV, String maKH, Date ngayLap, String thongTinUuDai, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayLap = ngayLap;
        this.thongTinUuDai = thongTinUuDai;
        this.tongTien = tongTien;
        this.trangThai = 1;
    }
    
    public HoaDonDTO(HoaDonDTO hd) {
        this.maHoaDon = hd.maHoaDon;
        this.maNV = hd.maNV;
        this.maKH = hd.maKH;
        this.ngayLap = hd.ngayLap;
        this.thongTinUuDai = hd.thongTinUuDai;
        this.tongTien = hd.tongTien;
        this.trangThai = hd.getTrangThai();
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getThongTinUuDai() {
        return thongTinUuDai;
    }

    public void setThongTinUuDai(String thongTinUuDai) {
        this.thongTinUuDai = thongTinUuDai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDon [maHoaDon=" + maHoaDon + ", maNV=" + maNV + ", maKH=" + maKH + ", ngayLap=" + ngayLap
                + ", thongTinUuDai=" + thongTinUuDai + ", tongTien=" + tongTien + "]";
    }
}
