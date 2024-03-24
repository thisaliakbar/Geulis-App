/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import java.util.List;
/**
 *
 * @author usER
 */
public class ParamPemeriksaan {

    public ParamPemeriksaan(String noPemeriksaan, String tglPemeriksaan, String pasien, String karyawan, String total, List<FieldsPemeriksaan> fields) {
        this.noPemeriksaan = noPemeriksaan;
        this.tglPemeriksaan = tglPemeriksaan;
        this.pasien = pasien;
        this.karyawan = karyawan;
        this.total = total;
        this.fields = fields;
    }

    public ParamPemeriksaan() {
    }
    
    
    
    private String noPemeriksaan;
    private String tglPemeriksaan;
    private String pasien;
    private String karyawan;
    private String total;
    private List<FieldsPemeriksaan> fields;

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

    public String getPasien() {
        return pasien;
    }

    public void setPasien(String pasien) {
        this.pasien = pasien;
    }

    public String getKaryawan() {
        return karyawan;
    }

    public void setKaryawan(String karyawan) {
        this.karyawan = karyawan;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    public List<FieldsPemeriksaan> getFieldsReport() {
        return this.fields;
    }
    
    public void setFieldsReport(List<FieldsPemeriksaan> fields) {
        this.fields = fields;
    }
}
