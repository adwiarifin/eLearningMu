package com.kesatriakeyboard.elearningmu.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetail {

    @SerializedName("course")
    @Expose
    public Course course;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("curriculum")
    @Expose
    public List<Curriculum> curriculum = null;

    @SerializedName("reviews")
    @Expose
    public List<Object> reviews = null;

    @SerializedName("instructors")
    @Expose
    public List<Instructor> instructors = null;

    @SerializedName("purchase_link")
    @Expose
    public Boolean purchaseLink;
}
