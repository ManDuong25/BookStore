/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.SachDTO;
import dto.TaiKhoanDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class TaiKhoanDAO implements DAOInterface<TaiKhoanDTO>{

    private static TaiKhoanDAO instance;

    public static TaiKhoanDAO getInstance() {
        if (instance == null) {
            instance = new TaiKhoanDAO();
        }
        return instance;
    }

    @Override
    public int insert(TaiKhoanDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO taikhoan (maNV, matKhau, email, trangThai)"
                    + " VALUES (?, ?, ?, 1)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());
            pst.setString(2, t.getMatKhau());
            pst.setString(3, t.getEmail());
            
            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(TaiKhoanDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE taikhoan" + " SET" + " matKhau = ?" + " ,email = ?"
                    + " WHERE maNV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMatKhau());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getMaNV());
            

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
    public int delete(TaiKhoanDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE taikhoan SET taikhoan.trangThai = 0" + " WHERE maNV = ?";
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
    public ArrayList<TaiKhoanDTO> selectAll() {
        ArrayList<TaiKhoanDTO> ketQua = new ArrayList<TaiKhoanDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from taikhoan ORDER BY CAST(SUBSTRING(maNV, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String matKhau = rs.getString("matKhau");
                String email = rs.getString("email");
                int trangThai = rs.getInt("trangThai");
                

                TaiKhoanDTO tk = new TaiKhoanDTO(maNV, matKhau, email);
                if (trangThai == 0) {
                    tk.setTrangThai(0);
                }
                ketQua.add(tk);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public TaiKhoanDTO selectById(TaiKhoanDTO t) {
        TaiKhoanDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from taikhoan where maNV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNV());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String matKhau = rs.getString("matKhau");
                String email = rs.getString("email");
                
                ketQua = new TaiKhoanDTO(maNV, matKhau, email);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<TaiKhoanDTO> selectByCondition(String condition) {

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
    public TaiKhoanDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
