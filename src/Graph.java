import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjList;  // adjacency list with weighted edges
    private Map<Integer, Vertex> vertexMap;
    private boolean directed;

    public Graph(boolean directed) {
        adjList = new HashMap<>();
        vertexMap = new HashMap<>();
        this.directed = directed;
    }

    public Graph() {
        this(false);
    }

    public void addVertex(Vertex v) {
        int id = v.getId();
        if (!adjList.containsKey(id)) {
            adjList.put(id, new ArrayList<>());
            vertexMap.put(id, v);
        }
    }

    public void addEdge(int from, int to, int weight) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Vertex " + from + " or " + to + " not found");
        }

        Vertex sourceVertex = vertexMap.get(from);
        Vertex destVertex = vertexMap.get(to);

        // Add edge from -> to with weight
        adjList.get(from).add(new Edge(sourceVertex, destVertex, weight));

        // If undirected, add reverse edge with same weight
        if (!directed) {
            adjList.get(to).add(new Edge(destVertex, sourceVertex, weight));
        }
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void printGraph() {
        System.out.println("\nGraph Structure (Weighted Adjacency List):");
        for (int vertexId : adjList.keySet()) {
            System.out.print("V" + vertexId + " -> ");
            List<Edge> edges = adjList.get(vertexId);

            System.out.print("[");
            for (int i = 0; i < edges.size(); i++) {
                Edge e = edges.get(i);
                System.out.print(e.getDestination().getId() + "(" + e.getWeight() + ")");
                if (i < edges.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    public void bfs(int start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Error: Start vertex " + start + " not found");
            return;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> order = new ArrayList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            List<Edge> edges = new ArrayList<>(adjList.get(current));
            edges.sort(Comparator.comparingInt(e -> e.getDestination().getId()));

            for (Edge edge : edges) {
                int neighbor = edge.getDestination().getId();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        System.out.print("BFS: ");
        for (int i = 0; i < order.size(); i++) {
            System.out.print("V" + order.get(i));
            if (i < order.size() - 1) System.out.print(" ");
        }
        System.out.println();
    }

    public void dfs(int start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Error: Start vertex " + start + " not found");
            return;
        }

        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        List<Integer> order = new ArrayList<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                order.add(current);

                List<Edge> edges = new ArrayList<>(adjList.get(current));
                edges.sort((a, b) -> Integer.compare(b.getDestination().getId(),
                        a.getDestination().getId()));

                for (Edge edge : edges) {
                    int neighbor = edge.getDestination().getId();
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        System.out.print("DFS: ");
        for (int i = 0; i < order.size(); i++) {
            System.out.print("V" + order.get(i));
            if (i < order.size() - 1) System.out.print(" ");
        }
        System.out.println();
    }

    // DIJKSTRA'S ALGORITHM
    public void dijkstra(int start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Error: Start vertex " + start + " not found");
            return;
        }

        int numVertices = adjList.size();
        int[] distances = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] previous = new int[numVertices];  // to reconstruct paths

        // Initialize distances
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }
        distances[start] = 0;

        // Main loop - find shortest path to all vertices
        for (int count = 0; count < numVertices - 1; count++) {
            // Find vertex with minimum distance among unvisited vertices
            int minVertex = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && distances[v] < minDistance) {
                    minDistance = distances[v];
                    minVertex = v;
                }
            }

            // If no reachable vertex found, break
            if (minVertex == -1) {
                break;
            }

            visited[minVertex] = true;

            // Update distances to neighbors of minVertex
            List<Edge> edges = adjList.get(minVertex);
            for (Edge edge : edges) {
                int neighbor = edge.getDestination().getId();
                int weight = edge.getWeight();

                if (!visited[neighbor] && distances[minVertex] != Integer.MAX_VALUE) {
                    int newDistance = distances[minVertex] + weight;
                    if (newDistance < distances[neighbor]) {
                        distances[neighbor] = newDistance;
                        previous[neighbor] = minVertex;
                    }
                }
            }
        }

        // Print results
        printDijkstraResults(start, distances, previous);
    }

    private void printDijkstraResults(int start, int[] distances, int[] previous) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DIJKSTRA'S ALGORITHM RESULTS");
        System.out.println("Starting vertex: V" + start);
        System.out.println("=".repeat(60));

        System.out.println("\nShortest distances from V" + start + ":");
        System.out.println("Vertex\t\tDistance\tPath");
        System.out.println("-".repeat(50));

        for (int i = 0; i < distances.length; i++) {
            if (i != start) {
                System.out.print("V" + i + "\t\t");
                if (distances[i] == Integer.MAX_VALUE) {
                    System.out.print("∞\t\tNo path");
                } else {
                    System.out.print(distances[i] + "\t\t");
                    printPath(previous, i);
                }
                System.out.println();
            }
        }

        // Also show the starting vertex
        System.out.println("V" + start + "\t\t0\t\tV" + start);
        System.out.println();
    }

    private void printPath(int[] previous, int vertex) {
        List<Integer> path = new ArrayList<>();
        int current = vertex;

        while (current != -1) {
            path.add(current);
            current = previous[current];
        }

        Collections.reverse(path);

        for (int i = 0; i < path.size(); i++) {
            System.out.print("V" + path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }

    public void runAllTraversals(int start) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RUNNING ALL ALGORITHMS FROM V" + start);
        System.out.println("=".repeat(60));

        long startBfs = System.nanoTime();
        bfs(start);
        long endBfs = System.nanoTime();
        System.out.printf("BFS time: %d ns (%.3f ms)\n", (endBfs - startBfs), (endBfs - startBfs) / 1_000_000.0);

        long startDfs = System.nanoTime();
        dfs(start);
        long endDfs = System.nanoTime();
        System.out.printf("DFS time: %d ns (%.3f ms)\n", (endDfs - startDfs), (endDfs - startDfs) / 1_000_000.0);

        long startDijkstra = System.nanoTime();
        dijkstra(start);
        long endDijkstra = System.nanoTime();
        System.out.printf("Dijkstra time: %d ns (%.3f ms)\n", (endDijkstra - startDijkstra), (endDijkstra - startDijkstra) / 1_000_000.0);

        System.out.println("-".repeat(60));
    }

    public Map<Integer, List<Edge>> getAdjList() {
        return adjList;
    }

    public int getVertexCount() {
        return adjList.size();
    }
}