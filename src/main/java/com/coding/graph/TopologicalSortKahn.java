package main.java.com.coding.graph;

import java.util.*;

/**
 * Implementation of Kahn's algorithm to find a topological ordering
 *
 * <p>Kahn's algorithm finds a topological ordering by iteratively removing nodes in the graph which
 * have no incoming edges. When a node is removed from the graph, it is added to the topological
 * ordering and all its edges are removed allowing for the next set of nodes with no incoming edges
 * to be selected.
 *
 *Time complexity: O(V+E)
 */
public class TopologicalSortKahn {
	
	public static int[] topologicalSort(List<List<Integer>> g) {
		int n = g.size();
		int[] inDegree = new int[n];
		
		for (int from = 0; from <n; ++from) {
			List<Integer> toNodes = g.get(from);
			if (toNodes != null) {
				for (int to : toNodes) {
					inDegree[to]++;
				}
			}
		}
		
		Queue<Integer> q = new LinkedList<>();
		for (int i=0; i<n; ++i) {
			if (inDegree[i] == 0) {
				q.add(i);
			}
		}
		
		int[] order = new int[n];
		int idx = 0;
		
		while (!q.isEmpty()) {
			int u = q.poll();
			order[idx++] = u;
			
			List<Integer> toNodes = g.get(u);
			if (toNodes != null) {
				for (int v : toNodes) {
					inDegree[v]--;
					if (inDegree[v] == 0) q.add(v);
				}
			}
		}
		
		if (idx != n) {
		     throw new IllegalArgumentException("Graph is not acyclic! Detected a cycle.");
		}
		
		return order;
	}
	
	// Example usage:
	  public static void main(String[] args) {
	    
	  }
}
