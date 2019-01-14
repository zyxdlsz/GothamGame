package main.gotham;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import main.views.HorizontalListView;

import java.util.List;

import main.dao.PeopleDao;
import main.entity.People;

/**
 * Created by liszdeng on 9/24/18.
 */

public class PeopleActivity extends AppCompatActivity {
    private List<People> pData;
    private PeopleDao pdao=new PeopleDao();
    private HorizontalListView list_p;
    private int index=1;
    private Button prev;
    private Button next;
    private TextView pages;
    HorizontalListViewAdapter myadapter;
    View.OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_main);

        Button button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        PeopleList();

    }

    public void PeopleList(){
        pData = pdao.getPair(index);

        myadapter = new HorizontalListViewAdapter(this);
        list_p = (HorizontalListView) findViewById(R.id.horizon_cuslist);
        list_p.setAdapter(myadapter);

        AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (parent.getId())
                {
                    case R.id.horizon_cuslist:
                        expressItemClick(position);//position 代表你点的哪一个
                        break;
                }
            }
        };

        list_p.setOnItemClickListener(itemClick);


        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        pages = (TextView) findViewById(R.id.pages);

        clickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.prev:
                        preView();
                        break;
                    case R.id.next:
                        nextView();
                        break;
                }
            }

        };

        prev.setOnClickListener(clickListener);
        next.setOnClickListener(clickListener);


        checkButton();
    }

    public void expressItemClick(int postion){
        int id=index+postion;

        Intent single = new Intent(this,SinglePActivity.class);
        single.putExtra("id",String.valueOf(id));
        startActivity(single);
        //finish(); //看你需不需要返回当前界面，如果点返回需要返回到当前界面，就不用这个
        /*
        People peo=pdao.getByID(id);
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle(peo.getName())
                .setMessage(peo.getConversations()[0][0])
                .setPositiveButton("make", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                })
                .create();
        dialog.show();
        //看你需不需要返回当前界面，如果点返回需要返回到当前界面，就不用这个
        */
    }

    public void preView() {
        index-=2;
        pData = pdao.getPair(index);
        myadapter.notifyDataSetChanged();
        checkButton();
    }

    public void nextView() {
        index+=2;
        pData = pdao.getPair(index);
        myadapter.notifyDataSetChanged();
        checkButton();
    }

    public void checkButton() {
        if (index <= 1) {
            prev.setEnabled(false);
        }else{
            prev.setEnabled(true);
        }
        if (index>=21) {
            next.setEnabled(false);
        }

        else {
            next.setEnabled(true);
        }
        String page="Page ";
        page+=(index/2+1)+"/11";
        pages.setText(page);

    }

    public final class ViewHolder{
        public ImageView img;
        public TextView pname;
        public TextView pfavday;
        public TextView pfavfood;
    }

    public class HorizontalListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public HorizontalListViewAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pData.size();
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

                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.people_item, null);
                holder.img = (ImageView) convertView.findViewById(R.id.picture);
                holder.pname = (TextView) convertView.findViewById(R.id.name);
                holder.pfavday = (TextView) convertView.findViewById(R.id.favday);
                holder.pfavfood = (TextView) convertView.findViewById(R.id.favfood);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.img.setImageResource((Integer) pData.get(position).getPicture());
            //setBackgroundResource();
            holder.pname.setText((String) pData.get(position).getName());
            String s_favdays = "常来的日子: ";
            int[] favdays = pData.get(position).getFavorite_days();
            for (int i = 0; i < favdays.length; i++) {
                switch (favdays[i]) {
                    case 0:
                        s_favdays += " 周一";
                        break;
                    case 1:
                        s_favdays += " 周二";
                        break;
                    case 2:
                        s_favdays += " 周三";
                        break;
                    case 3:
                        s_favdays += " 周四";
                        break;
                    case 4:
                        s_favdays += " 周五";
                        break;
                    case 5:
                        s_favdays += " 周六";
                        break;
                    case 6:
                        s_favdays += " 周日";
                        break;
                }
            }
            holder.pfavday.setText(s_favdays);
            String s_favfood = "喜欢的食物: ";
            String[] favfoods = pData.get(position).getFavorite_food();
            for (int i = 0; i < 3; i++) {
                s_favfood += " " + favfoods[i];
            }
            holder.pfavfood.setText(s_favfood);


            return convertView;
        }
    }
}
