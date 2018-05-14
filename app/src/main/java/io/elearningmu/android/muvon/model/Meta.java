package io.elearningmu.android.muvon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("access")
    @Expose
    public int access;

    @SerializedName("progress")
    @Expose
    public double progress;
}
