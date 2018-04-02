package com.kesatriakeyboard.elearningmu.util;

public class Config {

    // base
    public static final String BASE_URL = "https://www.elearningmu.com";

    // oauth
    public static final String AUTH_URL = BASE_URL + "/oauth/authorize";
    public static final String TOKEN_URL = BASE_URL + "/oauth/token";

    // public api
    public static final String TRACK_URL = BASE_URL + "/wp-json/wplms/v1/track";
    public static final String COURSE_URL = BASE_URL + "/wp-json/wplms/v1/course";
    public static final String FEATURED_COURSE_URL = BASE_URL + "/wp-json/wplms/v1/course/featured";
    public static final String POPULAR_COURSE_URL = BASE_URL + "/wp-json/wplms/v1/course/popular";
    public static final String SEARCH_COURSE_URL = BASE_URL + "/wp-json/wplms/v1/course/filters";
    public static final String CATEGORY_COURSE_URL = BASE_URL + "/wp-json/wplms/v1/course/category";
    public static final String TAXONOMY_COURSE_URL = BASE_URL + "/wp-json/wplms/v1/course/taxonomy";
    public static final String INSTRUCTOR_URL = BASE_URL + "/wp-json/wplms/v1/instructors";

    // protected api
    public static final String USER_ID_URL = BASE_URL + "/wp-json/wplms/v1/user";
    public static final String USER_PROFILE_URL = BASE_URL + "/wp-json/wplms/v1/user/profile";
    public static final String USER_COURSE_STATUS_URL = BASE_URL + "/wp-json/wplms/v1/user/course_status";
    public static final String USER_REVIEW_URL = BASE_URL + "/wp-json/wplms/v1/user/getreview";
}
