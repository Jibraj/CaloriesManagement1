package com.app.foodtonutrition.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.app.foodtonutrition.room.dao.DaoManager;
import com.app.foodtonutrition.room.model.DailyNutritionModel;
import com.app.foodtonutrition.room.model.MeasurementModel;
import com.app.foodtonutrition.room.model.User;


@Database(entities = {User.class, MeasurementModel.class, DailyNutritionModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoManager daoManager();
}
