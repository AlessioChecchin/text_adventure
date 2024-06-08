package com.adventure.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity enemy.
 */
public class Enemy extends NPC
{

    /**
     * Entity constructor.
     *
     * @param inventory Enemy inventory.
     * @param stats Enemy stats.
     * @param name Enemy name.
     */
    @JsonCreator
    public Enemy(@JsonProperty("inventory") Inventory inventory, @JsonProperty("stats") Stats stats, @JsonProperty("name") String name)
    {
        super(inventory, stats, name);
    }
}
