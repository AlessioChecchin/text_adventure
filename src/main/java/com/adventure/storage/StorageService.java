package com.adventure.storage;

import com.adventure.models.Game;

import java.util.List;

public interface StorageService
{
    public List<String> listGames();
    public void saveGame(Game game);
    public Game loadGame(String gameId);
    public void deleteGame(String gameId);
    public Game newGame();
}
