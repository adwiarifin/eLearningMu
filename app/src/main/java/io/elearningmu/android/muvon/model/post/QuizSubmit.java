package io.elearningmu.android.muvon.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.elearningmu.android.muvon.model.Question;

public class QuizSubmit {

    @SerializedName("quiz_id")
    @Expose
    public int quizId;

    @SerializedName("course_id")
    @Expose
    public int courseId;

    @SerializedName("results")
    @Expose
    public List<Question> results = null;
}
