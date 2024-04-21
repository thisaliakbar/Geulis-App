/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import model.ModelPasien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import swing.Pagination;

/**
 *
 * @author User
 */
public class ServicePasien {
    private Connection connection;
    
    public ServicePasien() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, Pagination pagination, DefaultTableModel model) {
        String sqlCount = "SELECT COUNT(ID_Pasien) AS Jumlah FROM Pasien";
        int limit = 5;
        int count = 0;
        String query = "SELECT * FROM pasien LIMIT "+(page-1) * limit+","+limit+"";
        System.out.println("query");
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
                String idPasien = rst.getString("Id_Pasien");
                String nama = rst.getString("Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                String no_Telp = rst.getString("No_Telp");
                String alamat = rst.getString("Alamat");
                String email = rst.getString("Email");
                String level = rst.getString("Level");
                model.addRow(new Object[]{idPasien, nama, jenisKelamin, no_Telp, alamat, email, level});
            }
            
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelPasien modelPasien) {
        String query = "INSERT INTO pasien (Id_Pasien, Nama, Jenis_Kelamin, No_Telp, Alamat, Email, Level) VALUES (?,?,?,?,?,?,?)";
        System.out.println(query);
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPasien.getIdPasien());
            System.out.println(modelPasien.getIdPasien());
            pst.setString(2, modelPasien.getNama());
            pst.setString(3, modelPasien.getJenisKelamin());
            pst.setString(4, modelPasien.getNoTelp());
            pst.setString(5, modelPasien.getAlamat());
            pst.setString(6, modelPasien.getEmail());
            pst.setString(7, modelPasien.getLevel());
            System.out.println(modelPasien.getLevel());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Pasien berhasil ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateData(ModelPasien modelPasien) {
        String query = "UPDATE pasien SET Nama=?, Jenis_Kelamin=?, No_Telp=?, Alamat=?, Email=?, Level=? WHERE Id_Pasien=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPasien.getNama());
            pst.setString(2, modelPasien.getJenisKelamin());
            pst.setString(3, modelPasien.getNoTelp());
            pst.setString(4, modelPasien.getAlamat());
            pst.setString(5, modelPasien.getEmail());
            pst.setString(6, modelPasien.getLevel());
            pst.setString(7, modelPasien.getIdPasien());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Pasien berhasil diperbarui");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteData(ModelPasien modelPasien) {
        String query = "DELETE FROM pasien WHERE Id_Pasien=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPasien.getIdPasien());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Pasien berhasil dihapus");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
