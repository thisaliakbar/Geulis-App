/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class RestokSementara {

    public RestokSementara(String[] kodeBarang, int[] jumlah, int[] subtotal) {
        this.kodeBarang = kodeBarang;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public RestokSementara() {
    }
    
    
    private String[] kodeBarang;
    private int[] jumlah;
    private int[] subtotal;

    public String[] getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String[] kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public int[] getJumlah() {
        return jumlah;
    }

    public void setJumlah(int[] jumlah) {
        this.jumlah = jumlah;
    }

    public int[] getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int[] subtotal) {
        this.subtotal = subtotal;
    }
}
