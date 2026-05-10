import java.util.*;

public class Experiment {

    public static void runTraversals(Graph g, int startVertex) {
        if (g.getVertexCount() == 0) {
            System.out.println("Graph is empty!");
            return;
        }

        long startBfs = System.nanoTime();
        g.bfs(startVertex);
        long endBfs = System.nanoTime();
        long bfsTime = endBfs - startBfs;

        long startDfs = System.nanoTime();
        g.dfs(startVertex);
        long endDfs = System.nanoTime();
        long dfsTime = endDfs - startDfs;

        System.out.printf("BFS time: %d ns (%.3f ms)\n", bfsTime, bfsTime / 1_000_000.0);
        System.out.printf("DFS time: %d ns (%.3f ms)\n", dfsTime, dfsTime / 1_000_000.0);
        System.out.println("-------------------------");
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

            for (int i = 0; i < size - 1; i++) {
                g.addEdge(i, i + 1);
            }

            int additionalEdges = size * 2;
            for (int e = 0; e < additionalEdges; e++) {
                int from = rand.nextInt(size);
                int to = rand.nextInt(size);
                if (from != to) {
                    g.addEdge(from, to);
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
        System.out.println("RESULTS & ANALYSIS");
        System.out.println("=".repeat(60));
        System.out.println("Both BFS and DFS show O(V+E) linear complexity");
        System.out.println("BFS explores level by level, DFS goes deep first");
        System.out.println("Performance difference is minimal on sparse graphs");
    }
}