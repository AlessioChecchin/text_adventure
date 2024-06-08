package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.Game;
import com.adventure.models.Player;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CmdDrop extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if(!this.correctArgumentsNumber(1)) { return; }

        Game game = this.context.getGame();
        Player player = game.getPlayer();
        StoryNode currentNode = game.getCurrentNode();

        // Checks if the StoryNode is a Room or something else
        Room currentRoom = currentNode instanceof Room ? (Room) currentNode : null;
        if(currentRoom == null)
        {
            this.writer.println("Looks like there's nowhere to put this item");
            return;
        }

        // Get the dropped item name
        String key = this.getArgs().get(0);

        try
        {
            Item droppedItem = player.getInventory().dropItem(key);
            currentRoom.getItems().add(droppedItem);
            this.writer.println("You dropped the item " + key + ".");
        }
        catch (NoSuchElementException noElement)
        {
            this.writer.println("Item not found!");
        }
    }

    @Override
    public ArrayList<String> getPossibleArgs() throws GameStorageException
    {
        Player player = context.getGame().getPlayer();
        List<Item> item = player.getInventory().getItems();
        ArrayList<String> possibleItems = new ArrayList<>();
        for(Item i : item)
            possibleItems.add(i.getName());
        return possibleItems;
    }
}
