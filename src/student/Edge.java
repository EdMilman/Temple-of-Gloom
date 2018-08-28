package student;

/**
 * class to represent an edge within a graph
 *
 * @author Edward Milman
 */
public class Edge {
    private final int weight;
    private Vertex source;
    private Vertex destination;

    public Edge(Vertex source, Vertex destination) {
        this.weight = source.getNode().getEdge(destination.getNode()).length();
        this.source = source;
        this.destination = destination;
    }

    /**
     * getter for weight of edge
     * @return int representing weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * getter for source of edge
     * @return Vertex representing the source
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * getter for destination of edge
     * @return Vertex representing the destination
     */
    public Vertex getDestination() {
        return destination;
    }
}
