package com.adventure.config;

import com.adventure.exceptions.ConfigurationException;
import com.adventure.services.BucketStorageService;
import com.adventure.services.FileSystemStorageService;
import com.adventure.services.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

            // Default storage provider is filesystem.
            String storageProvider = properties.getProperty("storage.provider", "filesystem");

            if(storageProvider.equals(SupportedStorage.AWS.name().toLowerCase()))
            {
                this.storageService = new BucketStorageService(this);
            }
            else
            {
                // Fallback storage service.
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
        return 85;
    }

    @Override
    public double getMonsterDodgeProbability()
    {
        return 30;
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

    private static final Logger logger = LogManager.getLogger();
}
