/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.NhaCungCapDTO;
import db.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class NhaCungCapDAO implements DAOInterface<NhaCungCapDTO>{

    private static NhaCungCapDAO instance;

    public static NhaCungCapDAO getInstance() {//
        if (instance == null) {
            instance = new NhaCungCapDAO();
        }
        return instance;
    }

    @Override
    public int insert(NhaCungCapDTO t) {//lay du lieu tu BUS
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO nhacungcap (maNCC, ten, diaChi, sdt, email, trangThai)"
                    + " VALUES (?, ?, ?, ?, ?, 1)";
            System.out.println(sql);
            
            PreparedStatement pst = c.prepareStatement(sql);// 
            pst.setString(1, t.getMaNCC());
            pst.setString(2, t.getTen());
            pst.setString(3, t.getDiaChi());
            pst.setString(4, t.getSoDienThoai());
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
    public int update(NhaCungCapDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE nhacungcap" + " SET" + " ten = ?" + " ,diachi = ?" + " ,sdt = ?"
                    + " ,email = ?"
                    + " WHERE maNCC = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTen());
            pst.setString(2, t.getDiaChi());
            pst.setString(3, t.getSoDienThoai());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getMaNCC());

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
    public int delete(NhaCungCapDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE nhacungcap SET nhacungcap.trangThai = 0" + " WHERE maNCC = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNCC());

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
    public ArrayList<NhaCungCapDTO> selectAll() {
        ArrayList<NhaCungCapDTO> ketQua = new ArrayList<NhaCungCapDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from nhacungcap ORDER BY CAST(SUBSTRING(maNCC, 4) AS UNSIGNED) ASC;";//tanng ma
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String ten = rs.getString("ten");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                int trangThai = rs.getInt("trangThai");

                NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, ten, diaChi, sdt, email);
                if (trangThai == 0) {
                    ncc.setTrangThai(0);
                }
                ketQua.add(ncc);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public NhaCungCapDTO selectById(NhaCungCapDTO t) {
        NhaCungCapDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from nhacungcap where maNCC = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNCC());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String ten = rs.getString("ten");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                ketQua = new NhaCungCapDTO(maNCC, ten, diaChi, sdt, email);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<NhaCungCapDTO> selectByCondition(String condition) {

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
    public NhaCungCapDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
