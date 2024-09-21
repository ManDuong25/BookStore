-- Đảm bảo sử dụng cơ sở dữ liệu "quanlycuahangsach"
DROP DATABASE if EXISTS quanlycuahangsach;

CREATE DATABASE IF NOT EXISTS quanlycuahangsach
CHARACTER SET UTF8MB4
COLLATE UTF8MB4_UNICODE_CI;

USE quanlycuahangsach;

-- Tạo bảng chucnang
CREATE TABLE IF NOT EXISTS chucnang (
    maChucNang VARCHAR(50) NOT NULL,
    tenChucNang VARCHAR(255),
    PRIMARY KEY (maChucNang)
);

-- Tạo bảng nhomquyen
CREATE TABLE IF NOT EXISTS nhomquyen (
    maNhomQuyen VARCHAR(50) NOT NULL,
    tenNhomQuyen VARCHAR(255),
    trangThai BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (maNhomQuyen)
);

-- Tạo bảng quyen
CREATE TABLE IF NOT EXISTS quyen (
    maQuyen VARCHAR(50) NOT NULL,
    tenQuyen VARCHAR(255),
    PRIMARY KEY (maQuyen)
);

-- Tạo bảng tacgia
CREATE TABLE IF NOT EXISTS tacgia (
    maTacGia VARCHAR(255) NOT NULL,
    ten VARCHAR(255),
    diaChi VARCHAR(512),
    sdt VARCHAR(10),
    email VARCHAR(255),
    trangThai BIT NOT NULL,
    PRIMARY KEY (maTacGia)
);

-- Tạo bảng nhaxuatban
CREATE TABLE IF NOT EXISTS nhaxuatban (
    maNXB VARCHAR(50) NOT NULL,
    ten VARCHAR(255),
    diaChi VARCHAR(512),
    sdt VARCHAR(20),
    email VARCHAR(255),
    trangThai BIT,
    PRIMARY KEY (maNXB)
);

