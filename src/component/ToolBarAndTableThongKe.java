/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bus.ThongKeBUS;
import dto.ThongKeDoanhThuDTO;
import dto.ThongKeHieuSuatNhanVienDTO;
import dto.ThongKeSachBanDuocDTO;
import gui.MainView;
import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import java.text.NumberFormat;
import java.util.Locale;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 *
 * @author mansh
 */
public class ToolBarAndTableThongKe extends javax.swing.JPanel {

    private ArrayList<ThongKeDoanhThuDTO> tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
    private ArrayList<ThongKeHieuSuatNhanVienDTO> tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
    private ArrayList<ThongKeSachBanDuocDTO> tkttsachList;
    private MainView mv;

    public ArrayList<ThongKeDoanhThuDTO> getTkdtList() {
        return tkdtList;
    }

    public ToolBarAndTableThongKe(MainView mv) {
        initComponents();
        this.mv = mv;
        DTFilterBtnGr.add(filterDTByDayRadio);
        DTFilterBtnGr.add(filterDTByQuyRadio);
        DTFilterBtnGr.add(filterDTByYearRadio);
        filterDTByDayRadio.setSelected(true);
        quyLbl.setEnabled(false);
        quyCbx.setEnabled(false);

        ArrayList<String> allYearHoaDon = ThongKeBUS.getInstance().getAllHoaDonYear();
        for (String year : allYearHoaDon) {
            yearCbx.addItem(year);
        }

        quyCbx.addItem("1");
        quyCbx.addItem("2");
        quyCbx.addItem("3");
        quyCbx.addItem("4");

        loadDataThongKeDoanhThuByNgay(tkdtList);
        loadDataThongHSNVByNgay(tkhsnvList);
    }

    public void loadDataThongKeDoanhThuByNgay(ArrayList<ThongKeDoanhThuDTO> listTKDT) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Ngày bán");
        defaultTableModel.addColumn("Tổng số hoá đơn");
        defaultTableModel.addColumn("Tổng số sách bán ra");
        defaultTableModel.addColumn("Tổng doanh thu");

