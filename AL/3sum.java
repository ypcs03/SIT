/*Main idea:
    sort the array
    from the begin to end, select current element and find the two sum equal to its opposite 
*/

public class Solution {
    public List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);

        List<List<Integer>> result = new ArrayList<List<Integer>>();

        for(int i = 0; i < num.length - 2; i ++)
        {
            if(i != 0 && num[i] == num[i - 1])
              continue;

            int target = -num[i];

            int left = i + 1;
            int right = num.length - 1;
            int sum = 0;

            while(left < right)
            {
                sum = num[left] + num[right];

                if(sum == target)
                {
                    ArrayList<Integer> one = new ArrayList<Integer>();
                    one.add(num[i]);
                    one.add(num[left]);
                    one.add(num[right]);
                    result.add(one);

                    do  
                    {
                        left ++;
                        right --;
                    }while(left < right && num[left] == num[left - 1] && num[right + 1] == num[right]);

                }
                else if(sum < target)
                {
                    left ++;
                }
                else
                  right --;
            }
        }

        return result;
    }
}
