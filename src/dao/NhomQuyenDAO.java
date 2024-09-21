/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.PreparedStatement;
import java.sql.Connection;
import db.JDBCUtil;
import dto.NhomQuyenDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author mansh
 */
public class NhomQuyenDAO implements DAOInterface<NhomQuyenDTO> {

    private static NhomQuyenDAO instance;

    public static NhomQuyenDAO getInstance() {
        if (instance == null) {
            instance = new NhomQuyenDAO();
        }
        return instance;
    }

    @Override
    public int insert(NhomQuyenDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO nhomquyen (maNhomQuyen, tenNhomQuyen, trangThai)"
                    + " VALUES (?, ?, 1)";
            System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNhomQuyen());
            pst.setString(2, t.getTenNhomQuyen());
            
            ketQua = pst.executeUpdate();
            System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(NhomQuyenDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE nhomquyen" + " SET" + " tenNhomQuyen = ?"
                    + " WHERE maNhomQuyen = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTenNhomQuyen());
            pst.setString(2, t.getMaNhomQuyen());
            

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
    public int delete(NhomQuyenDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE nhomquyen SET nhomquyen.trangThai = 0" + " WHERE maNhomQuyen = ?";
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
    public ArrayList<NhomQuyenDTO> selectAll() {
        ArrayList<NhomQuyenDTO> ketQua = new ArrayList<NhomQuyenDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from nhomquyen ORDER BY CAST(SUBSTRING(maNhomQuyen, 3) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNhomQuyen = rs.getString("maNhomQuyen");
                String tenNhomQuyen = rs.getString("tenNhomQuyen");
                
                int trangThai = rs.getInt("trangThai");

                NhomQuyenDTO nq = new NhomQuyenDTO(maNhomQuyen, tenNhomQuyen);
                if (trangThai == 0) {
                    nq.setTrangThai(0);
                }
                ketQua.add(nq);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public NhomQuyenDTO selectById(NhomQuyenDTO t) {
        NhomQuyenDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from nhomquyen where maNhomQuyen = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaNhomQuyen());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maNhomQuyen = rs.getString("maNhomQuyen");
                String tenNhomQuyen = rs.getString("tenNhomQuyen");
                int trangThai = rs.getInt("trangThai");
                if (trangThai == 1) ketQua = new NhomQuyenDTO(maNhomQuyen, tenNhomQuyen);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<NhomQuyenDTO> selectByCondition(String condition) {

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
    public NhomQuyenDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
