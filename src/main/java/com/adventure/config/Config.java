package com.adventure.config;

import com.adventure.services.StorageService;

import java.util.Properties;

/**
 * Configuration object.
 */
public interface Config
{
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
     * Properties getter.
     * @return Current properties.
     */
    Properties getProperties();
}
