package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    int s;
    Maze maze;
    boolean cycleFound;
    int[] fakeEdgeTo;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = 0;
        cycleFound = false;
        distTo[s] = 0;
        edgeTo[s] = s;
        fakeEdgeTo = new int[maze.V()];
    }

    /**
     * 1. first we just find the cycle without considering that
     * only the cycle can have edge linked
     * 2. start detection from a soruce like 0
     * 3. do dfs for v:
     *   a. marked v as visited
     *   b. for every adj w of v:
     *     (1) if w is marked and is not parent of v (edge[v] != w)
     *     we detect a cycle, we need to draw the cycle
     *     (2) if marked but parent, nothing happen
     *     (3) if not marked, set distTo and edgeTo, recursively do dfs
     *
     * 4. how can we draw the cycle? We also need the increasing process of marking !!!
     * The answer is, define ourselves "edgeTo", which will not lead to line drawing but record
     * the parent state
     */
    @Override
    public void solve() {
        findCycle(s);
    }

    // Helper methods go here
    private void findCycle(int v) {
        if (cycleFound) {
            return;
        }
        marked[v] = true;
        for (int w: maze.adj(v)) {
            if (marked[w] && fakeEdgeTo[v] != w) {
                // v is the end of edgeTo, also the end of distTo, the last of cycle
                // w is the start of cycle
                drawCycle(v, w);
                cycleFound = true;
                // regard announce() as onChage(), not depend on the linear exe process
                announce();
            }
            if (!marked[w]) {
                distTo[w] = distTo[v] + 1;
                fakeEdgeTo[w] = v;
                announce();
                findCycle(w);
                if (cycleFound) {
                    return;
                }
            }
        }
    }

    /**
     * @param end the last visited vertex of the cycle
     * @param start the first visited vertex of the cycle
     */
    private void drawCycle(int end, int start) {
        edgeTo[end] = start;
        while (end != start) {
            int prev = end;
            end = fakeEdgeTo[end];
            edgeTo[end] = prev;
        }
    }
}

