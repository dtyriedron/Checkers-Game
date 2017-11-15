import java.util.ArrayList;
import java.util.Random;

public class AI {
    Board board;
    Move move;
    public AI(Board board, Move move) {
        this.board = board;
        this.move = move;
    }

//    public Piece getAIMove(Colour c) {
//        //array to store the AI Pieces
//        ArrayList<Piece> AIPieces;
//        Random rn = new Random();
//        int pieceNum = 12;
//
//        ArrayList<Piece> AIwithpotMoves = new ArrayList<>();
//        AIPieces = new ArrayList<Piece>();
//
//        for (Piece[] p : board.getBoard()) {
//            {
//                for(Piece piece : p) {
//                    if(piece != null && piece.getColour() == c)
//                        AIPieces.add(piece);
//                }
//            }
//        }
//
//        for (Piece AIp: AIPieces) {
//            if ((!move.couldJump(AIp.getPoint().getRow(), AIp.getPoint().getCol()).isEmpty())) {
//                    for (Point p : move.couldJump(AIp.getPoint().getRow(), AIp.getPoint().getCol())) {
//                    System.out.println("---------POTENTIAL MOVE: " + p.toString());
//                    AIwithpotMoves.add(AIp);
//                }
//            } else if (!move.couldMove(AIp.getPoint().getRow(), AIp.getPoint().getCol()).isEmpty()) {
//                for (Point p : move.couldMove(AIp.getPoint().getRow(), AIp.getPoint().getCol())) {
//                    System.out.println("---------POTENTIAL MOVE: " + p.toString());
//                    AIwithpotMoves.add(AIp);
//                }
//            }
//        }
//
//        //choose a random Piece from all the Pieces
//        int decision = rn.nextInt(AIwithpotMoves.size());
//        Piece chosenPiece = AIwithpotMoves.get(decision);
//        System.out.println(chosenPiece);
//        //board.highlightTile(chosenPiece.getPoint().getRow(), chosenPiece.getPoint().getCol());
//        return chosenPiece;
//    }
}
