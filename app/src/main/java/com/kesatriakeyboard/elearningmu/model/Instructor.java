package com.kesatriakeyboard.elearningmu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Instructor implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("avatar")
    @Expose
    public String avatar;

    @SerializedName("sub")
    @Expose
    public String sub;

    @SerializedName("average_rating")
    @Expose
    public String averageRating;

    @SerializedName("student_count")
    @Expose
    public String studentCount;

    @SerializedName("course_count")
    @Expose
    public Integer courseCount;

    @SerializedName("bio")
    @Expose
    public Boolean bio;
}
