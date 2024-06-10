package com.adventure.models;

import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.NoSuchElementException;

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
        super(inventory, stats, name);
        this.isFighting = false;
    }

    //
    // OTHERS.
    //

    /**
     * Method implemented to use an item.
     * @param itemName Item to use.
     */
    public void use(String itemName) throws NoSuchElementException, NotUsableItemException
    {
        // Search item in the inventory
        Item item = this.getInventory().getItems().stream().filter(Item -> itemName.equals(Item.getName())).findFirst().orElse(null);

        // Check if item is in the inventory
        if(item != null)
        {
            // Check if it's a usable item
            if (item instanceof UsableItem usableItem)
            {
                // Healing the player.
                if (usableItem.getHp() != 0)
                {
                    this.heal(usableItem.getHp());
                }

                // Improving attack stats.
                if (usableItem.getAttack() != 0)
                {
                    int newAttack = usableItem.getAttack() + this.getStats().getBaseAttack();
                    this.getStats().setBaseAttack(newAttack);
                }

                // Improving defence.
                if (usableItem.getDefence() != 0)
                {
                    int newDefence = usableItem.getDefence() + this.getStats().getBaseDefense();
                    this.getStats().setBaseDefense(newDefence);
                }

                this.getInventory().dropItem(item);
            }
            else{
                throw new NotUsableItemException();
            }
        }
        else{
            throw new NoSuchElementException();
        }
    }

    /**
     * Checks whether the player is fighting or not
     * @return true if the player is fighting, false otherwise
     */
    @JsonIgnore
    public boolean isFighting(){ return this.isFighting; }

    /**
     * Set the fighting status of the player
     * @param status boolean fight status
     */
    @JsonIgnore
    public void setFightingStatus(boolean status) { this.isFighting = status; }

    /**
     * Entity fighting status
     */
    @JsonIgnore
    private boolean isFighting;
}
