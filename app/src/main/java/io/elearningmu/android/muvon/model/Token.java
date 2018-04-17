package io.elearningmu.android.muvon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    @Expose
    public String accessToken;

    @SerializedName("client_id")
    @Expose
    public String clientId;

    @SerializedName("user_id")
    @Expose
    public Integer userId;

    @SerializedName("expires")
    @Expose
    public String expires;

    @SerializedName("scope")
    @Expose
    public Object scope;
}
