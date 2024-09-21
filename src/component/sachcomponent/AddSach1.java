/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package component.sachcomponent;

import bus.NhaXuatBanBUS;
import bus.SachBUS;
import bus.TacGiaBUS;
import component.ToolBarAndTableSach;
import dto.NhaXuatBanDTO;
import dto.SachDTO;
import dto.TacGiaDTO;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mansh
 */
public class AddSach1 extends javax.swing.JDialog {

    private ToolBarAndTableSach tblSach;
    private String linkAnh;

    /**
     * Creates new form AddSach1
     *
     * @param parent
     * @param modal
     * @param tblSach
     */
    public AddSach1(java.awt.Frame parent, boolean modal, ToolBarAndTableSach tblSach) {
        super(parent, modal);
        initComponents();

        this.tblSach = tblSach;
        setupNumericField(giaBanFld);
        setupNumericField(giaNhapFld);

        ArrayList<NhaXuatBanDTO> nxbList = NhaXuatBanBUS.getInstance().getAll();
        ArrayList<TacGiaDTO> tgList = TacGiaBUS.getInstance().getAll();

        for (NhaXuatBanDTO nxb : nxbList) {
            if (nxb.getTrangThai() == 1) {
                maNxbComboBox.addItem(nxb.getMaNXB() + ": " + nxb.getTen());
            }
        }

        for (TacGiaDTO tg : tgList) {
            if (tg.getTrangThai() == 1) {
                maTgComboBox.addItem(tg.getMaTacGia() + ": " + tg.getTen());
            }
        }
    }

