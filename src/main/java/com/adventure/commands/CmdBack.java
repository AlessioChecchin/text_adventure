package com.adventure.commands;

import com.adventure.models.Game;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import javafx.application.Platform;
import org.jgrapht.Graph;

import java.util.ArrayList;

public class CmdBack extends AbstractCommand
{
    @Override
    public void execute()
    {
        Game game = this.context.getGame();
        StoryNode currentNode = game.getCurrentNode();
        StoryNode previousNode = game.getPreviousNode();

        // Check if previous node was invalidated.
        if(game.hasPreviousNode() && previousNode != null)
        {
            Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();

            StoryNode back = this.getBackNode(g, currentNode, previousNode);

            // If a valid back node is found, then the node is loaded.
            if(back != null)
            {
                this.writer.println("Moved back!");

                Platform.runLater(() -> {
                    game.setCurrentNode(back);
                    game.load();
                });
            }
            else
            {
                this.writer.println("Looks like there's no way back!");
            }

        }
        else
        {
            this.writer.println("Looks like there's no way back!");
        }
    }

    private StoryNode getBackNode(Graph<StoryNode, StoryNodeLink> g, StoryNode currentNode, StoryNode candidatePrevious)
    {
        // Checking outgoing edges of current node, to check if a path is available to come back.
        for(StoryNodeLink edge: g.outgoingEdgesOf(currentNode))
        {
            StoryNode backTarget = g.getEdgeTarget(edge);

            if(backTarget != null && backTarget.equals(candidatePrevious))
            {
                return candidatePrevious;
            }
        }

        return null;
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }

}
