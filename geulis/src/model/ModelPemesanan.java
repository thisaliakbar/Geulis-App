/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import swing.StatusType;

/**
 *
 * @author usER
 */
public class ModelPemesanan {

    public ModelPemesanan(String noPemesanan, String tglPemesanan, StatusType type, String statusPemesanan, int totalPemesanan, double bayar, double kembali, String jenisPembayaran, ModelSupplier modelSupplier, ModelPengguna modelPengguna) {
        this.noPemesanan = noPemesanan;
        this.tglPemesanan = tglPemesanan;
        this.type = type;
        this.statusPemesanan = statusPemesanan;
        this.totalPemesanan = totalPemesanan;
        this.bayar = bayar;
        this.kembali = kembali;
        this.jenisPembayaran = jenisPembayaran;
        this.modelSupplier = modelSupplier;
        this.modelPengguna = modelPengguna;
    }

    public ModelPemesanan() {
    }

    
        
    private String noPemesanan;
    private String tglPemesanan;
    private StatusType type;
    private String statusPemesanan;
    private int totalPemesanan;
    private double bayar;
    private double kembali;
    private String jenisPembayaran;
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
    
    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
    }

    public String getStatusPemesanan() {
        return statusPemesanan;
    }

    public void setStatusPemesanan(String statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }

    public int getTotalPemesanan() {
        return totalPemesanan;
    }

    public void setTotalPemesanan(int totalPemesanan) {
        this.totalPemesanan = totalPemesanan;
    }

    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public double getKembali() {
        return kembali;
    }

    public void setKembali(double kembali) {
        this.kembali = kembali;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(String jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
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
    
    public Object[] toRowTable() {
        return new Object[]{noPemesanan, modelSupplier.getIdSupplier(), modelSupplier.getNamaSupplier(), 
            tglPemesanan, totalPemesanan, bayar, kembali, jenisPembayaran, type, statusPemesanan, 
            modelPengguna.getIdpengguna(), modelPengguna.getNama()};
    }
    
}
