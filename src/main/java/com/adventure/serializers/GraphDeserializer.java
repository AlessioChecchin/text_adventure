package com.adventure.serializers;

import com.adventure.models.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.IOException;

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
    public GraphDeserializer(Class<?> vc, String test)
    {
        super(vc);
        this.test = test;
    }

    @Override
    public Graph deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);
        return new DefaultDirectedGraph<>(StoryNodeLink.class);
    }

    private String test;
}
