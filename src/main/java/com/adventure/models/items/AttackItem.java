package com.adventure.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Item used for attack.
 */
public class AttackItem extends Item implements Equipable
{
    /**
     * Default constructor.
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

    /**
     * Constructor.
     * @param name Item name.
     * @param attackMultiplier Attack multiplier >= 0
     * @param additionalAttack Additional attack >= 0
     */
    public AttackItem(@JsonProperty("name") String name, @JsonProperty("attackMultiplier") double attackMultiplier, @JsonProperty("additionalAttack") int additionalAttack)
    {
        super(name);

        if(attackMultiplier < 0) throw new IllegalArgumentException("attackMultiplier can't be negative");
        if(additionalAttack < 0) throw new IllegalArgumentException("additionalAttack cant' be negative");

        this.attackMultiplier = attackMultiplier;
        this.additionalAttack = additionalAttack;
    }

    //
    // SETTERS.
    //

    /**
     * Attack adder setter.
     * @param adder additional attack value.
     */
    public void setAdder(int adder)
    {
        this.additionalAttack = adder;
    }

    /**
     * Attack multiplier setter.
     * @param multiplier additional attack multiplier.
     */
    public void setMultiplier(double multiplier)
    {
        this.attackMultiplier = multiplier;
    }

    //
    // GETTERS.
    //

    /**
     * Attack adder getter.
     * @return Attack adder.
     */
    @JsonProperty("additionalAttack")
    public int getAdder()
    {
        return this.additionalAttack;
    }

    /**
     * Attack factor getter.
     * @return Attack factor.
     */
    @JsonProperty("attackMultiplier")
    public double getMultiplier()
    {
        return this.attackMultiplier;
    }

    //
    // OTHERS.
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
        return String.format("%s (atk: %d, %f)", this.getName(), this.getAdder(), this.getMultiplier());
    }

    /**
     * Additional attack.
     */
    private int additionalAttack;

    /**
     * Attack factor.
     */
    private double attackMultiplier;
}