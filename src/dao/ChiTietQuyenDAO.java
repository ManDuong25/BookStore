/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.JDBCUtil;
import dto.ChiTietQuyenDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class ChiTietQuyenDAO implements DAOInterface<ChiTietQuyenDTO> {

    private static ChiTietQuyenDAO instance;

    public static ChiTietQuyenDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietQuyenDAO();
        }
        return instance;
    }

    @Override
    public int insert(ChiTietQuyenDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO chitietquyen (maNhomQuyen, maChucNang, maQuyen)"
                    + " VALUES (?, ?, ?)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNhomQuyen());
            pst.setString(2, t.getMaChucNang());
            pst.setString(3, t.getMaQuyen());

            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(ChiTietQuyenDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE chitietquyen " + " SET" + " maQuyen = ?" + " maChucNang = ?"
                    + " WHERE maNhomQuyen = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaQuyen());
            pst.setString(2, t.getMaChucNang());
            pst.setString(3, t.getMaNhomQuyen());

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
    public int delete(ChiTietQuyenDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "DELETE FROM chitietquyen WHERE maNhomQuyen = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNhomQuyen());

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
    public ArrayList<ChiTietQuyenDTO> selectAll() {
        ArrayList<ChiTietQuyenDTO> ketQua = new ArrayList<ChiTietQuyenDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chitietquyen ORDER BY CAST(SUBSTRING(maNhomQuyen, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNhomQuyen = rs.getString("maNhomQuyen");
                String maChucNang = rs.getString("maChucNang");
                String maQuyen = rs.getString("maQuyen");

                ChiTietQuyenDTO ctnq = new ChiTietQuyenDTO(maNhomQuyen, maChucNang, maQuyen);
                ketQua.add(ctnq);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ChiTietQuyenDTO selectById(ChiTietQuyenDTO t) {
        ChiTietQuyenDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chitietquyen where maNhomQuyen = ? and maQuyen = ? and maChucNang = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNhomQuyen());
            pst.setString(2, t.getMaQuyen());
            pst.setString(3, t.getMaChucNang());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNhomQuyen = rs.getString("maNhomQuyen");
                String maQuyen = rs.getString("maQuyen");
                String maChucNang = rs.getString("maChucNang");
                ketQua = new ChiTietQuyenDTO(maNhomQuyen, maChucNang, maQuyen);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    public ArrayList<ChiTietQuyenDTO> selectByMaNhomQuyen(ChiTietQuyenDTO t) {
        ArrayList<ChiTietQuyenDTO> ketQua = new ArrayList<ChiTietQuyenDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from chitietquyen where maNhomQuyen = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNhomQuyen());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNhomQuyen = rs.getString("maNhomQuyen");
                String maQuyen = rs.getString("maQuyen");
                String maChucNang = rs.getString("maChucNang");
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maNhomQuyen, maChucNang, maQuyen);
                ketQua.add(ctq);
            }
            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }
    
    @Override
    public ArrayList<ChiTietQuyenDTO> selectByCondition(String condition) {

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
    public ChiTietQuyenDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
