package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    /**
     * 1. init a queue path, enqueue s
     * 2. while path is not empty, pop one (regarded as visit), set marked, and check if v == t
     * 3. and for all of its adjs:
     *   a. check marked
     *   b. if not, set distTo and edgeTo
     */
    private void bfs() {
        Queue<Integer> path = new Queue<Integer>();
        path.enqueue(s);

        while (!path.isEmpty()) {
            int v = path.dequeue();
            marked[v] = true;
            announce();
            if (v == t) {
                return;
            }
            for (int w: maze.adj(v)) {
                if (!marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    path.enqueue(w);
//                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

