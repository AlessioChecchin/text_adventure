package com.adventure.models;

import java.util.Objects;

abstract public class Entity
{
    protected Inventory inventory;
    protected boolean alive;
    protected Stats stats;

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
        return this.alive;
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

    public void heal(int points)
    {
        this.stats.setHp(this.stats.getHp() + points);
    }

    public void hit(int damage)
    {
        this.stats.setHp(this.stats.getHp() - damage);
    }
}