package com.example.administrator.midtermprojectgruop35;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class Splash extends Activity {

    private ImageView imageView;
    private Database database;
    private List<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        database = new Database(this);
        heroList = database.listHero();

        /*
        Thread init = new Thread(){
            public void run()
            {
                try{
                    if (heroList.size() == 0) {
                        initHero();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        init.start();*/

       imageView = (ImageView) findViewById(R.id.loadImage);
        // 设置加载动画透明度渐变从（0.1不显示-1.0完全显示）
        AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);
        // 设置动画时间5s
        animation.setDuration(100);


        // 将组件与动画关联
        imageView.setAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {
            // 动画开始时执行
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                // 初始化
                if (heroList.size() == 0) {
                    initHero();
                }

            }

            // 动画重复时执行
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            // 动画结束时执行
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initHero() {
        AssetManager assetManager = getAssets();
        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open("hero_list.xls"));
            Sheet sheet = workbook.getSheet(0);
            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();
            for (int i = 1; i < sheetRows; i++) {
                int id = Integer.valueOf(sheet.getCell(0, i).getContents());
                String name = sheet.getCell(1, i).getContents();
                Bitmap icon = ((BitmapDrawable) getResources().getDrawable(getResources().getIdentifier(sheet.getCell(24, i).getContents() + "_icon", "mipmap", getBaseContext().getPackageName()))).getBitmap();
                Bitmap minimapIcon = ((BitmapDrawable) getResources().getDrawable(getResources().getIdentifier(sheet.getCell(24, i).getContents() + "_minimap_icon", "mipmap", getBaseContext().getPackageName()))).getBitmap();
                String chineseName = sheet.getCell(2, i).getContents();
                String nickname = sheet.getCell(3, i).getContents();
                Hero.Species species = Hero.Species.valueOf(sheet.getCell(4, i).getContents());
                Hero.AttackMode attackMode = Hero.AttackMode.valueOf(sheet.getCell(5, i).getContents());
                int difficult = Integer.valueOf(sheet.getCell(6, i).getContents());
                int carry = Integer.valueOf(sheet.getCell(7, i).getContents());
                int support = Integer.valueOf(sheet.getCell(8, i).getContents());
                int nuker = Integer.valueOf(sheet.getCell(9, i).getContents());
                int disabler = Integer.valueOf(sheet.getCell(10, i).getContents());
                int jungler = Integer.valueOf(sheet.getCell(11, i).getContents());
                int durable = Integer.valueOf(sheet.getCell(12, i).getContents());
                int escape = Integer.valueOf(sheet.getCell(13, i).getContents());
                int pusher = Integer.valueOf(sheet.getCell(14, i).getContents());
                int initiator = Integer.valueOf(sheet.getCell(15, i).getContents());
                int strength = Integer.valueOf(sheet.getCell(16, i).getContents());
                int agility = Integer.valueOf(sheet.getCell(17, i).getContents());
                int intelligence = Integer.valueOf(sheet.getCell(18, i).getContents());
                double strengthUp = Double.valueOf(sheet.getCell(19, i).getContents());
                double agilityUp = Double.valueOf(sheet.getCell(20, i).getContents());
                double intelligenceUp = Double.valueOf(sheet.getCell(21, i).getContents());
                int health = Integer.valueOf(sheet.getCell(22, i).getContents());
                int mana = Integer.valueOf(sheet.getCell(23, i).getContents());
                database.insertHero(new Hero(id, name, icon, minimapIcon, chineseName, nickname, species, attackMode, difficult, carry, support, nuker, disabler, jungler, durable, escape, pusher, initiator, strength, agility, intelligence, strengthUp, agilityUp, intelligenceUp, health, mana));
            }
            workbook.close();
        } catch (Exception e) {

        }
    }

}