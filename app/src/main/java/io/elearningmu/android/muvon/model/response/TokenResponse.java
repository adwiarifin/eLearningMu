package io.elearningmu.android.muvon.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.elearningmu.android.muvon.model.CourseDetail;

public class TokenResponse {

    @SerializedName("access_token")
    @Expose
    public String access_token;

    @SerializedName("expires_in")
    @Expose
    public int expires_in;

    @SerializedName("token_type")
    @Expose
    public String token_type;

    @SerializedName("scope")
    @Expose
    public String scope;

    @SerializedName("refresh_token")
    @Expose
    public String refresh_token;
}
