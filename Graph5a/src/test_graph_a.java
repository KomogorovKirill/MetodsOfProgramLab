public class test_graph_a
{
    public static void main(String[] args)
    {
        System.out.println("Create graph without loops");
        graph Graph = new graph(7);
        Graph.Print();
        System.out.println();

        System.out.println("Depth-first search");
        Graph.DepthFirstSearch();
        System.out.println();

        System.out.println("Breadth-first search");
        Graph.BreadthFirstSearch(0);
        System.out.println();

        System.out.println("Floyd-Warshall algorithm");
        Graph.FloydWarshallAlgorithm();
        System.out.println();

        System.out.println("Dijkstra's algorithm");
        Graph.Dijkstra_sAlgorithm(3);
        System.out.println();

        System.out.println("Prime's algorithm");
        Graph.Prim_sAlgorithm();
        System.out.println();

        System.out.println("Kruskal's algorithm.");
        Graph.Kruskal_sAlgorithm();
        System.out.println();
    }
}
