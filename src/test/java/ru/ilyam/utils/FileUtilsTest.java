package ru.ilyam.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileUtilsTest {

    @Test
    void readFile_shouldReturnString_ifCorrectParams() throws Exception {
        String expectedJsonString = "{\n" +
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

        String actualJsonString = FileUtils.readFile("/ticket/", "tickets_object.json");
        JSONAssert.assertEquals(expectedJsonString, actualJsonString, true);
    }

    @Test
    void readFile_shouldReturnEmptyString_ifEmptyFile() throws Exception {
        String expectedString = "";
        String actualString = FileUtils.readFile("/ticket/", "empty_file.json");
        assertEquals(expectedString.length(), actualString.length());
    }

    @Test
    void readFile_shouldThrowFileNotFoundException_ifFilenameNotExists() throws Exception {
        assertThrows(FileNotFoundException.class, ()->  FileUtils.readFile("/ticket/", "not_found.json"));
    }

    @Test
    void readFile_shouldThrowFileNotFoundException_ifNullPath() {
        assertThrows(FileNotFoundException.class, ()->  FileUtils.readFile(null, "tickets_object.json"));
    }

    @Test
    void readFile_shouldThrowFileNotFoundException_ifNullFileName() {
        assertThrows(FileNotFoundException.class, ()->  FileUtils.readFile("/ticket/", null));
    }
}
