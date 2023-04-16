public class test_hash_map
{
    public static void main(String[] args)
    {
        hash_map Map = new hash_map(4);
        Map.Add(1, "aaa");
        Map.Add(2, "aaa");
        Map.Add(3, "aa");
        Map.Add(4, "a");
        Map.IsVoid();
        Map.PrintMap();
        Map.DeleteAll();
        Map.IsVoid();

    }
}
