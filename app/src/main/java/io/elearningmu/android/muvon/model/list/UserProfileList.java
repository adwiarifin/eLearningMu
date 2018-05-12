package io.elearningmu.android.muvon.model.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.elearningmu.android.muvon.model.response.ExtendedProfileResponse;

public class UserProfileList {

    @SerializedName("list")
    @Expose
    public List<ExtendedProfileResponse> listExtendedProfile;
}
