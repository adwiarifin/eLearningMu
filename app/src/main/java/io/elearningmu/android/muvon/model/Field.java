package io.elearningmu.android.muvon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("value")
    @Expose
    public String value;

}
