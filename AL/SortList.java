/*Main idea:
  merge sort, dummy node:
  cut the list half, first sort the second half, and then make the end of the first half to null, sort the first half
 */

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode sortList(ListNode head) {
        return helper(head);  
    }

    ListNode helper(ListNode head)
    {
        if(head == null || head.next == null)
          return head;

        ListNode head2 = head;
        ListNode half = null;

        ListNode pre = new ListNode(0);
        pre.next = head;

        while(head2 != null && head2.next != null)
        {
            pre = pre.next;
            head2 = head2.next.next;
        }

        half = helper(pre.next);
        pre.next = null;
        head = helper(head);

        pre = new ListNode(0);
        head2 = pre;

        while(half != null && head != null)
        {
            if(half.val > head.val)
            {
                head2.next = head;
                head = head.next;
            }
            else
            {
                head2.next = half;
                half = half.next;
            }

            head2 = head2.next;
        }

        if(head != null)
          head2.next = head;
        if(half != null)
          head2.next = half;

        return pre.next;
    }

}
