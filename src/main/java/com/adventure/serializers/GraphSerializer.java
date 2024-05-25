package com.adventure.serializers;

import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.LinkedNode;
import org.jgrapht.Graph;

import com.adventure.nodes.Room;

import java.io.IOException;

/*
    Used this structure
    https://stackoverflow.com/questions/43052290/representing-a-graph-in-json
 */

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

        //  Create adjacency list with
        //  vertex : [ array of touching edges ]
        jsonGenerator.writeObjectFieldStart("adjacency");
        for(Object vertex : graph.vertexSet())
        {
            jsonGenerator.writeArrayFieldStart(((Room)vertex).getID());
            for(Object edge : graph.edgesOf(vertex))
            {
                jsonGenerator.writeString(((StoryNodeLink)edge).getID());
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndObject();

        // Create array of vertexes
        jsonGenerator.writeObjectFieldStart("vertexes");
        for(Object vertex : graph.vertexSet())
        {
            jsonGenerator.writeFieldName(((Room)vertex).getID());
            jsonGenerator.writeObject(((StoryNode) vertex));
        }
        jsonGenerator.writeEndObject();

        //  Create array of edges
        jsonGenerator.writeObjectFieldStart("edges");
        for(Object edge : graph.edgeSet())
        {
            jsonGenerator.writeObjectFieldStart(((StoryNodeLink)edge).getID());

            jsonGenerator.writeFieldName("from");
            jsonGenerator.writeString(((Room)graph.getEdgeSource(edge)).getID());

            jsonGenerator.writeFieldName("to");
            jsonGenerator.writeString(((Room)graph.getEdgeTarget(edge)).getID());

            jsonGenerator.writeFieldName("data");
            jsonGenerator.writeObject(((StoryNodeLink)edge).getAction());

            jsonGenerator.writeEndObject();

        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();

    }
}