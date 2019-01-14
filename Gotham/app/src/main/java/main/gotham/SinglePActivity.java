package main.gotham;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import main.dao.PeopleDao;
import main.entity.People;

/**
 * Created by liszdeng on 9/25/18.
 */

public class SinglePActivity extends AppCompatActivity {

    private PeopleDao pdao=new PeopleDao();
    private People people=new People();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_dialog);

        Button button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        final int id;

        if(bundle != null) {
            if (bundle.getString("id") != null) {
                id = Integer.parseInt(bundle.get("id").toString());
                people=pdao.getByID(id);
                if(people.isEmpty()){
                    new AlertDialog.Builder(this)
                            .setTitle("ID Error")
                            .setMessage("the ID does not exist!")
                            .setPositiveButton("OK", null)
                            .show();
                }
                else{
                    TextView text=(TextView) findViewById(R.id.name);
                    text.setText(people.getName());
                    text=(TextView) findViewById(R.id.describe);
                    text.setText(people.getDescribe());
                    ImageView img= (ImageView) findViewById(R.id.picture);
                    img.setImageResource((Integer) people.getPicture());
                    String[] Covn=new String[6];
                    for(int j=0;j<6;j++){
                        Covn[j]=people.getConversations()[j][0];
                    }
                    Button covButton = (Button) findViewById(R.id.cov1);
                    covButton.setText(Covn[0]);
                    covButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {StartCov(id,1);}
                    });
                    covButton = (Button) findViewById(R.id.cov2);
                    covButton.setText(Covn[1]);
                    covButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {StartCov(id,2);}
                    });
                    covButton = (Button) findViewById(R.id.cov3);
                    covButton.setText(Covn[2]);
                    covButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {StartCov(id,3);}
                    });
                    covButton = (Button) findViewById(R.id.cov4);
                    covButton.setText(Covn[3]);
                    covButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {StartCov(id,4);}
                    });
                    covButton = (Button) findViewById(R.id.cov5);
                    covButton.setText(Covn[4]);
                    covButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {StartCov(id,5);}
                    });
                    covButton = (Button) findViewById(R.id.cov6);
                    covButton.setText(Covn[5]);
                    covButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {StartCov(id,6);}
                    });
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("ID Error")
                        .setMessage("the ID does not passed here!")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }



    }

    public void StartCov(int pid,int i){
        Intent single = new Intent(this,PeopleChatActivity.class);
        single.putExtra("id",String.valueOf(pid));
        single.putExtra("number",String.valueOf(i));
        startActivity(single);
    }


}
