#include "souce.h"
#include "bubble.h"

int main()
{
	int num[] = { 23, 14, 122, 34, 31, 45, 453, 76, 49, 85, 32, 128, 392, 243, 563, 293, 275, 364, 1938, 2832};
	vector<int> NumToSort(num,num + sizeof(num)/sizeof(int));

	long tstart, tend;
	long bstart, bend;
	
	srand(time(NULL));
	for (int i = 0; i < 20000; i++)
	{
		NumToSort.push_back(rand() % 10000);
	}
	
	vector<int> bub(NumToSort);

	for_each(NumToSort.begin(), NumToSort.end(), print<int>);
	cout << endl;
	
	tstart = clock();
	cout << "Merge Sort starts..." << endl;
	sortMerge(NumToSort,0,NumToSort.size()-1);
	tend = clock();
	for_each(NumToSort.begin(), NumToSort.end(), print<int>);
	
	bstart = clock();
	cout << "\nBubble sort starts..." << endl;
	bubbleSort(bub);
	bend = clock();
	for_each(bub.begin(), bub.end(), print<int>);
	
	
	cout << "\n\n \t\tTe total run time of mergesort is : " << (tend - tstart) * 1000 / CLOCKS_PER_SEC << endl;
	cout << " \t\tTe total run time of bubblesort is : " << (bend - bstart) * 1000 / CLOCKS_PER_SEC << endl;
	return 0;
}
