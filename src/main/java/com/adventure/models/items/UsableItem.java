package com.adventure.models.items;

import com.adventure.interfaces.Usable;

public class UsableItem extends Item implements Usable
{
    /**
     * Default constructor
     * <ul>
     *     <li>attack: 0</li>
     *     <li>defence: 0</li>
     *     <li>hp: 0</li>
     * @param name Name of the item
     */
    public UsableItem(String name)
    {
        super(name);
        this.atk = 0;
        this.def = 0;
        this.hp = 0;
    }

    //
    //  SETTERS
    //

    public void setAdditionalAttack(int atk) { this.atk = atk; }
    public void setAdditionalDefence(int def) { this.def = def; }
    public void setAdditionalHp(int hp) { this.hp = hp; }

    //
    //  GETTERS
    //

    public int getAttack() { return  this.atk; }
    public int getDefence() { return this.def; }
    public int getHp() { return this.hp; }


    private int atk, def, hp;
}
