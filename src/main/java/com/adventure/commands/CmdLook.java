package com.adventure.commands;

import com.adventure.models.items.Item;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to retrieve information about the room the user is in.
 */
public class CmdLook extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if (!this.correctArgumentsNumber(0))
        {
            this.writer.println("Invalid number of arguments! Usage: look");
            return;
        }

        StoryNode node = this.context.getGame().getCurrentNode();

        if(node instanceof Room room)
        {
            if( !room.getItems().isEmpty())
            {
                this.writer.println("In the current room you can see: ");
                for (Item itm : room.getItems())
                    this.writer.printf("* %s%n", itm);
            }
            else
            {
                this.writer.println("No items in this room.");
            }
        }
        else
        {
            this.writer.println("Command not supported in this node!");
        }
    }

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
