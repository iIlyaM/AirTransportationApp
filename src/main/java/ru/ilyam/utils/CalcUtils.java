package ru.ilyam.utils;

import java.util.List;

public final class CalcUtils {

    private CalcUtils() {
    }

    public static Double calcAvgValue(List<Integer> prices) {
        Integer sum = prices.stream()
                .reduce(0, Integer::sum);
        return (double)sum / prices.size();
    }

    public static Double calcMean(List<Integer> prices) {
        int size = prices.size();
        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return Double.valueOf(prices.get(size / 2));
        }
    }
}