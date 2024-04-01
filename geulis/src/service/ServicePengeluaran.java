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
import javax.swing.table.DefaultTableModel;
import model.ModelDetailPengeluaran;
import model.ModelJenisPengeluaran;
import model.ModelPengeluaran;
import model.PengeluaranSementara;
import swing.Pagination;
/**
 *
 * @author usER
 */
public class ServicePengeluaran {
    private Connection connection;
    
    public ServicePengeluaran() {
        connection = Koneksi.getConnection();
    }
    
        public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Pengeluaran) AS Jumlah FROM pengeluaran";
        int limit = 20;
        int count = 0;
        
        String query = "SELECT plrn.No_Pengeluaran, plrn.ID_Pengguna, pg.Nama, plrn.Tanggal_Pengeluaran, "
                + "plrn.Total_Pengeluaran, plrn.Deskripsi FROM pengeluaran plrn INNER JOIN pengguna pg "
                + "ON plrn.ID_Pengguna=pg.ID_Pengguna "
                + "ORDER BY No_Pengeluaran ASC LIMIT "+(page-1) * limit+","+limit+"";
        
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
                String noPengeluaran = rst.getString("No_Pengeluaran");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("Nama");
                String tgl = rst.getString("Tanggal_Pengeluaran");
                int total = rst.getInt("Total_Pengeluaran");
                String deskripsi = rst.getString("Deskripsi");
                tabmodel.addRow(new Object[]{noPengeluaran, idPengguna, namaPengguna, tgl, total, deskripsi});
            }
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
    
    public void addDataPengeluaran(ModelPengeluaran modelPengeluaran) {
        String query = "INSERT INTO pengeluaran(No_Pengeluaran, Tanggal_Pengeluaran, Total_Pengeluaran, Deskripsi, ID_Pengguna) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPengeluaran.getNoPengeluaran());
            pst.setString(2, modelPengeluaran.getTglPengeluaran());
            pst.setInt(3, modelPengeluaran.getTotal());
            pst.setString(4, modelPengeluaran.getDeskripsi());
            pst.setString(5, "USR-001");
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPengeluaran = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        Date date = new Date();
        String format = sdf.format(date);
        String query = "SELECT RIGHT (No_Pengeluaran, 3) AS Nomor FROM pengeluaran WHERE No_Pengeluaran LIKE 'PLRN-"+format+"%' ORDER BY Nomor DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int no = Integer.parseInt(rst.getString("Nomor"));
                no++;
                noPengeluaran = "PLRN-" + format + "-" + String.format("%03d", no);
            } else {
                noPengeluaran = "PLRN-" + format + "-001";
            }
        } catch(Exception ex) {
            
        }
        
        return noPengeluaran;
    }
    
    public String getNoJenis(ModelJenisPengeluaran modelJenis) {
        String noJenis = null;
        String query = "SELECT No_Jenis FROM jenis_pengeluaran WHERE Nama_Jenis='"+modelJenis.getJenis()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                noJenis = rst.getString("No_Jenis");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return noJenis;
    }
    
    public void addDataDetail(ModelDetailPengeluaran modelDetail, PengeluaranSementara sementara) {
        String query = "INSERT INTO detail_pengeluaran(No_Pengeluaran, No_Jenis, Detail_Jenis, Subtotal) VALUES(?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            String[] noJenis = sementara.getNoJenis();
            String[] detailJenis = sementara.getDetailJenis();
            int[] subtotal = sementara.getSubtotal();
            
            pst.setString(1, modelDetail.getModelPengeluaran().getNoPengeluaran());
            for(String no : noJenis) {
                pst.setString(2, no);
            }
            
            for(String detail : detailJenis) {
                pst.setString(3, detail);
            }
            
            for(int sub : subtotal) {
                pst.setInt(4, sub);
            }
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
