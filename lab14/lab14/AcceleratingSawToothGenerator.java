package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    public double next() {
        state = (state + 1) % period;
        if (state == 0) {
            period *= factor;
        }
        return normalize(state);
    }

    private double normalize(int state) {
        return (((double) state / period) - 0.5) * 2;
    }
}
