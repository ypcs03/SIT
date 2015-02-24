/*Main idea:
  using the 256 size array to record the appear times of each letter, for source string and des string;
  Compare the two arrays, when every element in S string is no less than that in Des string, then a container exists;
  Then, when find the match one, try to shrink the left pointer;
 */
public class Solution {
    public String minWindow(String S, String T) {
        if(S.length() < T.length())
          return "";

        int[] s_count = new int[256];
        int[] t_count = new int[256];

        for(int i = 0; i < T.length(); i ++)
          t_count[T.charAt(i)] ++;

        int start = 0;
        int left = 0;
        int end = S.length();
        boolean flag = false;

        for(int i = 0; i < T.length() - 1; i ++)
          s_count[S.charAt(i)] ++;

        for(int i = T.length() - 1; i < S.length(); i ++)
        {
            int count = 0;

            s_count[S.charAt(i)] ++;
            while(count < 256)
              if(t_count[count] > s_count[count++])
                break;

            if(count == 256)
            {
                while(s_count[S.charAt(left)] > t_count[S.charAt(left)])
                {
                    s_count[S.charAt(left)] --;
                    left ++;
                }

                flag = true; 

                if(end - start > i + 1 - left)
                {
                    start = left;
                    end = i + 1; 
                }    
            }
        }

        if(!flag)
          return "";

        return S.substring(start, end);
    }
}
