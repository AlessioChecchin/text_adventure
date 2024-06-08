package com.adventure.deserializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializingUtility {
    /**
     * Given the class name the JsonNode it uses the default deserializer to deserialize the JsonNode using the given class
     * @param objectNode JsonNode to use
     * @param tClass Class type to use
     * @return A deserialized object of type tClass
     * @param <T> the type to use
     */
    public static <T> T fromNodeToObject(JsonNode objectNode, Class<T> tClass) throws JsonProcessingException
    {
        return new ObjectMapper().readValue(objectNode.toString(), tClass);
    }
}
