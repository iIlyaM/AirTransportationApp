package ru.ilyam.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyam.entity.Ticket;
import ru.ilyam.entity.Tickets;
import ru.ilyam.entity.TicketsFlightTimeReport;
import ru.ilyam.service.TicketService;
import ru.ilyam.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketListHandlerTest {
    @InjectMocks
    private TicketListHandler ticketListHandler;

    @Mock
    private TicketService ticketService;

    @SneakyThrows
    @Test
    void handle_shouldPrintReport_ifCorrectPathAndFilename() {
        Map<String, String> report = new HashMap<>();
        report.put("SU", "6:00");
        report.put("S7", "6:30");
        report.put("TK", "5:50");
        report.put("BA", "8:05");
        String json = "{\n" +
                "  \"tickets\": [\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"16:20\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"22:10\",\n" +
                "      \"carrier\": \"TK\",\n" +
                "      \"stops\": 3,\n" +
                "      \"price\": 12400\n" +
                "    },\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"17:20\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"23:50\",\n" +
                "      \"carrier\": \"S7\",\n" +
                "      \"stops\": 1,\n" +
                "      \"price\": 13100\n" +
                "    },\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"12:10\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"18:10\",\n" +
                "      \"carrier\": \"SU\",\n" +
                "      \"stops\": 0,\n" +
                "      \"price\": 15300\n" +
                "    },\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"17:00\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"23:30\",\n" +
                "      \"carrier\": \"TK\",\n" +
                "      \"stops\": 2,\n" +
                "      \"price\": 11000\n" +
                "    },\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"12:10\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"20:15\",\n" +
                "      \"carrier\": \"BA\",\n" +
                "      \"stops\": 3,\n" +
                "      \"price\": 13400\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try (MockedStatic<FileUtils> utilities = Mockito.mockStatic(FileUtils.class)) {
            Tickets ticketsDto = new ObjectMapper().readValue(json, Tickets.class);
            List<Ticket> tickets = ticketsDto.getTickets();
            utilities.when(() -> FileUtils.readFile("/ticket/", "tickets_short.json")).thenReturn(json);

            when(ticketService.getMinFlightTimeReport(tickets, "VVO", "TLV")).thenReturn(report);
            ticketListHandler.handle("tickets_short.json", "VVO", "TLV");

            utilities.verify(() -> FileUtils.readFile("/ticket/", "tickets_short.json"),
                    times(1));
            verify(ticketService, times(1)).getMinFlightTimeReport(
                    tickets,
                    "VVO",
                    "TLV"
            );
            verify(ticketService, times(1)).calcMeanMedianDifference(
                    tickets,
                    "VVO",
                    "TLV"
            );
            verify(ticketService, times(1)).printReportsResults(any(TicketsFlightTimeReport.class),
                    anyDouble());
        }
    }

    @Test
    void handle_shouldThrowRuntimeException_ifContainsNullFilename() {
        assertThrows(RuntimeException.class, () -> ticketListHandler.handle(null, "VVO", "TLV"));
    }

    @Test
    void handle_shouldThrowIllegalArgumentException_ifContainsEmptyOrigin() {
        String json = "{\n" +
                "  \"tickets\": [\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"16:20\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"22:10\",\n" +
                "      \"carrier\": \"TK\",\n" +
                "      \"stops\": 3,\n" +
                "      \"price\": 12400\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try (MockedStatic<FileUtils> utilities = Mockito.mockStatic(FileUtils.class)) {
            utilities.when(() -> FileUtils.readFile("/ticket/", "tickets_object.json"))
                    .thenReturn(json);

            when(ticketService.getMinFlightTimeReport(anyList(), eq(""), anyString()))
                    .thenThrow(new IllegalArgumentException());
            assertThrows(IllegalArgumentException.class, () ->
                    ticketListHandler.handle("tickets_object.json", "", "TLV"));
        }
    }

    @Test
    void handle_shouldThrowIllegalArgumentException_ifContainsEmptyDestination() {
        String json = "{\n" +
                "  \"tickets\": [\n" +
                "    {\n" +
                "      \"origin\": \"VVO\",\n" +
                "      \"origin_name\": \"Владивосток\",\n" +
                "      \"destination\": \"TLV\",\n" +
                "      \"destination_name\": \"Тель-Авив\",\n" +
                "      \"departure_date\": \"12.05.18\",\n" +
                "      \"departure_time\": \"16:20\",\n" +
                "      \"arrival_date\": \"12.05.18\",\n" +
                "      \"arrival_time\": \"22:10\",\n" +
                "      \"carrier\": \"TK\",\n" +
                "      \"stops\": 3,\n" +
                "      \"price\": 12400\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try (MockedStatic<FileUtils> utilities = Mockito.mockStatic(FileUtils.class)) {
            utilities.when(() -> FileUtils.readFile("/ticket/", "tickets_object.json"))
                    .thenReturn(json);

            when(ticketService.getMinFlightTimeReport(anyList(), anyString(), eq("")))
                    .thenThrow(new IllegalArgumentException());
            assertThrows(IllegalArgumentException.class, () ->
                    ticketListHandler.handle("tickets_object.json", "VVO", ""));
        }
    }
}
