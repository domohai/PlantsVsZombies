package abc;

import java.util.Random;

public class RandomMachine extends Random {
	public static Random random = new Random();
	
	public static int randomInt(int from, int to) {
		return RandomMachine.random.nextInt(to) + from;
	}
	
	public static double randomDouble() {
		return RandomMachine.random.nextDouble();
	}
	
}
