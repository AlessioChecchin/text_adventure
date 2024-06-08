package com.adventure.serializers;

import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.jgrapht.Graph;

import com.adventure.models.nodes.Room;

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
            jsonGenerator.writeArrayFieldStart(((StoryNode)vertex).getID());
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
            jsonGenerator.writeFieldName(((StoryNode)vertex).getID());
            jsonGenerator.writeObject(((StoryNode) vertex));
        }
        jsonGenerator.writeEndObject();

        //  Create array of edges
        jsonGenerator.writeObjectFieldStart("edges");
        for(Object edge : graph.edgeSet())
        {
            jsonGenerator.writeObjectFieldStart(((StoryNodeLink)edge).getID());

            jsonGenerator.writeFieldName("from");
            jsonGenerator.writeString(((StoryNode)graph.getEdgeSource(edge)).getID());

            jsonGenerator.writeFieldName("to");
            jsonGenerator.writeString(((StoryNode)graph.getEdgeTarget(edge)).getID());

            jsonGenerator.writeFieldName("action");
            jsonGenerator.writeObject(((StoryNodeLink)edge).getAction());

            jsonGenerator.writeFieldName("locked");
            jsonGenerator.writeBoolean(((StoryNodeLink)edge).getLocked());

            jsonGenerator.writeFieldName("key");
            jsonGenerator.writeString(((StoryNodeLink)edge).getKey());

            jsonGenerator.writeFieldName("ID");
            jsonGenerator.writeString(((StoryNodeLink)edge).getID());

            jsonGenerator.writeFieldName("numericID");
            jsonGenerator.writeNumber(((StoryNodeLink)edge).getNumericID());

            jsonGenerator.writeEndObject();

        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();

    }
}