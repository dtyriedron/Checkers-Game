import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    Board board;
    Move move;

    Piece chosenPiece;
    Point chosenMove;

    int index;

    static final int AI_1 = 1;
    static final int AI_2 = 2;

    //array to store the AI Pieces
    ArrayList<Piece> AIPieces;
    Random rn = new Random();
    ArrayList<Point> potMoves;

    public AI(Board board, Move move) {
        this.board = board;
        this.move = move;
    }

    public int currentAIPlayer()
    {
        if(board.playerColour() == Colour.BLACK)
            return index = AI_2;
        else
            return index = AI_1;
    }
    public Colour currentAIColour()
    {
        if(index == 1)
            return Colour.WHITE;
        else
            return Colour.BLACK;
    }

    public Piece getAIPiece(Colour c) {

        ArrayList<Piece> pieceWithPotMoves = new ArrayList<>();
        AIPieces = new ArrayList<>();

        for (Piece[] p : board.getBoard()) {
            {
                for(Piece piece : p) {
                    if(piece != null && piece.getColour() == c)
                        AIPieces.add(piece);
                }
            }
        }

        try {
            for (Piece AIp : AIPieces) {
                if ((!move.couldJump(AIp.getPoint().getRow(), AIp.getPoint().getCol()).isEmpty())) {
                    for (Point p : move.couldJump(AIp.getPoint().getRow(), AIp.getPoint().getCol())) {
                        System.out.println("---------POTENTIAL AI JUMP: " + p.toString());
                        pieceWithPotMoves.add(AIp);
                    }
                } else if (!move.couldMove(AIp.getPoint().getRow(), AIp.getPoint().getCol()).isEmpty()) {
                    for (Point p : move.couldMove(AIp.getPoint().getRow(), AIp.getPoint().getCol())) {
                        System.out.println("---------POTENTIAL AI MOVE: " + p.toString());
                        pieceWithPotMoves.add(AIp);
                    }
                }
            }
        }catch (NullPointerException e){
            System.out.println("hey");
        }

        //choose a random Piece from all the Pieces
        int decision = rn.nextInt(pieceWithPotMoves.size());
        chosenPiece = pieceWithPotMoves.get(decision);
        System.out.println("chosen piece-----------------" + chosenPiece);
        //board.highlightTile(chosenPiece.getPoint().getRow(), chosenPiece.getPoint().getCol());
        return chosenPiece;
    }

    public Point getAIMove()
    {
        potMoves = new ArrayList<>();

        if ((!move.couldJump(chosenPiece.getPoint().getRow(), chosenPiece.getPoint().getCol()).isEmpty())) {
            for (Point p : move.couldJump(chosenPiece.getPoint().getRow(), chosenPiece.getPoint().getCol())) {
                potMoves.add(p);
            }
        } else if (!move.couldMove(chosenPiece.getPoint().getRow(), chosenPiece.getPoint().getCol()).isEmpty()) {
            for (Point p : move.couldMove(chosenPiece.getPoint().getRow(), chosenPiece.getPoint().getCol())) {
                potMoves.add(p);
            }
        }

        int decision = rn.nextInt(potMoves.size());
        chosenMove = potMoves.get(decision);
        System.out.println("chosen move-----------------"+ chosenMove);

        return chosenMove;
    }
}
