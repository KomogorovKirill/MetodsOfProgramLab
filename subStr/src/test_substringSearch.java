public class test_substringSearch
{
    public static void main(String[] args)
    {
        System.out.println("String selection");
        substringSearch Algorithm = new substringSearch();
        System.out.println();

        System.out.println("Rabin-Karp algorithm");
        Algorithm.Rabin_Karp();
        System.out.println();

        System.out.println("Algorithm based on a finite state machine");
        Algorithm.string = "aaabcdbbaaaaa";
        Algorithm.substring = "aaa";
        Algorithm.FSM();
        System.out.println();

        System.out.println("Boyer-Moore algorithm");
        Algorithm.string = "aaabcdbbaaaaa";
        Algorithm.substring = "aaa";
        Algorithm.Boyer_Moore();
        System.out.println();

        System.out.println("Knuth-Morris-Pratt algorithm");
        Algorithm.string = "aaabcdbbaaaaa";
        Algorithm.substring = "aaa";
        Algorithm.Knuth_Morris_Pratt();
    }
}