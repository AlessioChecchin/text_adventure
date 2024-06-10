package com.adventure.config;

import com.adventure.exceptions.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationConfigTest
{
    static Config config;

    @BeforeEach
    void newConfig() throws ConfigurationException {
        Properties props = new Properties();
        /**
         * app.title=Text adventure
         *
         * display.width=850
         * display.height=750
         * display.resizable=false
         *
         * config.folder=text_adventure/
         *
         * game.player.default.hp=100
         * game.player.default.max.hp=100
         * game.player.default.base.attack=1
         * game.player.default.base.defence=1
         * game.entity.default.max.dodges=3
         *
         * game.monster.default.dodgeProbability=30
         * game.monster.default.attackProbability=70
         */

        props.setProperty("app.title", "Test app title");
        props.setProperty("display.width", "500");
        props.setProperty("display.height", "800");
        props.setProperty("display.resizable", "false");
        props.setProperty("config.folder", "text_adventure/");
        props.setProperty("game.player.default.hp", "200");
        props.setProperty("game.player.default.max.hp", "500");
        props.setProperty("game.player.default.base.attack", "1");
        props.setProperty("game.player.default.base.defence", "3");
        props.setProperty("game.entity.default.max.dodges", "5");
        props.setProperty("game.monster.default.dodgeProbability", "20");
        props.setProperty("game.monster.default.attackProbability", "80");

        config = new ApplicationConfig(props);
    }

    @Test
    void testCorrectPopulation()
    {
        assertEquals(config.getAppTitle(), "Test app title");
        assertEquals(config.getDisplayWidth(), 500);
        assertEquals(config.getDisplayHeight(), 800);
        assertFalse(config.isResizable());
        assertEquals(config.getConfigFolder(), "text_adventure/");
        //assertEquals(config.get);

    }
}
