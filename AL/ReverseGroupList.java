/*Main idea: from the front to the end of the list, reverse every k nodes, the last node.next equals to the return value of the rest of the list,
  using recursion;
 */

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
public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k < 2)
          return head;

        return helper(head, k);
    }

    ListNode helper(ListNode root, int n)
    {
        if(root == null)
          return null;

        ListNode cur = root;
        ListNode next = null;
        ListNode pre = null;

        int i = 0;
        for(; i < n && cur != null; i ++)
        {
            next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }

        if(i != n)
        {
            cur = pre;
            pre = null;
            for(; i > 0; i --)
            {
                next = cur.next;
                cur.next = pre;

                pre = cur;
                cur = next;
            } 

            return pre;
        }


        root.next = helper(cur, n);

        return pre;
    }
}
