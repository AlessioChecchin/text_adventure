package com.adventure.serializers;

import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.LinkedNode;
import org.jgrapht.Graph;

import com.adventure.nodes.Room;

import java.io.IOException;

public class GraphSerializer extends StdSerializer<Graph>
{
    public GraphSerializer()
    {
        this(null);
    }
    public GraphSerializer(Class<Graph> t)
    {
        super(t);
    }

    @Override
    public void serialize(Graph graph, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("rooms");
        for(Object room : graph.vertexSet())
        {
            if(room instanceof Room)
                jsonGenerator.writeObject((Room)room);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}