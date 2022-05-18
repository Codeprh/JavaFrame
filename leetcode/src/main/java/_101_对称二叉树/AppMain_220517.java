package _101_对称二叉树;

import java.util.ArrayList;
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

        TreeNode h1 = new TreeNode(1);

        TreeNode h2_1 = new TreeNode(2);
        TreeNode h2_2 = new TreeNode(2);

        h1.left = h2_1;
        h1.right = h2_2;



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
