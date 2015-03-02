import java.lang.String;
import java.util.*;
import java.lang.*;

public class Solution
{
    public static void main(String []args)
    {
        List<List<String>> t = new ArrayList<List<String>>();

        for(int i = 0; i < 10; i ++)
            t.add(new ArrayList<String>());
        System.out.println(t.size());
        System.out.println(t.get(0).size());        
        //        Algorithm test = new Algorithm();
        //        String a = new String("ABCDABCDABDCEFCE ABCDABDCEFCSDL IWHS ABCDABDCLSIEWABCDABDCBDABCDASDF");
        //        String b = new String("ABCDABDCEF");

        //        System.out.println(test.kmpStr(a,b));

//        int arry[] = {0};
//        test.ResolveSubset(arry);

//        int array2[] = {2, 3,30,30,30,30, 201, 349,2323, 2382};
//        int pos = 11;
        //        test.ResolveSubset(arry);
        //        test.permutation(arry);
        //        System.out.println(test.largestNum2(arry));
        //        System.out.println(test.BinSearch2(arry,13));
        //        System.out.println(test.BinSearch(arry,13));
        //        System.out.println(test.InsertPosition(arry,10));
//        System.out.println(test.SearchRotated(arry, 23));
//        if(arry.length + array2.length >= pos)
//            System.out.println(test.TheKth(arry,0,array2,0,pos));
//        else
//            System.out.println(-1);
    }
}

class Algorithm
{

    int TheKth(int []array1, int index1, int []array2, int index2, int k)
    {
        if(k == 1)
        {
            if(index1 == -1)
                return array2[index2];
            if(index2 == -1)
                return array1[index1];

            System.out.println(index1);
            System.out.println(index2);
            return Math.min(array1[index1], array2[index2]);
        }

        int mid = k / 2;

        if((array1.length - index1) < mid)
        {
            if(array1[array1.length - 1] >= array2[index2 + mid - 1])
            {
                index2 = index2 + mid;
                return TheKth(array1, index1, array2, index2, k - k / 2);
            }
            else
            {
                index1 = -1;
                return array2[index2 + k - array1.length - 1];
            }

        }
        else if((array2.length - index2) < mid)
        {
            if(array2[array2.length - 1] >= array1[index1 + mid - 1])
            {
                index1 = index1 + mid;
                return TheKth(array1, index1, array2, index2, k - k / 2);
            }
            else
            {
                index2 = -1;
                return array1[index1 + k - array2.length - 1];
            }
        }
        else
        {
            if(array1[index1 + mid -1] >= array2[index2 + mid -1])
            {
                index2 = index2 + mid;
            }
            else
                index1 = index1 + mid;

            if(index1 == array1.length)
                return array2[index2 + k - mid - 1];
            if(index2 == array2.length)
                return array1[index1 + k - mid - 1];

            return TheKth(array1, index1, array2, index2, k - k/2);
        }
    }

    int InsertPosition(int []array, int target)
    {
        if(array == null)
          return 0;

        int start = 0;
        int end = array.length - 1;
        int mid = 0;

        while(start + 1 < end)
        {
            mid = start + (end - start) / 2;

            if(array[mid] < target)
            {
                start = mid;
            }
            else if(array[mid] > target)
            {
                end = mid;
            }
            else
            {
                end = mid;
            }
        }

        if(array[start] < target)
          return start + 1;
        else
          return start;
    }

    int SearchRotated(int [] array, int target)
    {
        if(array == null)
          return -1;

        int start = 0;
        int end = array.length - 1;
        int mid = 0;

        while(start + 1 < end)
        {
            mid = start + (end - start) / 2;

            if(array[mid] >= array[start])  //first half
            {
                if(array[mid] > target && target >= array[start])
                  end = mid;
                else if(array[mid] > target && target < array[start])
                  start = mid;
                else if(array[mid] < target)
                  start = mid;
                else
                  return mid;
            }
            else
            {
                if(array[mid] > target)
                  end = mid;
                else if(array[mid] < target && target <= array[end])
                  start = mid;
                else if(array[mid] < target && target > array[end])
                  end = mid;
                else
                  return mid;
            }
        }

        if(array[start] == target)
          return start;
        if(array[end] == target)
          return end;

        return -1;
    }


