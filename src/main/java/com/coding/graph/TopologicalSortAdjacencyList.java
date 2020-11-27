package main.java.com.coding.graph;

import java.util.*;

/**
 * This topological sort implementation takes an adjacency list of an acyclic graph and returns an
 * array with the indexes of the nodes in a (non unique) topological order which tells you how to
 * process the nodes in the graph. More precisely from wiki: A topological ordering is a linear
 * ordering of its vertices such that for every directed edge uv from vertex u to vertex v, u comes
 * before v in the ordering.
 *
 * <p>Time Complexity: O(V + E)
 */
public class TopologicalSortAdjacencyList {
	static class Edge {
		int from, to, weight;
		
		Edge(int f, int t, int w) {
			from = f;
			to = t;
			weight = w;
		}
	}
	
	// Helper method that performs a depth first search on the graph to give
	// us the topological ordering we want. Instead of maintaining a stack
	// of the nodes we see we simply place them inside the ordering array
	// in reverse order for simplicity.
	public static int dfs(int i, int at, int[] order, boolean[] visited, Map<Integer, List<Edge>> graph) {
		visited[at] = true;
		
		List<Edge> edges = graph.get(at);
		
		if (edges != null) {
			for (Edge edge : edges) {
				if (!visited[edge.to]) i = dfs(i, edge.to, order, visited, graph);
			}
		}
		
		order[i] = at;
		return i-1;
	}
	
	// Finds a topological ordering of the nodes in a Directed Acyclic Graph (DAG)
	  // The input to this function is an adjacency list for a graph and the number
	  // of nodes in the graph.
	  //
	  // NOTE: 'numNodes' is not necessarily the number of nodes currently present
	  // in the adjacency list since you can have singleton nodes with no edges which
	  // wouldn't be present in the adjacency list but are still part of the graph!
	  //
	public static int[] topologicalSort( Map<Integer, List<Edge>> graph, int numNodes) {
		int[] orders = new int[numNodes];
		boolean[] visited = new boolean[numNodes];
		
		int idx = numNodes-1;
		for (int i=0; i<numNodes; ++i) {
			if (!visited[i]) 
				idx = dfs(idx, i, orders, visited, graph);
		}
		
		return orders;
	}
	
	  // A useful application of the topological sort is to find the shortest path
	  // between two nodes in a Directed Acyclic Graph (DAG). Given an adjacency list
	  // this method finds the shortest path to all nodes starting at 'start'
	  //
	  // NOTE: 'numNodes' is not necessarily the number of nodes currently present
	  // in the adjacency list since you can have singleton nodes with no edges which
	  // wouldn't be present in the adjacency list but are still part of the graph!
	  //
	  public static Integer[] dagShortestPath(Map<Integer, List<Edge>> graph, int start, int numNodes) {
		  int[] orders = topologicalSort(graph, numNodes);
		  Integer[] dist = new Integer[numNodes];
		  dist[start] = 0;
		  
		  for (int j=0; j<numNodes; ++j) {
			  int i = orders[j];
			  if (dist[i] != null) {
				  List<Edge> edges = graph.get(i);
				  if (edges != null) {
					  for (Edge edge : edges) {
						  int d = dist[i] + edge.weight;
						  dist[edge.to] = (dist[edge.to] == null? d : Math.min(dist[edge.to], d));
					  }
				  }
			  }
		  }
		  
		  return dist;
	  }
	
	  // Example usage of topological sort
	  public static void main(String[] args) {

	    // Graph setup
	    final int N = 7;
	    Map<Integer, List<Edge>> graph = new HashMap<>();
	    for (int i = 0; i < N; i++) graph.put(i, new ArrayList<>());
	    graph.get(0).add(new Edge(0, 1, 3));
	    graph.get(0).add(new Edge(0, 2, 2));
	    graph.get(0).add(new Edge(0, 5, 3));
	    graph.get(1).add(new Edge(1, 3, 1));
	    graph.get(1).add(new Edge(1, 2, 6));
	    graph.get(2).add(new Edge(2, 3, 1));
	    graph.get(2).add(new Edge(2, 4, 10));
	    graph.get(3).add(new Edge(3, 4, 5));
	    graph.get(5).add(new Edge(5, 4, 7));

	    int[] ordering = topologicalSort(graph, N);

	    // // Prints: [6, 0, 5, 1, 2, 3, 4]
	    System.out.println(java.util.Arrays.toString(ordering));

	    // Finds all the shortest paths starting at node 0
	    Integer[] dists = dagShortestPath(graph, 0, N);

	    // Find the shortest path from 0 to 4 which is 8.0
	    System.out.println(dists[4]);

	    // Find the shortest path from 0 to 6 which
	    // is null since 6 is not reachable!
	    System.out.println(dists[6]);
	  }
	
}
