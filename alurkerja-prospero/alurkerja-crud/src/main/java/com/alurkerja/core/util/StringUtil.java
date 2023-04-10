package com.alurkerja.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static List<String> findWordsInMixedCase(String text) {
        Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(capitalizeFirst(matcher.group(0)));
        }
        return words;
    }

    public static String convertFieldToLabel(String fieldName){
        return String.join(" ", StringUtil.findWordsInMixedCase(fieldName));
    }

    public static String capitalizeFirst(String word) {
        return word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();
    }

}
