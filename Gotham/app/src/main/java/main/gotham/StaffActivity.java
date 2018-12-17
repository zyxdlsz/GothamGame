package main.gotham;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by liszdeng on 9/20/18.
 */

public class StaffActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff);
        TextView staff=(TextView) findViewById(R.id.staff);
        staff.setText(getStaff());
    }

    public String getStaff(){
        String[] name={"卷耳","湖芋","冥夜花伝廊","monster","謙仔","明","CenmoBatson"};
        String[] title={"策划","技术","文案","文案","文案","文案","文案"};
        String staff="";
        for(int i=0;i<name.length;i++){
            staff=staff+name[i]+title[i]+'\n';
        }
        return staff;
    }
}
