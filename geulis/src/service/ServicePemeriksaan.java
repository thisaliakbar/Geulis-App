/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelPemeriksaan;
import swing.Pagination;
/**
 *
 * @author usER
 */
public class ServicePemeriksaan {
    private Connection connection;

    public ServicePemeriksaan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Pemeriksaan) AS Jumlah FROM pemeriksaan";
        int limit = 20;
        int count = 0;
        
        String query = "SELECT pmn.No_Pemeriksaan, pmn.Tanggal_Pemeriksaan, pmn.Deskripsi, "
                + "pmn.Total, pmn.ID_Pasien, psn.Nama, pmn.ID_Karyawan, krn.Nama FROM pemeriksaan pmn "
                + "INNER JOIN pasien psn ON pmn.ID_Pasien=psn.ID_Pasien "
                + "INNER JOIN karyawan krn ON pmn.ID_Karyawan=krn.ID_Karyawan "
                + "ORDER BY No_Pemeriksaan ASC LIMIT "+(page-1) * limit+","+limit+"";
        
        try {
            PreparedStatement pst = connection.prepareStatement(sqlCount);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                count = rst.getInt("Jumlah");
            }
            
            pst.close();
            rst.close();
            
            pst = connection.prepareStatement(query);
            rst = pst.executeQuery();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String namaPasien = rst.getString("psn.Nama");
                String tgl = rst.getString("Tanggal_Pemeriksaan");
                int total = rst.getInt("Total");
                tabmodel.addRow(new Object[]{noPemeriksaan, namaPasien, tgl, total});
            }
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
    
    public void addData(ModelPemeriksaan modelPemeriksaan) {
        String query = "INSERT INTO pemeriksaan (No_Pemeriksaan, Tanggal_Pemeriksaan, Deskripsi, Total, ID_Pasien, ID_Karyawan) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPemeriksaan.getNoPemeriksaan());
            pst.setString(2, modelPemeriksaan.getTglPemeriksaan());
            pst.setString(3, modelPemeriksaan.getDeskripsi());
            pst.setInt(4, modelPemeriksaan.getTotal());
            pst.setString(5, modelPemeriksaan.getModelPasien().getIdPasien());
            pst.setString(6, modelPemeriksaan.getModelKaryawan().getIdKaryawan());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPemeriksaan = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        Date date = new Date();
        String format = sdf.format(date);
        String query = "SELECT RIGHT(No_Pemeriksaan, 3) AS Nomor FROM pemeriksaan WHERE No_Pemeriksaan LIKE 'PMRN-"+format+"%' ORDER BY Nomor DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int no = Integer.parseInt(rst.getString("Nomor"));
                no++;
                noPemeriksaan = "PMRN-" + format + "-" +String.format("%03d", no);
            } else {
                noPemeriksaan = "PMRN-" + format + "-001";
            }
            pst.close();
            rst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return noPemeriksaan;
    }
    
    public String readIdPasien(String noReservasi) {
        String query = "SELECT ID_Pasien FROM reservasi WHERE No_Reservasi='"+noReservasi+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                return rst.getString("ID_Pasien");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void readReservasi(JComboBox<String> comboBox) {
        String query = "SELECT No_Reservasi FROM reservasi WHERE Status_Reservasi='Menunggu'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noReservasi = rst.getString("No_Reservasi");
                comboBox.addItem(noReservasi);
            }
            pst.close();
            rst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void readLabel(String noReservasi, JLabel lbTgl, JLabel lbNama) {
        String query = "SELECT rsv.Tanggal_Kedatangan, rsv.ID_Pasien, psn.Nama FROM reservasi rsv "
                + "INNER JOIN pasien psn ON rsv.ID_Pasien=psn.ID_Pasien WHERE No_Reservasi='"+noReservasi+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                String tgl = rst.getString("Tanggal_Kedatangan");
                String nama = rst.getString("Nama");
                lbTgl.setText(tgl);
                lbNama.setText(nama);
            }
            pst.close();
            rst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addTemporaryCode(String actionCode) {
        String query = "INSERT INTO tindakansementara VALUES('"+actionCode+"')";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteTemporaryCode(String actionCode) {
        String query = "DELETE FROM tindakansementara WHERE Kode_Tindakan='"+actionCode+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteAll() {
        String query = "DELETE FROM tindakansementara";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean validationData(String actionCode) {
        boolean valid = false;
        String query = "SELECT Kode_Tindakan FROM tindakansementara WHERE Kode_Tindakan='"+actionCode+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
            JOptionPane.showMessageDialog(null, "Barang ini sudah ditambahkan");
            } else {
                valid = true;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
    
    public int promo() {
        int banyakPromo = 0;
        String query = "SELECT Banyak_Promo FROM promo WHERE Keterangan='Berjalan'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                banyakPromo = rst.getInt("Banyak_Promo");
            } else {
                banyakPromo = 0;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return banyakPromo;
    }
    
    public boolean checkMember(String idMember) {
        boolean member = false;
        String query = "SELECT Level FROM pasien WHERE ID_Pasien='"+idMember+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
               String level = rst.getString("Level");
               if(level.equals("Member")) {
                   member = true;
               }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return member;
    }
}
