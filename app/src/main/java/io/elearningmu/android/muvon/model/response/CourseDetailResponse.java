package io.elearningmu.android.muvon.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.elearningmu.android.muvon.model.CourseDetail;

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
