package game;

import java.util.Map;

/**
 * An ExitEdge represents an immutable directed, weighted edge.
 */
public class Edge {

  /**
   * The length of this edge
   */
  public final int length;
  /**
   * The Vertex this edge is coming from
   */
  private final Node src;
  /**
   * The node this edge is going to
   */
  private final Node dest;

  /**
   * Constructor: an edge of length len from src to dest.
   */
  public Edge(Node src, Node dest, int len) {
    this.src = src;
    this.dest = dest;
    this.length = len;
  }

  /**
   * Constructor: an edge like e ...
   */
  public Edge(Edge e, Map<Node, Node> isomorphism) {
    src = isomorphism.get(e.src);
    dest = isomorphism.get(e.dest);
    length = e.length;
  }

  /**
   * Return the <tt>Vertex</tt> on this <tt>ExitEdge</tt> that is not equal to n.
   * Throw an <tt>IllegalArgumentException</tt> if <tt>n</tt> is not in this <tt>ExitEdge</tt>.
   */
  public Node getOther(Node n) {
    if (src == n) {
      return dest;
    }
    if (dest == n) {
      return src;
    }
    throw new IllegalArgumentException("getOther: ExitEdge must contain provided node");
  }

  /**
   * Return the length of this <tt>ExitEdge</tt>
   */
  public int length() {
    return length;
  }

  /**
   * Return the source of this edge.
   */
  public Node getSource() {
    return src;
  }

  /**
   * Return the destination of this edge.
   */
  public Node getDest() {
    return dest;
  }
}
