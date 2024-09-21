/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.ChiTietHoaDonDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class ChiTietHoaDonDAO implements DAOInterface<ChiTietHoaDonDTO> {

    private static ChiTietHoaDonDAO instance;

    public static ChiTietHoaDonDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietHoaDonDAO();
        }
        return instance;
    }

    @Override
    public int insert(ChiTietHoaDonDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO chitiethoadon (maHoaDon, maSach, giaSach, soLuongMua)"
                    + " VALUES (?, ?, ?, ?)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            pst.setString(2, t.getMaSach());
            pst.setDouble(3, t.getGiaSach());
            pst.setLong(4, t.getSoLuongMua());
            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(ChiTietHoaDonDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE chitiethoadon" + " SET" + " maSach = ?" + " ,giaSach = ?" + " ,soLuongMua = ?"
                    + " WHERE maHoaDon = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaSach());
            pst.setDouble(2, t.getGiaSach());
            pst.setLong(3, t.getSoLuongMua());
            pst.setString(4, t.getMaHoaDon());

            // Bước 3: Thực thi câu lệnh SQL
            System.out.println(sql);
            ketQua = pst.executeUpdate();

            // Bước 4: Xử lí kết quả trả về
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            // Bước 5: Ngắt kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public int delete(ChiTietHoaDonDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "DELETE FROM chitiethoadon WHERE maHoaDon = ?";
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
    public ArrayList<ChiTietHoaDonDTO> selectAll() {
        ArrayList<ChiTietHoaDonDTO> ketQua = new ArrayList<ChiTietHoaDonDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chitiethoadon ORDER BY CAST(SUBSTRING(maHoaDon, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maSach = rs.getString("maSach");
                double giaSach = rs.getDouble("giaSach");
                int soLuongMua = rs.getInt("soLuongMua");

                ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(maHoaDon, maSach, giaSach, soLuongMua);
                ketQua.add(cthd);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ChiTietHoaDonDTO selectById(ChiTietHoaDonDTO t) {
        ChiTietHoaDonDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "DELETE FROM chitiethoadon WHERE maHoaDon = ? AND maSach = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaHoaDon());
            pst.setString(2, t.getMaSach());
            
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maSach = rs.getString("maSach");
                double giaSach = rs.getDouble("giaSach");
                int soLuongMua = rs.getInt("soLuongMua");

                ketQua = new ChiTietHoaDonDTO(maHoaDon, maSach, giaSach, soLuongMua);

            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<ChiTietHoaDonDTO> selectByCondition(String condition) {
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
    public ChiTietHoaDonDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
