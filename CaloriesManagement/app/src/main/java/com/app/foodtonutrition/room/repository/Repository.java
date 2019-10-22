package com.app.foodtonutrition.room.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.app.foodtonutrition.room.database.AppDatabase;
import com.app.foodtonutrition.room.model.DailyNutritionDisplayModel;
import com.app.foodtonutrition.room.model.DailyNutritionModel;
import com.app.foodtonutrition.room.model.MeasurementModel;
import com.app.foodtonutrition.room.model.User;

import java.util.List;


public class Repository {

    private String DB_NAME = "food_to_nutrition_db";

    private AppDatabase appDatabase;

    public Repository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoManager().insertUser(user);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertFoodMeasurement(final List<MeasurementModel> measurementModels) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoManager().insertFoodMeasurement(measurementModels);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertDailyNutrition(final DailyNutritionModel dailyNutritionModel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoManager().insertDailyNutritions(dailyNutritionModel);
                return null;
            }
        }.execute();
    }

    public LiveData<Integer> checkAuthentication(final String email, final String password) {
        return appDatabase.daoManager().checkAuthentication(email, password);
    }

    public LiveData<User> getUser(final String email) {
        return appDatabase.daoManager().getUser(email);
    }

    public LiveData<List<MeasurementModel>> getFoods() {
        return appDatabase.daoManager().getFoods();
    }

    public LiveData<DailyNutritionDisplayModel> getTodaysNutritions(String itemId) {
        return appDatabase.daoManager().getTodaysNutritions(itemId);
    }

    public LiveData<List<DailyNutritionModel>> getTodaysFoodItems(String itemId) {
        return appDatabase.daoManager().getTodaysFoodItems(itemId);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteFoodItem(final DailyNutritionModel dailyNutritionModel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoManager().deleteDailyFood(dailyNutritionModel);
                return null;
            }
        }.execute();
    }

    public int checkUserExistance(final String email) {
       return appDatabase.daoManager().checkUserExistance(email);
    }
}