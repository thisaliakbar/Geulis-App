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

    public ModelPemesanan(String noPemesanan, String tglPemesanan, int total, String keterangan) {
        this.noPemesanan = noPemesanan;
        this.tglPemesanan = tglPemesanan;
        this.total = total;
        this.keterangan = keterangan;
    }

    public ModelPemesanan() {
    
    }
    
    private String noPemesanan;
    private String tglPemesanan;
    private int total;
    private String keterangan;

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
}
