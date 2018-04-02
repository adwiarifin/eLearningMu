package com.kesatriakeyboard.elearningmu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CourseList implements Serializable {

    @SerializedName("data")
    @Expose
    public Course course;

    @SerializedName("headers")
    @Expose
    public List<Object> headers = null;

    @SerializedName("status")
    @Expose
    public Integer status;
}
