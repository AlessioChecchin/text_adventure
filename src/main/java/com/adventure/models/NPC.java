package com.adventure.models;

import com.adventure.models.items.Item;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class used for a generic Non-Playing-Character.
 */
public abstract class NPC extends Entity
{
    /**
     * Entity constructor.
     *
     * @param inventory Player inventory.
     * @param stats Player stats.
     * @param name Npc name
     */
    public NPC(Inventory inventory, Stats stats, String name)
    {
        super(inventory, stats, name);
        this.defaultDialog = "";
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
        Objects.requireNonNull(defaultDialog, "Default dialog cannot be null");
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
    public void drop(StoryNode node)
    {
        if((node instanceof Room room) && (!this.getAlive()))
        {
            for(Item itm: this.inventory.getItems())
            {
                room.getItems().add(itm);
            }

            this.inventory.setItems(new ArrayList<>());
        }
    }

    /**
     * NPC default dialog.
     */
    private String defaultDialog;
}
