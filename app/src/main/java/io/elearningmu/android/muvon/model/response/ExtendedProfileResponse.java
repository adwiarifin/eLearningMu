package io.elearningmu.android.muvon.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.elearningmu.android.muvon.model.Field;

public class ExtendedProfileResponse {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("fields")
    @Expose
    public List<Field> fields = null;

}