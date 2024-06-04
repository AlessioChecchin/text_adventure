package com.adventure.commands;

import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import org.jgrapht.Graph;

import java.util.Objects;
import java.util.Set;

public class CmdWai extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        StoryNode currentNode = this.context.getGame().getCurrentNode();

        if(currentNode instanceof Room room)
        {
            this.writer.println(room.getDescription());
            this.writer.println("Allowed directions: ");

            Graph<StoryNode, StoryNodeLink> g = this.context.getGame().getGameGraph();

            System.out.println(room.getItems().hashCode() + " " + Objects.hash(room.getName(), room.getDescription(), room.getItems()));

            for(StoryNode node: g.vertexSet())
            {
                Room r = (Room)node;
                System.out.println(r.getDescription() + " " + (r.hashCode() == room.hashCode()) + " " + r.equals(currentNode) + " " + Objects.hash(r.getName(), r.getDescription(), r.getItems()));
            }

            System.out.println(g.vertexSet().contains(currentNode));

            for(StoryNodeLink link: g.outgoingEdgesOf(currentNode))
            {
                this.writer.printf("%s, locked=%s%n", link.getAction().getActionName(), link.getLocked() ? "true" : "false");
            }
        }
    }
}
