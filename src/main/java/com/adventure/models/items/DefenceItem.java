package com.adventure.models.items;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    /**
     * Constructor.
     * @param name Item name.
     * @param defenceMultiplier Defence multiplier.
     * @param additionalDefence Additional defence.
     */
    @JsonCreator
    public DefenceItem(@JsonProperty("name") String name, @JsonProperty("defenceMultiplier") double defenceMultiplier, @JsonProperty("additionalDefence") int additionalDefence)
    {
        super(name);

        if(defenceMultiplier < 0) throw new IllegalArgumentException("defenceMultiplier cannot be negative");
        if(additionalDefence < 0) throw new IllegalArgumentException("additionalDefence cant be negative");

        this.defenceMultiplier = defenceMultiplier;
        this.additionalDefence = additionalDefence;
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

    /**
     * isEquiped getter
     * @return IsEquiped
     */
    public boolean getIsEquiped(){
        return this.isEquiped;
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

    /**
     * isEquipable setter.
     * @param equip equip condition.
     */
    public void setEquiped(boolean equip){
        this.isEquiped = equip;
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
        String result = String.format("%s (def: %d, %f)", this.getName(), this.getAdder(), this.getMultiplier());
        if(this.isEquiped) return result + " [Equipped]";
        return result;
    }

    /**
     * Item additional defence.
     */
    private int additionalDefence;

    /**
     * item defence multiplier.
     */
    private double defenceMultiplier;

    /**
     * Check if item is equiped.
     */
    private boolean isEquiped;

}
