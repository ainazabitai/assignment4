import java.util.*;

public class Experiment {

    public static void runTraversals(Graph g, int startVertex) {
        if (g.getVertexCount() == 0) {
            System.out.println("Graph is empty!");
            return;
        }

        g.runAllTraversals(startVertex);
    }

    public static void runMultipleTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PERFORMANCE ANALYSIS");
        System.out.println("=".repeat(60));

        int[] sizes = {10, 30, 100};

        for (int size : sizes) {
            System.out.println("\n--- Graph with " + size + " vertices ---");
            Graph g = new Graph(false);

            for (int i = 0; i < size; i++) {
                g.addVertex(new Vertex(i));
            }

            Random rand = new Random(42);

            // Create a path with random weights
            for (int i = 0; i < size - 1; i++) {
                int weight = rand.nextInt(20) + 1;  // weights 1-20
                g.addEdge(i, i + 1, weight);
            }

            // Add additional random edges with random weights
            int additionalEdges = size * 2;
            for (int e = 0; e < additionalEdges; e++) {
                int from = rand.nextInt(size);
                int to = rand.nextInt(size);
                int weight = rand.nextInt(20) + 1;
                if (from != to) {
                    g.addEdge(from, to, weight);
                }
            }

            if (size == 10) {
                g.printGraph();
            }

            runTraversals(g, 0);
        }
    }

    public static void printResults() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALGORITHM COMPLEXITY COMPARISON");
        System.out.println("=".repeat(60));
        System.out.println("BFS:       O(V + E) - unweighted shortest path");
        System.out.println("DFS:       O(V + E) - graph traversal");
        System.out.println("Dijkstra:  O(V²) - weighted shortest path (simple implementation)");
        System.out.println("\nDijkstra's algorithm finds the shortest path in weighted graphs");
        System.out.println("BFS works only for unweighted graphs (all edges have same weight)");
        System.out.println("DFS does not guarantee shortest path");
    }
}