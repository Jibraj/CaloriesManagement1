package com.app.foodtonutrition.room.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MeasurementModel implements Serializable {

    @PrimaryKey
    private int foodId;
    private String foodName;
    private double proteinPerGram;
    private double carbohydratePerGram;
    private double totalSugarPerGram;
    private double totalDietaryFibre;
    private double totalFat;
    private double saturatedFat;

    public MeasurementModel(int foodId,
                            String foodName,
                            double proteinPerGram,
                            double carbohydratePerGram,
                            double totalSugarPerGram,
                            double totalDietaryFibre,
                            double totalFat,
                            double saturatedFat) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.proteinPerGram = proteinPerGram;
        this.carbohydratePerGram = carbohydratePerGram;
        this.totalSugarPerGram = totalSugarPerGram;
        this.totalDietaryFibre = totalDietaryFibre;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getProteinPerGram() {
        return proteinPerGram;
    }

    public void setProteinPerGram(double proteinPerGram) {
        this.proteinPerGram = proteinPerGram;
    }

    public double getCarbohydratePerGram() {
        return carbohydratePerGram;
    }

    public void setCarbohydratePerGram(double carbohydratePerGram) {
        this.carbohydratePerGram = carbohydratePerGram;
    }

    public double getTotalSugarPerGram() {
        return totalSugarPerGram;
    }

    public void setTotalSugarPerGram(double totalSugarPerGram) {
        this.totalSugarPerGram = totalSugarPerGram;
    }

    public double getTotalDietaryFibre() {
        return totalDietaryFibre;
    }

    public void setTotalDietaryFibre(double totalDietaryFibre) {
        this.totalDietaryFibre = totalDietaryFibre;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }
}
