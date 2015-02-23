/*Main idea:
  The gray code can be calculated from binary:
        current bit equals to the right bit xor current bit, the highest bit keep its value;
        eg: b:101110 ---> g: 111001
*/

/*Another idea:
  00,10,11,10 
  Each time the gray code(>2 bits): first add 0 to the highest bit, and then in the opposite order add 1 to the highest bit;
eg: 4bits,  00 01 11 10; 
            000 001 011 010, 110 111 101 100; 
            0000 0001 0011 0010 0110 0111 0101 0100, 1100 1101 1111 1110 1010 1011 1001 1000;
 */


public class Solution {
    public List<Integer> grayCode(int n) {
        int len = 1 << n;
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < len ; i ++)
        {
            result.add(i ^ (i >> 1));
        }
        return result;
    }
}
