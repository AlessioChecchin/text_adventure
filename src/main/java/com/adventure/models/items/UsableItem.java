package com.adventure.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    public UsableItem(@JsonProperty("name") String name)
    {
        super(name);
        this.atk = 0;
        this.def = 0;
        this.hp = 0;
    }

    //
    //  SETTERS
    //

    @JsonProperty("atk")
    public void setAdditionalAttack(int atk) { this.atk = atk; }
    @JsonProperty("def")
    public void setAdditionalDefence(int def) { this.def = def; }
    @JsonProperty("hp")
    public void setAdditionalHp(int hp) { this.hp = hp; }

    //
    //  GETTERS
    //

    @JsonProperty("atk")
    public int getAttack() { return  this.atk; }
    @JsonProperty("def")
    public int getDefence() { return this.def; }
    @JsonProperty("hp")
    public int getHp() { return this.hp; }

    //
    //  OTHER
    //

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("name \t").append(name).append("\n");
        result.append("atk  \t").append(atk).append("\n");
        result.append("def  \t").append(def).append("\n");
        result.append("hp   \t").append(hp).append("\n");
        return result.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        UsableItem usa = (UsableItem) obj;
        return ((atk == usa.getAttack()) && (def == usa.getDefence()) && (hp == usa.getHp()) && super.equals(usa));
    }


    private int atk, def, hp;
}
