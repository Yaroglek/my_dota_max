package com.example.administrator.midtermprojectgruop35;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mHead;//标题头
    private ListView mListView;
    private List<TestData> mDataList;
    private ListViewAdapter mAdapter;
    private int leftPos;//用于记录CustomHScrollView的初始位置
    private int topPos;
    private List<Hero> heroList;
    private Database database;
    CustomHScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
        heroList = database.listHero();
        if (heroList.size() == 0) {
            initHero();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initData();
    }

    private void initView(){
        mListView = (ListView) findViewById(R.id.list_view);
        mScrollView = (CustomHScrollView) findViewById(R.id.h_scrollView);
        mHead = (RelativeLayout) findViewById(R.id.head_layout);
        mHead.setBackgroundResource(R.color.colorAccent);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setOnTouchListener(new MyTouchLinstener());
        mListView.setOnTouchListener(new MyTouchLinstener());
    }

    /**
     * 加载数据
     */
    private void initData(){
        mDataList = new ArrayList<>();
        TestData data = null;
        for (int i = 1; i <= 30; i++) {
            data = new TestData();
            data.setText1("第"+i+"行-1");
            data.setText2("第"+i+"行-2");
            data.setText3("第"+i+"行-3");
            data.setText4("第"+i+"行-4");
            data.setText5("第"+i+"行-5");
            data.setText6("第"+i+"行-6");
            data.setText7("第"+i+"行-7");
            data.setText8("第"+i+"行-8");
            data.setText9("第"+i+"行-9");
            data.setText10("第"+i+"行-10");
            data.setText11("第"+i+"行-11");
            data.setText12("第"+i+"行-12");
            mDataList.add(data);
        }
        setData();
    }

    private void setData(){
        mAdapter = new ListViewAdapter(this, mDataList, mHead);
        mListView.setAdapter(mAdapter);
    }

    class MyTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            //当在表头和listView控件上touch时，将事件分发给 ScrollView
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead.findViewById(R.id.h_scrollView);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            initData();//刷新，重新加载数据
            mScrollView.smoothScrollTo(leftPos, topPos);//每次刷新数据都让CustomHScrollView回到初始位置，避免错乱
            if (mAdapter != null){
                mAdapter.notifyDataSetChanged();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 记录CustomHScrollView的初始位置
     * @param l
     * @param t
     */
    public void setPosData(int l, int t){
        this.leftPos = l;
        this.topPos = t;
    }

    private void initHero() {
        AssetManager assetManager = getAssets();
        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open("hero_list.xlsx"));
            Sheet sheet = workbook.getSheet(0);
            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();
            for (int i = 1; i < sheetRows; i++) {
                int id = Integer.valueOf(sheet.getCell(0, i).getContents());
                String name = sheet.getCell(1, i).getContents();
                Bitmap icon = ((BitmapDrawable) getResources().getDrawable(getResources().getIdentifier(sheet.getCell(24, i).getContents() + "_icon.png", "mipmap", getBaseContext().getPackageName()))).getBitmap();
                Bitmap minimapIcon = ((BitmapDrawable) getResources().getDrawable(getResources().getIdentifier(sheet.getCell(24, i).getContents() + "_minimap_icon.png", "mipmap", getBaseContext().getPackageName()))).getBitmap();
                String chineseName = sheet.getCell(2, i).getContents();
                String nickname = sheet.getCell(3, i).getContents();
                Hero.Species species = Hero.Species.values()[Integer.valueOf(sheet.getCell(4, i).getContents())];
                Hero.AttackMode attackMode = Hero.AttackMode.values()[Integer.valueOf(sheet.getCell(5, i).getContents())];
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
            heroList = database.listHero();
        } catch (Exception e) {

        }

    }

}
