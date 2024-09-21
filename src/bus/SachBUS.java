package bus;

import java.util.ArrayList;

import dao.SachDAO;
import dto.SachDTO;
import java.util.Collections;
import javax.swing.JOptionPane;

public class SachBUS implements BUSInterface<SachDTO> {

    private ArrayList<SachDTO> sachList = new ArrayList<SachDTO>();
    private static SachBUS instance;

    public static SachBUS getInstance() {
        if (instance == null) {
            instance = new SachBUS();
        }
        return instance;
    }

    public ArrayList<SachDTO> getAll() {
        this.sachList = SachDAO.getInstance().selectAll();
        return sachList;
    }

    public void refreshData() {
        this.sachList = SachDAO.getInstance().selectAll();
    }

    public int add(SachDTO s) {
        int result = SachDAO.getInstance().insert(s);
        return result;
    }

    @Override
    public int update(SachDTO t) {
        return SachDAO.getInstance().update(t);
    }

    @Override
    public int delete(SachDTO t) {
        return SachDAO.getInstance().delete(t);
    }

    public String validateData(String tenSach, String namXuatBan, String maTacGia, String maNXB, String soLuong, String giaNhap, String giaBan, String theLoai, String photo) {
        if (tenSach.isEmpty() || tenSach == null) {
            return "Tên sách không được để trống!";
        } else {
            if (tenSach.matches(".*[àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ].*")) {
                return "Tên sách không được chứa ký tự tiếng Việt!";
            }
        }
        if (namXuatBan.isEmpty() || namXuatBan == null) {
            return "Năm xuất bản không được để trống";
        } else {
            try {
                int namInt = Integer.parseInt(namXuatBan);
                if (namInt < 1000 || namInt > 9999) {
                    return "Năm xuất bản phải là số nguyên có 4 chữ số";
                }
            } catch (NumberFormatException e) {
                return "Năm xuất bản phải là số nguyên.";
            }
        }

        if (maTacGia.isEmpty() || maTacGia == null) {
            return "Mã tác giả không được để trống";
        } else {
            String pattern = "TG\\d+"; // \d là ký hiệu cho chữ số, + nghĩa là một hoặc nhiều chữ số
            if (!maTacGia.matches(pattern)) {
                return "Mã tác giả phải theo định dạng 'TG' tiếp theo là số.";
            }
        }

        if (maNXB.isEmpty() || maNXB == null) {
            return "Mã NXB không được để trống";
        } else {
            String pattern = "NXB\\d+";
            if (!maNXB.matches(pattern)) {
                return "Mã tác giả phải theo định dạng 'TG' tiếp theo là số.";
            }
        }

        if (soLuong.isEmpty() || soLuong == null) {
            return "Số lượng không được để trống";
        } else {
            if (Integer.parseInt(soLuong) < 0) {
                return "Số lượng phải lớn hơn hoặc bằng 0";
            }
        }

        if (giaNhap.isEmpty() || giaNhap == null) {
            return "Giá nhập không được để trống";
        }
        if (giaBan.isEmpty() || giaBan == null) {
            return "Giá bán không được bé hơn 0";
        }
        if (theLoai.isEmpty() || theLoai == null) {
            return "Thể loại không được để trống";
        }
        return null;
    }

    public String validateData(String maSach, String tenSach, String namXuatBan, String maTacGia, String maNXB, String soLuong, String giaNhap, String giaBan, String theLoai, String photo) {
        String maSachPattern = "S\\d+";
        if (maSach == null || maSach.isEmpty()) {
            return "Mã sách không được để trống!";
        } else if (!maSach.matches(maSachPattern)) {
            return "Mã sách phải có định dạng 'S' tiếp theo là số.";
        }

        if (tenSach.isEmpty() || tenSach == null) {
            return "Tên sách không được để trống!";
        } else {
            if (tenSach.matches(".*[àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ].*")) {
                return "Tên sách không được chứa ký tự tiếng Việt!";
            }
        }
        if (namXuatBan.isEmpty() || namXuatBan == null) {
            return "Năm xuất bản không được để trống";
        } else {
            try {
                int namInt = Integer.parseInt(namXuatBan);
                if (namInt < 1000 || namInt > 9999) {
                    return "Năm xuất bản phải là số nguyên có 4 chữ số";
                }
            } catch (NumberFormatException e) {
                return "Năm xuất bản phải là số nguyên.";
            }
        }

        if (maTacGia.isEmpty() || maTacGia == null) {
            return "Mã tác giả không được để trống";
        } else {
            String pattern = "TG\\d+"; // \d là ký hiệu cho chữ số, + nghĩa là một hoặc nhiều chữ số
            if (!maTacGia.matches(pattern)) {
                return "Mã tác giả phải theo định dạng 'TG' tiếp theo là số.";
            }
        }

        if (maNXB.isEmpty() || maNXB == null) {
            return "Mã NXB không được để trống";
        } else {
            String pattern = "NXB\\d+";
            if (!maNXB.matches(pattern)) {
                return "Mã tác giả phải theo định dạng 'TG' tiếp theo là số.";
            }
        }

        if (soLuong.isEmpty() || soLuong == null) {
            return "Số lượng không được để trống";
        }

        if (giaNhap.isEmpty() || giaNhap == null) {
            return "Giá nhập không được để trống";
        }
        if (giaBan.isEmpty() || giaBan == null) {
            return "Giá bán không được bé hơn 0";
        }
        if (theLoai.isEmpty() || theLoai == null) {
            return "Thể loại không được để trống";
        }
        return null;
    }

