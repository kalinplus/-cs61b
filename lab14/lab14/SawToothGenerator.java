package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class SawToothGenerator implements Generator{
    private int period;
    private int state;
    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }
    public double next() {
        state = (state + 1) % period;
        return normalize(state);
    }

    private double normalize(int state) {
        return (((double) state / period) - 0.5) * 2;
    }
}
