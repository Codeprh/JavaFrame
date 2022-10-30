package com.noah.practice.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapHashCode {

    public static void main(String[] args) {

        String Aa = "Aa";
        String BB = "BB";

        String AaAa = "AaAa";

        System.out.println("Aa=" + Aa.hashCode() + ",AaAa=" + AaAa.hashCode());

        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("4", 4);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            System.out.println(next.getKey());
            //map.put("3", 3);
            if (next.getKey().equals("2")) {
                map.put("3", 3);
            }
        }
        System.out.println(map);
    }
}
