package dto;

import java.sql.Date;

public class PhieuNhapDTO {

    private String maPhieu;
    private String maNV;
    private Date ngayNhap;
    private String maNCC;
    private double tongTien;
    private int trangThai;

    public PhieuNhapDTO() {
        this.trangThai = 1;
    }

    public PhieuNhapDTO(PhieuNhapDTO pn) {
        this.maPhieu = pn.maPhieu;
        this.maNV = pn.maNV;
        this.maNCC = pn.maNCC;
        this.ngayNhap = pn.ngayNhap;
        this.tongTien = pn.tongTien;
        this.trangThai = 1;
    }

    public PhieuNhapDTO(String maPhieu, String maNV, String maNCC, Date ngayNhap, double tongTien) {
        this.maPhieu = maPhieu;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.trangThai = 1;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
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

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{" + "maPhieu=" + maPhieu + ", maNV=" + maNV + ", ngayNhap=" + ngayNhap + ", maNCC=" + maNCC + ", tongTien=" + tongTien + ", trangThai=" + trangThai + '}';
    }

}
