package com.adventure.models;

import com.adventure.models.items.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

public class Player extends Entity
{
    private String name;

    public Player(String name, Inventory inventory, Stats stats)
    {
        super(inventory, stats);

        this.setName(name);
    }

    public void setName(String name)
    {
        Objects.requireNonNull(name, "Can't set a null name");
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
