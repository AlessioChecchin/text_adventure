package com.adventure.models.items;

public class Usable extends Item
{
    public Usable(String name, int weight, int atk, int def, int hp, int armor)
    {
        super(name, weight);
        this.atk = atk;
        this.def = def;
        this.hp = hp;
    }
    public Usable(String name, int weight, String description, int atk, int def, int hp, int armor)
    {
        super(name, weight, description);
        this.atk = atk;
        this.def = def;
        this.hp = hp;
    }

    public int getAtk()
    {
        return this.atk;
    }
    public int getDef()
    {
        return this.def;
    }
    public int getHp()
    {
        return this.hp;
    }

    private final int atk, def, hp;
}
