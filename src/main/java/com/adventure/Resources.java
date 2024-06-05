package com.adventure;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;

/**
 * Class used to gather useful resources.
 */
public class Resources
{
    public static BackgroundImage getBackground(String source)
    {
        URL imageUrl = Resources.class.getResource(source);

        if (imageUrl != null)
        {
            return new BackgroundImage(
                    new Image(imageUrl.toString()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    // Set to cover
                    new BackgroundSize(0,0, false, false, false, true)
            );
        }

        return null;
    }

    // TODO : può lanciare NullPointerException
    public static String getAssetsPath()
    {
        return Resources.class.getResource("assets").getPath();
    }

}
