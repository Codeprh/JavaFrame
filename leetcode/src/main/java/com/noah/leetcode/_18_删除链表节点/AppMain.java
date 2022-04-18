package com.noah.leetcode._18_删除链表节点;

public class AppMain {

    public static void main(String[] args) {
        ListNode one = new ListNode(4);

    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        static class Solution {

            public ListNode deleteNode(ListNode head, int val) {

                ListNode hh = head;
                ListNode p = head;
                ListNode node = head, e;
                e = head.next;

                if (head.val == val) {
                    return head.next;
                }

                do {

                    if (e.val == val) {
                        node = e;
                        break;
                    }

                    p = e;

                } while ((e = e.next) != null);

                p.next = node.next;
                return hh;

            }
        }

    }
}
