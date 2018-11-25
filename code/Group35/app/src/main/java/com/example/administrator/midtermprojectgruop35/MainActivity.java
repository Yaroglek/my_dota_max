package com.example.administrator.midtermprojectgruop35;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;


import com.melnykov.fab.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;

    private RelativeLayout mHead;//标题头
    private ListView mListView;
    private ListViewAdapter mAdapter;
    private int leftPos;//用于记录CustomHScrollView的初始位置
    private int topPos;
    private List<Hero> heroList;
    private List<Hero> collectList;
    private List<Hero> selectList;
    private Database database;
    private boolean collectFlag;
    private CustomHScrollView mScrollView;

    private FloatingActionButton fab;
    private ImageView strength;
    private ImageView agility;
    private ImageView intelligence;
    private boolean strengthSelected;
    private boolean agilittySelected;
    private boolean intelligenceSelected;
    private TextView textViewMelee0;
    private TextView textViewRanged0;
    private TextView textViewCarry0;
    private TextView textViewSupport0;
    private TextView textViewNuker0;
    private TextView textViewDisabler0;
    private TextView textViewJungler0;
    private TextView textViewDurable0;
    private TextView textViewEscape0;
    private TextView textViewPusher0;
    private TextView textViewInitiator0;
    private boolean meleeFlag;
    private boolean rangedFlag;
    private boolean carryFlag;
    private boolean supportFlag;
    private boolean nukerFlag;
    private boolean disablerFlag;
    private boolean junglerFlag;
    private boolean durableFlag;
    private boolean escapeFlag;
    private boolean pusherFlag;
    private boolean initiatorFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        database = new Database(this);
        heroList = database.listHero();
        if (heroList.size() == 0) {
            initHero();
        }
        collectList = database.listCollect();
        collectFlag = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        mAdapter = new ListViewAdapter(this, heroList, mHead);
        mListView.setAdapter(mAdapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(mListView);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                final Hero hero = (Hero) mAdapter.getItem(position);
                if (collectFlag) {
                    dialog.setMessage("取消收藏 " + hero.getChineseName() + " ?");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.deleteCollect(hero.getId());
                            collectList = database.listCollect();
                            mAdapter.setList(collectList);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else {
                    dialog.setMessage("收藏英雄 " + hero.getChineseName() + " ?");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.insertCollect(hero.getId());
                            collectList = database.listCollect();
                        }
                    });
                }
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collectFlag == false)
                {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_round));
                    Toast.makeText(getApplication(), "已进入收藏夹", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                    Toast.makeText(getApplication(), "已退出收藏夹", Toast.LENGTH_SHORT).show();
                }
                strengthSelected = true;
                agilittySelected = true;
                intelligenceSelected = true;
                strength.setImageResource(R.mipmap.strength_attribute_symbol);
                agility.setImageResource(R.mipmap.agility_attribute_symbol);
                intelligence.setImageResource(R.mipmap.intelligence_attribute_symbol);
                meleeFlag = false;
                rangedFlag = false;
                carryFlag = false;
                supportFlag = false;
                nukerFlag = false;
                disablerFlag = false;
                junglerFlag = false;
                durableFlag = false;
                escapeFlag = false;
                pusherFlag = false;
                initiatorFlag = false;
                textViewMelee0.setTextColor(getResources().getColor(R.color.gray));
                textViewRanged0.setTextColor(getResources().getColor(R.color.gray));
                textViewCarry0.setTextColor(getResources().getColor(R.color.gray));
                textViewSupport0.setTextColor(getResources().getColor(R.color.gray));
                textViewNuker0.setTextColor(getResources().getColor(R.color.gray));
                textViewDisabler0.setTextColor(getResources().getColor(R.color.gray));
                textViewJungler0.setTextColor(getResources().getColor(R.color.gray));
                textViewDurable0.setTextColor(getResources().getColor(R.color.gray));
                textViewEscape0.setTextColor(getResources().getColor(R.color.gray));
                textViewPusher0.setTextColor(getResources().getColor(R.color.gray));
                textViewInitiator0.setTextColor(getResources().getColor(R.color.gray));
                collectFlag = !collectFlag;
                query();
            }
        });
        strength = (ImageView)findViewById(R.id.imageViewStrength);
        agility = (ImageView)findViewById(R.id.imageViewAgility);
        intelligence = (ImageView)findViewById(R.id.imageViewIntelligence);
        strengthSelected = true;
        agilittySelected = true;
        intelligenceSelected = true;
        textViewMelee0 = (TextView) findViewById(R.id.textViewMelee0);
        textViewRanged0 = (TextView) findViewById(R.id.textViewRanged0);
        textViewCarry0 = (TextView) findViewById(R.id.textViewCarry0);
        textViewSupport0 = (TextView) findViewById(R.id.textViewSupport0);
        textViewNuker0 = (TextView) findViewById(R.id.textViewNuker0);
        textViewDisabler0 = (TextView) findViewById(R.id.textViewDisabler0);
        textViewJungler0 = (TextView) findViewById(R.id.textViewJungler0);
        textViewDurable0 = (TextView) findViewById(R.id.textViewDurable0);
        textViewEscape0 = (TextView) findViewById(R.id.textViewEscape0);
        textViewPusher0 = (TextView) findViewById(R.id.textViewPusher0);
        textViewInitiator0 = (TextView) findViewById(R.id.textViewInitiator0);
        meleeFlag = false;
        rangedFlag = false;
        carryFlag = false;
        supportFlag = false;
        nukerFlag = false;
        disablerFlag = false;
        junglerFlag = false;
        durableFlag = false;
        escapeFlag = false;
        pusherFlag = false;
        initiatorFlag = false;
        searchClick();
    }


    private void searchClick()
    {
        strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strength.setImageResource(R.mipmap.strength_attribute_symbol);
                if (strengthSelected && agilittySelected && intelligenceSelected || !strengthSelected && (agilittySelected || intelligenceSelected)) {
                    agilittySelected = false;
                    intelligenceSelected = false;
                    agility.setImageResource(R.mipmap.agility_attribute_symbol_gray);
                    intelligence.setImageResource(R.mipmap.intelligence_attribute_symbol_gray);
                }
                else {
                    agilittySelected = true;
                    intelligenceSelected = true;
                    agility.setImageResource(R.mipmap.agility_attribute_symbol);
                    intelligence.setImageResource(R.mipmap.intelligence_attribute_symbol);
                }
                strengthSelected = true;
                query();
            }
        });

        agility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agility.setImageResource(R.mipmap.agility_attribute_symbol);
                if (strengthSelected && agilittySelected && intelligenceSelected || !agilittySelected && (strengthSelected || intelligenceSelected)) {
                    strengthSelected = false;
                    intelligenceSelected = false;
                    strength.setImageResource(R.mipmap.strength_attribute_symbol_gray);
                    intelligence.setImageResource(R.mipmap.intelligence_attribute_symbol_gray);
                }
                else {
                    strengthSelected = true;
                    intelligenceSelected = true;
                    strength.setImageResource(R.mipmap.strength_attribute_symbol);
                    intelligence.setImageResource(R.mipmap.intelligence_attribute_symbol);
                }
                agilittySelected = true;
                query();
            }
        });

        intelligence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intelligence.setImageResource(R.mipmap.intelligence_attribute_symbol);
                if (strengthSelected && agilittySelected && intelligenceSelected || !intelligenceSelected && (strengthSelected || agilittySelected)) {
                    strengthSelected = false;
                    agilittySelected = false;
                    strength.setImageResource(R.mipmap.strength_attribute_symbol_gray);
                    agility.setImageResource(R.mipmap.agility_attribute_symbol_gray);
                }
                else {
                    strengthSelected = true;
                    agilittySelected = true;
                    strength.setImageResource(R.mipmap.strength_attribute_symbol);
                    agility.setImageResource(R.mipmap.agility_attribute_symbol);
                }
                intelligenceSelected = true;
                query();
            }
        });

        textViewMelee0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meleeFlag) {
                    meleeFlag = false;
                    textViewMelee0.setTextColor(getResources().getColor(R.color.gray));
                }
                else {
                    meleeFlag = true;
                    rangedFlag = false;
                    textViewMelee0.setTextColor(getResources().getColor(R.color.black));
                    textViewRanged0.setTextColor(getResources().getColor(R.color.gray));
                }
                query();
            }
        });

        textViewRanged0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rangedFlag) {
                    rangedFlag = false;
                    textViewRanged0.setTextColor(getResources().getColor(R.color.gray));
                }
                else {
                    meleeFlag = false;
                    rangedFlag = true;
                    textViewMelee0.setTextColor(getResources().getColor(R.color.gray));
                    textViewRanged0.setTextColor(getResources().getColor(R.color.black));
                }
                query();
            }
        });

        textViewCarry0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCarry0.setTextColor(getResources().getColor(carryFlag ? R.color.gray : R.color.black));
                carryFlag = !carryFlag;
                query();
            }
        });

        textViewSupport0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewSupport0.setTextColor(getResources().getColor(supportFlag ? R.color.gray : R.color.black));
                supportFlag = !supportFlag;
                query();
            }
        });

        textViewNuker0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewNuker0.setTextColor(getResources().getColor(nukerFlag ? R.color.gray : R.color.black));
                nukerFlag = !nukerFlag;
                query();
            }
        });

        textViewDisabler0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDisabler0.setTextColor(getResources().getColor(disablerFlag ? R.color.gray : R.color.black));
                disablerFlag = !disablerFlag;
                query();
            }
        });

        textViewJungler0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewJungler0.setTextColor(getResources().getColor(junglerFlag ? R.color.gray : R.color.black));
                junglerFlag = !junglerFlag;
                query();
            }
        });

        textViewDurable0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDurable0.setTextColor(getResources().getColor(durableFlag ? R.color.gray : R.color.black));
                durableFlag = !durableFlag;
                query();
            }
        });

        textViewEscape0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewEscape0.setTextColor(getResources().getColor(escapeFlag ? R.color.gray : R.color.black));
                escapeFlag = !escapeFlag;
                query();
            }
        });

        textViewPusher0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPusher0.setTextColor(getResources().getColor(pusherFlag ? R.color.gray : R.color.black));
                pusherFlag = !pusherFlag;
                query();
            }
        });

        textViewInitiator0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewInitiator0.setTextColor(getResources().getColor(initiatorFlag ? R.color.gray : R.color.black));
                initiatorFlag = !initiatorFlag;
                query();
            }
        });
    }

    private void query() {
        boolean andFlag = false;
        String and = " AND ";
        String selection = "";
        if (!(strengthSelected && agilittySelected && intelligenceSelected)) {
            andFlag = true;
            selection += "species = ";
            if (strengthSelected) {
                selection += Hero.Species.strength.ordinal();
            }
            else if (agilittySelected) {
                selection += Hero.Species.agility.ordinal();
            }
            else if (intelligenceSelected) {
                selection += Hero.Species.intelligence.ordinal();
            }
        }
        if (meleeFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "attack_mode = " + Hero.AttackMode.melee.ordinal();
        }
        else if (rangedFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "attack_mode = " + Hero.AttackMode.ranged.ordinal();
        }
        if (carryFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "carry > 0";
        }
        if (supportFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "support > 0";
        }
        if (nukerFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "nuker > 0";
        }
        if (disablerFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "disabler > 0";
        }
        if (junglerFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "jungler > 0";
        }
        if (durableFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "durable > 0";
        }
        if (escapeFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "escape_ > 0";
        }
        if (pusherFlag) {
            if (andFlag) {
                selection += and;
            }
            andFlag = true;
            selection += "pusher > 0";
        }
        if (initiatorFlag) {
            if (andFlag) {
                selection += and;
            }
            selection += "initiator > 0";
        }
        if (selection.equals("")) {
            mAdapter.setList(collectFlag ? collectList : heroList);
        }
        else {
            selectList = collectFlag ? database.queryCollect(selection) : database.queryHero(selection);
            mAdapter.setList(selectList);
        }
        mAdapter.notifyDataSetChanged();
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


    private void setData(){
        mAdapter = new ListViewAdapter(this, heroList, mHead);
        mListView.setAdapter(mAdapter);
    }

    class MyTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            arg0.getParent().requestDisallowInterceptTouchEvent(true);
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
            heroList = database.listHero();
        } catch (Exception e) {

        }

    }
}



