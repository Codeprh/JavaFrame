package com.noah.practice.log;

import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataSorted {
    static double u = 0.5;

    public static void main(String[] args) {
        List<Item> ls = new ArrayList<>();
        ls.add(new Item("1", "A", 11.0));
        ls.add(new Item("2", "B", 10.0));
        ls.add(new Item("3", "B", 10.1));
        ls.add(new Item("4", "A", 4.0));
        ls.add(new Item("4", "C", 9.0));
        ls.add(new Item("4", "C", 11.0));
        ls.add(new Item("4", "B", 11.0));
        Collections.sort(ls, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                Double diff = o1.getScore() - o2.getScore();
                if (diff > 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        System.out.println(JSONUtil.toJsonStr(ls));
        System.out.println(JSONUtil.toJsonStr(windowsScatter(ls, 1,2)));
    }

    /**
     * 通过设置滑动窗口，对窗口内元素一定程度打散
     *
     * @param numbers
     * @param windowsSize
     * @return
     */
    public static List<Item> windowsScatter(List<Item> numbers, Integer windowsSize, Integer maxSize) {

        for (int i = 0; i < numbers.size() - windowsSize; i++) {
            List<Item> subls = numbers.subList(i, i + windowsSize);
            List<String> keys = new ArrayList<>();
            int j = windowsSize + i;
            for (int m = 0; m < windowsSize; m++) {

                Item item = subls.get(m);

                if (keys.size() >= maxSize && keys.contains(item.getType())) {
                    numbers.set(i + m, numbers.get(j));
                    numbers.set(j, item);
                    keys.add(item.getType());
                    j += 1;
                } else {
                    keys.add(item.getType());
                }
            }
        }
        return numbers;
    }

    static class Item {
        String pid = null;
        String type = null;
        Double score = 0.0;
        Double newScore = null;

        public Item(String pid, String type, Double score) {
            this.pid = pid;
            this.score = score;
            this.type = type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public void setNewScore(Double newScore) {
            this.newScore = newScore;
        }

        public String getType() {
            return type;
        }

        public Double getScore() {
            return score;
        }

        public Double getNewScore() {
            return newScore;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPid() {
            return pid;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "pid='" + pid + '\'' +
                    ", type='" + type + '\'' +
                    ", score=" + score +
                    ", newScore=" + newScore +
                    '}';
        }
    }
}

