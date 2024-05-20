package com.adventure.models.items;

public class Item
{

    public Item(String name, int weight)
    {
        this(name, weight, "");
    }

    public Item(String name, int weight, String description)
    {
        this.weight = weight;
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return this.name;
    }

    public int getWeight()
    {
        return this.weight;
    }

    public String getDescription()
    {
        return this.description;
    }


    private final int weight;
    private final String name;
    private final String description;
}
