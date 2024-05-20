package com.adventure.models;

import com.adventure.models.items.*;
import java.util.ArrayList;

public class Inventory
{
    public Inventory(int maxWeight)
    {
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
    }


    public void add(Item item)
    {
        this.items.add(item);
        this.currentWeight += item.getWeight();
    }

    public void drop(Item item)
    {
        this.items.remove(item);
        this.currentWeight -= item.getWeight();
    }

    public void equip(Item item)
    {
        if(item instanceof Equipable)
        {
            // If it's a defense item than it has atk = 0
            if(((Equipable) item).getAtk() == 0)
                this.defItem = item;
            // If it's an attack item than it has def = 0
            else if(((Equipable) item).getDef() == 0)
                this.atkItem = item;
        }
    }


    // Getters
    public int getCurrentWeight() {
        return currentWeight;
    }
    public int getMaxWeight() {
        return maxWeight;
    }
    public ArrayList<Item> getItems() {
        return items;
    }

    private ArrayList<Item> items;
    private Item atkItem;
    private Item defItem;
    private int currentWeight;
    private final int maxWeight;
}
