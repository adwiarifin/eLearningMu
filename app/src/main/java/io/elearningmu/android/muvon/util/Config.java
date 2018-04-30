package io.elearningmu.android.muvon.util;

public class Config {

    // base
    public static final String BASE_URL = "https://www.elearningmu.com/wp-json/wplms/v1";
    public static final String CLIENT_ID = "lAD8KycBnrZQnXT0Schz8Fq";

    // public api
    public static final String TRACK_URL = BASE_URL + "/track";
    public static final String COURSE_URL = BASE_URL + "/course";
    public static final String FEATURED_COURSE_URL = BASE_URL + "/course/featured";
    public static final String POPULAR_COURSE_URL = BASE_URL + "/course/popular";
    public static final String SEARCH_COURSE_URL = BASE_URL + "/course/filters";
    public static final String CATEGORY_COURSE_URL = BASE_URL + "/course/category";
    public static final String TAXONOMY_COURSE_URL = BASE_URL + "/course/taxonomy";
    public static final String INSTRUCTOR_URL = BASE_URL + "/instructors";

    // protected api
    public static final String SIGNIN_URL = BASE_URL + "/user/signin";
    public static final String USER_URL = BASE_URL + "/user";
    public static final String USER_X_PROFILE_URL = BASE_URL + "/user/profile";
    public static final String USER_TAB_PROFILE_URL = BASE_URL + "/user/profile?tab=profile";
    public static final String USER_TAB_COURSE_URL = BASE_URL + "/user/profile?tab=courses";
    public static final String USER_COURSE_STATUS_URL = BASE_URL + "/user/course_status";
    public static final String USER_REVIEW_URL = BASE_URL + "/user/getreview";
}
