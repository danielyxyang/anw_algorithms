package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import graph.Graph;

public class TestTools {
	public static TestScanner getTestScanner(String file) throws FileNotFoundException {
		return new TestScanner(new Scanner(new File(file)));
	}
	public static FastScanner getFastScanner(String file) throws FileNotFoundException {
		return new FastScanner(new FileInputStream(new File(file)));
	}
	public static Graph parseGraph(Scannable scanner) {
		return parseGraph(scanner, false); // default: unweighted graph
	}
	public static Graph parseGraph(Scannable scanner, boolean weighted) {
		int n = scanner.nextInt();
		int m = scanner.nextInt();

		Graph graph = new Graph(n);
		for(int i = 0; i < m; i++) {
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			if(weighted) {
				int w = scanner.nextInt();
				graph.addEdge(u, v, w);
			}
			else graph.addEdge(u, v);
		}
		return graph;
	}
	public static int[][] parseMatrix(Scannable scanner, int n) {
		int[][] result = new int[n][n];
		for(int i = 0; i < n; i++) result[i] = parseLine(scanner, n);
		return result;
	}
	public static int[] parseLine(Scannable scanner, int n) {
		int[] result = new int[n];
		for(int i = 0; i < n; i++) result[i] = scanner.nextInt();
		return result;
	}
}
