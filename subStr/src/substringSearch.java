import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class substringSearch
{
    public String string ;
    public String substring;
    int simple = 101;


    public substringSearch()
    {
        string = "abaabababaabca";
        substring = "abab";
    }


    public void getString()
    {
        System.out.print("\tInput your string: ");
        Scanner in = new Scanner(System.in);
        string = in.nextLine();
        System.out.print("\tInput substring: ");
        substring = in.nextLine();
        in.close();

        if (substring.length() > string.length())
        {
            System.out.println("\tSubstring can't be longer than string, enter the data again.");
            getString();
        }
    }

    public void Rabin_Karp()
    {
        string = "123463214576363";
        substring = "63";

        int stringLength = string.length();
        int substringLength = substring.length();
        int alphPower = 10;
        int multiplier = (int) (Math.pow(alphPower, substringLength-1) % simple);
        int hash = 0;
        int newHash = 0;
        LinkedList<Integer> result = new LinkedList<>();

        for (int temp = 0; temp < substringLength; temp++)
        {
            hash = (alphPower * hash) + Integer.parseInt(substring.substring(temp, temp+1)) % simple;
            newHash = (alphPower * newHash) + Integer.parseInt(string.substring(temp, temp+1)) % simple;
        }

        for (int step = 0; step < stringLength - substringLength + 1; step++)
        {
            if(hash == newHash)
                if (substring.equals(string.substring(step, step+substringLength)))
                    result.add(step);

            if (step < stringLength - substringLength)
                newHash = (alphPower * (newHash - multiplier * Integer.parseInt(string.substring(step, step + 1)))
                        + Integer.parseInt(string.substring(step + substringLength, step + substringLength+1))) % simple;
        }

        System.out.println("\tString: " + string + "\n\tSubstring: " + substring);

        if (result.size() == 0)
            System.out.println("\tSubstring not found.");
        else
            System.out.println("\tSubstring position(s): " + result);

    }

    public void FSM()
    {
        String[] alphabet = alphabet().toArray(new String[0]);
        int[][] table = new int[substring.length()+1][alphabet.length];
        LinkedList<Integer> result = new LinkedList<>();

        suffix(table, alphabet);
        int status = 0;

        for (int temp = 0; temp < string.length(); temp++)
        {
            for (int step = 0; step < alphabet.length; step++)
                if (alphabet[step].equals(string.substring(temp, temp+1)))
                    status = table[status][step];

            if (status == substring.length())
                result.add(temp-substring.length()+1);
        }

        System.out.println("\n\n\tString: " + string + "\n\tSubstring: " + substring);

        if (result.size() == 0)
            System.out.println("\tSubstring not found.");
        else
            System.out.println("\tSubstring position(s): " + result);

        System.out.println();
    }

    public void suffix(int[][] table, String[] alphabet)
    {
        StringBuilder model = new StringBuilder();
        String letter = "";

        for (int step = 0; step < substring.length()+1; step++)
        {
            for (int temp = 0; temp < alphabet.length; temp++)
            {
                table[step][temp] = 0;

                for (int stage = model.length(); stage > 0; stage--)
                    if ((model+alphabet[temp]).substring(stage).equals(substring.substring(0, model.length()-stage+1)))
                        table[step][temp] = model.length()-stage+1;

                if (substring.substring(0, (step+1)%(substring.length()+1)).equals(model + alphabet[temp]))
                {
                    table[step][temp] = (model + alphabet[temp]).length();
                    letter = alphabet[temp];
                }
            }
            model.append(letter);
        }

        System.out.print("\t   ");
        for (String s : alphabet)
            System.out.print(s + " ");

        for (int step = 0; step < substring.length()+1; step++)
        {
            System.out.print("\n\t" + step + "  ");
            for (int temp = 0; temp < alphabet.length; temp ++)
                System.out.print(table[step][temp] + " ");
        }
    }

    public ArrayList<String> alphabet()
    {
        ArrayList<String> alphabet = new ArrayList<>();

        for (int step = 0; step < string.length(); step++)
            if (!alphabet.contains(string.substring(step, step+1)))
                alphabet.add(string.substring(step, step+1));

        for (int step = 0; step < substring.length(); step++)
            if (!alphabet.contains(substring.substring(step, step+1)))
                alphabet.add(substring.substring(step, step+1));

        return alphabet;
    }

    public void Boyer_Moore()
    {
        String[] alphabet = alphabet().toArray(new String[0]);
        LinkedList<Integer> result = new LinkedList<>();
        int[] stop_table = new int[alphabet.length];

        for (int step = 0; step < stop_table.length; step++)
        {
            stop_table[step] = -1;
            for (int temp = 0; temp < substring.length()-1; temp++)
                if (alphabet[step].equals(substring.substring(temp, temp+1)))
                    stop_table[step] = temp;
        }

        int[] suffix_table = suffix_tab(substring);
        int delta_stop = 0;
        int delta_suff = 0;

        for (int temp = 0; temp < string.length()-substring.length()+1; temp += Math.max(delta_stop, delta_suff))
        {
            int step = substring.length()-1;

            while (step >= 0 && substring.substring(step, step+1).equals(string.substring(temp+step, temp+step+1)))
                step --;

            if (step == -1)
            {
                result.add(temp);
                delta_stop = 1;
            }
            else
            {
                for (int stage = 0; stage < alphabet.length; stage++)
                    if (string.substring(step+temp, step+temp+1).equals(alphabet[stage]))
                        delta_stop = step - stop_table[stage];
            }

            delta_suff = suffix_table[step+1];

        }

        System.out.println("\tString: " + string + "\n\tSubstring: " + substring);

        if (result.size() == 0)
            System.out.println("\tSubstring not found.");
        else
            System.out.println("\tSubstring position(s): " + result);
        System.out.println();
    }

    public int[] suffix_tab(String substring)
    {
        int sub_len = substring.length();
        int[] table = new int[sub_len+1];
        int[] pref = prefix(substring);
        String inverse = "";

        for (int step = sub_len; step > 0; step--)
            inverse += substring.substring(step-1, step);
        int[] pref_inverse = prefix(inverse);

        for (int step = 0; step < sub_len+1; step++)
            table[step] = sub_len - pref[sub_len-1];

        for (int step = 0; step < sub_len; step++)
        {
            int index = sub_len - pref_inverse[step];
            int shift = step - pref_inverse[step] + 1;
            if (table[index] > shift)
                table[index] = shift;
        }

        return table;
    }

    public int[] prefix(String model)
    {
        int[] pref = new int[model.length()];
        pref[0] = 0;
        int temp = 0;

        for (int step = 1; step < model.length(); step++)
        {
            while (temp > 0 & !model.substring(temp, temp+1).equals(model.substring(step, step+1)))
                temp = pref[temp-1];

            if (model.substring(temp, temp+1).equals(model.substring(step, step+1)))
                temp++;

            pref[step] = temp;
        }
        return pref;
    }

    public void Knuth_Morris_Pratt()
    {
        LinkedList<Integer> result = new LinkedList<>();
        int[] pref = prefix(substring);
        int temp = 0;

        for (int step = 0; step < string.length(); step++)
        {
            while (temp > 0 & !substring.substring(temp, temp+1).equals(string.substring(step, step+1)))
                temp = pref[temp-1];

            if (substring.substring(temp, temp+1).equals(string.substring(step, step+1)))
                temp++;

            if (temp == substring.length())
            {
                result.add(step-substring.length()+1);
                temp = pref[temp-1];
            }
        }

        System.out.println("\tString: " + string + "\n\tSubstring: " + substring);

        if (result.size() == 0)
            System.out.println("\tSubstring not found.");
        else
            System.out.println("\tSubstring position(s): " + result);
        System.out.println();
    }
}