    int BinSearch(int []array, int target) //find the first occurence
    {
        if(array == null)
          return -1;
        int start = 0;
        int end = array.length - 1;
        int mid = start + (end - start)/2;

        while(start != end)
        {
            mid = start + (end - start)/2;
            if(array[mid] < target)
              start = mid + 1;
            else if(array[mid] > target)
              end = mid - 1;
            else
              start = mid;
        }

        if(array[start] == target)
          return start;
        else
          return -1;
    }

    int BinSearch2(int []array, int target)
    {
        if(array == null)
          return -1;
        int start = 0;
        int end = array.length - 1;
        int mid = 0;

        while(start + 1 < end)
        {
            mid = start + (end - start) / 2;
            if(array[mid] > target)
              end = mid;
            else if(array[mid] < target)
              start = mid;
            else
              start = mid;
        }

        if(target == array[start])
          return start;
        if(target == array[end])
          return end;
        return -1;
    }

    int path2(int m, int n)
    {
        int [][]result = new int[101][101];
        result[1][2] = 1;
        result[2][1] = 1;

        for(int i = 2; i <= m; i ++)
          for(int j = 2; j <= n; j ++)
            result[i][j] = result[i-1][j] + result[i][j-1];

        return result[m][n];
    }
    long path(int m, int n)
    {
        if(m + n == 3)
          return 1;
        if(m <= 1 || n <= 1)
          return 0;

        return path(m-1,n) + path(m, n -1);
    }

    String largestNum2(int [] num)
    {
        StringBuffer result = new StringBuffer();
        ArrayList<Integer> arrays = new ArrayList<Integer>();

        for(int i = 0; i < num.length; i ++)
          arrays.add(num[i]);

        Collections.sort(arrays, new Comparator<Integer>(){
                    public int compare(Integer o1, Integer o2)
                    {
                    int len1 = 10, len2 = 10;
                    int t1 = o1;
                    int t2 = o2;

                    while((o1 /= 10) > 0)
                    len1 *= 10;
                    while((o2 /= 10) > 0)
                    len2 *= 10;
                    System.out.println(len1 + " " + len2);
                    return (t1 * len2 + t2 - t2 * len1 - t1) ;
                    }
                    });

        for(int j = arrays.size() -1 ; j >= 0; j --)
          result.append(arrays.get(j));

        int j = 0;
        while(j < result.length() && result.charAt(j) == '0')
        {
            result.deleteCharAt(j);
        }
        if(result.length() == 0)
          result.append("0");

        return result.toString();
    }

