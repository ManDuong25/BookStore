/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import db.JDBCUtil;
import dto.KhachHangDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class KhachHangDAO implements DAOInterface<KhachHangDTO> {

    private static KhachHangDAO instance;
    
    public static KhachHangDAO getInstance() {
        if (instance == null) {
            instance = new KhachHangDAO();
        }    
        return instance;
    }
        
    
    @Override
    public int insert(KhachHangDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO khachhang (maKH, tenKH, diaChi, sdt, trangThai)"
                    + " VALUES (?, ?, ?, ?, 1)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaKH());
            pst.setString(2, t.getTenKH());
            pst.setString(3, t.getDiaChi());
            pst.setString(4, t.getSdt());

            ketQua = pst.executeUpdate();

            JDBCUtil.closeConnection(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(KhachHangDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "UPDATE khachhang SET tenKH = ?, diaChi = ?, sdt = ? WHERE maKH = ?";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTenKH());
            pst.setString(2, t.getDiaChi());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getMaKH());

            ketQua = pst.executeUpdate();

            JDBCUtil.closeConnection(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(KhachHangDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE khachhang SET khachhang.trangThai = 0" + " WHERE maKH = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaKH());

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
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> ketQua = new ArrayList<KhachHangDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from khachhang ORDER BY CAST(SUBSTRING(maKH, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String tenKH = rs.getString("tenKH");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                int trangThai = rs.getInt("trangThai");

                KhachHangDTO kh = new KhachHangDTO(maKH, tenKH, diaChi, sdt, trangThai);

                ketQua.add(kh);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public KhachHangDTO selectById(KhachHangDTO t) {
        KhachHangDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from khachhang where maKH = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaKH());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String tenKH = rs.getString("tenKH");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                int trangThai = rs.getInt("trangThai");
                ketQua = new KhachHangDTO(maKH, tenKH, diaChi, sdt, trangThai);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return ketQua;
    }

    @Override
    public ArrayList<KhachHangDTO> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        KhachHangDAO khd = new KhachHangDAO();
        KhachHangDTO kh2 = new KhachHangDTO();
        kh2.setMaKH("2");
//		khd.insert(kh2);
//		ArrayList<KhachHangDTO> listKH = new ArrayList<KhachHangDTO>();
//		listKH = khd.selectAll();
//		for (KhachHangDTO kh : listKH) {
//			System.out.println(kh);
//		}
        khd.delete(kh2);
    }

    @Override
    public KhachHangDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
