/*Main idea:
  first sum equals to the first element,
  then traverse array, if sum < 0, then sum = array[i], else sum += array[i]
  use one variable to record the max sum;
 */

public class Solution {
    /**
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(ArrayList<Integer> nums) {
        // write your code
        int sum = nums.get(0), left = 0, right = nums.size() - 1;
        int maxsum = sum;

        for(int i = 1; i < nums.size(); i ++)
        {
            if(sum < 0)
              sum = 0;

            sum += nums.get(i);
            maxsum = Math.max(sum, maxsum);
        }

        return maxsum;

    }
}


/*Calculate the sum of first i elements,
  then use the sum minus the minimum sum of the first i - 1 elements, that could be the max sum
 */
public class Solution {
    /**
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(ArrayList<Integer> nums) {
        int sum = 0, max = -1000000, min=110000;

        for(int i = 0; i < nums.size(); i ++)
        {
            min = Math.min(sum, min);
            sum += nums.get(i);
            max = Math.max(max, sum - min);
        }

        return max;
    }
}
