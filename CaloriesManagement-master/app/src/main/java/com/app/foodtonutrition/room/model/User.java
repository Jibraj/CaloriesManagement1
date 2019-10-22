package com.app.foodtonutrition.room.model;

//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String gender;
    private int age;
    private int weight;
    private String email;
    private String password;

    public User(String name, String gender, int age, int weight, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
