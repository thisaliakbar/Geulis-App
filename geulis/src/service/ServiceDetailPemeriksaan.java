/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import control.ProductPemeriksaan;
import model.ModelDetailPemeriksaan;
import model.PemeriksaanSementara;
/**
 *
 * @author usER
 */
public class ServiceDetailPemeriksaan {
    
    private Connection conncetion;

    public ServiceDetailPemeriksaan() {
        conncetion = Koneksi.getConnection();
    }
    
    public void loadData(JLabel lbIdPasien, JLabel lbIdKaryawan, JTextArea txtDesc, DefaultTableModel tabModel, ModelDetailPemeriksaan detail) {
        String query1 = "SELECT ID_Pasien, ID_Karyawan, Deskripsi FROM Pemeriksaan WHERE No_Pemeriksaan='"+detail.getModelPemeriksaan().getNoPemeriksaan()+"'";
        String query2 = "SELECT detPem.No_Pemeriksaan, detPem.Kode_Tindakan, tdk.Nama_Tindakan, tdk.Biaya_Tindakan, "
                + "detPem.Potongan, detPem.Subtotal FROM detail_pemeriksaan detPem INNER JOIN "
                + "tindakan tdk ON detPem.Kode_Tindakan=tdk.Kode_Tindakan WHERE No_Pemeriksaan='"+detail.getModelPemeriksaan().getNoPemeriksaan()+"'";
        
        try {
            PreparedStatement pst = conncetion.prepareStatement(query1);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                String idPasien = rst.getString("ID_Pasien");
                String idKaryawan = rst.getString("ID_Karyawan");
                String desc = rst.getString("Deskripsi");
                
                lbIdPasien.setText(idPasien);
                lbIdKaryawan.setText(idKaryawan);
                txtDesc.setText(desc);
                
            }
            pst.close();
            rst.close();
            
            pst = conncetion.prepareStatement(query2);
            rst = pst.executeQuery();
            while(rst.next()) {
                String kode = rst.getString("Kode_Tindakan");
                String nama = rst.getString("Nama_Tindakan");
                int harga = rst.getInt("Biaya_Tindakan");
                int potongan = rst.getInt("Potongan");
                int totalHarga = rst.getInt("Subtotal");
                
//                tabModel.addRow(new Object[]{kode, nama, harga, potongan, totalHarga});
                tabModel.addRow(new ProductPemeriksaan(kode, nama, harga, potongan, totalHarga).toTableRow());
            }
            
            pst.close();
            rst.close();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelDetailPemeriksaan detail, PemeriksaanSementara ps) {
        String query = "INSERT INTO detail_pemeriksaan (No_Pemeriksaan, Kode_Tindakan, Potongan, Subtotal) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst = conncetion.prepareStatement(query);
            String[] kodeTindakan = ps.getKodeTindakan();
            int[] subtotal = ps.getSubtotal();
            int[] potongan = ps.getPotongan();

            pst.setString(1, detail.getModelPemeriksaan().getNoPemeriksaan());
            for(String kode : kodeTindakan) {
                pst.setString(2, kode);
            }
            
            for(int pot : potongan) {
                pst.setInt(3, pot);
            }
            
            for(int sub : subtotal) {
                pst.setInt(4, sub);
            }
            
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            
        }
    }
}
