package ru.ilyam.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CalcUtils {

    private CalcUtils() {
    }

    public static Double calcAvgPrice(List<Integer> prices) {
        if(prices.isEmpty()) {
            throw new IllegalArgumentException("Список цен на билет не должен быть пустым!");
        }
        Integer sum = prices.stream()
                .reduce(0, Integer::sum);
        return (double)sum / prices.size();
    }

    public static Double calcPriceMean(List<Integer> prices) {
        List<Integer> sortedPricesList = new ArrayList<>(prices);
        if(prices.isEmpty()) {
            throw new IllegalArgumentException("Список цен на билет не должен быть пустым!");
        }
        Collections.sort(sortedPricesList);
        int size = sortedPricesList.size();
        if (size % 2 == 0) {
            return (sortedPricesList.get(size / 2 - 1) + sortedPricesList.get(size / 2)) / 2.0;
        } else {
            return Double.valueOf(sortedPricesList.get(size / 2));
        }
    }
}