package com.adventure.commands;

public class CmdRun extends CmdFight{
    @Override
    public void execute() throws InterruptedException {
        this.context.getGame().getPlayer().setFightingStatus(false);
        this.writer.println("You run away from the battle");
    }
}
