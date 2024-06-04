package com.adventure.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

abstract public class Entity
{
    protected Inventory inventory;
    protected boolean alive;
    protected Stats stats;
    private int dodge;

    /**
     * Entity constructor.
     * @param inventory Player inventory.
     * @param stats Player stats.
     */
    public Entity(Inventory inventory, Stats stats)
    {
        this.setInventory(inventory);
        this.alive = true;
        this.setStats(stats);
        this.dodge = 3;
    }

    public void setInventory(Inventory inventory)
    {
        Objects.requireNonNull(inventory, "inventory cannot be null");
        this.inventory = inventory;
    }

    public Inventory getInventory()
    {
        return this.inventory;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public boolean getAlive()
    {
        return (stats.getHp() > 0);
    }

    public void setStats(Stats stats)
    {
        Objects.requireNonNull(inventory, "stats cannot be null");
        this.stats = stats;
    }

    public Stats getStats()
    {
        return this.stats;
    }

    public boolean useDodge() {
        if(dodge == 0) return false;
        dodge--;
        return true;
    }

    public void resetDodge() {
        this.dodge = 3;
    }

    public void heal(int points)
    {
        this.stats.setHp(this.stats.getHp() + points);
    }

    public void hit(int damage)
    {
        this.stats.setHp(this.stats.getHp() - damage);
    }

    //  Necessary for deserialization of entities
    private void setDodge(int dodges) { this.dodge = dodges; }
}