        for (ThongKeDoanhThuDTO tk : listTKDT) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongDoanhThu());
            defaultTableModel.addRow(new Object[]{
                tk.getNgayBanHang(),
                tk.getTongSoHoaDon(),
                tk.getTongSoSachBanRa(),
                formattedTongDoanhThu});
        }
        doanhThuTbl.setModel(defaultTableModel);
        doanhThuTbl.setRowHeight(50);
        doanhThuTbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataThongKeDoanhThuByQuy(ArrayList<ThongKeDoanhThuDTO> listTKDT) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Quý");
        defaultTableModel.addColumn("Tổng số hoá đơn");
        defaultTableModel.addColumn("Tổng số sách bán ra");
        defaultTableModel.addColumn("Tổng doanh thu");

        for (ThongKeDoanhThuDTO tk : listTKDT) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongDoanhThu());
            defaultTableModel.addRow(new Object[]{
                tk.getNgayBanHang(),
                tk.getTongSoHoaDon(),
                tk.getTongSoSachBanRa(),
                formattedTongDoanhThu});
        }
        doanhThuTbl.setModel(defaultTableModel);
        doanhThuTbl.setRowHeight(50);
        doanhThuTbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataThongKeDoanhThuByNam(ArrayList<ThongKeDoanhThuDTO> listTKDT) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Năm");
        defaultTableModel.addColumn("Tổng số hoá đơn");
        defaultTableModel.addColumn("Tổng số sách bán ra");
        defaultTableModel.addColumn("Tổng doanh thu");

        for (ThongKeDoanhThuDTO tk : listTKDT) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongDoanhThu());
            defaultTableModel.addRow(new Object[]{
                tk.getNgayBanHang(),
                tk.getTongSoHoaDon(),
                tk.getTongSoSachBanRa(),
                formattedTongDoanhThu});
        }
        doanhThuTbl.setModel(defaultTableModel);
        doanhThuTbl.setRowHeight(50);
        doanhThuTbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataThongHSNVByNgay(ArrayList<ThongKeHieuSuatNhanVienDTO> listTKHSNV) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã NV");
        defaultTableModel.addColumn("Tên NV");
        defaultTableModel.addColumn("Ngày lập");
        defaultTableModel.addColumn("Số lượng hoá đơn");
        defaultTableModel.addColumn("Tổng doanh thu ");
        defaultTableModel.addColumn("Số sách bán được");

        for (ThongKeHieuSuatNhanVienDTO tk : listTKHSNV) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongDoanhThu());
            defaultTableModel.addRow(new Object[]{
                tk.getMaNV(),
                tk.getTenNV(),
                tk.getNgayLap(),
                tk.getSoLuongHoaDon(),
                formattedTongDoanhThu,
                tk.getSoSachBanDuoc()});
        }
        hieuSuatNVTbl.setModel(defaultTableModel);
        hieuSuatNVTbl.setRowHeight(50);
        hieuSuatNVTbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataThongHSNVByQuy(ArrayList<ThongKeHieuSuatNhanVienDTO> listTKHSNV) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã NV");
        defaultTableModel.addColumn("Tên NV");
        defaultTableModel.addColumn("Quý lập");
        defaultTableModel.addColumn("Số lượng hoá đơn");
        defaultTableModel.addColumn("Tổng doanh thu ");
        defaultTableModel.addColumn("Số sách bán được");

        for (ThongKeHieuSuatNhanVienDTO tk : listTKHSNV) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongDoanhThu());
            defaultTableModel.addRow(new Object[]{
                tk.getMaNV(),
                tk.getTenNV(),
                tk.getNgayLap(),
                tk.getSoLuongHoaDon(),
                formattedTongDoanhThu,
                tk.getSoSachBanDuoc()});
        }
        hieuSuatNVTbl.setModel(defaultTableModel);
        hieuSuatNVTbl.setRowHeight(50);
        hieuSuatNVTbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataThongHSNVByNam(ArrayList<ThongKeHieuSuatNhanVienDTO> listTKHSNV) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã NV");
        defaultTableModel.addColumn("Tên NV");
        defaultTableModel.addColumn("Năm lập");
        defaultTableModel.addColumn("Số lượng hoá đơn");
        defaultTableModel.addColumn("Tổng doanh thu ");
        defaultTableModel.addColumn("Số sách bán được");

        for (ThongKeHieuSuatNhanVienDTO tk : listTKHSNV) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongDoanhThu());
            defaultTableModel.addRow(new Object[]{
                tk.getMaNV(),
                tk.getTenNV(),
                tk.getNgayLap(),
                tk.getSoLuongHoaDon(),
                formattedTongDoanhThu,
                tk.getSoSachBanDuoc()});
        }
        hieuSuatNVTbl.setModel(defaultTableModel);
        hieuSuatNVTbl.setRowHeight(50);
        hieuSuatNVTbl.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDataThongKeSBD(ArrayList<ThongKeSachBanDuocDTO> listTKSBD) {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.addColumn("Mã Sách");
        defaultTableModel.addColumn("Tên Sách");
        defaultTableModel.addColumn("Số lượng bán được");
        defaultTableModel.addColumn("Tổng tiền");

        for (ThongKeSachBanDuocDTO tk : listTKSBD) {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
            String formattedTongDoanhThu = currencyFormat.format(tk.getTongTienMangLai());
            defaultTableModel.addRow(new Object[]{
                tk.getMaSach(),
                tk.getTenSach(),
                tk.getSoLuongBanDuoc(),
                formattedTongDoanhThu});
        }
        tieuThuSachTbl.setModel(defaultTableModel);
        tieuThuSachTbl.setRowHeight(50);
        tieuThuSachTbl.getTableHeader().setReorderingAllowed(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DTFilterBtnGr = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        containerFilter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        yearCbx = new javax.swing.JComboBox<>();
        filterDTByYearRadio = new javax.swing.JRadioButton();
        filterDTByQuyRadio = new javax.swing.JRadioButton();
        filterDTByDayRadio = new javax.swing.JRadioButton();
        thongKeBtn = new javax.swing.JButton();
        quyLbl = new javax.swing.JLabel();
        quyCbx = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        doanhThuTbl = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        hieuSuatNVTbl = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tieuThuSachTbl = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        containerFilter.setPreferredSize(new java.awt.Dimension(1206, 100));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText(" Chọn năm:");

        yearCbx.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        yearCbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định" }));
        yearCbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearCbxActionPerformed(evt);
            }
        });

        filterDTByYearRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterDTByYearRadio.setText("Theo năm");
        filterDTByYearRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterDTByYearRadioMouseClicked(evt);
            }
        });
        filterDTByYearRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterDTByYearRadioActionPerformed(evt);
            }
        });

        filterDTByQuyRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterDTByQuyRadio.setText("Theo quý");
        filterDTByQuyRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterDTByQuyRadioMouseClicked(evt);
            }
        });

        filterDTByDayRadio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filterDTByDayRadio.setText("Theo ngày");
        filterDTByDayRadio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterDTByDayRadioItemStateChanged(evt);
            }
        });
        filterDTByDayRadio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                filterDTByDayRadioStateChanged(evt);
            }
        });
        filterDTByDayRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterDTByDayRadioMouseClicked(evt);
            }
        });

        thongKeBtn.setBackground(new java.awt.Color(153, 255, 102));
        thongKeBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        thongKeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/chart.png"))); // NOI18N
        thongKeBtn.setText("Xem biểu đồ");
        thongKeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongKeBtnMouseClicked(evt);
            }
        });

        quyLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        quyLbl.setText("Chọn quý");

        quyCbx.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        quyCbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định" }));
        quyCbx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quyCbxMouseClicked(evt);
            }
        });
        quyCbx.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                quyCbxInputMethodTextChanged(evt);
            }
        });
        quyCbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quyCbxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containerFilterLayout = new javax.swing.GroupLayout(containerFilter);
        containerFilter.setLayout(containerFilterLayout);
        containerFilterLayout.setHorizontalGroup(
            containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFilterLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerFilterLayout.createSequentialGroup()
                        .addComponent(filterDTByYearRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 34, 34)
                        .addComponent(filterDTByQuyRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(containerFilterLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearCbx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)))
                .addGroup(containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerFilterLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(filterDTByDayRadio)
                        .addGap(212, 212, 212))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFilterLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quyLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quyCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)))
                .addComponent(thongKeBtn)
                .addGap(539, 539, 539))
        );
        containerFilterLayout.setVerticalGroup(
            containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFilterLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quyLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(yearCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(quyCbx)))
                .addGap(19, 19, 19)
                .addGroup(containerFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterDTByYearRadio)
                    .addComponent(filterDTByQuyRadio)
                    .addComponent(filterDTByDayRadio))
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFilterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(thongKeBtn)
                .addGap(19, 19, 19))
        );

        jScrollPane1.setViewportView(containerFilter);

        add(jScrollPane1, java.awt.BorderLayout.NORTH);

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        doanhThuTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(doanhThuTbl);

        jTabbedPane1.addTab("Thống kê doanh thu", jScrollPane2);

        hieuSuatNVTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(hieuSuatNVTbl);

        jTabbedPane1.addTab("Thống kê hiệu suất NV", jScrollPane3);

        tieuThuSachTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Số lượng bán được", "Tổng tiền"
            }
        ));
        jScrollPane5.setViewportView(tieuThuSachTbl);

        jTabbedPane1.addTab("Thống kê tiêu thụ sách", jScrollPane5);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void filterDTByYearRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterDTByYearRadioActionPerformed

    }//GEN-LAST:event_filterDTByYearRadioActionPerformed

    private void thongKeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongKeBtnMouseClicked
        if (jTabbedPane1.getSelectedIndex() == 0) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Determine which radio button is selected and prepare data accordingly
            if (filterDTByDayRadio.isSelected()) {
                for (ThongKeDoanhThuDTO tkdt : tkdtList) {
                    dataset.addValue(tkdt.getTongDoanhThu(), "Doanh Thu theo ngày", tkdt.getNgayBanHang());
                }
            } else if (filterDTByQuyRadio.isSelected()) {
                for (ThongKeDoanhThuDTO tkdt : tkdtList) {
                    dataset.addValue(tkdt.getTongDoanhThu(), "Doanh Thu theo quý", "Quý " + tkdt.getNgayBanHang());
                }
            } else if (filterDTByYearRadio.isSelected()) {
                for (ThongKeDoanhThuDTO tkdt : tkdtList) {
                    dataset.addValue(tkdt.getTongDoanhThu(), "Doanh Thu theo năm", "Năm " + tkdt.getNgayBanHang());
                }
            }

            // Create the chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Thống Kê Doanh Thu", // Chart title
                    "Thời Gian", // Domain axis label
                    "Doanh Thu", // Range axis label
                    dataset // Data
            );

            CategoryPlot plot = chart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.BLACK);

            // Custom color renderer
