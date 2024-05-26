package com.adventure.serializers;

import com.adventure.models.Game;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.adventure.utils.ApplicationContextProvider;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jgrapht.Graph;

import java.io.IOException;

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
        Game game = new Game(ApplicationContextProvider.getInstance().getProperties());

        // Set stage
        game.setStage(ApplicationContextProvider.getInstance().getStage());

        // Get game graph
        Graph<StoryNode, StoryNodeLink> gameGraph = game.getGameGraph();

        // Construct all rooms
        ObjectMapper mapper = new ObjectMapper();

        //JsonNode array = node.get("gameGraph").get("rooms");
        for(JsonNode room : node.get("gameGraph").get("rooms"))
            gameGraph.addVertex(mapper.readValue(room.toString(), Room.class));

//        JsonNode firstRoom = array.get(0);
//        Room room = null;
//        if(firstRoom.get("@type").asText().equals("Room"))
//        {
//            room = mapper.readValue(firstRoom.toString(), Room.class);
//            System.out.println(room.toString());
//        }
        return null;
    }
}
