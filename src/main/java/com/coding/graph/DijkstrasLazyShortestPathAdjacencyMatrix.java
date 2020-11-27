package main.java.com.coding.graph;

import java.util.*;

/**
This is lazy implementation of dijkstra as we keep stale values in PQ and delete them later when found. 
This PQ is java's default PR implementation where removal is O(V) and insert is log(V). So we insert new
value and later delete the stale value with higher distance.
This implementation has time complexity of O(E * logV)
**/
public class DijkstrasLazyShortestPathAdjacencyMatrix {
    // adj_matrix
    int[][] adj;

    public void buildGraph(int n) {
        adj = new int[n][n];
    }

    public void addEdge(int from, int to, int cost) {
        adj[from][to] = cost;
    }

    // this is a lazy implementation of dijkstra 
    // time complexity O(E * logV)
    public int[] dijkstra(int src) {
        int n = adj.length;
        int[] dist = new int[n];
	Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] visited = new boolean[n];

        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[1],b[1]);
	    }
       });

       dist[src] = 0;
       pq.add(new int[]{src, 0});

       while (!pq.isEmpty()) {
           int[] v = pq.poll();
           int node = v[0];
           int cost = v[1];
           visited[node] = true;           
           if (dist[node] < cost) continue;

           for (int i=0; i<n; ++i) {
                if (adj[node][i] == 0 || visited[i]) continue; // no edge or done processing
                if (cost + adj[node][i] < dist[i]) {
                    dist[i] = cost + adj[node][i];
                    pq.add(new int[]{i, dist[i]});
                }
           }

           
       }  
       
       return dist;
    }
}
