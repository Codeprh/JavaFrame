package com.noah.leetcode._121_二叉树的层序遍历;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

class TreeNode {
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

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<List<Integer>>();
        }

        List<List<Integer>> recs = new ArrayList<List<Integer>>();
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();

        queue.add(root);
        while (queue.size() > 0) {
            int size = queue.size();
            List<Integer> tep = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
                TreeNode t = queue.remove();
                tep.add(t.val);

                if (t.left != null) {
                    queue.add(t.left);
                }

                if (t.right != null) {
                    queue.add(t.right);
                }
            }

            recs.add(tep);
        }

        return recs;
    }
}

public class AppMain_231106 {

}
