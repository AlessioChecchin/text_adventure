package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.*;
import com.adventure.models.nodes.*;
import com.adventure.services.StorageService;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to start a fight with an enemy (if there is one).
 */
public class CmdFight extends AbstractCommand {

    private Command command;

    @Override
    public void execute() throws InterruptedException
    {
        // Check the correct number of parameters for this command
        if(!this.correctArgumentsNumber(0))
        {
            this.writer.println("Incorrect number of arguments! Usage: fight");
            return;
        }

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

                // Disables all commands and saves previous enabled commands.
                List<String> enabledCommands = commandParser.disableAll();

                commandParser.enable("attack");
                commandParser.enable("dodge");
                commandParser.enable("use");
                commandParser.enable("help");
                commandParser.enable("clear");
                commandParser.enable("show");
                commandParser.enable("equip");
                commandParser.enable("run");

                // Introduction fight.
                this.writer.println(monster.getDefaultDialog());

                player.resetDodge();
                monster.resetDodge();
                monster.heal(monster.getStats().getMaxHp());

                // Player is now fighting
                player.setFightingStatus(true);

                this.writer.println("");
                this.writer.println("The battle against " + monster.getName() + " is starting...");

                // Iterate battle.
                while (player.getAlive() && monster.getAlive() && player.isFighting())
                {
                    // Player have to digit a command.
                    this.writer.println("What do you want to do?");

                    String s = this.safeReadNextLine();

                    // Clears the screen.
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

                // Restores previous enabled commands.
                commandParser.disableAll();
                commandParser.enableAll(enabledCommands);

                if(player.getAlive() && player.isFighting())
                {
                    this.writer.println("Fight finished!");
                    String winOutput = "You won!";
                    player.setFightingStatus(false);
                    if(!monster.getInventory().getItems().isEmpty())
                    {
                        monster.drop(currentRoom);
                        winOutput += " Take the monster's loot!";
                    }

                    this.writer.println(winOutput);

                }
                else if(!player.getAlive())
                {
                    this.writer.println("Fight finished!");
                    this.writer.println("You lost!");
                    this.writer.println("Press ENTER to start new game...");

                    commandParser.disableAll();
                    this.safeReadNextLine();

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

        Platform.runLater(() -> {
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
            logger.debug("Loading new game...");
            newGame.setStage(currentGame.getStage());
            newGame.load();
        });
    }


    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}

