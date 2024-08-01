package ru.ilyam.service;

import ru.ilyam.dto.TicketDto;

import java.util.List;
import java.util.Map;

public interface TicketService {
    Map<String, String> getMinFlightTimeReport(List<TicketDto> tickets, String origin, String destination);
    Double calcMeanMedianDifference(List<TicketDto> tickets, String origin, String destination);
}
