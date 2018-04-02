package com.kesatriakeyboard.elearningmu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("date_created")
    @Expose
    public Integer dateCreated;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("price")
    @Expose
    public Boolean price;

    @SerializedName("price_html")
    @Expose
    public String priceHtml = null;

    @SerializedName("total_students")
    @Expose
    public Integer totalStudents;

    @SerializedName("seats")
    @Expose
    public String seats;

    @SerializedName("start_date")
    @Expose
    public Integer startDate;

    @SerializedName("average_rating")
    @Expose
    public String averageRating;

    @SerializedName("rating_count")
    @Expose
    public String ratingCount;

    @SerializedName("featured_image")
    @Expose
    public String featuredImage;

    @SerializedName("categories")
    @Expose
    public List<Category> categories = null;

    @SerializedName("instructor")
    @Expose
    public Instructor instructor;

    @SerializedName("menu_order")
    @Expose
    public Integer menuOrder;
}
