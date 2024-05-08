package lab14;

import lab14lib.*;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		// Simple Generator demo
//		Generator generator = new SineWaveGenerator(200);
		// audio
//		GeneratorPlayer gp = new GeneratorPlayer(generator);
//		gp.play(1000000);

		// draw
//		GeneratorDrawer gd = new GeneratorDrawer(generator);
//		gd.draw(4096);

		// both audio and draw
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);

		// real-time animation of generator's output
//		GeneratorAudioAnimator gaa = new GeneratorAudioAnimator(generator);
//		gaa.drawAndPlay(4096, 1000000);

		// multiple generators
//		Generator g1 = new SineWaveGenerator(60);
//		Generator g2 = new SineWaveGenerator(61);
//
//		ArrayList<Generator> generators = new ArrayList<>();
//		generators.add(g1);
//		generators.add(g2);
//		MultiGenerator mg = new MultiGenerator(generators);
//
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
//		gav.drawAndPlay(500000, 1000000);

		// SwaToothGenerator demo
//		Generator generator = new SawToothGenerator(512);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);

		// AcceleratingSawToothGenerator demo
//		Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);

		// StrangeBitwiseGenerator demo
		Generator generator = new StrangeBitwiseGenerator(1024);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(128000, 1000000);
	}
} 