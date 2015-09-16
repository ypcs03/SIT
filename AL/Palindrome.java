/*Main idea:
    work out whether the area of i to j of the string is palindrome: Using dynamic programming
    during the period calculate the cut
 */


public class Solution {
    public int minCut(String s) {
        int length = s.length();

        boolean palind[][] = new boolean[length][length];
        int cut [] = new int[length];

        for(int i = 0; i < length; i ++)
        {
            cut[i] = i;
            palind[i][i] = true;
        }

        for(int i = 1; i < length; i ++)
          for(int j = 0; j <= i; j ++)
          {
              if(s.charAt(j) == s.charAt(i) && (i < j + 2  || palind[i - 1][j + 1]))
              {
                  cut[i] = Math.min(j == 0?0:cut[j - 1] + 1, cut[i]);
                  palind[i][j] = true;
              }
          }

        return cut[length - 1];

    }
}
