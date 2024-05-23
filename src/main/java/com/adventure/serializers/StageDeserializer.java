package com.adventure.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import javafx.stage.Stage;

import java.io.IOException;

public class StageDeserializer extends StdDeserializer<Stage>
{
    public StageDeserializer()
    {
        this(null);
    }

    public StageDeserializer(Class<?> vc)
    {
        super(vc);
    }
    @Override
    public Stage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);
        return new Stage();
    }
}
