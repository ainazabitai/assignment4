public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("GRAPH TRAVERSAL: BFS vs DFS PERFORMANCE ANALYSIS");
        System.out.println("=".repeat(60));

        Graph graph = new Graph(false); // undirected graph

        for (int i = 0; i < 5; i++) {
            graph.addVertex(new Vertex(i));
        }

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 0);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 1);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);

        graph.printGraph();

        System.out.println("\n--- Running Traversals from V0 ---");
        Experiment.runTraversals(graph, 0);

        Experiment.runMultipleTests();
        Experiment.printResults();
    }
}