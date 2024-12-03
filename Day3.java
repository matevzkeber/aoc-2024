import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    // Thank you, ChatGPT, I'm not writing these patterns.
    static final String MUL_REGEX = "mul\\(\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\)";
    static final String DO_REGEX = "do\\(\\)";
    static final String DONT_REGEX = "don't\\(\\)";

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/Day3.txt"))) {
            // Save the input in a list to allow both parts to use it in one run.
            List<String> lines;
            lines = reader.lines().toList();

            System.out.println("Part 1: " + part1(lines));
            System.out.println("Part 2: " + part2(lines));

        }
    }

    public static int part1(List<String> lines) throws IOException {
        return process(lines, false);
    }

    public static int part2(List<String> lines) throws IOException {
        return process(lines, true);
    }


    public static int process(List<String> lines, boolean useEnablers) {
        int sum = 0;
        // At the beginning the multiplication process is enabled.
        boolean enabled = true;

        for (String line : lines) {
            // Sorted instructions based on their starting indexes.
            TreeMap<Integer, Instruction> instructions = new TreeMap<Integer, Instruction>();
            // If it's a mul(), we combine the 2 groups that are defined in the regex into a Mul object.
            parseInstructions(Pattern.compile(MUL_REGEX), line, instructions, match -> new Mul(Integer.parseInt(match.group(1)), Integer.parseInt(match.group(2))));
            // Do and don't share the same type with a property "enabled".
            parseInstructions(Pattern.compile(DO_REGEX), line, instructions, _ -> new Enabler(true));
            parseInstructions(Pattern.compile(DONT_REGEX), line, instructions, _ -> new Enabler(false));

            for (Instruction instruction : instructions.values()) {
                if (enabled && instruction instanceof Mul mul) {
                    sum += mul.result();
                // If enablers are not enabled (part 1), the enabled state of the sequence will remain the same, otherwise it's set to the state of the current enabler instruction.
                } else if (useEnablers && instruction instanceof Enabler enabler) {
                    enabled = enabler.enabled;
                }
            }
        }

        return sum;
    }

    // Helper function that parses the instructions given a conversion function.
    public static void parseInstructions(Pattern pattern, String line, TreeMap<Integer, Instruction> instructions, Function<Matcher, Instruction> conversion) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            instructions.put(matcher.start(), conversion.apply(matcher));
        }
    }


    static abstract class Instruction {
    }

    static class Enabler extends Instruction {
        boolean enabled;

        public Enabler(boolean enabled) {
            this.enabled = enabled;
        }
    }

    static class Mul extends Instruction {
        int a;
        int b;

        public Mul(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int result() {
            return this.a * this.b;
        }
    }
}