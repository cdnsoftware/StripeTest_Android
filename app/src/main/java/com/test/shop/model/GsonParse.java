package com.test.shop.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GsonParse {
    @SerializedName("count")
    private String count;

    @SerializedName("colbreak")
    private String colbreak;

    @SerializedName("name")
    private String name;

    @SerializedName("score")
    private String score;

    @SerializedName("Words")
    private List<Words> mWords = new ArrayList<Words>();

    @SerializedName("seek")
    private String seek;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getColbreak() {
        return colbreak;
    }

    public void setColbreak(String colbreak) {
        this.colbreak = colbreak;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Words> getmWords() {
        return mWords;
    }

    public void setmWords(List<Words> mWords) {
        this.mWords = mWords;
    }

    public String getSeek() {
        return seek;
    }

    public void setSeek(String seek) {
        this.seek = seek;
    }
}