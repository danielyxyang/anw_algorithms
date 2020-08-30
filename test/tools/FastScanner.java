package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FastScanner implements Scannable {
	// https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
	private BufferedReader br;
	private StringTokenizer st;

	public FastScanner(InputStream in) {
		br = new BufferedReader(new InputStreamReader(in));
	}

	public boolean hasNext() {
		while (st == null || !st.hasMoreElements()) {
			try {
				String line = br.readLine();
				if(line == null) return false; // end of file
				else st = new StringTokenizer(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	public String next() { // no comments support
		while (st == null || !st.hasMoreElements()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}
	public int nextInt() {
		return Integer.parseInt(next());
	}
	public char nextChar() {
		return next().charAt(0);
	}
	public String nextLine() {
		String str = "";
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
