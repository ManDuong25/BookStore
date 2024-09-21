USE quanlycuahangsach;

INSERT INTO chucnang (maChucNang, tenChucNang)
VALUES 
    ('CN1', 'Xem'),
    ('CN2', 'Tạo mới'),
    ('CN3', 'Cập nhật'),
    ('CN4', 'Xoá'),
    ('CN5', 'Excel');
    
INSERT INTO quyen (maQuyen, tenQuyen)
VALUES 
    ('Q1', 'Quản lí bán hàng'),
    ('Q2', 'Quản lí hoá đơn'),
    ('Q3', 'Quản lí khách hàng'),
    ('Q4', 'Quản lí nhà cung cấp'),
    ('Q5', 'Quản lí nhập hàng'),
    ('Q6', 'Quản lí nhân viên'),
    ('Q7', 'Quản lí phiếu nhập'),
    ('Q8', 'Quản lí phân quyền'),
    ('Q9', 'Quản lí sản phẩm'),
    ('Q10', 'Quản lí tài khoản'),
    ('Q11', 'Quản lí thống kê'),
    ('Q12', 'Quản lí tác giả'),
    ('Q13', 'Quản lí nhà xuất bản');
    
    
INSERT INTO nhomquyen (maNhomQuyen, tenNhomQuyen, trangThai)
VALUES 
   ('NQ1', 'Admin', 1),
	('NQ2', 'Manager', 1),
	('NQ3', 'Employee', 1);
	
	
	
