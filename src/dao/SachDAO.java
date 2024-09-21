package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JDBCUtil;
import dto.SachDTO;

public class SachDAO implements DAOInterface<SachDTO> {

    private static SachDAO instance;

    public static SachDAO getInstance() {
        if (instance == null) {
            instance = new SachDAO();
        }
        return instance;
    }

    @Override
    public int insert(SachDTO t) {
        int ketQua = 0;

        try {
            Connection c = JDBCUtil.getConnection();
            String sql = "INSERT INTO sach (maSach, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan, theLoai, trangThai, photo)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1, ?)";
//			System.out.println(sql);
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaSach());
            pst.setString(2, t.getTenSach());
            pst.setLong(3, t.getNamXuatBan());
            pst.setString(4, t.getMaTacGia());
            pst.setString(5, t.getMaNXB());
            pst.setLong(6, t.getSoLuong());
            pst.setDouble(7, t.getGiaNhap());
            pst.setDouble(8, t.getGiaBan());
            pst.setString(9, t.getTheLoai());
            pst.setString(10, t.getPhoto());
            ketQua = pst.executeUpdate();
//			System.out.println("Số dòng bị thay đổi: " + ketQua);

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(SachDTO t) {
        int ketQua = 0;
//                System.out.println(t);
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE sach" + " SET" + " tenSach = ?" + " ,namXuatBan = ?" + " ,maTacGia = ?"
                    + " ,maNhaXuatBan = ?" + " ,soLuong = ?" + " ,giaNhap = ?" + " ,giaBan = ?" + " ,theLoai = ?" + " ,photo = ?"
                    + " WHERE maSach = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getTenSach());
            pst.setInt(2, t.getNamXuatBan());
            pst.setString(3, t.getMaTacGia());
            pst.setString(4, t.getMaNXB());
            pst.setLong(5, t.getSoLuong());
            pst.setDouble(6, t.getGiaNhap());
            pst.setDouble(7, t.getGiaBan());
            pst.setString(8, t.getTheLoai());
            pst.setString(9, t.getPhoto());
            pst.setString(10, t.getMaSach());

            // Bước 3: Thực thi câu lệnh SQL
//			System.out.println(sql);
            ketQua = pst.executeUpdate();

            // Bước 4: Xử lí kết quả trả về
//			System.out.println("Số dòng bị thay đổi: " + ketQua);
            // Bước 5: Ngắt kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public int delete(SachDTO t) {
        int ketQua = 0;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo ra đối tượng statement
            String sql = "UPDATE sach SET sach.trangThai = 0" + " WHERE maSach = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaSach());

            // Bước 3: Thực thi câu lệnh SQL
//			System.out.println(sql);
            ketQua = pst.executeUpdate();

            // Bước 4: Xử lí kết quả trả về
//			System.out.println("Số dòng bị thay đổi: " + ketQua);
            // Bước 5: Ngắt kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public ArrayList<SachDTO> selectAll() {
        ArrayList<SachDTO> ketQua = new ArrayList<SachDTO>();

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * FROM sach ORDER BY CAST(SUBSTRING(maSach, 2) AS UNSIGNED) ASC;";
            PreparedStatement pst = c.prepareStatement(sql);

            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maSach = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getInt("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                int trangThai = rs.getInt("trangThai");
                String photo = rs.getString("photo");

                SachDTO s = new SachDTO(maSach, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan,
                        theLoai, photo);
                if (trangThai == 0) {
                    s.setTrangThai(0);
                }
                ketQua.add(s);
            }

            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public SachDTO selectById(SachDTO t) {
        SachDTO ketQua = null;

        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection c = JDBCUtil.getConnection();

            // Bước 2: Tạo đối tượng Statement
            String sql = "SELECT * from sach where maSach = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, t.getMaSach());
            // Bước 3: Thực thi câu lệnh SQL
            ResultSet rs = pst.executeQuery();

            // Bước 4: Xử lí kết quả trả về
            while (rs.next()) {
                String maSach = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getInt("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                String photo = rs.getString("photo");
                ketQua = new SachDTO(maSach, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan,
                        theLoai, photo);
            }

            // Bước 5: Đóng kết nối đến CSDL
            JDBCUtil.closeConnection(c);
        } catch (SQLException e) {
            // TODO: handle exception
        }

        return ketQua;
    }

    @Override
    public ArrayList<SachDTO> selectByCondition(String condition) {

        return null;
    }

    public ArrayList<SachDTO> filterByGiaNhapAndMaSachASC(Double giaNhapTu, Double giaNhapDen, String maSach) {
        ArrayList<SachDTO> ketQua = new ArrayList<>();
        Connection c = null;

        try {
            c = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sach WHERE giaNhap >= ? AND giaNhap <= ? AND maSach LIKE ? AND trangThai = 1 ORDER BY giaNhap ASC";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setDouble(1, giaNhapTu);
            pst.setDouble(2, giaNhapDen);
            pst.setString(3, "%" + maSach + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSachResult = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getLong("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                String photo = rs.getString("photo");
                int trangThai = rs.getInt("trangThai");

                SachDTO sach = new SachDTO(maSachResult, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan, theLoai, photo);
                if (trangThai == 0) {
                    sach.setTrangThai(0);
                }
                ketQua.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(c);
        }
        return ketQua;
    }
    
    public ArrayList<SachDTO> filterByGiaNhapAndMaSachDESC(Double giaNhapTu, Double giaNhapDen, String maSach) {
        ArrayList<SachDTO> ketQua = new ArrayList<>();
        Connection c = null;

        try {
            c = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sach WHERE giaNhap >= ? AND giaNhap <= ? AND maSach LIKE ? AND trangThai = 1 ORDER BY giaNhap DESC";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setDouble(1, giaNhapTu);
            pst.setDouble(2, giaNhapDen);
            pst.setString(3, "%" + maSach + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSachResult = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getLong("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                String photo = rs.getString("photo");
                int trangThai = rs.getInt("trangThai");

                SachDTO sach = new SachDTO(maSachResult, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan, theLoai, photo);
                if (trangThai == 0) {
                    sach.setTrangThai(0);
                }
                ketQua.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(c);
        }
        return ketQua;
    }

    public ArrayList<SachDTO> filterByGiaBanAndMaSachASC(Double giaTu, Double giaDen, String maSach) {
        ArrayList<SachDTO> ketQua = new ArrayList<>();
        Connection c = null;

        try {
            c = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sach WHERE giaBan >= ? AND giaBan <= ? AND maSach LIKE ? AND trangThai = 1 ORDER BY giaBan ASC";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setDouble(1, giaTu);
            pst.setDouble(2, giaDen);
            pst.setString(3, "%" + maSach + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSachResult = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getLong("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                String photo = rs.getString("photo");
                int trangThai = rs.getInt("trangThai");

                SachDTO sach = new SachDTO(maSachResult, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan, theLoai, photo);
                if (trangThai == 0) {
                    sach.setTrangThai(0);
                }
                ketQua.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(c);
        }
        return ketQua;
    }
    
    public ArrayList<SachDTO> filterByGiaBanAndMaSachDESC(Double giaTu, Double giaDen, String maSach) {
        ArrayList<SachDTO> ketQua = new ArrayList<>();
        Connection c = null;

        try {
            c = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sach WHERE giaBan >= ? AND giaBan <= ? AND maSach LIKE ? AND trangThai = 1 ORDER BY giaBan DESC";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setDouble(1, giaTu);
            pst.setDouble(2, giaDen);
            pst.setString(3, "%" + maSach + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSachResult = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getLong("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                String photo = rs.getString("photo");
                int trangThai = rs.getInt("trangThai");

                SachDTO sach = new SachDTO(maSachResult, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan, theLoai, photo);
                if (trangThai == 0) {
                    sach.setTrangThai(0);
                }
                ketQua.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(c);
        }
        return ketQua;
    }

    public ArrayList<SachDTO> filterByMaNXB(String maNXB) {
        ArrayList<SachDTO> ketQua = new ArrayList<>();
        Connection c = null;

        try {
            c = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sach WHERE maNXB LIKE ? AND trangThai = 1";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, "%" + maNXB + "%"); // Sử dụng phép nối chuỗi để tạo mẫu cho tìm kiếm LIKE
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSach = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                int namXuatBan = rs.getInt("namXuatBan");
                String maTacGia = rs.getString("maTacGia");
                String maNhaXuatBan = rs.getString("maNhaXuatBan");
                long soLuong = rs.getLong("soLuong");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("giaBan");
                String theLoai = rs.getString("theLoai");
                String photo = rs.getString("photo");
                int trangThai = rs.getInt("trangThai");

                SachDTO sach = new SachDTO(maSach, tenSach, namXuatBan, maTacGia, maNhaXuatBan, soLuong, giaNhap, giaBan, theLoai, photo);
                if (trangThai == 0) {
                    sach.setTrangThai(0);
                }
                ketQua.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(c);
        }
        return ketQua;
    }

    @Override
    public SachDTO search() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
