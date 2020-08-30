package probability;

import java.util.Random;

public class TargetShooting {
	public static double approximatePi(int n) {
		Random random = new Random();
		double z = 0;
		for(int i = 0; i < n; i++) {
			double x = random.nextDouble();
			double y = random.nextDouble();
			if(Math.sqrt(x*x + y*y) <= 1) z++;
		}
		return z / n * 4;
	}
	
	public static void main(String[] args) {
		for(int i = 1; i <= 7; i++) {
			double result = approximatePi((int) Math.pow(10, i));
			System.out.printf("N = 1e%1d: %.8f (d = %6.8f)%n", i, result, Math.abs(Math.PI - result));
		}
	}
}
