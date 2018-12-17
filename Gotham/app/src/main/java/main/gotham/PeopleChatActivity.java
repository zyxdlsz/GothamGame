package main.gotham;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;


/**
 * Created by liszdeng on 9/29/18.
 */

public class PeopleChatActivity extends AppCompatActivity {

    ArrayList<HashMap<String,Object>> chatList=null;

    //String[] conversation;
    //int[] speakers;

    //test the buttom
    String[] conversation1={"title","第一句话","第二句话","第三句话","第四句话","第五句话"};
    String[] conversation_c={"选择1","选择2"};
    String[] conversation2={"选择1-第六句话","选择1-第七句话","选择1-第八句话"};
    String[] conversation3={"选择2-第六句话","选择2-第七句话","选择2-第八句话"};
    String[] conversation;
    int[] speakers1={-1,1,0,1,1,0,1};//speakers要比conversation长一个1，为了方便之后的判断
    int[] speakers2={0,1,1,1};
    int[] speakers3={0,1,1,1};
    int[] speakers;
    int prograss=0;
    String context;
    String context_c1;
    String context_c2;
    int next_speaker;
    boolean choiceflag=false;
    Button wait;

    public final static int OTHER=0;
    public final static int ME=1;
    protected ListView chatListView=null;
    protected MyChatAdapter adapter=null;

    int[] to={R.id.chatlist_text_me,R.id.chatlist_text_other};
    int[] layout={R.layout.people_chat_talk,R.layout.people_chat_ans};

    TextView title;
    ImageView other;
    LinearLayout buttonfiled;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_chat);
        chatList=new ArrayList<HashMap<String,Object>>();
        chatListView=(ListView)findViewById(R.id.chat_list);

        title=(TextView) findViewById(R.id.title);
        other=(ImageView) findViewById(R.id.other);
        buttonfiled=(LinearLayout) findViewById(R.id.buttonfield);
        wait=new Button(this);
        LinearLayout.LayoutParams lpw = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpw.setMargins(300, 180, 100, 200);
        wait.setText("wait");
        wait.setLayoutParams(lpw);
        wait.setEnabled(false);
/*
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            if (bundle.getString("id") != null&&bundle.getString("number") != null) {
                int id = Integer.parseInt(bundle.get("id").toString());
                int number=Integer.parseInt(bundle.get("number").toString());//number = i+1
                PeopleDao pdao=new PeopleDao();
                People people=pdao.getByID(id);
                conversation=people.getConversations()[number-1];
                speakers=people.getSpeakers()[number-1];
                title.setText(conversation[0]);
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("ID Error")
                        .setMessage("the ID does not passed here!")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
*/
        adapter=new MyChatAdapter(this,chatList,layout,to);
        chatListView.setAdapter(adapter);
        conversation=conversation1;
        speakers=speakers1;

        continueConverstaion();
        /*
        chatListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    prograss++;
                }
                return false;
            }
        });
        */
    }

    protected void addTextToList(String text, int who){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person",who );
        map.put("text", text);
        chatList.add(map);
    }

    private void continueConverstaion(){
        buttonfiled.removeAllViews();
        //Log.v("mylog","continue with"+prograss);
        prograss++;
        int next=prograss+1;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(300, 150, 100, 0);

        if(prograss<conversation.length) {//开头第一段相同的对话
            int speaker=speakers[prograss];
            next_speaker = speakers[next];
            String content=conversation[prograss];
            //Log.v("mylog","next is "+next_speaker);

            if(speaker==OTHER) {
                addTextToList(content, speaker);
                adapter.notifyDataSetChanged();
                if (next_speaker == OTHER) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Log.v("mylog", "delay");
                            continueConverstaion();
                        }
                    }, 1000);
                }
                else {
                    continueConverstaion();
                }

            }
            else{
                Button btn1=new Button(this);
                btn1.setText(content);
                btn1.setLayoutParams(lp);
                context=content;
                btn1.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.v("mylog","button down");
                        addTextToList(context, ME);
                        adapter.notifyDataSetChanged();
                        buttonfiled.removeAllViews();
                        buttonfiled.addView(wait);
                        if (next_speaker == OTHER) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Log.v("mylog", "delayover");
                                    continueConverstaion();
                                }
                            }, 1000);
                        }
                        else {
                            continueConverstaion();
                        }
                    }
                });
                buttonfiled.addView(btn1);
                Log.v("mylog","button add over");
            }

        }
        else if(prograss==conversation.length && !choiceflag){//做选择
            choiceflag=true;
            context_c1=conversation_c[0];
            context_c2=conversation_c[1];

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp2.setMargins(300, 50, 100, 0);


            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp3.setMargins(300, 50, 100, 0);

            Button btn1=new Button(this);
            btn1.setText(context_c1);
            btn1.setLayoutParams(lp2);

            Button btn2=new Button(this);
            btn2.setText(context_c2);
            btn2.setLayoutParams(lp3);


            btn1.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Log.v("mylog","button down");
                    addTextToList(context_c1, ME);
                    adapter.notifyDataSetChanged();
                    prograss=-1;
                    conversation=conversation2;
                    speakers=speakers2;
                    buttonfiled.removeAllViews();
                    buttonfiled.addView(wait);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Log.v("mylog", "delay");
                            continueConverstaion();
                        }
                    }, 1000);
                }
            });
            btn2.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Log.v("mylog","button down");
                    addTextToList(context_c2, ME);
                    adapter.notifyDataSetChanged();
                    prograss=-1;
                    conversation=conversation3;
                    speakers=speakers3;
                    buttonfiled.removeAllViews();
                    buttonfiled.addView(wait);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Log.v("mylog", "delay");
                            continueConverstaion();
                        }
                    }, 1000);
                }
            });

            buttonfiled.addView(btn1);
            buttonfiled.addView(btn2);

            Log.v("mylog","choice add over");
        }
        else{// if(prograss == conversation.length){
            Button btn1=new Button(this);
            btn1.setText("END");
            lp.setMargins(300, 150, 100, 0);
            btn1.setLayoutParams(lp);
            btn1.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) { finish(); }
            });
            buttonfiled.addView(btn1);
        }

        chatListView.setSelection(chatListView.getBottom());
    }

    private class MyChatAdapter extends BaseAdapter {

        Context context=null;
        ArrayList<HashMap<String,Object>> chatList=null;
        int[] layout;
        int[] to;

        public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout,
                             int[] to) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder{
            public TextView textView=null;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder=null;
            int who=(Integer)chatList.get(position).get("person");

            convertView= LayoutInflater.from(context).inflate(layout[who==OTHER?0:1], null);
            holder=new ViewHolder();
            //Log.v("mylog","who is "+who);
            holder.textView=(TextView)convertView.findViewById(to[who]);
            holder.textView.setText(chatList.get(position).get("text").toString());
            return convertView;
        }

    }

}
