package com.adventure.services;


import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
import com.adventure.models.items.*;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.adventure.deserializers.GameDeserializer;
import com.adventure.serializers.GraphSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jgrapht.Graph;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BucketStorageService implements StorageService
{
    /**
     * Constructor
     * @param properties Application properties.
     */
    public BucketStorageService(Properties properties)
    {
        this.properties = properties;

        String key = properties.getProperty("storage.aws.key");
        String secret = properties.getProperty("storage.aws.secret");
        Region region = Region.of(properties.getProperty("storage.aws.region"));

        AwsBasicCredentials credentials = AwsBasicCredentials.create(key, secret);

        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        this.s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Override
    public List<String> listGames()
    {
        String bucketName = this.properties.getProperty("storage.aws.bucket.name");

        ListObjectsV2Request request = ListObjectsV2Request
                .builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Iterable response = this.s3.listObjectsV2Paginator(request);

        List<String> result = new ArrayList<>();

        for (ListObjectsV2Response page : response)
        {
            for (S3Object object : page.contents())
            {
                result.add(object.key());
            }
        }

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
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
            System.out.println(json);
        }
        catch (JsonProcessingException e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    @Override
    public Game loadGame(String gameId) throws GameStorageException
    {
        String json = "<load from bucket>";
        ObjectMapper mapper = new ObjectMapper();

        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Game.class, new GameDeserializer());
        mapper.registerModule(module);

        try
        {
            return mapper.readValue(json, Game.class);
        } catch (JsonProcessingException e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    @Override
    public void deleteGame(String gameId)
    {

    }

    @Override
    public Game newGame(String name) {
        Game game = new Game(this.properties);

        Inventory playerInventory = new Inventory(100);

        playerInventory.addItem(new AttackItem("Sword"));

        Stats stats = new Stats();
        stats.setMaxHp(100);
        stats.setHp(100);
        stats.setBaseAttack(1);
        stats.setBaseDefense(1);

        game.setPlayer(new Player("default", playerInventory, stats));

        String firstFightRoomKey = "Level 1";

        Room startingRoom = new Room("Introduction room", "Welcome to the first room, take the key and go on an adventure!");
        startingRoom.setBackgroundPath("assets/castle.png");
        startingRoom.getItems().add(new Key(firstFightRoomKey));

        game.setCurrentNode(startingRoom);

        Room firstFightRoom = new Room("First fight room", "Oh no, a goblin! Fight him and take the loot that drops");
        firstFightRoom.setBackgroundPath("assets/castle.png");

        // Populating the game graph.
        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();
        g.addVertex(startingRoom);
        g.addVertex(firstFightRoom);

        StoryNodeLink toFirstFightRoom = new StoryNodeLink();
        toFirstFightRoom.setLocked(true);
        toFirstFightRoom.setKey(firstFightRoomKey);

        g.addEdge(startingRoom, firstFightRoom, toFirstFightRoom);
        return game;


        /*Room room = new Room("First room", "First room description");
        room.setBackgroundPath("assets/castle.png");
        AttackItem sword = new AttackItem("Sword");
        sword.setAdder(3);
        sword.setMultiplier(1.2);
        sword.setWeight(4);
        UsableItem healthPotion = new UsableItem("Potion");
        healthPotion.setAdditionalHp(10);
        healthPotion.setWeight(3);

        // First room items.
        List<Item> items = new ArrayList<>();
        items.add(sword);
        items.add(healthPotion);
        room.setItems(items);

        // Action to reach left room or right room.
        Action actionLeftRoom = new Action("Left room");
        Action actionRightRoom = new Action("Right room");

        StoryNodeLink leftLink = new StoryNodeLink();
        leftLink.setAction(actionLeftRoom);

        StoryNodeLink rightLink = new StoryNodeLink();
        rightLink.setAction(actionRightRoom);


        Room leftRoom = new Room("Left room", "Left room description");
        UsableItem food = new UsableItem("Food");
        food.setWeight(3);
        List<Item> leftItems = new ArrayList<>();
        leftItems.add(food);
        leftRoom.setItems(leftItems);

        Room rightRoom = new Room("Right room", "Right room description");
        AttackItem bow = new AttackItem("Bow");
        bow.setAdder(3);
        bow.setMultiplier(1);
        bow.setWeight(2);
        List<Item> rightItems = new ArrayList<>();
        rightItems.add(bow);
        rightRoom.setItems(rightItems);

        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();
        g.addVertex(room);
        g.addVertex(leftRoom);
        g.addVertex(rightRoom);
        g.addEdge(room, leftRoom, leftLink);
        g.addEdge(room, rightRoom, rightLink);

        game.setCurrentNode(room);

        return game;
    }*/
    }
        /**
         * Application properties.
         */
        private final Properties properties;

        /**
         * Client instance.
         */
        private final S3Client s3;
    }

