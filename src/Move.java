import com.sun.corba.se.impl.oa.NullServantImpl;
import com.sun.management.VMOption;

import javax.swing.*;

import static java.sql.Types.NULL;


public class Move extends JPanel
{
    //determine whether the Piece can move
    //determine whether the Piece can take an opposing Piece
    //determine whether the Piece has moved to the end of the Board
    //determine if the Piece is a king

    private static Board board;
    private Piece piece;
    private Type type;
    public Move(Board b)
    {
        this.board = b;
    }

    public boolean couldjumpRight(int row, int col)
    {
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.WHITE) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row - 1, col + 1) != null && board.getChecker(row - 1, col + 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.validPos(row - 2, col + 2) &&board.getChecker(row - 2, col + 2) == null) {
                if(board.getChecker(row - 1,col + 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.BLACK) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row + 1, col + 1) != null && board.getChecker(row + 1, col + 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.validPos(row + 2, col + 2) && board.getChecker(row + 2, col + 2) == null) {
                if(board.getChecker(row + 1,col + 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        return false;
    }
    public boolean couldjumpLeft(int row, int col)
    {
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.WHITE) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row - 1, col - 1) != null && board.getChecker(row - 1, col - 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.validPos(row - 2, col - 2) && board.getChecker(row - 2, col - 2) == null) {
                if (board.getChecker(row - 1, col - 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.BLACK) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row + 1, col - 1) != null && board.getChecker(row + 1, col - 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.validPos(row + 2, col - 2) &&board.getChecker(row + 2, col - 2) == null) {
                if (board.getChecker(row + 1, col - 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        return false;
    }

    public boolean couldJump(int row, int col)
    {
        if (couldjumpLeft(row, col)) {
            return true;
        }
        if (couldjumpRight(row, col))
            return true;

//            if(board.getChecker(row,col) != null && board.getChecker(row,col).getType() == Type.king)
//            {
//                if(board.getChecker(destRow, destCol) == null && ((row - 2 == destRow && col + 2 == destCol) || (row - 2 == destRow && col - 2 == destCol) || (row + 2 == destRow && col + 2 == destCol) || (row + 2 == destRow && col - 2 == destCol)))
//                    return true;
//            }

        return false;
    }

    public Colour oppositeColour(Colour c)
    {
        if(c == Colour.WHITE)
            return Colour.BLACK;

        return Colour.WHITE;
    }

    public boolean validKing(int destRow)
    {
                if (destRow == 0 || destRow == 7) {
                    return true;
                }

        return false;
    }

    public boolean couldMove(int row, int col)
    {
            if (board.getChecker(row, col) != null && board.getChecker(row, col).getColour() == Colour.WHITE && board.getChecker(row, col).getType() == Type.normal) {
                if (board.getChecker(row - 1, col + 1) == null && board.validPos(row - 1, col + 1)) {
                    return true;
                }
                if (board.getChecker(row - 1, col - 1) == null && board.validPos(row - 1, col - 1)) {
                    return true;
                }

            } else if (board.getChecker(row, col) != null && board.getChecker(row, col).getColour() == Colour.BLACK && board.getChecker(row, col).getType() == Type.normal) {
                if (board.getChecker(row + 1, col + 1) == null && board.validPos(row + 1, col + 1)) {
                    return true;
                }
                if (board.getChecker(row + 1, col - 1) == null && board.validPos(row + 1, col - 1)) {
                    return true;
                }
            }
            if (board.getChecker(row, col) != null && board.getChecker(row, col).getType() == Type.king) {
                if ((board.getChecker(row - 1, col + 1) == null || board.getChecker(row - 1, col - 1) == null || board.getChecker(row + 1, col + 1) == null || board.getChecker(row + 1, col - 1) == null))
                    return true;
            }
        return false;
    }


    public static void undoMove() {
        //System.out.println(PastMoves.getLast().getOrigin().getRow()+" "+ PastMoves.getLast().getOrigin().getCol());
        PreviousMove pm = PastMoves.getLast();
        //addChecker(pm);
        board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()];
//        System.out.println(Board.removedPiece.getPoint().getRow()+ " "+ Board.removedPiece.getPoint().getCol());
        if(pm.getTakenPiece() != null)
        board.getBoard()[Board.removedPiece.getPoint().getRow()][Board.removedPiece.getPoint().getCol()] = Board.removedPiece;
        board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()] = null;
    }

////    public static void addChecker(PreviousMove pm){
////        Piece origin = board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()];
////        System.out.println("Piece: "+ pm.getTakenPiece().getRow() + " "+ pm.getTakenPiece().getCol());
////        if(pm.getTakenPiece() != null) {
////            if(pm.getTakenPiece().getColour() == Colour.WHITE)
////            {
////                if (origin.getType() == Type.normal)
////                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.normal, Colour.WHITE);
////                else
////                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.king, Colour.WHITE);
////            }
////            else if(pm.getTakenPiece() != null && board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()].getColour() == Colour.BLACK)
////            {
////                if (origin.getType() == Type.normal)
////                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.normal, Colour.WHITE);
////                else
////                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.king, Colour.WHITE);
////            }
////        }
//        if(origin.getColour() == Colour.WHITE) {
//            if (origin.getType() == Type.normal)
//                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.normal, Colour.WHITE);
//            else
//                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.king, Colour.WHITE);
//        }
//        else if(origin.getColour() == Colour.BLACK) {
//            if (origin.getType() == Type.normal)
//                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.normal, Colour.BLACK);
//            else
//                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.king, Colour.BLACK);
//        }
//    }



}
