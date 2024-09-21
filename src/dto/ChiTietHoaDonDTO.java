package dto;

public class ChiTietHoaDonDTO {

    private String maHoaDon;
    private String maSach;
    private double giaSach;
    private long soLuongMua;

    public ChiTietHoaDonDTO() {
    }

    public ChiTietHoaDonDTO(String maHoaDon, String maSach, double giaSach, int soLuongMua) {
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.giaSach = giaSach;
        this.soLuongMua = soLuongMua;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public double getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(double giaSach) {
        this.giaSach = giaSach;
    }

    public long getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(long soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDonDTO{" + "maHoaDon=" + maHoaDon + ", maSach=" + maSach + ", giaSach=" + giaSach + ", soLuongMua=" + soLuongMua + '}';
    }


   
}
