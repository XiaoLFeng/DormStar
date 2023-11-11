package com.xiaolfeng.dormstar.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 筱锋xiao_lfeng
 * @version v1.0.0
 */
public class SwitchingPeriod {
    /**
     *
     *
     * @param timestamp 时间戳
     * @return 返回是否在可登录时间段
     */
    public static boolean checkTime(long timestamp) {
        Date getDate = new Date(timestamp);
        // 检查星期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate);

        // 获取时间
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 检查日期
        if (dayOfWeek > Calendar.SUNDAY && dayOfWeek < Calendar.WEDNESDAY) {
            // 时间范围
            return 2300 >= Integer.parseInt(new SimpleDateFormat("HHmm").format(getDate));
        } else {
            return 2330 >= Integer.parseInt(new SimpleDateFormat("HHmm").format(getDate));
        }
    }
}
