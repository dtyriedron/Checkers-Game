import java.util.Stack;

public class PastMoves {

    private static Stack<PreviousMove> previousMoves = new Stack<PreviousMove>();
    private static int index = 0;
    private static PreviousMove redoMove;

    public static void addMove(PreviousMove pm) {
        previousMoves.push(pm);
        System.out.println("ADDING " + pm.getDest().getRow() + pm.getDest().getCol());
        index++;
    }

    public static PreviousMove getLast() {
        if(previousMoves.size() < 1)
            return null;

        redoMove = previousMoves.pop();
        return redoMove;
    }

    public static PreviousMove getRedoMove() {
        PreviousMove tmp = redoMove;
        redoMove = null;
        return tmp;
    }

    public static Stack<PreviousMove> getPreviousMoves() {
        return previousMoves;
    }
}
