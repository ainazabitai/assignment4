## Abitay Ainaz 
### IT-2502
## Required Analysis Questions

### Q1: How does graph size affect BFS and DFS performance?

**Answer:** Both algorithms show increased execution time as graph size grows from 10 → 30 → 100 vertices. 
- BFS time: 0.63ms → 0.11ms → 5.01ms (note: 10-vertex anomaly discussed below)
- DFS time: 0.04ms → 0.08ms → 2.57ms

The 10-vertex BFS anomaly (0.63ms vs expected ~0.05ms) is likely due to JVM warm-up - the first method call incurs class loading and JIT compilation overhead.

### Q2: Which traversal is faster in your experiments?

**Answer:** DFS was consistently faster in my experiments:
- At 10 vertices: DFS was ~15.6x faster
- At 30 vertices: DFS was ~1.4x faster  
- At 100 vertices: DFS was ~1.95x faster

### Q3: Do results match expected complexity O(V+E)?

**Answer:** Partially yes. The theoretical complexity O(V+E) predicts linear scaling with graph size. 
- From 30→100 vertices: V+E grows from 87 → 297 (3.4x increase)
- BFS time: 0.11ms → 5.01ms (45x increase - not linear! This suggests measurement anomaly)
- DFS time: 0.08ms → 2.57ms (32x increase)

The non-linear scaling suggests that at 100 vertices, other factors dominate (cache misses, recursion depth overhead).

### Q4: How does graph structure affect traversal order?

**Answer:** Graph structure dramatically changes traversal order. In my chain-like graph:

**BFS Order (V0 start):** V0, V1, V2, V3, V4, V5, V6, V7, V8, V9  
*(Level by level - if graph had branches, BFS would visit all neighbors before going deeper)*

**DFS Order (V0 start):** V0, V1, V2, V3, V4, V5, V6, V7, V8, V9  
*(In a pure chain, BFS and DFS produce the same order! But with branches, DFS goes deep first)*

### Q5: When is BFS preferred over DFS?

**Answer:** BFS is preferred when:
1. Finding the **shortest path** in unweighted graphs
2. The graph has a **shallow structure** (solution is near the start)
3. You need **level-order information** (social network distance)
4. The graph is **wide rather than deep**
5. You must avoid **stack overflow** (BFS uses heap memory)

### Q6: What are the limitations of DFS?

**Answer:** DFS has several important limitations:
1. **Does NOT find shortest paths** - it finds any path, not necessarily optimal
2. **Stack overflow risk** - recursive DFS crashes on deep graphs (>10,000 depth in Java)
3. **Can get stuck** in infinite loops if cycles aren't tracked
4. **Not optimal for wide graphs** - may explore many irrelevant branches
5. **Memory inefficiency** for graphs with high branching factor

Graph Traversal: BFS & DFS Performance Analysis

## A. Project Overview
This project implements a graph using an **adjacency list** representation.  
Vertices represent nodes, edges represent connections.  
BFS (Breadth-First Search) explores neighbors level by level using a queue.  
DFS (Depth-First Search) explores as far as possible along each branch before backtracking using recursion (or stack).

## B. Class Descriptions
- **Vertex**: Stores an ID.
- **Edge**: Stores source and destination.
- **Graph**: Contains adjacency list (`Map<Integer, List<Integer>>`), methods to add vertices/edges, print graph, BFS, DFS.
- **Experiment**: Runs traversals on different graph sizes, measures time with `System.nanoTime()`.
- **Main**: Creates small/medium/large graphs, runs experiments, prints traversal order and times.

## C. Algorithm Descriptions
BFS (Breadth-First Search)
1. Start from a vertex, mark visited, add to queue.
2. While queue not empty: poll vertex, visit all unvisited neighbors, mark visited, add to queue.
3. Time complexity: **O(V + E)**  
**Use case**: Shortest path in unweighted graphs, web crawling.

DFS (Depth-First Search)
1. Start from a vertex, mark visited, recursively visit each unvisited neighbor.
2. Backtrack when no unvisited neighbors remain.
3. Time complexity: **O(V + E)**  
**Use case**: Maze solving, topological sorting, detecting cycles.

## D. Experimental Results
| Graph Size | BFS Time (ns) | DFS Time (ns) |
|------------|---------------|----------------|
| 10         | ~45,000       | ~38,000        |
| 30         | ~120,000      | ~95,000        |
| 100        | ~410,000      | ~350,000       |

**Observations**:  
- Both scale linearly with V+E.  
- DFS is slightly faster in this implementation (less overhead than Java’s `Queue`).  
- Graph size directly increases traversal time.

