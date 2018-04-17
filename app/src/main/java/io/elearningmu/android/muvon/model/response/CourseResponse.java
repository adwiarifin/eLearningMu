package io.elearningmu.android.muvon.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.elearningmu.android.muvon.model.Course;

import java.io.Serializable;
import java.util.List;

public class CourseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    public Course course;

    @SerializedName("headers")
    @Expose
    public List<Object> headers = null;

    @SerializedName("status")
    @Expose
    public Integer status;
}
