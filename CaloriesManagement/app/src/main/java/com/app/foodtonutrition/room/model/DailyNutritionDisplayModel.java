package com.app.foodtonutrition.room.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DailyNutritionDisplayModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String itemId;
    private double totalProtein;
    private double totalCarbohydrate;
    private double totalSugar;
    private double totalDietaryFibre;
    private double totalFat;
    private double totalSaturatedFat;

    public DailyNutritionDisplayModel(String itemId, double totalProtein, double totalCarbohydrate, double totalSugar, double totalDietaryFibre, double totalFat, double totalSaturatedFat) {
        this.itemId = itemId;
        this.totalProtein = totalProtein;
        this.totalCarbohydrate = totalCarbohydrate;
        this.totalSugar = totalSugar;
        this.totalDietaryFibre = totalDietaryFibre;
        this.totalFat = totalFat;
        this.totalSaturatedFat = totalSaturatedFat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public double getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(double totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public double getTotalSugar() {
        return totalSugar;
    }

    public void setTotalSugar(double totalSugar) {
        this.totalSugar = totalSugar;
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

    public double getTotalSaturatedFat() {
        return totalSaturatedFat;
    }

    public void setTotalSaturatedFat(double totalSaturatedFat) {
        this.totalSaturatedFat = totalSaturatedFat;
    }
}
