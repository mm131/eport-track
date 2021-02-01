package com.njeport.track.common.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class IntValueEnumSerializer extends JsonSerializer<IntValueEnum> {

    @Override
    public void serialize(IntValueEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.intValue());
    }

}