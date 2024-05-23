package com.adventure.models.items;

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


    protected String name;
    protected int weight;
}
