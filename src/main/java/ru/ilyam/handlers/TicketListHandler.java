package ru.ilyam.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ilyam.entity.Ticket;
import ru.ilyam.entity.Tickets;
import ru.ilyam.entity.TicketsFlightTimeReport;
import ru.ilyam.service.TicketService;
import ru.ilyam.utils.FileUtils;

import java.util.List;

public class TicketListHandler {
    private final TicketService ticketService;
    private static final String TICKET_PATH = "/ticket/";

    public TicketListHandler(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void handle(String fileName, String origin, String destination) {
        String jsonContent;
        List<Ticket> tickets;
        try {
            jsonContent = FileUtils.readFile(TICKET_PATH, fileName);
            Tickets ticketsDto = new ObjectMapper().readValue(jsonContent, Tickets.class);
            tickets = ticketsDto.getTickets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        TicketsFlightTimeReport flightTimeReport = new TicketsFlightTimeReport();
        flightTimeReport.setReport(ticketService.getMinFlightTimeReport(tickets, origin, destination));
        ticketService.printReportsResults(flightTimeReport,
                ticketService.calcMeanMedianDifference(tickets, origin, destination));
    }
}
