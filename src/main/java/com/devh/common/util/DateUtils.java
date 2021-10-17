package com.devh.common.util;

import com.devh.common.constant.DateConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * <pre>
 * Description :
 *     날짜 관련 유틸
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-09
 * </pre>
 */
public class DateUtils {
    private final SimpleDateFormat SDF_YM = new SimpleDateFormat("yyyy-MM");
    private final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Pattern DASH_PATTERN = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    private final Pattern JAP_PATTERN = Pattern.compile("^[0-9]{4}年[0-9]{2}月[0-9]{2}日$");
    private final Pattern SLASH_PATTERN = Pattern.compile("^[0-9]{4}/[0-9]{2}/[0-9]{2}$");

    /* Singleton */
    private static DateUtils instance;
    public static DateUtils getInstance() {
        if(instance == null)
            instance = new DateUtils();
        return instance;
    }
    /* Singleton */

    /**
     * <pre>
     * Description
     *     현재 날짜를 yyyy-MM-dd 형식으로 반환
     * ===============================================
     * Parameters
     *
     * Returns
     *     "yyyy-MM-dd"
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-09
     * </pre>
     */
    public String getYMDNow() {
        return SDF_YMD.format(new Date());
    }
    public String getYMNow() {
        return SDF_YM.format(new Date());
    }

    /**
     * <pre>
     * Description
     *     현재 날짜를 yyyy-MM-dd HH:mm:ss 형식으로 반환
     * ===============================================
     * Parameters
     *
     * Returns
     *     "yyyy-MM-dd HH:mm:ss"
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-09
     * </pre>
     */
    public String getYMDHMSNow() {
        return SDF_YMDHMS.format(new Date());
    }

    /**
     * <pre>
     * Description
     *     현재 날짜의 4자리 년도 반환
     * ===============================================
     * Parameters
     *
     * Returns
     *     "yyyy"
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-09
     * </pre>
     */
    public String getCurrentYear4Digit() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * <pre>
     * Description
     *     현재 날짜의 2자리 월 반환
     * ===============================================
     * Parameters
     *
     * Returns
     *     "MM"
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-09
     * </pre>
     */
    public String getCurrentMonth2Digit() {
        return String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    /**
     * <pre>
     * Description
     *     현재 날짜의 2자리 일 반환
     * ===============================================
     * Parameters
     *
     * Returns
     *     "dd"
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-09
     * </pre>
     */
    public String getCurrentDate2Digit() {
        return String.format("%02d", Calendar.getInstance().get(Calendar.DATE));
    }

    /**
     * <pre>
     * Description
     *     DateContant형으로 변경해주는 메소드
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    public DateConstant getDateConstant(String target) {
        target = target.toLowerCase();
        if(DateConstant.JANUARY.getLowerCaseValue().equals(target))
            return DateConstant.JANUARY;
        else if(DateConstant.FEBRUARY.getLowerCaseValue().equals(target))
            return DateConstant.FEBRUARY;
        else if(DateConstant.MARCH.getLowerCaseValue().equals(target))
            return DateConstant.MARCH;
        else if(DateConstant.APRIL.getLowerCaseValue().equals(target))
            return DateConstant.APRIL;
        else if(DateConstant.MAY.getLowerCaseValue().equals(target))
            return DateConstant.MAY;
        else if(DateConstant.JUNE.getLowerCaseValue().equals(target))
            return DateConstant.JUNE;
        else if(DateConstant.JULY.getLowerCaseValue().equals(target))
            return DateConstant.JULY;
        else if(DateConstant.AUGUST.getLowerCaseValue().equals(target))
            return DateConstant.AUGUST;
        else if(DateConstant.SEPTEMBER.getLowerCaseValue().equals(target))
            return DateConstant.SEPTEMBER;
        else if(DateConstant.OCTOBER.getLowerCaseValue().equals(target))
            return DateConstant.OCTOBER;
        else if(DateConstant.NOVEMBER.getLowerCaseValue().equals(target))
            return DateConstant.NOVEMBER;
        else if(DateConstant.DECEMBER.getLowerCaseValue().equals(target))
            return DateConstant.DECEMBER;
        else
            return null;
    }

    /**
     * <pre>
     * Description
     *     yyyy-MM-dd 형식인지 체크
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    public boolean checkDashPattern(String target) {
        return DASH_PATTERN.matcher(target).matches();
    }
    /**
     * <pre>
     * Description
     *     yyyy/MM/dd 형식인지 체크
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    public boolean checkSlashPattern(String target) {
        return SLASH_PATTERN.matcher(target).matches();
    }
    /**
     * <pre>
     * Description
     *     yyyy年MM月dd日형식인지 체크
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    public boolean checkJapPattern(String target) {
        return JAP_PATTERN.matcher(target).matches();
    }
}
