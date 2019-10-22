package com.app.foodtonutrition.room.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DailyNutritionModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String itemId;
    private String foodName;
    private double protein;
    private double carbohydrate;
    private double totalSugar;
    private double dietaryFibre;
    private double totalFat;
    private double saturatedFat;

    public DailyNutritionModel(String itemId, String foodName, double protein, double carbohydrate, double totalSugar, double dietaryFibre, double totalFat, double saturatedFat) {
        this.itemId = itemId;
        this.foodName = foodName;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.totalSugar = totalSugar;
        this.dietaryFibre = dietaryFibre;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getTotalSugar() {
        return totalSugar;
    }

    public void setTotalSugar(double totalSugar) {
        this.totalSugar = totalSugar;
    }

    public double getDietaryFibre() {
        return dietaryFibre;
    }

    public void setDietaryFibre(double dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
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
