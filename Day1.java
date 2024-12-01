import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Day1 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/Day1.txt"))) {
//            System.out.println(part1(reader));
            System.out.println(part2(reader));
        }
    }

    public static int part1(BufferedReader reader) throws IOException {
        int sum = 0;
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        // Parsing
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(" {3}");

            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));

            line = reader.readLine();
        }

        // Sorting the arrays by ascending
        Collections.sort(left);
        Collections.sort(right);

        // Calculating the distances between the elements.
        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(left.get(i) - right.get(i));
        }

        return sum;
    }

    public static int part2(BufferedReader reader) throws IOException {
        int sum = 0;
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        // Parsing
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(" {3}");

            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));

            line = reader.readLine();
        }

        // For each number on the left, find n-appearances on the right.
        for (int i = 0; i < left.size(); i++) {
            int target = left.get(i);
            int n = 0;
            for (int j = 0; j < right.size(); j++) {
                int current = right.get(j);
                if (current == target) n++;
            }

            sum += target * n;
        }

        return sum;
    }
}
