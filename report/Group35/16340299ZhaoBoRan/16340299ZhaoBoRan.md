# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016 | 专业（方向） | 软件工程(数字媒体) |
| 学号 | 16340299 | 姓名 | 赵博然 |
| 电话 | 15626143471 | Email | king_yaroglek@163.com |
| 开始日期 | 2018.11.24 | 完成日期 | 2018.11.25
---
## 一、实验题目
## Dota2英雄大全
---

## 二、实现内容


因为我讨厌Dota2以外的moba游戏, 所以我提议选择Dota2作为期中项目的素材.

Dota2有116个英雄, 我选择了26个属性(实际用了25个).

增删功能作为收藏夹实现.

可以修改英雄的8个属性(实际只能修改6个, 其中的2个属性关联另外2个属性). 因为Dota2版本更新修改的英雄属性在我选择的26个属性中也只修改这8个.

可以根据英雄的其中的11个属性进行筛选.

---

## 三、实验结果

## 资源文件, 数据存储, 增删改查.

资源文件, 包括一份表格`hero_list.xls`: 各英雄的id, 英文名字, 中文名字, 昵称(这是项目中没用上的属性), 主属性(力量, 敏捷或智力), 攻击方式(近战或远程), 难度(1-3), 以及9大指数(0-3), 包括核心, 辅助, 爆发, 控制, 打野, 耐久, 逃生, 推进, 先手, 还有三大属性(力量, 敏捷, 智力)的初始值及属性的成长值, 初始生命值和魔法值, 共24个属性(除去id和未使用的昵称, 在app中体现为22个), 以及其对应的图片文件. 还有各英雄的图标, 小图标(加上这两个就是26个属性), 以及三大属性的图标. 是我从Dota2维基和Dota2一点点扒下来的. 无聊并快乐着.

---

数据库`Database.java`. 使用数据库存储英雄数据. 维护英雄和收藏夹两个表, 建表语句如下.
```
@Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String CREATE_TABLE_HERO = "CREATE TABLE if not exists HERO (id INTEGER PRIMARY KEY, name TEXT, icon BLOB, minimap_icon BLOB, chinese_name TEXT, nickname TEXT, species INTEGER, attack_mode INTEGER, difficult INTEGER, carry INTEGER, support INTEGER, nuker INTEGER, disabler INTEGER, jungler INTEGER, durable INTEGER, escape_ INTEGER, pusher INTEGER, initiator INTEGER, strength INTEGER, agility INTEGER, intelligence INTEGER, strength_up REAL, agility_up REAL, intelligence_up REAL, health INTEGER, mana INTEGER)";

    sqLiteDatabase.execSQL(CREATE_TABLE_HERO);

    String CREATE_TABLE_COLLECT = "CREATE TABLE if not exists COLLECT (id INTERGER PRIMARY KEY REFERENCES HERO(id) ON DELETE CASCADE ON UPDATE CASCADE)";

    sqLiteDatabase.execSQL(CREATE_TABLE_COLLECT);
}
```

---

第一次启动应用时, 通过`hero_list.xls`, 将所有英雄的信息同步至数据库.

```
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
```
---

增删英雄. 考虑到随意增删英雄的不现实性, 我设计了一个收藏夹列表, 可以收藏英雄至收藏夹, 也可以将已收藏的英雄从收藏夹中移出, 这样就做到了某种意义上的增删功能.

在`MainActivity`中维护一个`collectList`列表, 用来同步数据库收藏夹表对应的英雄信息, 在点击收藏按钮时将它与英雄列表的`ListView`所展示的列表(全英雄列表或者筛选过的列表)进行替换.

---

修改英雄. Dota2版本更新会修改很多, 但在我选取的26个属性中只会修改其中6个, 即力量, 敏捷, 智力, 力量成长, 敏捷成长, 智力成长. 另外力量和智力还关系到初始生命值和魔法值.

`初始生命值 = 200 + 初始力量 × 倍数n`. 对于力量英雄, `n = 22.48`, 否则`n = 18`.

`初始魔法值 = 75 + 初始智力 × 倍数n`. 对于智力英雄, `n = 15`, 否则`n = 12`.

在英雄详情界面点击对应的`TextView`即可修改. 当输入值非法时不进行修改. 下面以修改力量值为例.

```
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
```

