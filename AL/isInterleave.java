/*main iead: dynamic programming;
  f[i][j] implies whether the first i letters of string1 and the first j letters of string2 can match the i+j letters of string3
  f[i][j] = f[i-1][j] && string1[i] == string3[i+j-1] || f[i][j-1] && string2[j] == string3[i+j-1]
 */
public class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length();
        int l2 = s2.length();
        int l3 = s3.length();

        if(l3 != l2 + l1)
          return false;
        //        if(l3 == 0 && l2 == 0 && l1 ==0)
        //            return true;

        boolean match[][] = new boolean[l1 + 1][l2 + 1];
        //        for(int i = 0; i < l1 + 1; i ++)
        //            for(int j = 0; j < l2 + 1; j ++)
        //            {
        //                match[i][j] = false;
        //            }
        match[0][0] = true;
        int i = 1;
        while(i < l1 + 1 && i < l3 + 1 && s1.charAt(i - 1) == s3.charAt(i - 1))
        {

            match[i][0] = true;
            i ++;
        }
        i = 1;
        while(i < l2 + 1 && i < l3 + 1 && s2.charAt(i - 1) == s3.charAt(i - 1))
        {

            match[0][i] = true;
            i ++;
        }

        for(i = 1; i < l1 + 1; i ++)
          for(int j = 1; j < l2 + 1; j ++)
          {
              //                if(i + j - 1 == l3)
              //                    return match[i][j-1] || match[i-1][j];
              if(match[i - 1][j]  && s1.charAt(i-1) == s3.charAt(i+j-1))
                match[i][j] = true;
              else if(match[i][j - 1] && s2.charAt(j-1) == s3.charAt(i+j-1))
                match[i][j] = true;
          }
        return match[l1][l2];
    }
}
