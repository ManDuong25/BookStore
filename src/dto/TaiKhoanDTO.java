package dto;

public class TaiKhoanDTO {

    private String maNV;
    private String matKhau;
    private String email;
    private int trangThai;

    public TaiKhoanDTO() {
        this.trangThai = 1;
    }

    public TaiKhoanDTO(String maNV, String matKhau, String email) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.email = email;
        this.trangThai = 1;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
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
        return "TaiKhoan [maNV=" + maNV + ", matKhau=" + matKhau + ", email=" + email + ", trangThai=" + trangThai
                + "]";
    }

}
