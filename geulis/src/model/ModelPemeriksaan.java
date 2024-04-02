/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPemeriksaan {

    public ModelPemeriksaan() {
        
    }

    public ModelPemeriksaan(String noPemeriksaan, String tglPemeriksaan, String deskripsi, int total, double bayar, double kembalian, String jenisPembayaran, ModelPasien modelPasien, ModelKaryawan modelKaryawan) {
        this.noPemeriksaan = noPemeriksaan;
        this.tglPemeriksaan = tglPemeriksaan;
        this.deskripsi = deskripsi;
        this.total = total;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.jenisPembayaran = jenisPembayaran;
        this.modelPasien = modelPasien;
        this.modelKaryawan = modelKaryawan;
    }
    
    private String noPemeriksaan;
    private String tglPemeriksaan;
    private String deskripsi;
    private int total;
    private double bayar;
    private double kembalian;
    private String jenisPembayaran;
    private ModelPasien modelPasien;
    private ModelKaryawan modelKaryawan;

    public String getNoPemeriksaan() {
        return noPemeriksaan;
    }

    public void setNoPemeriksaan(String noPemeriksaan) {
        this.noPemeriksaan = noPemeriksaan;
    }

    public String getTglPemeriksaan() {
        return tglPemeriksaan;
    }

    public void setTglPemeriksaan(String tglPemeriksaan) {
        this.tglPemeriksaan = tglPemeriksaan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public double getKembalian() {
        return kembalian;
    }

    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(String jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }

    public ModelPasien getModelPasien() {
        return modelPasien;
    }

    public void setModelPasien(ModelPasien modelPasien) {
        this.modelPasien = modelPasien;
    }

    public ModelKaryawan getModelKaryawan() {
        return modelKaryawan;
    }

    public void setModelKaryawan(ModelKaryawan modelKaryawan) {
        this.modelKaryawan = modelKaryawan;
    }
}
