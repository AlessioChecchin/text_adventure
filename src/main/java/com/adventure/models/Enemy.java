package com.adventure.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Enemy extends NPC{

    /**
     * Entity constructor.
     *
     * @param inventory Enemy inventory.
     * @param stats     Enemy stats.
     */
    @JsonCreator
    public Enemy(@JsonProperty("inventory") Inventory inventory, @JsonProperty("stats") Stats stats)
    {
        super(inventory, stats);
    }
}