    String largestNum(int [] num)
    {
        StringBuffer result = new StringBuffer();
        ArrayList<ArrayList<String>> bucket = new ArrayList<ArrayList<String>> ();
        for(int i = 0; i < 10; i ++)
          bucket.add(new ArrayList<String>());

        for(int i = 0; i < num.length; i ++)
        {
            int HighBit = num[i];

            while(HighBit >= 10)
              HighBit /= 10;
            bucket.get(HighBit).add(Integer.toString(num[i]));
        }

        for(int i = bucket.size() - 1; i >= 0; i --)
        {
            if(bucket.get(i).size() == 0)
              continue;

            outputList(bucket.get(i));

            Collections.sort(bucket.get(i), new Comparator<String>(){
                        public int compare(String o1, String o2)
                        {
                        int exchanged = 1;
                        if(o1.length() == o2.length())
                        return o1.compareTo(o2);

                        if(o1.length() > o2.length())
                        {
                        String t = o1;
                        o1 = o2;
                        o2 = t;
                        exchanged = -1;
                        }           //ensure o1 is shorter than o2

                        int index_o1 = 0, index_o2 = 0; 

                        System.out.print("Comparing: " + o1 + " " + o2);
                        System.out.println(" " + exchanged);

                        while(index_o2 < o2.length() && o1.charAt(index_o1) == o2.charAt(index_o2))
                        {
                            index_o2 ++;
                            index_o1 ++;

                            if(index_o1 == o1.length())
                              index_o1 = 0;
                        }

                        if(index_o2 == o2.length() && index_o1 == o1.length())
                          return 0;
                        else 
                        {
                            if(index_o2 == o2.length())
                            {
                                System.out.println((o2.charAt(index_o2 - 1) - o1.charAt(index_o1)) * exchanged * (-1));
                                return (o2.charAt(index_o2 - 1) - o1.charAt(index_o1)) * exchanged * (-1);
                            }
                            else
                            {
                                System.out.println((o1.charAt(index_o1) - o2.charAt(index_o2)) * exchanged);
                                return (o1.charAt(index_o1) - o2.charAt(index_o2)) * exchanged;
                            }
                        }
                        }
            });
            for(int j = bucket.get(i).size() -1 ; j >= 0; j --)
              result.append(bucket.get(i).get(j));
    }

    System.out.println(result);
    int j = 0;
    while(j < result.length() && result.charAt(j) == '0')
    {
        System.out.println(j);
        result.deleteCharAt(j);
    }
    if(result.length() == 0)
      result.append("0");
    return result.toString();
}


void permutation(int arr[])
{
    ArrayList<Integer> sequence = new ArrayList<Integer>();
    Arrays.sort(arr);
    PossibleSequence(sequence, arr);
}

void PossibleSequence(ArrayList<Integer> sequence, int arry[])
{
    for(int i = 0; i < arry.length; i ++)
    {
        if(i != 0 && arry[i] == arry[i-1])
          continue;

        sequence.add(arry[i]);

        if(arry.length == 1)
        {
            outputList(sequence);
            sequence.remove(sequence.size() - 1);
            return;
        }

        int temp[] = new int[arry.length - 1];
        for(int j = 0, t = 0; j < temp.length ;)
        {
            if(t != i)
            {
                temp[j] = arry[t];
                t ++;
                j ++;
            }
            else
            {
                t ++;
            }
        }

        PossibleSequence(sequence, temp);
        sequence.remove(sequence.size()-1);
    }
}

void ResolveSubset(int arr[])
{
    ArrayList<Integer> subset = new ArrayList<Integer>();
    Arrays.sort(arr); 
    IterSubset(subset, arr, 0);
}

void IterSubset(ArrayList subset, int arry[], int index)
{
    outputList(subset);

    for(int i = index; i < arry.length; i ++)
    {
        if(i != index && arry[i] == arry[i-1])
          continue;
        subset.add(arry[i]);
        IterSubset(subset, arry, i + 1);
        subset.remove(subset.size()-1);
    }
}

void outputList(ArrayList list)
{
    System.out.print("{");
    StringBuffer outStr = new StringBuffer();

    int t = 0;
    if(list.size() != 0)
    {
        for(int i = 0; i < list.size(); i ++)
          outStr.append(list.get(i)+", ");
        t = outStr.length() - 2;

    }
    System.out.println(outStr.substring(0, t) + "}");
}


public int strStr(String haystack, String needle) {

    int haylength = haystack.length(); 
    int needlength = needle.length();

    if(haylength < needlength)
      return -1;
    if(needlength == 0)
      return 0;
    for(int i = 0; haylength - i >= needlength; i ++ )
    {
        int index_need = 0;
        int index_hay = i;

        while(haystack.charAt(index_hay) == needle.charAt(index_need) && (haylength - index_hay) >= (needlength - index_need))
        {
            index_need ++;
            index_hay ++;

            if(index_need == needlength)
              return i;

            if(index_hay == haylength)
              return -1;
        }
    }
    return -1;
}

public int kmpStr(String haystack, String needle)
{
    int len_needle = needle.length();
    int len_hay = haystack.length();
    int []arr = new int[len_needle];

    if(len_hay < len_needle)
      return -1;
    if(len_needle == 0)
      return 0;

    for(int i = 1; i < len_needle; i ++)
    {
        int j = 0;
        int count = 0;
        while(i < len_needle && needle.charAt(i) == needle.charAt(j))
        {
            arr[i] = ++ count;
            i ++;
            j ++;
        }
    }

    for(int i = 0; i < arr.length; i ++)
      System.out.println(arr[i]);
    System.out.println("The arr finished!");


    for(int i = 0; len_hay - i >= len_needle;  )
    {
        int index_need = 0;
        int index_hay = i;

        while(haystack.charAt(index_hay) == needle.charAt(index_need))
        {
            index_need ++;
            index_hay ++;

            if(index_need == len_needle)
              return i;

            if(index_hay == len_hay)
              return -1;
        }
        if(index_need == 0)
          i ++;
        else
        {		
            i += index_need - arr[index_need-1];
            System.out.println(i);
        }
    }
    return -1;
}
}
