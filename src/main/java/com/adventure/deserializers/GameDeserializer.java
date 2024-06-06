package com.adventure.deserializers;

import com.adventure.models.*;
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
            creatStoryNodeMap(vertexIterator.next());




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
            previous = storyNodes.get(node.get("previousNode").get("id").asText());
        if(!node.get("currentNode").asText().equals("null"))
            current = storyNodes.get(node.get("currentNode").get("id").asText());

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

    /**
     * Deserialize vertexes and populates storyNodes map
     * @param element {@literal <String,JsonNode>} as {@literal <unique id, storyNode node>}
     */
    private void creatStoryNodeMap(Map.Entry<String,JsonNode> element) throws JsonProcessingException
    {
        //  Get key and value from map
        String key = element.getKey();
        JsonNode value = element.getValue();

        //  Checks the type of the storyNode
        if(value.get("@type").asText().equals("Room"))
        {
            //  Get room
            Room room = fromNodetoRoom(value);

            //  Populate map
            storyNodes.put(key,room);
        }

    }

    /**
     * Deserialize a JsonNode in a Room object
     * @param roomNode JsonNode to use
     * @return Room deserialized
     */
    private Room fromNodetoRoom(JsonNode roomNode)  throws JsonProcessingException
    {
        if(roomNode.asText().equals("null"))
            return null;
        // * Name
        // * Description
        Room room = new Room(roomNode.get("name").asText(), roomNode.get("description").asText(), roomNode.get("numericID").asInt());
        // * Completed
        room.setCompleted(roomNode.get("completed").asBoolean());
        // * BackgroundPath
        room.setBackgroundPath(roomNode.get("backgroundPath").asText());
        // * Items
        ArrayList<Item> items = new ArrayList<>();
        Iterator<JsonNode> iterator = roomNode.get("items").elements();
        while(iterator.hasNext())
            items.add(fromNodeToObject(iterator.next(),Item.class));
        room.setItems(items);
        // * Entities
        room.setMonster(fromNodeToObject(roomNode.get("monster"), Enemy.class));

        return room;
    }


    /**
     * Given the class name the JsonNode it uses the default deserializer to deserialize the JsonNode using the given class
     * @param objectNode JsonNode to use
     * @param tClass Class type to use
     * @return A deserialized object of type tClass
     * @param <T> the type to use
     */
    private <T> T fromNodeToObject(JsonNode objectNode, Class<T> tClass) throws JsonProcessingException
    {
            return new ObjectMapper().readValue(objectNode.toString(), tClass);
    }


    private HashMap<String, StoryNode> storyNodes;
    private Graph<StoryNode, StoryNodeLink> gameGraph;

}
