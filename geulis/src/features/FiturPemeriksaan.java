/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.ActionPagination;
import action.TableAction;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.Main;
import model.ModelDetailPemeriksaan;
import model.ModelHeaderTable;
import model.ModelKaryawan;
import model.ModelPemeriksaan;
import model.ModelRenderTable;
import model.ModelPasien;
import model.PemeriksaanSementara;
import service.ServiceDetailPemeriksaan;
import swing.TableCellActionRender;
import swing.TableCellEditor;
import service.ServicePemeriksaan;

/**
 *
 * @author usER
 */
public class FiturPemeriksaan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private DefaultTableModel tabmodel1;
    private DefaultTableModel tabmodel2;
    private TableAction action;
    private ServicePemeriksaan servicPemeriksaan = new ServicePemeriksaan();
    private ServiceDetailPemeriksaan serviceDetail = new ServiceDetailPemeriksaan();
    private String idPasien;
    public FiturPemeriksaan() {
        initComponents();
      
        styleTable(scrollPane, table, 5);
        tabmodel1 = (DefaultTableModel) table.getModel();
        
        styleTable(scrollPanePasien, tableDetail, 5);
        tabmodel2 = (DefaultTableModel) tableDetail.getModel();
        txtDeskripsi.setLineWrap(true);
        
//      Tampil Data Main
        servicPemeriksaan.loadData(1, tabmodel1, pagination);
        pagination.addActionPagination(new ActionPagination() {
            @Override
            public void pageChanged(int page) {
                remov();
                servicPemeriksaan.loadData(page, tabmodel1, pagination);
            }
        });
                
        actionTableMain();
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
    
//  Tambah Data
    private void tambahData() {
        ModelPasien pasien = new ModelPasien();
        ModelKaryawan karyawan = new ModelKaryawan();
        
        String noPemeriksaan = lbNoPemeriksaan.getText();
        String tgl = lbTgl.getText();
        String deskripsi = txtDeskripsi.getText();
        int total = Integer.parseInt(lbTotal.getText());
        
        pasien.setIdPasien(this.idPasien);
        pasien.setNama(lbNamaPasien.getText());
        karyawan.setIdKaryawan(lbIdKaryawan.getText());
        karyawan.setNama(lbNamaKaryawan.getText());
        
        
        ModelPemeriksaan pemeriksaan = new ModelPemeriksaan(noPemeriksaan, tgl, deskripsi, total, pasien, karyawan);
        servicPemeriksaan.addData(pemeriksaan);
        
//      Tambah Detail
        for(int a = 0; a < tabmodel2.getRowCount(); a++) {
            String kodeTindakan = (String) tableDetail.getValueAt(a, 0);
            int totalHarga = (int) tableDetail.getValueAt(a, 4);
            int potongan = (int) tableDetail.getValueAt(a, 3);
            PemeriksaanSementara ts = new PemeriksaanSementara(new String[]{kodeTindakan}, new int[]{potongan}, new int[]{totalHarga});
            serviceDetail.addData(noPemeriksaan, ts);
        }
        
        tabmodel2.setRowCount(0);
    }
    
    
    private int total() {
        
        int total = 0;
         for(int a = 0; a < tabmodel2.getRowCount(); a++) {
             int subtotal = (int) tableDetail.getValueAt(a, 4);
             total += subtotal;
         }
        return total; 
    }
    
//  Tambah Sementara
    private void tambahDataSementara() {
         String kodeTindakan = lbKodeTindakan.getText();
         String namaTindaan = lbNamaTindakan.getText();
         int harga = Integer.parseInt(lbHarga.getText());
         int potongan = 100000;
         tabmodel2.addRow(new Object[]{kodeTindakan, namaTindaan, harga, potongan, harga - potongan});
         lbTotal.setText(String.valueOf(total()));
    }
    
//  Update,Delete,Detail Table Main
    private void actionTableMain() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
        }

        @Override
        public void delete(int row) {
        }

        @Override
        public void view(int row) {
            ModelPemeriksaan modelPemeriksaan = new ModelPemeriksaan();
            ModelPasien modelPasien = new ModelPasien();
            
            modelPemeriksaan.setNoPemeriksaan((String) table.getValueAt(row, 0));
            modelPasien.setNama((String) table.getValueAt(row, 1));
            modelPemeriksaan.setModelPasien(modelPasien);
            modelPemeriksaan.setTglPemeriksaan((String) table.getValueAt(row, 2));
            modelPemeriksaan.setTotal((int) table.getValueAt(row, 3));
            
            ModelDetailPemeriksaan modelDetail = new ModelDetailPemeriksaan();
            modelDetail.setModelPemeriksaan(modelPemeriksaan);
            
            DialogDetail dialog = new DialogDetail(null, true, "Slide-1", modelDetail);
            dialog.setVisible(true);
        }
    };        
        table.getColumnModel().getColumn(4).setCellRenderer(new TableCellActionRender(false, false, true));
        table.getColumnModel().getColumn(4).setCellEditor(new TableCellEditor(action, false, false, true));
    }
    
