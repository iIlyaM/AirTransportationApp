package ru.ilyam.service;

import ru.ilyam.dto.TicketDto;
import ru.ilyam.utils.CalcUtils;

import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileTicketService implements TicketService {
    @Override
    public Map<String, String> getMinFlightTimeReport(List<TicketDto> tickets, String origin, String destination) {
        Map<String, Long> companiesMinFlightTime = new HashMap<>();
        for (var ticket : filterTicketsByRoute(tickets, origin, destination)) {
            var flightTime = calcFlightTime(ticket);
            companiesMinFlightTime.compute(ticket.getCarrier(), (key, value) ->
                    value == null || flightTime < value ? flightTime : value);
        }
        return null;
    }

    public Double calcMeanMedianDifference(List<TicketDto> tickets, String origin, String destination) {
        List<Integer> prices = new ArrayList<>();
        for (var ticket: filterTicketsByRoute(tickets, origin, destination)) {
            prices.add(ticket.getPrice());
        }
        Collections.sort(prices);
        return CalcUtils.calcAvgValue(prices) - CalcUtils.calcMean(prices);
    }


    private List<TicketDto> filterTicketsByRoute(List<TicketDto> tickets, String origin, String destination) {
        Predicate<TicketDto> destinationPredicate = ticket -> destination.equals(ticket.getDestination());
        Predicate<TicketDto> originPredicate = ticket -> origin.equals(ticket.getOrigin());
        return tickets.stream()
                .filter(originPredicate.and(destinationPredicate))
                .collect(Collectors.toList());
    }

    private Long calcFlightTime(TicketDto ticket) {
        return Duration.between(ticket.getDepartureDateTime(),
                ticket.getArrivalDateTime()).toMinutes();
    }
}
