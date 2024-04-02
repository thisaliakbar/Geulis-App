/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package features;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.ModelDetailPemeriksaan;
import model.ModelDetailPengeluaran;
import model.ModelHeaderTable;
import model.ModelPasien;
import model.ModelPemeriksaan;
import model.ModelRenderTable;
import service.ServiceDetailPemeriksaan;
import service.ServiceRiwayatPasien;

/**
 *
 * @author usER
 */
public class DialogDetail extends java.awt.Dialog {

    /**
     * Creates new form DialogDetail
     */
    private ServiceDetailPemeriksaan servisDetail1 = new ServiceDetailPemeriksaan();
    private ServiceRiwayatPasien serviceRiwayat = new ServiceRiwayatPasien();
    private DefaultTableModel tabModel1;
    private DefaultTableModel tabModel2;
    private DefaultTableModel tabModel3;
    private ModelDetailPemeriksaan detailPemeriksaan;
    private ModelDetailPengeluaran detailPengeluaran;
    
    public DialogDetail(java.awt.Frame parent, boolean modal, String slide, 
    ModelDetailPemeriksaan detailPemeriksaan, ModelDetailPengeluaran detailPengeluaran) {
        super(parent, modal);
        initComponents();
        this.detailPemeriksaan = detailPemeriksaan;
        this.detailPengeluaran = detailPengeluaran;
        
        styleTable(scroll1, tablePemeriksaan, 5);
        styleTable(scroll2, tableRiwayat, 3);
        styleTable(scroll3, tableDetailPengeluaran, 4);
        tabModel1 = (DefaultTableModel) tablePemeriksaan.getModel();
        tabModel2 = (DefaultTableModel) tableRiwayat.getModel();
        tabModel3 = (DefaultTableModel) tableDetailPengeluaran.getModel();
        
        changePanel(slide);
        styleTextArea(txtDesc);
        styleTextArea(txtMessage);
        styleTextArea(txtDescPengeluaran);
    }
    
    private void styleTextArea(JTextArea txtArea) {
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
    }
    
//  Tampil Data Pemeriksaan
    private void tampilDataPemeriksaan() {
        
        ModelDetailPemeriksaan detail = new ModelDetailPemeriksaan();
        ModelPemeriksaan pemeriksaan = new ModelPemeriksaan();
        String noPemeriksaan = detailPemeriksaan.getModelPemeriksaan().getNoPemeriksaan();
        String idPasien = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getIdPasien();
        String nama = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getNama();
        String idKaryawan = detailPemeriksaan.getModelPemeriksaan().getModelKaryawan().getIdKaryawan();
        String tgl = detailPemeriksaan.getModelPemeriksaan().getTglPemeriksaan();
        int total = detailPemeriksaan.getModelPemeriksaan().getTotal();
        double bayar = detailPemeriksaan.getModelPemeriksaan().getBayar();
        double kembalian = detailPemeriksaan.getModelPemeriksaan().getKembalian();
        String jenisPembayaran = detailPemeriksaan.getModelPemeriksaan().getJenisPembayaran();
        String deskripsi = detailPemeriksaan.getModelPemeriksaan().getDeskripsi();
        pemeriksaan.setNoPemeriksaan(noPemeriksaan);
        detail.setModelPemeriksaan(pemeriksaan);
        
        lbNoPemeriksaan.setText(noPemeriksaan);
        lbIdPasien.setText(idPasien);
        lbNama.setText(nama);
        lbTgl.setText(tgl);
        lbIdKaryawan.setText(idKaryawan);
        DecimalFormat df = new DecimalFormat("#,##0.##");
        lbTotal.setText("Rp " + df.format(total));
        txtDesc.setText(deskripsi);
        lbBayar.setText("Rp " + df.format(bayar));
        lbKembalian.setText("Rp " + df.format(kembalian));
        lbJenisPembayaran.setText(jenisPembayaran);
        servisDetail1.loadData(tabModel1, detail);
    }
    
