package student;

import java.util.LinkedList;

/**
 * class to hold static function to find gold on the escape stage. this function will keep going to the tile with the
 * most gold while time allows, before heading for the exit
 *
 * @author Edward Milman
 */
class GetTheGold {

    /**
     * static function to search for the highest value gold tile, and calculate if it is possible to get there and to
     * the exit without running out of time. When if is not possible to get to the gold tile, the method will go to
     * the exit
     *
     * @param path - a list of vertices for the route to take
     * @param s    - a StateHandler giving quicker access to functions in EscapeState
     * @param time - the remaining time left before the end of the game
     * @return LinkedList of vertices representing the path to be taken by the player
     */
    protected static LinkedList<Vertex> getTheGold(LinkedList<Vertex> path, StateHandler s, int time) {
        DFS fromGoldToExit = new DFS(s.getAllNodes());
        DFS fromCurrentToExit = new DFS(s.getAllNodes());
        DFS fromCurrentToGold = new DFS(s.getAllNodes());
        Vertex mostGold = s.findMostGold();
        // corner case if starting point has the most gold, DFS doesn't work
        if (mostGold.equals(s.getSTART())) {
            mostGold = s.findMostGold();
        }
        // moves from current location to tile with the most gold
        LinkedList<Vertex> currentToGold = fromCurrentToGold.route(s.getSTART(), mostGold);
        // moves from tile with most gold to exit
        fromGoldToExit.route(mostGold, s.getEXIT());
        // calculate if possible to get to tile with most gold and exit without running out of time
        while (time - (fromCurrentToGold.getWeightOfPath() + fromGoldToExit.getWeightOfPath()) > 0) {
            // adjusts time left
            time -= fromCurrentToGold.getWeightOfPath();
            // adds move to path to take
            path.addAll(currentToGold);
            // finds next tile with most gold
            mostGold = s.findMostGold();
            // checks tile has not been visited, else chooses now tile
            if (mostGold.equals(path.peekLast()) || path.contains(mostGold)) {
                mostGold = s.findMostGold();
            }
            // calculates new routes
            currentToGold = fromCurrentToGold.route(path.peekLast(), mostGold);
            fromCurrentToExit.route(path.peekLast(), s.getEXIT());
            fromGoldToExit.route(mostGold, s.getEXIT());
        }
        // adds appropriate move set to path to take
        if (path.size() == 0) {
            path.addAll(fromCurrentToExit.route(s.getSTART(), s.getEXIT()));
        } else {
            path.addAll(fromCurrentToExit.route(path.peekLast(), s.getEXIT()));
        }
        return path;
    }
}