数据库更新语句.
```
public void updateHero(int id, String selection, int number) {
    SQLiteDatabase sqLiteDatabase = getReadableDatabase();
    ContentValues values = new ContentValues();
    values.put(selection, number);
    sqLiteDatabase.update("HERO", values, "id = ?", new String[]{id + ""});
    sqLiteDatabase.close();
}

public void updateHero(int id, String selection, double number) {
    SQLiteDatabase sqLiteDatabase = getReadableDatabase();
    ContentValues values = new ContentValues();
    values.put(selection, number);
    sqLiteDatabase.update("HERO", values, "id = ?", new String[]{id + ""});
    sqLiteDatabase.close();
}
```
---

查找英雄. 我认为这是本次项目我做得最好的部分, 因为很巧妙. 另外这也是Dota2游戏内的功能. 即根据英雄的其中11个属性筛选英雄. 包括主属性, 攻击方式, 以及9大指数(核心, 辅助, 爆发, 控制, 打野, 耐久, 逃生, 推进, 先手).

在主界面的上方, 从左至右分别排列为3个图标(主属性), 2个单字按钮(攻击方式, 即近, 远), 9个单字按钮(9大属性, 即核, 辅, 爆, 控, 野, 肉, 逃, 推, 先).

主属性和攻击方式只能各选择其中一项, 9大属性可以多个同时筛选.

如果在收藏夹中进行筛选, 筛选总体为收藏夹, 而非全部英雄.

举例. 我想筛选出所有敏捷英雄, 远程英雄, 核心英雄, 控制英雄. 只需如图点击即可筛选. 筛选出了`巨魔战将`和`卓尔游侠`.

![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/222153_b51bfcb4_2162369.png "屏幕截图.png")

维护一个`selectList`列表, 用来存储被筛选出的英雄信息. 使用`query()`函数进行查找. 如果未进行查找, 则显示为全英雄列表或收藏列表, 否则显示筛选列表.

```
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
```

数据库查询函数. 在`query()`中会传来一个筛选条件`selection`, 根据这个筛选条件生成相应的查询语句. 这里是处于全英雄界面中的查询函数. 当处于收藏界面时, 调用另一个查询函数.

```
public List<Hero> queryHero(String selection) {
    List<Hero> heroList = new ArrayList<Hero>();
    SQLiteDatabase sqLiteDatabase = getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT y.* FROM HERO y WHERE  y." + selection, null);
    while (cursor.moveToNext()) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        byte[] bytesIcon = cursor.getBlob(2);
        Bitmap icon = BitmapFactory.decodeByteArray(bytesIcon, 0, bytesIcon.length);
        byte[] bytesMinimapIcon = cursor.getBlob(3);
        Bitmap minimapIcon = BitmapFactory.decodeByteArray(bytesMinimapIcon, 0, bytesMinimapIcon.length);
        String chineseName = cursor.getString(4);
        String nickname = cursor.getString(5);
        Hero.Species species = Hero.Species.values()[cursor.getInt(6)];
        Hero.AttackMode attackMode = Hero.AttackMode.values()[cursor.getInt(7)];
        int difficult = cursor.getInt(8);
        int carry = cursor.getInt(9);
        int support = cursor.getInt(10);
        int nuker = cursor.getInt(11);
        int disabler = cursor.getInt(12);
        int jungler = cursor.getInt(13);
        int durable = cursor.getInt(14);
        int escape = cursor.getInt(15);
        int pusher = cursor.getInt(16);
        int initiator = cursor.getInt(17);
        int strength = cursor.getInt(18);
        int agility = cursor.getInt(19);
        int intelligence = cursor.getInt(20);
        double strengthUp = cursor.getDouble(21);
        double agilityUp = cursor.getDouble(22);
        double intelligenceUp = cursor.getDouble(23);
        int health = cursor.getInt(24);
        int mana = cursor.getInt(25);
        heroList.add(new Hero(id, name, icon, minimapIcon, chineseName, nickname, species, attackMode, difficult, carry, support, nuker, disabler, jungler, durable, escape, pusher, initiator, strength, agility, intelligence, strengthUp, agilityUp, intelligenceUp, health, mana));
    }
    cursor.close();
    sqLiteDatabase.close();
    return heroList;
}
```
---

## 四、实验遇到的困难及解决思路

如何读取`Excel`表格中的数据. 我查找资料后新增了一个`jxl.jar`包, 调用其中的方法实现了读取表格.

---
## 五、实验思考及感想

这次期中项目内容充实, 也是我喜欢的Dota2的内容. 没有做王者荣耀还请见谅.