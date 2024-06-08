package com.adventure.commands;

import com.adventure.models.items.Item;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;

public class CmdLook extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
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
                this.writer.println("No items in this room");
        }
        else
        {
            this.writer.println("Command not supported in this room!");
        }
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
