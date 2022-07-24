package com.noah.leetcode._62_实现前缀树;

import java.util.HashSet;
import java.util.Set;

public class AppMain {
    class Trie {

        Set<String> sets = new HashSet<>();

        /**
         * Initialize your data structure here.
         */
        public Trie() {

        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            sets.add(word);
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            return sets.contains(word);
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            for (String s:sets){
                if (s.startsWith(prefix)) {
                    return true;
                }
            }
            return false;
        }
    }
}
