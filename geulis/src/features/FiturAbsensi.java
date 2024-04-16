/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.ModelAbsensi;
import model.ModelKaryawan;
import service.ServiceAbsensi;

/**
 *
 * @author usER
 */
public class FiturAbsensi extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private ServiceAbsensi serviceAbsensi = new ServiceAbsensi();
    public FiturAbsensi() {
        initComponents();
        attendence();
    }
    
    
    private void simpanAbsen() {
        String idKaryawan = txtIdKaryawan.getText();
        
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateNow = LocalDate.now();
        String strDateNow = dateNow.format(formatDate);
        String attendenceIn = txtAbsensiMasuk.getText();
        String attendenceOut = txtAbsensiKeluar.getText();
        
        ModelKaryawan modelKaryawan = new ModelKaryawan();
        modelKaryawan.setIdKaryawan(idKaryawan);
        ModelAbsensi modelAbsensi = new ModelAbsensi(strDateNow, attendenceIn, attendenceOut, null, modelKaryawan);
        
        boolean validationAttendence = txtAbsensiMasuk.isVisible();
        if(validationAttendence) {
            serviceAbsensi.validationSetAttendenceIn(modelAbsensi);
        } else {
            serviceAbsensi.validationSetAttendenceOut(modelAbsensi);
        }
        
        serviceAbsensi.setInformation(modelAbsensi);
    }
    
    private void cekDataKaryawan() {
        String idKaryawam = txtIdKaryawan.getText();
        ModelKaryawan modelKaryawan = new ModelKaryawan();
        modelKaryawan.setIdKaryawan(idKaryawam);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateNow = LocalDate.now();
        String strDateNow = dateNow.format(formatDate);
        
        ModelAbsensi modelAbsensi = new ModelAbsensi();
        modelAbsensi.setTanggal(strDateNow);
        modelAbsensi.setModelKaryawan(modelKaryawan);
        String namaKaryawan = serviceAbsensi.getNamaKaryawan(modelAbsensi);
        txtNamaKaryawan.setText(namaKaryawan);
        
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime timeNow = LocalDateTime.now();
        String strTimeNow = timeNow.format(formatTime);
        
        txtAbsensiMasuk.setText(strTimeNow);
        txtAbsensiKeluar.setText(strTimeNow);
        
        String keterangan = serviceAbsensi.getKeteranganAbsen(modelAbsensi);
        txtKeterangan.setText(keterangan);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTambah = new javax.swing.JPanel();
        panelAbsensi = new javax.swing.JPanel();
        panel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        btnSimpan1 = new swing.Button();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbAbsensiKeluar = new javax.swing.JLabel();
        lbAbsensiMasuk = new javax.swing.JLabel();
        lbKeterangan = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtIdKaryawan = new javax.swing.JTextField();
        txtAbsensiKeluar = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        txtNamaKaryawan = new javax.swing.JTextField();
        txtAbsensiMasuk = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        panelTambah.setBackground(new java.awt.Color(153, 153, 153));
        panelTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panelAbsensi.setBackground(new java.awt.Color(255, 255, 255));
        panelAbsensi.setLayout(new java.awt.CardLayout());

        panel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 2, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(175, 175, 175));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Belum Saatnya Absen");

        jLabel2.setFont(new java.awt.Font("SansSerif", 2, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(175, 175, 175));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Silahkan Kembali Pada Pukul 08.00 dan Pukul 16.00");

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        panelAbsensi.add(panel4, "card2");

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        btnSimpan1.setBackground(new java.awt.Color(135, 15, 50));
        btnSimpan1.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan1.setText("SIMPAN");
        btnSimpan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Nama Karyawan");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Scan ID Card");

        lbAbsensiKeluar.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbAbsensiKeluar.setForeground(new java.awt.Color(0, 0, 0));
        lbAbsensiKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbAbsensiKeluar.setText("Absensi Keluar");

        lbAbsensiMasuk.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbAbsensiMasuk.setForeground(new java.awt.Color(0, 0, 0));
        lbAbsensiMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbAbsensiMasuk.setText("Absensi Masuk");

        lbKeterangan.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbKeterangan.setForeground(new java.awt.Color(0, 0, 0));
        lbKeterangan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbKeterangan.setText("Keterangan");

        jLabel17.setBackground(new java.awt.Color(135, 15, 50));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/barcode.png"))); // NOI18N
        jLabel17.setOpaque(true);

        txtIdKaryawan.setFont(new java.awt.Font("SansSerif", 2, 20)); // NOI18N
        txtIdKaryawan.setForeground(new java.awt.Color(185, 185, 185));
        txtIdKaryawan.setText("Klik disini dan Scan ID Card");
        txtIdKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        txtIdKaryawan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdKaryawanFocusGained(evt);
            }
        });
        txtIdKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdKaryawanActionPerformed(evt);
            }
        });

        txtAbsensiKeluar.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtAbsensiKeluar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtKeterangan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtKeterangan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtNamaKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtNamaKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtAbsensiMasuk.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtAbsensiMasuk.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Note :");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Keterangan absen akan terverifikasi \"Sudah Absen\" jika telah melakukan absen 2x ( Pagi & Sore )");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbAbsensiKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbAbsensiMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAbsensiKeluar)
                            .addComponent(txtKeterangan)
                            .addComponent(txtNamaKaryawan)
                            .addComponent(txtAbsensiMasuk)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(txtIdKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)))))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAbsensiMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbsensiMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAbsensiKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbsensiKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        panelAbsensi.add(panel1, "card2");

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("ABSENSI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(353, Short.MAX_VALUE)
                .addComponent(label1)
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1)
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelAbsensi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(panelAbsensi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        add(panelTambah, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        if(txtKeterangan.getText().equals("Sudah Absen")) {
            JOptionPane.showMessageDialog(null, "Karyawan Sudah Melakukan Absen");
        } else if(txtNamaKaryawan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Karyawan Tidak Terdaftar");
        } else {
        simpanAbsen();
        }
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void txtIdKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdKaryawanActionPerformed
        cekDataKaryawan(); 
    }//GEN-LAST:event_txtIdKaryawanActionPerformed

    private void txtIdKaryawanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdKaryawanFocusGained
        txtIdKaryawan.setText(null);
        txtIdKaryawan.setFont(new Font("sansserif", 0, 20));
        txtIdKaryawan.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_txtIdKaryawanFocusGained

    private void changePanel(JPanel panel) {
        panelAbsensi.removeAll();
        panelAbsensi.add(panel);
        panelAbsensi.repaint();
        panelAbsensi.revalidate();
    }
    
    private void attendence() {
        LocalDateTime timeNow = LocalDateTime.now();
        int hour = timeNow.getHour();
        if(hour == 8) {
            changePanel(panel1);
            setField(true, false);
        } else if(hour == 16) {
            changePanel(panel1);
            setField(false, true);
        } else {
            changePanel(panel4);
        }
        
    }
    
    private void setField(boolean showAttendenceIn, boolean showAttendenceOut) {
    txtNamaKaryawan.setEnabled(false);
    txtAbsensiMasuk.setEnabled(false);
    lbAbsensiMasuk.setVisible(showAttendenceIn);
    txtAbsensiMasuk.setVisible(showAttendenceIn);
    
    txtAbsensiKeluar.setEnabled(false);
    lbAbsensiKeluar.setVisible(showAttendenceOut);
    txtAbsensiKeluar.setVisible(showAttendenceOut);
    
    txtKeterangan.setEnabled(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnSimpan1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lbAbsensiKeluar;
    private javax.swing.JLabel lbAbsensiMasuk;
    private javax.swing.JLabel lbKeterangan;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panelAbsensi;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JTextField txtAbsensiKeluar;
    private javax.swing.JTextField txtAbsensiMasuk;
    private javax.swing.JTextField txtIdKaryawan;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNamaKaryawan;
    // End of variables declaration//GEN-END:variables
}
