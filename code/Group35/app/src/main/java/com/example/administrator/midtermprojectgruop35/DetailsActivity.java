package com.example.administrator.midtermprojectgruop35;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private Hero hero;
    private TextView name;
    private Bitmap bitmap;
    private Bitmap mini_bitmap;
    private ImageView img;
    private ImageView mini_img;
    private TextView hp_num;
    private TextView mp_num;
    private TextView strength_num;
    private TextView agility_num;
    private TextView intelligence_num;
    private TextView strength_num_up;
    private TextView agility_num_up;
    private TextView intelligence_num_up;
    private TextView diffcult_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle=this.getIntent().getExtras();
        int id=bundle.getInt("id");
        Database database=new Database(DetailsActivity.this);
        hero=database.queryHero(id);
        bitmap=hero.getIcon();
        img=findViewById(R.id.detail_image);
        img.setImageBitmap(bitmap);
        hp_num=findViewById(R.id.detail_hp);
        hp_num.setText(hero.getHealth()+"");
        mp_num=findViewById(R.id.detail_mp);
        mp_num.setText(hero.getMana()+"");
        strength_num=findViewById(R.id.detail_strength);
        strength_num.setText(hero.getStrength()+"");
        agility_num=findViewById(R.id.detail_agility);
        agility_num.setText(hero.getAgility()+"");
        intelligence_num=findViewById(R.id.detail_iq);
        intelligence_num.setText(hero.getIntelligence()+"");
        strength_num_up=findViewById(R.id.detail_up_strength);
        strength_num_up.setText(hero.getStrengthUp()+"");
        agility_num_up=findViewById(R.id.detail_up_agility);
        agility_num_up.setText(hero.getAgilityUp()+"");
        intelligence_num_up=findViewById(R.id.detail_up_iq);
        intelligence_num_up.setText(hero.getIntelligenceUp()+"");
        name=findViewById(R.id.detail_name);
        name.setText(hero.getChineseName()+" "+hero.getName());
        diffcult_rate=findViewById(R.id.detail_difficulty_rate);
        int num=hero.getDifficult();
        String str="";
        for (int i=0;i<num;i++){
            str+="★";
        }
        diffcult_rate.setText(str);
        mini_img=findViewById(R.id.detail_mini_img);
        mini_bitmap=hero.getMinimapIcon();
        mini_img.setImageBitmap(mini_bitmap);
    }
}
