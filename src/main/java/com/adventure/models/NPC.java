package com.adventure.models;

/**
 * Class used for a generic Non-Playing-Character.
 */
public abstract class NPC extends Entity
{
    /**
     * Entity constructor.
     *
     * @param inventory Player inventory.
     * @param stats     Player stats.
     */
    public NPC(Inventory inventory, Stats stats)
    {
        super(inventory, stats);
    }

    //
    // GETTERS.
    //

    /**
     * Default dialog getter.
     * @return Default dialog.
     */
    public String getDefaultDialog()
    {
        return defaultDialog;
    }

    //
    // SETTERS.
    //

    /**
     * Default dialog setter.
     * @param defaultDialog Default dialog.
     */
    public void setDefaultDialog(String defaultDialog)
    {
        this.defaultDialog = defaultDialog;
    }

    @Override
    public boolean equals(Object obj)
    {
        NPC npc = (NPC) obj;
        return ((super.equals(npc)) && (this.defaultDialog.equals(npc.getDefaultDialog())));
    }

    /**
     * NPC default dialog.
     */
    private String defaultDialog;
}
