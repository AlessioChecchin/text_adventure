package com.adventure.models;

import com.adventure.models.items.Equipable;
import com.adventure.models.items.*;
import com.adventure.exceptions.TooMuchWeightException;
import com.adventure.serializers.InventorySerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class used to handle an inventory of items.
 */
@JsonSerialize(using = InventorySerializer.class)
public class Inventory
{
    /**
     * Default constructor.
     * <ul>
     *     <li>currentWeight: 0</li>
     *     <li>attackItem: hands</li>
     *     <li>defenceItem: hands</li>
     * </ul>
     * @param maxWeight Max weight the inventory can have.
     */
    @JsonCreator
    public Inventory(@JsonProperty("maxWeight") int maxWeight)
    {
        this.items = new ArrayList<>();

        this.maxWeight = maxWeight;
        this.currentWeight = 0;

        // Instantiate default items
        this.defaultAtkItem = new AttackItem("Hands");
        this.defaultDefItem = new DefenceItem("Hands");

        this.attackItem = this.defaultAtkItem;
        this.defenceItem = this.defaultDefItem;
    }

    //
    //  GETTERS.
    //

    /**
     * Get the equipped attack item.
     * @return AttackItem equipped.
     */
    public AttackItem getEquipedAttackItem()
    {
        return this.attackItem;
    }

    /**
     * Get the equipped defence item.
     * @return DefenceItem equipped.
     */
    public DefenceItem getEquipedDefenceItem()
    {
        return this.defenceItem;
    }

    /**
     * Get the value of max weight the inventory can handle.
     * @return max weight of the inventory.
     */
    public int getMaxWeight()
    {
        return this.maxWeight;
    }

    /**
     * Get the value of the current weight in the inventory.
     * @return current weight in the inventory.
     */
    public int getCurrentWeight()
    {
        return this.currentWeight;
    }

    /**
     * Get a copy of the ArrayList containing all the items.
     * @return ArrayList with all items of the inventory.
     */
    public ArrayList<Item> getItems()
    {
        return this.items;
    }

    //
    // SETTERS.
    //

    /**
     * Set max weight the inventory can hold.
     * @param maxWeight value of max weight.
     */
    public void setMaxWeight(int maxWeight)
    {
        this.maxWeight = maxWeight;
    }

    /**
     * Set the current weight in the inventory.
     * @param currentWeight value of the current weight.
     */
    public void setCurrentWeight(int currentWeight)
    {
        this.currentWeight = currentWeight;
    }

    /**
     * Set all items in the inventory.
     * @param items ArrayList containing all the items.
     * @throws TooMuchWeightException If the weight is over maxWeight.
     */
    public void setItems(ArrayList<Item> items) throws TooMuchWeightException
    {
        Objects.requireNonNull(items, "items cannot be null");

        this.items = new ArrayList<>();

        for(Item itm: items)
        {
            this.addItem(itm);
        }
    }

    /**
     * Attack item setter.
     * @param equipable Attack item.
     */
    @JsonProperty("equippedAttackItem")
    private void setAttackItem(AttackItem equipable)
    {
        this.attackItem = equipable;
    }

    /**
     * Defence item setter.
     * @param equipable Defence item.
     */
    @JsonProperty("equippedDefenceItem")
    private void setDefenceItem(DefenceItem equipable)
    {
        this.defenceItem = equipable;
    }

    //
    // OTHERS.
    //

    /**
     * Equip an equipable item
     * @param equipable Item to equip
     */
    public void equipItem(Equipable equipable)
    {
        if(!items.contains((Item) equipable)) throw new NoSuchElementException();
        if(equipable instanceof AttackItem){
            this.attackItem = (AttackItem) equipable;
            this.attackItem.setEquiped(true);
        }
        if(equipable instanceof DefenceItem){
            this.defenceItem = (DefenceItem) equipable;
            this.defenceItem.setEquiped(true);
        }
    }

    /**
     * Unequip an attack or defense item
     * @param type Type of the item to unequip (ATTACK or DEFENSE)
     */
    public void unequipItem(EquipType type)
    {
        if(type == EquipType.ATTACK)
            this.attackItem = this.defaultAtkItem;
        if(type == EquipType.DEFENSE)
            this.defenceItem = this.defaultDefItem;
    }

    /**
     * Add item to the inventory
     * @param item Item to add
     * @throws TooMuchWeightException The item weights too much for the current inventory
     */
    public void addItem(Item item)
    {
        if(!canAdd(item)) throw new TooMuchWeightException("Cannot add an item to the inventory");
        this.items.add(item);
        this.currentWeight += item.getWeight();
    }

    /**
     * Method to obtain an item instance from its name
     * @param name Name of the item to search
     * @return ArrayList with all the items with the same name
     */
    public ArrayList<Item> itemsByName(String name)
    {
        ArrayList<Item> matchItems = new ArrayList<>();
        for(Item item: this.items)
        {
            if (item.getName().equals(name))
            {
                matchItems.add(item);
            }
        }

        return matchItems;
    }

    /**
     * Drop an item and remove it from the inventory
     * @param item Item to be removed
     */
    public void dropItem(Item item)
    {
        if( !items.contains(item) ) throw new NoSuchElementException("Item not in inventory");
        items.remove(item);
        this.currentWeight -= item.getWeight();
    }

    /**
     * Check whether an item can be added or not th
     * @param item Item to be checked
     * @return True if item can be added. False otherwise
     */
    private boolean canAdd(Item item)
    {
        if(item == null) return false;
        return this.currentWeight + item.getWeight() <= this.maxWeight;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Inventory inventory = (Inventory) obj;
        return ((this.attackItem.equals(inventory.getEquipedAttackItem())) && (this.defenceItem.equals(inventory.getEquipedDefenceItem()))
        && (this.items.equals(inventory.getItems())) && (this.maxWeight == inventory.getMaxWeight()));
    }

    /**
     * Currently equipped attack item.
     */
    private AttackItem attackItem;

    /**
     * Currently equipped defence item.
     */
    private DefenceItem defenceItem;

    /**
     * Default attack item.
     */
    @JsonIgnore
    private final AttackItem defaultAtkItem;

    /**
     * Default defence item.
     */
    @JsonIgnore
    private final DefenceItem defaultDefItem;

    /**
     * List of items present in the inventory.
     */
    private ArrayList<Item> items;

    /**
     * The max weight that the inventory can carry.
     */
    private int maxWeight;

    /**
     * Current weight.
     */
    private int currentWeight;

    /**
     * Enum used to
     */
    public enum EquipType { ATTACK, DEFENSE }
}
