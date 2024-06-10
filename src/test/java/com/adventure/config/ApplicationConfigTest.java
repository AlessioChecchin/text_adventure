package com.adventure.config;

import com.adventure.Resources;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.Stats;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationConfigTest
{
    Config config;

    @BeforeEach
    void newConfig() throws ConfigurationException {
        Properties props = new Properties();

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
        assertEquals(config.getPlayerStats(), new Stats(200, 500, 1, 3));
        assertEquals(config.getPlayerMaxDodges(), 5);
        assertEquals(config.getMonsterDodgeProbability(), 20);
        assertEquals(config.getMonsterAttackProbability(), 80);
    }

    @Test
    void testValidConfiguration() throws ConfigurationException, IOException {
        Properties props = new Properties();

        assertDoesNotThrow(() -> {
            InputStream fis = Resources.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(fis);
            config = new ApplicationConfig(props);
        });
    }
}
