package io.elearningmu.android.muvon.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.elearningmu.android.muvon.model.CourseItem;

public class CourseIndexResponse {

    @SerializedName("current_unit_key")
    @Expose
    public int currentUnitKey;

    @SerializedName("courseitems")
    @Expose
    public List<CourseItem> courseitems = null;
}
