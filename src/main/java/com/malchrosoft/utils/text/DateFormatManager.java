/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.utils.text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class DateFormatManager
{
    /**
     * Formats the provided date in dd MMMM yyyy format, like 25 June 1976
     * @param date the date to format
     * @return the formatted date
     */
    public static String formatDate(Date date)
    {
        return getDefaultDateFormat().format(date);
    }

    public static String formatDateFromFormat(Date date, String format)
    {
        return getDateFormatFromFormat(format).format(date);
    }

    public static SimpleDateFormat getDefaultDateFormat()
    {
        return getDateFormatFromFormat("dd MMMM yyyy");
    }

    public static SimpleDateFormat getFullDateFormat()
    {
        return getDateFormatFromFormat("EEEE dd MMMM yyyy");
    }

    /**
     * Returns a simple date format from the provided format expression
     * @param format the format
     * @return the simple date format
     */
    public static SimpleDateFormat getDateFormatFromFormat(String format)
    {
        return new SimpleDateFormat(format);
    }
}
