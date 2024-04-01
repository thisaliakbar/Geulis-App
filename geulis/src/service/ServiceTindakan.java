/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import model.ModelTindakan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import swing.Pagination;

/**
 *
 * @author User
 */
public class ServiceTindakan {
       private Connection connection;

       public ServiceTindakan() {
           connection = Koneksi.getConnection();
       }
       public void loadData(int page, Pagination pagination, DefaultTableModel model) {
        String sqlCount = "SELECT COUNT(Kode_Tindakan) AS Jumlah FROM tindakan";
        int limit = 5;
        int count = 0;
        String query = "SELECT * FROM tindakan LIMIT "+(page-1) * limit+","+limit+"";
        System.out.println(query);
        try{
            PreparedStatement pst = connection.prepareStatement(sqlCount);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                count = rst.getInt("Jumlah");
            }
        }
        
        try {
            PreparedStatement pst = connection.prepareStatement(sqlCount);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                count = rst.getInt("Jumlah");
            }
            
            pst.close();
            rst.close();
            

        }
       
