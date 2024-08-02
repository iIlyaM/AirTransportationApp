package ru.ilyam.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class FileUtils {

    private FileUtils() {
    }

    public static String readFile(String path, String fileName) throws Exception {
        try (InputStream inputStream = FileUtils.class.getResourceAsStream(path + fileName);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
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
        } catch (FileNotFoundException | NullPointerException exception) {
            throw new FileNotFoundException(exception.getMessage());
        }
    }
}