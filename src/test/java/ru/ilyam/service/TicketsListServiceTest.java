package ru.ilyam.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyam.entity.Ticket;
import ru.ilyam.entity.Tickets;
import ru.ilyam.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TicketsListServiceTest {

    private String json = "";
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        try {
            json = FileUtils.readFile("/ticket/", "tickets.json");
            Tickets ticketsDto = new ObjectMapper().readValue(json, Tickets.class);
            tickets = ticketsDto.getTickets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getMinFlightTimeReport_shouldReturnMinFlightTimeReport_ifCorrectTicketList() {
        TicketService ticketService = new TicketsListService();
        Map<String, String> report = new HashMap<>();
        report.put("SU", "6:00");
        report.put("S7", "6:30");
        report.put("TK", "5:50");
        report.put("BA", "8:05");
        assertIterableEquals(ticketService.getMinFlightTimeReport(
                tickets, "VVO", "TLV").entrySet(),
                report.entrySet()
        );
    }

    @Test
    void getMinFlightTimeReport_shouldThrowIllegalArgumentException_ifEmptyTicketsList() {
        TicketService ticketService = new TicketsListService();
        tickets.clear();
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.getMinFlightTimeReport(tickets, "VVO", "TLV"));
    }

    @Test
    void getMinFlightTimeReport_shouldThrowIllegalArgumentException_ifEmptyOrigin() {
        TicketService ticketService = new TicketsListService();
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.getMinFlightTimeReport(tickets, "", "TLV"));
    }

    @Test
    void getMinFlightTimeReport_shouldThrowIllegalArgumentException_ifEmptyDestination() {
        TicketService ticketService = new TicketsListService();
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.getMinFlightTimeReport(tickets, "VVO", ""));
    }

    @Test
    void calcMeanMedianDifference_shouldReturnDifference_ifCorrectTicketList() {
        TicketService ticketService = new TicketsListService();
        assertEquals(460.0, ticketService.calcMeanMedianDifference(tickets, "VVO", "TLV"));
    }

    @Test
    void calcMeanMedianDifference_shouldThrowIllegalArgumentException_ifEmptyTicketsList() {
        TicketService ticketService = new TicketsListService();
        tickets.clear();
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.calcMeanMedianDifference(tickets, "VVO", "TLV"));
    }

    @Test
    void calcMeanMedianDifference_shouldThrowIllegalArgumentException_ifEmptyOrigin() {
        TicketService ticketService = new TicketsListService();
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.calcMeanMedianDifference(tickets, "", "TLV"));
    }

    @Test
    void calcMeanMedianDifference_shouldThrowIllegalArgumentException_ifEmptyDestination() {
        TicketService ticketService = new TicketsListService();
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.calcMeanMedianDifference(tickets, "VVO", ""));
    }
}
