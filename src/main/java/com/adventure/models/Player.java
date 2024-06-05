package com.adventure.models;

import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Player entity.
 */
public class Player extends Entity
{

    /**
     * Player constructor.
     * @param name Player name.
     * @param inventory Inventory name.
     * @param stats Player stats.
     */
    public Player(@JsonProperty("name") String name, @JsonProperty("inventory") Inventory inventory, @JsonProperty("stats") Stats stats)
    {
        super(inventory, stats);

        this.setName(name);
    }

    //
    // GETTERS.
    //

    /**
     * Name getter.
     * @return Player name.
     */
    public String getName()
    {
        return name;
    }

    //
    // SETTERS.
    //

    /**
     * Name setter.
     * @param name Player name.
     */
    public void setName(String name)
    {
        Objects.requireNonNull(name, "Can't set a null name");
        this.name = name;
    }

    //
    // OTHERS.
    //

    /**
     * Method implemented to use an item.
     * @param item Item to use.
     */
    public void use(Item item)
    {
        UsableItem usableItem = (UsableItem) item;

        if(usableItem.getHp() != 0)
        {
            this.heal(usableItem.getHp());
        }

        if(usableItem.getAttack() != 0)
        {
            int newAttack = usableItem.getAttack() + this.getStats().getBaseAttack();
            this.getStats().setBaseAttack(newAttack);
        }

        if(usableItem.getDefence() != 0)
        {
            int newDefence = usableItem.getDefence() + this.getStats().getBaseDefense();
            this.getStats().setBaseDefense(newDefence);
        }

        this.getInventory().getItems().remove(item);
    }

    @Override
    public boolean equals(Object obj)
    {
        Player player = (Player) obj;

        return ((super.equals(player)) && (this.name.equals(player.getName())));
    }

    private String name;
}
