package com.noah.leetcode._62_实现前缀树;

public class AppTwoMain {

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        trie.insert("app");
        trie.insert("a");
        trie.insert("aa");

        System.out.println(trie.search("aa"));
        System.out.println(trie.startsWith("ap"));
    }

    static class Trie {
        // 记录前缀树的根节点
        TreeNode root;

        // 定义前缀树节点
        class TreeNode {
            TreeNode[] next;
            boolean isEnd;

            public TreeNode() {
                next = new TreeNode[26];
            }
        }

        // 初始化前缀树
        public Trie() {
            root = new TreeNode();
        }

        // 插入
        public void insert(String word) {
            TreeNode cur = root;
            for (char ch : word.toCharArray()) {
                // 判断对应节点是否为空，如果为空，则直接插入
                if (cur.next[ch - 'a'] == null) {
                    cur.next[ch - 'a'] = new TreeNode();
                }
                // 继续插入下一个节点
                cur = cur.next[ch - 'a'];
            }
            // 将最后一个字符设置为结尾
            cur.isEnd = true;
        }

        // 查找单词
        public boolean search(String word) {
            TreeNode cur = root;
            for (char ch : word.toCharArray()) {
                // 如果对应节点为空，则表明不存在这个单词，返回false
                if (cur.next[ch - 'a'] == null)
                    return false;
                cur = cur.next[ch - 'a'];
            }
            // 检查最后一个字符是否是结尾
            return cur.isEnd;
        }

        // 查找前缀
        public boolean startsWith(String prefix) {
            TreeNode cur = root;
            for (char ch : prefix.toCharArray()) {
                if (cur.next[ch - 'a'] == null)
                    return false;
                cur = cur.next[ch - 'a'];
            }
            return true;
        }
    }
}
