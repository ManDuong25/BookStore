/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ChucNangDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import db.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author mansh
 */
public class ChucNangDAO implements DAOInterface<ChucNangDTO> {

    private static ChucNangDAO instance;

    public static ChucNangDAO getInstance() {
        if (instance == null) {
            instance = new ChucNangDAO();
        }
        return instance;
    }

    @Override
    public int insert(ChucNangDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO chucnang (maChucNang, tenChucNang)"
                    + " VALUES (?, ?)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaChucNang());
            pst.setString(2, t.getTenChucNang());

            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(ChucNangDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE chucnang" + " SET" + " tenChucNang = ?"
                    + " WHERE maChucNang = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTenChucNang());
            pst.setString(2, t.getMaChucNang());

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
    public int delete(ChucNangDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "DELETE FROM chucnang WHERE maChucNang = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaChucNang());

            // Bước 3: Thực thi câu lệnh SQL
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
    public ArrayList<ChucNangDTO> selectAll() {
        ArrayList<ChucNangDTO> ketQua = new ArrayList<ChucNangDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chucnang ORDER BY CAST(SUBSTRING(maChucNang, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maChucNang = rs.getString("maChucNang");
                String tenChucNang = rs.getString("tenChucNang");

                ChucNangDTO nq = new ChucNangDTO(maChucNang, tenChucNang);
                ketQua.add(nq);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ChucNangDTO selectById(ChucNangDTO t) {
        ChucNangDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chucnang where maChucNang = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaChucNang());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maChucNang = rs.getString("maChucNang");
                String tenChucNang = rs.getString("tenChucNang");
                int trangThai = rs.getInt("trangThai");
                if (trangThai == 1) {
                    ketQua = new ChucNangDTO(maChucNang, tenChucNang);
                }
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<ChucNangDTO> selectByCondition(String condition) {

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
    public ChucNangDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
