package com.adventure.models;

import java.util.List;

public class Player {
    private String name;
    private List<Item> inventory;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory)
    {
        this.inventory = inventory;
    }
}
