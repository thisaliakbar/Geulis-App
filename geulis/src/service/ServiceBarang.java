/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import model.ModelBarang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import swing.Pagination;
/**
 *
 * @author User
 */
public class ServiceBarang {
    private Connection connection;

    public ServiceBarang() {
       connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, Pagination pagination, DefaultTableModel model) {
        String sqlCount = "SELECT COUNT(Kode_Barang) AS Jumlah FROM barang";
        int limit = 5;
        int count = 0;
        String query = "SELECT * FROM barang LIMIT "+(page-1) * limit+","+limit+"";
        System.out.println(query);
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
                String kodeBarang = rst.getString("Kode_Barang");
                String namaBarang = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hargaBeli = rst.getInt("Harga_Beli");
                int hargaJual = rst.getInt("Harga_Jual");
                int stok = rst.getInt("Stok");
                model.addRow(new Object[]{kodeBarang, namaBarang, satuan, hargaBeli, hargaJual, stok});
            }
            
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelBarang modelBarang) {
     String query = "INSERT INTO barang (Kode_Barang, Nama_Barang, Satuan, Harga_Beli, Harga_Jual, Stok) VALUES (?,?,?,?,?,?)"; 
     try {
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, modelBarang.getKode_Barang());
         pst.setString(2, modelBarang.getNama_Barang());
         pst.setString(3, modelBarang.getSatuan());
         pst.setInt(4, modelBarang.getHarga_Beli());
         pst.setInt(5, modelBarang.getHarga_Jual());
         pst.setInt(6, modelBarang.getStok());
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Barang berhasil ditambahkan");
     } catch(Exception ex) {
         ex.printStackTrace();
     }
    }
    
    public void updateData(ModelBarang modelBarang) {
     String query = "UPDATE barang SET Nama_Barang=?, Satuan=?, Harga_Beli=?, Harga_Jual=?, Stok=? WHERE Kode_Barang=?";
     try {
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, modelBarang.getNama_Barang());
         pst.setString(2, modelBarang.getSatuan());
         pst.setInt(3, modelBarang.getHarga_Beli());
         pst.setInt(4, modelBarang.getHarga_Jual());
         pst.setInt(5, modelBarang.getStok());
         pst.setString(6, modelBarang.getKode_Barang());
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Barang berhasil diperbarui");
     } catch(Exception ex) {
         ex.printStackTrace();
     }
    }
    
    public void deleteData(ModelBarang modelBarang) {
     String query = "DELETE FROM barang WHERE Kode_Barang=?";
     try {
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, modelBarang.getKode_Barang());
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Barang berhasil dihapus");
     } catch(Exception ex) {
         ex.printStackTrace();
     }
    }
}
