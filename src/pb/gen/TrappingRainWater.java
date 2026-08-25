package pb.gen;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class TrappingRainWater {
    /**
     * @param heights : a matrix of integers
     * @return: an integer
     */
    class Node {
        int x;
        int y;
        int h;

        public Node(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }
    }

    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};

    public int trapRainWater(int[][] heights) {
        // write your code here
        if (heights == null || heights.length <= 2 || heights[0].length <= 2) {
            return 0;
        }

        int n = heights.length;
        int m = heights[0].length;
        boolean[][] isVisited = new boolean[n][m];

        Queue<Node> queue = new PriorityQueue<Node>(1, new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return a.h - b.h;
            }
        });
        // add first and last column
        for (int i = 0; i < n; i++) {
            queue.offer(new Node(i, 0, heights[i][0]));
            isVisited[i][0] = true;
            queue.offer(new Node(i, m - 1, heights[i][m - 1]));
            isVisited[i][m - 1] = true;
        }
        // add first and last row
        for (int j = 1; j < m - 1; j++) {
            queue.offer(new Node(0, j, heights[0][j]));
            isVisited[0][j] = true;
            queue.offer(new Node(n - 1, j, heights[n - 1][j]));
            isVisited[n - 1][j] = true;
        }

        int sum = 0;
        while (!queue.isEmpty()) {
            Node curt = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nextX = curt.x + dx[i];
                int nextY = curt.y + dy[i];
                if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < m
                        && !isVisited[nextX][nextY]) {
                    if (heights[nextX][nextY] < curt.h) {
                        sum += curt.h - heights[nextX][nextY];
                        queue.offer(new Node(nextX, nextY, curt.h));
                        isVisited[nextX][nextY] = true;
                    } else {
                        queue.offer(new Node(nextX, nextY,
                                heights[nextX][nextY]));
                        isVisited[nextX][nextY] = true;
                    }
                }
            }
        }
        return sum;
    }

    public int trappingRainWater(int[][] heights) {
        // Input validation
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return 0;
        }

        int m = heights.length;
        int n = heights[0].length;

        // Initialize min-heap minheap, visited matrix visited[][]
        PriorityQueue<Node> minheap = new PriorityQueue<Node>(1, new Comparator<Node>() {
            public int compare(Node c1, Node c2) {
                if (c1.h > c2.h) {
                    return 1;
                } else if (c1.h < c2.h) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        int[][] visited = new int[m][n];

        // Traverse the outer cells, add to the minheap
        for (int i = 0; i < m; i++) {
            minheap.offer(new Node(i, 0, heights[i][0]));
            minheap.offer(new Node(i, n - 1, heights[i][n - 1]));

            visited[i][0] = 1;
            visited[i][n - 1] = 1;
        }

        for (int j = 0; j < n; j++) {
            minheap.offer(new Node(0, j, heights[0][j]));
            minheap.offer(new Node(m - 1, j, heights[m - 1][j]));

            visited[0][j] = 1;
            visited[m - 1][j] = 1;
        }

        // Helper direction array
        int[] dirX = new int[]{0, 0, -1, 1};
        int[] dirY = new int[]{-1, 1, 0, 0};

        int water = 0;

        // Starting from the min height cell, check 4 direction
        while (!minheap.isEmpty()) {
            Node now = minheap.poll();

            for (int k = 0; k < 4; k++) {
                int x = now.x + dirX[k];
                int y = now.y + dirY[k];

                if (x < m && x >= 0 && y < n && y >= 0 && visited[x][y] != 1) {
                    minheap.offer(new Node(x, y, Math.max(now.h, heights[x][y])));
                    visited[x][y] = 1;

                    // Fill in water or not
                    water += Math.max(0, now.h - heights[x][y]);
                }
            }
        }
        return water;
    }
}
