/*Main idea:
  find the target - numbers[i] whether it has already existed;
  using hashmap;
 */
public class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int occured[] = new int[target + 1];
        int index[] = new int[2];
        Map<Integer, Integer> occur = new HashMap<Integer, Integer>();
        for(int i = 0; i < numbers.length; i ++)
        {
            if(occur.containsKey(target - numbers[i]))
            {
                index[0] = occur.get(target - numbers[i]) + 1;
                index[1] = i + 1;
                return index;
            }
            else
            {
                occur.put(numbers[i],i);
            }
        }
        return null;
    }
}
public class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int tem[] = new int[numbers.length];
        for(int i = 0; i < numbers.length; i ++)
          tem[i] = numbers[i];
        Arrays.sort(numbers);
        int index[] = new int[2];
        int left = 0;
        int right = numbers.length - 1;
        int mid = 0;
        while(left < right)
        {
            mid = numbers[left] + numbers[right];
            if(mid == target)
              break;
            else if(mid < target)
              left ++;
            else
              right --;
        }
        if(mid != target)
          return null;
        else
        {
            for(int i = 0; i < numbers.length; i ++)
            {
                if(numbers[left] == tem[i])
                {
                    index[0] = i + 1;
                    break;
                }
            }
            for(int i = 0; i < numbers.length; i ++)
            {
                if(numbers[right] == tem[i])
                {
                    if(index[0] == i + 1)
                      continue;
                    index[1] = i + 1;
                    break;
                }
            }
        }
        if(index[0] > index[1])
        {
            mid = index[0];
            index[0] = index[1];
            index[1] = mid;
        }
        return index;
    }
}
