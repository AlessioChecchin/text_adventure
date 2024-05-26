package com.adventure.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    //  SETTERS
    //

    public void setAdder(int adder) { this.additionalDefence = adder; }
    public void setMultiplier(double multiplier) { this.defenceMultiplier = multiplier; }

    //
    //  GETTERS
    //

    @JsonProperty("additionalDefence")
    public int getAdder() { return this.additionalDefence; }
    @JsonProperty("defenceMultiplier")
    public double getMultiplier() { return this.defenceMultiplier; }



    //
    //  OTHER
    //

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { return true; }
        if (obj == null || this.getClass() != obj.getClass()) { return false; }

        DefenceItem def = (DefenceItem) obj;
        return ((additionalDefence == def.getAdder()) && (defenceMultiplier == def.getMultiplier() && (super.equals(def))));
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("name \t").append(name).append("\n");
        result.append("adder\t").append(additionalDefence).append("\n");
        result.append("multi\t").append(defenceMultiplier).append("\n");
        return result.toString();
    }


    private int additionalDefence;
    private double defenceMultiplier;

}
