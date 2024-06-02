package com.adventure.models;

public abstract class NPC extends Entity {
    /**
     * Entity constructor.
     *
     * @param inventory Player inventory.
     * @param stats     Player stats.
     */
    public NPC(Inventory inventory, Stats stats) {
        super(inventory, stats);
    }

    public String getDefaultDialog() {
        return defaultDialog;
    }

    public void setDefaultDialog(String defaultDialog) {
        this.defaultDialog = defaultDialog;
    }

    private String defaultDialog;
}
