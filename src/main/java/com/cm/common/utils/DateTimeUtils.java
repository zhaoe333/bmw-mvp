package com.cm.common.utils;

import com.cm.common.constant.DimConstants;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author yunlu
 *
 */
public class DateTimeUtils {
	
	public static final String DateFormat="yyyy-MM-dd";
	
	public static final String TimeFormat="HH:mm:ss";

	public static final String DateTimeFormat="yyyy-MM-dd HH:mm:ss";

	public static final String MonthFormat = "yyyyMM";
	public static final String YearFormat = "yyyy";
	public static final String DayFormat = "yyyyMMdd";

	public static final String EChartsDateFormat="yyyy.MM.dd";

	public static Date getCurrentTime(){
		return Calendar.getInstance().getTime();
	}

	public static String formatDate(Date date, String format) {
		return FastDateFormat.getInstance(format).format(date);
	}
	
	public static String formatDate(long time, String format) {
		return FastDateFormat.getInstance(format).format(time);
	}
	
	public static String formatDate(String format){
		return FastDateFormat.getInstance(format).format(getCurrentTime());
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(Date date) {
		return FastDateFormat.getInstance(DateTimeFormat).format(date);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(long time) {
		return FastDateFormat.getInstance(DateTimeFormat).format(time);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * current time
	 * @return
	 */
	public static String formatDate() {
		return FastDateFormat.getInstance(DateTimeFormat).format(getCurrentTime());
	}

	public static Date parseDate(String date, String format)
			throws ParseException {
		return FastDateFormat.getInstance(format).parse(date);
	}
	
	public static String parseDate(String date, String format,String newFormat)
			throws ParseException {
		return FastDateFormat.getInstance(newFormat).format(FastDateFormat.getInstance(format).parse(date));
	}
	/** 
     *  
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
     *  
     * @param date 
     * @return 
     */  
    public static int getSeason(Date date) {  
  
        int season = 0;  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = 1;  
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = 2;  
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = 3;  
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = 4;  
            break;  
        default:  
            break;  
        }  
        return season;  
    }



	/**
     * Return the last month string in "yyyyMM" format
     * @param month string in "yyyyMM" format
     * @return last month string
     * @throws ParseException ParseException
     */
    public static String getLastMonth(String month) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(FastDateFormat.getInstance(MonthFormat).parse(month));
        cal.add(Calendar.MONTH, -1);
        return FastDateFormat.getInstance(MonthFormat).format(cal.getTime());
    }

    /**
     * Return the last year string in "yyyyMM" format
     * @param month string in "yyyyMM" format
     * @return last year string
     * @throws ParseException ParseException
     */
    public static String getLastYear(String month) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(FastDateFormat.getInstance(MonthFormat).parse(month));
        cal.add(Calendar.YEAR, -1);
        return FastDateFormat.getInstance(MonthFormat).format(cal.getTime());
    }
	
	/**
	 * 获取当前时间
	 * @return yyyyMMDD
	 * */
	public static String getCurrentDay() {
		return FastDateFormat.getInstance(DayFormat).format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取上一年年份
	 * @param month 格式如: yyyy
	 */
	public static String yearGetLastYear(String month) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(FastDateFormat.getInstance(YearFormat).parse(month));
		cal.add(Calendar.YEAR, -1);
		return FastDateFormat.getInstance(YearFormat).format(cal.getTime());
	}

