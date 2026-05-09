import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjList;
    private Map<Integer, Vertex> vertexMap;

    public Graph() {
        adjList = new HashMap<>();
        vertexMap = new HashMap<>();
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
    }

    public void printGraph() {
        for (int vertex : adjList.keySet()) {
            System.out.print(vertexMap.get(vertex) + " -> ");
            for (int neighbor : adjList.get(vertex)) {
                System.out.print(vertexMap.get(neighbor) + " ");
            }
            System.out.println();
        }
    }

    private void dfsRecursive(int current, Set<Integer> visited) {
        visited.add(current);
        System.out.print(vertexMap.get(current) + " ");

        for (int neighbor : adjList.get(current)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }
}