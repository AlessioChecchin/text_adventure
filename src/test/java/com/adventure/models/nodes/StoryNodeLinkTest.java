package com.adventure.models.nodes;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryNodeLinkTest {

    StoryNodeLink storyNodeLink;

    @BeforeEach
    void setUp() {
        storyNodeLink = new StoryNodeLink();
    }

    @Test
    void setTest(){
        // Exercise.
        storyNodeLink.setAction(new Action("testAction"));
        storyNodeLink.setKey("testKey");
        storyNodeLink.setLocked(true);

        // Test.
        assertEquals(new Action("testAction"), storyNodeLink.getAction(), "Problems with action equals or setAction");
        assertEquals("testKey", storyNodeLink.getKey(), "Problems with key");
        assertTrue(storyNodeLink.getLocked(), "Problems with locker of the link");
    }

    @Test
    void testEquals() {
        // Exercise.
        storyNodeLink.setAction(new Action("testAction"));
        storyNodeLink.setKey("testKey");
        storyNodeLink.setLocked(true);
        StoryNodeLink storyLinkTest = new StoryNodeLink();
        storyLinkTest.setAction(new Action("testAction"));
        storyLinkTest.setKey("testKey");
        storyLinkTest.setLocked(true);
        Room source = new Room("test", "test");
        Room target = new Room("test", "test");
        Graph<StoryNode, StoryNodeLink> graph = new DirectedPseudograph<>(StoryNodeLink.class);
        graph.addVertex(source);
        graph.addVertex(target);
        graph.addEdge(source, target, storyNodeLink);
        graph.addEdge(source, target, storyLinkTest);


        // Test.
        assertEquals(storyLinkTest, storyNodeLink, "Problems with equals of a StoryNodeLink");
    }
}