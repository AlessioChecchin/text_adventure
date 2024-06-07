package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import org.jgrapht.Graph;

import java.util.ArrayList;

public class CmdWai extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        StoryNode currentNode = this.context.getGame().getCurrentNode();

        if(currentNode instanceof Room room)
        {
            this.writer.println(room.getDescription());
            this.writer.println("Allowed directions:" + System.lineSeparator());

            Graph<StoryNode, StoryNodeLink> g = this.context.getGame().getGameGraph();

            for(StoryNodeLink link: g.outgoingEdgesOf(currentNode))
            {
                if(link.getLocked())
                {
                    this.writer.printf("* %s, locked with key=%s%n", link.getAction().getActionName(), link.getKey());
                }
                else
                {
                    this.writer.printf("* %s, not locked%n", link.getAction().getActionName());
                }
            }
        }
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
