/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import component.PanelLoading;
import component.PanelVerifyCode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import main.Main;
import model.ModelMessage;
import model.ModelPengguna;
/**
 *
 * @author usER
 */
public class ServiceLogin {
    private Connection connection;
    public ServiceLogin() {
        connection = Koneksi.getConnection();
    }
    
//    Login
    public void login(ModelPengguna modelPengguna, PanelLoading panelLoading, JFrame frameLogin) {
        String query = "SELECT * FROM pengguna WHERE Username='"+modelPengguna.getUsername()+"' OR Email='"+modelPengguna.getEmail()+"' "
                + "AND Password='"+modelPengguna.getPassword()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    panelLoading.setVisible(true);
                    try {
                        if(rst.next()) {
                        String idPenguna = rst.getString("ID_Pengguna");
                        String namaPengguna = rst.getString("Nama");
                        String username = rst.getString("Username");
                        String level = rst.getString("Level");
                        ModelPengguna pengguna = new ModelPengguna();
                        pengguna.setIdpengguna(idPenguna);
                        pengguna.setNama(namaPengguna);
                        pengguna.setUsername(username);
                        pengguna.setLevel(level);
                        Main main = new Main(pengguna);
                        panelLoading.setVisible(false);
                        main.setVisible(true);
                        frameLogin.dispose();
                        } else {
                            panelLoading.setVisible(false);
                            JOptionPane.showMessageDialog(null, "Username atau Email\ndan Password Salah");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ServiceLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    private String generateVerifyCode() throws SQLException{
        DecimalFormat df = new DecimalFormat("000000");
        Random random = new Random();
        String verifyCode = df.format(random.nextInt(1000000));
        while(checkDuplicateVerifyCode(verifyCode)) {
            verifyCode = df.format(random.nextInt(1000000));
        }
        return verifyCode;
    }
    
    private boolean checkDuplicateVerifyCode(String verifyCode) throws SQLException{
        boolean check = false;
        String query = "SELECT ID_Pengguna FROM pengguna WHERE Kode_Verifikasi='"+verifyCode+"'";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rst = pst.executeQuery();
        if(rst.next()) {
            check = true;
        }
        
        return check;
    }
    
//    Forgot Password
    public boolean checkEmail(ModelPengguna modelPengguna) {
        boolean check = false;
        String query = "SELECT Email FROM pengguna WHERE Email='"+modelPengguna.getEmail()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                check = true;
            } else {
                JOptionPane.showMessageDialog(null, "Email tidak tedaftar");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return check;
    }
    
    public boolean cekVerifyCode(ModelPengguna modelPengguna) {
        boolean check = false;
        String query = "SELECT Kode_Verifikasi FROM pengguna WHERE Email='"+modelPengguna.getEmail()+"' AND "
                + "Kode_Verifikasi='"+modelPengguna.getKode_verifikasi()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                check = true;
            } else {
                JOptionPane.showMessageDialog(null, "Kode Verifikasi Salah");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    public void doneVerify(ModelPengguna modelPengguna) {
        String query = "UPDATE pengguna SET Password='"+modelPengguna.getPassword()+"', Kode_Verifikasi='' "
                + "WHERE Email='"+modelPengguna.getEmail()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password berhasil diubah");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void cancelVerify(ModelPengguna modelPengguna) {
        String query = "UPDATE pengguna SET Password='', Kode_Verifikasi='' WHERE Email='"+modelPengguna.getEmail()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getVerifyEmail(ModelPengguna modelPengguna, PanelVerifyCode verifyCode, PanelLoading panelLoading) {
        String query = "UPDATE pengguna SET Kode_Verifikasi=? WHERE Email=?";
        try {
            modelPengguna.setKode_verifikasi(generateVerifyCode());
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPengguna.getKode_verifikasi());
            pst.setString(2, modelPengguna.getEmail());
            if(modelPengguna.getConfirPass().equals(modelPengguna.getPassword())) {
                pst.executeUpdate();
                sendEmail(modelPengguna, verifyCode, panelLoading);
            } else {
                JOptionPane.showMessageDialog(null, "Konfirmasi Password Salah");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void sendEmail(ModelPengguna modelPengguna, PanelVerifyCode verifyCode, PanelLoading panelLoading) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                panelLoading.setVisible(true);
                ModelMessage modelMessage = new ServiceMail().sendMail(modelPengguna.getEmail(), modelPengguna.getKode_verifikasi());
                if(modelMessage.isSucces()) {
                    panelLoading.setVisible(false);
                    verifyCode.setVisible(true);
                } else{
                    panelLoading.setVisible(false);
                }
            }
        }).start();
    }
    
    
}
