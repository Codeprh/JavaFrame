package com.noah.leetcode._1206_跳表;

import java.util.concurrent.ThreadLocalRandom;

public class AppNoah {

    static class Skiplist {

        private static int DEFAULT_MAX_VALUE = 32;
        private static double DEFAULT_P_FACTOR = 0.25;

        Node head = new Node(null, DEFAULT_MAX_VALUE);

        int currentLevel = 1;//表示当前nodes的实际层数，它从1开始

        public Skiplist() {
        }

        public boolean search(int target) {

            Node searchNode = head;
            for (int i = currentLevel - 1; i > 0; i--) {
                searchNode = findClosest(searchNode, i, target);
                if (searchNode.next[i] != null && searchNode.next[i].value == target) {
                    return true;
                }
            }
            return false;
        }

        public void add(int num) {

            int level = randomLevel();
            Node updateNode = head;
            Node newNode = new Node(num, level);

            for (int i = currentLevel - 1; i >= 0; i--) {
                updateNode = findClosest(updateNode, i, num);

                if (i < level) {

                    if (updateNode.next[i] == null) {
                        updateNode.next[i] = newNode;
                    } else {

                        Node temp = updateNode.next[i];
                        updateNode.next[i] = newNode;
                        newNode.next[i] = temp;

                    }
                }
            }

            if (level > currentLevel) {

                for (int i = currentLevel; i < currentLevel; i++) {
                    head.next[i] = newNode;
                }
                currentLevel = level;
            }
        }

        public boolean erase(int num) {

            boolean flag = false;
            Node searchNode = head;

            for (int i = currentLevel - 1; i >= 0; i--) {

                Node node = findClosest(searchNode, i, num);
                if (node.next[i] != null && node.next[i].value == num) {
                    flag = true;
                }
            }
            return flag;
        }


        private Node findClosest(Node node, int levelIndex, int value) {
            while ((node.next[levelIndex] != null) && (value > node.next[levelIndex].value)) {
                node = node.next[levelIndex];
            }
            return node;
        }

        private static int randomLevel() {
            int level = 1;

            while (ThreadLocalRandom.current().nextDouble() < DEFAULT_P_FACTOR && level < DEFAULT_MAX_VALUE) {
                level++;
            }
            return level;
        }

        class Node {

            Integer value;
            Node[] next;

            public Node(Integer value, int size) {
                this.value = value;
                this.next = new Node[size];
            }

        }

    }
}
