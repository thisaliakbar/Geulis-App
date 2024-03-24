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
import javax.swing.JOptionPane;
import model.ModelPromo;
/**
 *
 * @author usER
 */
public class ServicePengaturan {
    private Connection connection;
    public ServicePengaturan() {
        connection = Koneksi.getConnection();
    }
    
//    service atur promo
    public void addPromo(ModelPromo modelPromo) {
        String query = "INSERT INTO promo (No_Promo, Nama_Promo, Tanggal_Awal, Tanggal_Akhir, Banyak_Promo, Jenis_Promo, Keterangan) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPromo.getNoPromo());
            pst.setString(2, modelPromo.getNamaPromo());
            pst.setString(3, modelPromo.getTglAwal());
            pst.setString(4, modelPromo.getTglAkhir());
            pst.setInt(5, modelPromo.getBanyakPromo());
            pst.setString(6, modelPromo.getJenisPromo());
            pst.setString(7, modelPromo.getKeterangan());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil menambahkan promo");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPromosi = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        Date date = new Date();
        String format = sdf.format(date);
        String query = "SELECT RIGHT(No_Promo, 3) AS Nomor FROM promo WHERE No_Promo LIKE 'P-"+format+"%' ORDER BY Nomor DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int no = Integer.parseInt(rst.getString("Nomor"));
                no++;
                noPromosi = "P-" + format + "-"+String.format("%03d", no);
            } else {
                noPromosi = "P-" + format + "-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return noPromosi;
    }
}
