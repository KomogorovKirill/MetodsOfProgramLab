#include <iostream>
#include <vector>
#include "priority-queue.h"
using namespace std;

int main()
{
	setlocale(LC_ALL, "Russian");
	priority_queue <int, vector, int> pQueue;
	pQueue.insertElement(200);
	pQueue.insertElement(10);
	pQueue.insertElement(50);
	pQueue.insertElement(150);
	pQueue.insertElement(300);
	pQueue.printQueue();
	pQueue.removeElement(300);
	pQueue.printQueue();
	cout << "Пуста ли очередь: " << pQueue.isQueueEmpty() << endl;
	cout << "Максимальный элемент очереди: " << pQueue.getMaxValue() << endl;
	cout << "Колличество элементов в очереди: " << pQueue.getQueueSize() << endl;
	return 0;
}