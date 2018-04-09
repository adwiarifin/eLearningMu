package com.kesatriakeyboard.elearningmu.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kesatriakeyboard.elearningmu.model.CourseDetail;

public class CourseDetailResponse {

    @SerializedName("data")
    @Expose
    public CourseDetail data;

    @SerializedName("headers")
    @Expose
    public List<Object> headers = null;

    @SerializedName("status")
    @Expose
    public Integer status;
}