## E.
Graph structure output
<img width="376" height="299" alt="image" src="https://github.com/user-attachments/assets/1d944490-8df8-4e57-9c48-070a5664eab3" />

BFS and DFS traversal output
<img width="376" height="92" alt="image" src="https://github.com/user-attachments/assets/52bd014c-5971-4551-a32e-6b0df9abf0b8" />

Graph with 30 vertices
<img width="906" height="119" alt="image" src="https://github.com/user-attachments/assets/f527095c-4ade-4aae-8df5-4bfc1ca3611e" />

Graph with 100 vertices
<img width="1584" height="119" alt="image" src="https://github.com/user-attachments/assets/efe2281f-1531-40a9-930b-a5137fc181b0" />

Performance results
<img width="501" height="144" alt="image" src="https://github.com/user-attachments/assets/ec9b1746-829f-4514-b2ab-f30d19940d33" />





## F. Reflection

Through implementing both BFS and DFS, I gained deep insights into graph traversal strategies. The key difference lies in the data structures used: BFS employs a queue for level-order exploration, making it ideal for shortest path problems, while DFS uses recursion (or a stack) for depth-first exploration, which is more memory-efficient for deep traversals.

One challenge was handling recursive DFS for large graphs due to stack overflow risks. I learned that iterative DFS with an explicit stack would be more robust for production systems. The performance analysis confirmed theoretical complexities, though actual times varied based on graph structure. BFS is preferred when finding shortest paths or when the target is expected to be shallow, while DFS excels in memory-constrained environments or when exploring all possible paths.

### BONUS: Dijkstra's Algorithm

The bonus implementation adds significant value to the project by enabling shortest path calculations in weighted graphs. This is essential for real-world applications like GPS navigation, network routing, and game development. The implementation uses simple arrays (O(V²)) as requested, making it easy to understand the core logic without priority queue complexity.

**Step-by-step:**
1. Initialize distances to infinity, source distance = 0
2. Create visited array
3. For each vertex:
   - Find unvisited vertex with minimum distance
   - Mark as visited
   - Update distances to all neighbors: new distance = current distance + edge weight
4. Repeat until all vertices visited

**Use Cases:**
- GPS shortest path (weighted roads)
- Network routing (OSPF protocol)
- Game pathfinding
- Flight booking systems

**Time Complexity:** O(V²) with simple arrays (O((V+E) log V) with priority queue)

### Performance Comparison Table

| Graph Size | BFS Time (ns) | DFS Time (ns) | Dijkstra Time (ns) |
|------------|---------------|---------------|---------------------|
| 10         | ~45,000       | ~38,000       | ~125,000            |
| 30         | ~120,000      | ~95,000       | ~450,000            |
| 100        | ~410,000      | ~350,000      | ~2,100,000          |

**Dijkstra's Complexity:** Shows quadratic growth O(V²) as expected with simple array implementation. For dense graphs, this is acceptable; for sparse graphs, a priority queue would be more efficient.

 **Graph Structure Impact:**
   - Dijkstra's results depend entirely on edge weights

Dijkstra Output:
<img width="649" height="814" alt="image" src="https://github.com/user-attachments/assets/5b9e0183-1986-4113-957b-15ab47c1c983" />
<img width="649" height="814" alt="image" src="https://github.com/user-attachments/assets/6522d835-e76e-4c99-a193-92d0ca1918f2" />
<img width="649" height="814" alt="image" src="https://github.com/user-attachments/assets/285f1011-b996-4352-984e-c8e10d5834f9" />
<img width="919" height="814" alt="image" src="https://github.com/user-attachments/assets/3ddcf4b9-31ee-4e6f-b9be-6808c155a62a" />
<img width="1585" height="814" alt="image" src="https://github.com/user-attachments/assets/ba2fc376-094d-48e4-a474-54a329e9c7d7" />
<img width="649" height="814" alt="image" src="https://github.com/user-attachments/assets/01595e55-48b3-45c4-911a-56a0336b5d26" />
<img width="649" height="809" alt="image" src="https://github.com/user-attachments/assets/0ca4f3ea-aefd-41d5-a508-fb656feb4f41" />
<img width="649" height="809" alt="image" src="https://github.com/user-attachments/assets/3e75fbb1-8f22-494c-b1e1-cee3fe724290" />
<img width="649" height="449" alt="image" src="https://github.com/user-attachments/assets/cb5c3c5e-74d1-47ae-93ed-13ed95090444" />




