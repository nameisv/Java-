package com.xiaoniucr.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 */
public class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式化成字符串
     *
     * @param date    Date
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN， 如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串解析成时间对象
     *
     * @param dateTimeString String
     * @param pattern        StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN，如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date dateParse(String dateTimeString, String pattern) {

        try {
            if (StringUtils.isEmpty(pattern)) {
                pattern = DateUtils.DATE_PATTERN;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据生日计算年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthday = sdf.parse(sdf.format(birthday));
            //Calendar：日历
            /*从Calendar对象中或得一个Date对象*/
            Calendar cal = Calendar.getInstance();
            /*把出生日期放入Calendar类型的bir对象中，进行Calendar和Date类型进行转换*/
            Calendar bir = Calendar.getInstance();
            bir.setTime(birthday);
            /*如果生日大于当前日期，则抛出异常：出生日期不能大于当前日期*/
            if (cal.before(birthday)) {
                throw new IllegalArgumentException("The birthday is before Now,It's unbelievable");
            }
            /*取出当前年月日*/
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayNow = cal.get(Calendar.DAY_OF_MONTH);
            /*取出出生年月日*/
            int yearBirth = bir.get(Calendar.YEAR);
            int monthBirth = bir.get(Calendar.MONTH);
            int dayBirth = bir.get(Calendar.DAY_OF_MONTH);
            /*大概年龄是当前年减去出生年*/
            int age = yearNow - yearBirth;
            /*如果出当前月小与出生月，或者当前月等于出生月但是当前日小于出生日，那么年龄age就减一岁*/
            if (monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)) {
                age--;
            }
            return age;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }


    /**
     * 获取本月第一天
     *
     * @return
     */
    public static String getMinDateOfMonth(String dateStr) {
        Date date = DateUtils.dateParse(dateStr,"yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return DateUtils.dateFormat(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     * 获取本月最后一天
     *
     * @return
     */
    public static String getMaxDateOfMonth(String dateStr) {
        Date date = DateUtils.dateParse(dateStr,"yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.dateFormat(calendar.getTime(),"yyyy-MM-dd");
    }


    /**
     * 根据年份获取年份下所有月份
     *
     * @param year
     * @return
     */

    public static String[] getYearFullMonth(String year) {


        String[] array = new String[12];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, 0);
        for (int i = 0; i < 12; i++) {
            array[i] = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, 1);
        }
        return array;
    }


    /**
     * 获取过去12个月
     */
    public static String[] getLast12Months() {
        String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); //要先+1,才能把本月的算进去
        for (int i = 0; i < 12; i++) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
            last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + fillZero((cal.get(Calendar.MONTH) + 1));
        }
        return last12Months;
    }


    /**
     * 获取某年某月的最后一天
     * @return
     *
     */
    public static String getLastDayOfMonth(Date month)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        cal.add(Calendar.MONTH,1);//月增加1天
        cal.add(Calendar.DAY_OF_MONTH,-1);//日期倒数一日,既得到本月最后一天
        return DateUtils.dateFormat(cal.getTime(),DateUtils.DATE_PATTERN);
    }


    /**
     * 获取某年某月的第一天
     * @param month
     * @return
     */
    public static String getFisrtDayOfMonth(Date month)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return DateUtils.dateFormat(cal.getTime(),DateUtils.DATE_PATTERN);
    }




    /**
     * 日期比较
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean dateCompare(Date date1,Date date2){

        if(date1.getTime() < date2.getTime()){
            return true;
        }
        return false;
    }


    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 取得当前日期所在周的第一天
     * <p>
     * param date
     * return
     */
    public static String getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return DateUtils.dateFormat(cal.getTime(),DateUtils.DATE_PATTERN);
    }

    /**
     * 取得当前日期所在周的最后一天
     * <p>
     * param date
     * return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }


    /**
     * 计算两个时间之间相隔小时
     * @param startDate
     * @param endDate
     * @return
     */
    public static Double getDifferHour(Date startDate, Date endDate) {
        long hourM = 1000 * 60 * 60;
        long differ = endDate.getTime() - startDate.getTime();
        Double hours = 0.0;
        //取整
        hours = Double.parseDouble(String.valueOf(differ / hourM));
        //取余
        long mill = differ % hourM;
        //超过30分钟，按1个小时计算，小于30分钟，按半小时计算
        if(mill > 30 * 60 * 1000){
            hours = hours + 1;
        }else{
            hours = hours + 0.5;
        }
        return hours;
    }


    /**
     * 格式化月份
     */
    public static String fillZero(int i){
        String month = "";
        if(i<10){
            month = "0" + i;
        }else{
            month = String.valueOf(i);
        }
        return month;
    }

    public static void main(String[] args) {
        System.out.println(30 / 60);
    }
}
