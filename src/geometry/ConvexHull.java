package geometry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConvexHull {
	
	public static List<Point> jarvisWrap(List<Point> points) {
		// assuming no points with same coordinates
		List<Point> convexHull = new ArrayList<Point>();

		// finding starting point (minimum x-coordinate)
		Point current = points.get(0);
		for(Point p : points) {
			if(p.x < current.x || (p.x == current.x && p.y < current.y)) {
				current = p;
			}
		}
		convexHull.add(current);
		
		while(true) {
			// finding next point (right-most relative to "current")
			Point next = !current.equals(points.get(0)) ? points.get(0) : points.get(1);
			for(Point p : points) {
				if(orientation(current, next, p) < 0) next = p;
			}
			
			if(next.equals(convexHull.get(0))) return convexHull;
			convexHull.add(next);
			current = next;
		}
	}
	private static int orientation(Point p1, Point p2, Point p3) {
		// +: CCW, 0: colinear, -: CW		
		return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x); 
	}
	
	public static List<Point> grahamScan(List<Point> points) {
		// assuming no points with same coordinates
		List<Point> convexHull = new ArrayList<Point>();
		
		// sorting
		points.sort(new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.x < p2.x || (p1.x == p2.x && p1.y < p2.y) ? -1 : 1;
			}
		});
		// round 1: 0 to n-1
		for(int i = 0; i < points.size(); i++) {
			// local repair
			for(int j = convexHull.size() - 1; j >= 1; j--) {
				if(orientation(convexHull.get(j-1), convexHull.get(j), points.get(i)) < 0) {
					convexHull.remove(j);
				}
				else break;
			}
			// adding to convex hull
			convexHull.add(points.get(i));
//			System.out.println(convexHull);
		}
		// round 2: n-2 to 1
		for(int i = points.size() - 2; i >= 1; i--) {
			// local repair
			for(int j = convexHull.size() - 1; j >= 1; j--) {
				if(orientation(convexHull.get(j-1), convexHull.get(j), points.get(i)) < 0) {
					convexHull.remove(j);
				}
				else break;
			}
			// adding to convex hull
			convexHull.add(points.get(i));
//			System.out.println(convexHull);
		}
		
		return convexHull;
	}

	static class Point {
		public final int key, x, y;
		public Point(int key, int x, int y) {
			this.key = key; this.x = x; this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null || getClass() != obj.getClass()) return false;
			else {
				Point other = (Point) obj;
				return x == other.x && y == other.y;
			}
		}
		@Override
		public String toString() {
			return String.valueOf(key);
		}
	}
}
