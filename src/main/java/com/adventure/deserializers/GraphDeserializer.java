package com.adventure.deserializers;

import com.adventure.models.Enemy;
import com.adventure.models.Entity;
import com.adventure.models.items.Item;
import com.adventure.models.nodes.Action;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;


//
//
//  USELESS
//
//
//  TO DELETE ALL THE CLASS
//
public class GraphDeserializer extends StdDeserializer<Graph>
{
    public GraphDeserializer()
    {
        this(null);
    }
    public GraphDeserializer(Class<?> vc)
    {
        super(vc);
    }

    @Override
    public Graph deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode adjacencyNode = node.get("adjacency");
        JsonNode vertexesNode = node.get("vertexes");
        JsonNode edgesNode = node.get("edges");

        //  StoryNodes map
        //  key: StoryNode unique id
        //  value: StoryNode object
        storyNodes  = new HashMap<>();

        vertexesNode.fields().forEachRemaining(this::creatStoryNodeMap);

        //  StoryNodeLinks map
        //  key: storyNodeLink unique id
        //  value: storyNodeLink object
        storyNodeLinks = new HashMap<>();
        edgesNode.fields().forEachRemaining(this::creatStoryNodeLinkMap);


        return new DefaultDirectedGraph<>(StoryNodeLink.class);
    }

    private void creatStoryNodeLinkMap(Map.Entry<String,JsonNode> element)
    {
        String key = element.getKey();
        JsonNode value = element.getValue();

        StoryNodeLink link = new StoryNodeLink();
        link.setKey(value.get("key").asText());
        link.setLocked(value.get("locked").asBoolean());
        link.setAction(fromNodeToObject(value.get("action"), Action.class));

        storyNodeLinks.put(key, link);
    }

    private void creatStoryNodeMap(Map.Entry<String,JsonNode> element)
    {
        String key = element.getKey();
        JsonNode value = element.getValue();

        //  Checks the type of the storyNode
        if(value.get("@type").asText().equals("Room"))
        {
            // * Name
            // * Description
            Room room = new Room(value.get("name").asText(), value.get("description").asText());
            // * Completed
            room.setCompleted(value.get("completed").asBoolean());
            // * BackgroundPath
            room.setBackgroundPath(value.get("backgroundPath").asText());
            // * Items
            ArrayList<Item> items = new ArrayList<>();
            Iterator<JsonNode> iterator = value.get("items").elements();
            iterator.forEachRemaining(e -> items.add(fromNodeToObject(e,Item.class)));
            room.setItems(items);
            // * Entities
            room.setMonster(fromNodeToObject(value.get("monster"), Enemy.class));

            storyNodes.put(key,room);
        }

    }


    private <T> T fromNodeToObject(JsonNode objectNode, Class<T> tClass)
    {
        try {
            return new ObjectMapper().readValue(objectNode.toString(), tClass);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            System.err.println("Error: cannot get Object from json");
        }
        return null;
    }


    private HashMap<String, StoryNode> storyNodes;
    private HashMap<String, StoryNodeLink> storyNodeLinks;
}
