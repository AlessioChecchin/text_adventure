package com.adventure.models;

public class Enemy extends NPC{

    /**
     * Entity constructor.
     *
     * @param inventory Enemy inventory.
     * @param stats     Enemy stats.
     */
    public Enemy(Inventory inventory, Stats stats) {
        super(inventory, stats);
    }
}
