package br.com.partner.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(final LocalDate localDate,
                          final JsonGenerator json,
                          final SerializerProvider serializerProvider) throws IOException {
        json.writeString(localDate.toString());
    }

}