INSERT INTO chitietquyen (maNhomQuyen, maQuyen, maChucNang)
VALUES 
    -- Admin có full quyền và chức năng
    ('NQ1', 'Q1', 'CN1'),
    ('NQ1', 'Q1', 'CN2'),
    ('NQ1', 'Q1', 'CN3'),
    ('NQ1', 'Q1', 'CN4'),
    ('NQ1', 'Q1', 'CN5'),

    ('NQ1', 'Q2', 'CN1'),
    ('NQ1', 'Q2', 'CN2'),
    ('NQ1', 'Q2', 'CN3'),
    ('NQ1', 'Q2', 'CN4'),
    ('NQ1', 'Q2', 'CN5'),

    ('NQ1', 'Q3', 'CN1'),
    ('NQ1', 'Q3', 'CN2'),
    ('NQ1', 'Q3', 'CN3'),
    ('NQ1', 'Q3', 'CN4'),
    ('NQ1', 'Q3', 'CN5'),

    ('NQ1', 'Q4', 'CN1'),
    ('NQ1', 'Q4', 'CN2'),
    ('NQ1', 'Q4', 'CN3'),
    ('NQ1', 'Q4', 'CN4'),
    ('NQ1', 'Q4', 'CN5'),

    ('NQ1', 'Q5', 'CN1'),
    ('NQ1', 'Q5', 'CN2'),
    ('NQ1', 'Q5', 'CN3'),
    ('NQ1', 'Q5', 'CN4'),
    ('NQ1', 'Q5', 'CN5'),

    ('NQ1', 'Q6', 'CN1'),
    ('NQ1', 'Q6', 'CN2'),
    ('NQ1', 'Q6', 'CN3'),
    ('NQ1', 'Q6', 'CN4'),
    ('NQ1', 'Q6', 'CN5'),

    ('NQ1', 'Q7', 'CN1'),
    ('NQ1', 'Q7', 'CN2'),
    ('NQ1', 'Q7', 'CN3'),
    ('NQ1', 'Q7', 'CN4'),
    ('NQ1', 'Q7', 'CN5'),

    ('NQ1', 'Q8', 'CN1'),
    ('NQ1', 'Q8', 'CN2'),
    ('NQ1', 'Q8', 'CN3'),
    ('NQ1', 'Q8', 'CN4'),
    ('NQ1', 'Q8', 'CN5'),

    ('NQ1', 'Q9', 'CN1'),
    ('NQ1', 'Q9', 'CN2'),
    ('NQ1', 'Q9', 'CN3'),
    ('NQ1', 'Q9', 'CN4'),
    ('NQ1', 'Q9', 'CN5'),
    
    ('NQ1', 'Q10', 'CN1'),
    ('NQ1', 'Q10', 'CN2'),
    ('NQ1', 'Q10', 'CN3'),
    ('NQ1', 'Q10', 'CN4'),
    ('NQ1', 'Q10', 'CN5'),
    ('NQ1', 'Q11', 'CN1'),
    ('NQ1', 'Q11', 'CN2'),
    ('NQ1', 'Q11', 'CN3'),
    ('NQ1', 'Q11', 'CN4'),
    ('NQ1', 'Q11', 'CN5'),
    ('NQ1', 'Q12', 'CN1'),
    ('NQ1', 'Q12', 'CN2'),
    ('NQ1', 'Q12', 'CN3'),
    ('NQ1', 'Q12', 'CN4'),
    ('NQ1', 'Q12', 'CN5'),
    ('NQ1', 'Q13', 'CN1'),
    ('NQ1', 'Q13', 'CN2'),
    ('NQ1', 'Q13', 'CN3'),
    ('NQ1', 'Q13', 'CN4'),
    ('NQ1', 'Q13', 'CN5'),
    
    ('NQ2', 'Q1', 'CN1'),
    ('NQ2', 'Q1', 'CN2'),
    ('NQ2', 'Q1', 'CN3'),
    ('NQ2', 'Q1', 'CN4'),
    ('NQ2', 'Q1', 'CN5'),

    ('NQ2', 'Q2', 'CN1'),
    ('NQ2', 'Q2', 'CN2'),
    ('NQ2', 'Q2', 'CN3'),
    ('NQ2', 'Q2', 'CN4'),
    ('NQ2', 'Q2', 'CN5'),

    ('NQ2', 'Q3', 'CN1'),
    ('NQ2', 'Q3', 'CN2'),
    ('NQ2', 'Q3', 'CN3'),
    ('NQ2', 'Q3', 'CN4'),
    ('NQ2', 'Q3', 'CN5'),

    ('NQ2', 'Q4', 'CN1'),
    ('NQ2', 'Q4', 'CN2'),
    ('NQ2', 'Q4', 'CN3'),
    ('NQ2', 'Q4', 'CN4'),
    ('NQ2', 'Q4', 'CN5'),

    ('NQ2', 'Q5', 'CN1'),
    ('NQ2', 'Q5', 'CN2'),
    ('NQ2', 'Q5', 'CN3'),
    ('NQ2', 'Q5', 'CN4'),
    ('NQ2', 'Q5', 'CN5'),

    ('NQ2', 'Q6', 'CN1'),
    ('NQ2', 'Q6', 'CN2'),
    ('NQ2', 'Q6', 'CN3'),
    ('NQ2', 'Q6', 'CN4'),
    ('NQ2', 'Q6', 'CN5'),

    ('NQ2', 'Q7', 'CN1'),
    ('NQ2', 'Q7', 'CN2'),
    ('NQ2', 'Q7', 'CN3'),
    ('NQ2', 'Q7', 'CN4'),
    ('NQ2', 'Q7', 'CN5'),

    ('NQ2', 'Q8', 'CN1'),
    ('NQ2', 'Q8', 'CN2'),
    ('NQ2', 'Q8', 'CN3'),
    ('NQ2', 'Q8', 'CN4'),
    ('NQ2', 'Q8', 'CN5'),

    ('NQ2', 'Q9', 'CN1'),
    ('NQ2', 'Q9', 'CN2'),
    ('NQ2', 'Q9', 'CN3'),
    ('NQ2', 'Q9', 'CN4'),
    ('NQ2', 'Q9', 'CN5'),
    
    ('NQ3', 'Q1', 'CN1'),
    ('NQ3', 'Q1', 'CN2'),
    ('NQ3', 'Q1', 'CN3'),
    ('NQ3', 'Q1', 'CN4'),
    ('NQ3', 'Q1', 'CN5');

