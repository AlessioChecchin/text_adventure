package com.adventure;

import com.adventure.models.Game;
import com.adventure.nodes.Action;
import com.adventure.nodes.Room;
import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedPseudograph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {



    Room room1;
    Room room2;
    Game game;
    Graph<StoryNode, StoryNodeLink> testGraph;
    TestInterface stage;
    @BeforeEach
    void setUp() {
        Properties init = new Properties();
        game = new Game(init, stage);
        testGraph = new DirectedPseudograph<>(StoryNodeLink.class);
        room1 = new Room("Test", "Test");
        room2 = new Room("Test", "Test");
    }
    @Test
    void currentNodeTest(){
        //exercise
        game.setCurrentNode(room1);
        Room room3 = new Room("test", "test");

        //test get and set current node
        assertEquals(room2, game.getCurrentNode(), "Problems with set and get current node");
        assertNotEquals(room3, game.getCurrentNode(), "Problems with set and get current node");
    }

    @Test
    void previousNodeTest(){
        //exercise
        game.setCurrentNode(room1);

        //test hasPreviousNode
        assertFalse(game.hasPreviousNode(), "Problems with previous node");

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

        Room room3 = new Room("Test","Test");
        Room room4 = new Room("Test","Test");

        assertEquals(room3, room4, "Rooms not matching");

        game.getGameGraph().addVertex(room3);
        game.getGameGraph().addVertex(room4);

        StoryNodeLink link1 = new StoryNodeLink();
        link1.setAction(new Action("Ciao"));

        StoryNodeLink link2 = new StoryNodeLink();
        link2.setAction(new Action("Ciao"));

        //assertEquals(link1, link2, "Links not matching");

        testGraph.addEdge(room1, room2, link1);
        game.getGameGraph().addEdge(room3, room4, link2);

        Set<Room> r = new HashSet<>();
        r.add(room1);

        Set<Room> b = new HashSet<>();
        b.add(room2);

        assertEquals(testGraph.vertexSet(), game.getGameGraph().vertexSet(), "negro");

        //assertEquals(link1, link2, "Links not matching");
        //test
        //assertEquals(testGraph, game.getGameGraph(), "Problems with equals method");
    }


}

//Abstract Class for Testing without the graphics
abstract class TestInterface extends Stage
{

}
