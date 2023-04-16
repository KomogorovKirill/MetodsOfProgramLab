import java.util.ArrayList;
import java.util.LinkedList;

public class hash_map
{
    class node
    {
        int key;
        String value;

        node(int key, String value)
        {
            this.key = key;
            this.value = value;
        }

        public String toString()
        {
            return this.key + " " + this.value;
        }

        public boolean equals(Object o)
        {
            if (o == null)
                return false;
            if (o == this)
                return true;

            node Node = (node) o;
            return this.key == Node.key;
        }
    }
    ArrayList<LinkedList<node>> Array ;

    int elements = 0;
    float alpha = 0;
    int arraySize;

    public hash_map(int size){
        arraySize = size;
        Array = new ArrayList<>(arraySize);
        for (int step = 0; step < arraySize; step++)
            Array.add(null);
    }

    public void Add(int key, String value)
    {
        node current = new node(key, value);
        if(Array.get(hashCode(key)%arraySize) == null)
        {
            LinkedList<node> List = new LinkedList<>();
            List.add(current);
            Array.set(hashCode(key)%arraySize, List);
            elements++;
        }

        else if(Array.get(hashCode(key)%arraySize).contains(current))
        {
            int index = Array.get(hashCode(key)%arraySize).indexOf(current);
            Array.get(hashCode(key)%arraySize).set(index, current);
        }

        else
        {
            Array.get(hashCode(key)%arraySize).addLast(current);
            elements++;
        }
        if (Workload() >= 2.0)
            Rehashing(2 * arraySize + 1);
    }

    public void Rehashing(int newSize)
    {
        Array.ensureCapacity(newSize);
        for (int step = arraySize; step < newSize; step++)
            Array.add(null);
        arraySize = newSize;
        elements = 0;
        node current;

        for (int temp = 0; temp < (arraySize-1)/2; temp++)
        {
            int size = Array.get(temp).size();
            for (int step = 0; step < size; step++)
            {
                current = Array.get(temp).remove();
                Add(current.key, current.value);
            }
        }
    }

    public int Search(int key, String print)
    {
        node current = new node(key, null);
        if (Array.get(hashCode(key) % arraySize) == null ||
                !Array.get(hashCode(key) % arraySize).contains(current))
        {
            if (print.equals("print"))
                System.out.println("\tElement with key " + key + " isn't exists.");
            return -1;
        }
        else
        {
            int result = Array.get(hashCode(key) % arraySize).indexOf(current);
            current = Array.get(hashCode(key) % arraySize).get(result);
            if (print.equals("print"))
                System.out.println("\tElement: " + current.key + ", value: " + current.value);
            return result;
        }
    }

    public void Change(int key, String newValue)
    {
        if (Search(key, "null") == -1)
            System.out.println("\tChange impossible. Element with key " + key + " isn't exists.");
        else
        {
            node current = new node(key, newValue);
            Array.get(hashCode(key) % arraySize).set(Search(key, "null"), current);
        }
    }

    public void DeleteAll()
    {
        Array.clear();
        elements = 0;
        arraySize = 0;
    }

    public void DeleteElement(int key)
    {
        if (Search(key, "null") == -1)
            System.out.println("\tDelete impossible. Element with key " + key + " isn't exists.");
        else
        {
            Array.get(hashCode(key) % arraySize).remove(Search(key, "null"));
            elements--;
        }
    }

    public void IsVoid()
    {
        if (elements == 0)
            System.out.print("\tThis map is empty.");
        else
            System.out.print("\tThis map is no empty. Use \"PrintMap\" method.");

        System.out.println( "\n\tThis map size: " + arraySize + "\n\tThe number of elements: "
                + elements + "\n\tMap load coefficient: " + Workload());
    }

    public void PrintMap()
    {
        if (elements == 0)
        {
            System.out.println("\tThis map is empty.");
            return;
        }

        for (LinkedList<node> nodes : Array)
        {
            System.out.println("\t" + nodes);
        }
    }

    public float Workload()
    {
        if (elements == 0)
            alpha = 0.0F;
        else
            alpha = (float) elements/arraySize;
        return alpha;
    }

    public int hashCode(int key)
    {
        return key;
    }
}