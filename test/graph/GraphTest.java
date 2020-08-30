package graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.Test;

import graph.Graph;
import tools.Scannable;
import tools.TestScanner;
import tools.TestTools;

public class GraphTest {

	@Test
	public void testArtVertices() throws FileNotFoundException {
		Scannable scanner = TestTools.getTestScanner("test/graph/files/dfsArtVertices.txt");
		while(scanner.hasNext()) {
			Graph graph = TestTools.parseGraph(scanner, false);
			
			int numResults = scanner.nextInt();
			int[] expectedArtVertices = TestTools.parseLine(scanner, numResults);
			boolean[] artVertices = new boolean[graph.n];
			for(int v : expectedArtVertices) artVertices[v] = true;
			
			boolean[] result = graph.computeArticulationVertices();
			assertArrayEquals(artVertices, result);
		}
	}

	@Test
	public void testBridges() throws FileNotFoundException {
		Scannable scanner = TestTools.getTestScanner("test/graph/files/dfsBridges.txt");
		while(scanner.hasNext()) {
			Graph graph = TestTools.parseGraph(scanner, false);
			
			int numResults = scanner.nextInt();
			boolean[][] bridges = new boolean[graph.n][graph.n];
			for(int i = 0; i < numResults; i++) {
				int u = scanner.nextInt(), v = scanner.nextInt();
				bridges[u][v] = bridges[v][u] = true;
			}
			
			boolean[][] result = graph.computeBridges();
			for(int i = 0; i < bridges.length; i++) assertArrayEquals(bridges[i], result[i]);
		}
	}

	@Test
	public void testMaximumFlow() throws FileNotFoundException {
		Scannable scanner = TestTools.getTestScanner("test/graph/files/maxflow.txt");
		while(scanner.hasNext()) {
			Graph graph = TestTools.parseGraph(scanner, true);
			int maxflow = scanner.nextInt();

			int result = graph.computeMaximumFlow(0, graph.n-1);
			assertEquals(maxflow, result);
		}
	}

	@Test
	public void testMaximumFlowUsecase() throws FileNotFoundException {
		// CodeExpert: Bicycle Auction
		Scannable scannerIn = TestTools.getFastScanner("test/graph/files/maxflowUsecase.in.txt");
		Scannable scannerOut = TestTools.getFastScanner("test/graph/files/maxflowUsecase.out.txt");
		while(scannerIn.hasNext()) {
			// Input using In.java class
	        int c = scannerIn.nextInt();
	        int b = scannerIn.nextInt();
	        
	        // Vertex encoding
	        // cyclist (0-index) = 0...(c-1)
	        // bicycle (0-index) = c+0...c+(b-1)
	        // s = c+b
	        // t = c+b+1
	        Graph g = new Graph(c+b+2);
	        final int s = c+b, t = c+b+1;
	        
	        for(int i = 0; i < c; i++) g.addEdge(s, i, 1); // edge: s-cyclist
	        for(int i = 0; i < b; i++) g.addEdge(c+i, t, 1); // edge: bicycle-t
	        for(int i = 0; i < c; i++) {
	          int cyclist = i;
	          int x = scannerIn.nextInt();
	          for(int j = 0; j < x; j++) {
	            int bicycle = scannerIn.nextInt()-1;
	            g.addEdge(cyclist, c+bicycle, 1); // edge: cyclist-bicycle
	          }
	        }
	        
	        int maxflow = scannerOut.nextInt();
	        int result = g.computeMaximumFlow(s, t);
	        assertEquals(maxflow, result);
		}
		
	}
}
