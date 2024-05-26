package com.adventure.models.items;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AttackItem.class, name = "AttackItem"),
        @JsonSubTypes.Type(value = DefenceItem.class, name = "DefenceItem"),
        @JsonSubTypes.Type(value = UsableItem.class, name = "UsableItem")
})
public abstract class Item
{

    /**
     * Default constructor
     * <ul>
     *     <li>weight: 0</li>
     * </ul>
     * @param name Name of the item
     */
    public Item(String name)
    {
        this.name = name;
        this.weight = 0;
    }

    //
    //  SETTERS
    //

    /**
     * Set the weight of the item
     * @param weight weight of the item
     */
    public void setWeight(int weight) { this.weight = weight; }

    //
    //  GETTERS
    //

    /**
     * Get the name of the item
     * @return name of the item
     */
    public String getName() { return this.name; }
    /**
     * Get the weight of the item
     * @return weight of the item
     */
    public int getWeight() { return this.weight; }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item a = (Item) o;
        return ((name.equals(a.getName())) && weight == a.getWeight());
    }


    protected String name;
    protected int weight;
}
