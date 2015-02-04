/*Output the median number of input sequence
 *imediately after input one data each time;
 */
#include <string>
#include <iostream>
#include <vector>
#include <fstream>

#define MAXHEAP 0
#define MINHEAP 1
#define PARENT(i) ((i-1)/2)

using namespace std;

void heapDelete(vector<int> &, int, int);
void heapInsert(vector<int> &, int ,int);

int main()
{
    vector<int> Harrays, Larrays;
    ifstream Num_file("../rand.txt");

    int tem;
    int t;
    int count = 0;
try{
    while(Num_file >> tem)
    {
        count ++;

        if(count == 1)
        {
          t = tem;  
          continue;
        }
        if(count == 2)
        {
            if(t > tem)
            {
              heapInsert(Harrays,t,MINHEAP);
              heapInsert(Larrays,tem,MAXHEAP);
            }
            else
            {
                heapInsert(Harrays,tem,MINHEAP);
                heapInsert(Larrays,t,MAXHEAP);
            }
            continue;
        }
        switch(Larrays.size() < Harrays.size())
        {
            case true:
                if(tem <= Harrays[0])
                    heapInsert(Larrays, tem, MAXHEAP);
                else
                {
                    heapInsert(Larrays,Harrays[0],MAXHEAP);
                    heapDelete(Harrays,tem,MINHEAP);
                }
                break;

            case false:
                if(tem >= Larrays[0])
                    heapInsert(Harrays,tem,MINHEAP);
                else
                {
                    heapInsert(Harrays,Larrays[0], MINHEAP);
                    heapDelete(Larrays,tem,MAXHEAP);
                }
                break;

            default:
                break;
        }
        cout << "Currently readed " << count << "numbers" << endl;
        if(!(count % 2))
        {
            cout << Larrays[0] << "\t" << Harrays[0] << endl;
        }
        else
        {
            cout << Harrays[0] << endl;
        }
    }
}catch(exception e)
{
    cout << e.what() << endl;
}
    Num_file.close();
    return 0;
}

void heapInsert(vector<int> & arrays, int data, int maxOrmin)
{
    int flag = -1;
    int index = 0;
    int parent = 0;

    arrays.push_back(data);
    index = arrays.size() - 1;

    if(index == 0)
      return;
    switch(maxOrmin)
    {
        case MAXHEAP:
            while(index >= 0)
            {
                if(index == 0)
                  return ;
                if(arrays[index] > arrays[PARENT(index)])
                {
                    int t = arrays[index];
                    arrays[index] = arrays[PARENT(index)];
                    arrays[PARENT(index)] = t;
                }
                index = PARENT(index);
            }
            break;
        case MINHEAP:
            while(index >= 0)
            {
                if(index == 0)
                  return;
                if(arrays[index] < arrays[PARENT(index)])
                {
                    int t = arrays[index];
                    arrays[index] = arrays[PARENT(index)];
                    arrays[PARENT(index)] = t;
                }
                index = PARENT(index);
            }
            break;
        default:
            throw exception();
            //            throw exception("error parameter in heapInsert");
            break;
    }
}

void heapDelete(vector<int> &arrays, int data, int maxOrmin)
{
    int index  = 0;
    arrays[0] = data;

    switch(maxOrmin)
    {
        case MINHEAP:
            // min heap
            while((index+1)*2 < arrays.size() && ((index+1)*2-1) < arrays.size())
            {
                if(arrays[index] > arrays[(index+1)*2 - 1] && arrays[index] > arrays[(index +1)*2])
                {
                    if(arrays[(index+1)*2 - 1] < arrays[(index+1)*2])
                    {
                        int t = arrays[index];
                        arrays[index] = arrays[(index+1)*2 - 1];
                        arrays[(index+1)*2 - 1] = t;
                        index = (index + 1) *2 -1;
                    }
                    else if(arrays[(index+1)*2 - 1] >= arrays[(index+1)*2])
                    {
                        int t = arrays[index];
                        arrays[index] = arrays[(index+1)*2];
                        arrays[(index+1)*2] = t;
                        index = (index + 1)*2;
                    }
                }
                else if(arrays[index] > arrays[(index+1)*2 - 1] && arrays[index] <= arrays[(index +1)*2])
                {
                    int t = arrays[index];
                    arrays[index] = arrays[(index+1)*2 - 1];
                    arrays[(index+1)*2 - 1] = t;
                    index = (index+1)*2-1;
                }
                else if(arrays[index] <= arrays[(index+1)*2 - 1] && arrays[index] > arrays[(index +1)*2])
                {
                    int t = arrays[index];
                    arrays[index] = arrays[(index+1)*2];
                    arrays[(index+1)*2] = t;
                    index = (index+1)*2;
                }
                else
                  index = arrays.size();
            }
            break;
        case MAXHEAP:
            // max heap
            while((index+1)*2 < arrays.size() && ((index+1)*2-1) < arrays.size())
            {
                if(arrays[index] < arrays[(index+1)*2 - 1] && arrays[index] < arrays[(index +1)*2])
                {
                    if(arrays[(index+1)*2 - 1] > arrays[(index+1)*2])
                    {
                        int t = arrays[index];
                        arrays[index] = arrays[(index+1)*2 - 1];
                        arrays[(index+1)*2 - 1] = t;
                        index = (index+1)*2 - 1;
                    }
                    else if(arrays[(index+1)*2 - 1] <= arrays[(index+1)*2])
                    {
                        int t = arrays[index];
                        arrays[index] = arrays[(index+1)*2];
                        arrays[(index+1)*2] = t;
                        index = (index+1)*2;
                    }
                }
                else if(arrays[index] < arrays[(index+1)*2 - 1] && arrays[index] >= arrays[(index +1)*2])
                {
                    int t = arrays[index];
                    arrays[index] = arrays[(index+1)*2 - 1];
                    arrays[(index+1)*2 - 1] = t;
                    index = (index+1)*2 - 1;
                }
                else if(arrays[index] >= arrays[(index+1)*2 - 1] && arrays[index] < arrays[(index +1)*2])
                {
                    int t = arrays[index];
                    arrays[index] = arrays[(index+1)*2];
                    arrays[(index+1)*2] = t;
                    index = (index+1)*2;
                }
                else
                  index = arrays.size() + 1;
            }
            break;
        default:
            break;
    }
}
