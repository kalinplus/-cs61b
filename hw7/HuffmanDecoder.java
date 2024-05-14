import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args) {
        String encoded = args[0];
        String decoded = args[1];
        ObjectReader or = new ObjectReader(encoded);

        BinaryTrie btrie = (BinaryTrie) or.readObject();
        BitSequence entire = (BitSequence) or.readObject();
        List<Character> symbols = new ArrayList<>();
        while(true) {
            Match lpfm = btrie.longestPrefixMatch(entire);
            if (lpfm == null) {
                break;
            }
            int lpfmLen = lpfm.getSequence().length();
            symbols.add(lpfm.getSymbol());
            entire = entire.allButFirstNBits(lpfmLen);
        }
        char[] chars = new char[symbols.size()];
        for (int i = 0; i < chars.length; i += 1) {
            chars[i] = symbols.get(i);
        }
        FileUtils.writeCharArray(decoded, chars);
    }
}
