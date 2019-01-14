package main.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;

import main.dbhelper.TimeDBHelper;

/**
 * 全局可用的时间  单例模式:http://www.jianshu.com/p/8b59089a12f6
 * Created by liszdeng on 10/22/18.
 */

public class StartTime {
    private static Calendar instance;
    private static long millitime;
    private Context context;
    private static TimeDBHelper timeDBHelper;
    //setTimeInMillis

    public StartTime(Context context){
        this.context=context;
        timeDBHelper=new TimeDBHelper(context);
    }

    /**
     * 全局信息 只能调用一次
     */
    public static void init(Calendar start) {
        if (isInitialized()) {
            throw new RuntimeException(StartTime.class.getSimpleName() + " can not be initialized multiple times!");
        }
        millitime=start.getTimeInMillis();
        SQLiteDatabase db = null;

        try {
            db = timeDBHelper.getWritableDatabase();
            db.beginTransaction();
            db.execSQL("insert into " + timeDBHelper.TABLE_NAME + " (starttime) values ("+millitime+")");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("mylog", "init error", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public static Calendar getInstance() {
        if (!isInitialized()){
            throw new RuntimeException(StartTime.class.getSimpleName() + "has not been initialized!");
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = timeDBHelper.getReadableDatabase();

            // select starttime from Orders
            cursor = db.query(timeDBHelper.TABLE_NAME, new String[]{"starttime"},
                    null,null, null, null, null);

            if (cursor.moveToFirst()) {
                millitime = cursor.getLong(0);
            }
        }
        catch (Exception e) {
            Log.e("mylog", "get time", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        instance=Calendar.getInstance();
        instance.setTimeInMillis(millitime);
        return instance;
    }

    public static boolean isInitialized() {
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = timeDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(timeDBHelper.TABLE_NAME, new String[]{"COUNT(starttime)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e("mylog", "db exception", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

}
