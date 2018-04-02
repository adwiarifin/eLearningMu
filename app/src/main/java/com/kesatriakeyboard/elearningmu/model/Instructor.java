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
}
