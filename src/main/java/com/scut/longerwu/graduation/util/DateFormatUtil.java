package com.scut.longerwu.graduation.util;

import java.text.*;
import java.util.*;

public class DateFormatUtil {
    private static final String pattern="yyyy/MM/dd";

    private static SimpleDateFormat format = new SimpleDateFormat(pattern);

    public static String formatDate(Long time){

        return format.format(new Date(time));
    }
}
