package ru.ilyam;

import ru.ilyam.handlers.TicketListHandler;
import ru.ilyam.service.TicketService;
import ru.ilyam.service.TicketsListService;

public class Main {
    public static void main(String[] args) {
        TicketService ticketService = new TicketsListService();
        TicketListHandler ticketListHandler = new TicketListHandler(ticketService);
        ticketListHandler.handle("tickets.json", "VVO", "TLV");
    }
}