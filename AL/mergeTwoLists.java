/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

/*Main idea:
  Using the merge sort, compare the two head node, until one list reachs to the end;
  Then add another non-empty list to the new list;
 */

public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode newlist = head;
        while(l1 != null && l2 != null)
        {
            if(l1.val < l2.val)
            {
                newlist.next = new ListNode(l1.val);
                newlist = newlist.next;
                l1 = l1.next;
            }
            else
            {
                newlist.next = new ListNode(l2.val);
                newlist = newlist.next;
                l2 = l2.next;
            }
        }
        while(l1 != null)
        {
            newlist.next = new ListNode(l1.val);
            newlist = newlist.next;
            l1 = l1.next;
        }
        while(l2 != null)
        {
            newlist.next = new ListNode(l2.val);
            newlist = newlist.next;
            l2 = l2.next;
        }
        return head.next;
    }
}
