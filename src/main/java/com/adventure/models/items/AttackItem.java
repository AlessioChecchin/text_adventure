package com.adventure.models.items;

import com.adventure.interfaces.Equipable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public AttackItem(@JsonProperty("name") String name)
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

    @JsonProperty("additionalAttack")
    public int getAdder() { return this.additionalAttack; }
    @JsonProperty("attackMultiplier")
    public double getMultiplier() { return this.attackMultiplier; }



    //
    //  OTHER
    //

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { return true; }
        if (obj == null || this.getClass() != obj.getClass()) { return false; }

        AttackItem att = (AttackItem) obj;
        return ((additionalAttack == att.getAdder()) && (attackMultiplier == att.getMultiplier() && (super.equals(att))));
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("name \t").append(name).append("\n");
        result.append("adder\t").append(additionalAttack).append("\n");
        result.append("multi\t").append(attackMultiplier).append("\n");
        return result.toString();
    }

    private int additionalAttack;
    private double attackMultiplier;

}