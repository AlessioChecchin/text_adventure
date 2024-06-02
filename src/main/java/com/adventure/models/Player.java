package com.adventure.models;

import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;

import java.util.List;
import java.util.Objects;

public class Player extends Entity
{
    private String name;

    public Player(String name, Inventory inventory, Stats stats)
    {
        super(inventory, stats);
        stats.setHp(20);
        stats.setBaseAttack(3);
        stats.setBaseDefense(3);

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

    public void use(Item item){
        UsableItem usableItem = (UsableItem) item;
        this.heal(usableItem.getHp());
        this.getInventory().getItems().remove(item);
    }
}
