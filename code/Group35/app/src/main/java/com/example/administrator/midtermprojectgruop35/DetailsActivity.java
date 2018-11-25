package com.example.administrator.midtermprojectgruop35;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

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
    private TextView attack_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = this.getIntent().getExtras();
        final int id = bundle.getInt("id");
        final Database database = new Database(DetailsActivity.this);
        hero = database.queryHero(id);
        bitmap = hero.getIcon();
        img = findViewById(R.id.detail_image);
        img.setImageBitmap(bitmap);
        hp_num = findViewById(R.id.detail_hp);
        hp_num.setText("HP: " + hero.getHealth());
        mp_num = findViewById(R.id.detail_mp);
        mp_num.setText("MP: " + hero.getMana());
        strength_num = findViewById(R.id.detail_strength);
        strength_num.setText(hero.getStrength() + "");
        agility_num = findViewById(R.id.detail_agility);
        agility_num.setText(hero.getAgility() + "");
        intelligence_num = findViewById(R.id.detail_iq);
        intelligence_num.setText(hero.getIntelligence() + "");
        strength_num_up = findViewById(R.id.detail_up_strength);
        strength_num_up.setText(hero.getStrengthUp() + "");
        agility_num_up = findViewById(R.id.detail_up_agility);
        agility_num_up.setText(hero.getAgilityUp() + "");
        intelligence_num_up = findViewById(R.id.detail_up_iq);
        intelligence_num_up.setText(hero.getIntelligenceUp() + "");
        name = findViewById(R.id.detail_name);
        name.setText(hero.getChineseName() + " " + hero.getName());
        diffcult_rate = findViewById(R.id.detail_difficulty_rate);
        attack_mode = findViewById(R.id.detail_attack_mode);
        attack_mode.setText(hero.getAttackMode().ordinal() == 0 ? "近战" : "远程");
        int num = hero.getDifficult();
        String str = "";
        for (int i = 0; i < num; i++) {
            str += "★";
        }
        diffcult_rate.setText(str);
        mini_img = findViewById(R.id.detail_mini_img);
        mini_bitmap = hero.getMinimapIcon();
        mini_img.setImageBitmap(mini_bitmap);

        strength_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(DetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("力量");
                builder.setIcon(R.mipmap.strength_attribute_symbol);
                builder.setView(inputServer);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int number = Integer.valueOf(inputServer.getText().toString());
                            int health = (int) (hero.getSpecies().equals(Hero.Species.strength) ? 200 + 22.48 * number : 200 + 18 * number);
                            database.updateHero(id, "strength", number);
                            database.updateHero(id, "health", health);
                            strength_num.setText(number + "");
                            hp_num.setText("HP: " + health);
                            MainActivity.instance.updateList();
                        }
                        catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });

        agility_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(DetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("敏捷");
                builder.setIcon(R.mipmap.agility_attribute_symbol);
                builder.setView(inputServer);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int number = Integer.valueOf(inputServer.getText().toString());
                            database.updateHero(id, "agility", number);
                            agility_num.setText(number + "");
                            MainActivity.instance.updateList();
                        }
                        catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });
        intelligence_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(DetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("智力");
                builder.setIcon(R.mipmap.intelligence_attribute_symbol);
                builder.setView(inputServer);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int number = Integer.valueOf(inputServer.getText().toString());
                            int mana = (int) (hero.getSpecies().equals(Hero.Species.intelligence) ? 75 + 15 * number : 75 + 12 * number);
                            database.updateHero(id, "intelligence", number);
                            database.updateHero(id, "mana", mana);
                            intelligence_num.setText(number + "");
                            mp_num.setText("MP: " + mana);
                            MainActivity.instance.updateList();
                        } catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });
        strength_num_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(DetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("力量成长");
                builder.setIcon(R.mipmap.strength_attribute_symbol_up);
                builder.setView(inputServer);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            double number = Double.valueOf(inputServer.getText().toString());
                            database.updateHero(id, "strength_up", number);
                            strength_num_up.setText(number + "");
                            MainActivity.instance.updateList();
                        }
                        catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });
        agility_num_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(DetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("敏捷成长");
                builder.setIcon(R.mipmap.agility_attribute_symbol_up);
                builder.setView(inputServer);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            double number = Double.valueOf(inputServer.getText().toString());
                            database.updateHero(id, "agility_up", number);
                            agility_num_up.setText(number + "");
                            MainActivity.instance.updateList();
                        }
                        catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });
        intelligence_num_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(DetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("智力成长");
                builder.setIcon(R.mipmap.intelligence_attribute_symbol_up);
                builder.setView(inputServer);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            double number = Double.valueOf(inputServer.getText().toString());
                            database.updateHero(id, "intelligence_up", number);
                            intelligence_num_up.setText(number + "");
                            MainActivity.instance.updateList();
                        }
                        catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });
        RadarView mRdv = (RadarView) findViewById(R.id.rdv);
        List<Double> data;
        data=new ArrayList<>(9);
        double temp = hero.getCarry();
        data.add(temp);
        temp = hero.getSupport();
        data.add(temp);
        temp = hero.getNuker();
        data.add(temp);
        temp = hero.getDisabler();
        data.add(temp);
        temp = hero.getJungler();
        data.add(temp);
        temp = hero.getDurable();
        data.add(temp);
        temp = hero.getEscape();
        data.add(temp);
        temp = hero.getPusher();
        data.add(temp);
        temp = hero.getInitiator();
        data.add(temp);
        mRdv.setData(data);
    }
}
