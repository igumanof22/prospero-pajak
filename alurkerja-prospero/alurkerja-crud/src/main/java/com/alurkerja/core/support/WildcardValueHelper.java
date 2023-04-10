package com.alurkerja.core.support;

public class WildcardValueHelper {

    private static final String WILDCARD_CHAR = "%";

    public static String decorate(String value) {
        return decorate(value, true, true);
    }

    public static String decorate(String value, boolean beginWildcard, boolean endWildcard) {
        if (beginWildcard && !value.startsWith(WILDCARD_CHAR)) {
            value = WILDCARD_CHAR + value;
        }
        if (endWildcard && !value.endsWith(WILDCARD_CHAR)) {
            value = value + WILDCARD_CHAR;
        }
        return value;
    }

}
