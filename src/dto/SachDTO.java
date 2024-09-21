package dto;

public class SachDTO {

    private String maSach;
    private String tenSach;
    private int namXuatBan;
    private String maTacGia;
    private String maNXB;
    private long soLuong;
    private double giaNhap;
    private double giaBan;
    private String theLoai;
    private int trangThai;
    private String photo;

    public SachDTO() {
        this.trangThai = 1;
    }

    public SachDTO(String maSach, String tenSach, int namXuatBan, String maTacGia, String maNXB, long soLuong,
            double giaNhap, double giaBan, String theLoai, String photo) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.namXuatBan = namXuatBan;
        this.maTacGia = maTacGia;
        this.maNXB = maNXB;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.theLoai = theLoai;
        this.trangThai = 1;
        this.photo = photo;
    }
    
    public SachDTO(SachDTO s) {
        this.maSach = s.maSach;
        this.tenSach = s.tenSach;
        this.namXuatBan = s.namXuatBan;
        this.maTacGia = s.maTacGia;
        this.maNXB = s.maNXB;
        this.soLuong = s.soLuong;
        this.giaNhap = s.giaNhap;
        this.giaBan = s.giaBan;
        this.theLoai = s.theLoai;
        this.trangThai = s.trangThai;
        this.photo = s.photo;
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

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(long soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "SachDTO{" + "maSach=" + maSach + ", tenSach=" + tenSach + ", namXuatBan=" + namXuatBan + ", maTacGia=" + maTacGia + ", maNXB=" + maNXB + ", soLuong=" + soLuong + ", giaNhap=" + giaNhap + ", giaBan=" + giaBan + ", theLoai=" + theLoai + ", trangThai=" + trangThai + ", photo=" + photo + '}';
    }

    

}
