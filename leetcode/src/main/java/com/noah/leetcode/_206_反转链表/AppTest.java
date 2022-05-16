package com.noah.leetcode._206_反转链表;

public class AppTest {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {

        ListNode h1 = new ListNode(1);
        ListNode h2 = new ListNode(2);
        ListNode h3 = new ListNode(3);

        h1.next = h2;
        h2.next = h3;
        h3.next = null;

        reverseList(h1);

    }

    public static ListNode reverseList(ListNode head) {

        ListNode next;
        ListNode l;
        ListNode temp = null;

        if (head == null) {
            return head;
        }

        do {

            l = head;
            next = l.next;

            l.next = temp;

            temp = head;
            head = next;
            //head = next;
            //l = next;

        } while (head != null);

        return l;
    }

    public static ListNode r2(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

}
