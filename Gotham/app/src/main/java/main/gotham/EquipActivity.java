package main.gotham;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import main.dao.EquipDao;
import main.entity.Equip;

public class EquipActivity extends AppCompatActivity {
    List<Equip> equips;
    private EquipDao equipdao=new EquipDao();
    private ListView list_e;
    MyEquipAdapter myadapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_main);

        Button button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        EquipList();

    }


    //检查该设备是否已经被购买过了
    private List<Integer> bought=new ArrayList<>();
    private int charactermoney=3500;
    public boolean inScreen(int id){
        if(bought.contains(id))
            return true;
        else
            return false;
    }
    //检查该设备等级是否达到
    public boolean checkLevel(int level){
        if(level<8)
            return true;
        else
            return false;
    }
    //检查该设备金钱是否达到
    public boolean checkMoney(int money){
        if(money<=charactermoney)
            return true;
        else
            return false;
    }
    //购买设备，并且更新数据
    public void buyEquip(int id){
        bought.add(id);
        Equip e=equipdao.getByID(id);
        charactermoney-=e.getMoney();
        myadapter.notifyDataSetChanged();
    }

    public void EquipList(){
        bought.add(0);
        bought.add(1);
        equips=equipdao.getAll();
        list_e=(ListView)findViewById(R.id.equiplist);
        myadapter=new MyEquipAdapter(this);
        list_e.setAdapter(myadapter);
    }



    public final class ViewHolder{
        public ImageView img;
        public ImageView over;
        public TextView name;
        public TextView describe;
        public TextView money;
        public TextView level;
        public Button buy;
    }

    public class MyEquipAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public  MyEquipAdapter(Context context){
            this.mInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return equips.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view==null){
                holder=new ViewHolder();
                view=mInflater.inflate(R.layout.equip_item,null);
                holder.describe=(TextView)view.findViewById(R.id.describe);
                holder.over=(ImageView)view.findViewById(R.id.over);
                holder.img=(ImageView)view.findViewById(R.id.desk);
                holder.level=(TextView)view.findViewById(R.id.level);
                holder.money=(TextView)view.findViewById(R.id.money);
                holder.name=(TextView)view.findViewById(R.id.name);
                holder.buy=(Button)view.findViewById(R.id.buy);
                view.setTag(holder);
            }
            else{
                holder=(ViewHolder) view.getTag();
            }

            final int equipID=equips.get(i).getId();
            //holder.over.setImageResource(R.drawable.wan);
            holder.img.setImageResource((Integer)equips.get(i).getPicture());
            holder.name.setText(equips.get(i).getName());
            holder.describe.setText(equips.get(i).getDescription());
            int level=equips.get(i).getLevel();
            int money=equips.get(i).getMoney();
            holder.level.setText(Integer.toString(level));
            holder.money.setText(Integer.toString(money));

            boolean levelfit=checkLevel(level);
            boolean moneyfit=checkMoney(money);
            if(!inScreen(equipID)){
                holder.over.setVisibility(View.INVISIBLE);
                if(!levelfit){
                    holder.level.setTextColor(Color.RED);
                }
                else{
                    holder.level.setTextColor(Color.BLACK);
                }
                if(!moneyfit){
                    holder.money.setTextColor(Color.RED);

                }
                else{
                    holder.money.setTextColor(Color.BLACK);
                }
                if(moneyfit&&levelfit)
                    holder.buy.setEnabled(true);
                else
                    holder.buy.setEnabled(false);
            }
            else{
                holder.level.setTextColor(Color.GRAY);
                holder.money.setTextColor(Color.GRAY);
                holder.over.setVisibility(View.VISIBLE);
                holder.buy.setEnabled(false);
            }

            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyEquip(equipID);
                }
            });

            return view;
        }
    }
}
