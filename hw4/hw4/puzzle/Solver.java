package hw4.puzzle;
import java.util.*;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    // inner class of searchind node
    private class SearchNode {
        public WorldState ws;
        public int moves;
        public SearchNode prev;
        public int edtg;

        public SearchNode(WorldState initial) {
            ws = initial;
            moves = 0;
            prev = null;
            edtg = initial.estimatedDistanceToGoal();
        }
        public SearchNode(WorldState ws, int moves, SearchNode prev) {
            this.ws =  ws;
            this.moves = moves;
            this.prev = prev;
            // edtg is computed, not constructed
            this.edtg = ws.estimatedDistanceToGoal();
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode sn1, SearchNode sn2) {
            return (sn1.moves + sn1.edtg) - (sn2.moves + sn2.edtg);
        }
    }
    // attributes
    MinPQ<SearchNode> solu;
    SearchNode goal;

    /**
     * 1. initialize the world and state
     *
     * 2. Use the A* algorithm:
     *   1.(also base case) Check if peek in solu in goal,
     *     if it is, go to solution() and return
     *   2.if not, go to recursive cases:
     *   3.pop the peek, for each of its neighbors except
     *     its prev, construct them with (ws: neighbor, moves:
     *     cur.moves + 1, prev: cur)
     *   4.push them into solu with priority (ws.moves + ws.edtg())
     *   5. recursively do 2. until you find the goal
     * @param initial initial state of world
     */
    public Solver(WorldState initial) {
        solu = new MinPQ<>(new SearchNodeComparator());
        SearchNode sn = new SearchNode(initial);
        solu.insert(sn);
        solver();
    }

    private void solver() {
        if (solu.min().ws.isGoal()) {
            goal = solu.min();
            return;
        }
        SearchNode cur = solu.delMin();
        for (WorldState neigh: cur.ws.neighbors()) {
            if (cur.prev == null || !neigh.equals(cur.prev.ws)) {
                solu.insert(new SearchNode(neigh, cur.moves + 1, cur));
            }
        }
        solver();
    }
    public int moves() {
        return goal.moves;
    }

    public Iterable<WorldState> solution() {
        List<WorldState> s = new ArrayList<>();
        SearchNode tra = goal;
        while (tra != null) {
            s.add(0, tra.ws);
            tra = tra.prev;
        }
        return s;
    }
}
