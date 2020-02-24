package graphalgorithms;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraShortestPath extends AbstractPathSearch {

    public DijkstraShortestPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
//        distTo[graph.getIndexOfStationByName(start)] = 0.0;
    }

    private Integer[] prev;

    @Override
    public void search() {
        //
        int degree = graph.getNumberEdges()/graph.getNumberOfStations();
        IndexMinPQ<Double> ipq = new IndexMinPQ<>(graph.getNumberOfStations());
        ipq.insert(startIndex, 0.0);

        double[] distTo = new double[graph.getNumberOfStations()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[startIndex] = 0.0;

        //to reconstruct the shortest path
        prev = new Integer[graph.getNumberOfStations()];

        while (!ipq.isEmpty()) {

            int nodeId = ipq.minIndex();

            nodesVisited.add(graph.getStation(nodeId));
            double minValue = ipq.minKey();

            if (minValue > distTo[nodeId]) continue;

            for (int station : graph.getAdjacentVertices(nodeId)) {
                //if (nodesVisited.get(station) == null) continue;
                if (nodesVisited.contains(graph.getStation(station))) continue;

                double newDist = distTo[nodeId] + graph.getConnection(
                        graph.getIndexOfStationByName(graph.getStation(station-1).getStationName()),
                        graph.getIndexOfStationByName(graph.getStation(station).getStationName())).getWeight();
                if (newDist < distTo[station]) {
                    prev[station] = nodeId;
                    distTo[station] = newDist;

                    if (!ipq.contains(station)) {
                        ipq.insert(station,newDist);
                        System.out.println(station +":  "+newDist);
                    } else {
                        ipq.decreaseKey(station, newDist);
                        System.out.println("delete");
                    }
                }
                if (nodeId == endIndex) {
                    break;
                }
            }
        }
        reconstructPath();
    }

    private void reconstructPath() {
        List<Station> path = new ArrayList<>();
        //double dist = //hmm;

        for(Integer at = endIndex; at != null; at = prev[at]) {
            path.add(graph.getStation(at));
            pathTo(at);
        }
        Collections.reverse(path);
    }
}
