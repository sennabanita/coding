package main.java.com.coding.graph;

/**
 * This Topological sort takes an adjacency matrix of an acyclic graph and returns an array with the
 * indexes of the nodes in a (non unique) topological order which tells you how to process the nodes
 * in the graph. More precisely from wiki: A topological ordering is a linear ordering of its
 * vertices such that for every directed edge uv from vertex u to vertex v, u comes before v in the
 * ordering.
 *
 * <p>Time Complexity: O(V^2)
 */
public class TopologicalSortAdjacencyMatrix {
	private static int dfs(Double[][] graph, boolean[] visited, int[] order, int index, int from) {
		if (visited[from]) return index;
		
		visited[from] = true;
		
		for (int to=0; to<graph.length; ++to) {
			if (graph[from][to] != null && !visited[to])
				index = dfs(graph, visited, order, index, to);
		}
		
		order[index] = from;
		return index-1;
	}
	
	public static int[] topologicalSort(Double[][] graph) {
		int n = graph.length;
		int[] order = new int[n];
		boolean[] visited = new boolean[n];
		
		int index = n-1;
		for (int i=0; i<n; ++i) {
			if (!visited[i]) {
				index = dfs(graph, visited, order, index, i);
			}
		}
		
		return order;
	}
	
	// A useful application of the topological sort is to find the shortest path
	// between two nodes in a Directed Acyclic Graph (DAG). Given an adjacency matrix
	// with double valued edge weights this method finds the shortest path from
	// a start node to all other nodes in the graph.
	public static Double[] dagShortestPath(Double[][] graph, int start) {
		int[] order = topologicalSort(graph);
		Double[] dist = new Double[graph.length];
		dist[start] = 0.0;
		
		for (int i=0; i<graph.length; ++i) {
			int idx = order[i];
			if (dist[idx] != null) {
				for (int to=0; to<graph.length; ++to) {
					if (graph[idx][to] != null) {
						double newDist = dist[idx] + graph[idx][to] ;
						dist[to] = (dist[to] == null? newDist : Math.min(dist[to], newDist));
					}
				}
			}
		}
		
		return dist;
	}
	
	// Example usage of topological sort
	  public static void main(String[] args) {

	    final int N = 7;
	    Double[][] adjMatrix = new Double[N][N];

	    adjMatrix[0][1] = 3.0;
	    adjMatrix[0][2] = 2.0;
	    adjMatrix[0][5] = 3.0;

	    adjMatrix[1][3] = 1.0;
	    adjMatrix[1][2] = 6.0;

	    adjMatrix[2][3] = 1.0;
	    adjMatrix[2][4] = 10.0;

	    adjMatrix[3][4] = 5.0;

	    adjMatrix[5][4] = 7.0;

	    int[] ordering = topologicalSort(adjMatrix);

	    // Prints: [6, 0, 5, 1, 2, 3, 4]
	    System.out.println(java.util.Arrays.toString(ordering));

	    // Find the shortest path from 0 to all other nodes
	    Double[] dists = dagShortestPath(adjMatrix, 0);

	    // Find the distance from 0 to 4 which is 8.0
	    System.out.println(dists[4]);

	    // Finds the shortest path from 0 to 6
	    // prints Infinity (6 is not reachable!)
	    System.out.println(dists[6]);
	  }
}
