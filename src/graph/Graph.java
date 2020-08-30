package graph;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	public final int n;
	
	private ArrayList<Integer>[] adjList; // undirected Graph
	private int[][] weight;
	
	public Graph(int n) {
		this.n = n;
		
		adjList = new ArrayList[n];
		for(int i = 0; i < n; i++) adjList[i] = new ArrayList<Integer>();
		weight = new int[n][n];
	}
	
	public void addEdge(int u, int v) {
		addEdge(u, v, 1);
	}
	
	public void addEdge(int u, int v, int c) {
		adjList[u].add(v);
		adjList[v].add(u);
		weight[u][v] = c;
	}
	
	public int computeMaximumFlow(int s, int t) {
		MaximumFlowSolver task = new MaximumFlowSolver(s, t);
		return task.solve();
	}

	public boolean[] computeArticulationVertices() {
		DFSSolver task = new DFSSolver();
		task.solve();
		return task.getArtVertices();
	}
	
	public boolean[][] computeBridges() {
		DFSSolver task = new DFSSolver();
		task.solve();
		return task.getBridges();
	}
	
	// SOLVER CLASSES
	private class DFSSolver {
		private final int ROOT = 0;
		private boolean[] visited;
		private int rootDeg;
		private int dfsCounter = 0;
		private int[] dfs, low;

		private boolean[] isArtVertex;
		private boolean[][] isBridge;
		
		public DFSSolver() {
			int n = adjList.length;
			visited = new boolean[n];
			dfs = new int[n];
			low = new int[n];
			rootDeg = 0;
			isArtVertex = new boolean[n];
			isBridge = new boolean[n][n];
		}
		
		public void solve() {
			dfsCounter = 0;
			dfsVisit(ROOT, -1);
			
			if(rootDeg >= 2) isArtVertex[ROOT] = true;
			else isArtVertex[ROOT] = false;
		}
		private int dfsVisit(int node, int parent) {
			visited[node] = true;
			dfs[node] = dfsCounter++;
			low[node] = dfs[node];
			
			for(int v : adjList[node]) {
				if(!visited[v]) {
					if(node == ROOT) rootDeg++;
					low[node] = Math.min(low[node], dfsVisit(v, node)); // minimize with back-propagation
					
					if(low[v] >= dfs[node]) isArtVertex[node] = true;
					if(low[v] > dfs[node]) isBridge[node][v] = isBridge[v][node] = true;
				}
				else if(v != parent) low[node] = Math.min(low[node], dfs[v]); // minimize with backwards-edge (not with forward-edge backwards!)
			}
			return low[node];
		}
		
		public boolean[] getArtVertices() {
			return isArtVertex;
		}
		public boolean[][] getBridges() {
			return isBridge;
		}
	}
	
	private class MaximumFlowSolver {
		private int s, t;
		private int[][] capacity;

		private int[] pred;
		private int[][] flow;

		public MaximumFlowSolver(int s, int t) {
			int n = adjList.length;
			this.s = s; this.t = t;
			capacity = weight;
			pred = new int[n];
			flow = new int[n][n]; 
		}
		
		public int solve() {
			int maxflow = 0;
			while(findAugmentedPath()) {
				// find smallest delta to augment with
				int delta = Integer.MAX_VALUE;
				for(int node = t; node != s; node = pred[node]) {
					int p = pred[node];
					
					// OPTION 1: distinguish between "forward flow" and "backward flow"
//					if(capacity[p][node] > 0) delta = Math.min(delta, capacity[p][node] - flow[p][node]);
//					else delta = Math.min(delta, flow[node][p]);
					
					// OPTION 2: handling "forward/backward flow" the same (backward: flow < 0, forward: flow < capacity)
					delta = Math.min(delta, capacity[p][node] - flow[p][node]);
				}
				// augment path
				for(int node = t; node != s; node = pred[node]) {
					int p = pred[node];
					
					// OPTION 1
//					if(capacity[p][node] > 0) flow[p][node] += delta;
//					else flow[node][p] -= delta;
					
					// OPTION 2
					flow[p][node] += delta; // forward flow
					flow[node][p] -= delta; // backward flow
				}
				// increase maxflow
				maxflow += delta;
			}
			return maxflow;
		}
		private boolean findAugmentedPath() {
			LinkedList<Integer> queue = new LinkedList<Integer>();
			boolean[] visited = new boolean[n];

			queue.addLast(s);
			visited[s] = true;
			while(!queue.isEmpty()) {
				int node = queue.pollFirst();
				for(int v : adjList[node]) {
					if(!visited[v]) {
						// OPTION 1
//						if(capacity[node][v] - flow[node][v] > 0 || flow[v][node] > 0) {
						
						// OTPION 2
						if(flow[node][v] < capacity[node][v]) { // backward: flow < 0 < capacity, forward < capacity
							visited[v] = true;
							pred[v] = node;
							queue.addLast(v);
							
							if(v == t) return true;
						}
					}
				}
			}
			return false;
		}
	}
}
