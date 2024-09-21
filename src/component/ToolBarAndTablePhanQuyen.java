/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.ChiTietQuyenBUS;
import bus.NhomQuyenBUS;
import dto.ChiTietQuyenDTO;
import dto.NhomQuyenDTO;
import gui.MainView;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mansh
 */
public class ToolBarAndTablePhanQuyen extends javax.swing.JPanel implements ActionListener {

    private ArrayList<NhomQuyenDTO> listNq = NhomQuyenBUS.getInstance().getAll();
    private MainView mv;

    private ArrayList<Integer> qlbhChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlhdChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlkhChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlnccChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlnhChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlnvChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlpnChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlpqChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlspChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qltkChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qltkeChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qltgChecked = new ArrayList<Integer>();
    private ArrayList<Integer> qlnxbChecked = new ArrayList<Integer>();

    public void refreshDataNhomQuyen() {
        this.listNq = NhomQuyenBUS.getInstance().getAll();
    }

    private ArrayList<Integer> checkSelectedCheckboxes(JPanel panel) {
        ArrayList<Integer> checkedIndexes = new ArrayList<>();
        int index = 0;
        boolean isFirstCheckboxSelected = false;

        // Duyệt qua tất cả các thành phần trong panel
        for (Component comp : panel.getComponents()) {
            // Kiểm tra nếu thành phần là checkbox
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                // Kiểm tra nếu checkbox được chọn
                if (checkBox.isSelected()) {
                    // Nếu đây là checkbox đầu tiên được chọn, đặt isFirstCheckboxSelected thành true
                    if (index == 0) {
                        isFirstCheckboxSelected = true;
                    }
                    // Nếu isFirstCheckboxSelected là true, thêm chỉ số của checkbox vào danh sách
                    if (isFirstCheckboxSelected) {
                        checkedIndexes.add(index);
                    } else {
                        // Nếu isFirstCheckboxSelected không phải là true, không thêm checkbox vào danh sách
                        JOptionPane.showMessageDialog(mv, "Bạn phải chọn quyền xem trước!", "Lỗi chọn quyền!", JOptionPane.ERROR_MESSAGE);
                        checkedIndexes.clear();
                        resetCheckBoxes(panel);
                        return checkedIndexes; // Trả về danh sách rỗng nếu checkbox đầu tiên không được chọn
                    }
                }
                index++;
            }
        }
        return checkedIndexes;
    }

    private void resetCheckBoxes(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                checkBox.setSelected(false); // Reset trạng thái của checkbox về false
            }
        }
    }

    public ToolBarAndTablePhanQuyen(MainView mv) {
        initComponents();
        this.mv = mv;
        loadDataNhomQuyen(listNq);

        qlbhPanel.setLayout(new GridLayout(1, 6));
        qlbhPanel.add(new Label("Quản lí bán hàng"));
        for (int i = 1; i <= 5; i++) {
            qlbhPanel.add(new JCheckBox());
        }

        qlhdPanel.setLayout(new GridLayout(1, 6));
        qlhdPanel.add(new Label("Quản lí hoá đơn"));
        for (int i = 1; i <= 5; i++) {
            qlhdPanel.add(new JCheckBox());
        }

        qlkhPanel.setLayout(new GridLayout(1, 6));
        qlkhPanel.add(new Label("Quản lí khách hàng"));
        for (int i = 1; i <= 5; i++) {
            qlkhPanel.add(new JCheckBox());
        }

        qlnccPanel.setLayout(new GridLayout(1, 6));
        qlnccPanel.add(new Label("Quản lí nhà cung cấp"));
        for (int i = 1; i <= 5; i++) {
            qlnccPanel.add(new JCheckBox());
        }

        qlnhPanel.setLayout(new GridLayout(1, 6));
        qlnhPanel.add(new Label("Quản lí nhập hàng"));
        for (int i = 1; i <= 5; i++) {
            qlnhPanel.add(new JCheckBox());
        }

        qlnvPanel.setLayout(new GridLayout(1, 6));
        qlnvPanel.add(new Label("Quản lí nhân viên"));
        for (int i = 1; i <= 5; i++) {
            qlnvPanel.add(new JCheckBox());
        }

        qlpnPanel.setLayout(new GridLayout(1, 6));
        qlpnPanel.add(new Label("Quản lí phiếu nhập"));
        for (int i = 1; i <= 5; i++) {
            qlpnPanel.add(new JCheckBox());
        }

        qlpqPanel.setLayout(new GridLayout(1, 6));
        qlpqPanel.add(new Label("Quản lí phân quyền"));
        for (int i = 1; i <= 5; i++) {
            qlpqPanel.add(new JCheckBox());
        }

        qlspPanel.setLayout(new GridLayout(1, 6));
        qlspPanel.add(new Label("Quản lí sản phẩm"));
        for (int i = 1; i <= 5; i++) {
            qlspPanel.add(new JCheckBox());
        }

        qltkPanel.setLayout(new GridLayout(1, 6));
        qltkPanel.add(new Label("Quản lí tài khoản"));
        for (int i = 1; i <= 5; i++) {
            qltkPanel.add(new JCheckBox());
        }

        qltkePanel.setLayout(new GridLayout(1, 6));
        qltkePanel.add(new Label("Quản lí thống kê"));
        for (int i = 1; i <= 5; i++) {
            qltkePanel.add(new JCheckBox());
        }

        qltgPanel.setLayout(new GridLayout(1, 6));
        qltgPanel.add(new Label("Quản lí tác giả"));
        for (int i = 1; i <= 5; i++) {
            qltgPanel.add(new JCheckBox());
        }

        qlnxbPanel.setLayout(new GridLayout(1, 6));
        qlnxbPanel.add(new Label("Quản lí nhà xuất bản"));
        for (int i = 1; i <= 5; i++) {
            qlnxbPanel.add(new JCheckBox());
        }

        addCheckboxListeners();

        jTabbedPane1.setEnabledAt(1, false);
        if (!NhomQuyenBUS.getInstance().checkIfAllowToTaoMoiQ(mv.getNv().getMaNhomQuyen(), "Q8")) {
            addBtn.setEnabled(false);
            createNewBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToXoaQ(mv.getNv().getMaNhomQuyen(), "Q8")) {
            deleteBtn.setEnabled(false);
        }
        if (!NhomQuyenBUS.getInstance().checkIfAllowToCapNhatQ(mv.getNv().getMaNhomQuyen(), "Q8")) {
            updateBtn.setEnabled(false);
            saveUpdateBtn.setEnabled(false);
        }
    }

    public void loadDataNhomQuyen(ArrayList<NhomQuyenDTO> listNq) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã nhóm quyền");
        defaultTableModel.addColumn("Tên nhóm quyền");
        for (NhomQuyenDTO nq : listNq) {
            if (nq.getTrangThai() == 1) {
                defaultTableModel.addRow(new Object[]{
                    nq.getMaNhomQuyen(),
                    nq.getTenNhomQuyen(),});
            }
//            System.out.println(sach);
        }
        nhomQuyenTbl.setModel(defaultTableModel);
        nhomQuyenTbl.setRowHeight(100);
        nhomQuyenTbl.getTableHeader().setReorderingAllowed(false);
    }

    private void addCheckboxListeners() {
        addCheckboxListener(qlbhPanel);
        addCheckboxListener(qlhdPanel);
        addCheckboxListener(qlkhPanel);
        addCheckboxListener(qlnccPanel);
        addCheckboxListener(qlnhPanel);
        addCheckboxListener(qlnvPanel);
        addCheckboxListener(qlpnPanel);
        addCheckboxListener(qlpqPanel);
        addCheckboxListener(qlspPanel);
        addCheckboxListener(qltkPanel);
        addCheckboxListener(qltkePanel);
        addCheckboxListener(qltgPanel);
        addCheckboxListener(qlnxbPanel);
    }

    private void addCheckboxListener(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                checkBox.addActionListener(this); // Thêm sự kiện cho checkbox
            }
        }
    }

    public void checkSelectedAllCheckBox() {
        qlbhChecked = checkSelectedCheckboxes(qlbhPanel);
        qlhdChecked = checkSelectedCheckboxes(qlhdPanel);
        qlkhChecked = checkSelectedCheckboxes(qlkhPanel);
        qlnccChecked = checkSelectedCheckboxes(qlnccPanel);
        qlnhChecked = checkSelectedCheckboxes(qlnhPanel);
        qlnvChecked = checkSelectedCheckboxes(qlnvPanel);
        qlpnChecked = checkSelectedCheckboxes(qlpnPanel);
        qlpqChecked = checkSelectedCheckboxes(qlpqPanel);
        qlspChecked = checkSelectedCheckboxes(qlspPanel);
        qltkChecked = checkSelectedCheckboxes(qltkPanel);
        qltkeChecked = checkSelectedCheckboxes(qltkePanel);
        qltgChecked = checkSelectedCheckboxes(qltgPanel);
        qlnxbChecked = checkSelectedCheckboxes(qlnxbPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkSelectedAllCheckBox();
    }

    public void addToChiTietQuyen(ArrayList<Integer> checkedList, String maNhomQuyen, String maQuyen) {
        if (!checkedList.isEmpty()) {
            for (int sttChucNang : checkedList) {
                String maChucNang = "CN" + (sttChucNang + 1);
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maNhomQuyen, maChucNang, maQuyen);
                ChiTietQuyenBUS.getInstance().add(ctq);
            }
        }
    }

    public String getSelectedItemIdNhomQuyen() {
        DefaultTableModel dtm = (DefaultTableModel) nhomQuyenTbl.getModel();
        int selectedRowIndex = nhomQuyenTbl.getSelectedRow();
        String id = "";
        if (selectedRowIndex >= 0) {
            id = (String) dtm.getValueAt(selectedRowIndex, 0);
        }
        return id;
    }

    public void showCurrentNhomQuyenSetting() {
        String selectedNhomQuyenId = getSelectedItemIdNhomQuyen();
        if (selectedNhomQuyenId == null || selectedNhomQuyenId.isEmpty()) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn nhóm quyền muốn sửa ở bảng bên trái!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            ChiTietQuyenDTO ctq = new ChiTietQuyenDTO();
            ctq.setMaNhomQuyen(selectedNhomQuyenId);
            NhomQuyenDTO nq = new NhomQuyenDTO();
            nq.setMaNhomQuyen(selectedNhomQuyenId);
            ArrayList<ChiTietQuyenDTO> chiTietQuyenList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctq);
            tenQuyenTxt.setText(NhomQuyenBUS.getInstance().getById(nq).getTenNhomQuyen());
            // Reset trạng thái của tất cả các checkbox về false trước khi điều chỉnh
            resetCheckBoxes(qlbhPanel);
            resetCheckBoxes(qlhdPanel);
            resetCheckBoxes(qlkhPanel);
            resetCheckBoxes(qlnccPanel);
            resetCheckBoxes(qlnhPanel);
            resetCheckBoxes(qlnvPanel);
            resetCheckBoxes(qlpnPanel);
            resetCheckBoxes(qlpqPanel);
            resetCheckBoxes(qlspPanel);
            resetCheckBoxes(qltkPanel);
            resetCheckBoxes(qltkePanel);
            resetCheckBoxes(qltgPanel);
            resetCheckBoxes(qlnxbPanel);
            // Duyệt qua danh sách các chi tiết quyền đã được chọn
            for (ChiTietQuyenDTO ctqGot : chiTietQuyenList) {
                String maChucNang = ctqGot.getMaChucNang();
                String maQuyen = ctqGot.getMaQuyen();

                // Dựa vào mã quyền để xác định checkbox cần đánh dấu
                switch (maQuyen) {
                    case "Q1":
                        tickCheckbox(qlbhPanel, maChucNang);
                        break;
                    case "Q2":
                        tickCheckbox(qlhdPanel, maChucNang);
                        break;
                    case "Q3":
                        tickCheckbox(qlkhPanel, maChucNang);
                        break;
                    case "Q4":
                        tickCheckbox(qlnccPanel, maChucNang);
                        break;
                    case "Q5":
                        tickCheckbox(qlnhPanel, maChucNang);
                        break;
                    case "Q6":
                        tickCheckbox(qlnvPanel, maChucNang);
                        break;
                    case "Q7":
                        tickCheckbox(qlpnPanel, maChucNang);
                        break;
                    case "Q8":
                        tickCheckbox(qlpqPanel, maChucNang);
                        break;
                    case "Q9":
                        tickCheckbox(qlspPanel, maChucNang);
                        break;
                    case "Q10":
                        tickCheckbox(qltkPanel, maChucNang);
                        break;
                    case "Q11":
                        tickCheckbox(qltkePanel, maChucNang);
                        break;
                    case "Q12":
                        tickCheckbox(qltgPanel, maChucNang);
                        break;
                    case "Q13":
                        tickCheckbox(qlnxbPanel, maChucNang);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // Phương thức để đánh dấu checkbox dựa trên mã chức năng
    private void tickCheckbox(JPanel panel, String maChucNang) {
        int index = 1;
        String maChucNangStt = maChucNang.split("CN")[1];
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (index == Integer.parseInt(maChucNangStt)) {
                    System.out.println(index + " " + maChucNang);
                    checkBox.setSelected(true);
                    break; 
                }
                index++;
            }
        }
    }

    public void deleteItem() {
        String id = getSelectedItemIdNhomQuyen();
        if (id.isEmpty() || id == null) {
            JOptionPane.showMessageDialog(mv, "Vui lòng chọn nhóm quyền muốn xoá!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn xóa nhóm quyền này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            // Kiểm tra người dùng nhấn Yes hay không
            if (confirm == JOptionPane.YES_OPTION) {
                NhomQuyenDTO nq = new NhomQuyenDTO();
                nq.setMaNhomQuyen(id);
                ChiTietQuyenDTO ctqTemp = new ChiTietQuyenDTO();
                ctqTemp.setMaNhomQuyen(id);
                ArrayList<ChiTietQuyenDTO> ctqList = ChiTietQuyenBUS.getInstance().getByMaNhomQuyen(ctqTemp);
                for (ChiTietQuyenDTO ctq : ctqList) {
                    ChiTietQuyenBUS.getInstance().delete(ctq);
                }

                int result = NhomQuyenBUS.getInstance().delete(nq);
                if (result > 0) {
                    JOptionPane.showMessageDialog(mv, "Xoá nhóm quyền thành công!", "Thành công", JOptionPane.ERROR_MESSAGE);
                    this.listNq = NhomQuyenBUS.getInstance().getAll();
                    loadDataNhomQuyen(listNq);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        toolBar2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        filterByPricePanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPaneNq = new javax.swing.JScrollPane();
        nhomQuyenTbl = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tenQuyenTxt = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        qlbhPanel = new javax.swing.JPanel();
        qlnvPanel = new javax.swing.JPanel();
        qlnhPanel = new javax.swing.JPanel();
        qlnccPanel = new javax.swing.JPanel();
        qlhdPanel = new javax.swing.JPanel();
        qlkhPanel = new javax.swing.JPanel();
        qlpnPanel = new javax.swing.JPanel();
        qlspPanel = new javax.swing.JPanel();
        qltkPanel = new javax.swing.JPanel();
        qlpqPanel = new javax.swing.JPanel();
        qltkePanel = new javax.swing.JPanel();
        qltgPanel = new javax.swing.JPanel();
        qlnxbPanel = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        createNewBtn = new javax.swing.JButton();
        saveUpdateBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setToolTipText("");
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1135, 100));

        toolBar2.setBackground(new java.awt.Color(51, 102, 255));
        toolBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        toolBar2.setLayout(new java.awt.GridBagLayout());

        jPanel9.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Danh sách nhóm quyền");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        toolBar2.add(jPanel9, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(118, 50));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        toolBar2.add(jPanel1, new java.awt.GridBagConstraints());

        jPanel2.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        toolBar2.add(jPanel2, new java.awt.GridBagConstraints());

        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 70));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        toolBar2.add(jPanel10, new java.awt.GridBagConstraints());

        filterByPricePanel.setOpaque(false);

        javax.swing.GroupLayout filterByPricePanelLayout = new javax.swing.GroupLayout(filterByPricePanel);
        filterByPricePanel.setLayout(filterByPricePanelLayout);
        filterByPricePanelLayout.setHorizontalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
        );
        filterByPricePanelLayout.setVerticalGroup(
            filterByPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );

        toolBar2.add(filterByPricePanel, new java.awt.GridBagConstraints());

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(320, 58));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 8, 6));

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Plus__Orange.png"))); // NOI18N
        addBtn.setText("Thêm");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        jPanel4.add(addBtn);

        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil-icon.png"))); // NOI18N
        updateBtn.setText("Sửa");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        jPanel4.add(updateBtn);

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-icon.png"))); // NOI18N
        deleteBtn.setText("Xoá");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });
        jPanel4.add(deleteBtn);

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loc-icon.png"))); // NOI18N
        jButton6.setText("Huỷ lọc");
        jPanel4.add(jButton6);

        toolBar2.add(jPanel4, new java.awt.GridBagConstraints());

        jScrollPane1.setViewportView(toolBar2);

        add(jScrollPane1, java.awt.BorderLayout.NORTH);

        nhomQuyenTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneNq.setViewportView(nhomQuyenTbl);

        jTabbedPane1.addTab("Nhóm quyền", jScrollPaneNq);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1));
        jPanel5.setPreferredSize(new java.awt.Dimension(1156, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên nhóm quyền");

        tenQuyenTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addComponent(tenQuyenTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(359, 359, 359))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tenQuyenTxt)
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 30, 10, 30));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jPanel7.setLayout(new java.awt.GridLayout(15, 1));

        jPanel8.setLayout(new java.awt.GridLayout(1, 6));

        jLabel4.setText("Danh mục chức năng");
        jPanel8.add(jLabel4);

        jLabel5.setText("Xem");
        jPanel8.add(jLabel5);

        jLabel6.setText("Thêm");
        jPanel8.add(jLabel6);

        jLabel8.setText("Cập nhật");
        jPanel8.add(jLabel8);

        jLabel7.setText("Xoá");
        jPanel8.add(jLabel7);

        jLabel3.setText("Excel");
        jPanel8.add(jLabel3);

        jPanel7.add(jPanel8);

        javax.swing.GroupLayout qlbhPanelLayout = new javax.swing.GroupLayout(qlbhPanel);
        qlbhPanel.setLayout(qlbhPanelLayout);
        qlbhPanelLayout.setHorizontalGroup(
            qlbhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlbhPanelLayout.setVerticalGroup(
            qlbhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlbhPanel);

        javax.swing.GroupLayout qlnvPanelLayout = new javax.swing.GroupLayout(qlnvPanel);
        qlnvPanel.setLayout(qlnvPanelLayout);
        qlnvPanelLayout.setHorizontalGroup(
            qlnvPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlnvPanelLayout.setVerticalGroup(
            qlnvPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlnvPanel);

        javax.swing.GroupLayout qlnhPanelLayout = new javax.swing.GroupLayout(qlnhPanel);
        qlnhPanel.setLayout(qlnhPanelLayout);
        qlnhPanelLayout.setHorizontalGroup(
            qlnhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlnhPanelLayout.setVerticalGroup(
            qlnhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlnhPanel);

        javax.swing.GroupLayout qlnccPanelLayout = new javax.swing.GroupLayout(qlnccPanel);
        qlnccPanel.setLayout(qlnccPanelLayout);
        qlnccPanelLayout.setHorizontalGroup(
            qlnccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlnccPanelLayout.setVerticalGroup(
            qlnccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlnccPanel);

        javax.swing.GroupLayout qlhdPanelLayout = new javax.swing.GroupLayout(qlhdPanel);
        qlhdPanel.setLayout(qlhdPanelLayout);
        qlhdPanelLayout.setHorizontalGroup(
            qlhdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlhdPanelLayout.setVerticalGroup(
            qlhdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlhdPanel);

        javax.swing.GroupLayout qlkhPanelLayout = new javax.swing.GroupLayout(qlkhPanel);
        qlkhPanel.setLayout(qlkhPanelLayout);
        qlkhPanelLayout.setHorizontalGroup(
            qlkhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlkhPanelLayout.setVerticalGroup(
            qlkhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlkhPanel);

        javax.swing.GroupLayout qlpnPanelLayout = new javax.swing.GroupLayout(qlpnPanel);
        qlpnPanel.setLayout(qlpnPanelLayout);
        qlpnPanelLayout.setHorizontalGroup(
            qlpnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlpnPanelLayout.setVerticalGroup(
            qlpnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlpnPanel);

        javax.swing.GroupLayout qlspPanelLayout = new javax.swing.GroupLayout(qlspPanel);
        qlspPanel.setLayout(qlspPanelLayout);
        qlspPanelLayout.setHorizontalGroup(
            qlspPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlspPanelLayout.setVerticalGroup(
            qlspPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlspPanel);

        javax.swing.GroupLayout qltkPanelLayout = new javax.swing.GroupLayout(qltkPanel);
        qltkPanel.setLayout(qltkPanelLayout);
        qltkPanelLayout.setHorizontalGroup(
            qltkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qltkPanelLayout.setVerticalGroup(
            qltkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qltkPanel);

        javax.swing.GroupLayout qlpqPanelLayout = new javax.swing.GroupLayout(qlpqPanel);
        qlpqPanel.setLayout(qlpqPanelLayout);
        qlpqPanelLayout.setHorizontalGroup(
            qlpqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        qlpqPanelLayout.setVerticalGroup(
            qlpqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.add(qlpqPanel);

        javax.swing.GroupLayout qltkePanelLayout = new javax.swing.GroupLayout(qltkePanel);
        qltkePanel.setLayout(qltkePanelLayout);
        qltkePanelLayout.setHorizontalGroup(
            qltkePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1096, Short.MAX_VALUE)
        );
        qltkePanelLayout.setVerticalGroup(
            qltkePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        jPanel7.add(qltkePanel);

        javax.swing.GroupLayout qltgPanelLayout = new javax.swing.GroupLayout(qltgPanel);
        qltgPanel.setLayout(qltgPanelLayout);
        qltgPanelLayout.setHorizontalGroup(
            qltgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1096, Short.MAX_VALUE)
        );
        qltgPanelLayout.setVerticalGroup(
            qltgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        jPanel7.add(qltgPanel);

        javax.swing.GroupLayout qlnxbPanelLayout = new javax.swing.GroupLayout(qlnxbPanel);
        qlnxbPanel.setLayout(qlnxbPanelLayout);
        qlnxbPanelLayout.setHorizontalGroup(
            qlnxbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1096, Short.MAX_VALUE)
        );
        qlnxbPanelLayout.setVerticalGroup(
            qlnxbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        jPanel7.add(qlnxbPanel);

        cancelBtn.setText("Huỷ");
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelBtnMouseClicked(evt);
            }
        });

        createNewBtn.setText("Tạo mới");
        createNewBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createNewBtnMouseClicked(evt);
            }
        });

        saveUpdateBtn.setText("Cập nhật");
        saveUpdateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveUpdateBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(380, Short.MAX_VALUE)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(createNewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(saveUpdateBtn)
                .addGap(362, 362, 362))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(saveUpdateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createNewBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel11);

        jPanel6.add(jPanel7);

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

        jScrollPane2.setViewportView(jPanel3);

        jTabbedPane1.addTab("Thao tác quyền", jScrollPane2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        if (!addBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        resetCheckBoxes(qlbhPanel);
        resetCheckBoxes(qlhdPanel);
        resetCheckBoxes(qlkhPanel);
        resetCheckBoxes(qlnccPanel);
        resetCheckBoxes(qlnhPanel);
        resetCheckBoxes(qlnvPanel);
        resetCheckBoxes(qlpnPanel);
        resetCheckBoxes(qlpqPanel);
        resetCheckBoxes(qlspPanel);
        resetCheckBoxes(qltkPanel);
        resetCheckBoxes(qltkePanel);
        resetCheckBoxes(qltgPanel);
        resetCheckBoxes(qlnxbPanel);
        tenQuyenTxt.setText("");
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_addBtnMouseClicked

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        if (!updateBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        showCurrentNhomQuyenSetting();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_updateBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        if (!deleteBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.deleteItem();
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void createNewBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createNewBtnMouseClicked
        if (!createNewBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        checkSelectedAllCheckBox();
        if (tenQuyenTxt.getText() == null || tenQuyenTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mv, "Tên nhóm quyền không được để trống!", "Có lỗi xảy ra!", JOptionPane.ERROR_MESSAGE);
        } else {
            if (qlbhChecked.isEmpty() && qlhdChecked.isEmpty() && qlkhChecked.isEmpty()
                    && qlnccChecked.isEmpty() && qlnhChecked.isEmpty() && qlnvChecked.isEmpty()
                    && qlpnChecked.isEmpty() && qlpqChecked.isEmpty() && qlspChecked.isEmpty()
                    && qltkChecked.isEmpty() && qltkeChecked.isEmpty() && qltgChecked.isEmpty() && qlnxbChecked.isEmpty()) {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn ít nhất một quyền cho nhóm quyền này!", "Có lỗi xảy ra!", JOptionPane.ERROR_MESSAGE);
            } else {
                String errors = NhomQuyenBUS.getInstance().validateData(tenQuyenTxt.getText());
                if (errors == null || errors.isEmpty()) {
                    NhomQuyenDTO nq = NhomQuyenBUS.getInstance().createNewNhomQuyen(tenQuyenTxt.getText());
                    int result = NhomQuyenBUS.getInstance().add(nq);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(mv, "Thêm nhóm quyền thành công", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                        addToChiTietQuyen(qlbhChecked, nq.getMaNhomQuyen(), "Q1");
                        addToChiTietQuyen(qlhdChecked, nq.getMaNhomQuyen(), "Q2");
                        addToChiTietQuyen(qlkhChecked, nq.getMaNhomQuyen(), "Q3");
                        addToChiTietQuyen(qlnccChecked, nq.getMaNhomQuyen(), "Q4");
                        addToChiTietQuyen(qlnhChecked, nq.getMaNhomQuyen(), "Q5");
                        addToChiTietQuyen(qlnvChecked, nq.getMaNhomQuyen(), "Q6");
                        addToChiTietQuyen(qlpnChecked, nq.getMaNhomQuyen(), "Q7");
                        addToChiTietQuyen(qlpqChecked, nq.getMaNhomQuyen(), "Q8");
                        addToChiTietQuyen(qlspChecked, nq.getMaNhomQuyen(), "Q9");
                        addToChiTietQuyen(qltkChecked, nq.getMaNhomQuyen(), "Q10");
                        addToChiTietQuyen(qltkeChecked, nq.getMaNhomQuyen(), "Q11");
                        addToChiTietQuyen(qltgChecked, nq.getMaNhomQuyen(), "Q12");
                        addToChiTietQuyen(qlnxbChecked, nq.getMaNhomQuyen(), "Q13");

                        resetCheckBoxes(qlbhPanel);
                        resetCheckBoxes(qlhdPanel);
                        resetCheckBoxes(qlkhPanel);
                        resetCheckBoxes(qlnccPanel);
                        resetCheckBoxes(qlnhPanel);
                        resetCheckBoxes(qlnvPanel);
                        resetCheckBoxes(qlpnPanel);
                        resetCheckBoxes(qlpqPanel);
                        resetCheckBoxes(qlspPanel);
                        resetCheckBoxes(qltkPanel);
                        resetCheckBoxes(qltkePanel);
                        resetCheckBoxes(qltgPanel);
                        resetCheckBoxes(qlnxbPanel);

                        tenQuyenTxt.setText("");
                        jTabbedPane1.setSelectedIndex(0);
                        refreshDataNhomQuyen();
                        loadDataNhomQuyen(listNq);
                    }
                } else {
                    JOptionPane.showMessageDialog(mv, errors, "Có lỗi xảy ra!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_createNewBtnMouseClicked

    private void cancelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseClicked
        int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn huỷ thao tác quyền?", "Thông báo!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            resetCheckBoxes(qlbhPanel);
            resetCheckBoxes(qlhdPanel);
            resetCheckBoxes(qlkhPanel);
            resetCheckBoxes(qlnccPanel);
            resetCheckBoxes(qlnhPanel);
            resetCheckBoxes(qlnvPanel);
            resetCheckBoxes(qlpnPanel);
            resetCheckBoxes(qlpqPanel);
            resetCheckBoxes(qlspPanel);
            resetCheckBoxes(qltkPanel);
            resetCheckBoxes(qltkePanel);
            resetCheckBoxes(qltgPanel);
            resetCheckBoxes(qlnxbPanel);

            tenQuyenTxt.setText("");
            jTabbedPane1.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cancelBtnMouseClicked

    private void saveUpdateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveUpdateBtnMouseClicked
        if (!saveUpdateBtn.isEnabled()) {
            JOptionPane.showMessageDialog(mv, "Bạn không đủ quyền hạn thực hiện thao tác này!", "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        checkSelectedAllCheckBox();
        if (tenQuyenTxt.getText() == null || tenQuyenTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mv, "Tên nhóm quyền không được để trống!", "Có lỗi xảy ra!", JOptionPane.ERROR_MESSAGE);
        } else {
            if (qlbhChecked.isEmpty() && qlhdChecked.isEmpty() && qlkhChecked.isEmpty()
                    && qlnccChecked.isEmpty() && qlnhChecked.isEmpty() && qlnvChecked.isEmpty()
                    && qlpnChecked.isEmpty() && qlpqChecked.isEmpty() && qlspChecked.isEmpty()
                    && qltkChecked.isEmpty() && qltkeChecked.isEmpty() && qltgChecked.isEmpty() && qlnxbChecked.isEmpty()) {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn ít nhất một quyền cho nhóm quyền này!", "Có lỗi xảy ra!", JOptionPane.ERROR_MESSAGE);
            } else {
                String errors = NhomQuyenBUS.getInstance().validateData(tenQuyenTxt.getText());
                if (errors != null && !errors.isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(mv, "Bạn có chắc chắn muốn cập nhật lại quyền này?", "Cảnh báo", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        String id = getSelectedItemIdNhomQuyen();
                        ChiTietQuyenDTO ctq = new ChiTietQuyenDTO();
                        ctq.setMaNhomQuyen(id);
                        int result = ChiTietQuyenBUS.getInstance().delete(ctq);
                        if (result > 0) {
                            JOptionPane.showMessageDialog(mv, "Sửa nhóm quyền thành công!", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                            addToChiTietQuyen(qlbhChecked, id, "Q1");
                            addToChiTietQuyen(qlhdChecked, id, "Q2");
                            addToChiTietQuyen(qlkhChecked, id, "Q3");
                            addToChiTietQuyen(qlnccChecked, id, "Q4");
                            addToChiTietQuyen(qlnhChecked, id, "Q5");
                            addToChiTietQuyen(qlnvChecked, id, "Q6");
                            addToChiTietQuyen(qlpnChecked, id, "Q7");
                            addToChiTietQuyen(qlpqChecked, id, "Q8");
                            addToChiTietQuyen(qlspChecked, id, "Q9");
                            addToChiTietQuyen(qltkChecked, id, "Q10");
                            addToChiTietQuyen(qltkeChecked, id, "Q11");
                            addToChiTietQuyen(qltgChecked, id, "Q12");
                            addToChiTietQuyen(qlnxbChecked, id, "Q13");

                            resetCheckBoxes(qlbhPanel);
                            resetCheckBoxes(qlhdPanel);
                            resetCheckBoxes(qlkhPanel);
                            resetCheckBoxes(qlnccPanel);
                            resetCheckBoxes(qlnhPanel);
                            resetCheckBoxes(qlnvPanel);
                            resetCheckBoxes(qlpnPanel);
                            resetCheckBoxes(qlpqPanel);
                            resetCheckBoxes(qlspPanel);
                            resetCheckBoxes(qltkPanel);
                            resetCheckBoxes(qltkePanel);
                            resetCheckBoxes(qltgPanel);
                            resetCheckBoxes(qlnxbPanel);

                            tenQuyenTxt.setText("");
                            jTabbedPane1.setSelectedIndex(0);
                            refreshDataNhomQuyen();
                            loadDataNhomQuyen(listNq);
                        }
                    }
                } else {
                    int confirm = JOptionPane.showConfirmDialog(mv, "Nhóm quyền với tên này chưa tồn tại, có muốn tạo mới?", "Cảnh báo", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        NhomQuyenDTO nq = NhomQuyenBUS.getInstance().createNewNhomQuyen(tenQuyenTxt.getText());
                        int result = NhomQuyenBUS.getInstance().add(nq);
                        if (result > 0) {
                            JOptionPane.showMessageDialog(mv, "Thêm nhóm quyền thành công", "Thành công!", JOptionPane.INFORMATION_MESSAGE);
                            addToChiTietQuyen(qlbhChecked, nq.getMaNhomQuyen(), "Q1");
                            addToChiTietQuyen(qlhdChecked, nq.getMaNhomQuyen(), "Q2");
                            addToChiTietQuyen(qlkhChecked, nq.getMaNhomQuyen(), "Q3");
                            addToChiTietQuyen(qlnccChecked, nq.getMaNhomQuyen(), "Q4");
                            addToChiTietQuyen(qlnhChecked, nq.getMaNhomQuyen(), "Q5");
                            addToChiTietQuyen(qlnvChecked, nq.getMaNhomQuyen(), "Q6");
                            addToChiTietQuyen(qlpnChecked, nq.getMaNhomQuyen(), "Q7");
                            addToChiTietQuyen(qlpqChecked, nq.getMaNhomQuyen(), "Q8");
                            addToChiTietQuyen(qlspChecked, nq.getMaNhomQuyen(), "Q9");
                            addToChiTietQuyen(qltkChecked, nq.getMaNhomQuyen(), "Q10");
                            addToChiTietQuyen(qltkeChecked, nq.getMaNhomQuyen(), "Q11");
                            addToChiTietQuyen(qltgChecked, nq.getMaNhomQuyen(), "Q12");
                            addToChiTietQuyen(qlnxbChecked, nq.getMaNhomQuyen(), "Q13");

                            resetCheckBoxes(qlbhPanel);
                            resetCheckBoxes(qlhdPanel);
                            resetCheckBoxes(qlkhPanel);
                            resetCheckBoxes(qlnccPanel);
                            resetCheckBoxes(qlnhPanel);
                            resetCheckBoxes(qlnvPanel);
                            resetCheckBoxes(qlpnPanel);
                            resetCheckBoxes(qlpqPanel);
                            resetCheckBoxes(qlspPanel);
                            resetCheckBoxes(qltkPanel);
                            resetCheckBoxes(qltkePanel);
                            resetCheckBoxes(qltgPanel);
                            resetCheckBoxes(qlnxbPanel);

                            tenQuyenTxt.setText("");
                            jTabbedPane1.setSelectedIndex(0);
                            refreshDataNhomQuyen();
                            loadDataNhomQuyen(listNq);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_saveUpdateBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton createNewBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JPanel filterByPricePanel;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneNq;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable nhomQuyenTbl;
    private javax.swing.JPanel qlbhPanel;
    private javax.swing.JPanel qlhdPanel;
    private javax.swing.JPanel qlkhPanel;
    private javax.swing.JPanel qlnccPanel;
    private javax.swing.JPanel qlnhPanel;
    private javax.swing.JPanel qlnvPanel;
    private javax.swing.JPanel qlnxbPanel;
    private javax.swing.JPanel qlpnPanel;
    private javax.swing.JPanel qlpqPanel;
    private javax.swing.JPanel qlspPanel;
    private javax.swing.JPanel qltgPanel;
    private javax.swing.JPanel qltkPanel;
    private javax.swing.JPanel qltkePanel;
    private javax.swing.JButton saveUpdateBtn;
    private javax.swing.JTextField tenQuyenTxt;
    private javax.swing.JPanel toolBar2;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

}
