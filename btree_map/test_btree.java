public class test_btree
{
    public static void main(String[] args)
    {
        BtreeMap Map = new BtreeMap(3);
        Map.Add(7, "hi");
        Map.Add(52, "book");
        Map.Add(21, "age");
        Map.Add(76, "tea");
        Map.PrintMap();
        Map.Search(7);
        Map.DeleteAll();
    }
}