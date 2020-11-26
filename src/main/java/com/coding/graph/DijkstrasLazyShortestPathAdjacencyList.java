public class DijkstrasLazyShortestPathAdjacencyMatrix {
     public static final double EPS = 1e-6;
     
     class Edge {
          int to;
          int from;
          double cost;
     }

     public static class Node {
         int id;
         double cost;

         Node(int id, double cost) {
              this.id = id;
              this.cost = cost;
         }
     }

     private int n;
     private double[] dist;
     private List<List<Edge>> graph;
     private int[] prev;

     private Comparator<Node> comparator = new Comparator<Node>() {
          @Override
          public int compare(Node a, Node b) {
              if (a.cost - b.cost < EPS) return 0;
              return (a.cost - b.cost > 0 : 1 : -1);
          }
     }; 

     public DijkstrasLazyShortestPathAdjacencyMatrix(int n) {
         this.n = n;
         createEmptyGraph();
     }

     public DijkstrasShortestPathAdjacencyList(int n, Comparator<Node> comparator) {
         this(n);
         if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null");
         this.comparator = comparator;
     }

     public getShortestPath(int start, int end) {
         if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
         if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");
         double dist = dijkstra(start, end);
         List<Integer> path = new ArrayList<>();
         if (dist == Double.POSITIVE_INFINITY) return path;
         for (Integer at = end; at != null; at = prev[at]) path.add(at);
         Collections.reverse(path);
         return path;
     }

     private double dijkstras(int from, int to) {
          double[] dist = new double[n];
          Arrays.fill(dist, Double.POSITIVE_INFINITY);
          boolean[] visited = new boolean[n];
          prev = new int[n];

          PriorityQueue<Node> pq = new PriorityQueue<>(2*n, comparator);

          pq.add(new Node(from, 0));
          dist[from] = 0;
          prev[from] = -1;
	  while (!pq.isEmpty()) {
              Node node = pq.poll();
              visited[node.id] = true;

              if (dist[node.id] < node.cost) continue;

              for (Edge e : graph.get(node.id)) { 
                    if (visited[e.to]) continue;
                    double newDist = node.cost + e.cost;
                    if (newDist < dist[e.to]) {
                         prev[e.to] = e.from;
                         dist[e.to] = newDist;
 			 pq.add(new Node(e.to, newDist));
                    }
              }
              if (node.id == to) return dist[node.id]; 
          }
          return Double.POSITIVE_INFINITY; 
     }

     private void createEmptyGraph() {
         graph = new ArrayList<>(n);
         for (int i=0; i<n; ++i) {
             graph.add(new ArrayList<>());
         }
     }
}
