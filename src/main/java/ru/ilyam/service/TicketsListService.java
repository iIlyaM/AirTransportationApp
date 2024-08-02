package ru.ilyam.service;

import ru.ilyam.entity.Ticket;
import ru.ilyam.entity.TicketsFlightTimeReport;
import ru.ilyam.utils.CalcUtils;

import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TicketsListService implements TicketService {
    @Override
    public Map<String, String> getMinFlightTimeReport(List<Ticket> tickets, String origin, String destination) {
        if(tickets.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Список рейсов, а также значения отправления" +
                    " и прибытия не должны быть пустыми!");
        }
        Map<String, Long> companiesMinFlightTime = new HashMap<>();
        for (var ticket : filterTicketsByRoute(tickets, origin, destination)) {
            var flightTime = calcFlightTime(ticket);
            companiesMinFlightTime.compute(ticket.getCarrier(), (key, value) ->
                    value == null || flightTime < value ? flightTime : value);
        }
        return formatTimeReport(companiesMinFlightTime);
    }

    @Override
    public Double calcMeanMedianDifference(List<Ticket> tickets, String origin, String destination) {
        if(tickets.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Список рейсов, а также значения отправления" +
                    " и прибытия не должны быть пустыми!");
        }
        List<Integer> prices = new ArrayList<>();
        for (var ticket: filterTicketsByRoute(tickets, origin, destination)) {
            prices.add(ticket.getPrice());
        }
        return CalcUtils.calcAvgPrice(prices) - CalcUtils.calcPriceMean(prices);
    }

    @Override
    public void printReportsResults(TicketsFlightTimeReport ticketsFlightTimeReport, Double meanMedianDiff) {
        System.out.println(ticketsFlightTimeReport.toString());
        System.out.println("Разница между средним значением и медианой: " + meanMedianDiff);
    }

    private Map<String, String> formatTimeReport(Map<String, Long> report) {
        Map<String, String> formattedReport = new HashMap<>();
        for (var entry: report.entrySet()) {
            long hours   = entry.getValue() / 60;
            long minutes = entry.getValue() % 60;
            formattedReport.put(entry.getKey(), String.format("%d:%02d", hours, minutes));
        }
        return formattedReport;
    }


    private List<Ticket> filterTicketsByRoute(List<Ticket> tickets, String origin, String destination) {
        Predicate<Ticket> destinationPredicate = ticket -> destination.equals(ticket.getDestination());
        Predicate<Ticket> originPredicate = ticket -> origin.equals(ticket.getOrigin());
        return tickets.stream()
                .filter(originPredicate.and(destinationPredicate))
                .collect(Collectors.toList());
    }

    private Long calcFlightTime(Ticket ticket) {
        return Duration.between(ticket.getDepartureDateTime(),
                ticket.getArrivalDateTime()).toMinutes();
    }
}
