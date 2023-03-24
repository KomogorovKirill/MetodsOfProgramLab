public class map <T extends Comparable <T>>
{
    public class node <T extends Comparable<T>>
    {
        int key;
        String value;
        String color;
        node<T> parent = null;
        node<T> left = null;
        node<T> right = null;

        node(int key, String value)
        {
            this.key = key;
            this.value = value;
            this.color = "red";
        }
    }
    //вывод текста цветной
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";

    node<T> Null = new node<>(0, null);
    node<T> root = new node<>(-1, null);
    int blackHeight = 0;

    public map()
    {
        root.color = Null.color = "black";
        root.right = root.left = Null;
        root.left.parent = root;
    }

    public void Add(int key, String value){
        if (root.value == null)
        {
            root.key = key;
            root.value = value;
            blackHeight++;
        }
        else
        {
            node <T> current = root;
            while (true)
            {
                if (key < current.key)
                {
                    if (current.left.value == null)
                    {
                        current.left = new node<>(key, value);
                        current.left.parent = current;
                        current.left.right = current.left.left = Null;
                        Balance(current.left);
                        break;
                    }
                    else
                        current = current.left;
                }
                if (key >= current.key)
                {
                    if (key == current.key & current.value.equals(value))
                    {
                        System.out.println("\tThis element already exists: " + key + " " + value);
                        return;
                    }
                    if (current.right.value == null)
                    {
                        current.right = new node<>(key, value);
                        current.right.parent = current;
                        current.right.right = current.right.left = Null;
                        Balance(current.right);
                        break;
                    }
                    else
                        current = current.right;
                }
            }
        }
    }

    public void Balance(node<T> added)
    {
        while (true)
        {
            again:
            {
                if (added.key == root.key & added.value.equals(root.value))
                {
                    root.color = "black";
                    return;
                } else if (added.parent.color.equals("black"))
                    return;
                else if (added.parent.color.equals("red"))
                {
                    if (added.parent.parent != null && added.parent.parent.right.color.equals("red") &&
                            added.parent.parent.left.color.equals("red"))
                    {
                        added.parent.parent.right.color = "black";
                        added.parent.parent.left.color = "black";
                        added.parent.parent.color = "red";

                        added = added.parent.parent;
                        break again;
                    }
                    if (added.parent.left.key == added.key &&
                            added.parent.left.value.equals(added.value))
                    {
                        if (added.parent.parent.left.key == added.parent.key &&
                                added.parent.parent.left.value.equals(added.parent.value))
                        {
                            added = added.parent;
                            node<T> temp;
                            temp = added.parent;
                            if (added.parent.parent != null)
                            {
                                if (added.parent.parent.left.key == added.parent.key && added.parent.parent.left.value.equals(added.parent.value))
                                {
                                    node <T> step = added.parent.parent;
                                    added.parent.parent.left = added;
                                    added.parent = step;
                                }
                                else
                                {
                                    node <T> step = added.parent.parent;
                                    added.parent.parent.right = added;
                                    added.parent = step;
                                }

                                temp.left = added.right;
                                added.right.parent = temp;
                                added.right = temp;
                                added.right.parent = added;
                            }
                            else
                            {
                                root = added;
                                temp.left = added.right;
                                temp.left.parent = temp;
                                added.right = temp;
                                added.right.parent = added;
                                root.parent = null;
                            }

                            added.color = "black";
                            added.right.color = "red";
                            return;
                        }

                        else
                        {
                            node<T> temp;
                            temp = added.parent;
                            if (added.parent.parent != null)
                            {
                                if (added.parent.parent.left.key == added.parent.key && added.parent.parent.left.value.equals(added.parent.value))
                                {
                                    node <T> step = added.parent.parent;
                                    added.parent.parent.left = added;
                                    added.parent = step;
                                }
                                else
                                {
                                    node <T> step = added.parent.parent;
                                    added.parent.parent.right = added;
                                    added.parent = step;
                                }

                                temp.left = added.right;
                                temp.left.parent = temp;
                                added.right = temp;
                                added.right.parent = added;
                                added = added.right;
                            }
                            else
                            {
                                root = added;
                                temp.left = added.right;
                                added.right = temp;
                                added.right.parent = added;
                                root.parent = null;
                            }
                        }

                    }
                    if (added.parent.right.key == added.key &&
                            added.parent.right.value.equals(added.value))
                    {
                        node <T> temp;
                        added = added.parent;
                        temp = added.parent;

                       if (added.parent.parent != null)
                       {
                           if (added.parent.parent.left.key == added.parent.key
                                   && added.parent.parent.left.value.equals(added.parent.value))
                           {
                               node <T> step = added.parent.parent;
                               added.parent.parent.left = added;
                               added.parent = step;
                           }
                           else
                           {
                               node <T> step = added.parent.parent;
                               added.parent.parent.right = added;
                               added.parent = step;
                           }
                       }

                       else
                       {
                           root = added;
                           root.parent = null;
                       }

                        temp.right = added.left;
                        temp.right.parent = temp;
                        added.left = temp;
                        added.left.parent = added;

                        added.color = "black";
                        added.left.color = "red";
                    }
                }
            }
        }
    }

    public void PrintTree()
    {
        if (root.value == null)
            System.out.println("\tThis tree is null!");

        else
        {
            if (root.color.equals("red"))
                System.out.print("\tTree: " + ANSI_RED + root.key + "_(" + root.value + ") " + ANSI_RESET);
            else
                System.out.print("\tTree: " + ANSI_BLACK + root.key + "_(" + root.value + ") " + ANSI_RESET);
            RecursePrint(root);
            System.out.println();
        }
    }

    public void RecursePrint(node <T> Node)
    {
        if (Node.left != null)
        {
            node<T> left = Node.left;
            if (Node.left.color.equals("red"))
                System.out.print(ANSI_RED + Node.left.key + "_(" + Node.left.parent.key + ") " + ANSI_RESET);
            else
                System.out.print(ANSI_BLACK + Node.left.key + "_(" + Node.left.parent.key + ") " + ANSI_RESET);
            RecursePrint(left);
        }
        if (Node.right != null)
        {
            node<T> right = Node.right;
            if (Node.right.color.equals("red"))
                System.out.print(ANSI_RED + Node.right.key + "_(" + Node.right.parent.key + ") " + ANSI_RESET);
            else
                System.out.print(ANSI_BLACK + Node.right.key + "_(" + Node.right.parent.key + ") " + ANSI_RESET);
            RecursePrint(right);
        }
        if (Node.left == null & Node.right == null)
            return;
    }

    public void IsVoid()
    {
        if (root.value == null)
            System.out.println("\tThis tree is null!");
        else
            System.out.println("\tTree is no empty. Use \"PrintTree\".");
    }

    public void DeleteAll()
    {
        RecurseDelete(root);
        root.key = 0;
        root.value = null;
        root.color = Null.color = "black";
        root.right = root.left = Null;
    }

    public void RecurseDelete(node <T> Node)
    {
        if (Node.left != null)
        {
            node<T> left = Node.left;
            Node.left = null;
            System.gc();
            RecurseDelete(left);
        }
        if (Node.right != null)
        {
            node<T> right = Node.right;
            Node.right = null;
            System.gc();
            RecurseDelete(right);
        }
        if (Node.left == null & Node.right == null)
            return;
    }

    public void Search(int key)
    {
        node<T> current = root;
        while (current.value != null)
        {
            if (current.key == key)
            {
                System.out.println("\tElement: " + current.key + ", value: " + current.value);
                return;
            }
            else if (current.key > key)
                current = current.left;

            else
                current = current.right;
        }
        System.out.println("\tElement " + key + " isn't exists.");
    }
}
