package main.gotham;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import main.views.WalkView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import main.dao.StartTime;
import main.entity.Walker;

/**
 * Created by liszdeng on 9/20/18.
 */

public class StartActivity extends AppCompatActivity {
    private static final int msgKey1 = 1;
    private static final int msgKey2 = 2;
    private static final int msgKey3 = 3;

    private int bcount=0;
    private FrameLayout guestfl;

    private Calendar startDay = Calendar.getInstance();

    private TextView gTime;
    private ProgressBar strengthProgress;
    MediaPlayer mediaPlayer;
    private int current=0;

    WalkView[] walkguests=new WalkView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.start_main);
        Log.v("mylog","on create start activity");

        guestfl=(FrameLayout)findViewById(R.id.peoplefl);

        StartTime startTime=new StartTime(this);
        if(!startTime.isInitialized()){
            startTime.init(startDay);
        }
        startDay=startTime.getInstance();
        gTime=(TextView) findViewById(R.id.time);
        gTime.setText(getTime());
        new TimeThread().start();

        mediaPlayer=MediaPlayer.create(this,R.raw.demo3);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        strengthProgress= (ProgressBar) findViewById(R.id.strength);
        setProgress(strengthProgress.getProgress() * 100);

        Button button = (Button) findViewById(R.id.food);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent food = new Intent(StartActivity.this, FoodActivity.class);
                Log.v("mylog","into food page");
                startActivity(food);
            }
        });

        button = (Button) findViewById(R.id.guest);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent people = new Intent(StartActivity.this, PeopleActivity.class);
                Log.v("mylog","into guest page");
                startActivity(people);
            }
        });


        button = (Button) findViewById(R.id.equip);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent people = new Intent(StartActivity.this, EquipActivity.class);
                Log.v("mylog","into equip page");
                startActivity(people);
            }
        });

        button = (Button) findViewById(R.id.collect);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent people = new Intent(StartActivity.this, CollectionActivity.class);
                Log.v("mylog","into equip page");
                startActivity(people);
            }
        });

        //new StrengthThread().start();

    }

    //onStop and onRestart are for the music play
    @Override
    protected void onStop(){
        mediaPlayer.stop();
        super.onPause();
        current=mediaPlayer.getCurrentPosition();
        Log.v("mylog","on stop start activity");
        super.onStop();
    }
    @Override
    protected void onRestart(){
        Log.v("mylog","on restart start activity");
        Log.v("mylogtotestmediaplayer","start again："+mediaPlayer.getCurrentPosition());
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(current);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener(){
            public void onSeekComplete(MediaPlayer m){
                m.start();
            }
        });
        super.onRestart();
    }

    //onPause and onResume are for the animation stop
    @Override
    protected void onPause(){
        //for(int i=0;i<bcount;i++){
        //walkguests[i].Pause();
        //}
        super.onPause();
    }
    @Override
    protected void onResume(){
        //for(int i=0;i<bcount;i++){
        //walkguests[i].Resume();
        //}
        super.onResume();
    }

    //display the time and
    public class TimeThread extends Thread {

        private int tcount=0;
        private int scount=0;
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    tcount++;
                    scount++;
                    //display time
                    if(tcount>9) {
                        Message msg = new Message();
                        msg.what = msgKey1;
                        mHandler.sendMessage(msg);
                        //Log.v("mylog", "send time");
                        tcount=0;
                    }
                    //recover strength
                    if(scount>5) {
                        Message msg2 = new Message();
                        msg2.what = msgKey2;
                        mHandler.sendMessage(msg2);
                        Log.v("mylog", "send strength");
                        scount=0;
                    }
                    if(bcount<1) {
                        bcount++;
                        Message msg3 = new Message();
                        msg3.what = msgKey3;
                        mHandler.sendMessage(msg3);
                        Log.v("mylog", "send view change");
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    gTime.setText(getTime());
                    //Log.v("mylog",""+gTime.getText());
                    break;
                case msgKey2:
                    strengthProgress.incrementProgressBy(1);
                    setProgress(100 * strengthProgress.getProgress());
                    break;
                case msgKey3:
                    addFLView();
                    break;
                default:
                    break;
            }
        }
    };

    public String getTime(){
        final Calendar c = Calendar.getInstance();
        //Log.v("mylog","update time");
        /*
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份 系统是从0开始计月份，到12就归零
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        //String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
        String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
        String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒
        */

        Date start=startDay.getTime();
        //Log.v("mylog","time is "+start.getTime());
        Date end=c.getTime();

        long m_total=(end.getTime()-start.getTime())/(60*1000);
        long s=(end.getTime()-start.getTime())/1000-m_total*60;
        int gD=(int)m_total/24;
        int gH=(int)m_total%24/10;
        int gM=(int)(m_total%24%10*60+s)/10;

        String gDay = String.format("%d", gD%7);
        String gHour = String.format("%02d",gH);
        String gMinute=String.format("%02d",gM);

        if("0".equals(gDay)){
            gDay ="天";
        }else if("1".equals(gDay)){
            gDay ="一";
        }else if("2".equals(gDay)){
            gDay ="二";
        }else if("3".equals(gDay)){
            gDay ="三";
        }else if("4".equals(gDay)){
            gDay ="四";
        }else if("5".equals(gDay)){
            gDay ="五";
        }else if("6".equals(gDay)){
            gDay ="六";
        }


        return "日期：星期" + gDay + " " +" "+gHour+":"+gMinute;

    }

    public void addFLView(){
        Walker walk=new Walker();
        walkguests[bcount-1]=new WalkView(this,walk,2);
        guestfl.addView(walkguests[bcount-1]);
    }
}
