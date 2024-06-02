package com.adventure.services;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StorageService
{
    public List<String> listGames();
    public void saveGame(Game game) throws GameStorageException;
    public Game loadGame(String gameId) throws GameStorageException;
    public void deleteGame(String gameId);
    public Game newGame(String playerName);
}
