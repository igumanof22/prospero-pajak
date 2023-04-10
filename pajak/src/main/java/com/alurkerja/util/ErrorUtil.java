package com.alurkerja.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class ErrorUtil {
    private ErrorUtil() {
        throw new IllegalStateException("this is utility class");
    }
    public static String getExceptionStacktrace(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos);
        e.printStackTrace(pw);
        pw.close();

        return baos.toString();
    }
}
