package com.test.shop.model;

import com.google.gson.annotations.SerializedName;

public class Words {
    @SerializedName(value = "count")
    private String count;
    @SerializedName(value = "word")
    private String word;
    @SerializedName(value = "score")
    private String name;
    @SerializedName(value = "Words")
    private String words;
    @SerializedName(value = "seek")
    private String seek;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getSeek() {
        return seek;
    }

    public void setSeek(String seek) {
        this.seek = seek;
    }
}