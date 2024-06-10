package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to gather information about current player location.
 * Stands for "where am I".
 */
public class CmdWai extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if(!this.correctArgumentsNumber(0))
        {
            this.writer.println("Incorrect number of arguments! Usage: wai");
            return;
        }

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

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
