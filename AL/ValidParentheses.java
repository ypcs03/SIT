/*Main idea:
  Using the stack to match the '(' and ')'
  '(' push to the stack;
  ')': if the stack is empty, this character is useless, and the left-most '(' could only be the next(i)
  if the stack isn't empty, maybe only one '(' left in the stack, thus the previous substr may be matched, so i - count or i - stack.peek() 
 */

public class Solution {
    public int longestValidParentheses(String s) {
        Stack<Integer> left = new Stack<Integer>();
        int max = 0;
        int count = -1;
        int pre_count = 0;

        for(int i = 0; i < s.length(); i ++)
        {
            if(s.charAt(i) == '(')
              left.push(i);
            else
            {
                if(!left.empty())
                {
                    left.pop();
                    if(left.empty())
                      max = Math.max(max, i - count );
                    else
                      max = Math.max(max, i - left.peek());
                }
                else
                {
                    count = i;
                }
            }
        }
        return max;
    }
}

/*Main idea:
    from the back to the front, an array used to record the length of matched substr from current character(included)
    then each time when there is a '(' try to match the substr behind:

 */

public class Solution {
    public int longestValidParentheses(String s) {

        int matched[] = new int[s.length()];
        if(s.length() < 1)
          return 0;
        matched[s.length() - 1] = 0;
        int right = 0;
        int max = 0;

        for(int i = s.length() - 2; i > -1; i --)
        {
            if(s.charAt(i) == '(')
            {
                right = i + matched[i + 1] + 1;
                if(right < s.length() && s.charAt(right) == ')')
                {
                    matched[i] = matched[i + 1] + 2;
                    if(right + 1 < s.length())
                      matched[i] += matched[right + 1];
                }
            }

            if(max <= matched[i])
              max = matched[i];
        }

        return max;
    }
}
