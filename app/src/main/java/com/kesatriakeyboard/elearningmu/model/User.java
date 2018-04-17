package com.kesatriakeyboard.elearningmu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("sub")
    @Expose
    public String sub;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("avatar")
    @Expose
    public String avatar;
}
