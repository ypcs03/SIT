/*
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
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        Map<Integer, ListNode> table = new HashMap<Integer, ListNode>();

        ListNode pre = dummy;

        while(head != null){
            if(table.containsKey(head.val)){
                pre.next = head.next;

                ListNode First = table.get(head.val);

                if(First != null){
                    if(First.next != null && head.next != First.next.next)
                      table.put(First.next.val, First);
                    else
                      pre = First;

                    First.next = First.next.next;
                    table.put(head.val, null);
                }
            }
            else{
                table.put(head.val, pre);
                pre = pre.next;
            }

            head = head.next;
        }

        return dummy.next;
    }
}
