/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.PhieuNhapDTO;
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
public class PhieuNhapDAO implements DAOInterface<PhieuNhapDTO> {

    private static PhieuNhapDAO instance;

    public static PhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new PhieuNhapDAO();
        }
        return instance;
    }

    @Override
    public int insert(PhieuNhapDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO phieunhap (maPhieu, maNV, maNCC, ngayNhap, tongTien, trangThai)"
                    + " VALUES (?, ?, ?, ?, ?, 1)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaNV());
            pst.setString(3, t.getMaNCC());
            pst.setDate(4, t.getNgayNhap());
            pst.setDouble(5, t.getTongTien());
            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(PhieuNhapDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE phieunhap" + " SET" + " maNV = ?" + " ,maNCC = " + " ,ngayNhap = ?"
                    + " ,tongTien = ?"
                    + " WHERE maPhieu = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());
            pst.setString(2, t.getMaNCC());
            pst.setDate(3, t.getNgayNhap());
            pst.setDouble(4, t.getTongTien());
            pst.setString(5, t.getMaPhieu());

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
    public int delete(PhieuNhapDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE phieunhap SET phieunhap.trangThai = 0" + " WHERE maPhieu = ?";
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
    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<PhieuNhapDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from phieunhap ORDER BY CAST(SUBSTRING(maPhieu, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maNV = rs.getString("maNV");
                String maNCC = rs.getString("maNCC");
                Date ngayNhap = rs.getDate("ngayNhap");
                double tongTien = rs.getDouble("tongTien");
                int trangThai = rs.getInt("trangThai");

                PhieuNhapDTO pn = new PhieuNhapDTO(maPhieu, maNV, maNCC, ngayNhap, tongTien);
                if (trangThai == 0) {
                    pn.setTrangThai(0);
                }
                ketQua.add(pn);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public PhieuNhapDTO selectById(PhieuNhapDTO t) {
        PhieuNhapDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from phieunhap where maPhieu = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maNV = rs.getString("maNV");
                String maNCC = rs.getString("maNCC");
                Date ngayNhap = rs.getDate("ngayNhap");
                double tongTien = rs.getDouble("tongTien");
                
                ketQua = new PhieuNhapDTO(maPhieu, maNV, maNCC, ngayNhap, tongTien);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<PhieuNhapDTO> selectByCondition(String condition) {

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
    public PhieuNhapDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
