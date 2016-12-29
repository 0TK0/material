package com.zyq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TK on 2016/12/9.
 */
public class TimeUtil {
    private static final String FORMAT_NYR = "yyyy-MM-dd";

    public static String formatNYR(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_NYR);
        return sdf.format(date);
    }
}
