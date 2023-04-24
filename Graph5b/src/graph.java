import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class graph
{
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    int nodes;
    int NumberOfRibs = 0;
    int[][] matrix;
    ArrayList<Integer> result = new ArrayList<>(0);
    ArrayList<LinkedList<Integer>> peaks;

    int[][] matrix1 = new int[][]{{0, 1, 1, 0, 0, 0, 0}, {1, 0, 0, 1, 1, 0, 0}, {1, 0, 0, 1, 0, 1, 1}, {0, 1, 1, 0, 1, 0, 0},
            {0, 1, 0, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0}};
    int[][] matrix2 = new int[][]{{0, 1, 2, 4, 0, 0, 0}, {1, 0, 0, 2, 5, 0, 0}, {2, 0, 0, 7, 0, 4, 1}, {4, 2, 7, 0, 1, 0, 1},
            {0, 5, 0, 1, 0, 0, 0}, {0, 0, 4, 0, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 0}};

    public graph(int size)
    {
        if (size < 2)
        {
            System.out.println("\tSize can't be less 2!");
            java.lang.System.exit(-1);
        }
        nodes = size;

        matrix = new int[][]{{0, 2, 1, 4, 0, 6, 0}, {2, 0, 0, 1, 3, 0, 0}, {1, 0, 0, 2, 0, 4, 0}, {4, 1, 2, 0, 0, 0, 1},
                {0, 3, 0, 0, 0, 0, 4}, {6, 0, 4, 0, 0, 0, 7}, {0, 0, 0, 1, 4, 7, 0}};

        peaks = new ArrayList<>(nodes);

        for (int step = 0; step < nodes; step++)
        {
            LinkedList<Integer> list = new LinkedList<>();
            for (int temp = 0; temp < nodes; temp++)
            {
                if (matrix[step][temp] != 0)
                {
                    list.add(temp);
                    NumberOfRibs++;
                }
            }
            peaks.add(step, list);
        }
        NumberOfRibs /= 2;
    }

    public void RandomGraph()
    {
        NumberOfRibs = 0;
        matrix = new int[nodes][nodes];
        for (int step = 0; step < nodes; step++)
            for (int temp = 1+step; temp < nodes; temp++)
            {
                matrix[step][temp] = (int) (Math.random() * 10);
                matrix[temp][step] = matrix[step][temp];
            }

        peaks = new ArrayList<>(nodes);

        for (int step = 0; step < nodes; step++)
        {
            LinkedList<Integer> list = new LinkedList<>();
            for (int temp = 0; temp < nodes; temp++)
            {
                if (matrix[step][temp] != 0)
                {
                    list.add(temp);
                    NumberOfRibs++;
                }
            }
            peaks.add(step, list);
        }
        NumberOfRibs /= 2;
    }

    public void DepthFirstSearch()
    {
        DFS(0);
        System.out.println("\t" + result);
        result.clear();
    }

    public void DFS(int step)
    {
        result.add(step);
        for (int temp = 0; temp < peaks.get(step).size(); temp++)
        {
            if (!result.contains(peaks.get(step).get(temp)))
                DFS(peaks.get(step).get(temp));
        }

    }

    public void BreadthFirstSearch(int begin)
    {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visit = new boolean[nodes];
        visit[begin] = true;
        queue.add(begin);

        while (queue.size() != 0)
        {
            int current = queue.pollFirst();
            result.add(current);
            for (int temp = 0; temp < peaks.get(current).size(); temp++)
                if (!visit[peaks.get(current).get(temp)])
                {
                    queue.add(peaks.get(current).get(temp));
                    visit[peaks.get(current).get(temp)] = true;
                }
        }
        System.out.println("\t" + result);
        result.clear();
    }

    public void Dijkstra_sAlgorithm(int begin)
    {
        System.out.println("\tDistance from peak " + begin);
        System.out.print("\t" + ANSI_GREEN);
        for (int step = 0; step < nodes; step++)
            System.out.print(step + " ");
        System.out.println(ANSI_RESET);

        boolean[] visit = new boolean[nodes];
        boolean[] check = new boolean[nodes];
        Arrays.fill(check, true);
        double[] distances = new double[nodes];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[begin] = 0;

        while(!Arrays.equals(visit, check))
        {
            int current = CurrentSearch(distances, visit);
            visit[current] = true;
            for (int temp = 0; temp < nodes; temp++)
            {
                if(matrix[current][temp] != 0 & distances[temp] > distances[current] + matrix[current][temp])
                    distances[temp] = distances[current] + matrix[current][temp];
            }
        }

        System.out.print("\t");
        for (int temp = 0; temp < nodes; temp++)
        {
            System.out.print(distances[temp] + "\b\b ");
        }
    }

    public int CurrentSearch(double[] distances, boolean[] visit)
    {
        double min = Double.POSITIVE_INFINITY;
        int current = 0;

        for (int temp = 0; temp < nodes; temp++)
        {
            if (distances[temp] < min & !visit[temp])
            {
                min = distances[temp];
                current = temp;
            }
        }
        return current;
    }

    public void Prim_sAlgorithm()
    {
        ArrayList<LinkedList<Integer>> result = new ArrayList<>(nodes);
        for (int step = 0; step < nodes; step++)
        {
            LinkedList<Integer> list = new LinkedList<>();
            result.add(step, list);
        }

        boolean[] visit = new boolean[nodes];
        boolean[] check = new boolean[nodes];
        Arrays.fill(check, true);
        int[] parents = new int[nodes];
        double[] distances = new double[nodes];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[0] = 0;

        while(!Arrays.equals(visit, check))
        {
            int current = CurrentSearch(distances, visit);
            visit[current] = true;
            for (int temp = 0; temp < nodes; temp++)
            {
                if(matrix[current][temp] != 0 & distances[temp] > matrix[current][temp] & !visit[temp])
                {
                    distances[temp] = matrix[current][temp];
                    parents[temp] = current;
                }
            }
        }

        for (int temp = 0; temp < nodes; temp++)
        {
            if (temp != parents[temp])
            {
                result.get(temp).add(parents[temp]);
                result.get(parents[temp]).add(temp);
            }

        }

        int temp = 0;
        for (LinkedList<Integer> integers : result)
        {
            System.out.println("\t" + ANSI_GREEN + temp + " " + ANSI_RESET + integers);
            temp++;
        }
    }

    public void Kruskal_sAlgorithm()
    {
        ArrayList<LinkedList<Integer>> result = new ArrayList<>(nodes);
        int[] vertex = new int[nodes];
        for (int step = 0; step < nodes; step++)
        {
            LinkedList<Integer> list = new LinkedList<>();
            result.add(step, list);
            vertex[step] = step;
        }
        int[][] ribs = new int[NumberOfRibs][3];
        Sort(ribs);

        for (int step = 0; step < NumberOfRibs; step++)
        {
            if (vertex[ribs[step][0]] != vertex[ribs[step][1]])
            {
                result.get(ribs[step][0]).add(ribs[step][1]);
                result.get(ribs[step][1]).add(ribs[step][0]);
                int temp = vertex[ribs[step][0]];
                for (int stage = 0; stage < nodes; stage++)
                {
                    if (vertex[stage] == temp)
                        vertex[stage] = vertex[ribs[step][1]];
                }
            }
        }

        int temp = 0;
        for (LinkedList<Integer> integers : result)
        {
            System.out.println("\t" + ANSI_GREEN + temp + " " + ANSI_RESET + integers);
            temp++;
        }
    }

    public void Sort(int[][] ribs)
    {
        int stage = 0;

        for (int step = 0; step < nodes; step++)
            for (int temp = 1+step; temp < nodes; temp++)
            {
                if (matrix[step][temp] != 0)
                {
                    ribs[stage][0] = step;
                    ribs[stage][1] = temp;
                    ribs[stage][2] = matrix[step][temp];
                    stage++;
                }
            }


        int minimum = Integer.MAX_VALUE;
        int buf1 = 0;
        int buf0 = 0;
        int move = 0;
        int selection = 0;
        while (move != NumberOfRibs)
        {
            for (int step = move; step < NumberOfRibs+1 && step < NumberOfRibs; step++)
            {
                if (ribs[step][2] < minimum)
                {
                    minimum = ribs[step][2];
                    buf1 = ribs[step][1];
                    buf0 = ribs[step][0];
                    selection = step;
                }
            }
            ribs[selection][2] = ribs[move][2];
            ribs[selection][1] = ribs[move][1];
            ribs[selection][0] = ribs[move][0];
            ribs[move][2] = minimum;
            ribs[move][1] = buf1;
            ribs[move][0] = buf0;
            minimum = Integer.MAX_VALUE;
            move++;
        }
    }

    public void FloydWarshallAlgorithm()
    {
        double[][] newMatrix = new double[nodes][nodes];

        for (int step = 0; step < nodes; step++)
            for (int temp = 1+step; temp < nodes; temp++)
            {
                newMatrix[step][temp] = matrix[step][temp];
                newMatrix[temp][step] = matrix[step][temp];
                if(newMatrix[step][temp] == 0 & step != temp)
                {
                    newMatrix[step][temp] = Double.POSITIVE_INFINITY;
                    newMatrix[temp][step] = Double.POSITIVE_INFINITY;
                }
            }

        System.out.println("\tThe initial state:");

        System.out.print("\n\t   " + ANSI_GREEN);
        for (int step = 0; step < nodes; step++)
            System.out.print(step + "  ");

        for (int step = 0; step < nodes; step++)
        {
            System.out.print("\n\t" + ANSI_GREEN + step + "  " + ANSI_RESET);
            for (int temp = 0; temp < nodes; temp++)
            {
                if (matrix[step][temp] == 0 & step != temp)
                    System.out.print("I  ");
                else
                    System.out.print(matrix[step][temp] + "  ");
            }

        }
        System.out.println("\n");

        for (int stage = 0; stage < nodes; stage++)
            for (int step = 0; step < nodes; step++)
                for (int temp = 0; temp < nodes; temp++)
                    newMatrix[step][temp] = Math.min(newMatrix[step][temp], newMatrix[step][stage] + newMatrix[stage][temp]);

        System.out.println("\tShortest distances:");

        System.out.print("\n\t   " + ANSI_GREEN);
        for (int step = 0; step < nodes; step++)
            System.out.print(step + "  ");

        for (int step = 0; step < nodes; step++)
        {
            System.out.print("\n\t" + ANSI_GREEN + step + "  " + ANSI_RESET);
            for (int temp = 0; temp < nodes; temp++)
                System.out.print(newMatrix[step][temp] + "\b\b  ");
        }
        System.out.println("\n");
    }

    public void Print()
    {
        System.out.print("\n\t   " + ANSI_GREEN);
        for (int step = 0; step < nodes; step++)
            System.out.print(step + "  ");

        for (int step = 0; step < nodes; step++)
        {
            System.out.print("\n\t" + ANSI_GREEN + step + "  " );
            for (int temp = 0; temp < nodes; temp++)
                System.out.print(ANSI_RESET + matrix[step][temp] + "  ");
        }
        System.out.println("\n");

        int temp = 0;
        for (LinkedList<Integer> peak : peaks)
        {
            System.out.println("\t" + ANSI_GREEN + temp + " " + ANSI_RESET + peak);
            temp++;
        }
    }
}