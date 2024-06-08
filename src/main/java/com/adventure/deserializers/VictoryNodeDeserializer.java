package com.adventure.deserializers;

import com.adventure.models.nodes.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class VictoryNodeDeserializer extends StdDeserializer<VictoryNode>
{
    public VictoryNodeDeserializer()
    {
        this(null);
    }
    public VictoryNodeDeserializer(Class<?> vc)
    {
        super(vc);
    }
    @Override
    public VictoryNode deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);
        VictoryNode victoryNode = new VictoryNode(node.get("numericID").asInt());
        victoryNode.setTargetView(node.get("targetView").asText());
        return victoryNode;
    }
}