    private void setupNumericField(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();  // Ngăn không cho nhập các ký tự không phải là số
                }
            }
        });
    }

    public ImageIcon ResizeImage(String ImagePath) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(previewImg.getWidth(), previewImg.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    private File getUniqueFile(File file) {
        if (!file.exists()) {
            return file;
        }
        String name = file.getName();
        String parent = file.getParent();
        String baseName = name.substring(0, name.lastIndexOf('.'));
        String extension = name.substring(name.lastIndexOf('.'));
        int i = 1;
        while (true) {
            File newFile = new File(parent + File.separator + baseName + "_" + i + extension);
            if (!newFile.exists()) {
                return newFile;
            }
            i++;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tenSachFld = new javax.swing.JTextField();
        namXuatBanFld = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        giaNhapFld = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        giaBanFld = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        theLoaiFld = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        addSachBtn = new javax.swing.JButton();
        huySachBtn = new javax.swing.JButton();
        chooseImg = new javax.swing.JButton();
        previewImg = new javax.swing.JLabel();
        maTgComboBox = new javax.swing.JComboBox<>();
        maNxbComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên sách");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Năm xuất bản");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã tác giả");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mã NXB");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Giá nhập");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Giá bán");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Thể loại");

        addSachBtn.setBackground(new java.awt.Color(102, 255, 0));
        addSachBtn.setText("Thêm");
        addSachBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSachBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addSachBtnMouseEntered(evt);
            }
        });
        addSachBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSachBtnActionPerformed(evt);
            }
        });

        huySachBtn.setBackground(new java.awt.Color(102, 102, 102));
        huySachBtn.setForeground(new java.awt.Color(255, 255, 255));
        huySachBtn.setText("Huỷ");
        huySachBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                huySachBtnMouseClicked(evt);
            }
        });

        chooseImg.setText("Ảnh");
        chooseImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseImgMouseClicked(evt);
            }
        });

        previewImg.setText("Hiển thị ảnh...");

        maTgComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định" }));

        maNxbComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namXuatBanFld, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(tenSachFld, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(maTgComboBox, 0, 154, Short.MAX_VALUE)
                    .addComponent(maNxbComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addSachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(giaBanFld, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                    .addComponent(giaNhapFld)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(theLoaiFld, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(huySachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chooseImg)
                                .addGap(18, 18, 18)
                                .addComponent(previewImg, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tenSachFld, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(namXuatBanFld)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(maTgComboBox))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(maNxbComboBox)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(giaNhapFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(giaBanFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(theLoaiFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chooseImg, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(previewImg, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(huySachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addSachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addSachBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSachBtnMouseClicked
        String tenSach = tenSachFld.getText();
        String namXuatBan = namXuatBanFld.getText();
        String maTacGia = "";
        String maNXB = "";

        if (maTgComboBox.getSelectedIndex() > 0) {
            String tgTxt = (String) maTgComboBox.getSelectedItem();
            String[] maTgArray = tgTxt.split(":");
            maTacGia = maTgArray[0];
        }

        if (maNxbComboBox.getSelectedIndex() > 0) {
            String nxbTxt = (String) maNxbComboBox.getSelectedItem();
            String[] maNXBArray = nxbTxt.split(":");
            maNXB = maNXBArray[0];
        }

        String soLuong = "0";
        String giaNhap = giaNhapFld.getText();
        String giaBan = giaBanFld.getText();
        String theLoai = theLoaiFld.getText();
        String photo = this.linkAnh;

        String errors = SachBUS.getInstance().validateData(tenSach, namXuatBan, maTacGia, maNXB, soLuong, giaNhap, giaBan, theLoai, photo);
        if (errors == null || errors.isEmpty()) {
            SachDTO s = SachBUS.getInstance().createNewSach(tenSach, namXuatBan, maTacGia, maNXB, soLuong, giaNhap, giaBan, theLoai, photo);
            int result = SachBUS.getInstance().add(s);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                tblSach.refreshDataListS();
                tblSach.loadDataSach(tblSach.getListS());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sách thất bại, lỗi hệ thống!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, errors, "Thêm sách thất bại", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_addSachBtnMouseClicked

    private void addSachBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSachBtnMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_addSachBtnMouseEntered

    private void addSachBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSachBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addSachBtnActionPerformed

    private void huySachBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_huySachBtnMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_huySachBtnMouseClicked

    private void chooseImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseImgMouseClicked
        JFileChooser fc = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", "img", "png");
        fc.setFileFilter(imageFilter);

        int returnVal = fc.showOpenDialog(this); // hiển thị hộp thoại chọn tệp để mở trên this.view

        if (returnVal == JFileChooser.APPROVE_OPTION) { // Nếu người dùng chọn Open
            File selectedFile = fc.getSelectedFile();
            File destinationFile;

            System.out.println(System.getProperty("user.dir"));
            if (selectedFile.getAbsolutePath().contains(System.getProperty("user.dir"))) {
                destinationFile = selectedFile;
            } else {
                destinationFile = new File("./src/icon/" + selectedFile.getName());
                destinationFile = getUniqueFile(destinationFile); // Lấy tên tệp duy nhất
                try {
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Không thể sao chép tệp.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Hiển thị hình ảnh xem trước
            if (imageFilter.accept(destinationFile)) {
                try (FileInputStream fis = new FileInputStream(destinationFile)) {
                    this.previewImg.setIcon(new ImageIcon(ImageIO.read(fis)));
                    this.linkAnh = destinationFile.getName();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Không thể đọc tệp.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chỉ mở được định dạng file .img, .png", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (returnVal == JFileChooser.CANCEL_OPTION) { // Nếu người dùng chọn Cancel
            // Xử lý khi người dùng chọn Cancel (nếu cần)
        }
    }//GEN-LAST:event_chooseImgMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSachBtn;
    private javax.swing.JButton chooseImg;
    private javax.swing.JTextField giaBanFld;
    private javax.swing.JTextField giaNhapFld;
    private javax.swing.JButton huySachBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JComboBox<String> maNxbComboBox;
    private javax.swing.JComboBox<String> maTgComboBox;
    private javax.swing.JTextField namXuatBanFld;
    private javax.swing.JLabel previewImg;
    private javax.swing.JTextField tenSachFld;
    private javax.swing.JTextField theLoaiFld;
    // End of variables declaration//GEN-END:variables
}
