package main.gotham;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    boolean backflag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer=MediaPlayer.create(this,R.raw.main_bg_demo1);
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onPause(){
        mediaPlayer.stop();
        backflag=true;
        super.onPause();
    }

    @Override
    protected void onResume(){

        if(backflag){
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.start();
        super.onResume();
    }


    public void start(View view){
        mediaPlayer.stop();
        Intent start = new Intent(this, StartActivity.class);
        Log.v("mylog","into next page");
        startActivity(start);
        backflag=true;
        return;
    }

    public void exit(View view){
        finish();
    }

    public void staff(View view){
        Intent start = new Intent(this, StaffActivity.class);
        Log.v("mylog","into next page");
        startActivity(start);
        return;
    }

    @Override

    protected void onDestroy() {

        super.onDestroy();
        System.exit(0);//直接结束程序
        // 或者下面这种方式
        // android.os.Process.killProcess(android.os.Process.myPid());

    }

}