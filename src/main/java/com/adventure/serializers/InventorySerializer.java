package com.adventure.serializers;

import com.adventure.models.Inventory;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.DefenceItem;
import com.adventure.models.items.Item;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class InventorySerializer extends StdSerializer<Inventory>
{
    public InventorySerializer()
    {
        this(null);
    }
    public InventorySerializer(Class<Inventory> t)
    {
        super(t);
    }

    @Override
    public void serialize(Inventory inventory, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        //  Attack Item
        jsonGenerator.writeFieldName("equippedAttackItem");
        jsonGenerator.writeObject((AttackItem) inventory.getEquipedAttackItem());

        //  Defence Item
        jsonGenerator.writeFieldName("equippedDefenceItem");
        jsonGenerator.writeObject((DefenceItem) inventory.getEquipedDefenceItem());

        //  All items
        jsonGenerator.writeArrayFieldStart("items");
        for (Item item : inventory.getItems())
            jsonGenerator.writeObject((Item) item);
        jsonGenerator.writeEndArray();

        //  Other info
        jsonGenerator.writeFieldName("maxWeight");
        jsonGenerator.writeNumber(inventory.getMaxWeight());
        jsonGenerator.writeFieldName("currentWeight");
        jsonGenerator.writeNumber(inventory.getCurrentWeight());

        jsonGenerator.writeEndObject();
    }
}