/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
/* First: Using divid and conquer, first create the left child tree, then the right child tree, finally combined them together
*  Notification: NULL node should be paied attention
*
*/
public class Solution {
    public void flatten(TreeNode root) {
        inOrderTraver(root);
    }
    /*Return the last leave of the processed list
     */
    public TreeNode inOrderTraver(TreeNode currentNode){
        if(currentNode == null)
            return null;
        //Process the left children tree
        TreeNode leave_left = inOrderTraver(currentNode.left);
        //Process the right children tree
        TreeNode leave_right = inOrderTraver(currentNode.right);
        if(leave_left != null){
            leave_left.right = currentNode.right;
            currentNode.right = currentNode.left;
            currentNode.left = null;
        }
        if(leave_right == null)
            return leave_left == null ? currentNode: leave_left;
        return leave_right;
    }
}
/*Second:
* Traverse the whole tree, each time to move the right child tree of the root to the right most node of the left child tree
*/
public class Solution {
    public void flatten(TreeNode root) {
        TreeNode head = root;
        TreeNode left_child;
        
        while(head != null){
            if(head.left != null){
            
                left_child = head.left;
            
                while(left_child.right != null)
                    left_child = left_child.right;
                
                left_child.right = head.right;
                head.right = head.left;
                head.left = null;
            }
            head = head.right;
        }
        
    }
}
