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

    public ModelPemeriksaan(String noPemeriksaan, String tglPemeriksaan, String deskripsi, int total, ModelPasien modelPasien, ModelKaryawan modelKaryawan) {
        this.noPemeriksaan = noPemeriksaan;
        this.tglPemeriksaan = tglPemeriksaan;
        this.deskripsi = deskripsi;
        this.total = total;
        this.modelPasien = modelPasien;
        this.modelKaryawan = modelKaryawan;
    }
    
    
    private String noPemeriksaan;
    private String tglPemeriksaan;
    private String deskripsi;
    private int total;
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