    //    Tampil data riwayat pasien
    private void tampilDataRiwayat() {
        ModelPemeriksaan pemeriksaan = new ModelPemeriksaan();
        ModelPasien pasien = new ModelPasien();
        String idPasien = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getIdPasien();
        String namaPasien = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getNama();
        String tgl = detailPemeriksaan.getModelPemeriksaan().getTglPemeriksaan();
        String noPemeriksaan = detailPemeriksaan.getModelPemeriksaan().getNoPemeriksaan();
        
        lbIdPasien2.setText(idPasien);
        lbNamaPasien.setText(namaPasien);
        lbTerakhirPemeriksaan.setText(tgl);
        lbNoPemeriksaan2.setText(noPemeriksaan);
        
        pasien.setIdPasien(idPasien);
        pemeriksaan.setModelPasien(pasien);
        serviceRiwayat.loadDataDetail(pemeriksaan, tabModel2);
    }
    
    private void tampilDataPengeluaran() {
        ModelDetailPengeluaran modelDetail = new ModelDetailPengeluaran();
        String noPengeluaran = detailPengeluaran.getModelPengeluaran().getNoPengeluaran();
        String idPengguna = "USR-001";
        String namaPengguna = "Admin 1";
        String user = idPengguna.concat("/" + namaPengguna);
        String tgl = detailPengeluaran.getModelPengeluaran().getTglPengeluaran();
        int total = detailPengeluaran.getModelPengeluaran().getTotal();
        
        lbNoPengeluaran.setText(noPengeluaran);
        lbPengguna.setText(user);
        lbTglPengeluaran.setText(tgl);
        DecimalFormat df = new DecimalFormat("#,##0.##");
        lbTotalPengeluaran.setText("Rp " + df.format(total));
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
    
    private void changePanel(String slide) {
        if(slide.equals("Slide-1")) {
            showPanel(panel1);
            tampilDataPemeriksaan();
        } else if(slide.equals("Slide-2")) {
            showPanel(panel2);
            tampilDataRiwayat();
        } else if(slide.equals("Slide-3")) {
            showPanel(panel3);
            tampilDataPengeluaran();
        }
    }
    
    private void showPanel(JPanel pn) {
        panel.removeAll();
        panel.add(pn);
        panel.repaint();
        panel.revalidate();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponents(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        lb1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbIdPasien = new javax.swing.JLabel();
        lb2 = new javax.swing.JLabel();
        lbNoPemeriksaan = new javax.swing.JLabel();
        lb3 = new javax.swing.JLabel();
        lbNama = new javax.swing.JLabel();
        lb4 = new javax.swing.JLabel();
        lb5 = new javax.swing.JLabel();
        lb6 = new javax.swing.JLabel();
        lbTgl = new javax.swing.JLabel();
        lbIdKaryawan = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextArea();
        scroll1 = new javax.swing.JScrollPane();
        tablePemeriksaan = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        lbBayar = new javax.swing.JLabel();
        lb7 = new javax.swing.JLabel();
        lb16 = new javax.swing.JLabel();
        lbKembalian = new javax.swing.JLabel();
        lbJenisPembayaran = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnKirim = new swing.Button();
        lbNamaPasien = new javax.swing.JLabel();
        lb8 = new javax.swing.JLabel();
        lbIdPasien2 = new javax.swing.JLabel();
        lb10 = new javax.swing.JLabel();
        lb11 = new javax.swing.JLabel();
        lbTerakhirPemeriksaan = new javax.swing.JLabel();
        lbNoPemeriksaan2 = new javax.swing.JLabel();
        scroll2 = new javax.swing.JScrollPane();
        tableRiwayat = new javax.swing.JTable();
        lb13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        panel3 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbPengguna = new javax.swing.JLabel();
        lb9 = new javax.swing.JLabel();
        lbNoPengeluaran = new javax.swing.JLabel();
        lb12 = new javax.swing.JLabel();
        lb14 = new javax.swing.JLabel();
        lbTglPengeluaran = new javax.swing.JLabel();
        lbTotalPengeluaran = new javax.swing.JLabel();
        scroll3 = new javax.swing.JScrollPane();
        tableDetailPengeluaran = new javax.swing.JTable();
        lb15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescPengeluaran = new javax.swing.JTextArea();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panel.setLayout(new java.awt.CardLayout());

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        lb1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb1.setForeground(new java.awt.Color(0, 0, 0));
        lb1.setText("No Pemeriksaan");

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        jLabel1.setBackground(new java.awt.Color(135, 15, 50));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DETAIL PEMERIKSAAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        lbIdPasien.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdPasien.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPasien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb2.setForeground(new java.awt.Color(0, 0, 0));
        lb2.setText("ID Pasien");

        lbNoPemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemeriksaan.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPemeriksaan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb3.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb3.setForeground(new java.awt.Color(0, 0, 0));
        lb3.setText("Nama");

        lbNama.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNama.setForeground(new java.awt.Color(0, 0, 0));
        lbNama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb4.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb4.setForeground(new java.awt.Color(0, 0, 0));
        lb4.setText("Tanggal");

        lb5.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb5.setForeground(new java.awt.Color(0, 0, 0));
        lb5.setText("ID Karyawan");

        lb6.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb6.setForeground(new java.awt.Color(0, 0, 0));
        lb6.setText("Total");

        lbTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTgl.setForeground(new java.awt.Color(0, 0, 0));
        lbTgl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lbIdKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        lbIdKaryawan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lbTotal.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(0, 0, 0));
        lbTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        txtDesc.setBackground(new java.awt.Color(255, 255, 255));
        txtDesc.setColumns(20);
        txtDesc.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDesc.setForeground(new java.awt.Color(0, 0, 0));
        txtDesc.setRows(5);
        txtDesc.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        txtDesc.setCaretColor(new java.awt.Color(255, 255, 255));

        tablePemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablePemeriksaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Tindakan", "Nama Tindakan", "Harga", "Potongan", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll1.setViewportView(tablePemeriksaan);

        jSeparator1.setBackground(new java.awt.Color(185, 185, 185));

        lbBayar.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbBayar.setForeground(new java.awt.Color(0, 0, 0));
        lbBayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb7.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb7.setForeground(new java.awt.Color(0, 0, 0));
        lb7.setText("Bayar");

        lb16.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb16.setForeground(new java.awt.Color(0, 0, 0));
        lb16.setText("Kembalian");

        lbKembalian.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKembalian.setForeground(new java.awt.Color(0, 0, 0));
        lbKembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lbJenisPembayaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbJenisPembayaran.setForeground(new java.awt.Color(0, 0, 0));
        lbJenisPembayaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(scroll1)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(lb1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb2)
                                    .addComponent(lb3))
                                .addGap(71, 71, 71)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbIdPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb4)
                            .addComponent(lb5)
                            .addComponent(lb6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbIdKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(lb7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lb16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbJenisPembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbIdPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNama, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lb4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(11, 11, 11)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lb5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(12, 12, 12)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lb6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtDesc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbJenisPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scroll1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel.add(panel1, "card2");

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(135, 15, 50));

        jLabel2.setBackground(new java.awt.Color(135, 15, 50));
        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DETAIL RIWAYAT");

        btnKirim.setBackground(new java.awt.Color(135, 15, 50));
        btnKirim.setForeground(new java.awt.Color(255, 255, 255));
        btnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/send.png"))); // NOI18N
        btnKirim.setText("Kirim");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKirim, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKirim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        lbNamaPasien.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaPasien.setForeground(new java.awt.Color(0, 0, 0));
        lbNamaPasien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb8.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb8.setForeground(new java.awt.Color(0, 0, 0));
        lb8.setText("Nama");

        lbIdPasien2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdPasien2.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPasien2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb10.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb10.setForeground(new java.awt.Color(0, 0, 0));
        lb10.setText("Terakhir Pemeriksaan");

        lb11.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb11.setForeground(new java.awt.Color(0, 0, 0));
        lb11.setText("No Pemeriksaan");

        lbTerakhirPemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTerakhirPemeriksaan.setForeground(new java.awt.Color(0, 0, 0));
        lbTerakhirPemeriksaan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lbNoPemeriksaan2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemeriksaan2.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPemeriksaan2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        tableRiwayat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemeriksaan", "Tanggal Pemeriksaan", "Estimasi Pemeriksaan Kembali"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll2.setViewportView(tableRiwayat);
        if (tableRiwayat.getColumnModel().getColumnCount() > 0) {
            tableRiwayat.getColumnModel().getColumn(0).setMinWidth(0);
            tableRiwayat.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableRiwayat.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        lb13.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb13.setForeground(new java.awt.Color(0, 0, 0));
        lb13.setText("ID Pasien");

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(lb11, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbNoPemeriksaan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lb8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbTerakhirPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbNamaPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbIdPasien2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbIdPasien2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNamaPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTerakhirPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNoPemeriksaan2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.add(panel2, "card2");

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(135, 15, 50));

        jLabel3.setBackground(new java.awt.Color(135, 15, 50));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DETAIL PENGELUARAN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        lbPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbPengguna.setForeground(new java.awt.Color(0, 0, 0));
        lbPengguna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb9.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb9.setForeground(new java.awt.Color(0, 0, 0));
        lb9.setText("Pengguna");

        lbNoPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPengeluaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lb12.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb12.setForeground(new java.awt.Color(0, 0, 0));
        lb12.setText("Tanggal Pengeluaran");

        lb14.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb14.setForeground(new java.awt.Color(0, 0, 0));
        lb14.setText("Total Pengeluaran");

        lbTglPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTglPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        lbTglPengeluaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        lbTotalPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTotalPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        lbTotalPengeluaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));

        tableDetailPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableDetailPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Jenis", "Jenis Pengeluaran", "Detail Jenis", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll3.setViewportView(tableDetailPengeluaran);
        if (tableDetailPengeluaran.getColumnModel().getColumnCount() > 0) {
            tableDetailPengeluaran.getColumnModel().getColumn(0).setMinWidth(0);
            tableDetailPengeluaran.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDetailPengeluaran.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        lb15.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb15.setForeground(new java.awt.Color(0, 0, 0));
        lb15.setText("No Pengeluaran");

        txtDescPengeluaran.setColumns(20);
        txtDescPengeluaran.setRows(5);
        jScrollPane2.setViewportView(txtDescPengeluaran);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scroll3, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTglPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbPengguna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNoPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTotalPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNoPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTglPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTotalPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addComponent(scroll3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.add(panel3, "card2");

        add(panel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    
    
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogDetail dialog = new DialogDetail(new java.awt.Frame(), true, "", null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnKirim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lb1;
    private javax.swing.JLabel lb10;
    private javax.swing.JLabel lb11;
    private javax.swing.JLabel lb12;
    private javax.swing.JLabel lb13;
    private javax.swing.JLabel lb14;
    private javax.swing.JLabel lb15;
    private javax.swing.JLabel lb16;
    private javax.swing.JLabel lb2;
    private javax.swing.JLabel lb3;
    private javax.swing.JLabel lb4;
    private javax.swing.JLabel lb5;
    private javax.swing.JLabel lb6;
    private javax.swing.JLabel lb7;
    private javax.swing.JLabel lb8;
    private javax.swing.JLabel lb9;
    private javax.swing.JLabel lbBayar;
    private javax.swing.JLabel lbIdKaryawan;
    private javax.swing.JLabel lbIdPasien;
    private javax.swing.JLabel lbIdPasien2;
    private javax.swing.JLabel lbJenisPembayaran;
    private javax.swing.JLabel lbKembalian;
    private javax.swing.JLabel lbNama;
    private javax.swing.JLabel lbNamaPasien;
    private javax.swing.JLabel lbNoPemeriksaan;
    private javax.swing.JLabel lbNoPemeriksaan2;
    private javax.swing.JLabel lbNoPengeluaran;
    private javax.swing.JLabel lbPengguna;
    private javax.swing.JLabel lbTerakhirPemeriksaan;
    private javax.swing.JLabel lbTgl;
    private javax.swing.JLabel lbTglPengeluaran;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbTotalPengeluaran;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JScrollPane scroll3;
    private javax.swing.JTable tableDetailPengeluaran;
    private javax.swing.JTable tablePemeriksaan;
    private javax.swing.JTable tableRiwayat;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextArea txtDescPengeluaran;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables
    
}