INSERT INTO tacgia (maTacGia, ten, diaChi, sdt, email, trangThai) 
VALUES 
  ('TG1', 'Nguyễn Văn A', '123 Đường ABC', '0123456789', 'nguyenvana@gmail.com', 1),
  ('TG2', 'Trần Thị B', '456 Phố XYZ', '0987654321', 'tranthib@gmail.com', 1),
  ('TG3', 'Lê Văn C', '789 Ngõ KLM', '0968123456', 'levanc@gmail.com', 1),
  ('TG4', 'Phạm Thị D', '321 Đường LMN', '0123987654', 'phamthid@gmail.com', 1),
  ('TG5', 'Hoàng Văn E', '654 Phố NOP', '0905321654', 'hoangvane@gmail.com', 1),
  ('TG6', 'Nguyễn Thị F', '987 Đường QRS', '0978654321', 'nguyenthif@gmail.com', 1),
  ('TG7', 'Trần Văn G', '159 Ngõ TUV', '0123789456', 'tranvang@gmail.com', 1),
  ('TG8', 'Lê Thị H', '753 Phố XYZ', '0901234567', 'lethih@gmail.com', 1),
  ('TG9', 'Phạm Văn I', '369 Đường JKL', '0987123456', 'phamvani@gmail.com', 1),
  ('TG10', 'Hoàng Thị K', '753 Ngõ MNO', '0963654321', 'hoangthik@gmail.com', 1);
  
INSERT INTO nhacungcap (maNCC, ten, diaChi, sdt, email, trangThai)
VALUES 
	('NCC1', 'Nhà cung cấp A', 'Địa chỉ A', '0323456789', 'email1@gmail.com', 1),
	('NCC2', 'Nhà cung cấp B', 'Địa chỉ B', '0987654321', 'email2@gmail.com', 1),
	('NCC3', 'Nhà cung cấp C', 'Địa chỉ C', '0369696969', 'email3@gmail.com', 1),
	('NCC4', 'Nhà cung cấp D', 'Địa chỉ D', '0888888888', 'email4@gmail.com', 1),
	('NCC5', 'Nhà cung cấp E', 'Địa chỉ E', '0777777777', 'email5@gmail.com', 1),
	('NCC6', 'Nhà cung cấp F', 'Địa chỉ F', '0999999999', 'email6@gmail.com', 1),
	('NCC7', 'Nhà cung cấp G', 'Địa chỉ G', '0912345678', 'email7@gmail.com', 1),
	('NCC8', 'Nhà cung cấp H', 'Địa chỉ H', '0966666666', 'email8@gmail.com', 1),
	('NCC9', 'Nhà cung cấp I', 'Địa chỉ I', '0333333333', 'email9@gmail.com', 1),
	('NCC10', 'Nhà cung cấp J', 'Địa chỉ J', '0355555555', 'email10@gmail.com', 1);

