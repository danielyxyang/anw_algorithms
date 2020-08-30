package geometry;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import geometry.ConvexHull.Point;
import tools.Scannable;
import tools.TestTools;

public class ConvexHullTest {

	@Test
	public void testJarvisWrap() throws FileNotFoundException {
		ArrayList<ConvexHull.Point> points = new ArrayList<ConvexHull.Point>();
		Scannable scanner = TestTools.getTestScanner("test/geometry/files/convexHull.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			for(int i = 0; i < n; i++) {
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				points.add(new ConvexHull.Point(i, x, y));
			}
			
			int numPoints = scanner.nextInt();
			int[] convexHull = TestTools.parseLine(scanner, numPoints);
			
			List<Point> result = ConvexHull.jarvisWrap(points);
			int[] resultArray = new int[result.size()];
			for(int i = 0; i < result.size(); i++) resultArray[i] = result.get(i).key;
			Arrays.sort(resultArray);
			
			assertArrayEquals(convexHull, resultArray);
		}
	}
	
	@Test
	public void testGrahamScan() throws FileNotFoundException {
		ArrayList<ConvexHull.Point> points = new ArrayList<ConvexHull.Point>();
		Scannable scanner = TestTools.getTestScanner("test/geometry/files/convexHull.txt");
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			for(int i = 0; i < n; i++) {
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				points.add(new ConvexHull.Point(i, x, y));
			}
			
			int numPoints = scanner.nextInt();
			int[] convexHull = TestTools.parseLine(scanner, numPoints);
			
			List<Point> result = ConvexHull.grahamScan(points);
			int[] resultArray = new int[result.size()];
			for(int i = 0; i < result.size(); i++) resultArray[i] = result.get(i).key;
			Arrays.sort(resultArray);
			
			assertArrayEquals(convexHull, resultArray);
		}
	}

}
