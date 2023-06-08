import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StringSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the source file name: ");
        String fileName = scanner.nextLine();

        System.out.print("Enter the pattern to search: ");
        String pattern = scanner.nextLine();

        System.out.println("Choose the algorithm to use:");
        System.out.println("1. Knuth-Morris-Pratt Algorithm");
        System.out.println("2. Boyer-Moore Algorithm");
        System.out.println("3. Sequential Search");

        int algorithmChoice = scanner.nextInt();

        try {
            String text = readFile(fileName);
            int position = -1;
            switch (algorithmChoice) {
                case 1:
                    position = searchKMP(text, pattern);
                    break;
                case 2:
                    position = searchBM(text, pattern);
                    break;
                case 3:
                    position = searchSimple(text, pattern);
                    break;
                default:
                    System.out.println("Invalid choice. The program will be finished.");
                    return;
            }

            if (position != -1) {
                System.out.println("Pattern found at position: " + position);
                System.out.println("Reading file:");
                underlinePattern(text, pattern, position);
            } else {
                System.out.println("Pattern not found in the text.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        scanner.close();
    }

    private static String readFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            //sb.append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private static int searchKMP(String text, String pattern) {
        int[] lps = computeLPSArray(pattern);
        int textLength = text.length();
        int patternLength = pattern.length();
        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                return textIndex - patternIndex;
            } else if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }
        }

        return -1;
    }

    private static int[] computeLPSArray(String pattern) {
        int patternLength = pattern.length();
        int[] lps = new int[patternLength];
        int length = 0;
        int i = 1;

        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }


    private static int searchBM(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        int[] lastOccurrence = getLastOccurrence(pattern);

        int textIndex = patternLength - 1;
        int patternIndex = patternLength - 1;

        while (textIndex < textLength) {
            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                if (patternIndex == 0) {
                    return textIndex;
                }
                textIndex--;
                patternIndex--;
            } else {
                int lastOccur = lastOccurrence[text.charAt(textIndex)];
                textIndex = textIndex + patternLength - Math.min(patternIndex, 1 + lastOccur);
                patternIndex = patternLength - 1;
            }
        }

        return -1;
    }

    private static int[] getLastOccurrence(String pattern) {
        int patternLength = pattern.length();
        int[] lastOccurrence = new int[256]; 

        for (int i = 0; i < 256; i++) {
            lastOccurrence[i] = -1; // vse ne najdeni
        }

        for (int i = 0; i < patternLength; i++) {
            lastOccurrence[pattern.charAt(i)] = i;
        }

        return lastOccurrence;
    }


    private static int searchSimple(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int i = 0; i <= textLength - patternLength; i++) {
            int j;
            for (j = 0; j < patternLength; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == patternLength) {
                return i;
            }
        }

        return -1;
    }

    private static void underlinePattern(String text, String pattern, int pos) {
    	System.out.println(text);
    	while(pos>0) {
    		pos--;
    		System.out.print(" ");
    	}
    	int pattern_size = 0;
    	while(pattern.length()>pattern_size) {
    		pattern_size++;
    		System.out.print("^");
    	}
    	System.out.println();
    }
}
