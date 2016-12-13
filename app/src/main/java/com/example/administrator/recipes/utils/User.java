package com.example.administrator.recipes.utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */

public class User {
    String img;//成品图
    String title;//名字
    String sumary;//简介
    String burden;//配料
    String ingredients;//材料
    ArrayList<User_Bz> arrayList;//步骤

    public User(String img, String title, String sumary, String burden, String ingredients, ArrayList<User_Bz> arrayList) {
        this.img = img;
        this.title = title;
        this.sumary = sumary;
        this.burden = burden;
        this.ingredients = ingredients;
        this.arrayList = arrayList;
    }

    public ArrayList<User_Bz> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<User_Bz> arrayList) {
        this.arrayList = arrayList;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
