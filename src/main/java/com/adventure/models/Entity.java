package com.adventure.models;

import java.util.Objects;

/**
 * Represents a generic entity.
 */
abstract public class Entity
{
    /**
     * Entity constructor.
     * @param inventory Player inventory.
     * @param stats Player stats.
     */
    public Entity(Inventory inventory, Stats stats)
    {
        this.setInventory(inventory);
        this.setStats(stats);

        this.availableDodges = 3;
    }

    //
    // GETTERS.
    //

    /**
     * Inventory getter.
     * @return The entity inventory.
     */
    public Inventory getInventory()
    {
        return this.inventory;
    }

    /**
     * Returns if the entity is alive.
     * @return True if the entity is alive, false otherwise.
     */
    public boolean getAlive()
    {
        return stats.getHp() > 0;
    }

    /**
     * Stats getter.
     * @return Entity stats.
     */
    public Stats getStats()
    {
        return this.stats;
    }

    //
    // SETTERS.
    //

    /**
     * Inventory setter.
     * @param inventory Inventory to set.
     */
    public void setInventory(Inventory inventory)
    {
        Objects.requireNonNull(inventory, "inventory cannot be null");
        this.inventory = inventory;
    }

    /**
     * Setter used to kill or revive the entity.
     * @param alive If is set to false, then the entity hp are set to 0. If is set to true and the entity was dead
     *              then the hp are set to maxHp.
     */
    public void setAlive(boolean alive)
    {
        if(!this.getAlive() && alive) stats.setHp(stats.getMaxHp());
        if(!alive) stats.setHp(0);
    }

    /**
     * Sets entity stats.
     * @param stats Entity stats.
     */
    public void setStats(Stats stats)
    {
        Objects.requireNonNull(inventory, "stats cannot be null");
        this.stats = stats;
    }

    /**
     * Available dodges setter.
     * @param dodges Available dodges.
     */
    private void setAvailableDodges(int dodges)
    {
        this.availableDodges = dodges;
    }

    //
    // OTHERS.
    //

    /**
     * Uses a dodge.
     * @return Returns true if the player had a dodge to consume, false otherwise.
     */
    public boolean useDodge()
    {
        if(availableDodges == 0) return false;
        availableDodges--;
        return true;
    }

    /**
     * Resets the available dodges to the maximum amount.
     */
    public void resetDodge()
    {
        this.availableDodges = 3;
    }

    /**
     * Heals the entity.
     * @param points Health points to gain.
     */
    public void heal(int points)
    {
        this.stats.setHp(this.stats.getHp() + points);
    }

    /**
     * Damages the entity.
     * @param damage Health points to lose.
     */
    public void hit(int damage)
    {
        if(damage > this.stats.getHp()) this.stats.setHp(0);
        else this.stats.setHp(this.stats.getHp() - damage);
    }

    //
    // OTHERS.
    //

    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Entity entity = (Entity) obj;
        return ((this.stats.equals(entity.getStats())) && (this.inventory.equals(entity.getInventory())));
    }

    //
    // VARIABLES.
    //

    /**
     * Entity inventory.
     */
    protected Inventory inventory;

    /**
     * Entity stats.
     */
    protected Stats stats;

    /**
     * Currently available dodges.
     */
    private int availableDodges;
}
