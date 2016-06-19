package sun.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author Cori
 * @date 2015-10-30
 *
 */
public class DateUtil {

	
	/**
	 * 获取当前时间
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = new Date(System.currentTimeMillis());
		String date = sdf.format(d);
		return date;
	}
	
	/**
	 * 获取指定格式化时间字符串
	 * @param obj 时间格式
	 * yyyyMMddHHmmss
	 * yyyyMMdd
     * yyyyMMddHHmmssSSS
     * yyMMddHHmmssSSS
	 */
	public static String getDateFormat(String obj) {
		String date = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(obj);
			Date d = new Date(System.currentTimeMillis());
			date = sdf.format(d);
		} catch (Exception e) {
			date = "";
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 比较2个日期相差秒数
	 * @param DATE1 开始时间
	 * @param DATE2 结束时间
	 */
	public static long compare_date2(String DATE1, String DATE2) {
         
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        long diff =0;
         try {
             Date dt1 = df.parse(DATE1);
             Date dt2 = df.parse(DATE2);
             diff = (dt2.getTime() - dt1.getTime())/1000;
             
         } catch (Exception exception) {
             exception.printStackTrace();
         }
         return diff;
     }
}
