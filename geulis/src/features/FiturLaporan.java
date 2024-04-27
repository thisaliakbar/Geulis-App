/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.TableAction;
import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ModelDetailPemeriksaan;
import model.ModelHeaderTable;
import model.ModelPemeriksaan;
import model.ModelRenderTable;
import model.ModelPasien;
import swing.TableCellActionRender;
import swing.TableCellEditor;
import service.ServiceRiwayatPasien;

/**
 *
 * @author usER
 */
public class FiturLaporan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private DefaultTableModel tabmodel;
    private TableAction action;
    private DateChooser dateChooser;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private ServiceRiwayatPasien serviceRiwayat = new ServiceRiwayatPasien();
    public FiturLaporan() {
        initComponents();
        initation();
        styleTable(scrollPemeriksaan, tablePemeriksaan, 13);
        styleTable(scrollPenjualan, tablePenjualan, 5);
        styleTable(scrollPemesanan, tablePemesanan, 5);
        changeType();
        rowSorter = new TableRowSorter<>(tabmodel);
        cariData();
    }
    
//  Style Table
    private void styleTable(JScrollPane scroll, JTable table, int columnTable) {
        scroll.getViewport().setBackground(new Color(255,255,255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.setBorder(new EmptyBorder(5,10,5,10));
        table.setRowHeight(40);        
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(columnTable));
    }
    
    //  Update,Delete,Detail
    private void actionRenderTable(JTable table, int columnIndex) {
        action = new TableAction() {
        @Override
        public void edit(int row) {
            
        }

        @Override
        public void delete(int row) {
            if(table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
        }

        @Override
        public void view(int row) {
            detailRiwayat(row);
        }
    };        
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(new TableCellActionRender(false, false, true));
        table.getColumnModel().getColumn(columnIndex).setCellEditor(new TableCellEditor(action, false, false, true));
    }
        
    private void detailRiwayat(int row) {
        ModelDetailPemeriksaan detailPemeriksaan = new ModelDetailPemeriksaan();
        ModelPemeriksaan modelPemeriksaan = new ModelPemeriksaan();
        ModelPasien modelPasien = new ModelPasien();
        String noPemeriksaan = (String) tablePemeriksaan.getValueAt(row, 0);
        String idPasien = (String) tablePemeriksaan.getValueAt(row, 1);
        String namaPasien = (String) tablePemeriksaan.getValueAt(row, 2);
        String tgl = (String) tablePemeriksaan.getValueAt(row, 3);
        
        modelPemeriksaan.setNoPemeriksaan(noPemeriksaan);
        modelPasien.setIdPasien(idPasien);
        modelPasien.setNama(namaPasien);
        modelPemeriksaan.setTglPemeriksaan(tgl);
        modelPemeriksaan.setModelPasien(modelPasien);
        detailPemeriksaan.setModelPemeriksaan(modelPemeriksaan);
        
        DialogDetail detail = new DialogDetail(null, true, "Slide-2", detailPemeriksaan, null, null);
        detail.setVisible(true);
    }
    
    private void cariData() {
        txtCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
              String text = txtCari.getText();
              if(text.length() == 0) {
                  rowSorter.setRowFilter(null);
              } else {
                  rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
              }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
              String text = txtCari.getText();
              if(text.length() == 0) {
                  rowSorter.setRowFilter(null);
              } else {
                  rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
              }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              
            }
        });
    }
    
    private void changePanelTable(JPanel panel) {
        panel3.removeAll();
        panel3.add(panel);
        panel3.repaint();
        panel3.revalidate();
    }
    
    private void initation() {
        cbxJenisLaporan.setBackground(new Color(255,255,255));
        cbxJenisLaporan.setForeground(new Color(0,0,0));
        cbxJenisLaporan.setFont(new Font("sansserif",0,20));
        cbxUrutan.setBackground(new Color(255,255,255));
        cbxUrutan.setForeground(new Color(0,0,0));
        cbxUrutan.setFont(new Font("sansserif",0,20));
        String[] types = new String[]{"Laporan Pemeriksaan","Laporan Penjualan","Laporan Pemesanan"};
        String[] orders = new String[]{"Terbaru","Terlama"};
        
        for(String type : types) {
            cbxJenisLaporan.addItem(type);
        }
        
        for(String order : orders) {
            cbxUrutan.addItem(order);
        }
        
        dateChooser = new DateChooser();
        dateChooser.setTextField(txtTgl);
        dateChooser.setBetweenCharacter(" Sampai ");
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        dateChooser.setSelectedDate(new Date());
        dateChooser.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fromDate = sdf.format(date.getFromDate());
                String toDate = sdf.format(date.getToDate());
                String Date = "From " + fromDate + " To " + toDate;
            }
           
        });
    }
    
    private void changeType() { 
        cbxJenisLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int index = cbxJenisLaporan.getSelectedIndex();
               switch(index) {
                   case 0:
                       changePanelTable(panelPemeriksaan);
                       tabmodel = (DefaultTableModel) tablePemeriksaan.getModel();
                       tablePemeriksaan.setRowSorter(rowSorter);
                       actionRenderTable(tablePemeriksaan, 13);
                       txtCari.setText("Cari Berdasarkan No Pemeriksaan");
                       lbRp.setVisible(true);
                       lbTotal.setVisible(true);
                       break;
                   case 1:
                       changePanelTable(panelPenjualan);
                       tabmodel = (DefaultTableModel) tablePenjualan.getModel();
                       tablePenjualan.setRowSorter(rowSorter);
                       txtCari.setText("Cari Berdasarkan No Penjualan");
                       lbRp.setVisible(true);
                       lbTotal.setVisible(true);
                       break;
                   case 2:
                       changePanelTable(panelPemesanan);
                       tabmodel = (DefaultTableModel) tablePemesanan.getModel();
                       tablePemesanan.setRowSorter(rowSorter);
                       txtCari.setText("Cari Berdasarkan No Pemesanan");
                       lbRp.setVisible(true);
                       lbTotal.setVisible(true);
                       break;
               }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelData = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        label = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        cbxJenisLaporan = new javax.swing.JComboBox<>();
        cbxUrutan = new javax.swing.JComboBox<>();
        txtTgl = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lbRp = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        panelPemeriksaan = new javax.swing.JPanel();
        scrollPemeriksaan = new javax.swing.JScrollPane();
        tablePemeriksaan = new javax.swing.JTable();
        panelPenjualan = new javax.swing.JPanel();
        scrollPenjualan = new javax.swing.JScrollPane();
        tablePenjualan = new javax.swing.JTable();
        panelPemesanan = new javax.swing.JPanel();
        scrollPemesanan = new javax.swing.JScrollPane();
        tablePemesanan = new swing.Table();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        mainPanel.setBackground(new java.awt.Color(185, 185, 185));
        mainPanel.setForeground(new java.awt.Color(255, 255, 255));

        label.setBackground(new java.awt.Color(135, 15, 50));
        label.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("LAPORAN");
        label.setOpaque(true);

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        panel1.setForeground(new java.awt.Color(255, 255, 255));

        cbxJenisLaporan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        cbxUrutan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtTgl.setBackground(new java.awt.Color(255, 255, 255));
        txtTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtTgl.setForeground(new java.awt.Color(0, 0, 0));
        txtTgl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        jLabel1.setBackground(new java.awt.Color(135, 15, 50));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FILTER");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxJenisLaporan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxUrutan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTgl))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbxJenisLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxUrutan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan No Pemeriksaan");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printer.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setOpaque(false);

        lbRp.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lbRp.setForeground(new java.awt.Color(135, 15, 50));
        lbRp.setText("Rp");

        lbTotal.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(135, 15, 50));
        lbTotal.setText("0");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lbRp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(74, 74, 74))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbRp)
                        .addComponent(lbTotal))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel3.setBackground(new java.awt.Color(255, 255, 255));
        panel3.setLayout(new java.awt.CardLayout());

        panelPemeriksaan.setBackground(new java.awt.Color(255, 255, 255));

        scrollPemeriksaan.setBackground(new java.awt.Color(255, 255, 255));
        scrollPemeriksaan.setBorder(null);
        scrollPemeriksaan.setOpaque(false);

        tablePemeriksaan.setBackground(new java.awt.Color(255, 255, 255));
        tablePemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablePemeriksaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemeriksaan", "No Reservasi", "ID Pasien", "Nama Pasien", "ID Karyawan", "Tanggal Pemeriksaan", "Total", "Deskripsi", "Bayar", "Kembalian", "Jenis Pembayaran", "ID Pengguna", "Nama Pengguna", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePemeriksaan.setGridColor(new java.awt.Color(185, 185, 185));
        tablePemeriksaan.setOpaque(false);
        tablePemeriksaan.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPemeriksaan.setViewportView(tablePemeriksaan);
        if (tablePemeriksaan.getColumnModel().getColumnCount() > 0) {
            tablePemeriksaan.getColumnModel().getColumn(2).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(2).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(4).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(4).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(7).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(7).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(8).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(8).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(9).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(9).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(10).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(10).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(11).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(11).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(11).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(12).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(12).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        javax.swing.GroupLayout panelPemeriksaanLayout = new javax.swing.GroupLayout(panelPemeriksaan);
        panelPemeriksaan.setLayout(panelPemeriksaanLayout);
        panelPemeriksaanLayout.setHorizontalGroup(
            panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 793, Short.MAX_VALUE)
            .addGroup(panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE))
        );
        panelPemeriksaanLayout.setVerticalGroup(
            panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPemeriksaan, "card2");

        panelPenjualan.setBackground(new java.awt.Color(255, 255, 255));

        scrollPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        scrollPenjualan.setBorder(null);
        scrollPenjualan.setOpaque(false);

        tablePenjualan.setBackground(new java.awt.Color(255, 255, 255));
        tablePenjualan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablePenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Penjualan", "Pengguna", "Tanggal", "Total", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePenjualan.setGridColor(new java.awt.Color(185, 185, 185));
        tablePenjualan.setOpaque(false);
        tablePenjualan.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPenjualan.setViewportView(tablePenjualan);

        javax.swing.GroupLayout panelPenjualanLayout = new javax.swing.GroupLayout(panelPenjualan);
        panelPenjualan.setLayout(panelPenjualanLayout);
        panelPenjualanLayout.setHorizontalGroup(
            panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 793, Short.MAX_VALUE)
            .addGroup(panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE))
        );
        panelPenjualanLayout.setVerticalGroup(
            panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPenjualan, "card2");

        panelPemesanan.setBackground(new java.awt.Color(255, 255, 255));

        tablePemesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemesanan", "ID Supplier", "Supplier", "Tanggal Pemesanan", "Total", "Bayar", "Kembali", "Jenis Pembayaran", "Status", "", "ID Pengguna", "Pengguna", "        Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePemesanan.setSelectionBackground(new java.awt.Color(245, 245, 245));
        tablePemesanan.setSelectionForeground(new java.awt.Color(0, 0, 0));
        scrollPemesanan.setViewportView(tablePemesanan);
        if (tablePemesanan.getColumnModel().getColumnCount() > 0) {
            tablePemesanan.getColumnModel().getColumn(1).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(1).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(5).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(5).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(6).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(6).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(7).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(7).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(9).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(9).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(10).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(10).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(11).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(11).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        javax.swing.GroupLayout panelPemesananLayout = new javax.swing.GroupLayout(panelPemesanan);
        panelPemesanan.setLayout(panelPemesananLayout);
        panelPemesananLayout.setHorizontalGroup(
            panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 793, Short.MAX_VALUE)
            .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE))
        );
        panelPemesananLayout.setVerticalGroup(
            panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPemesanan, "card2");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 1221, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(869, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        add(panelData, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
    }//GEN-LAST:event_txtCariFocusGained
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxJenisLaporan;
    private javax.swing.JComboBox<String> cbxUrutan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel label;
    private javax.swing.JLabel lbRp;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelPemeriksaan;
    private javax.swing.JPanel panelPemesanan;
    private javax.swing.JPanel panelPenjualan;
    private javax.swing.JScrollPane scrollPemeriksaan;
    private javax.swing.JScrollPane scrollPemesanan;
    private javax.swing.JScrollPane scrollPenjualan;
    private javax.swing.JTable tablePemeriksaan;
    private swing.Table tablePemesanan;
    private javax.swing.JTable tablePenjualan;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtTgl;
    // End of variables declaration//GEN-END:variables
}
