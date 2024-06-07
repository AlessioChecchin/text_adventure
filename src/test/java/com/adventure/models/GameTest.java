package com.adventure.models;

import com.adventure.config.ApplicationConfig;
import com.adventure.config.ApplicationContextProvider;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.nodes.Action;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {



    static Room room1;
    static Room room2;
    static Game game;
    static Graph<StoryNode, StoryNodeLink> testGraph;
    TestInterface stage;
    @BeforeAll
    static void setUp() throws ConfigurationException {
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        applicationContextProvider.getConfig();
        game = new Game(applicationContextProvider.getConfig());
        testGraph = new DirectedPseudograph<>(StoryNodeLink.class);
        room1 = new Room("Test", "Test");
        room2 = new Room("Test", "Test");
    }
    @Test
    void currentNodeTest(){
        //exercise
        game.setCurrentNode(room1);
        //test get and set current node
        assertEquals(room1, game.getCurrentNode(), "Problems with set and get current node");
    }

    @Test
    void previousNodeTest(){

        //exercise
        game.setCurrentNode(room2);

        //test2 hasPreviousNode
        assertTrue(game.hasPreviousNode(), "Previous node not recognised");

        //exercise
        game.invalidatePreviousNode();

        //test invalidatePreviousNode
        assertNull(game.getPreviousNode(), "Problems with InvalidatePreviousNode method");
        assertFalse(game.hasPreviousNode(), "Problems with InvalidatePreviousNode method");

        //retesting previousNode
        game.setCurrentNode(room2);
        assertTrue(game.hasPreviousNode(), "Problems with InvalidatePreviousNode method");
    }

    @Test
    void testGameGraph(){
        testGraph.addVertex(room1);
        testGraph.addVertex(room2);

        game.getGameGraph().addVertex(room1);
        game.getGameGraph().addVertex(room2);

        StoryNodeLink link1 = new StoryNodeLink();
        link1.setAction(new Action("Ciao"));

        StoryNodeLink link2 = new StoryNodeLink();
        link2.setAction(new Action("Ciao"));

        testGraph.addEdge(room1, room2, link1);
        game.getGameGraph().addEdge(room1, room2, link1);

        //test equals for Graphs
        assertEquals(testGraph, game.getGameGraph(), "Problems with equals method");
    }


}

//Abstract Class for Testing without the graphics
abstract class TestInterface extends Stage
{

}
