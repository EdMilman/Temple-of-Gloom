package student;

import game.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Depth First Search - will calculate the shortest possible distance across a graph of weighted edges, capable of
 * choosing the start and finish locations, and returning the total weight of the path
 *
 * @author Edward Milman
 */
public class DFS {
    private final List<Edge> edges = new ArrayList<>();
    private Set<Vertex> visited = new HashSet<>();
    private Map<Vertex, Vertex> previous = new HashMap<>();
    private Map<Vertex, Integer> weights = new HashMap<>();
    private int weightOfPath;

    /**
     * constructor for class, populates this.edges
     *
     * @param allNodes - a list of all nodes in the game
     */
    public DFS(List<Node> allNodes) {
        for (Node node : allNodes) {
            Set<Node> neighbours = node.getNeighbours();
            for (Node neighbour : neighbours) {
                edges.add(new Edge(new Vertex(node), new Vertex(neighbour)));
            }
        }
    }

    /**
     * clears all existing data and sets starting vertex, starts finding neighbouring vertices and recording
     * cumulative weights from the starts vertex to other vertices
     *
     * @param start - the vertex to start from
     */
    public void from(Vertex start) {
        weights.clear();
        visited.clear();
        previous.clear();
        weights.put(start, 0);
        visited.add(start);
        while (!visited.isEmpty()) {
            // selects vertex to work with, starting with the smallest distance from the start
            Vertex vertex = visited.stream().reduce((x, y) -> getShortestDistance(x) < getShortestDistance(y) ? x : y).get();
            // removes node once calculated
            visited.remove(vertex);
            findMinimalDistances(vertex);
        }
    }

    /**
     * finds weights of edges from one node to its neighbours and stores the weights in this.weights
     * (cumulative total of all weights from start to get to that node)
     *
     * @param node - the node to find the weights of the outgoing edges of
     */
    private void findMinimalDistances(Vertex node) {
        // finds vertices connected to given vertex by an edge
        List<Vertex> adjacentNodes = otherVertexOfEdge(node);
        // calculates weight from start node to every node neighbouring the given node
        for (Vertex neighbour : adjacentNodes) {
            if (getShortestDistance(neighbour) > getShortestDistance(node) + getWeight(node, neighbour)) {
                weights.put(neighbour, getShortestDistance(node) + getWeight(node, neighbour));
                // adds vertices to map for retrieval later
                previous.put(neighbour, node);
                // adds neighbours to list to be worked on
                visited.add(neighbour);
            }
        }
    }

    /**
     * finds the weight of the edge between 2 given vertices
     *
     * @param start  - initial vertex of the edge
     * @param target - terminal vertex of the edge
     * @return int - integer representing the weight of the edge
     */
    private int getWeight(Vertex start, Vertex target) {
        return edges.stream().filter(x -> x.getSource().equals(start)).filter(x -> x.getDestination().equals(target))
                .map(Edge::getWeight).findFirst().get();
    }

    /**
     * given a vertex, will find every other vertex connected to that one by an edge
     *
     * @param node - the vertex you want to find the connected vertices of
     * @return List of vertices that are connected to the given edge
     */
    private List<Vertex> otherVertexOfEdge(Vertex node) {
        return edges.stream().filter(x -> x.getSource().equals(node))
                .map(Edge::getDestination).collect(Collectors.toList());
    }

    /**
     * returns the smallest total weight of edges from the start vertex to the destination vertex (if available)
     *
     * @param destination - the vertex to find the total weight of edges to
     * @return integer - either the highest possible int value if the weight is not calculated, or the total weight
     * of all edges to get to that vertex from the start vertex
     */
    private int getShortestDistance(Vertex destination) {
        return weights.get(destination) == null ? Integer.MAX_VALUE : weights.get(destination);
    }

    /**
     * creates a list of vertices to be used to move the player from one location to another
     *
     * @param target - the vertex you are looking to get the path to
     * @return - LinkedList in order from the start vertex to the destination vertex via the shortest possible path
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        path.add(target);
        while (previous.get(target) != null) {
            target = previous.get(target);
            path.add(target);
        }
        // path needs to be reversed
        Collections.reverse(path);
        // first item is starting vertex, so needs to be removed
        path.removeFirst();
        // stores weight of whole path
        this.weightOfPath = weights.get(path.getLast());
        return path;
    }

    /**
     * getter for the total weight of the path from the start vertex to the end vertex
     *
     * @return int of the total path weight
     */
    public int getWeightOfPath() {
        return weightOfPath;
    }

    /**
     * wrapper method to call from and getPath with one call instead of 2
     *
     * @param start  - the vertex to start from
     * @param finish - the vertex to travel to
     * @return LinkedList of vertices giving the shortest path from start to finish
     */
    public LinkedList<Vertex> route(Vertex start, Vertex finish) {
        from(start);
        return getPath(finish);
    }

}
