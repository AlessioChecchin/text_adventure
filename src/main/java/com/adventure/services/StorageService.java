package com.adventure.services;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;

import java.util.List;

/**
 * Interfaces for a storage service.
 */
public interface StorageService
{
    /**
     * Lists available games.
     * @return A list of unique string identifiers of available games.
     */
    List<String> listGames();

    /**
     * Saves a game.
     * @param game Game to save.
     * @throws GameStorageException If an error occurs while saving the game.
     */
    void saveGame(Game game) throws GameStorageException;

    /**
     * Loads a game.
     * @param gameId Unique identifier of the game to load.
     * @return An instance of the loaded game.
     * @throws GameStorageException If an error occurs while loading the provided game.
     */
    Game loadGame(String gameId) throws GameStorageException;

    /**
     * Deletes a game.
     * @param gameId Unique identifier of the game to delete.
     */
    void deleteGame(String gameId);

    /**
     * Creates a new game.
     * @param playerName The name of the player for the new game.
     * @return An instance of the new game.
     */
    Game newGame(String playerName);
}
