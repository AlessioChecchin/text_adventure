package com.adventure.models.nodes;

import com.adventure.deserializers.VictoryNodeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Node that corresponds to the victory state.
 */
@JsonDeserialize(using = VictoryNodeDeserializer.class)
public class VictoryNode extends StoryNode
{
    public VictoryNode()
    {
        super("views/victory.fxml");
        super.setID();
    }

    public VictoryNode(@JsonProperty("numericID") int ID)
    {
        super("views/victory.fxml");
        super.setID(ID);
    }
}

