package tools;

import java.util.Scanner;

public class TestScanner implements Scannable {
	private Scanner scanner;
	public TestScanner(Scanner scanner) {
		this.scanner = scanner;
		this.scanner.useDelimiter("(\\p{javaWhitespace}|#\\w+)+"); // removes comments
	}
	public boolean hasNext() {
		return scanner.hasNext();
	}
	public String next() {
		return scanner.next();
	}
	public int nextInt() {
		return scanner.nextInt();
	}
}
