package ru.ilyam.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class FileUtils {

    private FileUtils() {
    }

    public static String readFile(String path, String fileName) throws Exception {
        try (InputStream inputStream = FileUtils.class.getResourceAsStream(path + fileName);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            // Пропускаем BOM, если он присутствует
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                if (firstLine.startsWith("\uFEFF")) {
                    firstLine = firstLine.substring(1);
                }
                StringBuilder content = new StringBuilder(firstLine);
                while (scanner.hasNextLine()) {
                    content.append(System.lineSeparator()).append(scanner.nextLine());
                }
                return content.toString().trim();
            }
            return "";
        } catch (Exception exception) {
            throw new Exception("File not found: " + path + fileName, exception);
        }
    }

    public static void writeFile(String filePath, String data) {
        if(filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Путь к файлу указан не верно или отсутствует");
        }
        if(data == null) {
            throw new IllegalArgumentException("Отсутствуют данные для записи в файл");
        }
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(data);
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath))) {
            bWriter.write(sBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}