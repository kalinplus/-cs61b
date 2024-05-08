package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator{
    private int period;
    private int state;
    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }
    public double next() {
        state = state + 1;
//        int weirdState = state & (state >>> 3) % period;
//        weirdState = state & (state >> 3) & (state >> 8) % period;
        int weirdState = state & (state >> 7) % period;
        return normalize(weirdState);
    }

    private double normalize(int state) {
        return (((double) state / period) - 0.5) * 2;
    }
}
