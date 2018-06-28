package io.elearningmu.android.muvon.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // shared preference mode
    private static final int PRIVATE_MODE = 0;

    // Shared Preferences file name
    private static final String PREF_NAME = "io.elearningmu.android.muvon.MuvonPreference";

    private static final String QUIZ_START_MESSAGE = "QuizStartMessage";
    private static final String QUIZ_FINISH_MESSAGE = "QuizFinishMessage";
    private static final String QUIZ_START_TIME = "QuizStartTime";
    private static final String IS_QUIZ_STARTED = "IsQuizStarted";
    private static final String QUIZ_ID = "QuizId";
    private static final String COURSE_ID = "CourseId";

    public PreferenceHelper(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setCourseId(int courseId) {
        editor = pref.edit();
        editor.putInt(COURSE_ID, 0);
        editor.apply();
    }

    public void setQuizId(int quizId) {
        editor = pref.edit();
        editor.putInt(QUIZ_ID, 0);
        editor.apply();
    }

    public void setQuizStarted(boolean quizStarted) {
        editor = pref.edit();
        editor.putBoolean(IS_QUIZ_STARTED, quizStarted);
        editor.apply();
    }

    public void setQuizStartTime(long startTime) {
        editor = pref.edit();
        editor.putLong(QUIZ_START_TIME, startTime);
        editor.apply();
    }

    public void setQuizFinishMessage(String finishMessage) {
        editor = pref.edit();
        editor.putString(QUIZ_FINISH_MESSAGE, finishMessage);
        editor.apply();
    }

    public void setQuizStartMessage(String startMessage) {
        editor = pref.edit();
        editor.putString(QUIZ_START_MESSAGE, startMessage);
        editor.apply();
    }

    public int getCourseId() {
        return pref.getInt(COURSE_ID, 0);
    }

    public int getQuizId() {
        return pref.getInt(QUIZ_ID, 0);
    }

    public boolean isQuisStarted() {
        return pref.getBoolean(IS_QUIZ_STARTED, false);
    }

    public long getQuizStartTime() {
        return pref.getLong(QUIZ_START_TIME, 0);
    }

    public String getQuizFinishMessage() {
        return pref.getString(QUIZ_FINISH_MESSAGE, "");
    }

    public String getQuizStartMessage() {
        return pref.getString(QUIZ_START_MESSAGE, "");
    }
}
