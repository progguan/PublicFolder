import java.util.*;

public class TextArrangement {
    public static List<String> arrangeText(String str, int width) {
        String[] words = str.split(" ");
        int len = words.length;
        // the index of the first word of this line
        int idx = 0;

        List<String> res = new ArrayList<>();

        while (idx < len) {
            // words with one space in between
            int validLen = words[idx].length();
            int nextFirstIdx = idx + 1;

            while (nextFirstIdx < len) {
                if (validLen + 1 + words[nextFirstIdx].length() > width) break;
                validLen += 1 + words[nextFirstIdx].length();
                nextFirstIdx++;
            }

            // e.g. w1g1w2g2w3g3w4
            int gaps = nextFirstIdx - idx - 1;
            StringBuilder sb = new StringBuilder();

            // deal with the last line
            if (nextFirstIdx == len || gaps == 0) {
                for (int i = idx; i < nextFirstIdx; i++) {
                    sb.append(words[i]);
                    sb.append(" ");
                }
                sb.deleteCharAt(sb.length() - 1);
                while (sb.length() < width) {
                    sb.append(" ");
                }
            } else {
                // if it's not the last line, then fill in with spaces, and add the last word separately.
                int spaces = (width - validLen) / gaps;
                int extraSpaces = (width - validLen) % gaps;

                for (int i = idx; i < nextFirstIdx - 1; i++) {
                    sb.append(words[i]);
                    sb.append(" ");

                    int numOfSpace = spaces + (i - idx < extraSpaces ? 1 : 0);
                    for (int j = 0; j < numOfSpace; j++) {
                        sb.append(" ");
                    }
                }

                sb.append(words[nextFirstIdx - 1]);
            }

            res.add(sb.toString());
            idx = nextFirstIdx;
        }

        return res;
    }

    public static void main(String args[]) {
        String str = "This is a test of text arrangement. The result should be arranged in line with max width of 'width'. The " +
                "last line should be aligned to left without extra filling spaces. Other lines should be aligned to both " +
                "sides.";
        for (String s : arrangeText(str, 25)) {
            System.out.println(s);
        }
    }
}
