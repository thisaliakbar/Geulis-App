/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelDetailPemeriksaan {

    public ModelDetailPemeriksaan(ModelPemeriksaan modelPemeriksaan, ModelTindakan modelTindakan) {
        this.modelPemeriksaan = modelPemeriksaan;
        this.modelTindakan = modelTindakan;
    }

    public ModelDetailPemeriksaan() {
    
    }
    
    
    private ModelPemeriksaan modelPemeriksaan;
    private ModelTindakan modelTindakan;
    private int subtotal;

    public ModelPemeriksaan getModelPemeriksaan() {
        return modelPemeriksaan;
    }

    public void setModelPemeriksaan(ModelPemeriksaan modelPemeriksaan) {
        this.modelPemeriksaan = modelPemeriksaan;
    }

    public ModelTindakan getModelTindakan() {
        return modelTindakan;
    }

    public void setModelTindakan(ModelTindakan modelTindakan) {
        this.modelTindakan = modelTindakan;
    }
}
