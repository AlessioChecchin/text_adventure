package com.adventure.deserializers;

import com.adventure.models.Enemy;
import com.adventure.models.items.Item;
import com.adventure.models.nodes.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.adventure.deserializers.DeserializingUtility.fromNodeToObject;

public class RoomDeserializer extends StdDeserializer<Room>
{
    public RoomDeserializer()
    {
        this(null);
    }
    public RoomDeserializer(Class<?> vc)
    {
        super(vc);
    }
    @Override
    public Room deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);
        if(node.asText().equals("null"))
            return null;
        // * Name
        // * Description
        Room room = new Room(node.get("name").asText(), node.get("description").asText(), node.get("numericID").asInt());
        // * Completed
        room.setCompleted(node.get("completed").asBoolean());
        // * BackgroundPath
        room.setBackgroundPath(node.get("backgroundPath").asText());
        // * Items
        ArrayList<Item> items = new ArrayList<>();
        Iterator<JsonNode> iterator = node.get("items").elements();
        while(iterator.hasNext())
            items.add(fromNodeToObject(iterator.next(),Item.class));
        room.setItems(items);
        // * Entities
        room.setMonster(fromNodeToObject(node.get("monster"), Enemy.class));

        return room;
    }
}
