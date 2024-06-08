package com.adventure.models;

import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
        super(inventory, stats, name);
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
        //search item in the inventory
        Item item = this.getInventory().getItems().stream().filter(Item -> itemName.equals(Item.getName())).findFirst().orElse(null);

        //check if item is in the inventory
        if(item != null) {
            //check if it's a usable item
            if (item instanceof UsableItem usableItem) {

                if (usableItem.getHp() != 0) {
                    this.heal(usableItem.getHp());
                }

                if (usableItem.getAttack() != 0) {
                    int newAttack = usableItem.getAttack() + this.getStats().getBaseAttack();
                    this.getStats().setBaseAttack(newAttack);
                }

                if (usableItem.getDefence() != 0) {
                    int newDefence = usableItem.getDefence() + this.getStats().getBaseDefense();
                    this.getStats().setBaseDefense(newDefence);
                }

                this.getInventory().getItems().remove(item);
            }
            else{
                throw new NotUsableItemException();
            }
        }
        else{
            throw new NoSuchElementException();
        }
    }
}
