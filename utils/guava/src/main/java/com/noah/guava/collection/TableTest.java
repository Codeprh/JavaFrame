package com.noah.guava.collection;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;

public class TableTest {

    public static void main(String[] args) {

        Table<String, String, Double> weightedGraph = HashBasedTable.create();

        weightedGraph.put("java", "k8s", 4.0);
        weightedGraph.put("java", "k8s", 20.0);
        weightedGraph.put("java", "k8s", 5.0);

        Map<String, Double> java = weightedGraph.row("java");// returns a Map mapping v2 to 4, v3 to 20

        Map<String, Double> k8s = weightedGraph.column("k8s");// returns a Map mapping v1 to 20, v2 to 5

        Map<String, Map<String, Double>> stringMapMap = weightedGraph.rowMap();

        Double aDouble = weightedGraph.get("java", "k8s");

        //System.out.println(111);

        Map<String, Map<String, Double>> stringMapMap1 = weightedGraph.rowMap();

        System.out.println(JSONUtil.toJsonStr(weightedGraph));
    }
}
