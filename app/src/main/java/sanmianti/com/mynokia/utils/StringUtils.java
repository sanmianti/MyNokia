package sanmianti.com.mynokia.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author sanmianti
 * @description 字符串操作工具类
 * @date 2019/9/28 11:16
 */
public class StringUtils {


    /**
     * 判断是否是有效手机号
     * @param phoneNum   手机号码
     * @return  true:是手机号，false:不是手机号
     */
    public static boolean isMobile(String phoneNum) {
        Pattern pMobile = Pattern.compile("^(13|15|18|14|17|19|16)[0-9]{9}$");
        return pMobile.matcher(phoneNum).matches();
    }

    /**
     * 把时间毫秒值转换成指定格式的英文日期字符串
     *
     * @param milliSeconds 时长
     * @param pattern      yyyy-MM-dd HH:mm:ss yyyy年-MM月-dd日 yyyy-MM-dd E HH:mm:ss
     *
     * @return 日期字符串
     */
    public static String formatTime(long milliSeconds, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date date = new Date(milliSeconds);
        return format.format(date);
    }
}
