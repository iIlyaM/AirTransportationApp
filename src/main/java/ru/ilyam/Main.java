package ru.ilyam;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ilyam.dto.TicketDto;
import ru.ilyam.dto.TicketsDto;
import ru.ilyam.service.FileTicketService;
import ru.ilyam.service.TicketService;
import ru.ilyam.utils.FileUtils;

import java.util.List;

public class Main {
    private static final String TICKET_PATH = "/ticket/";
    public static void main(String[] args) throws Exception {
        String jsonContent = FileUtils.readFile(TICKET_PATH, "tickets.json");

        // Десериализация JSON в объект TicketContainer
        TicketsDto ticketsDto = new ObjectMapper().readValue(jsonContent, TicketsDto.class);

        // Извлечение массива tickets
        List<TicketDto> tickets = ticketsDto.getTickets();

        TicketService ticketService = new FileTicketService();
        ticketService.getMinFlightTimeReport(tickets, "VVO", "TLV");

        System.out.println(ticketService.calcMeanMedianDifference(tickets, "VVO", "TLV"));
        // Вывод результата
        System.out.println(tickets);
    }
}