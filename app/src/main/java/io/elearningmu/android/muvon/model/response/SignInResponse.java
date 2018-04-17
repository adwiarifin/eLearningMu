package io.elearningmu.android.muvon.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.elearningmu.android.muvon.model.Token;
import io.elearningmu.android.muvon.model.User;

public class SignInResponse {

    @SerializedName("status")
    @Expose
    public Boolean status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("token")
    @Expose
    public Token token;

    @SerializedName("user")
    @Expose
    public User user;
}
