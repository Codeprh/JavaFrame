package com.noah.leetcode._94_中序遍历;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class AppMain_220517 {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {

    }

    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> q = new LinkedList<>();

        while (root != null || !q.isEmpty()) {

            while (root != null) {
                q.push(root);
                root = root.left;
            }

            root = q.pop();
            res.add(root.val);
            root = root.right;
        }

        return res;
    }
}
