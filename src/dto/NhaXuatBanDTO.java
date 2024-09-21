package dto;

public class NhaXuatBanDTO {

    private String maNXB;
    private String ten;
    private String diaChi;
    private String sdt;
    private String email;
    private int trangThai;

    public NhaXuatBanDTO() {
        this.trangThai = 1;
    }

    public NhaXuatBanDTO(String maNXB, String ten, String diaChi, String sdt, String email) {
        this.maNXB = maNXB;
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = 1;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
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
        return "NhaXuatBan [maNXB=" + maNXB + ", ten=" + ten + ", diaChi=" + diaChi + ", soDienThoai=" + sdt
                + ", email=" + email + "]";
    }

}
