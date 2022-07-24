package com.noah.leetcode._19_倒数节点;

import java.util.Deque;
import java.util.LinkedList;

public class AppMain_level_2 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //use staock
        ListNode dummyNode = new ListNode(0, head);
        ListNode cur = head;

        Deque<ListNode> linked = new LinkedList<ListNode>();
        while (cur != null) {
            linked.push(cur);
            cur = cur.next;
        }


        while (n > 0) {
            linked.pop();
            n--;
        }
        ListNode pre = linked.peek();
        pre.next = pre.next.next;
        return dummyNode.next;
    }
}
