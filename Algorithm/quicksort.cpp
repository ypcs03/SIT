#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

#define PIVOT 0

void quicksort(vector<int> & c, int a, int b);
void swap(int &, int &);


int main( )
{
    ifstream num("num.txt");
    vector<int> Array;
    int i;

    while(num >> i)
    {
        Array.push_back(i);
    }

    quicksort(Array, 0 , Array.size());

    for(vector<int>::const_iterator it = Array.begin(); it != Array.end(); it ++)
        cout << *it << endl;    

    return 0;
}

void swap(int &i, int &j)
{
    if(i == j)
       return ;
    int temp = i;
    i = j;
    j = temp;
}

void quicksort(vector<int> &Array, int left, int right) //right is excluded
{
    int pivot = Array[PIVOT + left];
    int bigger;

    if(right - left < 2)
        return;

    if(Array[left +1] > Array[left])
        bigger = left;
    else 
        bigger = left + 1;

    for(int i = left + 2; i < right; i ++)
    {
        switch( Array[i] > pivot )
        {
            case true:
                break;
            case false:
                swap(Array[i], Array[bigger+1]);
                bigger ++;
                break;
            default:
                break;
        }
    }
    swap(Array[PIVOT+left], Array[bigger]);
    
    quicksort(Array, left, bigger);
    quicksort(Array, bigger + 1, right);
}
