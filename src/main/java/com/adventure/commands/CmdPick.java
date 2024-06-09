package com.adventure.commands;

import com.adventure.models.Inventory;
import com.adventure.models.items.Item;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to pick an item from a room.
 */
public class CmdPick extends AbstractCommand
{

    @Override
    public void execute() throws InterruptedException
    {
        if(!this.correctArgumentsNumber(1))
        {
            return;
        }

        String itemName = this.getArgs().get(0);

        StoryNode node = this.context.getGame().getCurrentNode();

        Inventory playerInventory = this.context.getGame().getPlayer().getInventory();

        if(node instanceof Room room)
        {
            List<Item> items = room.getItems();

            boolean found = false;
            for(int i = 0; i < items.size() && !found; i++)
            {
                if(itemName.equals(items.get(i).getName()))
                {
                    Item removed = items.remove(i);
                    playerInventory.addItem(removed);
                    found = true;
                }
            }

            if(!found)
            {
                this.writer.println("Item not found.");
            }
            else
            {
                this.writer.println("Item is now in your inventory.");
            }
        }
        else
        {
            this.writer.println("Can't perform this command here.");
        }
    }

    /**
     * @return all possible items the player can pick
     */
    public ArrayList<String> getPossibleArgs()
    {
        ArrayList<String> result = new ArrayList<>();
        StoryNode node = context.getGame().getCurrentNode();
        if(node instanceof Room room)
        {
            List<Item> items = room.getItems();
            for(Item item : items)
                result.add(item.getName());
        }
        return result;
    }
}
