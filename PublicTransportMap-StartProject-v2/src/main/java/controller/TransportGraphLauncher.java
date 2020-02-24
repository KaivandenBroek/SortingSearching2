package controller;

import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
//        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
//        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
//        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
//        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        /* Assignment B Metro lines */

        String[] redLine = {"red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        double[] redLineWeights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        String[] blueLine = {"blue", "metro", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "violetplantsoen"};
        double[] blueLineWeights = {6.0, 5.3, 5.1, 3.3};
        String[] purpleLine = {"purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        double[] purpleLineWeights = {6.2, 5.2, 3.8, 3.6,};
        String[] greenLine = {"green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        double[] greenLineWeights = {5.0, 3.7, 6.9, 3.9, 3.4};
        String[] yellowLine = {"yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        double[] yellowLineWeights = {26.0, 19.0, 37.0, 25.0, 22.0, 28.0};


        //Use the builder to build the graph from the String array.
        TransportGraph transportGraph;
        TransportGraph.Builder helper = new TransportGraph.Builder();

        helper.addLine(redLine);
        helper.addLine(blueLine);
        helper.addLine(purpleLine);
        helper.addLine(greenLine);
        helper.addLine(yellowLine);

        helper.buildStationSet();
        helper.addLinesToStations();
        helper.buildConnections();

        helper.addWeightToEdge(redLine, redLineWeights);
        helper.addWeightToEdge(blueLine, blueLineWeights);
        helper.addWeightToEdge(purpleLine, purpleLineWeights);
        helper.addWeightToEdge(greenLine, greenLineWeights);
        helper.addWeightToEdge(yellowLine, yellowLineWeights);

        transportGraph = helper.build();

//        to test the builder:
        System.out.println(transportGraph);

//        to test the DepthFirstPath algorithm
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "Haven", "Violetplantsoen");
        dfpTest.search();
        System.out.println("Result of DepthFirstSearch: ");
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

//       to test the BreadthFirstPath algorithm
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "Haven", "Violetplantsoen");
        bfsTest.search();
        System.out.println("Result of BreadthFirstSearch: ");
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();

        //       to test the BreadthFirstPath algorithm
//        DijkstraShortestPath dspTest = new DijkstraShortestPath(transportGraph, "Haven", "Violetplantsoen");
//        dspTest.search();
//        System.out.println("Result of DijkstraShortestPath: ");
//        System.out.println(dspTest);
//        dspTest.printNodesInVisitedOrder();

    }
}
