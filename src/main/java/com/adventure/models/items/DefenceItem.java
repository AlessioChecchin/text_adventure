package com.adventure.models.items;

import com.adventure.interfaces.Equipable;

public class DefenceItem extends Item implements Equipable
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
    public DefenceItem(String name)
    {
        super(name);
        this.additionalDefence = 0;
        this.defenceMultiplier = 1;
    }

    //
    //  SETTERS
    //

    public void setAdder(int adder) { this.additionalDefence = adder; }
    public void setMultiplier(double multiplier) { this.defenceMultiplier = multiplier; }

    //
    //  GETTERS
    //

    public int getAdder() { return this.additionalDefence; }
    public double getMultiplier() { return this.defenceMultiplier; }


    private int additionalDefence;
    private double defenceMultiplier;

}
