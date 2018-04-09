package com.kesatriakeyboard.elearningmu.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Curriculum {

    @SerializedName("key")
    @Expose
    public Integer key;

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
    public Integer duration;

    @SerializedName("meta")
    @Expose
    public List<Object> meta = null;
}
