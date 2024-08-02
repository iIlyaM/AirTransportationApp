package ru.ilyam.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.ilyam.entity.Ticket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketDeserializer extends JsonDeserializer<Ticket> {
    final String DATE_TIME_PATTERN = "dd.MM.yy HH:mm";

    @Override
    public Ticket deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
    ) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String origin = node.get("origin").asText();
        String originName = node.get("origin_name").asText();
        String destination = node.get("destination").asText();
        String destinationName = node.get("destination_name").asText();
        LocalDateTime departureDateTime = getFileLocalDateTime(node, "departure_date", "departure_time");
        LocalDateTime arrivalDateTime = getFileLocalDateTime(node, "arrival_date", "arrival_time");
        String carrier = node.get("carrier").asText();
        int stops = (Integer) node.get("stops").numberValue();
        int price = (Integer) node.get("price").numberValue();
        return new Ticket(
                origin,
                originName,
                destination,
                destinationName,
                departureDateTime,
                arrivalDateTime,
                carrier,
                stops,
                price
        );
    }

    private LocalDateTime getFileLocalDateTime(JsonNode node, String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String dateTime = node.get(date).asText() + " " + normalizeTime(node.get(time).asText());
        return LocalDateTime.parse(dateTime, formatter);
    }

    private String normalizeTime(String time) {
        return time.replaceFirst("^(\\d):(\\d{2})$", "0$1:$2");
    }
}
