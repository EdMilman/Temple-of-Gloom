package student;

/**
 * Wrapper class for game.Node
 * will store game.Nodes in a wrapper giving access to the node, as well as the equals method required in DFS
 *
 * @author Edward Milman
 */
public class Vertex {
    private game.Node node;
    private long id;


    public Vertex(game.Node node) {
        this.node = node;
        this.id = node.getId();
    }

    public game.Node getNode() {
        return node;
    }

    /**
     * checks for equality of objects
     * @param o object to compare to
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        if (id != vertex.id) return false;
        return node != null ? node.equals(vertex.node) : vertex.node == null;
    }

    /**
     * hashcode to help compare objects
     * @return int hashcode of object
     */
    @Override
    public int hashCode() {
        int result = node != null ? node.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
