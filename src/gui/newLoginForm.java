package gui;

import bus.NhanVienBUS;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

import bus.TaiKhoanBUS;
import dto.NhanVienDTO;

import java.awt.Color;

import dto.TaiKhoanDTO;
import java.awt.Toolkit;

public class newLoginForm extends JFrame {

    private JPanel panel, panel_1;
    private JLabel lbl, lblUsername, lblPassword;
    private JButton btnDangnhap;
    private JTextField usernameFld;
    private JPasswordField passwordFld;
    private Font myFont = new Font("Segoe UI", Font.BOLD, 16);
    private ArrayList<TaiKhoanDTO> tkList = TaiKhoanBUS.getInstance().getAll();

    public newLoginForm() {
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(newLoginForm.class.getResource("/icon/logo.png")));
        setTitle("Quản lý cửa hàng sách");
        innit();
    }

    private void login() {
        String taiKhoan = usernameFld.getText();
        char[] matKhauChar = passwordFld.getPassword();
        String matKhau = new String(matKhauChar);

        if (taiKhoan == null || taiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return; // Dừng việc xử lý tiếp theo nếu tài khoản rỗng
        }

        // Kiểm tra mật khẩu
        if (matKhau == null || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return; // Dừng việc xử lý tiếp theo nếu mật khẩu rỗng
        }

        TaiKhoanDTO account = null;
        for (TaiKhoanDTO tk : tkList) {
            NhanVienDTO nvTemp = new NhanVienDTO();
            nvTemp.setMaNV(taiKhoan);
            NhanVienDTO nv = NhanVienBUS.getInstance().getById(nvTemp);
            if (nv != null) {
                if (tk.getMaNV().equals(taiKhoan)) {
                    account = tk;
                }
            }
        }

        if (account != null) {
            if (account.getMatKhau().equals(matKhau)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                MainView mv = new MainView(account);
                mv.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn đã nhập sai mật khẩu!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại hoặc đã bị vô hiệu hoá!", "Thất bại!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void innit() {
        getContentPane().setBackground(new Color(100, 149, 237));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(880, 553);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
        JLabel lblLoginbackground = new JLabel("");
        lblLoginbackground.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginbackground.setIcon(new ImageIcon(newLoginForm.class.getResource("/icon/loginBackground.jpg")));
        getContentPane().add(lblLoginbackground);

        panel = new JPanel();
        panel.setBackground(new Color(100, 149, 237));
        getContentPane().add(panel);

        lbl = new JLabel("CHÀO MỪNG!");
        lbl.setForeground(new Color(245, 245, 245));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(myFont);

        lblUsername = new JLabel("Tài khoản");
        lblUsername.setForeground(new Color(245, 245, 245));
        lblUsername.setFont(myFont);

        usernameFld = new JTextField();
        usernameFld.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameFld.setColumns(10);

        lblPassword = new JLabel("Mật khẩu");
        lblPassword.setForeground(new Color(245, 245, 245));
        lblPassword.setFont(myFont);

        panel_1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        panel_1.setBackground(new Color(100, 149, 237));
        panel_1.setForeground(new Color(100, 149, 237));

        passwordFld = new JPasswordField();
        passwordFld.setFont(myFont);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lbl, GroupLayout.PREFERRED_SIZE, 414, Short.MAX_VALUE))
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(36)
                                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                                .addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(passwordFld, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                                .addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(19)
                                                                .addComponent(usernameFld, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)))
                                                .addGap(36)))
                                .addGap(0))
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(36))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(60)
                                .addComponent(lbl, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addGap(52)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameFld, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordFld, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                                .addGap(56)
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(169, Short.MAX_VALUE))
        );

        btnDangnhap = new JButton("Đăng Nhập");
        btnDangnhap.setBackground(new Color(255, 255, 255));
        btnDangnhap.setIcon(new ImageIcon(newLoginForm.class.getResource("/icon/login_icon.png")));
        btnDangnhap.setForeground(new Color(0, 0, 0));
        btnDangnhap.setFont(myFont);

        btnDangnhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(); // Gọi phương thức login khi click vào btnDangNhap
            }
        });

        passwordFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(); // Gọi phương thức login khi nhấn Enter trên passwordFld
            }
        });

        panel_1.add(btnDangnhap);
        panel.setLayout(gl_panel);
    }

    // Ham main
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newLoginForm().setVisible(true);
            }
        });
    }
}
