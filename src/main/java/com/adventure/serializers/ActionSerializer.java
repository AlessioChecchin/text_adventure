package com.adventure.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.adventure.models.nodes.Action;

import java.io.IOException;

/**
 * Serializes the Action class.
 */
public class ActionSerializer extends StdSerializer<Action>
{
    public ActionSerializer()
    {
        this(null);
    }
    public ActionSerializer(Class<Action> t)
    {
        super(t);
    }

    @Override
    public void serialize(Action action, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", action.getActionName());
        jsonGenerator.writeEndObject();
    }

}
