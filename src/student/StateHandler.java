package student;

import game.EscapeState;
import game.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle EscapeState, will extract information and store in appropriate data structures required by
 * DFS
 *
 * @author Edward Milman
 */
public class StateHandler {
    private Map<Integer, Vertex> goldByTile = new HashMap<>();
    private List<Node> allNodes;
    private final Node EXIT;
    private final Node START;

    public StateHandler(EscapeState state) {
        this.allNodes = new ArrayList<>(state.getVertices());
        this.EXIT = state.getExit();
        this.START = state.getCurrentNode();
        allNodes.forEach(x -> goldByTile.put(x.getTile().getOriginalGold(), new Vertex(x)));
    }

    /**
     * locates the tile with the largest amount of gold and returns the location, while removing from the list of
     * gold locations
     *
     * @return - Vertex that has the largest amount of gold on it
     */
    public Vertex findMostGold() {
        int mostGold = goldByTile.keySet().stream().reduce((x, y) -> x > y ? x : y).get();
        Vertex temp = goldByTile.get(mostGold);
        goldByTile.remove(mostGold);
        return temp;
    }

    public List<Node> getAllNodes() {
        return allNodes;
    }

    public Vertex getEXIT() {
        return new Vertex(EXIT);
    }

    public Vertex getSTART() {
        return new Vertex(START);
    }
}
