package com.adventure.utils;

import com.adventure.Main;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.models.Game;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.nodes.Action;
import com.adventure.nodes.Room;
import com.adventure.nodes.StoryNodeLink;
import com.adventure.nodes.StoryNode;
import com.adventure.serializers.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.stage.Stage;
import org.jgrapht.Graph;

import com.adventure.storage.BucketStorageService;
import com.adventure.storage.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApplicationContextProvider implements ApplicationContext
{

    /**
     * Private singleton constructor.
     */
    private ApplicationContextProvider()
    {
        this.properties = new Properties();
        try (InputStream fis = Main.class.getClassLoader().getResourceAsStream("application.conf"))
        {
            this.properties.load(fis);
        }
        catch (IOException ignored)
        {
            ignored.printStackTrace();
        }

        this.storageService = new BucketStorageService(this.properties);
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

    public void setGame(Game game)
    {
        Objects.requireNonNull(game, "game cannot be null");
        this.game = game;
    }

    @Override
    public Game getGame()
    {
        return this.game;
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    public StorageService getStorageService()
    {
        return this.storageService;
    }

    //
    //  SERIALIZER and DESERIALIZER
    //

    /**
     * Serializer
     */
    public void save()
    {
        ObjectMapper mapper = new ObjectMapper();

        //   Make all member fields serializable without further annotations, instead of just public fields (default setting)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //  Set custom serializers
        SimpleModule module = new SimpleModule();
        module.addSerializer(Graph.class, new GraphSerializer());
        mapper.registerModule(module);
        try {
            json.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.game));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(json.toString());
    }

    /**
     * Deserializer
     */
    public void loadSavedGame()
    {

        String test = "HELLO";
        ObjectMapper mapper = new ObjectMapper();

        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Graph.class, new GraphDeserializer());
        module.addDeserializer(Game.class, new GameDeserializer());
        mapper.registerModule(module);

        try
        {
            Game newGame = mapper.readValue(json.toString(), Game.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    //
    //  VARIABLES
    //

    /**
     * Context singleton.
     */
    private static ApplicationContextProvider instance;
    /**
     * Application properties.
     */
    private final Properties properties;
    /**
     * Current game instance.
     */
    private Game game;
    /**
     * Final json
     */
    private StringBuilder json = new StringBuilder();
    /**
     * Game current stage (passed from ApplicationContextProvider)
     */
    private Stage stage;

    /**
     * Storage service.
     */
    private StorageService storageService;
}
