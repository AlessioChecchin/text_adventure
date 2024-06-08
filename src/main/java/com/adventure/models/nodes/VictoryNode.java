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
    /**
     * Default constructor.
     */
    public VictoryNode()
    {
        super("views/victory.fxml");
        super.setID();
    }

    /**
     * Constructor with id.
     * @param ID Node unique id.
     */
    public VictoryNode(@JsonProperty("numericID") int ID)
    {
        super("views/victory.fxml");
        super.setID(ID);
    }
}

