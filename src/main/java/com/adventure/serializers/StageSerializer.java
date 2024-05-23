package com.adventure.serializers;

import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javafx.stage.Stage;


import java.io.IOException;


public class StageSerializer extends StdSerializer<Stage>
{
    public StageSerializer()
    {
        this(null);
    }
    public StageSerializer(Class<Stage> t)
    {
        super(t);
    }

    @Override
    public void serialize(Stage Stage, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeEndObject();
    }
}