	/**
	 * 求上一个月的今天的日期
	 * @param month yyyyMMDD
	 */
	public static String dayGetLastMonth(String month) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(FastDateFormat.getInstance(DayFormat).parse(month));
		cal.add(Calendar.MONTH, -1);
		return FastDateFormat.getInstance(DayFormat).format(cal.getTime());
	}

	/**
	 * 求上一年的今天的日期
	 * @param month yyyyMMDD
	 */
	public static String dayGetLastYear(String month) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(FastDateFormat.getInstance(DayFormat).parse(month));
		cal.add(Calendar.YEAR, -1);
		return FastDateFormat.getInstance(DayFormat).format(cal.getTime());
	}

	/**
	 * 根据日期获取年月日
	 * @author liujie
	 * */
	public static String getTime(String month, String timeType) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(FastDateFormat.getInstance(DayFormat).parse(month));
		String time = month;
		if(timeType.equals(DimConstants.DATE_YEAR)) {
			time = FastDateFormat.getInstance(YearFormat).format(cal.getTime());
		}else if(timeType.equals(DimConstants.DATE_MONTH)) {
			time = FastDateFormat.getInstance(MonthFormat).format(cal.getTime());
		}
		return time;
	}

	/**
	 * 根据时间获取（最近[上] 年/月/日）
	 * @param month	要获取的原时间  yyyyMMDD
	 * @param time 时间（年/月/日）
	 * @param
	 * */
	public static String getTimeByLately(String month, Integer time, String timeType) throws Exception{
		String resultTime = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(FastDateFormat.getInstance(DayFormat).parse(month));
		switch (timeType) {
			case DimConstants.DATE_YEAR:
				cal.add(Calendar.YEAR, -time);
				resultTime = FastDateFormat.getInstance(YearFormat).format(cal.getTime());
				break;
			case DimConstants.DATE_MONTH:
				cal.add(Calendar.MONTH, -time);
				resultTime = FastDateFormat.getInstance(MonthFormat).format(cal.getTime());
				break;
			case DimConstants.DATE_DAY:
				cal.add(Calendar.DATE, -time);
				resultTime = FastDateFormat.getInstance(DayFormat).format(cal.getTime());
				break;
			case DimConstants.DATE_QUARTER:
				//获取当前年&季度
				String year = "";
				Integer nowQuarter = (cal.get(Calendar.MONTH) + 1) % 4 == 0 ? (cal.get(Calendar.MONTH) + 1) / 4 : (cal.get(Calendar.MONTH) + 1) / 4 + 1;	//获取当前季度
				Integer diffQuarter = time - nowQuarter;
				if(diffQuarter < 0) {	//当前季度大于最近季度
					year = FastDateFormat.getInstance(YearFormat).format(cal.getTime());
					resultTime = year + "," + (nowQuarter - time);
				}else {
					//当前季度小/等于最近季度 年份-1
					cal.add(Calendar.YEAR, -1);
					if(diffQuarter == 0) {	//季度相等
						year = FastDateFormat.getInstance(YearFormat).format(cal.getTime());
						resultTime = year + ",4";	//默认4个季度
					}else {
						//判断剩余最近季度跨年数
						Integer number = diffQuarter / 4;
						if(number < 1) {	//不够4个季度(一年)
							year = FastDateFormat.getInstance(YearFormat).format(cal.getTime());
							resultTime = year + "," + (4 - number);	//默认4个季度
						}else {	// 剩余季度够一年
							cal.add(Calendar.YEAR, -(number));
							year = FastDateFormat.getInstance(YearFormat).format(cal.getTime());
							Integer numberYu = diffQuarter % 4;
							if(numberYu == 0) {	//判断是否为整年
								resultTime = year + ",4";
							}else {
								resultTime = year + "," + (4 - numberYu);
							}
						}
					}
				}
				break;
		}
		return resultTime;
	}

	/**
	 * 获取上一个周日
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastSunDay(Date date)throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.WEEK_OF_YEAR,-1);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		cleanTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当周周日
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getSunDay(Date date)throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		cleanTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取上个月的最后一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDayOfLastMonth(Date date)
			throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.DAY_OF_YEAR,-1);
		cleanTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当月的最后一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDayOfMonth(Date date)
			throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.DAY_OF_YEAR,-1);
		cleanTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取昨天 零时零分零秒
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getYesterday(Date date)
			throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,-1);
		cleanTime(calendar);
		return calendar.getTime();
	}

	public static Date addDay(Date date,int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,days);
		cleanTime(calendar);
		return calendar.getTime();
	}

	public static Date addDay(Date date,String timeType,int num){
		switch(timeType){
			case "day":
				return addDay(date,num);
			case "week":
				return addDay(date,num*7);
			case "month":
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.MONTH,num);
				cleanTime(calendar);
				return calendar.getTime();
			default:
				return addDay(date,num);
		}
	}

	private static void cleanTime(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
	}

	/**
	 * 用来format 不同timetype的时间数据
	 * @param date
	 * @param timeType
	 * @return
	 */
	public static String formatByTimeType(Date date,String timeType){
		switch(timeType){
			case "day":
				return FastDateFormat.getInstance("yyyy.MM.dd").format(date);
			case "week":
				Date startDate = addDay(date,-6);
				return FastDateFormat.getInstance("MM.dd").format(startDate)+"-"+
						FastDateFormat.getInstance("MM.dd").format(date);
			case "month":
				return FastDateFormat.getInstance("yyyy.MM").format(date);
			default:
				return FastDateFormat.getInstance("yyyy.MM.dd").format(date);
		}
	}

	/**
	 * 用来转换不同timetype的不同date
	 * @param date
	 * @param timeType
	 * @return
	 */
	public static Date convertByTimeType(Date date,String timeType)throws Exception{
		switch(timeType){
			case "day":
				return date;
			case "week":
				return getSunDay(date);
			case "month":
				return getLastDayOfMonth(date);
			default:
				return date;
		}
	}
}
