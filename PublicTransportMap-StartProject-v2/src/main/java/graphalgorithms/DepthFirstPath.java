package graphalgorithms;

import model.Station;
import model.TransportGraph;

import java.util.*;

public class DepthFirstPath extends AbstractPathSearch {

    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    Stack<Station> path = new Stack<>();

    @Override
    public void search() {

        Station startNode = graph.getStation(startIndex);
        Station goalNode = graph.getStation(endIndex);

        // empty graph failsafe
        if (graph == null) {
            System.out.println("Empty graph!");
            return;
        }

        // just to be sure
        if (startNode == goalNode) {
            System.out.println("Goal Station Found at 0 depth");
            return;
        }

        // Mark the startNode as visited
        nodesVisited.add(graph.getStation(startIndex));

        //call helper
        depthFirstSearch(startIndex);

        // if endNode is found in visited nodes, fill nodesInPath
        for (Station s : path) {
            pathTo(graph.getIndexOfStationByName(s.getStationName()));
            if (graph.getIndexOfStationByName(s.getStationName()) == endIndex) {
                break;
            }
        }
    }

    /**
     * @param vertex
     * @return true if vertex is the end station or leads to the endstation
     */

    private boolean depthFirstSearch(int vertex) {
        path.add(graph.getStation(vertex));
        boolean result = (vertex == endIndex);

        for (int s : graph.getAdjacentVertices(vertex)) {
            if (!nodesVisited.contains(graph.getStation(s))) { // dont visit doubles
                nodesVisited.add(graph.getStation(s));
                if (depthFirstSearch(s)) {
                    result = true;
                }
            }
        }
        if (!result){
            path.pop();
        }
        return result;
    }
}