    public SachDTO createNewSach(String tenSach, String namXuatBan, String maTacGia, String maNXB, String soLuong, String giaNhap, String giaBan, String theLoai, String photo) {
        SachDTO s = new SachDTO();
        int lastElementId = SachBUS.getInstance().getLastElementId();
        if (lastElementId != -1) {
            s.setMaSach("S" + (lastElementId + 1));
        } else {
            s.setMaSach("S" + 1);
        }
        s.setTenSach(tenSach);
        s.setNamXuatBan(Integer.parseInt(namXuatBan));
        s.setMaTacGia(maTacGia);
        s.setMaNXB(maNXB);
        s.setSoLuong(Integer.parseInt(soLuong));
        s.setGiaNhap(Double.parseDouble(giaNhap));
        s.setGiaBan(Double.parseDouble(giaBan));
        s.setTheLoai(theLoai);
        s.setPhoto(photo);
        return s;
    }

    public SachDTO createUpdatedSach(String maSach, String tenSach, String namXuatBan, String maTacGia, String maNXB, String soLuong, String giaNhap, String giaBan, String theLoai, String photo) {
        SachDTO s = new SachDTO(maSach, tenSach, Integer.parseInt(namXuatBan), maTacGia, maNXB, Integer.parseInt(soLuong), Double.parseDouble(giaNhap), Double.parseDouble(giaBan), theLoai, photo);
        return s;
    }

    @Override
    public int getLastElementId() {
        getAll();
        if (!this.sachList.isEmpty()) {
            ArrayList<Integer> sIndex = new ArrayList<Integer>();
            for (SachDTO s : sachList) {
                String[] getIndex = s.getMaSach().split("S");
                sIndex.add(Integer.parseInt(getIndex[1]));
            }
            int lastIndex = Collections.max(sIndex);
            return lastIndex;
        } else {
            return -1;
        }
    }

    @Override
    public SachDTO getById(SachDTO s) {
        return SachDAO.getInstance().selectById(s);
    }

    @Override
    public SachDTO search() {
        return null;
    }

    public ArrayList<SachDTO> filterByGiaNhapAndMaSachASC(String giaTu, String giaDen, String maSach) {
        double giaTuDouble = 0.0;
        double giaDenDouble = 1000000000;

        if (giaTu != null && !giaTu.isEmpty()) {
            try {
                giaTuDouble = Double.parseDouble(giaTu);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá từ không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        if (giaDen != null && !giaDen.isEmpty()) {
            try {
                giaDenDouble = Double.parseDouble(giaDen);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá đến không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        return SachDAO.getInstance().filterByGiaNhapAndMaSachASC(giaTuDouble, giaDenDouble, maSach);
    }

    public ArrayList<SachDTO> filterByGiaNhapAndMaSachDESC(String giaTu, String giaDen, String maSach) {
        double giaTuDouble = 0.0;
        double giaDenDouble = 1000000000;

        if (giaTu != null && !giaTu.isEmpty()) {
            try {
                giaTuDouble = Double.parseDouble(giaTu);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá từ không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        if (giaDen != null && !giaDen.isEmpty()) {
            try {
                giaDenDouble = Double.parseDouble(giaDen);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá đến không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        return SachDAO.getInstance().filterByGiaNhapAndMaSachDESC(giaTuDouble, giaDenDouble, maSach);
    }

    public ArrayList<SachDTO> filterByGiaBanAndMaSachASC(String giaTu, String giaDen, String maSach) {
        double giaTuDouble = 0.0;
        double giaDenDouble = 1000000000;

        if (giaTu != null && !giaTu.isEmpty()) {
            try {
                giaTuDouble = Double.parseDouble(giaTu);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá từ không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        if (giaDen != null && !giaDen.isEmpty()) {
            try {
                giaDenDouble = Double.parseDouble(giaDen);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá đến không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        return SachDAO.getInstance().filterByGiaBanAndMaSachASC(giaTuDouble, giaDenDouble, maSach);
    }

    public ArrayList<SachDTO> filterByGiaBanAndMaSachDESC(String giaTu, String giaDen, String maSach) {
        double giaTuDouble = 0.0;
        double giaDenDouble = 1000000000;

        if (giaTu != null && !giaTu.isEmpty()) {
            try {
                giaTuDouble = Double.parseDouble(giaTu);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá từ không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        if (giaDen != null && !giaDen.isEmpty()) {
            try {
                giaDenDouble = Double.parseDouble(giaDen);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Giá đến không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return new ArrayList<>();
            }
        }

        return SachDAO.getInstance().filterByGiaBanAndMaSachDESC(giaTuDouble, giaDenDouble, maSach);
    }
}
