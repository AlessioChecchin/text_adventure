package com.adventure;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;

/**
 * Class used to gather useful resources.
 */
public class Resources
{
    /**
     * Returns background image.
     * @param source Source of the background image.
     * @return Background image.
     */
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

    /**
     * Returns asset path is available
     * @return Asset path or null if the path is not found.
     */
    public static String getAssetsPath()
    {
        URL url =  Resources.class.getResource("assets");

        if(url != null)
        {
            return url.getPath();
        }

        return null;

    }

}
