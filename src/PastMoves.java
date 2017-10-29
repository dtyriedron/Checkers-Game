import java.util.Stack;

public class PastMoves {

    private static Stack<PreviousMove> previousMoves = new Stack<PreviousMove>();
    private static int index = 0;

    public static void addMove(PreviousMove pm) {
        previousMoves.push(pm);
        System.out.println("ADDING " + pm.getDest().getRow() + pm.getDest().getCol());
        index++;
    }

    public static PreviousMove getLast() {
        return previousMoves.peek();
    }

    public static Stack<PreviousMove> getPreviousMoves() {
        return previousMoves;
    }
}