//    Update,Delete,Detail Table Detail
    private void actionTableDetail() {
        action = new TableAction() {
            @Override
            public void edit(int row) {
            }

            @Override
            public void delete(int row) {
                if(tableDetail.isEditing()) {
                    tableDetail.getCellEditor().stopCellEditing();
                }
                tabmodel2.removeRow(row);
                lbTotal.setText(String.valueOf(total()));
                
            }

            @Override
            public void view(int row) {
                
            }
        };
        
        tableDetail.getColumnModel().getColumn(5).setCellRenderer(new TableCellActionRender(false, true, false));
        tableDetail.getColumnModel().getColumn(5).setCellEditor(new TableCellEditor(action, false, true, false));
    }
    
    private void remov() {
        for(int a = 0; a < tabmodel1.getRowCount(); a=a+0) {
            tabmodel1.removeRow(a);
        }
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
        panel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnTambah = new swing.Button();
        label = new javax.swing.JLabel();
        pagination = new swing.Pagination();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        panelTambah = new javax.swing.JPanel();
        panel3 = new javax.swing.JPanel();
        scrollPanePasien = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbRp = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        btnTambahSementara = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbxNoReservasi = new javax.swing.JComboBox<>();
        btnPilih1 = new swing.Button();
        btnPilih2 = new swing.Button();
        txtDeskripsi = new javax.swing.JTextArea();
        lbNoPemeriksaan = new javax.swing.JLabel();
        lbTgl = new javax.swing.JLabel();
        lbNamaPasien = new javax.swing.JLabel();
        lbIdKaryawan = new javax.swing.JLabel();
        lbNamaKaryawan = new javax.swing.JLabel();
        lbKodeTindakan = new javax.swing.JLabel();
        lbNamaTindakan = new javax.swing.JLabel();
        lbHarga = new javax.swing.JLabel();
        lbPotongan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        btnBatal = new swing.Button();
        btnSimpan = new swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);

        table.setBackground(new java.awt.Color(255, 255, 255));
        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemeriksaan", "Nama Pasien", "Tanggal Pemeriksaan", "Total", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(185, 185, 185));
        table.setOpaque(false);
        table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setViewportView(table);

        btnTambah.setBackground(new java.awt.Color(135, 15, 50));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("TAMBAH");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        label.setBackground(new java.awt.Color(135, 15, 50));
        label.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label.setForeground(new java.awt.Color(135, 15, 50));
        label.setText("PEMERIKSAAN");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan No Pemeriksaan Atau Nama Pasien");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCariFocusLost(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 651, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(label)
                            .addGap(0, 933, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 765, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addGap(63, 63, 63)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        add(panelData, "card2");

        panelTambah.setBackground(new java.awt.Color(153, 153, 153));
        panelTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        scrollPanePasien.setBorder(null);
        scrollPanePasien.setOpaque(false);

        tableDetail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Tindakan", "Nama Tindakan", "Harga", "Potongan", "Total Harga", "Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDetail.setOpaque(false);
        tableDetail.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tableDetail.setSelectionForeground(new java.awt.Color(255, 255, 255));
        scrollPanePasien.setViewportView(tableDetail);

        jPanel2.setBackground(new java.awt.Color(135, 15, 50));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("TOTAL : ");

        lbRp.setBackground(new java.awt.Color(255, 255, 255));
        lbRp.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbRp.setForeground(new java.awt.Color(255, 255, 255));
        lbRp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbRp.setText("Rp");

        lbTotal.setBackground(new java.awt.Color(255, 255, 255));
        lbTotal.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTotal.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPanePasien, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(scrollPanePasien, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnTambahSementara.setBackground(new java.awt.Color(135, 15, 50));
        btnTambahSementara.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahSementara.setText("Tambah");
        btnTambahSementara.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambahSementara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSementaraActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("No Reservasi");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("No Pemeriksaan");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Kode Tindakan");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Nama Pasien");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Nama Tindakan");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Tanggal");

        jLabel10.setBackground(new java.awt.Color(134, 15, 50));
        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pilih Karyawan");
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(134, 15, 50));
        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Pilih Tindakan");
        jLabel11.setOpaque(true);

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("ID Karyawan");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Nama Karyawan");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Harga");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Potongan");

        cbxNoReservasi.setBackground(new java.awt.Color(255, 255, 255));
        cbxNoReservasi.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbxNoReservasi.setForeground(new java.awt.Color(0, 0, 0));
        cbxNoReservasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih No Reservasi" }));
        cbxNoReservasi.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        cbxNoReservasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNoReservasiActionPerformed(evt);
            }
        });

        btnPilih1.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih1.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih1.setText("PILIH");
        btnPilih1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilih1ActionPerformed(evt);
            }
        });

        btnPilih2.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih2.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih2.setText("PILIH");
        btnPilih2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilih2ActionPerformed(evt);
            }
        });

        txtDeskripsi.setColumns(20);
        txtDeskripsi.setForeground(new java.awt.Color(185, 185, 185));
        txtDeskripsi.setRows(5);
        txtDeskripsi.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        txtDeskripsi.setOpaque(false);

        lbNoPemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbNoPemeriksaan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaPasien.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaPasien.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbIdKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbIdKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbNamaKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbKodeTindakan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKodeTindakan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaTindakan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaTindakan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbHarga.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbHarga.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbPotongan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbPotongan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxNoReservasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNamaPasien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDeskripsi)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(lbIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPilih1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(lbKodeTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnPilih2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbNamaKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNamaTindakan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbPotongan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxNoReservasi, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNamaPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(btnPilih1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbIdKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(lbNamaKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPilih2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbKodeTindakan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNamaTindakan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPotongan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Tambah Pemeriksaan");

        btnBatal.setForeground(new java.awt.Color(135, 15, 50));
        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setForeground(new java.awt.Color(135, 15, 50));
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label1)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTambahLayout = new javax.swing.GroupLayout(panelTambah);
        panelTambah.setLayout(panelTambahLayout);
        panelTambahLayout.setHorizontalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        changePanel(panelTambah);
        lbNoPemeriksaan.setText(servicPemeriksaan.createNo());
        actionTableDetail();
        servicPemeriksaan.readReservasi(cbxNoReservasi);
        tabmodel2.setRowCount(0);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
    }//GEN-LAST:event_txtCariFocusGained

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
        txtCari.setText("Cari Berdasarkan Kode Barang & Nama Barang");
        txtCari.setForeground(new Color(185,185,185));
        txtCari.setFont(new Font("sansserif",Font.ITALIC,14));
    }//GEN-LAST:event_txtCariFocusLost

    private void btnTambahSementaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSementaraActionPerformed
        tambahDataSementara();
        clearFieldTindakan();
    }//GEN-LAST:event_btnTambahSementaraActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        tambahData();
        clearField();
        changePanel(panelData);
        tabmodel1.setRowCount(0);
        servicPemeriksaan.loadData(1, tabmodel1, pagination);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        changePanel(panelData);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnPilih1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilih1ActionPerformed
        PilihPemeriksaan pilih = new PilihPemeriksaan(new Main(), true, "Slide-1");
        pilih.setVisible(true);
        lbIdKaryawan.setText(pilih.modelKaryawan.getIdKaryawan());
        lbNamaKaryawan.setText(pilih.modelKaryawan.getNama());
    }//GEN-LAST:event_btnPilih1ActionPerformed

    private void btnPilih2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilih2ActionPerformed
        PilihPemeriksaan pilih = new PilihPemeriksaan(new Main(), true, "Slide-2");
        pilih.setVisible(true);
        lbKodeTindakan.setText(pilih.modelTindakan.getKodeTindakan());
        lbNamaTindakan.setText(pilih.modelTindakan.getNamaTindakan());
        lbHarga.setText(String.valueOf(pilih.modelTindakan.getBiaya()));
    }//GEN-LAST:event_btnPilih2ActionPerformed

    private void cbxNoReservasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNoReservasiActionPerformed
        int selectIndex = cbxNoReservasi.getSelectedIndex();
        
        if(selectIndex == selectIndex) {
            String noReservasi = (String) cbxNoReservasi.getSelectedItem();
            servicPemeriksaan.readLabel(noReservasi, lbTgl, lbNamaPasien);
            this.idPasien = servicPemeriksaan.readIdPasien(noReservasi);
        }
    }//GEN-LAST:event_cbxNoReservasiActionPerformed

    private void changePanel(JPanel panel) {
        removeAll();
        add(panel);
        repaint();
        revalidate();
    }
    
    private void characterDigit(KeyEvent evt) {
        char typed = evt.getKeyChar();
        if(!Character.isDigit(typed)) {
            evt.consume();
        }
    }
    
    private void clearField() {
        lbTgl.setText(null);
        lbNamaPasien.setText(null);
        lbIdKaryawan.setText(null);
        lbNamaKaryawan.setText(null);
        clearFieldTindakan();
    }
    
    private void clearFieldTindakan() {
        lbKodeTindakan.setText(null);
        lbNamaTindakan.setText(null);
        lbHarga.setText(null);
        lbPotongan.setText(null);
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnBatal;
    private swing.Button btnPilih1;
    private swing.Button btnPilih2;
    private swing.Button btnSimpan;
    private swing.Button btnTambah;
    private swing.Button btnTambahSementara;
    private javax.swing.JComboBox<String> cbxNoReservasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lbHarga;
    private javax.swing.JLabel lbIdKaryawan;
    private javax.swing.JLabel lbKodeTindakan;
    private javax.swing.JLabel lbNamaKaryawan;
    private javax.swing.JLabel lbNamaPasien;
    private javax.swing.JLabel lbNamaTindakan;
    private javax.swing.JLabel lbNoPemeriksaan;
    private javax.swing.JLabel lbPotongan;
    private javax.swing.JLabel lbRp;
    private javax.swing.JLabel lbTgl;
    private javax.swing.JLabel lbTotal;
    private swing.Pagination pagination;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPanePasien;
    private javax.swing.JTable table;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextArea txtDeskripsi;
    // End of variables declaration//GEN-END:variables
}
