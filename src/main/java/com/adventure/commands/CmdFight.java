package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.*;
import com.adventure.models.nodes.*;
import com.adventure.services.StorageService;
import javafx.application.Platform;

import java.util.ArrayList;


public class CmdFight extends AbstractCommand {

    private Command command;

    @Override
    public void execute() throws InterruptedException
    {
        // Check the correct number of parameters for this command
        if(!this.correctArgumentsNumber(0)) { return; }

        // Set player and monster.
        Game game = this.context.getGame();
        Player player = game.getPlayer();
        StoryNode node = this.context.getGame().getCurrentNode();

        if((node instanceof Room currentRoom) && (currentRoom.getMonster() != null))
        {
            Enemy monster = currentRoom.getMonster();

            // Check if the monster is alive.
            if (monster.getAlive())
            {
                // Enabling commands during the fight.
                CommandParser commandParser = CommandParser.getInstance();
                commandParser.disableAll();

                commandParser.enable("attack");
                commandParser.enable("dodge");
                commandParser.enable("use");
                commandParser.enable("help");
                commandParser.enable("clear");
                commandParser.enable("show");

                // Introduction fight.
                this.writer.println(monster.getDefaultDialog());

                player.resetDodge();
                monster.resetDodge();

                this.writer.println("");
                this.writer.println("The battle against " + monster.getName() + " is starting...");

                // Iterate battle.
                while (player.getAlive() && monster.getAlive())
                {
                    // Player have to digit a command.
                    this.writer.println("What do you want to do?");

                    String s = this.safeReadNextLine();

                    this.writer.flush();
                    this.command = commandParser.parseCommand(s);

                    if(command != null)
                    {
                        this.command.setInputStream(this.getInputStream());
                        this.command.setWriter(this.getWriter());
                        this.command.execute();
                    }
                    else
                    {
                        this.writer.println("Unknown command, try with attack, dodge or use");
                    }
                }

                this.writer.println("Fight finished!");

                if(player.getAlive())
                {
                    // TODO: loot drop.

                    String winOutput = "You won!";

                    if(!monster.getInventory().getItems().isEmpty())
                    {
                        winOutput += " Take the monster's loot!";
                    }

                    this.writer.println(winOutput);

                    // Enable all other commands and disable command after finish the fight
                    commandParser.enableAll();
                    commandParser.disable("attack");
                    commandParser.disable("dodge");
                }
                else
                {
                    this.writer.println("You lost :(");
                    this.writer.println("Press ENTER to start new game...");

                    this.safeReadNextLine();
                    commandParser.disableAll();

                    this.resetGame();
                }


            }
            else
            {
                this.writer.println("You have already kill the monster in this room.");
            }
        }
        else
        {
            this.writer.println("No monster to fight!");
        }

    }


    public void kill()
    {
        super.kill();

        if(this.command != null)
        {
            command.kill();
        }
    }

    // Enum for choosing what a monster has to do in a random way
    public enum Move
    {
        ATTACK,
        DODGE
    }

    private void resetGame()
    {
        StorageService storageService = this.context.getConfig().getStorageService();

        Game currentGame = this.context.getGame();
        String currentGameName = currentGame.getId();

        Game newGame = storageService.newGame(currentGame.getPlayer().getName());
        this.context.setGame(newGame);

        // It means that the user had previously saved the game, and it must be overwritten.
        if(currentGameName != null)
        {
            try
            {
                storageService.saveGame(newGame);
            }
            catch (GameStorageException e)
            {
                logger.error("Error saving game", e);
                this.writer.println("Error saving game");
            }
        }

        Platform.runLater(() -> {
            logger.debug("Loading new game...");
            newGame.load();
        });


    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}

