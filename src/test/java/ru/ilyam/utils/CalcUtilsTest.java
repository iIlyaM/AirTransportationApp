package ru.ilyam.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CalcUtilsTest {

    @Test
    void calcAvgPrice_shouldReturnAvgPrice_ifCorrectParams() {
        List<Integer> prices = List.of(100, 250, 300, 480, 657, 382, 645, 894);
        assertEquals(463.5, CalcUtils.calcAvgPrice(prices));
    }

    @Test
    void calcAvgPrice_shouldThrowIllegalArgumentException_ifEmptyPrices() {
        assertThrows(IllegalArgumentException.class, () ->
                CalcUtils.calcAvgPrice(new ArrayList<>()));
    }

    @Test
    void calcPriceMean_shouldReturnAvgPrice_ifCorrectParams() {
        List<Integer> prices = List.of(100, 250, 300, 480, 657, 382, 645, 894);
        assertEquals(431.0, CalcUtils.calcPriceMean(prices));
    }

    @Test
    void calcPriceMean_shouldThrowIllegalArgumentException_ifEmptyPrices() {
        assertThrows(IllegalArgumentException.class, () ->
                CalcUtils.calcPriceMean(new ArrayList<>()));
    }
}
