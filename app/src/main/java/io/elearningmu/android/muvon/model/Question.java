package io.elearningmu.android.muvon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    public int id;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("hint")
    @Expose
    public String hint;

    @SerializedName("explanation")
    @Expose
    public String explanation;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("options")
    @Expose
    public Object options = null;

    @SerializedName("correct")
    @Expose
    public String correct;

    @SerializedName("marks")
    @Expose
    public int marks;

    @SerializedName("user_marks")
    @Expose
    public int userMarks;

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("marked")
    @Expose
    public Object marked;

    @SerializedName("auto")
    @Expose
    public int auto;
}
