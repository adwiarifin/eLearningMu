package io.elearningmu.android.muvon.model;

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
    public Object price = null; // Integer or Boolean

    @SerializedName("price_html")
    @Expose
    public Object priceHtml = null; // String or Array

    @SerializedName("total_students")
    @Expose
    public Integer totalStudents;

    @SerializedName("seats")
    @Expose
    public String seats;

    @SerializedName("start_date")
    @Expose
    public Object startDate = null; // Integer or Boolean

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

    @SerializedName("user_progress")
    @Expose
    public Double userProgress;

    @SerializedName("user_status")
    @Expose
    public String userStatus;
}
