/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.ModelPromo;
/**
 *
 * @author usER
 */
public class ServicePromo {
    
    private Connection connection;
    
    public ServicePromo() {
        connection = Koneksi.getConnection();
    }
    
//  loadPromo
    public void loadPromo(DefaultTableModel tabmodel) {
        String query = "SELECT * FROM promo";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPromo = rst.getString("No_Promo");
                String nama = rst.getString("Nama_Promo");
                String tglAwal = rst.getString("Tanggal_Awal");
                String tglAkhir = rst.getString("Tanggal_Akhir");
                String rentang = tglAwal + " Sampai " + tglAkhir;
                int lots = rst.getInt("Banyak_Promo");
                String banyak;
                if(lots <= 100) {
                    banyak = rst.getString("Banyak_Promo").concat(" %");
                } else {
                    banyak  = rst.getString("Banyak_Promo");
                }
                String jenis = rst.getString("Jenis_Promo");
                String keterangan = rst.getString("Keterangan");
                tabmodel.addRow(new String[]{noPromo,nama, rentang, banyak, jenis, keterangan});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//    end promo
    public void endPromo(ModelPromo promo) {
        String query = "UPDATE promo SET Keterangan='Berakhir' WHERE No_Promo='"+promo.getNoPromo()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Promo berhasil di akhiri");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String lastDatePromo(ModelPromo promo) {
        String lastDay = null;
        String query = "SELECT Tanggal_Akhir FROM promo WHERE No_Promo='"+promo.getNoPromo()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                lastDay = rst.getString("Tanggal_Akhir");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return lastDay;
    }
}
