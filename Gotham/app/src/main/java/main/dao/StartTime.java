package main.dao;

import java.util.Calendar;

/**
 * 全局可用的时间  单例模式:http://www.jianshu.com/p/8b59089a12f6
 * Created by liszdeng on 10/22/18.
 */

public class StartTime {
    private static Calendar instance;

    public StartTime(){}

    /**
     * 全局信息 只能调用一次
     */
    public static void init(Calendar start) {
        if (instance != null) {
            throw new RuntimeException(StartTime.class.getSimpleName() + " can not be initialized multiple times!");
        }
        instance = start;
    }
    public static Calendar getInstance() {
        if (instance == null){
            throw new RuntimeException(StartTime.class.getSimpleName() + "has not been initialized!");
        }

        return instance;
    }

    public static boolean isInitialized() {
        return (instance != null);
    }

}
