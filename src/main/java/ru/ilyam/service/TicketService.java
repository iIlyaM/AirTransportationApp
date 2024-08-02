package ru.ilyam.service;

import ru.ilyam.entity.Ticket;
import ru.ilyam.entity.TicketsFlightTimeReport;

import java.util.List;
import java.util.Map;

public interface TicketService {
    Map<String, String> getMinFlightTimeReport(List<Ticket> tickets, String origin, String destination);
    Double calcMeanMedianDifference(List<Ticket> tickets, String origin, String destination);

    void printReportsResults(TicketsFlightTimeReport ticketsFlightTimeReport, Double meanMedianDiff);
}
