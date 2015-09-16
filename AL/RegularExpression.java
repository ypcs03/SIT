/*
 Main idea: Dynamic Programming
    state: match[i][j] indicates whether the first i characters of 's' match the first j characters of 'p'
function:  if(p[j - 1] != '*') compare the current character can be matched or not

            if(p[j - 1] == '*'):
                                the character before repeat once: match[i][j] = match[i - 1][j - 1]
                                the character before repeat none: match[i][j] = match[i][j - 2] 
                                the character before repeat more: match[i][j] = match[i - 1][j] && s[i] == p[i] || p[i] == '.'

    Return: match[s.length][p.length]
 */


public class Solution {
    public boolean isMatch(String s, String p) {
        int s_len = s.length();
        int p_len = p.length();

        boolean match[][] = new boolean[s_len + 1][p_len + 1];
        match[0][0] = true;

        for(int i = 1; i < s_len; i ++)
          match[i][0] = false;

        for(int i = 1; i < p_len + 1; i ++)
        {
            if(p.charAt(i - 1) != '*')
              match[0][i] = false;
            else
              match[0][i] = match[0][i - 2];
        }

        for(int i = 1; i < s_len + 1; i ++)
        {
            for(int j = 1; j < p_len + 1; j ++)
            {
                if(p.charAt(j - 1) != '*')
                {
                    match[i][j] = match[i - 1][j - 1] && (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i - 1));
                }
                else
                {
                    match[i][j] = match[i][j - 1] || match[i][j - 2] || (match[i - 1][j] && ((p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.')));
                }
            }

        }

        return match[s_len][p_len];

    }
}

/*
   Main idea:
        compare one by one: if the next character is '*', then iterate all the possible repeat times;
                            else compare current character, and compare the rest;
 */

public class Solution {
    public boolean isMatch(String s, String p) {
        if(s.length() == 0)
        {
            while(p.length() > 1 && p.charAt(1) == '*')
            {
                p = p.substring(2);
            }

            return p.length() == 0;

        }

        if(p.length() == 0)
          return s.length() == 0;

        if(p.length() == 1 || p.charAt(1) != '*')
          return ((p.charAt(0) == s.charAt(0) || p.charAt(0) == '.') && isMatch(s.substring(1), p.substring(1)));
        else
        {
            int index = 0;
            while((index < s.length()) && ((p.charAt(0) == '.') || s.charAt(index) == p.charAt(0)))
            {
                if(isMatch(s.substring(index + 1), p.substring(2)))
                  return true;
                else
                  index ++;
            }

            return isMatch(s, p.substring(2));
        }

    }
}
