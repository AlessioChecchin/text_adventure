package com.adventure.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @Override
    public boolean equals(Object obj){
        NPC npc = (NPC) obj;
        return ((super.equals(npc)) && (this.defaultDialog.equals(npc.getDefaultDialog())));
    }

    private String defaultDialog;
}
