package com.adventure;

import com.adventure.paths.StateLink;
import com.adventure.paths.StateNode;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DefaultApplicationContext implements ApplicationContext
{
    /**
     * Private singleton constructor.
     */
    private DefaultApplicationContext()
    {
        this.g = new DefaultDirectedGraph<>(StateLink.class);
        this.lookupNodes = new HashMap<>();
    }

    /**
     * Singleton getter.
     * @return Singleton instance.
     */
    public static DefaultApplicationContext getInstance()
    {
        if(instance == null) { instance = new DefaultApplicationContext(); }
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
    public void load(Class<? extends StateNode> stateNode) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        StateNode node = this.lookupNodes.get(stateNode);

        if(node == null)
        {
            node = stateNode.getDeclaredConstructor().newInstance();
        }

        // We loaded the node, but now we also need to load all the adjacent nodes.


        this.currentNode = node;
    }

    /**
     * Runs current node and expects a new node to be loaded.
     * @return The new node to run or if no nodes are loaded from the current node, then null is returned.
     */
    public StateNode nextNode()
    {
        StateNode app = this.currentNode;
        this.currentNode = null;
        if(app != null) app.run(this);
        return this.currentNode;
    }

    /**
     * Context singleton.
     */
    private static DefaultApplicationContext instance;

    /**
     * This represents a decision graph that is incrementally loaded as the application flow proceeds.
     */
    private Graph<StateNode, StateLink> g;

    /**
     * This table contains all the state nodes that are already loaded.
     * This is done to maintain the state of the nodes even if the node is not active.
     * This also allows to save the state of the whole application and not only the single active node.
     */
    private final HashMap<Class<? extends StateNode>, StateNode> lookupNodes;

    /**
     * Currently active node.
     */
    private StateNode currentNode;
}
