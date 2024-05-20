package com.adventure.models.items;

public class Equipable extends Item
{
    public Equipable(String name, int weight, int atk, int def)
    {
        this(name,weight,"",atk,def);
    }
    public Equipable(String name, int weight, String description, int atk, int def)
    {
        super(name, weight, description);
        // An equipable item cannot give both atk and def
        if(atk != 0 && def != 0)
            throw new IllegalArgumentException("No such equipable item");
        this.atk = atk;
        this.def = def;
    }

    public int getAtk()
    {
        return this.atk;
    }
    public int getDef()
    {
        return this.def;
    }

    private final int  atk, def;
}
