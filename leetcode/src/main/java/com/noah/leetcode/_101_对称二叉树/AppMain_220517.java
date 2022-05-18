package com.noah.leetcode._101_对称二叉树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

        TreeNode h1 = new TreeNode(1);

        TreeNode h2_1 = new TreeNode(2);
        TreeNode h2_2 = new TreeNode(2);

        h1.left = h2_1;
        h1.right = h2_2;

        TreeNode h3_1 = new TreeNode(3);
        TreeNode h3_2 = new TreeNode(3);

        h2_1.left = h3_1;
        h2_2.right = h3_2;

        TreeNode h4_1 = new TreeNode(4);
        TreeNode h4_2 = new TreeNode(4);

        h2_1.right = h4_1;
        h2_2.left = h4_2;

        System.out.println(isSymmetric2(h1));
        //System.out.println(isSymmetric3(h1));


    }

    public static boolean isSymmetric2(TreeNode root) {

        TreeNode left = root;
        TreeNode right = root;

        Queue<TreeNode> q = new LinkedList<>();

        q.offer(left);
        q.offer(right);

        while (!q.isEmpty()) {

            left = q.poll();
            right = q.poll();

            if (left == null && right == null) {
                continue;
            }

            if (((left == null || right == null) || (left.val != right.val))) {
                return false;
            }

            q.offer(left.left);
            q.offer(right.right);

            q.offer(left.right);
            q.offer(right.left);

        }

        return true;
    }

    public static boolean isSymmetric3(TreeNode root) {
        return check(root, root);
    }

    public static boolean check(TreeNode u, TreeNode v) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(u);
        q.offer(v);
        while (!q.isEmpty()) {
            u = q.poll();
            v = q.poll();
            if (u == null && v == null) {
                continue;
            }
            if ((u == null || v == null) || (u.val != v.val)) {
                return false;
            }
            q.offer(u.left);
            q.offer(v.right);
            q.offer(u.right);
            q.offer(v.left);
        }
        return true;
    }

    public boolean isSymmetric(TreeNode root) {

        //中续遍历
        List<Integer> res = new ArrayList<>();

        LinkedList<TreeNode> stock = new LinkedList<>();

        while (root != null || !stock.isEmpty()) {
            while (root != null) {
                stock.push(root);
                root = root.left;
            }

            root = stock.pop();
            res.add(root.val);
            root = root.right;
        }

        for (int i = 0, j = res.size() / 2; j < res.size(); i++, j++) {
            if (res.indexOf(i) != res.indexOf(j)) {
                return false;
            }
        }
        return true;
    }
}
