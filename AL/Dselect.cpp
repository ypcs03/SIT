#include <iostream>
#include <ctime>
#include <vector>
#include <fstream>

using namespace std;

class position
{
public:
    int num;
    int index;
    position(int x, int y)
    {
        num = x;
        index = y;
    }
    position()
    {
        num = 0;
        index = 0;
    }
};

position Dselect(vector<position> &arr, int , int);
void swap(position &, position &);
position bubblesort(vector<position> arr, int , int);
void result(vector<position> &arr, int left, int right, const int select);
int main(int argc, char* argv[])
{
    string filename;
    int select,i = 0;
    int pivot;
    clock_t start, finish;
    ifstream file_data(argv[1]);
    vector<position> Arrays(10);

    if(!file_data.good())
    {
        cout << "Input the file of data: ";
        cin >> filename;

        file_data.open(filename.c_str());

        if(!file_data.good())
        {
            cout << "Open file failed \n";
            return -1;
        }
    }

    while(file_data >> select)
    {
        if(i < 10)
        {
            Arrays[i].num = select;
            Arrays[i].index = i;
            i ++;
        }
        else
        {  
          Arrays.push_back(position(select,i));
          i ++;
        }
    }

    cout << "Input the position you want to know: ";
    cin >> select;

    if(select > Arrays.size())
    {
        cout << "Out of range! \n";
        return -1;
    }
    start = clock();
   result(Arrays,0,Arrays.size(),select); 
    finish = clock();

    cout << "Time consuming: " << ((double)finish-start)/CLOCKS_PER_SEC << endl;
    return 0;
}

void result(vector<position> &arr, int left, int right, const int select)
{
    int lit_end;
    if(right - left == 1 && left == select)
    {
        cout << "The " << select << "th number is: " << arr[left].num << endl;
        return ;
    }
    else if(right - left == 1 && left != select)
    {
        cout << "error";
        return ;
    }

    const position pivot = Dselect(arr, left, right);
    swap(arr[left], arr[pivot.index]);

    if(arr[left + 1].num > pivot.num)
    {
        lit_end = left;
    }
    else
      lit_end = left + 1;

    for(int i = left + 2; i < right; i ++)
    {
        if(arr[i].num < pivot.num)
        {
            swap(arr[i],arr[lit_end + 1]);
            lit_end ++;
        }
    }
    swap(arr[left], arr[lit_end]);
    

    if(lit_end == select)
    {
        cout << "The " << select << "th number is: " << arr[lit_end].num << endl;
        return ;
    }
    else if(lit_end < select)
    {
        for(int i = lit_end + 1; i < right; i ++)
            arr[i].index = i;
        result(arr,lit_end+1, right,select);
    }
    else
    {
        for(int i = left; i < lit_end; i ++)
        arr[i].index = i;
        result(arr,left,lit_end,select);
    }
}


position Dselect(vector<position> &arr, int left, int right)
{
    if(right - left <= 5)
    {
        return bubblesort(arr, left, right);
    }

    vector<position> temp;

    for(int i= left; i< right; i += 5)
    {
        int r = right - i > 5? 5: right - i;
        temp.push_back(Dselect(arr,i,r + i));
    }

    return Dselect(temp,0,temp.size());
}

//sort the array upforward
position bubblesort(vector<position> arr, int left, int right)
{
    int flag = 1;
    int j = 0;

    while( j < (right - left) && flag == 1)
    {
        flag = 0;
        for(int i = left; i < right - j - 1; i ++)
        {
            if(arr[i].num > arr[i + 1].num)
            {
                swap(arr[i], arr[i+1]);
                flag = 1;
            }
        }
        j ++;
    }
    return arr[(right+left)/2];
}
/*
test 
 
 */

void swap(position &a, position &b)
{
    int temp = a.num;
    int temp_index = a.index;
    a.num = b.num;
    a.index = b.index;
    b.num = temp;
    b.index = temp_index;
}
