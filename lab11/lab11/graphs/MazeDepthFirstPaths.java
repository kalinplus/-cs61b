package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeDepthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeDepthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     *  base case: if v == t, target is found, return
     *  recursive case:
     *  1. set current vertex to marked
     *  2. check base case
     *  3. for each neighbor w of v
     *    a. check if w is marked, if it does, skip it
     *    b. set distTo and edgeTo
     *    c. recursively find dfs(w)
     */
    private void dfs(int v) {
        marked[v] = true;
        announce();
        if (v == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        for (int w: maze.adj(v)) {
            // why you ignore this case? Think about it
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfs(w);
                if (targetFound) {
                    return;
                }
            }
        }

//        marked[v] = true;
//        announce();
//
//        if (v == t) {
//            targetFound = true;
//        }
//
//        if (targetFound) {
//            return;
//        }
//
//        for (int w : maze.adj(v)) {
//            if (!marked[w]) {
//                edgeTo[w] = v;
//                announce();
//                distTo[w] = distTo[v] + 1;
//                dfs(w);
//                if (targetFound) {
//                    return;
//                }
//            }
//        }
    }

    @Override
    public void solve() {
        dfs(s);
    }
}

