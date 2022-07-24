package com.noah.leetcode._19_倒数节点;

public class AppMain_level_1 {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummyNode = new ListNode(0, head);
        ListNode first = dummyNode;

        int nodeLen = getNodeLenght(head);

        for (int i = 0; i < nodeLen - n + 1; i++) {
            first = first.next;
        }
        first.next = first.next.next;
        return dummyNode.next;

    }

    private int getNodeLenght(ListNode node) {
        int count = 0;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }
}
