package com.example.administrator.midtermprojectgruop35;

import android.graphics.Bitmap;

public class Hero {

    public enum Species {strength, agility, intelligence};
    public enum AttackMode {melee, ranged};

    public static String speciesToString(Species species) {
        switch (species) {
            case strength:
                return "力量";
            case agility:
                return "敏捷";
            case intelligence:
                return "智力";
            default:
                return "";
        }
    }

    public static String attackModeToString(AttackMode attackMode) {
        switch (attackMode) {
            case melee:
                return "近战";
            case ranged:
                return "远程";
            default:
                return "";
        }
    }

    private int id;
    private String name;
    private Bitmap icon;
    private Bitmap minimapIcon;
    private String chineseName;
    private String nickname;
    private Species species;
    private AttackMode attackMode;
    private int difficult;
    private int carry;
    private int support;
    private int nuker;
    private int disabler;
    private int jungler;
    private int durable;
    private int escape;
    private int pusher;
    private int initiator;
    private int strength;
    private int agility;
    private int intelligence;
    private double strengthUp;
    private double agilityUp;
    private double intelligenceUp;
    private int health;
    private int mana;


    public Hero(int id, String name, Bitmap icon, Bitmap minimapIcon, String chineseName, String nickname, Species species, AttackMode attackMode, int difficult, int carry, int support, int nuker, int disabler, int jungler, int durable, int escape, int pusher, int initiator, int strength, int agility, int intelligence, double strengthUp, double agilityUp, double intelligenceUp, int health, int mana) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.minimapIcon = minimapIcon;
        this.chineseName = chineseName;
        this.nickname = nickname;
        this.species = species;
        this.attackMode = attackMode;
        this.difficult = difficult;
        this.carry = carry;
        this.support = support;
        this.nuker = nuker;
        this.disabler = disabler;
        this.jungler = jungler;
        this.durable = durable;
        this.escape = escape;
        this.pusher = pusher;
        this.initiator = initiator;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.strengthUp = strengthUp;
        this.agilityUp = agilityUp;
        this.intelligenceUp = intelligenceUp;
        this.health = health;
        this.mana = mana;
    }

    public void setMinimapIcon(Bitmap minimapIcon) {
        this.minimapIcon = minimapIcon;
    }

    public Bitmap getMinimapIcon() {
        return minimapIcon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIntelligenceUp(double intelligenceUp) {
        this.intelligenceUp = intelligenceUp;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public double getIntelligenceUp() {
        return intelligenceUp;
    }

    public void setAgilityUp(double agilityUp) {
        this.agilityUp = agilityUp;
    }

    public double getAgilityUp() {
        return agilityUp;
    }

    public void setStrengthUp(double strengthUp) {
        this.strengthUp = strengthUp;
    }

    public double getStrengthUp() {
        return strengthUp;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getAgility() {
        return agility;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setInitiator(int initiator) {
        this.initiator = initiator;
    }

    public int getInitiator() {
        return initiator;
    }

    public void setPusher(int pusher) {
        this.pusher = pusher;
    }

    public int getPusher() {
        return pusher;
    }

    public void setEscape(int escape) {
        this.escape = escape;
    }

    public int getEscape() {
        return escape;
    }

    public void setDurable(int durable) {
        this.durable = durable;
    }

    public int getDurable() {
        return durable;
    }

    public void setJungler(int jungler) {
        this.jungler = jungler;
    }

    public int getJungler() {
        return jungler;
    }

    public void setDisabler(int disabler) {
        this.disabler = disabler;
    }

    public int getDisabler() {
        return disabler;
    }

    public void setNuker(int nuker) {
        this.nuker = nuker;
    }

    public int getNuker() {
        return nuker;
    }

    public void setCarry(int carry) {
        this.carry = carry;
    }

    public int getCarry() {
        return carry;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setAttackMode(AttackMode attackMode) {
        this.attackMode = attackMode;
    }

    public AttackMode getAttackMode() {
        return attackMode;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Species getSpecies() {
        return species;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public int getSupport() {
        return support;
    }
}
