package graphalgorithms;

import model.Station;
import model.TransportGraph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {

        // empty graph failsafe
        if (graph == null) {
            System.out.println("Empty graph!");
            return;
        }

        // just to be sure
        if (startIndex == endIndex) {
            System.out.println("Goal Station Found at 0 depth");
            return;
        }

        // to keep track of the paths
        Stack<Station> q = new Stack<>();
        Queue<Stack<Station>> qs = new LinkedList<>();
        q.add(graph.getStation(startIndex));
        qs.add(q);

        // Mark the startNode as visited
        nodesVisited.add(graph.getStation(startIndex));

        while (!qs.isEmpty()) {
            Stack<Station> current = qs.remove();

            // if the endNode is found, fill nodesInPath
            if (graph.getIndexOfStationByName(current.peek().getStationName()) == endIndex) {
                for (Station s : current) {
                    pathTo(graph.getIndexOfStationByName(s.getStationName()));
                }
                return;
            }

            // loop trough graph and keep track of all possible routes
            for (int s : graph.getAdjacentVertices(graph.getIndexOfStationByName(current.peek().getStationName()))) {
                if (!nodesVisited.contains(graph.getStation(s))) { // dont visit doubles
                    Stack<Station> p = new Stack<>();
                    p.addAll(current);
                    p.add(graph.getStation(s));
                    nodesVisited.add(graph.getStation(s));
                    qs.add(p);
                }
            }
        }
    }
}
