package com.adventure.deserializers;

import com.adventure.models.*;
import static com.adventure.deserializers.DeserializingUtility.fromNodeToObject;

import com.adventure.models.items.AttackItem;
import com.adventure.models.items.DefenceItem;
import com.adventure.models.items.Equipable;
import com.adventure.models.items.Item;
import com.adventure.models.nodes.*;
import com.adventure.config.ApplicationContextProvider;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jgrapht.Graph;

import java.io.IOException;
import java.util.*;

public class GameDeserializer extends StdDeserializer<Game>
{
    public GameDeserializer()
    {
        this(null);
    }
    public GameDeserializer(Class<?> vc)
    {
        super(vc);
    }
    @Override
    public Game deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);

        // Create Game with Properties
        Game game = new Game(Objects.requireNonNull(ApplicationContextProvider.getInstance()).getConfig());



        //
        //  GAMEGRAPH
        //

        //  Reset ID counter for nodes when loading a new game
        IdManager.getInstance().resetCounter();

        // Get game graph node and gameGraph object
        ObjectMapper mapper = new ObjectMapper();
        JsonNode graphNode = node.get("gameGraph");
        gameGraph = game.getGameGraph();

        //  Vertexes contains all info concerning StoryNodes
        //  Edges contains all info concerning StoryNodeLinks that links 2 StoryNodes together
        JsonNode vertexesNode = graphNode.get("vertexes");
        JsonNode edgesNode = graphNode.get("edges");


        //  vertexes deserialization
        //      StoryNodes map
        //      key: StoryNode unique id
        //      value: StoryNode object
        storyNodes  = new HashMap<>();
        Iterator<Map.Entry<String,JsonNode>> vertexIterator = vertexesNode.fields();
        while(vertexIterator.hasNext())
        {
            Map.Entry<String,JsonNode> element = vertexIterator.next();
            storyNodes.put(element.getKey(), fromNodeToObject(element.getValue(), StoryNode.class));
        }


        //  Add vertexes to graph
        //  Vertexes are added to graph in order of ID
        ArrayList<String> sortedKeys = new ArrayList<String>(storyNodes.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys)
            gameGraph.addVertex(storyNodes.get(key));
        //storyNodes.forEach((k,v) -> gameGraph.addVertex(v));

        //  Deserialize edges and populate graph
        Iterator<Map.Entry<String,JsonNode>> edgesIterator = edgesNode.fields();
        while(edgesIterator.hasNext())
            populateGraph(edgesIterator.next());



        //
        //  CURRENTNODE
        //  PREVIOUSNODE
        //


        //  To obtain previous or current we get the ID of the node inside the json
        //  Then the ID is used to get the node from the storyNodes
        StoryNode previous = null;
        StoryNode current = null;
        if(!node.get("previousNode").asText().equals("null"))
            previous = storyNodes.get(node.get("previousNode").get("ID").asText());
        if(!node.get("currentNode").asText().equals("null"))
            current = storyNodes.get(node.get("currentNode").get("ID").asText());

        /*  Since previousNode is set only when a new currentNode is set, we set
            firstly set the currentNode as previousNode and the set the currentNode with
            the real currentNode.
            In this way previousNode is set correctly
            If the previousNode is null we only set the currentNode
         */

        if(previous != null)
        {
            game.setCurrentNode(previous);
            game.setCurrentNode(current);
        }
        else
        {
            game.setCurrentNode(current);
        }


        //
        //  ID
        //

        game.setId(node.get("id").asText());


        //
        //  PLAYER
        //

        Player player = fromNodeToObject(node.get("player"), Player.class);

        // Fix bug for which when you loaded a saved game where you had an item equipped,
        // the instance saved in "equippedItem" and the instance saved in the Inventory were different.
        // This implies that when you drop that item, the item is removed from the inventory but not from
        // the "equippedItem" variable, thus leaving the atk/def stats untouched

        // If the player has an attackItem equipped
        if(player.getInventory().getEquipedAttackItem() != null)
        {
            // Get the name of the equipped item
            AttackItem equipped = player.getInventory().getEquipedAttackItem();
            String equippedName = equipped.getName();
            // Try to find it in the inventory
            ArrayList<Item> realItem = player.getInventory().itemsByName(equippedName);
            // If it's found, equip the item contained in the inventory
            if(realItem != null && realItem.size() == 1 && realItem.get(0) instanceof Equipable)
                player.getInventory().equipItem((Equipable) realItem.get(0));
            // Else unequip all
            else
                player.getInventory().unequipItem(Inventory.EquipType.ATTACK);
        }
        // If the player has a defenceItem equipped
        if(player.getInventory().getEquipedDefenceItem() != null)
        {
            // Get the name of the equipped item
            DefenceItem equipped = player.getInventory().getEquipedDefenceItem();
            String equippedName = equipped.getName();
            // Try to find it in the inventory
            ArrayList<Item> realItem = player.getInventory().itemsByName(equippedName);
            // If it's found, equip the item contained in the inventory
            if(realItem != null && realItem.size() == 1 && realItem.get(0) instanceof Equipable)
                // Else unequip all
                player.getInventory().equipItem((Equipable) realItem.get(0));
            else
                player.getInventory().unequipItem(Inventory.EquipType.DEFENSE);
        }

        game.setPlayer(player);

        return game;
    }


    /**
     * Populate gameGraph with vertexes and links
     * @param element {@literal <String,JsonNode>} as {@literal <unique id, edge node>}
     */
    private void populateGraph(Map.Entry<String,JsonNode> element) throws JsonProcessingException
    {
        //  Get key and value from map
        String key = element.getKey();
        JsonNode value = element.getValue();

        //  Creates StoryNodeLink
        StoryNodeLink link = new StoryNodeLink();
        link.setKey(value.get("key").asText());
        link.setLocked(value.get("locked").asBoolean());
        link.setAction(fromNodeToObject(value.get("action"), Action.class));

        //  Get source and target of the StoryNodeLink
        StoryNode sourceStoryNode = storyNodes.get(value.get("from").asText());
        StoryNode targetStoryNode = storyNodes.get(value.get("to").asText());

        //  Populate the graph
        gameGraph.addEdge(sourceStoryNode, targetStoryNode, link);
    }


    private HashMap<String, StoryNode> storyNodes;
    private Graph<StoryNode, StoryNodeLink> gameGraph;

}
