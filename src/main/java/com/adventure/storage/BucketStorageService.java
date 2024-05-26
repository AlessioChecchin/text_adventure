package com.adventure.storage;


import com.adventure.models.Game;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.nodes.Action;
import com.adventure.nodes.Room;
import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import org.jgrapht.Graph;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BucketStorageService implements StorageService
{
    /**
     * Application properties.
     */
    private final Properties properties;

    /**
     * Client instance.
     */
    private final S3Client s3;

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
    public void saveGame(Game game) {

    }

    @Override
    public Game loadGame(String gameId) {
        return null;
    }

    @Override
    public void deleteGame(String gameId) {

    }

    @Override
    public Game newGame()
    {
        Game game = new Game(this.properties);

        // First room
        Room room = new Room("First room", "First room description");
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
    }
}