-- Tạo bảng sach
CREATE TABLE IF NOT EXISTS sach (
    maSach VARCHAR(50) NOT NULL,
    tenSach VARCHAR(512),
    namXuatBan INT,
    maTacGia VARCHAR(255),
    maNhaXuatBan VARCHAR(255),
    soLuong INT,
    giaNhap DOUBLE,
    giaBan DOUBLE,
    theLoai VARCHAR(255),
    trangThai BIT NOT NULL,
    photo VARCHAR(512),
    PRIMARY KEY (maSach),
    FOREIGN KEY (maTacGia) REFERENCES tacgia(maTacGia) ON UPDATE RESTRICT ON DELETE RESTRICT,
    FOREIGN KEY (maNhaXuatBan) REFERENCES nhaxuatban(maNXB) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tạo bảng khachhang
CREATE TABLE IF NOT EXISTS khachhang (
    maKH VARCHAR(50) NOT NULL,
    tenKH VARCHAR(50),
    diaChi VARCHAR(512),
    sdt VARCHAR(10),
    trangThai BIT,
    PRIMARY KEY (maKH)
);

-- Tạo bảng nhanvien
CREATE TABLE IF NOT EXISTS nhanvien (
    maNV VARCHAR(50) NOT NULL,
    tenNV VARCHAR(255),
    diaChi VARCHAR(512),
    email VARCHAR(255),
    soDienThoai VARCHAR(10),
    luong DOUBLE,
    trangThai BIT NOT NULL,
    maNhomQuyen VARCHAR(50),
    PRIMARY KEY (maNV),
    FOREIGN KEY (maNhomQuyen) REFERENCES nhomquyen(maNhomQuyen) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tạo bảng taikhoan
CREATE TABLE IF NOT EXISTS taikhoan (
    maNV VARCHAR(50) NOT NULL,
    matKhau VARCHAR(50),
    email VARCHAR(255),
    trangThai BIT NOT NULL,
    PRIMARY KEY (maNV),
    FOREIGN KEY (maNV) REFERENCES nhanvien(maNV) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tạo bảng nhacungcap
CREATE TABLE IF NOT EXISTS nhacungcap (
    maNCC VARCHAR(50) NOT NULL,
    ten VARCHAR(255),
    diaChi VARCHAR(512),
    sdt VARCHAR(20),
    email VARCHAR(255),
    trangThai BIT NOT NULL,
    PRIMARY KEY (maNCC)
);

-- Tạo bảng phieunhap
CREATE TABLE IF NOT EXISTS phieunhap (
    maPhieu VARCHAR(50) NOT NULL,
    maNV VARCHAR(50),
    maNCC VARCHAR(50),
    ngayNhap DATE,
    tongTien DOUBLE,
    trangThai BIT,
    PRIMARY KEY (maPhieu),
    FOREIGN KEY (maNV) REFERENCES nhanvien(maNV) ON UPDATE RESTRICT ON DELETE RESTRICT,
    FOREIGN KEY (maNCC) REFERENCES nhacungcap(maNCC) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tạo bảng hoadon
CREATE TABLE IF NOT EXISTS hoadon (
    maHoaDon VARCHAR(50) NOT NULL,
    maNV VARCHAR(50),
    maKH VARCHAR(50),
    ngayLap DATE,
    thongTinUuDai VARCHAR(255),
    tongTien DOUBLE,
    trangThai BIT NOT NULL,
    PRIMARY KEY (maHoaDon),
    FOREIGN KEY (maNV) REFERENCES nhanvien(maNV) ON UPDATE RESTRICT ON DELETE RESTRICT,
    FOREIGN KEY (maKH) REFERENCES khachhang(maKH) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tạo bảng chitiethoadon
CREATE TABLE IF NOT EXISTS chitiethoadon (
    maHoaDon VARCHAR(50) NOT NULL,
    maSach VARCHAR(50) NOT NULL,
    giaSach DOUBLE,
    soLuongMua INT,
    PRIMARY KEY (maHoaDon, maSach)
);

-- Tạo bảng chitietphieunhap
CREATE TABLE IF NOT EXISTS chitietphieunhap (
    maPhieu VARCHAR(50) NOT NULL,
    maSach VARCHAR(50) NOT NULL,
    giaSach DOUBLE,
    soLuongNhap INT,
    PRIMARY KEY (maPhieu, maSach)
);

-- Tạo bảng chitietquyen
CREATE TABLE IF NOT EXISTS chitietquyen (
    maNhomQuyen VARCHAR(50) NOT NULL,
    maChucNang VARCHAR(50) NOT NULL,
    maQuyen VARCHAR(50) NOT NULL,
    PRIMARY KEY (maNhomQuyen, maChucNang, maQuyen)
);

-- Thêm ràng buộc khóa ngoại cho bảng chitiethoadon
ALTER TABLE chitiethoadon
ADD CONSTRAINT fk_chitiethoadon_hoadon FOREIGN KEY (maHoaDon) REFERENCES hoadon(maHoaDon) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE chitiethoadon
ADD CONSTRAINT fk_chitiethoadon_sach FOREIGN KEY (maSach) REFERENCES sach(maSach) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- Thêm ràng buộc khóa ngoại cho bảng chitietphieunhap
ALTER TABLE chitietphieunhap
ADD CONSTRAINT fk_chitietphieunhap_phieunhap FOREIGN KEY (maPhieu) REFERENCES phieunhap(maPhieu) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE chitietphieunhap
ADD CONSTRAINT fk_chitietphieunhap_sach FOREIGN KEY (maSach) REFERENCES sach(maSach) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- Thêm ràng buộc khóa ngoại cho bảng chitietquyen
ALTER TABLE chitietquyen
ADD CONSTRAINT fk_chitietquyen_nhomquyen FOREIGN KEY (maNhomQuyen) REFERENCES nhomquyen(maNhomQuyen) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE chitietquyen
ADD CONSTRAINT fk_chitietquyen_chucnang FOREIGN KEY (maChucNang) REFERENCES chucnang(maChucNang) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE chitietquyen
ADD CONSTRAINT fk_chitietquyen_quyen FOREIGN KEY (maQuyen) REFERENCES quyen(maQuyen) ON UPDATE RESTRICT ON DELETE RESTRICT;
