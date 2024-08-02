package ru.ilyam.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketsFlightTimeReport {
    private Map<String, String> report;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : report.entrySet()) {
            sb.append("Компания: ")
                    .append(entry.getKey())
                    .append(". ")
                    .append("Время полёта: ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return sb.toString();
    }
}
