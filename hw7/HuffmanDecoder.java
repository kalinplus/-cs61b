import java.util.ArrayList;

public class HuffmanDecoder {
    public static void main(String[] args) {
        String encoded = args[0];
        String decoded = args[1];
        ObjectReader or = new ObjectReader(encoded);

        BinaryTrie btrie = (BinaryTrie) or.readObject();
        Integer len = (Integer) or.readObject();
        BitSequence entire = (BitSequence) or.readObject();
        char[] chars = new char[len];
        for (int i = 0; i < len; i += 1) {
            Match lpfm = btrie.longestPrefixMatch(entire);
            chars[i] = lpfm.getSymbol();
            int lpfmLen = lpfm.getSequence().length();
            if (lpfmLen > entire.length()) {
                break;
            }
            entire = entire.allButFirstNBits(lpfmLen);
        }
        FileUtils.writeCharArray(decoded, chars);
    }
}