//        plot.setRenderer(new CustomBarRenderer(dataset));
            // Create and display the chart frame
            ChartFrame frame = new ChartFrame("Biểu Đồ Thống Kê Doanh Thu", chart);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Determine which radio button is selected and prepare data accordingly
            if (filterDTByDayRadio.isSelected()) {
                for (ThongKeHieuSuatNhanVienDTO tkhsnv : tkhsnvList) {
                    dataset.addValue(tkhsnv.getTongDoanhThu(), "Doanh thu của nhân viên theo ngày", tkhsnv.getMaNV());
                }
            } else if (filterDTByQuyRadio.isSelected()) {
                for (ThongKeHieuSuatNhanVienDTO tkhsnv : tkhsnvList) {
                    dataset.addValue(tkhsnv.getTongDoanhThu(), "Doanh thu của nhân viên theo quý", tkhsnv.getMaNV());
                }
            } else if (filterDTByYearRadio.isSelected()) {
                for (ThongKeHieuSuatNhanVienDTO tkhsnv : tkhsnvList) {
                    dataset.addValue(tkhsnv.getTongDoanhThu(), "Doanh thu của nhân viên theo năm", tkhsnv.getMaNV());
                }
            }

            // Create the chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Thống Kê Hiệu Suất Nhân Viên", // Chart title
                    "Mã Nhân Viên", // Domain axis label
                    "Doanh Thu", // Range axis label
                    dataset // Data
            );

            CategoryPlot plot = chart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.BLACK);

            // Custom color renderer
            // plot.setRenderer(new CustomBarRenderer(dataset));
            // Create and display the chart frame
            ChartFrame frame = new ChartFrame("Biểu Đồ Thống Kê Hiệu Suất Nhân Viên", chart);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else if (jTabbedPane1.getSelectedIndex() == 2) {
            if (!this.tkttsachList.isEmpty() && this.tkttsachList != null) {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                // Determine which radio button is selected and prepare data accordingly
                if (filterDTByQuyRadio.isSelected()) {
                    for (ThongKeSachBanDuocDTO tksbd : tkttsachList) {
                        dataset.addValue(tksbd.getSoLuongBanDuoc(), "Sách bán được theo quý", tksbd.getMaSach());
                    }
                } else if (filterDTByYearRadio.isSelected()) {
                    for (ThongKeSachBanDuocDTO tksbd : tkttsachList) {
                        dataset.addValue(tksbd.getSoLuongBanDuoc(), "Sách bán được theo năm", tksbd.getMaSach());
                    }
                }

                // Create the chart
                JFreeChart chart = ChartFactory.createBarChart(
                        "Thống Kê Sách Bán Được", // Chart title
                        "Mã sách", // Domain axis label
                        "Số Lượng Sách", // Range axis label
                        dataset // Data
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.setRangeGridlinePaint(Color.BLACK);

                // Custom color renderer
                // plot.setRenderer(new CustomBarRenderer(dataset));
                // Create and display the chart frame
                ChartFrame frame = new ChartFrame("Biểu Đồ Thống Kê Hiệu Suất Nhân Viên", chart);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }
    }//GEN-LAST:event_thongKeBtnMouseClicked

    class CustomBarRenderer extends BarRenderer {

        private final DefaultCategoryDataset dataset;

        public CustomBarRenderer(DefaultCategoryDataset dataset) {
            this.dataset = dataset;
        }

        @Override
        public Paint getItemPaint(int row, int column) {
            // Generate a random color for each bar
            float hue = (float) column / (float) dataset.getColumnCount();
            return Color.getHSBColor(hue, 0.9f, 0.9f);
        }
    }
    private void filterDTByDayRadioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_filterDTByDayRadioStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_filterDTByDayRadioStateChanged

    private void filterDTByDayRadioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterDTByDayRadioItemStateChanged

    }//GEN-LAST:event_filterDTByDayRadioItemStateChanged

    private void filterDTByDayRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterDTByDayRadioMouseClicked
        if (filterDTByDayRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 0) {
            tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
            loadDataThongKeDoanhThuByNgay(tkdtList);
            yearCbx.setSelectedIndex(0);
        } else if (filterDTByDayRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 1) {
            tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
            loadDataThongHSNVByNgay(tkhsnvList);
            yearCbx.setSelectedIndex(0);
            quyCbx.setSelectedIndex(0);
        }
    }//GEN-LAST:event_filterDTByDayRadioMouseClicked

    private void filterDTByQuyRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterDTByQuyRadioMouseClicked
        if (filterDTByQuyRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 0) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
                    loadDataThongKeDoanhThuByNgay(tkdtList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }
            tkdtList = ThongKeBUS.getInstance().thongKeTheoQuy(year);
            loadDataThongKeDoanhThuByQuy(tkdtList);
        } else if (filterDTByQuyRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 1) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    quyCbx.setSelectedIndex(0);
                }
                return;
            }
            String quy = "";
            if (quyCbx.getSelectedIndex() > 0) {
                quy = (String) quyCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn quý trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }

            tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoQuyTrongNam(year, quy);
            loadDataThongHSNVByQuy(tkhsnvList);
        } else if (filterDTByQuyRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 2) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    quyCbx.setSelectedIndex(0);
                }
                return;
            }
            String quy = "";
            if (quyCbx.getSelectedIndex() > 0) {
                quy = (String) quyCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn quý trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }

            tkttsachList = ThongKeBUS.getInstance().thongKeSachBanDuocTheoQuy(year, quy);
            loadDataThongKeSBD(tkttsachList);
        }
    }//GEN-LAST:event_filterDTByQuyRadioMouseClicked

    private void filterDTByYearRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterDTByYearRadioMouseClicked
        if (filterDTByYearRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 0) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
                    loadDataThongKeDoanhThuByNgay(tkdtList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }
            tkdtList = ThongKeBUS.getInstance().thongKeTheoNam(year);
            loadDataThongKeDoanhThuByNam(tkdtList);
        } else if (filterDTByYearRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 1) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }
            tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNam(year);
            loadDataThongHSNVByNgay(tkhsnvList);
        } else if (filterDTByYearRadio.isSelected() && jTabbedPane1.getSelectedIndex() == 2) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByYearRadio.setSelected(false);
                filterDTByYearRadio.getParent().revalidate();
                filterDTByYearRadio.getParent().repaint();
                return;
            }
            tkttsachList = ThongKeBUS.getInstance().thongKeSachBanDuocTheoNam(year);
            loadDataThongKeSBD(tkttsachList);
        }
    }//GEN-LAST:event_filterDTByYearRadioMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        if (jTabbedPane1.getSelectedIndex() == 0) {
            filterDTByDayRadio.doClick();
            quyLbl.setEnabled(false);
            quyCbx.setEnabled(false);
            filterDTByDayRadio.setEnabled(true);
            tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
            loadDataThongKeDoanhThuByNgay(tkdtList);
            yearCbx.setSelectedIndex(0);
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            filterDTByDayRadio.doClick();
            tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
            loadDataThongHSNVByNgay(tkhsnvList);
            quyLbl.setEnabled(true);
            quyCbx.setEnabled(true);
            filterDTByDayRadio.setEnabled(true);
            quyCbx.setSelectedIndex(0);
            yearCbx.setSelectedIndex(0);
        } else if (jTabbedPane1.getSelectedIndex() == 2) {
            filterDTByDayRadio.setSelected(false);
            filterDTByQuyRadio.setSelected(false);
            filterDTByYearRadio.setSelected(false);
            filterDTByDayRadio.setEnabled(false);
            quyLbl.setEnabled(true);
            quyCbx.setEnabled(true);
            quyCbx.setSelectedIndex(0);
            yearCbx.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void quyCbxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quyCbxMouseClicked

    }//GEN-LAST:event_quyCbxMouseClicked

    private void quyCbxInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_quyCbxInputMethodTextChanged

    }//GEN-LAST:event_quyCbxInputMethodTextChanged

    private void quyCbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quyCbxActionPerformed
        int selectedIndex = quyCbx.getSelectedIndex();
        if (selectedIndex != 0 && filterDTByQuyRadio.isSelected()) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    quyCbx.setSelectedIndex(0);
                }
                return;
            }
            String quy = "";
            if (quyCbx.getSelectedIndex() > 0) {
                quy = (String) quyCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn quý trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }

            if (jTabbedPane1.getSelectedIndex() == 1) {
                tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoQuyTrongNam(year, quy);
                loadDataThongHSNVByQuy(tkhsnvList);
            } else if (jTabbedPane1.getSelectedIndex() == 2) {
                tkttsachList = ThongKeBUS.getInstance().thongKeSachBanDuocTheoQuy(year, quy);
                loadDataThongKeSBD(tkttsachList);
            }
        }
    }//GEN-LAST:event_quyCbxActionPerformed

    private void yearCbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearCbxActionPerformed
        if (jTabbedPane1.getSelectedIndex() == 0) {
            if (filterDTByYearRadio.isSelected()) {
                String year = "";
                if (yearCbx.getSelectedIndex() > 0) {
                    year = (String) yearCbx.getSelectedItem();
                } else {
                    JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    filterDTByDayRadio.doClick();
                    if (filterDTByDayRadio.isSelected()) {
                        tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
                        loadDataThongKeDoanhThuByNgay(tkdtList);
                        yearCbx.setSelectedIndex(0);
                    }
                    return;
                }
                tkdtList = ThongKeBUS.getInstance().thongKeTheoNam(year);
                loadDataThongKeDoanhThuByNam(tkdtList);
            } else if (filterDTByQuyRadio.isSelected()) {
                String year = "";
                if (yearCbx.getSelectedIndex() > 0) {
                    year = (String) yearCbx.getSelectedItem();
                } else {
                    JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    filterDTByDayRadio.doClick();
                    if (filterDTByDayRadio.isSelected()) {
                        tkdtList = ThongKeBUS.getInstance().thongKeTheoNgay();
                        loadDataThongKeDoanhThuByNgay(tkdtList);
                        yearCbx.setSelectedIndex(0);
                    }
                    return;
                }
                tkdtList = ThongKeBUS.getInstance().thongKeTheoQuy(year);
                loadDataThongKeDoanhThuByQuy(tkdtList);
            }
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            if (filterDTByYearRadio.isSelected()) {
                String year = "";
                if (yearCbx.getSelectedIndex() > 0) {
                    year = (String) yearCbx.getSelectedItem();
                } else {
                    JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    filterDTByDayRadio.doClick();
                    if (filterDTByDayRadio.isSelected()) {
                        tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                        loadDataThongHSNVByNgay(tkhsnvList);
                        yearCbx.setSelectedIndex(0);
                    }
                    return;
                }
                quyCbx.setSelectedIndex(0);
                tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNam(year);
                loadDataThongHSNVByNgay(tkhsnvList);
            }
        } else if (jTabbedPane1.getSelectedIndex() == 2) {
            String year = "";
            if (yearCbx.getSelectedIndex() > 0) {
                year = (String) yearCbx.getSelectedItem();
            } else {
                JOptionPane.showMessageDialog(mv, "Bạn phải chọn năm trước!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                filterDTByDayRadio.doClick();
                if (filterDTByDayRadio.isSelected()) {
                    tkhsnvList = ThongKeBUS.getInstance().thongKeHieuSuatNVTheoNgay();
                    loadDataThongHSNVByNgay(tkhsnvList);
                    yearCbx.setSelectedIndex(0);
                }
                return;
            }
            tkttsachList = ThongKeBUS.getInstance().thongKeSachBanDuocTheoNam(year);
            loadDataThongKeSBD(tkttsachList);
        }
    }//GEN-LAST:event_yearCbxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup DTFilterBtnGr;
    private javax.swing.JPanel containerFilter;
    private javax.swing.JTable doanhThuTbl;
    private javax.swing.JRadioButton filterDTByDayRadio;
    private javax.swing.JRadioButton filterDTByQuyRadio;
    private javax.swing.JRadioButton filterDTByYearRadio;
    private javax.swing.JTable hieuSuatNVTbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> quyCbx;
    private javax.swing.JLabel quyLbl;
    private javax.swing.JButton thongKeBtn;
    private javax.swing.JTable tieuThuSachTbl;
    private javax.swing.JComboBox<String> yearCbx;
    // End of variables declaration//GEN-END:variables
}
