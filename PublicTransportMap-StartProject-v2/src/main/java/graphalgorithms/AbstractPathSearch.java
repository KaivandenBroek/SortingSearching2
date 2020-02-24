package graphalgorithms;

import model.Line;
import model.Station;
import model.TransportGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class that contains methods and attributes shared by the DepthFirstPath en BreadthFirstPath classes
 */
public abstract class AbstractPathSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int transfers = 0;
    protected List<Station> nodesVisited;
    protected List<Station> nodesInPath = new LinkedList<>();
    protected LinkedList<Integer> verticesInPath = new LinkedList<>();
    protected TransportGraph graph;
    protected final int startIndex;
    protected final int endIndex;


    public AbstractPathSearch(TransportGraph graph, String start, String end) {
        startIndex = graph.getIndexOfStationByName(start);
        endIndex = graph.getIndexOfStationByName(end);
        this.graph = graph;
        nodesVisited = new ArrayList<>();
        marked = new boolean[graph.getNumberOfStations()];
        edgeTo = new int[graph.getNumberOfStations()];
    }

    public abstract void search();

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return return
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }


    /**
     * Method to build the path to the vertex, index of a Station.
     * First the LinkedList verticesInPath, containing the indexes of the stations, should be build, used as a stack
     * Then the list nodesInPath containing the actual stations is build.
     * Also the number of transfers is counted.
     *
     * @param vertex The station (vertex) as an index
     */
    public void pathTo(int vertex) {
        verticesInPath.add(vertex);
        nodesInPath.add(graph.getStation(vertex));
        countTransfers();
    }

    /**
     * Method to count the number of transfers in a path of vertices.
     * Uses the line information of the connections between stations.
     * If two consecutive connections are on different lines there was a transfer.
     */
    public void countTransfers() {

        int n = nodesInPath.size();

        if (n >= 3) {
            Station s1 = nodesInPath.get(nodesInPath.size() - 1);
            Station s2 = nodesInPath.get(nodesInPath.size() - 2);
            Station s3 = nodesInPath.get(nodesInPath.size() - 3);
            for (Line l : s1.getCommonLines(s2)) {
                if (s1.getCommonLines(s3).contains(l)) {
                    return;
                }
            }
            transfers++;
        }
    }


    /**
     * Method to print all the nodes that are visited by the search algorithm implemented in one of the subclasses.
     */
    public void printNodesInVisitedOrder() {
        System.out.print("Nodes in visited order: ");
        for (Station vertex : nodesVisited) {
            System.out.print(vertex.getStationName() + " ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder(String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)));
        resultString.append(nodesInPath).append(" with " + transfers).append(" transfers");
        return resultString.toString();
    }

    /**
     * Method that counts tho total weight of a path.
     *
     * @return weight
     */
    public double getTotalWeight(List<Station> nodesInPath) {
        double totalWeight = 0.0;
//        int n = nodesInPath.size();
//
//        if (n >= 2) {
//            Station s1 = nodesInPath.get(nodesInPath.size() - 1);
//            Station s2 = nodesInPath.get(nodesInPath.size() - 2);
//            for (Line l : s1.getCommonLines(s2)) {
//                if (s1.getCommonLines(s3).contains(l)) {
//
//                    break;
//                }
//            }
//        }
        return totalWeight;
    }
}
