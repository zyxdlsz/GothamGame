package main.entity;

import main.gotham.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liszdeng on 9/23/18.
 */

public class People {
    private int id;
    private String name;
    private String describe;
    private int satify=0;
    private String[] favorite_food;
    private int[] favorite_days;
    private int picture;
    private String[][] conversations;
    private int[][] speakers;


    public People(){

    }

    public boolean isEmpty(){
        if(name.isEmpty())
            return true;
        else
            return false;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSatify() {
        return satify;
    }

    public void setSatify(int satify) {
        this.satify = satify;
    }

    public String[] getFavorite_food() {
        return favorite_food;
    }

    public void setFavorite_food(String[] favorite_food) {
        this.favorite_food = favorite_food;
    }

    public int[] getFavorite_days() {
        return favorite_days;
    }

    public void setFavorite_days(int[] favorite_days) {
        this.favorite_days = favorite_days;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String[][] getConversations() {
        return conversations;
    }

    public void setConversations(String[][] conversations) {
        this.conversations = conversations;
    }

    public int[][] getSpeakers() {
        return speakers;
    }

    public void setSpeakers(int[][] speakers) {
        this.speakers = speakers;
    }
}
