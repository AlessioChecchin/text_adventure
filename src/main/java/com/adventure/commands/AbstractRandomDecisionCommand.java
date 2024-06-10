package com.adventure.commands;

import com.adventure.config.Config;
import com.adventure.models.RandomCollection;

import java.util.Objects;

public abstract class AbstractRandomDecisionCommand extends AbstractCommand
{
    /**
     * Default constructor.
     */
    public AbstractRandomDecisionCommand()
    {
        super();
        this.decision = new RandomCollection<>();
        Config currentConfig = this.context.getConfig();

        // Monster set moves
        this.decision
                .add(currentConfig.getMonsterAttackProbability(), CmdFight.Move.ATTACK)
                .add(currentConfig.getMonsterDodgeProbability(), CmdFight.Move.DODGE);
    }

    /**
     * Constructor with random collection injection.
     * @param randomCollection Random collection object.
     */
    public AbstractRandomDecisionCommand(RandomCollection<Object> randomCollection)
    {
        super();

        Objects.requireNonNull(randomCollection);
        this.decision = randomCollection;
    }

    protected final RandomCollection<Object> decision;
}
