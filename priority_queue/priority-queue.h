#pragma once

using namespace std;

template<typename T, template <class> class Container = vector, class U = int>
class priority_queue
{
private:
	Container<U> hT;
	void heapify(T i)
	{
		U tmp;
		int size = hT.size();
		T largest = i;
		T left = 2 * i + 1;
		T right = 2 * i + 2;
		if (left < size && hT[left] > hT[largest])
			largest = left;
		if (right < size && hT[right] > hT[largest])
			largest = right;

		if (largest != i)
		{
			tmp = hT[largest];
			hT[largest] = hT[i];
			hT[i] = tmp;
			heapify(largest);
		}
	}

public:
	void insertElement(T newNum)
	{
		int size = hT.size();
		if (0 == size)
		{
			hT.push_back(newNum);
		}
		else
		{
			hT.push_back(newNum);
			for (int i = size / 2 - 1; i >= 0; i--)
				heapify(i);
		}
	}

	void removeElement(T num)
	{
		U tmp;
		int size = hT.size(), i = 0;
		for (; i < size; i++)
			if (num == hT[i])
				break;
		tmp = hT[size - 1];
		hT[size - 1] = hT[i];
		hT[i] = tmp;
		hT.pop_back();
		for (i = size / 2 - 1; i >= 0; i--)
			heapify(i);
	}

	bool isQueueEmpty()
	{
		return hT.size() == 0;
	}

	U getMaxValue()
	{
		return hT[0];
	}

	int getQueueSize()
	{
		return hT.size();
	}

	void printQueue()
	{
		for (int i = 0; i < hT.size(); i++)
			cout << hT[i] << " ";
		cout << endl;
	}
};