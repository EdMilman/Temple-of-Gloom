package student;

import game.EscapeState;
import game.ExplorationState;
import game.NodeStatus;

import java.util.*;
import java.util.stream.Collectors;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        // stack for all possible (untaken) moves
        Stack<Long> possibleMoves = new Stack<>();
        //stack for every move taken
        Stack<Long> pathTaken = new Stack<>();
        // start loop until reaching target
        while (state.getDistanceToTarget() > 0) {
            // push current location onto path taken
            pathTaken.push(state.getCurrentLocation());
            // calculates all possible moves, ordered with the move that will go towards the goal first
            List<Long> moves = state.getNeighbours().stream().filter(x -> !pathTaken.contains(x.getId()))
                    .sorted((Comparator.comparing(x -> x.getDistanceToTarget())))
                    .map(NodeStatus::getId).collect(Collectors.toList());
            // if potential moves are available, takes the first move
            if (moves.size() != 0) {
                state.moveTo(moves.get(0));
                // push current location onto possible moves
                possibleMoves.push(state.getCurrentLocation());
            } else {
                // no moves possible, so have to backtrack
                possibleMoves.pop();
                state.moveTo(possibleMoves.peek());
            }
        }
    }


    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Vertex objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed from.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */

    public void escape(EscapeState state) {
        GetTheGold.getTheGold(new LinkedList<>(), new StateHandler(state), state.getTimeRemaining())
                .forEach(x -> {
                    if (state.getCurrentNode().getTile().getGold() > 0) state.pickUpGold();
                    state.moveTo(x.getNode());
                });
    }
}