package pb.gen;

import java.util.*;

public class TrappingRainWaterGraph {
    private int rows;
    private int columns;
    private int[][] heightMap;
    private Map<Integer, GraphNode> graph = new HashMap<>();
    private PriorityQueue pq = new PriorityQueue();

    public static void main(String[] a) {
        int[][] heightMap = {{1, 4, 3, 1, 3, 2}, {3, 2, 1, 3, 2, 4}, {2, 3, 3, 2, 3, 1}};
        TrappingRainWaterGraph trappingRainWaterGraph = new TrappingRainWaterGraph();
        int result = trappingRainWaterGraph.trapRainWater(heightMap);
        System.out.println(result);
    }

    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length <= 2
                || heightMap[0].length <= 2) {
            return 0;
        }
        this.rows = heightMap.length;
        this.columns = heightMap[0].length;
        this.heightMap = heightMap;
        initGraph();
        dijkstra();
        int water = 0;
        for (GraphNode node : graph.values()) {
            if (node.nodeId >= 0) {
                water += Math.max(0, node.dist - heightMap[node.x][node.y]);
            }
        }
        return water;
    }

    private void dijkstra() {
        pq.insert(graph.get(-1));
        for (GraphNode node : graph.values()) {
            pq.insert(node);
        }

        while (!pq.isEmpty()) {
            GraphNode node = pq.extractMin();
            for (Edge edge : node.children) {
                relax(node, edge);
            }
        }
    }

    private void relax(GraphNode node, Edge edge) {
        GraphNode child = edge.child;
        if (child.dist == null || child.dist > Math.max(node.dist, edge.weight)) {
            pq.remove(child);
            child.dist = Math.max(node.dist, edge.weight);
            pq.insert(child);
        }
    }

    class PriorityQueue {
        private TreeSet<GraphNode> bst = new TreeSet<>();

        public void insert(GraphNode node) {
            bst.add(node);
        }

        public GraphNode extractMin() {
            return bst.pollFirst();
        }

        public void remove(GraphNode node) {
            bst.remove(node);
        }

        public boolean isEmpty() {
            return bst.size() == 0;
        }
    }

    private void initGraph() {
        GraphNode source = new GraphNode(-1);
        source.dist = 0;
        graph.put(-1, source);
        for (int c = 0; c < columns - 1; c++) {
            source.children.add(new Edge(0, getNode(0, c)));
        }

        for (int r = 0; r < rows - 1; r++) {
            source.children.add(new Edge(0, getNode(r, columns - 1)));
        }

        for (int c = columns - 1; c > 0; c--) {
            source.children.add(new Edge(0, getNode(rows - 1, c)));
        }

        for (int r = rows - 1; r > 0; r--) {
            source.children.add(new Edge(0, getNode(r, 0)));
        }

        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                GraphNode node = getNode(r, c);
                for (int i = 0; i < 4; i++) {
                    int newx = node.x + dx[i];
                    int newy = node.y + dy[i];
                    if (newx >= 0 && newx < rows && newy >= 0 && newy < columns) {
                        node.children.add(new Edge(heightMap[node.x][node.y],
                                getNode(newx, newy)));
                    }
                }
            }
        }
    }

    private GraphNode getNode(int x, int y) {
        int nodeId = x * columns + y;
        if (!graph.containsKey(nodeId)) {
            graph.put(nodeId, new GraphNode(nodeId));
        }
        return graph.get(nodeId);
    }

    class GraphNode implements Comparable<GraphNode> {
        int nodeId;
        Integer dist = null;
        List<Edge> children = new ArrayList<>();
        int x;
        int y;

        GraphNode(int nodeId) {
            this.nodeId = nodeId;
            this.x = nodeId / columns;
            this.y = nodeId % columns;
        }

        @Override
        public int compareTo(GraphNode other) {
            if (nodeId == other.nodeId) {
                return 0;
            }
            if (dist == null && other.dist == null) {
                return nodeId - other.nodeId;
            } else if (dist == null && other.dist != null) {
                return 1;
            } else if (dist != null && other.dist == null) {
                return -1;
            } else {
                return dist.intValue() == other.dist.intValue() ? nodeId
                                                                  - other.nodeId : dist - other.dist;
            }
        }
    }

    class Edge {
        int weight;
        GraphNode child;

        Edge(int weight, GraphNode child) {
            this.weight = weight;
            this.child = child;
        }
    }
}
