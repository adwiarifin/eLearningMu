package io.elearningmu.android.muvon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meta {

    @SerializedName("access")
    @Expose
    public int access;

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("progress")
    @Expose
    public double progress;

    @SerializedName("marks")
    @Expose
    public int marks;

    @SerializedName("max")
    @Expose
    public int max;

    @SerializedName("questions")
    @Expose
    public List<Question> questions = null;

    @SerializedName("auto")
    @Expose
    public int auto;

    @SerializedName("retakes")
    @Expose
    public int retakes;

    @SerializedName("completion_message")
    @Expose
    public String completionMessage;
}
