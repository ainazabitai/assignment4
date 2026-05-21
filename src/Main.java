public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("GRAPH ALGORITHMS: BFS, DFS, and DIJKSTRA");
        System.out.println("=".repeat(60));

        // Part 1: Small graph demonstration with weights
        System.out.println("\n【PART 1: WEIGHTED GRAPH DEMONSTRATION】");
        Graph graph = new Graph(false);

        // Add vertices 0-6
        for (int i = 0; i < 7; i++) {
            graph.addVertex(new Vertex(i));
        }

        // Add weighted edges (creating a graph similar to textbook examples)
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);
        graph.addEdge(4, 6, 7);
        graph.addEdge(5, 6, 1);

        // Display graph
        graph.printGraph();

        // Run all algorithms from vertex 0
        Experiment.runTraversals(graph, 0);

        // Run Dijkstra from different starting vertex
        System.out.println("\n【DIJKSTRA FROM DIFFERENT START VERTEX】");
        graph.dijkstra(3);

        // Part 2: Performance testing on different graph sizes
        System.out.println("\n【PART 2: PERFORMANCE ANALYSIS】");
        Experiment.runMultipleTests();
        Experiment.printResults();

        System.out.println("\n✅ All algorithms completed successfully!");
        System.out.println("\nKey Takeaway:");
        System.out.println("- BFS: Fastest, but only for unweighted graphs");
        System.out.println("- DFS: Fast traversal, but no shortest path guarantee");
        System.out.println("- Dijkstra: Slower (O(V²)), but finds true shortest path in weighted graphs");
    }
}