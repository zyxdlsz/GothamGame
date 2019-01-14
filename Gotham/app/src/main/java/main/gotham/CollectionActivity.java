package main.gotham;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import main.views.CollectionDialog;

/**
 * Created by liszdeng on 2018/12/17.
 */

public class CollectionActivity extends AppCompatActivity {
    private GridView gridview;

    GridViewSim myGridView;
    Context maincontext;
    //LayoutInflater  inflater;
    //定义两个数组，放图片和文字
    private String text[]={"The Small Bell","The Small Bell","The Small Bell",
            "The Small Bell","The Small Bell","The Small Bell",
            "The Small Bell","The Small Bell","The Small Bell",//3
            "The Small Bell","The Small Bell","The Small Bell",
            "The Small Bell","The Small Bell","The Small Bell",
            "The Small Bell","The Small Bell","The Small Bell",//6
            "The Small Bell","The Small Bell","The Small Bell",
            "The Small Bell","The Small Bell","The Small Bell",
            "The Small Bell","The Small Bell","The Small Bell"};//9
    private int img_grid[] ={R.drawable.bell,R.drawable.bell,R.drawable.bell,
            R.drawable.bell,R.drawable.bell,R.drawable.bell,
            R.drawable.bell,R.drawable.bell,R.drawable.bell,//3
            R.drawable.bell,R.drawable.bell,R.drawable.bell,
            R.drawable.bell,R.drawable.bell,R.drawable.bell,
            R.drawable.bell,R.drawable.bell,R.drawable.bell,//6
            R.drawable.bell,R.drawable.bell,R.drawable.bell,
            R.drawable.bell,R.drawable.bell,R.drawable.bell,
            R.drawable.bell,R.drawable.bell,R.drawable.bell};//9
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_main);

        Button button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        maincontext=this;

        gridview=(GridView)findViewById(R.id.gridView);

        myGridView=new GridViewSim(this);
        gridview.setAdapter(myGridView);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Item Clicked: "+position, Toast.LENGTH_SHORT).show();
                CollectionDialog codialog= new CollectionDialog(maincontext,R.layout.collection_dialog);
                //显示
                codialog.show();
            }
        });

        //inflater= LayoutInflater.from(this);
    }


    class GridViewSim extends BaseAdapter {
        private Context context=null;
        private LayoutInflater mInflater;


        private class Holder{

            ImageView item_img;
            TextView item_tex;

        }
        //构造方法
        public GridViewSim(Context context) {
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {


            return text.length;

        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.collection_item, null);
                holder=new Holder();
                holder.item_img=(ImageView)convertView.findViewById(R.id.image);
                holder.item_tex=(TextView)convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }else{
                holder=(Holder) convertView.getTag();
            }
            holder.item_tex.setText(text[position]);
            holder.item_img.setImageResource(img_grid[position]);

            WindowManager windowManager = ((Activity)context).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = display.getWidth()/4;// 设置dialog宽度为屏幕的1/4
            lp.height = display.getHeight()/10;// 4排除8，5排除10
            convertView.setLayoutParams(lp);
            return convertView;
        }
    }
}
