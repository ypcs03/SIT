#include <iostream>
#include <vector>
#include <fstream>
#include <cstdlib>
#include <ctime>

using namespace std;

void swap_pivot(int &a, int &b);
int Rslect(vector<int> & arr, int left, int right, const int);

int main(int argc, char *argv[])
{
    string filename;
    ifstream randNum;
    int i = 0;
    int select;
    clock_t start,finish;

    vector<int> Array(10);
    
    randNum.open(argv[1]);

    if(!randNum.good())
    {
        cout << "file: rand.txt doesn't exist" << endl;
        cin >> filename;

        randNum.open(filename.c_str());
        if(!randNum.good())
            exit(-1);
    }

    while(randNum >> select)
    {
        if(i < 10)
        {
            Array[i] = select;
            i ++;
        }
        else
          Array.push_back(select);
    }

    randNum.close();

    vector<int>::const_iterator ite;
    for(ite = Array.begin(); ite < Array.end(); ite ++)
    {
        cout << *ite << "\t";
    }
    cout <<endl; 
    do
    {
        cout << "Input the position: ";
        cin.clear();
        cin.sync();
        cin >> select;
    }while(select < 0 || select > Array.size());
    
    start = clock();
    cout << "The " << select << "th number is: " << Rslect(Array,0,Array.size(),select) << endl;
    finish = clock();
    cout << "used time of : " << ((double)finish - start)/CLOCKS_PER_SEC << endl;
    return 0;
}

int Rslect(vector<int> & arr, int left, int right, const int select)
{
    int pivot = arr[left];
    int lit_end;

    if(right - left == 1 && select == left)
    {
        return arr[left];
    }
    else if(right - left == 1 && select != left)
    {
        cout << "error !" <<endl;
        return -1;
    }

    if(arr[left + 1] > pivot)
      lit_end = left;
    else if(arr[left + 1] <= pivot)
    {
        lit_end = left + 1;
    }

    for(int i=left + 2; i < right; i ++)
    {
        if(arr[i] <= pivot)
        {
            swap_pivot(arr[lit_end+1],arr[i]);
            lit_end ++;
        }
    }
    if(lit_end == select)
        return arr[lit_end];
    
    swap_pivot(arr[left], arr[lit_end]);

    if(lit_end > select)
    {
        return Rslect(arr,left,lit_end,select);
    }
    else
    {
        return Rslect(arr,lit_end+1,right,select);
    }
}

void swap_pivot(int &a, int &b)
{
    int temp = a;
    a = b;
    b = temp;
}
