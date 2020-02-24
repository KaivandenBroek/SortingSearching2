package model;

import java.util.*;

public class TransportGraph {

    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;
    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;

    public TransportGraph(int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     *               The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        stationList.add(vertex);
        stationIndices.put(vertex.getStationName(), stationList.size());
    }


    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The grap is bidirected, so edge(to, from) should also be added.
     *
     * @param from from
     * @param to   to
     */
    private void addEdge(int from, int to) {
        adjacencyLists[from].add(to);
        adjacencyLists[to].add(from);
        numberOfConnections++;
    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * 1    The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * 2    The method adds the connecion to the connections 2D-array.
     * 3    The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     *
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        // 1
        int indexFrom = 0;
        int indexTo = 0;
        for (int i = 0; i < stationList.size(); i++) {
            if (stationList.get(i).getStationName().equals(connection.getFrom().getStationName())) {
                indexFrom = i;
            }
            if (stationList.get(i).getStationName().equals(connection.getTo().getStationName())) {
                indexTo = i;
            }
        }
        stationIndices.put(connection.getFrom().getStationName(), indexFrom);
        stationIndices.put(connection.getTo().getStationName(), indexTo);
        addEdge(indexFrom, indexTo);  // dit kan niet goed zijn, nu loopt hij de hele lijst vol met hetzelfde-------------------------------
        // 2 / 3
        Connection reverseConnection = new Connection(connection.getTo(), connection.getFrom());
        for (int i = 0; i < numberOfStations; i++) {
            for (int j = 0; j < numberOfStations; j++) {
                if (connections[i][j] == null) {
                    connections[i][j] = connection;
                }
                if (connections[i][j] == null) {
                    connections[i][j] = reverseConnection;
                }
            }
        }
    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-");
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private Set<Connection> connectionSet;
        private List connectionsWeight;

        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return ?
         */
        public Builder addLine(String[] lineDefinition) {
            Line line = new Line(lineDefinition[1], lineDefinition[0]);

            // loop starts at 2 since the lineDefinition also holds the name/line, and we dont need them
            for (int i = 2; i < lineDefinition.length; i++) {
                Station station = new Station(lineDefinition[i]);
                line.addStation(station);
            }
            lineList.add(line);
            return this;
        }


        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         *
         * @return ?
         */
        public Builder buildStationSet() {
            for (Line line : lineList) {
                stationSet.addAll(line.getStationsOnLine());
            }
            return this;
        }

        /**
         * For every station on the set of station, add the lines of that station to the lineList in the station
         *
         * @return ?
         */
        public Builder addLinesToStations() {
            for (Station station : stationSet) {
                for (Line line : lineList) {
                    if (line.getStationsOnLine().contains(station)) {
                        station.addLine(line);
                    }
                }
            }
            System.out.println();
            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         *
         * @return ?
         */
        public Builder buildConnections() {
            for (Line line : lineList) {
                for (int i = 1; i < line.getStationsOnLine().size(); i++) {
                    Station from = line.getStationsOnLine().get(i - 1);
                    Station to = line.getStationsOnLine().get(i);

                    Connection connection = new Connection(from, to, 0.0, line);
                    connectionSet.add(connection);
                }
            }
            return this;
        }


        /**
         * Method that adds the weights of the connections to all the edges in the graph.
         *
         */
        public void addWeightToEdge(String[] lineDefinition, double[] weight) {
            Line line = new Line(lineDefinition[1], lineDefinition[0]);

            // loop starts at 2 since the lineDefinition also holds the name/line, and we dont need them
            for (int i = 2; i < lineDefinition.length; i++) {
                Station station = new Station(lineDefinition[i]);
                line.addStation(station);
            }

            // find correct connection
            for (Connection connection : connectionSet) {
                for (int i = 1; i < line.getStationsOnLine().size(); i++) {

                    String from = line.getStationsOnLine().get(i - 1).getStationName();
                    String to = line.getStationsOnLine().get(i).getStationName();

                    // if the connection is found, set weight
                    if (from.equals(connection.getFrom().getStationName()) && to.equals(connection.getTo().getStationName())) {

                        connection.setWeight(weight[i-1]);
                        break;
                    }
                }

            }
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are added as vertices to the graph.
         * All connections of the connectionSet are added as edges to the graph.
         *
         * @return .
         */
        public TransportGraph build() {
            TransportGraph graph = new TransportGraph(stationSet.size());
            for (Station station : stationSet) {
                graph.addVertex(station);
            }
            for (Connection connection : connectionSet) {
                graph.addEdge(connection);
            }
            return graph;
        }





        //                    Station from = line.getStationsOnLine().get(i - 1);
//                    Station to = line.getStationsOnLine().get(i);
//
//                    // if the connection is found, set weight
//                    if (from.getStationName().equals(connection.getFrom().getStationName()) && to.getStationName().equals(connection.getTo().getStationName())) {


        // System.out.println(connection.getLine()+ ": " +connection.getWeight());

    }
}
