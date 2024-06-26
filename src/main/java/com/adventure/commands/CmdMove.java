package com.adventure.commands;

import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.items.Item;
import com.adventure.models.items.Key;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import javafx.application.Platform;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Command used by the player to move inside the game graph.
 */
public class CmdMove extends AbstractCommand
{

    @Override
    public void execute() throws InterruptedException
    {
        if(!this.correctArgumentsNumber(1))
        {
            this.writer.println("Invalid number of arguments! Usage: move <direction>");
            return;
        }

        String edgeName = this.getArgs().get(0);
        Game game = this.context.getGame();

        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();
        Set<StoryNodeLink> outgoingEdges = g.outgoingEdgesOf(game.getCurrentNode());

        boolean validDirection = false;
        // Checking outgoing edges to see if the direction is allowed.
        for(StoryNodeLink outgoingEdge: outgoingEdges)
        {
            String targetName = outgoingEdge.getAction().getActionName();
            if(edgeName.equals(targetName))
            {
                validDirection = true;

                // If the passage is locked then we search if the user has the correct key.
                if(outgoingEdge.getLocked())
                {
                    Inventory playerInventory = this.context.getGame().getPlayer().getInventory();

                    List<Item> items = playerInventory.getItems();
                    boolean found = false;
                    for(int i = 0; i < items.size() && !found; i++)
                    {
                        Item curr = items.get(i);
                        if(curr instanceof Key inventoryKey)
                        {
                            // If the edge is successfully unlocked, then we stop the loop and consume the key.
                            if(outgoingEdge.tryUnlock(inventoryKey.getValue()))
                            {
                                found = true;
                                playerInventory.dropItem(curr);
                            }
                        }
                    }

                    // If the edge is still locked, then the user can't pass.
                    if(outgoingEdge.getLocked())
                    {
                        this.writer.printf("Sorry, you can't pass here, you need a %s key!%n", outgoingEdge.getKey());
                    }
                    else
                    {
                        this.moveToTarget(outgoingEdge);
                    }
                }
                else
                {
                    this.moveToTarget(outgoingEdge);
                }
            }
        }

        if(!validDirection)
        {
            this.writer.println("Direction not valid!");
        }
    }

    protected void moveToTarget(StoryNodeLink link)
    {
        Game game = this.context.getGame();

        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();
        StoryNode node = g.getEdgeTarget(link);

        Platform.runLater(() -> {
            game.setCurrentNode(node);
            game.load();

            this.writer.println("Passage opened successfully!");
        });
    }


    /**
     * @return all possible directions for the player
     */
    public List<String> getPossibleArgs()
    {
        ArrayList<String> result = new ArrayList<>();
        StoryNode currentNode = context.getGame().getCurrentNode();
        Graph<StoryNode, StoryNodeLink> g = context.getGame().getGameGraph();

        for(StoryNodeLink link: g.outgoingEdgesOf(currentNode))
            result.add(link.getAction().getActionName());
        return result;
    }

}
