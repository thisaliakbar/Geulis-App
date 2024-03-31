/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.ModelPasien;
import model.ModelPemeriksaan;
/**
 *
 * @author usER
 */
public class ServiceRiwayatPasien {
    private Connection connection;
    
    public ServiceRiwayatPasien() {
        connection = Koneksi.getConnection();
    }
    
    public List<String> getIdPasien(){
        List<String> idPasiens = new ArrayList<>();
        String query = "SELECT DISTINCT ID_Pasien FROM pemeriksaan";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String idPasien = rst.getString("ID_Pasien");
                idPasiens.add(idPasien);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return idPasiens;
    }
    
    public void loadData(ModelPemeriksaan modelPemeriksaan, DefaultTableModel model) {
        String query = "SELECT pmrn.No_Pemeriksaan, pmrn.ID_Pasien, psn.Nama, pmrn.Tanggal_Pemeriksaan FROM pemeriksaan pmrn "
                + "INNER JOIN pasien psn ON psn.ID_Pasien=pmrn.ID_Pasien WHERE pmrn.ID_Pasien='"+modelPemeriksaan.getModelPasien().getIdPasien()+"' "
                + "ORDER BY Tanggal_Pemeriksaan DESC LIMIT 1";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String idPasien = rst.getString("ID_Pasien");
                String nama = rst.getString("Nama");
                String tglPemeriksaan = rst.getString("Tanggal_Pemeriksaan");
                LocalDate lastCheckDate = LocalDate.parse(tglPemeriksaan, formatter);
                LocalDate dateNow = LocalDate.now();
                String tglSekarang = dateNow.format(formatter);
                LocalDate estimateCheck = lastCheckDate.plusDays(30);
                String estimasiPemeriksaan = estimateCheck.format(formatter);
                
                String status = "";
                if(tglSekarang.equals(estimasiPemeriksaan)) {
                    status = "Follow Up";
                }
                model.addRow(new String[]{noPemeriksaan, idPasien, nama, tglPemeriksaan, status});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void loadDataDetail(ModelPemeriksaan modelPemeriksaan, DefaultTableModel model) {
        String query = "SELECT No_Pemeriksaan, Tanggal_Pemeriksaan FROM pemeriksaan WHERE ID_Pasien='"+modelPemeriksaan.getModelPasien().getIdPasien()+"' ORDER BY Tanggal_Pemeriksaan DESC";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String tglPemeriksaan = rst.getString("Tanggal_Pemeriksaan");
                LocalDate lastDate = LocalDate.parse(tglPemeriksaan, formatter);
                LocalDate nextDate = lastDate.plusDays(30);
                String estimate = nextDate.format(formatter);
                model.addRow(new String[]{noPemeriksaan, tglPemeriksaan, estimate});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void addDataRiwayatPasien(ModelPemeriksaan modelPemeriksaan, String status) {
        String query = "INSERT INTO riwayat_pasien (No_Pemeriksaan,ID_Pasien,Nama,Terakhir_Pemeriksaan,Status) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPemeriksaan.getNoPemeriksaan());
            pst.setString(2, modelPemeriksaan.getModelPasien().getIdPasien());
            pst.setString(3, modelPemeriksaan.getModelPasien().getNama());
            pst.setString(4, modelPemeriksaan.getTglPemeriksaan());
            pst.setString(5, status);
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
