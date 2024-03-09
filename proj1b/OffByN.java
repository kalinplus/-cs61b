public class OffByN implements CharacterComparator{
    private int n;

    public OffByN(int n) {
        this.n = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x - y);
        if (diff == n) {
            return true;
        } else {
            return false;
        }
    }
}
