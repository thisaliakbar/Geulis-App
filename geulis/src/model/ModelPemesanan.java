/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPemesanan {

    public ModelPemesanan(String noPemesanan, String tglPemesanan, int total, String keterangan, ModelSupplier modelSupplier, ModelPengguna modelPengguna) {
        this.noPemesanan = noPemesanan;
        this.tglPemesanan = tglPemesanan;
        this.total = total;
        this.keterangan = keterangan;
        this.modelSupplier = modelSupplier;
        this.modelPengguna = modelPengguna;
    }

    public ModelPemesanan() {
    
    }
    
    private String noPemesanan;
    private String tglPemesanan;
    private int total;
    private String keterangan;
    private ModelSupplier modelSupplier;
    private ModelPengguna modelPengguna;
    
    public String getNoPemesanan() {
        return noPemesanan;
    }

    public void setNoPemesanan(String noPemesanan) {
        this.noPemesanan = noPemesanan;
    }

    public String getTglPemesanan() {
        return tglPemesanan;
    }

    public void setTglPemesanan(String tglPemesanan) {
        this.tglPemesanan = tglPemesanan;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public ModelSupplier getModelSupplier() {
        return modelSupplier;
    }

    public void setModelSupplier(ModelSupplier modelSupplier) {
        this.modelSupplier = modelSupplier;
    }

    public ModelPengguna getModelPengguna() {
        return modelPengguna;
    }

    public void setModelPengguna(ModelPengguna modelPengguna) {
        this.modelPengguna = modelPengguna;
    }
}
