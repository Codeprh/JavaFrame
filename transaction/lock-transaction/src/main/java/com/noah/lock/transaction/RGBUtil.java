package com.noah.lock.transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RGBUtil {


    public static String rgbHex(int[] rgb) {
        if (rgb == null) return null;
        return rgbHex(Arrays.stream(rgb).boxed().collect(Collectors.toList()));
    }

    public static String rgbHex(List<Integer> rgb) {
        if (rgb == null) return null;
        if (rgb.size() != 3) {
            return null;
        }
        for (Integer i : rgb) {
            if (i == null || i < 0 || i > 255) {
                return null;
            }
        }
        StringBuilder sb = new StringBuilder("#");
        for (int i : rgb) {
            sb.append(String.format("%02X", i));
        }
        return sb.toString();
    }
}
