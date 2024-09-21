/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.TacGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class TacGiaDAO implements DAOInterface<TacGiaDTO> {

    private static TacGiaDAO instance;

    public static TacGiaDAO getInstance() {
        if (instance == null) {
            instance = new TacGiaDAO();
        }
        return instance;
    }

    @Override
    public int insert(TacGiaDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO tacgia (maTacGia, ten, diaChi, sdt, email, trangThai)"
                    + " VALUES (?, ?, ?, ?, ?, 1)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaTacGia());
            pst.setString(2, t.getTen());
            pst.setString(3, t.getDiaChi());
            pst.setString(4, t.getSdt());
            pst.setString(5, t.getEmail());

            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(TacGiaDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE tacgia" + " SET" + " ten = ?" + " ,diachi = ?" + " ,sdt = ?"
                    + " ,email = ?"
                    + " WHERE maTacGia = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTen());
            pst.setString(2, t.getDiaChi());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getMaTacGia());

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
    public int delete(TacGiaDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE tacgia SET tacgia.trangThai = 0" + " WHERE maTacGia = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaTacGia());

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
    public ArrayList<TacGiaDTO> selectAll() {
        ArrayList<TacGiaDTO> ketQua = new ArrayList<TacGiaDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from tacgia ORDER BY CAST(SUBSTRING(maTacGia, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maTacGia = rs.getString("maTacGia");
                String ten = rs.getString("ten");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                int trangThai = rs.getInt("trangThai");

                TacGiaDTO tg = new TacGiaDTO(maTacGia, ten, diaChi, sdt, email);
                if (trangThai == 0) {
                    tg.setTrangThai(0);
                }
                ketQua.add(tg);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public TacGiaDTO selectById(TacGiaDTO t) {
        TacGiaDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from tacgia where maTacGia = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaTacGia());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maTacGia = rs.getString("maTacGia");
                String ten = rs.getString("ten");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                ketQua = new TacGiaDTO(maTacGia, ten, diaChi, sdt, email);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<TacGiaDTO> selectByCondition(String condition) {

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
    public TacGiaDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
