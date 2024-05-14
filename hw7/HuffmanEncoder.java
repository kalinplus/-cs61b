import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> freTable = new HashMap<>();
        for (char symbol : inputSymbols) {
            if (freTable.containsKey(symbol)) {
                freTable.put(symbol, freTable.get(symbol) + 1);
            } else {
                freTable.put(symbol, 1);
            }
        }
        return freTable;
    }

    /**
     * 1. read file as char[]
     * 2. use char[] to build frequency table, then use fre table to construct
     *    a binary decoding trie
     * 3. write binary trie object to `fileName`.huf file
     * 4. write the number of symbols to .huf file
     * 4. use binary trie to create lookup table for encoding
     * 5. for each symbol, look up in table, and add bit seq to list of bit seqs
     * 6. assemble all bit seqs in list to one huge bit seq
     * 7. write the huge bit seq to .huf file
     * @param args
     */
    public static void main(String[] args) {
        String fileName = args[0];
        char[] symbols = FileUtils.readFile(fileName);
        Map<Character, Integer> freTable = buildFrequencyTable(symbols);
        BinaryTrie btrie = new BinaryTrie(freTable);
        ObjectWriter ow = new ObjectWriter(fileName + ".huf");
        ow.writeObject(btrie);
        Map<Character, BitSequence> lookupTable = btrie.buildLookupTable();
        List<BitSequence> symbolList = new ArrayList<>();
        for (char s : symbols) {
            BitSequence symbol = lookupTable.get(s);
            symbolList.add(symbol);
        }
        BitSequence hugeBitSeq = BitSequence.assemble(symbolList);
        ow.writeObject(hugeBitSeq);
    }
}
