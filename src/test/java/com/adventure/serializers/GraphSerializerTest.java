package com.adventure.serializers;

import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphSerializerTest {

    static Graph<StoryNode, StoryNodeLink> graph;
    static Graph<StoryNode, StoryNodeLink> graph2;
    static GraphSerializer graphSerializer;
    static Room test;
    static Room test1;
    static StoryNodeLink link;

    @BeforeAll
    static void setUp() throws IOException {
        graph = new DirectedPseudograph<>(StoryNodeLink.class);
        graph2 = new DirectedPseudograph<>(StoryNodeLink.class);
        graphSerializer = new GraphSerializer();
        test = new Room("Test", "Test");
        test1 = new Room("Test", "Test");
        StoryNodeLink link = new StoryNodeLink();
        graph.addVertex(test);
        graph.addVertex(test1);
        graph.addEdge(test, test1, link);
        graph2.addVertex(test);
        graph2.addVertex(test1);
        graph2.addEdge(test, test1, link);

    }

    @Test
    public void testGraphSerialization() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Graph.class, graphSerializer);

        String expectedJson = mapper.writeValueAsString(graph2);
        String actualJson = mapper.writeValueAsString(graph);

        assertEquals(expectedJson, actualJson);
    }
}