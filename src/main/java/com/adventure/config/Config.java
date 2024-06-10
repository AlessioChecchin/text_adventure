package com.adventure.config;

import com.adventure.services.StorageService;

import java.util.Properties;

/**
 * Configuration object.
 */
public interface Config
{

    /**
     * Default setting folder
     * @return The path (relative to the .jar) where saves and setting are contained
     */
    String getConfigFolder();

    /**
     * Default display width.
     * @return Initial display width.
     */
    int getDisplayWidth();

    /**
     * Default display height.
     * @return Initial display height.
     */
    int getDisplayHeight();

    /**
     * Tells if the window is resizable or not.
     * @return True if the window is resizable, false otherwise.
     */
    boolean isResizable();

    /**
     * Gets type of storage service.
     * @return Type of the current storage service.
     */
    SupportedStorage getStorageServiceType();

    /**
     * Gets storage service.
     * @return Current storage service.
     */
    StorageService getStorageService();

    /**
     * Gets the probability that a monster attacks during a fight.
     * @return Monster attack probability.
     */
    double getMonsterAttackProbability();

    /**
     * Gets the probability that a monster dodges during a fight.
     * @return Monster dodges during a fight.
     */
    double getMonsterDodgeProbability();

    /**
     * App title getter;
     * @return Default app title.
     */
    String getAppTitle();

    /**
     * Properties getter.
     * @return Current properties.
     */
    Properties getProperties();
}
