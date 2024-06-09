package com.adventure.services;


import com.adventure.config.Config;
import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.serializers.GraphSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jgrapht.Graph;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class that implements storage service based on AWS S3 bucket.
 */
public class BucketStorageService extends AbstractStorageService
{
    /**
     * Constructor
     * @param config Application config.
     */
    public BucketStorageService(Config config)
    {
        super(config);

        Properties props = config.getProperties();

        String key = props.getProperty("storage.aws.key");
        String secret = props.getProperty("storage.aws.secret");
        Region region = Region.of(props.getProperty("storage.aws.region"));

        AwsBasicCredentials credentials = AwsBasicCredentials.create(key, secret);

        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        this.s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Override
    public List<String> listGames() throws GameStorageException
    {
        String bucketName = this.config.getProperties().getProperty("storage.aws.bucket.name");

        try
        {
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
        catch (Exception e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    @Override
    public void saveGame(Game game) throws GameStorageException
    {
        String bucketName = this.config.getProperties().getProperty("storage.aws.bucket.name");

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

            PutObjectRequest request = PutObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(game.getId())
                    .build();

            this.s3.putObject(request, RequestBody.fromString(json));
        }
        catch (Exception e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    @Override
    public Game loadGame(String gameId) throws GameStorageException
    {
        String bucketName = this.config.getProperties().getProperty("storage.aws.bucket.name");

        try
        {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(gameId)
                    .build();

            ResponseBytes<GetObjectResponse> responseResponseBytes = this.s3.getObjectAsBytes(objectRequest);

            String data = responseResponseBytes.asUtf8String();

            //  Creates game from json
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(data, Game.class);
        }
        catch (Exception e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    @Override
    public void deleteGame(String gameId) throws GameStorageException
    {
        String bucketName = this.config.getProperties().getProperty("storage.aws.bucket.name");

        try
        {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(gameId)
                    .build();

            this.s3.deleteObject(deleteObjectRequest);
        }
        catch (Exception e)
        {
            throw new GameStorageException(e.getMessage());
        }
    }

    /**
     * Client instance.
     */
    private final S3Client s3;
}



