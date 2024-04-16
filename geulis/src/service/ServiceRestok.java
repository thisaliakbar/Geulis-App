/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelBarang;
import model.ModelDetailRestok;
import model.ModelRestok;
import model.RestokSementara;
import swing.Pagination;
import swing.StatusType;
/**
 *
 * @author usER
 */
public class ServiceRestok {
    private Connection connection;
    
    public ServiceRestok() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, Pagination pagination, DefaultTableModel tabmodel) {
        String sqlCount = "SELECT COUNT(No_Restok) AS Jumlah FROM restok";
        int limit = 20;
        int count = 0;
        
        String query = "SELECT rst.No_Restok, DATE_FORMAT(rst.Tanggal, '%d - %M - %Y') AS Tanggal_Tiba, "
                + "rst.ID_Pengguna, pgn.Nama, rst.Total_Biaya FROM restok rst JOIN pengguna pgn "
                + "ON rst.ID_Pengguna=pgn.ID_Pengguna ORDER BY No_Restok ASC "
                + "LIMIT "+(page-1) * limit+","+limit+"";
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
                String noRestok = rst.getString("No_Restok");
                String tglTiba = rst.getString("Tanggal_Tiba");
                String idPengguna = rst.getString("ID_Pengguna");
                String nama = rst.getString("Nama");
                int total = rst.getInt("Total_Biaya");
                tabmodel.addRow(new Object[]{noRestok, tglTiba, idPengguna, nama, total});
            }
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
    }
    
    public void getData(ModelRestok modelRestok, DefaultTableModel tabmodel) {
        String query = "SELECT dtl.Kode_Barang, brg.Nama_Barang, brg.Harga_Beli, dtl.Jumlah, dtl.SubTotal "
                + "FROM detail_pemesanan dtl JOIN barang brg ON dtl.Kode_Barang=brg.Kode_Barang "
                + "WHERE No_Pemesanan='"+modelRestok.getModelPemesanan().getNoPemesanan()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBarang = rst.getString("Kode_Barang");
                String namaBarang = rst.getString("Nama_Barang");
                int hargaBeli = rst.getInt("Harga_Beli");
                int jumlah = rst.getInt("Jumlah");
                int subtotal = rst.getInt("SubTotal");
                tabmodel.addRow(new Object[]{kodeBarang, namaBarang, hargaBeli, jumlah, subtotal});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelRestok modelRestok) {
        String query = "INSERT INTO restok (No_Restok, Tanggal, Total_Biaya, Keterangan, ID_Pengguna) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelRestok.getModelPemesanan().getNoPemesanan());
            pst.setString(2, modelRestok.getTglTiba());
            pst.setInt(3, modelRestok.getTotal());
            pst.setString(4, "Diterima");
            pst.setString(5, modelRestok.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil Menambah Stok Baru");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addDataDetail(ModelDetailRestok modelDetail, RestokSementara rs) {
        String query = "INSERT INTO detail_restok (No_Restok, Kode_Barang, Jumlah, SubTotal) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelDetail.getModelRestok().getModelPemesanan().getNoPemesanan());
            String[] kodeBarang = rs.getKodeBarang();
            int[] jumlah = rs.getJumlah();
            int[] subtotal = rs.getSubtotal();
            
            for(String kode : kodeBarang) {
                pst.setString(2, kode);
            }
            
            for(int jml : jumlah) {
                pst.setInt(3, jml);
            }
            
            for(int sub : subtotal) {
                pst.setInt(4, sub);
            }
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadDataStok(int page, Pagination pagination, DefaultTableModel model) {
        String sqlCount = "SELECT COUNT(Kode_Barang) AS Jumlah FROM barang";
        int limit = 15;
        int count = 0;
        String query = "SELECT brg.Kode_Barang, brg.Kode_Jenis, jb.Nama_Jenis, brg.Nama_Barang, brg.Satuan, brg.Stok FROM barang brg "
                + "JOIN jenis_barang jb ON brg.Kode_Jenis=jb.Kode_Jenis LIMIT "+(page-1) * limit+","+limit+"";
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
                String kodeJenis = rst.getString("Kode_Jenis");
                String jenisBarang = rst.getString("Nama_Jenis");
                String namaBarang = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int stok = rst.getInt("Stok");
                
                StatusType type = null;
                if(stok == 0) {
                    type = StatusType.Habis;
                } else if(stok <= 10) {
                    type = StatusType.Tambah;
                }
                
                ModelBarang modelBarang = new ModelBarang();
                modelBarang.setKode_Barang(kodeBarang);
                modelBarang.setKode_Jenis(kodeJenis);
                modelBarang.setJenis_Barang(jenisBarang);
                modelBarang.setNama_Barang(namaBarang);
                modelBarang.setSatuan(satuan);
                modelBarang.setStok(stok);
                
                ModelDetailRestok modelDetail = new ModelDetailRestok();
                modelDetail.setModelBarang(modelBarang);
                modelDetail.setType(type);
                
                model.addRow(modelDetail.toRowTable());
            }
            
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Integer> getStok() {
        List<Integer> listStok = new ArrayList<>();
        String query = "SELECT Stok FROM barang";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                listStok.add(rst.getInt("Stok"));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return listStok;
    }
}
