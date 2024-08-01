package ru.ilyam.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ilyam.serializers.TicketDeserializer;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = TicketDeserializer.class)
public class TicketDto {
    private String origin;
    private String originName;
    private String destination;
    private String destinationName;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String carrier;
    private Integer stops;
    private Integer price;
}
