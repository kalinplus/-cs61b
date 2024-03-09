public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> charDeque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i += 1) {
            Character c = word.charAt(i);
            charDeque.addLast(c);
        }
        return charDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> charDeque = wordToDeque(word);
        while (true) {
            if (charDeque.isEmpty() || charDeque.size() == 1) {
                return true;
            }
            char first = charDeque.removeFirst();
            char last = charDeque.removeLast();
            if (first != last) {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> cd = wordToDeque(word);
        while (true) {
            if (cd.size() == 0 || cd.size() == 1)
                return true;
            char f = cd.removeFirst();
            char l = cd.removeLast();
            if (!cc.equalChars(f, l))
                return false;
        }
    }
}
