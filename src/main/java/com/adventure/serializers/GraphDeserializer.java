package com.adventure.serializers;

import com.adventure.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.IOException;
import java.util.Properties;

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
