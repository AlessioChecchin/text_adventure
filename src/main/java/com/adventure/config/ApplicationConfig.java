package com.adventure.config;

import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.Stats;
import com.adventure.services.BucketStorageService;
import com.adventure.services.FileSystemStorageService;
import com.adventure.services.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that implements configuration object.
 */
public class ApplicationConfig implements Config
{
    /**
     * Creates configuration object from a properties.
     * @param properties Application properties loaded.
     * @throws ConfigurationException If some error occur while generating the configuration object.
     */
    public ApplicationConfig(Properties properties) throws ConfigurationException
    {
        try
        {
            this.properties = properties;
            this.displayWidth = Integer.parseInt(properties.getProperty("display.width"));
            this.displayHeight = Integer.parseInt(properties.getProperty("display.height"));
            this.resizable = Boolean.parseBoolean(properties.getProperty("resizable"));
            this.configFolder = properties.getProperty("config.folder");
            this.appTitle = properties.getProperty("app.title");
            this.monsterAttackProbability = Double.parseDouble(properties.getProperty("game.monster.default.attackProbability"));
            this.monsterDodgeProbability = Double.parseDouble(properties.getProperty("game.monster.default.dodgeProbability"));

            this.defaultPlayerStats = new Stats(
                    Integer.parseInt(properties.getProperty("game.player.default.hp")),
                    Integer.parseInt(properties.getProperty("game.player.default.max.hp")),
                    Integer.parseInt(properties.getProperty("game.player.default.base.attack")),
                    Integer.parseInt(properties.getProperty("game.player.default.base.defence"))
            );

            this.playerMaxDodges = Integer.parseInt(properties.getProperty("game.entity.default.max.dodges"));

            // Ensure existence of the config folder
            if(!new File(configFolder).exists())
                new File(configFolder).mkdirs();

            // Default storage provider is filesystem.
            String storageProvider = "filesystem";

            // Try to obtain keys from key.conf
            try  (InputStream keys = new FileInputStream(new File(properties.getProperty("config.folder") + "key.conf")))
            {
                Properties keyProps = new Properties();
                keyProps.load(keys);
                properties.setProperty("storage.aws.key",keyProps.getProperty("storage.aws.key"));
                properties.setProperty("storage.aws.secret",keyProps.getProperty("storage.aws.secret"));
                properties.setProperty("storage.aws.bucket.name",keyProps.getProperty("storage.aws.bucket.name"));
                properties.setProperty("storage.aws.region",keyProps.getProperty("storage.aws.region"));
                storageProvider = "aws";
            } catch (Exception ex) { /*No aws key file, just local game*/ }

            // If key.conf exists and has keys, BucketStorage is used
            if(storageProvider.equals(SupportedStorage.AWS.name().toLowerCase()))
            {
                this.storageService = new BucketStorageService(this);
            }
            else
            {
                // Fallback to FileSystemStorage
                logger.debug("Preferred storage provider set to filesystem...");
                this.storageService = new FileSystemStorageService(this);
            }
        }
        catch (Exception e)
        {
            throw new ConfigurationException(e.getMessage());
        }
    }

    @Override
    public int getDisplayWidth()
    {
        return this.displayWidth;
    }

    @Override
    public String getConfigFolder()
    {
        return this.configFolder;
    }

    @Override
    public int getDisplayHeight()
    {
        return this.displayHeight;
    }

    @Override
    public boolean isResizable()
    {
        return this.resizable;
    }

    @Override
    public SupportedStorage getStorageServiceType()
    {
        return this.storageType;
    }

    @Override
    public StorageService getStorageService()
    {
        return this.storageService;
    }

    @Override
    public double getMonsterAttackProbability()
    {
        return this.monsterAttackProbability;
    }

    @Override
    public double getMonsterDodgeProbability()
    {
        return this.monsterDodgeProbability;
    }

    @Override
    public String getAppTitle()
    {
        return this.appTitle;
    }

    @Override
    public Stats getPlayerStats()
    {
        return new Stats(
                this.defaultPlayerStats.getHp(),
                this.defaultPlayerStats.getMaxHp(),
                this.defaultPlayerStats.getBaseAttack(),
                this.defaultPlayerStats.getBaseDefense()
        );
    }

    @Override
    public int getPlayerMaxDodges()
    {
        return this.playerMaxDodges;
    }

    @Override
    public Properties getProperties()
    {
        return this.properties;
    }

    private final int displayWidth;

    private final int displayHeight;

    private final boolean resizable;

    private SupportedStorage storageType;

    private final StorageService storageService;

    private final Properties properties;

    private final String configFolder;

    private final String appTitle;

    private final double monsterAttackProbability;

    private final Stats defaultPlayerStats;

    private final int playerMaxDodges;

    private final double monsterDodgeProbability;

    private static final Logger logger = LogManager.getLogger();
}
