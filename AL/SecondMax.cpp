#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <fstream>
#include <vector>

using namespace std;

void sort_merge(vector<int> &,int, int);
void swapdata(int &, int &);

int count = 0;

int main()
{
    ifstream data("../rand.txt");
    int times = 0;
    cin >> times;

    if(!data.good())
      exit(-1);

    vector<int> arrays;
    int i;

    while(data >> i && times > 0)
    {
        times --;
        arrays.push_back(i);
    }
    data.close();

    sort_merge(arrays,0,arrays.size() - 1);

    vector<int>::const_iterator it = arrays.begin();
    for(; it < arrays.end(); it ++)
      cout << *it << "\t";
    cout << endl;
    
    int secondLarge = arrays[1];
    int j = arrays.size()/2;

    while(j != 2)
    {
        count ++;
        if(secondLarge < arrays[j])
          secondLarge = arrays[j];
 
        j /= 2;
    }
    cout << "The second largest num is: " << secondLarge << endl;
    cout << "Totally compared : " << count << " times" << endl;
	return 0;
}

void sort_merge(vector<int> & arrays, int low, int high) //high and low are inclusive
{
    if((high - low) == 1)
    {
        count ++;
        if(arrays[high] > arrays[low])
          swapdata(arrays[high], arrays[low]);
        return ;
    }

    sort_merge(arrays, low, (high + low -1)/2);
    sort_merge(arrays, (high + low + 1)/2, high);

    count ++;
    if(arrays[low] < arrays[(high + low + 1)/2])
      for(int i = low; i < (high + low - 1)/2 ; i ++)
        swapdata(arrays[i], arrays[ i + (high - low + 1)/2]);
}

void swapdata(int &a, int &b)
{
    int t = a;
    a = b;
    b = t;
}
