/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPengeluaran {

    public ModelPengeluaran(String noPengeluaran, String tglPengeluaran, int total, String deskripsi) {
        this.noPengeluaran = noPengeluaran;
        this.tglPengeluaran = tglPengeluaran;
        this.total = total;
        this.deskripsi = deskripsi;
    }

    public ModelPengeluaran() {
    
    }    
    
    private String noPengeluaran;
    private String tglPengeluaran;
    private int total;
    private String deskripsi;
    private ModelPengeluaran modelPengeluaran;

    public String getNoPengeluaran() {
        return noPengeluaran;
    }

    public void setNoPengeluaran(String noPengeluaran) {
        this.noPengeluaran = noPengeluaran;
    }

    public String getTglPengeluaran() {
        return tglPengeluaran;
    }

    public void setTglPengeluaran(String tglPengeluaran) {
        this.tglPengeluaran = tglPengeluaran;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
