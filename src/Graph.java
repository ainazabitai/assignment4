import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjList;  // Now stores weighted edges    private Map<Integer, Vertex> vertexMap;
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

    public void addEdge(int from, int to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Vertex not found");
        }

        adjList.get(from).add(to);

        if (!directed) {
            adjList.get(to).add(from);
        }
    }

    public void printGraph() {
        System.out.println("\nGraph Structure (Adjacency List):");
        for (int vertexId : adjList.keySet()) {
            System.out.print("V" + vertexId + " -> ");
            List<Integer> neighbors = adjList.get(vertexId);

            System.out.print("[");
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.print(neighbors.get(i));
                if (i < neighbors.size() - 1) {
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

            List<Integer> neighbors = new ArrayList<>(adjList.get(current));
            Collections.sort(neighbors);

            for (int neighbor : neighbors) {
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

                List<Integer> neighbors = new ArrayList<>(adjList.get(current));
                Collections.sort(neighbors, Collections.reverseOrder());

                for (int neighbor : neighbors) {
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

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }

    public int getVertexCount() {
        return adjList.size();
    }
}