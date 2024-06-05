package com.adventure.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Item used for defence.
 */
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
    public DefenceItem(@JsonProperty("name") String name)
    {
        super(name);
        this.additionalDefence = 0;
        this.defenceMultiplier = 1;
    }

    //
    //  GETTERS.
    //

    /**
     * Adder getter.
     * @return Defence increase.
     */
    @JsonProperty("additionalDefence")
    public int getAdder()
    {
        return this.additionalDefence;
    }

    /**
     * Defence multiplier getter.
     * @return Defence factor.
     */
    @JsonProperty("defenceMultiplier")
    public double getMultiplier()
    {
        return this.defenceMultiplier;
    }

    //
    //  SETTERS.
    //

    /**
     * Additional defence setter.
     * @param adder additional defence value.
     */
    public void setAdder(int adder)
    {
        this.additionalDefence = adder;
    }

    /**
     * Defence multiplier setter.
     * @param multiplier additional defence multiplier
     */
    public void setMultiplier(double multiplier)
    {
        this.defenceMultiplier = multiplier;
    }

    //
    // OTHERS.
    //

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        DefenceItem def = (DefenceItem) obj;
        return ((additionalDefence == def.getAdder()) && (defenceMultiplier == def.getMultiplier() && (super.equals(def))));
    }

    @Override
    public String toString()
    {
        return String.format("name: %s, adder: %d, multi: %f", name, additionalDefence, defenceMultiplier);
    }

    /**
     * Item additional defence.
     */
    private int additionalDefence;

    /**
     * item defence multiplier.
     */
    private double defenceMultiplier;

}
