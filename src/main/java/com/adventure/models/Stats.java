package com.adventure.models;

public class Stats
{
    private int hp;
    private int maxHp;
    private int baseAttack;
    private int baseDefense;

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
     * Hp getter.
     * @return Hp.
     */
    public int getHp()
    {
        return this.hp;
    }

    /**
     * Max hp setter.
     * @param maxHp Max hp.
     */
    public void setMaxHp(int maxHp)
    {
        this.maxHp = maxHp;
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
     * Base attack setter.
     * @param baseAttack Base attack.
     */
    public void setBaseAttack(int baseAttack)
    {
        this.baseAttack = baseAttack;
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
     * Base defence setter.
     * @param baseDefense Base defence.
     */
    public void setBaseDefense(int baseDefense)
    {
        this.baseDefense = baseDefense;
    }

    /**
     * Base defence getter.
     * @return Base defence.
     */
    public int getBaseDefense()
    {
        return this.baseDefense;
    }
}
