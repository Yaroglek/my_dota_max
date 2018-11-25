package com.example.administrator.midtermprojectgruop35;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_HERO = "CREATE TABLE if not exists HERO (id INTEGER PRIMARY KEY, name TEXT, icon BLOB, minimap_icon BLOB, chinese_name TEXT, nickname TEXT, species INTEGER, attack_mode INTEGER, difficult INTEGER, carry INTEGER, support INTEGER, nuker INTEGER, disabler INTEGER, jungler INTEGER, durable INTEGER, escape_ INTEGER, pusher INTEGER, initiator INTEGER, strength INTEGER, agility INTEGER, intelligence INTEGER, strength_up REAL, agility_up REAL, intelligence_up REAL, health INTEGER, mana INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_HERO);
        String CREATE_TABLE_COLLECT = "CREATE TABLE if not exists COLLECT (id INTERGER PRIMARY KEY REFERENCES HERO(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        sqLiteDatabase.execSQL(CREATE_TABLE_COLLECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }

    public void insertHero(Hero hero) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapIcon = hero.getIcon();
        bitmapIcon.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] icon = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapMinimapIcon = hero.getMinimapIcon();
        bitmapMinimapIcon.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] minimapIcon = byteArrayOutputStream.toByteArray();
        contentValues.put("id", hero.getId());
        contentValues.put("name", hero.getName());
        contentValues.put("icon", icon);
        contentValues.put("minimap_icon", minimapIcon);
        contentValues.put("chinese_name", hero.getChineseName());
        contentValues.put("nickname", hero.getNickname());
        contentValues.put("species", hero.getSpecies().ordinal());
        contentValues.put("attack_mode", hero.getAttackMode().ordinal());
        contentValues.put("difficult", hero.getDifficult());
        contentValues.put("carry", hero.getCarry());
        contentValues.put("support", hero.getSupport());
        contentValues.put("nuker", hero.getNuker());
        contentValues.put("disabler", hero.getDisabler());
        contentValues.put("jungler", hero.getJungler());
        contentValues.put("durable", hero.getDurable());
        contentValues.put("escape_", hero.getEscape());
        contentValues.put("pusher", hero.getPusher());
        contentValues.put("initiator", hero.getInitiator());
        contentValues.put("strength", hero.getStrength());
        contentValues.put("agility", hero.getAgility());
        contentValues.put("intelligence", hero.getIntelligence());
        contentValues.put("strength_up", hero.getStrengthUp());
        contentValues.put("agility_up", hero.getAgilityUp());
        contentValues.put("intelligence_up", hero.getIntelligenceUp());
        contentValues.put("health", hero.getHealth());
        contentValues.put("mana", hero.getMana());
        sqLiteDatabase.insert("HERO", null, contentValues);
        sqLiteDatabase.close();
    }

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

    public Hero queryHero(int id) {
        Hero hero = null;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("HERO", null, "id = ?", new String[] {id + ""}, null, null, null);
        while (cursor.moveToNext()) {
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
            hero = new Hero(id, name, icon, minimapIcon, chineseName, nickname, species, attackMode, difficult, carry, support, nuker, disabler, jungler, durable, escape, pusher, initiator, strength, agility, intelligence, strengthUp, agilityUp, intelligenceUp, health, mana);
        }
        cursor.close();
        sqLiteDatabase.close();
        return hero;
    }

    public void deleteHero(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("HERO", "id = ?", new String[] {id + ""});
        sqLiteDatabase.close();
    }

    public List<Hero> listHero() {
        List<Hero> heroList = new ArrayList<Hero>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("HERO", null, null, null, null, null, null);
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

    public void insertCollect(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        sqLiteDatabase.insert("COLLECT", null, contentValues);
        sqLiteDatabase.close();
    }

    public List<Hero> listCollect() {
        List<Hero> heroList = new ArrayList<Hero>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("COLLECT", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            heroList.add(queryHero(id));
        }
        cursor.close();
        sqLiteDatabase.close();
        return heroList;
    }

    public void deleteCollect(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("COLLECT", "id = ?", new String[] {id + ""});
        sqLiteDatabase.close();
    }

    public boolean isCollected(int id) {
        List<Hero> heroList = new ArrayList<Hero>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("COLLECT", null, "id = ?", new String[] {id + ""}, null, null, null);
        boolean findFlag = false;
        while (cursor.moveToNext()) {
            findFlag = true;
        }
        cursor.close();
        sqLiteDatabase.close();
        return findFlag;
    }

    public List<Hero> queryCollect(String selection) {
        List<Hero> heroList = new ArrayList<Hero>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT y.* FROM COLLECT x, HERO y WHERE x.id = y.id AND y." + selection, null);
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

}