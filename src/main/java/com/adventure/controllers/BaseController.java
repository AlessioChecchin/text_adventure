package com.adventure.controllers;

/**
 * Interface for basic application controller.
 */
public interface BaseController
{
    /**
     * Method used to tear down the controller when the controller is being unloaded.
     */
    void shutdown();
}
