package com.kesatriakeyboard.elearningmu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kesatriakeyboard.elearningmu.model.response.CourseResponse;

import java.io.Serializable;
import java.util.List;

public class CourseList implements Serializable {

    @SerializedName("list")
    @Expose
    public List<CourseResponse> listCourses;
}
