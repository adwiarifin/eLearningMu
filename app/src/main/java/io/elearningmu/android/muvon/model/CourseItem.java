package io.elearningmu.android.muvon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseItem {

    @SerializedName("key")
    @Expose
    public int key;

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("duration")
    @Expose
    public int duration;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("meta")
    @Expose
    public List<Object> meta = null;

    @SerializedName("status")
    @Expose
    public int status;
}
