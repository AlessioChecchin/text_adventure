package com.adventure.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Player stats.
 */
public class    Stats
{
    /**
     * Default constructor.
     */
    public Stats(@JsonProperty("hp") int hp,
                 @JsonProperty("maxHp") int maxHp,
                 @JsonProperty("baseAttack") int baseAttack,
                 @JsonProperty("baseDefense") int baseDefense)
    {
        this.maxHp = maxHp;
        this.hp = Math.min(hp, this.maxHp);
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    //
    // GETTERS.
    //

    /**
     * Hp getter.
     * @return Hp.
     */
    public int getHp()
    {
        return this.hp;
    }

    /**
     * Max hp getter.
     * @return Max hp.
     */
    public int getMaxHp()
    {
        return this.maxHp;
    }

    /**
     * Base attack getter.
     * @return Base attack.
     */
    public int getBaseAttack()
    {
        return this.baseAttack;
    }

    /**
     * Base defence getter.
     * @return Base defence.
     */
    public int getBaseDefense()
    {
        return this.baseDefense;
    }

    //
    // SETTERS.
    //

    /**
     * Hp setter.
     * @param hp Hp.
     */
    public void setHp(int hp)
    {
        // If the user wants to set more hp than maximum, then we truncate to the maximum value.
        this.hp = Math.min(hp, this.maxHp);
    }

    /**
     * Max hp setter.
     * @param maxHp Max hp.
     */
    public void setMaxHp(int maxHp)
    {
        if(maxHp < 0) return;
        this.maxHp = maxHp;
    }

    /**
     * Base attack setter.
     * @param baseAttack Base attack.
     */
    public void setBaseAttack(int baseAttack)
    {
        if(baseAttack < 0) return;
        this.baseAttack = baseAttack;
    }

    /**
     * Base defence setter.
     * @param baseDefense Base defence.
     */
    public void setBaseDefense(int baseDefense)
    {
        this.baseDefense = baseDefense;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Stats stats = (Stats) obj;
        return ((this.baseDefense == stats.getBaseDefense()) && (this.baseAttack == stats.getBaseAttack())
            && (this.hp == stats.getHp()) && (this.maxHp == stats.getMaxHp()));
    }

    //
    // VARIABLES.
    //

    /**
     * Player current health points.
     */
    private int hp;

    /**
     * Player max health points.
     */
    private int maxHp;

    /**
     * Player base attack.
     */
    private int baseAttack;

    /**
     * Player base defence.
     */
    private int baseDefense;
}
