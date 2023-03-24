public class test_map
{
    public static void main(String[] args) {

        map RBtree = new map();
        RBtree.Add(5, "a");
        RBtree.Add(2, "aa");
        RBtree.Add(7, "ac");
        RBtree.Add(1, "aaa");
        RBtree.IsVoid();
        RBtree.PrintTree();

        RBtree.Search(5);
        RBtree.Search(7);
        RBtree.Search(1);

        RBtree.DeleteAll();
        RBtree.IsVoid();

        RBtree.DeleteAll();
        for (int step = 20; step > 0; step--)
            RBtree.Add(step, "b");
        RBtree.PrintTree();
    }
}
