/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.NhanVienDTO;
import dto.SachDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class NhanVienDAO implements DAOInterface<NhanVienDTO>{

    private static NhanVienDAO instance;

    public static NhanVienDAO getInstance() {
        if (instance == null) {
            instance = new NhanVienDAO();
        }
        return instance;
    }

    @Override
    public int insert(NhanVienDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO nhanvien (maNV, tenNV, diaChi, email, soDienThoai, luong, trangThai, maNhomQuyen)"
                    + " VALUES (?, ?, ?, ?, ?, ?, 1, ?)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());
            pst.setString(2, t.getTenNV());
            pst.setString(3, t.getDiaChi());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getSoDienThoai());
            pst.setDouble(6, t.getLuong());
            pst.setString(7, t.getMaNhomQuyen());
            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(NhanVienDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE nhanvien" + " SET" + " tenNV = ?" + " ,diaChi = ?" + " ,email = ?"
                    + " ,soDienThoai = ?" + " ,luong = ?" + " ,maNhomQuyen = ?" 
                    + " WHERE maNV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTenNV());
            pst.setString(2, t.getDiaChi());
            pst.setString(3, t.getEmail());
            pst.setString(4, t.getSoDienThoai());
            pst.setDouble(5, t.getLuong());
            pst.setString(6, t.getMaNhomQuyen());
            pst.setString(7, t.getMaNV());

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
    public int delete(NhanVienDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE nhanvien SET nhanvien.trangThai = 0" + " WHERE maNV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());

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
    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> ketQua = new ArrayList<NhanVienDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from nhanvien ORDER BY CAST(SUBSTRING(maNV, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String diaChi = rs.getString("diaChi");
                String email = rs.getString("email");
                String soDienThoai = rs.getString("soDienThoai");
                double luong = rs.getDouble("luong");
                String maNhomQuyen = rs.getString("maNhomQuyen");
                int trangThai = rs.getInt("trangThai");

                NhanVienDTO nv = new NhanVienDTO(maNV, tenNV, diaChi, email, soDienThoai, luong, maNhomQuyen);
                if (trangThai == 0) {
                    nv.setTrangThai(0);
                }
                ketQua.add(nv);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public NhanVienDTO selectById(NhanVienDTO t) {
        NhanVienDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from nhanvien where maNV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String diaChi = rs.getString("diaChi");
                String email = rs.getString("email");
                String soDienThoai = rs.getString("soDienThoai");
                double luong = rs.getDouble("luong");
                String maNhomQuyen = rs.getString("maNhomQuyen");
                int trangThai = rs.getInt("trangThai");
                if (trangThai == 1) ketQua = new NhanVienDTO(maNV, tenNV, diaChi, email, soDienThoai, luong, maNhomQuyen);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<NhanVienDTO> selectByCondition(String condition) {

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
    public NhanVienDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
