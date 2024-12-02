import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day2 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/Day2.txt"))) {
//            System.out.println(part1(reader));
            System.out.println(part2(reader));
        }
    }

    public static int part1(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int counter = 0;

        while (line != null) {
            // Splitting the input line by whitespace
            ArrayList<String> nums = new ArrayList<String>(Arrays.asList(line.split(" ")));

            if (isSafe(nums)) counter++;

            line = reader.readLine();
        }

        return counter;
    }

    public static int part2(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int counter = 0;

        while (line != null) {
            ArrayList<String> nums = new ArrayList<String>(Arrays.asList(line.split(" ")));

            if (isSafe(nums)) {
                counter++;
            } else {
                // If the report is not safe, remove elements one by one until it is safe (or never is safe).
                // Possible optimization would be to modify the isSafe() function to immediately remove an element if it's the factor that makes a report safe.
                for (int i = 0; i < nums.size(); i++) {
                    ArrayList<String> numsCopy = new ArrayList<String>(nums);
                    numsCopy.remove(i);

                    if (isSafe(numsCopy)) {
                        counter++;
                        break;
                    }
                }
            }

            line = reader.readLine();
        }

        return counter;
    }


    public static boolean isSafe(ArrayList<String> list) {
        boolean ok = true;
        boolean descending = false;
        boolean ascending = false;

        int p = Integer.parseInt(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            int n = Integer.parseInt(list.get(i));

            int diff = Math.abs(n - p);

            // If the difference between them isn't 1-3, it's not a valid report.
            if (diff < 1 || diff > 3) {
                ok = false;
                break;
            }

            // If the two elements lead to an ascending order but the report was previously descending, it's not a safe report.
            if (n - p > 0) {
                if (descending) {
                    ok = false;
                    break;
                } else {
                    ascending = true;
                }
                // - || -
            } else {
                if (ascending) {
                    ok = false;
                    break;
                } else {
                    descending = true;
                }
            }

            p = n;
        }

        return ok;
    }
}
