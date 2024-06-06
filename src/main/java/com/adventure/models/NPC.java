package com.adventure.models;

import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

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
    public NPC(Inventory inventory, Stats stats, String name)
    {
        super(inventory, stats, name);
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
        return ((super.equals(obj)) && (this.defaultDialog.equals(npc.getDefaultDialog())));
    }

    /**
     * Drop the inventory after death
     * @param node actual monster node
     */
    public void drop(StoryNode node){
        if((node instanceof Room room) && (!this.getAlive())){
            int i = 0;
            while(!this.inventory.getItems().isEmpty()){
                room.getItems().add(this.inventory.getItems().remove(i));
                i++;
            }
        }
    }

    /**
     * NPC default dialog.
     */
    private String defaultDialog;
}
