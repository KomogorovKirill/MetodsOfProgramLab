import java.util.ArrayList;
import java.util.LinkedList;

public class BtreeMap
{
    class node <E>
    {
        ArrayList<Integer> keys;
        ArrayList<String> values;
        ArrayList<E> sons;
        node<E> parent;

        public node()
        {
            keys = new ArrayList<>(1);
            values = new ArrayList<>(1);
            sons = new ArrayList<>(1);
            sons.add(0, null);
            parent = null;
        }
    }
    node root;
    int numberOfElements = 0;
    private final int t;

    public BtreeMap(int size)
    {
        if(size <= 1)
        {
            System.out.println("Parameter t can't be less 2!");
            root = null;
            java.lang.System.exit(0);
        }
        root = new node<>();
        root.parent = null;
        t = size;
    }

    public void Add(int key, String value)
    {
        node current = root;
        int temp = 0;

        if (root.values.size() == 0)
        {
            current.keys.add(key);
            current.values.add(value);
            numberOfElements++;
            return;
        }

        while (true)
        {
            if (current.keys.size() == 2*t-1)
                current = Partition(current, temp);

            for (int position = 0; position < current.keys.size(); position++)
            {
                if (key == (int) current.keys.get(position))
                {
                    current.keys.set(position, key);
                    current.values.set(position, value);
                    return;
                }

                else if (key < (int) current.keys.get(position))
                {
                    if (current.sons.get(0) != null)
                    {
                        current = (node) current.sons.get(position);
                        temp = position;
                        break;
                    }

                    current.keys.add(position, key);
                    current.values.add(position, value);
                    numberOfElements++;
                    return;
                }

                else if (position == current.keys.size()-1)
                {
                    if (current.sons.get(0) != null)
                    {
                        current = (node) current.sons.get(position+1);
                        temp = position+1;
                        break;
                    }
                    current.keys.add(position+1, key);
                    current.values.add(position+1, value);
                    numberOfElements++;
                    return;
                }
            }
        }
    }

    public node Partition(node Node, int position)
    {
        node current;

        if (Node == root)
        {
            current = new node<>();
            position = 0;
        }
        else
            current = Node.parent;

        current.keys.add(position, Node.keys.get((2*t-1)/2));
        current.values.add(position, Node.values.get((2*t-1)/2));

        node firstHalf = new node<>();
        firstHalf.keys = new ArrayList <Integer> (Node.keys.subList(0, (2*t-1)/2));
        firstHalf.values = new ArrayList<String> (Node.values.subList(0, (2*t-1)/2));

        if (Node.sons.get(0) != null)
        {
            firstHalf.sons = new ArrayList (Node.sons.subList(0, (2*t)/2));
            node temp;
            for (int step = 0; step < firstHalf.sons.size(); step++)
            {
                temp = (node) firstHalf.sons.get(step);
                if (temp == null)
                    break;
                temp.parent = firstHalf;
            }
        }

        node secondHalf = new node<>();
        secondHalf.keys = new ArrayList <Integer> (Node.keys.subList((2*t-1)/2+1, 2*t-1));
        secondHalf.values = new ArrayList<String> (Node.values.subList((2*t-1)/2+1, 2*t-1));

        if (Node.sons.get(0) != null)
        {
            secondHalf.sons = new ArrayList (Node.sons.subList((2*t)/2, 2*t));
            node temp;
            for (int step = 0; step < secondHalf.sons.size(); step++)
            {
                temp = (node) secondHalf.sons.get(step);
                if (temp == null)
                    break;
                temp.parent = secondHalf;
            }
        }

        current.sons.set(position, firstHalf);
        current.sons.add(position+1, secondHalf);
        firstHalf.parent = current;
        secondHalf.parent = current;

        if (Node == root)
            root = current;

        return current;
    }

    public void IsVoid()
    {
        if (root.values.size() == 0)
            System.out.println("B-tree map is void.");
        else
            System.out.println("B-tree map isn't void. Use 'PrintMap' method.");
    }

    public void DeleteAll()
    {
        root.keys = new ArrayList<>(1);
        root.values = new ArrayList<>(1);
        root.sons = new ArrayList<>(1);
        root.sons.add(0, null);
        numberOfElements = 0;
    }

    public void PrintMap()
    {
        if (root.values.size() == 0)
        {
            System.out.println("B-tree map is void.");
            return;
        }

        node current;
        LinkedList<node> list = new LinkedList<>();

        System.out.print("\t" + root.keys.toString() + " ");
        System.out.print(root.values.toString() + "\n\t");

        for (int temp = 0; temp < root.sons.size(); temp++)
        {
            if (root.sons.get(temp) == null)
                break;
            list.addLast((node)root.sons.get(temp));
        }
        int step = list.size();

        while (true)
        {
            current = list.pollFirst();
            step--;
            if (current == null)
                break;

            System.out.print(current.keys.toString() + " ");
            System.out.print(current.values.toString() + " ");
            for (int temp = 0; temp < current.sons.size(); temp++)
            {
                if (current.sons.get(temp) == null)
                    break;
                list.addLast((node)current.sons.get(temp));
            }
            if (step == 0)
            {
                step = list.size();
                System.out.println();
            }
        }
    }

    public void Search(int key)
    {
        node current = root;
        int temp = 0;

        while (true)
        {
            if (key == (int)current.keys.get(temp))
            {
                System.out.println("Element: " + key + " value: " + current.values.get(temp));
                return;
            }

            else if (key < (int)current.keys.get(temp))
            {
                if (current.sons.size() >= temp && current.sons.get(temp) != null)
                {
                    current = (node) current.sons.get(temp);
                    temp = -1;
                }
                else
                {
                    System.out.println("Element with key " + key + " isn't exists.");
                    return;
                }
            }

            else if (key > (int)current.keys.get(current.keys.size()-1))
            {
                if (current.sons.get(temp+2) != null)
                {
                    current = (node) current.sons.get(temp+2);
                    temp = -1;
                }
                else
                {
                    System.out.println("Element with key " + key + " isn't exists.");
                    return;
                }
            }
            temp++;
        }
    }

    public int Height()
    {
        node current = root;
        int height = 1;

        while (current.sons.get(0) != null)
        {
            current = (node) current.sons.get(0);
            height++;
        }
        return height;
    }
}