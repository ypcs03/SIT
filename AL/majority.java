class Majority{
    public static void main(String []args){
        int count1 = 1, count2 = 0;
        int elements[] = {1,2,3,4,5};
            
        int element1 = elements[0];
        int element2 = INTEGER.MAX_VALUE;

        for(int i = 1; i < elements.length; i ++){
            if(elements[i] == element1)
                count1 ++;
            else if(elements[i] == element2)
                count2 ++;
            else if(count1 == 0){
                count1 ++;
                element1 = elements[i];
            }
            else if(count2 == 0){
                count2 ++;
                element2 = elements[i];
            }
            else{
                count1 --;
                count2 --;
            }
        }
        
        count1 = count2 = 0;
        for(int i = 0; i < elements.length; i ++){
            if(element1 == elements[i])
                count1 ++;
            if(element2 == elements[i])
                count2 ++;
        }

        return count1 > count2 ? element1:element2;
    }
}
