package dto;

public class NhanVienDTO {

    private String maNV;
    private String tenNV;
    private String diaChi;
    private String email;
    private String soDienThoai;
    private double luong;
    private int trangThai;
    private String maNhomQuyen;

    public NhanVienDTO() {

    }

    public NhanVienDTO(String maNV, String tenNV, String diaChi, String email, String soDienThoai, double luong,
           String maNhomQuyen) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.luong = luong;
        this.trangThai = 1;
        this.maNhomQuyen = maNhomQuyen;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(String maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" + "maNV=" + maNV + ", tenNV=" + tenNV + ", diaChi=" + diaChi + ", email=" + email + ", soDienThoai=" + soDienThoai + ", luong=" + luong + ", trangThai=" + trangThai + ", maNhomQuyen=" + maNhomQuyen + '}';
    }

    

}
