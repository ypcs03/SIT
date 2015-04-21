import java.util.*;
//There is another way in c, using the index of an array:
//address=(char*)a; &address[b] is the result
public class AddWithoutMath{
    public static void main(String[]args){

        AddWithoutMath daemon = new AddWithoutMath();

        System.out.println(daemon.add(-3,4));
    }

    int add(int a, int b){
        int carry = a & b;
        int sum = a ^ b;

        while(carry != 0){
            sum = sum ^ carry;
            carry = sum & carry;
        }

        return sum;
    }
}
