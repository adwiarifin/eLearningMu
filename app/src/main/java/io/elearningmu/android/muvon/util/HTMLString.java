package io.elearningmu.android.muvon.util;

import org.jsoup.Jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLString {

    public static boolean hasHTMLTags(String text){
        String HTML_PATTERN = "<[a-z][\\s\\S]*>";
        Pattern pattern = Pattern.compile(HTML_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static String stripHTML(String html) {
        String result = Jsoup.parse(html).text();
        result = result.replaceAll("<em>", "");
        result = result.replaceAll("</em>", "");
        return result;
    }

    public static String parseHTML(String html) {
        String result = Jsoup.parse(html).text();
        return result;
    }
}
