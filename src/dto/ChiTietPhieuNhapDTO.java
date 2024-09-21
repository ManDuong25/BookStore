package dto;

public class ChiTietPhieuNhapDTO {
	private String maPhieu;
	private String maSach;
	private double giaSach;
	private long soLuongNhap;
	
	public ChiTietPhieuNhapDTO() {
		
	}

	public ChiTietPhieuNhapDTO(String maPhieu, String maSach, double giaSach, int soLuongNhap) {
		this.maPhieu = maPhieu;
		this.maSach = maSach;
		this.giaSach = giaSach;
		this.soLuongNhap = soLuongNhap;
	}

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
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

	public long getSoLuongNhap() {
		return soLuongNhap;
	}

	public void setSoLuongNhap(long soLuongNhap) {
		this.soLuongNhap = soLuongNhap;
	}
}