INSERT INTO `nhaxuatban` (`maNXB`, `ten`, `diaChi`, `sdt`, `email`) VALUES
('NXB1', 'Nhà xuất bản Giáo dục Việt Nam', '81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '(024) 3822 1386', 'veph@nxbgd.vn'),
('NXB2', 'Nhà xuất bản Trẻ', '161B Lý Chính Thắng, Phường Võ Thị Sáu, Quận 3, TP. Hồ Chí Minh', '(84.028) 39316289', 'hopthubandoc@nxbtre.com.vn'),
('NXB3', 'Nhà xuất bản văn học', '18 Nguyễn Trường Tộ, phường Trúc Bạch, quận Ba Đình, thành phố Hà Nội', '(024) 3716 1518', 'info.nxbvanhoc@gmail.com'),
('NXB4', 'Nhà xuất bản Hội nhà văn', '371/16 Hai Bà Trưng-Q3-TP. HCM', '(024) 3822 2135', 'nxbhnv.saigon@gmail.com'),
('NXB5', 'Nhà xuất bản Kim Đồng', '248 Cống Quỳnh, P. Phạm Ngũ Lão, Q.1, TP. Hồ Chí Minh', '(028) 39303832', 'cnkimdong@nxbkimdong.com.vn'),
('NXB6', 'NXB Đại học Quốc gia TPHCM', 'Nhà điều hành ĐHQG-HCM, Phường Linh Trung, Thủ Đức, Thành phố Hồ Chí Minh', '(028) 6272 6361', 'nttthuy.nxb@vnuhcm.edu.vn');
	
INSERT INTO `sach` (`maSach`, `tenSach`, `namXuatBan`, `maTacGia`, `maNhaXuatBan`, `soLuong`, `giaNhap`, `giaban`, `theLoai`, `trangThai`, `photo`) VALUES
('S1', 'Ky thuat lap trinh C', 2017, 'TG1', 'NXB1', 75, 0, 135000, 'Giáo dục', 1, ''),
('S2', 'Nguon goc cac loai', 2020, 'TG2', 'NXB5', 0, 0, 110000, 'Khoa học', 0, ''),
('S3', 'The gioi dong vat', 2013, 'TG3', 'NXB5', 63, 0, 280000, 'Khoa học', 1, ''),
('S4', 'Ong gia va bien ca', 2023, 'TG1', 'NXB3', 87, 0, 420000, 'Văn học nước ngoài', 1, ''),
('S5', 'Tat den', 2019, 'TG5', 'NXB2', 219, 0, 162000, 'Văn học Việt Nam', 1, ''),
('S6', 'Nha gia kim', 2013, 'TG9', 'NXB4', 119, 0, 570000, 'Văn học nước ngoài', 1, ''),
('S7', 'Thep da toi the day', 1936, 'TG6', 'NXB5', 110, 0, 250000, 'Văn học', 1, '');

INSERT INTO `nhanvien` (`maNV`, `tenNV`, `diaChi`, `email`, `soDienThoai`, `luong`, `trangThai`, `maNhomQuyen`) VALUES
('NV1', 'Nguyễn Hoàng Gia', '72 Hùng Vương TpHCM', 'BLe99@gmail.com', '0903138266', 15000000, 1, 'NQ1'),
('NV2', 'Nguyễn Gia Phát', 'CMT8 TpHCM', 'phatnguyen@gmail.com', '084317989', 15000000, 1, 'NQ2'),
('NV3', 'Lý Hoàng Tú', '3/2', 'tuhl@gmail.com', '0812345678', 15000000, 1, 'NQ3'),
('NV4', 'Nguyễn Hồng Ngân', '', 'nganhong@gmail.com', '098765123', 15000000, 1, 'NQ3'),
('NV5', 'Ngô Thanh Như', '', 'ngothanhnhu99@gmail.com', '0903313363', 2700000, 1, 'NQ3'),
('NV6', 'Lê Văn Giàu', '143/63 Hồ Xuân Hương TpHCM', 'giault@gmail.com', '0902236128', 30000000, 1, 'NQ3'),
('NV7', 'Nguyễn Hồng Phong', '312 Lê Duẩn TpHCM', 'phongnguyen@gmail.com', '0909090909', 2800000, 1, 'NQ3'),
('NV8', 'Nguyễn Hoàng Nam', '', 'namnguyen9@gmail.com', '090901324', 30000000, 1, 'NQ3'),
('NV9', 'Trần Văn Sơn', '', 'sonn98@gmail.com', '0843136122', 31000000, 1, 'NQ3');

INSERT INTO `taikhoan` (`maNV`, `matKhau`, `email`, `trangThai`)
VALUES
('NV1', '12345678', 'BLe99@gmail.com', 1),
('NV2', '12345678', 'phatnguyen@gmail.com', 1),
('NV3', '12345678', 'tuhl@gmail.com', 1),
('NV4', '12345678', 'nganhong@gmail.com', 1),
('NV5', '12345678', 'ngothanhnhu99@gmail.com', 1),
('NV6', '12345678', 'giault@gmail.com', 1),
('NV7', '12345678', 'phongnguyen@gmail.com', 1),
('NV8', '12345678', 'namnguyen9@gmail.com', 1),
('NV9', '12345678', 'sonn98@gmail.com', 1);

INSERT INTO `khachhang` (`maKH`, `tenKH`, `diaChi`, `sdt`, `trangThai`) VALUES
('KH1', 'Vũ Thị Liên', '12 Hậu Giang Quận 6 TpHCM', '0931331234', '1'),
('KH2', 'Lê Thu Hiền', '43 Vĩnh Viễn Quận 5 TpHCM', '089136255', '1'),
('KH3', 'Lý Gia Phúc', '314 Hương Lộ 2 TpHCM', '099919998', '1'),
('KH4', 'Vương Tuấn Kiệt', '63 Tân Hòa Đông Quận Bình Tân TpHCM', '0843613825', '1'),
('KH5', 'Nguyễn Dương', '99/143 CMT8', '0909277255', '1'),
('KH6', 'Lê Hoàng Kiều Anh', '412 3/2 Quận 11 TpHCM', '091236547', '1'),
('KH7', 'Trần Văn', '195 Dương Tử Giang Quận 11 TpHCM', '093123657', '1'),
('KH8', 'Hoàng Gia Bảo', '149 An Dương Vương Quận 5 TpHCM', '093336241', '1'),
('KH9', 'Nguyễn Tuấn Kiệt', '733 Trần Hưng Đạo Quận 5 TpHCM', '091199223', '1'),
('KH10', 'Trần Yến Nhi', '', '091236547', '1');

	
INSERT INTO phieunhap (maPhieu, maNV, maNCC, ngayNhap, tongTien, trangThai)
VALUES 
('PN1', 'NV1', 'NCC1', '2024-04-20', 3000000, 1),
('PN2', 'NV2', 'NCC2', '2024-04-21', 16100000, 1),
('PN3', 'NV3', 'NCC3', '2024-04-22', 24810000, 1),
('PN4', 'NV1', 'NCC4', '2024-04-23', 10000000, 1),
('PN5', 'NV2', 'NCC5', '2024-04-24', 11575000, 1),
('PN6', 'NV3', 'NCC6', '2024-04-25', 40600000, 1),
('PN7', 'NV1', 'NCC7', '2024-04-26', 50430000, 1),
('PN8', 'NV2', 'NCC8', '2024-04-27', 18750000, 1),
('PN9', 'NV3', 'NCC9', '2024-04-28', 20150000, 1),
('PN10', 'NV1', 'NCC10', '2024-04-29', 65100000, 1);

-- Chèn dữ liệu vào bảng chitietphieunhap
INSERT INTO chitietphieunhap (maPhieu, maSach, giaSach, soLuongNhap)
VALUES 
('PN1', 'S1', 135000, 10),
('PN1', 'S2', 110000, 15),
('PN2', 'S3', 280000, 20),
('PN2', 'S4', 420000, 25),
('PN3', 'S5', 162000, 30),
('PN3', 'S6', 570000, 35),
('PN4', 'S7', 250000, 40),
('PN5', 'S1', 135000, 45),
('PN5', 'S2', 110000, 50),
('PN6', 'S3', 280000, 55),
('PN6', 'S4', 420000, 60),
('PN7', 'S5', 162000, 65),
('PN7', 'S6', 570000, 70),
('PN8', 'S7', 250000, 75),
('PN9', 'S1', 135000, 80),
('PN9', 'S2', 110000, 85),
('PN10', 'S3', 280000, 90),
('PN10', 'S4', 420000, 95);



INSERT INTO `hoadon` (`maHoaDon`, `maNV`, `maKH`, `ngayLap`, `thongTinUuDai`, `tongTien`, `trangThai`) VALUES
('HD1', 'NV2', 'KH1', '2024-04-09', NULL, 465000, 1),
('HD2', 'NV1', 'KH1', '2024-04-09', NULL, 670000, 1),
('HD3', 'NV3', 'KH2', '2024-04-10', NULL, 732000, 1),
('HD4', 'NV1', 'KH3', '2024-04-10', NULL, 390000, 1),
('HD5', 'NV1', 'KH4', '2024-04-10', NULL, 280000, 1),
('HD6', 'NV3', 'KH1', '2024-04-10', NULL, 135000, 1),
('HD7', 'NV3', 'KH2', '2024-04-11', NULL, 840000, 1),
('HD8', 'NV4', 'KH5', '2024-04-11', NULL, 250000, 1),
('HD9', 'NV2', 'KH7', '2024-04-12', NULL, 280000, 1),
('HD10', 'NV5', 'KH4', '2024-04-12', NULL, 110000, 1);

INSERT INTO `chitiethoadon` (`maHoaDon`, `maSach`, `giaSach`, `soLuongMua`) VALUES
('HD1', 'S1', 135000, 1),
('HD1', 'S2', 110000, 3),
('HD6', 'S1', 135000, 1),
('HD2', 'S3', 280000, 2),
('HD7', 'S4', 420000, 2),
('HD2', 'S2', 110000, 1),
('HD3', 'S5', 162000, 1),
('HD8', 'S7', 250000, 1),
('HD3', 'S6', 570000, 1),
('HD4', 'S2', 110000, 1),
('HD9', 'S3', 280000, 1),
('HD4', 'S3', 280000, 1),
('HD5', 'S3', 280000, 1),
('HD10', 'S2', 110000, 1);
