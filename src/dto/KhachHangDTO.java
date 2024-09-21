package dto;

public class KhachHangDTO {

    private String maKH;
    private String tenKH;
    private String diaChi;
    private String sdt;
    private int trangThai;

    public KhachHangDTO() {
        this.trangThai = 1;
    }

    public KhachHangDTO(String maKH, String tenKH, String diaChi, String sdt, int trangThai) {
        super();
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThai = trangThai;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
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

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhachHangDTO{" + "maKH=" + maKH + ", tenKH=" + tenKH + ", diaChi=" + diaChi + ", sdt=" + sdt + ", trangThai=" + trangThai + '}';
    }

}
