/*Main idea:
  traverse the tree level by level, use an array to record the node for each level;
  do the same traversal based on the previous array, each time add the child to a new array
 */

/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<List<TreeNode>> tree = new ArrayList<List<TreeNode>>();
        int level = 0;
        if(root == null)
          return result;

        List<Integer> t = new ArrayList<Integer>();
        List<TreeNode> s = new ArrayList<TreeNode>();
        s.add(root);
        t.add(root.val);
        result.add(t);
        tree.add(s);

        while(level < tree.size())
        {
            List<TreeNode> temp = tree.get(level);

            List<TreeNode> child = new ArrayList<TreeNode>();
            List<Integer> added = new ArrayList<Integer>();

            for(int i = 0; i < temp.size(); i ++)
            {
                if(temp.get(i).left != null)
                {
                    child.add(temp.get(i).left);
                    added.add(temp.get(i).left.val);
                }
                if(temp.get(i).right != null)
                {
                    child.add(temp.get(i).right);
                    added.add(temp.get(i).right.val);
                }
            }

            if(child.size() > 0)
            {
                tree.add(child);
                result.add(added);
            }

            level ++;
        }

        return result;
    }
}
