package main.gotham;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import main.views.HorizontalListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liszdeng on 9/21/18.
 */

public class FoodActivity extends AppCompatActivity {

    private List<Map<String, Object>> mData;
    private ListView list_f;
    private Button class1;
    private Button class2;
    private Button all;
    MyFoodAdapter myadapter;
    View.OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_main);

        Button button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        FoodList();

    }

    public void FoodList(){
        mData = getData();
        list_f = (ListView) findViewById(R.id.foodlist);

        myadapter = new MyFoodAdapter(this);
        list_f.setAdapter(myadapter);


        class1 = (Button) findViewById(R.id.class1);
        class2 = (Button) findViewById(R.id.class2);
        all = (Button) findViewById(R.id.all);

        clickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.class1:
                        class1View();
                        break;
                    case R.id.class2:
                        class2View();
                        break;
                    case R.id.all:
                        allView();
                        break;
                }
                ((ListView)findViewById(R.id.foodlist)).setSelection(0);
            }

        };

        all.setOnClickListener(clickListener);
        class1.setOnClickListener(clickListener);
        class2.setOnClickListener(clickListener);


        //checkButton();

    }

    public void class1View() {
        mData = getClass1();
        class1.setEnabled(false);
        all.setEnabled(true);
        class2.setEnabled(true);
        myadapter.notifyDataSetChanged();
        //checkButton();
    }
    public void class2View() {

        mData = getClass2();
        class2.setEnabled(false);
        all.setEnabled(true);
        class1.setEnabled(true);
        myadapter.notifyDataSetChanged();
        //checkButton();
    }
    public void allView() {
        mData = getData();
        all.setEnabled(false);
        class1.setEnabled(true);
        class2.setEnabled(true);
        myadapter.notifyDataSetChanged();
        //checkButton();
    }

    public void checkButton() {

        if(!all.isEnabled()){
            class1.setEnabled(true);
            class2.setEnabled(true);
        }
        if(!class1.isEnabled()){
            all.setEnabled(true);
            class2.setEnabled(true);
        }
        if(!class2.isEnabled()) {
            all.setEnabled(true);
            class1.setEnabled(true);
        }

    }



    /**
    // ListView 中某项被选中后的逻辑
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.v("MyListView4-click", (String)mData.get(position).get("title"));
    }


     * listview中点击按键弹出对话框
     */
    public void showInfo(String name){

        View food_dialog = View.inflate(this, R.layout.food_dialog, null);
        TextView fname=(TextView)food_dialog.findViewById(R.id.name);
        fname.setText(name);
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(food_dialog);
        builder.setCancelable(true);
        Button make=(Button) food_dialog.findViewById(R.id.make);
        make.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FoodActivity.this.finish();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        */
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setView(food_dialog)
                .setPositiveButton("make", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setWidth(3000);
        dialog.getButton(dialog.BUTTON_POSITIVE).setHeight(200);
        dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#B3B4BA"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DA6DD"));

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =dialog.getWindow().getAttributes();//获取dialog信息
        params.width = (int)Math.floor(screenWidth*0.9);

        params.height = (int)Math.floor(screenHeigh*0.7);

        dialog.getWindow().setAttributes(params);

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View aalayout = View.inflate(this, R.layout.food_single, null);
        TextView fname=(TextView)aalayout.findViewById(R.id.name);
        fname.setText(name);
        //builder.setCancelable(true);
        builder.setPositiveButton("make", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //dialog.dismiss();
                finish();
            }
        });
        builder.setView(aalayout);
        AlertDialog dii = builder.create();
        //dii.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dii.show();
        */
    }

    public final class ViewHolder{
        public ImageView img;
        public TextView fname;
        public TextView fclass;
        public Button makeBtn;
    }

    public class MyFoodAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyFoodAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.food_list, null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.fname = (TextView)convertView.findViewById(R.id.fname);
                holder.fclass = (TextView)convertView.findViewById(R.id.fclass);
                holder.makeBtn = (Button)convertView.findViewById(R.id.make);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }


            holder.img.setImageResource((Integer)mData.get(position).get("img"));
                    //setBackgroundResource();
            holder.fname.setText((String)mData.get(position).get("title"));
            holder.fclass.setText((String)mData.get(position).get("class"));
            final String fname=(String)mData.get(position).get("title");
            holder.makeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInfo(fname);
                }
            });


            return convertView;
        }

    }





    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Food1");
        map.put("class", "class1");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food2");
        map.put("class", "class1");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food3");
        map.put("class", "class1");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food4");
        map.put("class", "class2");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food5");
        map.put("class", "class2");
        map.put("img", R.drawable.food);
        list.add(map);

        return list;
    }
    private List<Map<String, Object>> getClass1() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Food1");
        map.put("class", "class1");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food2");
        map.put("class", "class1");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food3");
        map.put("class", "class1");
        map.put("img", R.drawable.food);
        list.add(map);

        return list;
    }
    private List<Map<String, Object>> getClass2() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map = new HashMap<String, Object>();
        map.put("title", "Food4");
        map.put("class", "class2");
        map.put("img", R.drawable.food);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Food5");
        map.put("class", "class2");
        map.put("img", R.drawable.food);
        list.add(map);

        return list;
    }

}
