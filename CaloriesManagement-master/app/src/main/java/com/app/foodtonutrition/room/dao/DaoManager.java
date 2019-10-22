package com.app.foodtonutrition.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.app.foodtonutrition.room.model.User;
import com.app.foodtonutrition.room.model.DailyNutritionDisplayModel;
import com.app.foodtonutrition.room.model.DailyNutritionModel;
import com.app.foodtonutrition.room.model.MeasurementModel;

import java.util.List;

@Dao
public interface DaoManager {

    @Insert
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFoodMeasurement(List<MeasurementModel> measurementModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDailyNutritions(DailyNutritionModel dailyNutritionModel);

    @Query("SELECT COUNT(*) FROM User WHERE email=:email AND password=:password")
    LiveData<Integer> checkAuthentication(String email, String password);

    @Query("SELECT * FROM User WHERE email=:email LIMIT 1")
    LiveData<User> getUser(String email);

    @Query("SELECT * FROM MeasurementModel")
    LiveData<List<MeasurementModel>> getFoods();

    @Query("SELECT dmn.id as id, " +
            "dmn.itemId as itemId, " +
            "SUM(dmn.protein) as totalProtein, " +
            "SUM(dmn.carbohydrate) as totalCarbohydrate, " +
            "SUM(dmn.totalSugar) as totalSugar, " +
            "SUM(dmn.dietaryFibre) as totalDietaryFibre, " +
            "SUM(dmn.totalFat) as totalFat, " +
            "SUM(dmn.saturatedFat) as totalSaturatedFat " +
            "FROM DailyNutritionModel as dmn " +
            "where itemId=:itemId")
    LiveData<DailyNutritionDisplayModel> getTodaysNutritions(String itemId);

    @Query("SELECT * " +
            "FROM DailyNutritionModel " +
            "where itemId=:itemId")
    LiveData<List<DailyNutritionModel>> getTodaysFoodItems(String itemId);

    @Delete
    void deleteDailyFood(DailyNutritionModel nutritionModel);

    @Query("SELECT COUNT(*) " +
            "FROM USER " +
            "where email=:email")
    Integer checkUserExistance(String email);
}
