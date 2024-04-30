/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // init copy and max length
        String[] copy = new String[asciis.length];
        int longest = Integer.MIN_VALUE;
        int cou = 0;
        for (String s: asciis) {
            longest = Math.max(longest, s.length());
            copy[cou] = s;
            cou += 1;
        }
        // counting sort every char
        for (int d = longest - 1; d >= 0; d -= 1) {
            sortHelperLSD(copy, d);
        }
        return copy;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * 1. just counting sort for char in given index
     *   a. if length of string > index + 1, just sort normally
     *   b. if not, prepend there is a "_" as placeholder, and store it in [0]
     *
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] count = new int[256];
        for (String s: asciis) {
            if (s.length() > index) {
                int asc = (int)s.charAt(index);
                count[asc] += 1;
            } else {
                count[0] += 1;
            }
        }

        int[] start = new int[256];
        int pos = 0;
        for (int i = 0; i < 256; i += 1) {
            start[i] = pos;
            pos += count[i];
        }

        String[] indexSorted = new String[asciis.length];
        for (String s: asciis) {
            int place = s.length() > index ? (int)s.charAt(index) : 0;
            indexSorted[start[place]] = s;
            start[place] += 1;
        }
        for (int i = 0; i < asciis.length; i += 1) {
            asciis[i] = indexSorted[i];
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
