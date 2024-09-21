/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.HoaDonDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author mansh
 */
public class HoaDonDAO implements DAOInterface<HoaDonDTO> {

    private static HoaDonDAO instance;

    public static HoaDonDAO getInstance() {
        if (instance == null) {
            instance = new HoaDonDAO();
        }
        return instance;
    }

    @Override
    public int insert(HoaDonDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO hoadon (maHoaDon, maNV, maKH, ngayLap, thongTinUuDai, tongTien, trangThai)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
//			System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            pst.setString(2, t.getMaNV());
            pst.setString(3, t.getMaKH());
            pst.setDate(4, t.getNgayLap());
            pst.setString(5, t.getThongTinUuDai());
            pst.setDouble(6, t.getTongTien());
            pst.setInt(7, t.getTrangThai());

            ketQua = pst.executeUpdate();
//			System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(HoaDonDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE hoadon" + " SET" + " maNV = ?" + " ,maKH = ?" + " ,ngayLap = ?"
                    + " ,thongTinUuDai = ?" + " ,tongTien = ?"
                    + " WHERE maHoaDon = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());
            pst.setString(2, t.getMaKH());
            pst.setDate(3, t.getNgayLap());
            pst.setString(4, t.getThongTinUuDai());
            pst.setDouble(5, t.getTongTien());
            pst.setString(6, t.getMaHoaDon());

            // Bước 3: Thực thi câu lệnh SQL
            System.out.println(sql);
            ketQua = pst.executeUpdate();

            // Bước 4: Xử lí kết quả trả về
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            // Bước 5: Ngắt kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int delete(HoaDonDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE hoadon SET hoadon.trangThai = 0" + " WHERE maHoaDon = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());

            // Bước 3: Thực thi câu lệnh SQL
            System.out.println(sql);
            ketQua = pst.executeUpdate();

            // Bước 4: Xử lí kết quả trả về
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            // Bước 5: Ngắt kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }
    
    @Override
    public ArrayList<HoaDonDTO> selectAll() {
        ArrayList<HoaDonDTO> ketQua = new ArrayList<HoaDonDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from hoadon ORDER BY CAST(SUBSTRING(maHoaDon, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maNV = rs.getString("maNV");
                String maKH = rs.getString("maKH");
                Date ngayLap = rs.getDate("ngayLap");
                String thongTinUuDai = rs.getString("thongTinUuDai");
                double tongtien = rs.getDouble("tongTien");
                int trangThai = rs.getInt("trangThai");

                HoaDonDTO hd = new HoaDonDTO(maHoaDon, maNV, maKH, ngayLap, thongTinUuDai, tongtien);
                if (trangThai == 0) {
                    hd.setTrangThai(0);
                }
                ketQua.add(hd);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public HoaDonDTO selectById(HoaDonDTO t) {
        HoaDonDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from hoadon where maHoaDon = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maNV = rs.getString("maNV");
                String maKH = rs.getString("maKH");
                Date ngayLap = rs.getDate("ngayLap");
                String thongTinUuDai = rs.getString("thongTinUuDai");
                double tongtien = rs.getDouble("tongTien");

                ketQua = new HoaDonDTO(maHoaDon, maNV, maKH, ngayLap, thongTinUuDai, tongtien);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<HoaDonDTO> selectByCondition(String condition) {

        return null;
    }

//	public static void main(String[] args) {
//		SachDAO sd = new SachDAO();
////		SachDTO s3 = new SachDTO();
////		s3.setMaSach("3");
////		sd.insert(s3);
//		ArrayList<SachDTO> listS = new ArrayList<SachDTO>();
//		listS = sd.selectAll();
//		for (SachDTO s : listS) {
//			System.out.println(s);
//		}
////		sd.delete(s1);
//	}
    @Override
    public HoaDonDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
