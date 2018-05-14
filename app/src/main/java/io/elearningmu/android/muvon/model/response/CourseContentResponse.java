package io.elearningmu.android.muvon.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.elearningmu.android.muvon.model.Meta;

public class CourseContentResponse {

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("meta")
    @Expose
    public Meta meta;
}
