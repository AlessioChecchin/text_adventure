package com.adventure.models.items;

import com.adventure.interfaces.Equipable;

public class AttackItem extends Item implements Equipable
{
    /**
     * Default constructor
     * <ul>
     *     <li>weight: 0</li>
     *     <li>adder: 0</li>
     *     <li>multiplier: 1</li>
     * </ul>
     * @param name Name of the item
     */
    public AttackItem(String name)
    {
        super(name);
        this.attackMultiplier = 1;
        this.additionalAttack = 0;
    }

    //
    //  SETTERS
    //

    public void setAdder(int adder) { this.additionalAttack = adder; }
    public void setMultiplier(double multiplier) { this.attackMultiplier = multiplier; }

    //
    //  GETTERS
    //

    public int getAdder() { return this.additionalAttack; }
    public double getMultiplier() { return this.attackMultiplier; }


    private int additionalAttack;
    private double attackMultiplier;

}