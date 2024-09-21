/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JDBCUtil;
import dto.ThongKeDoanhThuDTO;
import dto.ThongKeHieuSuatNhanVienDTO;
import dto.ThongKeSachBanDuocDTO;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author mansh
 */
public class ThongKeDAO {

    private static ThongKeDAO instance;

    public static ThongKeDAO getInstance() {
        if (instance == null) {
            instance = new ThongKeDAO();
        }
        return instance;
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeTheoNgay() {
        ArrayList<ThongKeDoanhThuDTO> ketQua = new ArrayList<ThongKeDoanhThuDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT "
                    + "CAST(HoaDon.NgayLap AS DATE) AS NgayBanHang, "
                    + "COUNT(HoaDon.MaHoaDon) AS TongSoHoaDon, "
                    + "SUM(ChiTietHoaDon.soLuongMua) AS TongSoSachBanRa, "
                    + "SUM(ChiTietHoaDon.GiaSach * ChiTietHoaDon.soLuongMua) AS TongDoanhThu "
                    + "FROM "
                    + "HoaDon "
                    + "INNER JOIN "
                    + "ChiTietHoaDon ON HoaDon.MaHoaDon = ChiTietHoaDon.MaHoaDon "
                    + "WHERE "
                    + "HoaDon.TrangThai = 1 "
                    + "GROUP BY "
                    + "CAST(HoaDon.NgayLap AS DATE) "
                    + "ORDER BY "
                    + "NgayBanHang";

            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                Date ngayBanHangDate = rs.getDate("NgayBanHang");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String ngayBanHang = sdf.format(ngayBanHangDate);
                int tongSoHoaDon = rs.getInt("TongSoHoaDon");
                int tongSoSachBanRa = rs.getInt("TongSoSachBanRa");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");

                ThongKeDoanhThuDTO tkdt = new ThongKeDoanhThuDTO(ngayBanHang, tongSoHoaDon, tongSoSachBanRa, tongDoanhThu);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeTheoQuyTrongNam(String year) {
        ArrayList<ThongKeDoanhThuDTO> ketQua = new ArrayList<ThongKeDoanhThuDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    QUARTER(HoaDon.NgayLap) AS Quy,\n"
                    + "    COUNT(HoaDon.MaHoaDon) AS TongSoHoaDon,\n"
                    + "    SUM(ChiTietHoaDon.soLuongMua) AS TongSoSachBanRa,\n"
                    + "    SUM(ChiTietHoaDon.GiaSach * ChiTietHoaDon.soLuongMua) AS TongDoanhThu\n"
                    + "FROM \n"
                    + "    HoaDon\n"
                    + "INNER JOIN \n"
                    + "    ChiTietHoaDon ON HoaDon.MaHoaDon = ChiTietHoaDon.MaHoaDon\n"
                    + "WHERE \n"
                    + "    HoaDon.TrangThai = 1\n"
                    + "    AND YEAR(HoaDon.NgayLap) = ?\n"
                    + "GROUP BY \n"
                    + "    QUARTER(HoaDon.NgayLap)\n"
                    + "ORDER BY \n"
                    + "    Quy;";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, year);
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String ngayBanHang = rs.getString("Quy");
                int tongSoHoaDon = rs.getInt("TongSoHoaDon");
                int tongSoSachBanRa = rs.getInt("TongSoSachBanRa");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");

                ThongKeDoanhThuDTO tkdt = new ThongKeDoanhThuDTO(ngayBanHang, tongSoHoaDon, tongSoSachBanRa, tongDoanhThu);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeTheoNam(String year) {
        ArrayList<ThongKeDoanhThuDTO> ketQua = new ArrayList<ThongKeDoanhThuDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    YEAR(HoaDon.NgayLap) AS Nam,\n"
                    + "    COUNT(HoaDon.MaHoaDon) AS TongSoHoaDon,\n"
                    + "    SUM(ChiTietHoaDon.soLuongMua) AS TongSoSachBanRa,\n"
                    + "    SUM(ChiTietHoaDon.GiaSach * ChiTietHoaDon.soLuongMua) AS TongDoanhThu\n"
                    + "FROM \n"
                    + "    HoaDon\n"
                    + "INNER JOIN \n"
                    + "    ChiTietHoaDon ON HoaDon.MaHoaDon = ChiTietHoaDon.MaHoaDon\n"
                    + "WHERE \n"
                    + "    HoaDon.TrangThai = 1\n"
                    + "    AND YEAR(HoaDon.NgayLap) = ?\n"
                    + "GROUP BY \n"
                    + "    YEAR(HoaDon.NgayLap);";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, year);
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String ngayBanHang = rs.getString("Nam");
                int tongSoHoaDon = rs.getInt("TongSoHoaDon");
                int tongSoSachBanRa = rs.getInt("TongSoSachBanRa");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");

                ThongKeDoanhThuDTO tkdt = new ThongKeDoanhThuDTO(ngayBanHang, tongSoHoaDon, tongSoSachBanRa, tongDoanhThu);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeHieuSuatNhanVienDTO> thongKeHieuSuatNVTheoNgay() {
        ArrayList<ThongKeHieuSuatNhanVienDTO> ketQua = new ArrayList<ThongKeHieuSuatNhanVienDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    nv.MaNV,\n"
                    + "    nv.TenNV,\n"
                    + "    hd.ngayLap,\n"
                    + "    COUNT(hd.MaHoaDon) AS SoLuongHoaDon,\n"
                    + "    SUM(cthd.GiaSach * cthd.soLuongMua) AS TongDoanhThu,\n"
                    + "    SUM(cthd.soLuongMua) AS SoLuongSachBanDuoc\n"
                    + "FROM \n"
                    + "    NhanVien nv\n"
                    + "LEFT JOIN \n"
                    + "    HoaDon hd ON nv.MaNV = hd.MaNV\n"
                    + "LEFT JOIN \n"
                    + "    ChiTietHoaDon cthd ON hd.MaHoaDon = cthd.MaHoaDon\n"
                    + "WHERE \n"
                    + "    hd.TrangThai = 1\n"
                    + "GROUP BY \n"
                    + "    nv.MaNV, nv.TenNV, hd.ngayLap\n"
                    + "ORDER BY \n"
                    + "    nv.MaNV, hd.ngayLap;";

            PreparedStatement pst = c.prepareStatement(sql);
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("MaNV");
                String tenNV = rs.getString("TenNV");

                Date ngayLapDate = rs.getDate("ngayLap");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String ngayLap = sdf.format(ngayLapDate);

                long soLuongHoaDon = rs.getLong("SoLuongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                long soSachBanDuoc = rs.getLong("SoLuongSachBanDuoc");

                ThongKeHieuSuatNhanVienDTO tkdt = new ThongKeHieuSuatNhanVienDTO(maNV, tenNV, ngayLap, soLuongHoaDon, tongDoanhThu, soSachBanDuoc);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeHieuSuatNhanVienDTO> thongKeHieuSuatNVTheoQuyTrongNam(String year, String quy) {
        ArrayList<ThongKeHieuSuatNhanVienDTO> ketQua = new ArrayList<ThongKeHieuSuatNhanVienDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    nv.MaNV,\n"
                    + "    nv.TenNV,\n"
                    + "    QUARTER(hd.ngayLap) AS Quy,\n"
                    + "    COUNT(hd.MaHoaDon) AS SoLuongHoaDon,\n"
                    + "    SUM(cthd.GiaSach * cthd.soLuongMua) AS TongDoanhThu,\n"
                    + "    SUM(cthd.soLuongMua) AS SoLuongSachBanDuoc\n"
                    + "FROM \n"
                    + "    NhanVien nv\n"
                    + "LEFT JOIN \n"
                    + "    HoaDon hd ON nv.MaNV = hd.MaNV\n"
                    + "LEFT JOIN \n"
                    + "    ChiTietHoaDon cthd ON hd.MaHoaDon = cthd.MaHoaDon\n"
                    + "WHERE \n"
                    + "    hd.TrangThai = 1\n"
                    + "    AND YEAR(hd.ngayLap) = ? \n"
                    + "    AND QUARTER(hd.ngayLap) = ? \n"
                    + "GROUP BY \n"
                    + "    nv.MaNV, nv.TenNV, QUARTER(hd.ngayLap)\n"
                    + "ORDER BY \n"
                    + "    nv.MaNV, QUARTER(hd.ngayLap);";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, year);
            pst.setString(2, quy);
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("MaNV");
                String tenNV = rs.getString("TenNV");

                String ngayLap = rs.getString("Quy");

                long soLuongHoaDon = rs.getLong("SoLuongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                long soSachBanDuoc = rs.getLong("SoLuongSachBanDuoc");

                ThongKeHieuSuatNhanVienDTO tkdt = new ThongKeHieuSuatNhanVienDTO(maNV, tenNV, ngayLap, soLuongHoaDon, tongDoanhThu, soSachBanDuoc);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeHieuSuatNhanVienDTO> thongKeHieuSuatNVTheoNam(String year) {
        ArrayList<ThongKeHieuSuatNhanVienDTO> ketQua = new ArrayList<ThongKeHieuSuatNhanVienDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    nv.MaNV,\n"
                    + "    nv.TenNV,\n"
                    + "    YEAR(hd.ngayLap) AS Nam,\n"
                    + "    COUNT(hd.MaHoaDon) AS SoLuongHoaDon,\n"
                    + "    SUM(cthd.GiaSach * cthd.soLuongMua) AS TongDoanhThu,\n"
                    + "    SUM(cthd.soLuongMua) AS SoLuongSachBanDuoc\n"
                    + "FROM \n"
                    + "    NhanVien nv\n"
                    + "LEFT JOIN \n"
                    + "    HoaDon hd ON nv.MaNV = hd.MaNV\n"
                    + "LEFT JOIN \n"
                    + "    ChiTietHoaDon cthd ON hd.MaHoaDon = cthd.MaHoaDon\n"
                    + "WHERE \n"
                    + "    hd.TrangThai = 1\n"
                    + "    AND YEAR(hd.ngayLap) = ?\n"
                    + "GROUP BY \n"
                    + "    nv.MaNV, nv.TenNV, YEAR(hd.ngayLap)\n"
                    + "ORDER BY \n"
                    + "    nv.MaNV, YEAR(hd.ngayLap);";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, year);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("MaNV");
                String tenNV = rs.getString("TenNV");

                String ngayLap = rs.getString("Nam");

                long soLuongHoaDon = rs.getLong("SoLuongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                long soSachBanDuoc = rs.getLong("SoLuongSachBanDuoc");

                ThongKeHieuSuatNhanVienDTO tkdt = new ThongKeHieuSuatNhanVienDTO(maNV, tenNV, ngayLap, soLuongHoaDon, tongDoanhThu, soSachBanDuoc);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeSachBanDuocDTO> thongKeSachBanDuocTheoQuy(String year, String quy) {
        ArrayList<ThongKeSachBanDuocDTO> ketQua = new ArrayList<ThongKeSachBanDuocDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    cthd.MaSach,\n"
                    + "    s.TenSach,\n"
                    + "    SUM(cthd.soLuongMua) AS SoLuongBanDuoc,\n"
                    + "    SUM(cthd.GiaSach * cthd.soLuongMua) AS TongTienMangLai\n"
                    + "FROM \n"
                    + "    ChiTietHoaDon cthd\n"
                    + "INNER JOIN \n"
                    + "    Sach s ON cthd.MaSach = s.MaSach\n"
                    + "INNER JOIN \n"
                    + "    HoaDon hd ON cthd.MaHoaDon = hd.MaHoaDon\n"
                    + "WHERE \n"
                    + "    hd.TrangThai = 1\n"
                    + "    AND YEAR(hd.ngayLap) = ?\n"
                    + "    AND QUARTER(hd.ngayLap) = ?\n"
                    + "GROUP BY \n"
                    + "    cthd.MaSach, s.TenSach, QUARTER(hd.ngayLap);";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, year);
            pst.setString(2, quy);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maSach = rs.getString("MaSach");
                String tenSach = rs.getString("TenSach");

                long soLuongBanDuoc = rs.getLong("SoLuongBanDuoc");

                double tongTienMangLai = rs.getDouble("TongTienMangLai");

                ThongKeSachBanDuocDTO tkdt = new ThongKeSachBanDuocDTO(maSach, tenSach, soLuongBanDuoc, tongTienMangLai);

                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<ThongKeSachBanDuocDTO> thongKeSachBanDuocTheoNam(String year) {
        ArrayList<ThongKeSachBanDuocDTO> ketQua = new ArrayList<ThongKeSachBanDuocDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT \n"
                    + "    cthd.MaSach,\n"
                    + "    s.TenSach,\n"
                    + "    SUM(cthd.soLuongMua) AS SoLuongBanDuoc,\n"
                    + "    SUM(cthd.GiaSach * cthd.soLuongMua) AS TongTienMangLai\n"
                    + "FROM \n"
                    + "    ChiTietHoaDon cthd\n"
                    + "INNER JOIN \n"
                    + "    Sach s ON cthd.MaSach = s.MaSach\n"
                    + "INNER JOIN \n"
                    + "    HoaDon hd ON cthd.MaHoaDon = hd.MaHoaDon\n"
                    + "WHERE \n"
                    + "    hd.TrangThai = 1\n"
                    + "    AND YEAR(hd.ngayLap) = ?\n"
                    + "GROUP BY \n"
                    + "    cthd.MaSach, s.TenSach;";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, year);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maSach = rs.getString("MaSach");
                String tenSach = rs.getString("TenSach");
                long soLuongBanDuoc = rs.getLong("SoLuongBanDuoc");
                double tongTienMangLai = rs.getDouble("TongTienMangLai");

                ThongKeSachBanDuocDTO tkdt = new ThongKeSachBanDuocDTO(maSach, tenSach, soLuongBanDuoc, tongTienMangLai);
                ketQua.add(tkdt);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<String> getAllYearHoaDon() {
        ArrayList<String> ketQua = new ArrayList<String>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng PreparedStatement
            String sql = "SELECT DISTINCT YEAR(NgayLap) AS Nam FROM HoaDon;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lý kết quả trả về
            while (rs.next()) {
                String year = rs.getString("Nam"); // Lấy giá trị từ cột "Nam" trong kết quả ResultSet
                ketQua.add(year); // Thêm giá trị năm vào danh sách kết quả
            }

            // Bước 5: Đóng kết nối
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public static void main(String[] args) {
        ArrayList<ThongKeSachBanDuocDTO> tkdt = ThongKeDAO.getInstance().thongKeSachBanDuocTheoNam("2023");
        for (ThongKeSachBanDuocDTO tkhsnv : tkdt) {
            System.out.println(tkhsnv);
        }
    }
}
