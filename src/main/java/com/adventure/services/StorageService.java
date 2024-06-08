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
     * @throws GameStorageException If error occur while listing the games.
     */
    List<String> listGames() throws GameStorageException;

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
     * @throws GameStorageException If some error occur while deleting the specified game.
     */
    void deleteGame(String gameId) throws GameStorageException;

    /**
     * Creates a new game.
     * @param playerName The name of the player for the new game.
     * @return An instance of the new game.
     */
    Game newGame(String playerName);
}
