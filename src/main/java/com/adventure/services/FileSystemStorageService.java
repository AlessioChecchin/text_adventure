package com.adventure.services;

import com.adventure.Resources;
import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
import com.adventure.models.items.*;
import com.adventure.models.nodes.Action;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.adventure.serializers.GraphSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class FileSystemStorageService extends AbstractStorageService
{
    public FileSystemStorageService(Properties properties)
    {
        super(properties);

        //  Create or ensure the existence of the 'saves' folder
        ensureSaveFolder();
        this.savePath = Resources.getAssetsPath() + "saves/";
    }

    @Override
    public List<String> listGames()
    {
        ArrayList<String> result = new ArrayList<>();
        File directory = new File(this.savePath);
        for (File file : directory.listFiles())
            result.add(file.getName().replace(".json", ""));

        return result;
    }

    @Override
    public void saveGame(Game game) throws GameStorageException
    {
        ObjectMapper mapper = new ObjectMapper();

        // Make all member fields serializable without further annotations, instead of just public fields (default setting)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Set custom serializers
        SimpleModule module = new SimpleModule();
        module.addSerializer(Graph.class, new GraphSerializer());
        mapper.registerModule(module);

        try
        {
            //  Create the json string
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);

            //  Get json file path
            String saveName = game.getId() + ".json";

            //  Create json file and write the json string into it
            File file = new File(this.savePath + saveName);
            if(file.createNewFile())
                logger.debug("Saved {}", saveName);
            else
                logger.debug("{} overwritten", saveName);
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        }
        catch (JsonProcessingException e)
        {
            throw new GameStorageException(e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Game loadGame(String gameId) throws GameStorageException
    {
        File json = new File(this.savePath + gameId + ".json");

        if(!json.exists())
            throw new GameStorageException("Game with id " + gameId + " does not exist");

        //  Creates game from json
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.readValue(json, Game.class);
        }
        catch (IOException e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    @Override
    public void deleteGame(String gameId)
    {
        File game = new File(Resources.getAssetsPath() + "saves/" + gameId + ".json");

        if(game.delete())
            logger.debug("Deleted {}", game.getName().replace(".json", ""));
        else
            throw new NoSuchElementException("No such game");
    }

    /**
     * Checks whether the 'saves' folder exist.
     * If not it creates it inside
     *      target > classes > com > adventure > assets
     */
    private void ensureSaveFolder()
    {
        String savePath = Resources.getAssetsPath() + "saves";
        if(!new File(savePath).exists())
            new File(savePath).mkdirs();
    }

    /**
     * Save folder path
     */
    private final String savePath;
}
