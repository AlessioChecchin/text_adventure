package com.adventure;

import com.adventure.interfaces.ApplicationContext;
import com.adventure.nodes.StoryNodeLink;
import com.adventure.nodes.StoryNode;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ApplicationContextProvider implements ApplicationContext
{
    /**
     * Private singleton constructor.
     */
    private ApplicationContextProvider()
    {
        this.g = new DefaultDirectedGraph<>(StoryNodeLink.class);
        this.lookupNodes = new HashMap<>();
    }

    /**
     * Singleton getter.
     * @return Singleton instance.
     */
    public static ApplicationContextProvider getInstance()
    {
        if(instance == null) { instance = new ApplicationContextProvider(); }
        return instance;
    }

    /**
     * Loads application state.
     * @param stateNode State of the application.
     * @throws NoSuchMethodException Reflection exception.
     * @throws InvocationTargetException Reflection exception.
     * @throws InstantiationException Reflection exception.
     * @throws IllegalAccessException Reflection exception.
     */
    public ApplicationContext load(Class<? extends StoryNode> stateNode) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        StoryNode node = this.lookupNodes.get(stateNode);

        if(node == null)
        {
            node = stateNode.getDeclaredConstructor().newInstance();
        }

        // We loaded the node, but now we also need to load all the adjacent nodes.

        this.currentNode = node;

        return this;
    }


    @Override
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    public void activate() throws Exception {
        if(this.currentNode != null)
        {
            this.currentNode.run(this);
        }
    }


    /**
     * Context singleton.
     */
    private static ApplicationContextProvider instance;

    /**
     * This represents a decision graph that is incrementally loaded as the application flow proceeds.
     */
    private Graph<StoryNode, StoryNodeLink> g;

    /**
     * This table contains all the state nodes that are already loaded.
     * This is done to maintain the state of the nodes even if the node is not active.
     * This also allows to save the state of the whole application and not only the single active node.
     */
    private final HashMap<Class<? extends StoryNode>, StoryNode> lookupNodes;

    /**
     * Currently active node.
     */
    private StoryNode currentNode;

    private Stage stage;
}
