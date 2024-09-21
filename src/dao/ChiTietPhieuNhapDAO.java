/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class ChiTietPhieuNhapDAO implements DAOInterface<ChiTietPhieuNhapDTO> {

    private static ChiTietPhieuNhapDAO instance;

    public static ChiTietPhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietPhieuNhapDAO();
        }
        return instance;
    }

    @Override
    public int insert(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO chitietphieunhap (maPhieu, maSach, giaSach, soLuongNhap)"
                    + " VALUES (?, ?, ?, ?)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaSach());
            pst.setDouble(3, t.getGiaSach());
            pst.setLong(4, t.getSoLuongNhap());
            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE chitietphieunhap" + " SET" + " maSach = ?" + " ,giaSach = ?" + " ,soLuongNhap = ?"
                    + " WHERE maPhieu = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaSach());
            pst.setDouble(2, t.getGiaSach());
            pst.setLong(3, t.getSoLuongNhap());
            pst.setString(4, t.getMaPhieu());

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
    public int delete(ChiTietPhieuNhapDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "DELETE FROM chitietphieunhap WHERE maPhieu = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());

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
    public ArrayList<ChiTietPhieuNhapDTO> selectAll() {
        ArrayList<ChiTietPhieuNhapDTO> ketQua = new ArrayList<ChiTietPhieuNhapDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chitietphieunhap ORDER BY CAST(SUBSTRING(maPhieu, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maSach = rs.getString("maSach");
                double giaSach = rs.getDouble("giaSach");
                int soLuongNhap = rs.getInt("soLuongNhap");

                ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(maPhieu, maSach, giaSach, soLuongNhap);
                ketQua.add(ctpn);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ChiTietPhieuNhapDTO selectById(ChiTietPhieuNhapDTO t) {
        ChiTietPhieuNhapDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chitietphieunhap where maPhieu = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maSach = rs.getString("maSach");
                double giaSach = rs.getDouble("giaSach");
                int soLuongNhap = rs.getInt("soLuongNhap");

                ketQua = new ChiTietPhieuNhapDTO(maPhieu, maSach, giaSach, soLuongNhap);

            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> selectByCondition(String condition) {
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
    public ChiTietPhieuNhapDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
