import com.sun.corba.se.impl.oa.NullServantImpl;
import com.sun.management.VMOption;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private static Timer timer;
    public ArrayList<Point> potPoints;
    public  ArrayList<Point> potJumps = new ArrayList<>();

    public Move(Board b)
    {
        this.board = b;
    }


    public ArrayList<Point> couldJump(int row, int col)
    {
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.WHITE) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row - 1, col - 1) != null && board.getChecker(row - 1, col - 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.getChecker(row - 2, col - 2) == null && board.validPos(row - 2, col - 2)) {
                if (board.getChecker(row - 1, col - 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king) {
                    System.out.println("can jump left");
                    potJumps.add(new Point(row - 2, col - 2));
                }
            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.BLACK) && (board.getChecker(row, col).getType() == Type.normal))))
        {
            if (board.getChecker(row + 1, col - 1) != null && board.getChecker(row + 1, col - 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.getChecker(row + 2, col - 2) == null && board.validPos(row + 2, col - 2))
            {
                if (board.getChecker(row + 1, col - 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king) {
                    System.out.println("can jump left");
                    potJumps.add(new Point(row + 2, col - 2));
                }
            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.WHITE) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row - 1, col + 1) != null && board.getChecker(row - 1, col + 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.getChecker(row - 2, col + 2) == null && board.validPos(row - 2, col + 2)) {
                if(board.getChecker(row - 1,col + 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                {
                    System.out.println("can jump right");
                    potJumps.add(new Point(row - 2, col + 2));
                }

            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.BLACK) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row + 1, col + 1) != null && board.getChecker(row + 1, col + 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && board.getChecker(row + 2, col + 2) == null && board.validPos(row + 2, col + 2)) {
                if(board.getChecker(row + 1,col + 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                {
                    System.out.println("can jump right");
                    potJumps.add(new Point(row + 2, col + 2));
                }
            }
        }
        return potJumps;
    }

    public Colour oppositeColour(Colour c)
    {
        if(c == Colour.WHITE)
            return Colour.BLACK;

        return Colour.WHITE;
    }

    public boolean validKing(int destRow, int destCol)
    {
        if ((destRow == 0 && board.getChecker(destRow, destCol).getColour()== Colour.WHITE) || (destRow == 7 && board.getChecker(destRow, destCol).getColour() == Colour.BLACK)) {
            return true;
        }
        else
            return false;
    }

    public ArrayList<Point> couldMove(int row, int col)
    {
        potPoints = new ArrayList<>();
            if (board.getChecker(row, col) != null && (board.getChecker(row, col).getColour() == Colour.WHITE && board.getChecker(row, col).getType() == Type.normal) || board.getChecker(row, col).getType() == Type.king) {
                if (board.getChecker(row - 1, col + 1) == null && board.validPos(row - 1, col + 1)) {
                    potPoints.add(new Point(row - 1, col + 1));
                }
                if (board.getChecker(row - 1, col - 1) == null && board.validPos(row - 1, col - 1)) {
                    potPoints.add(new Point(row - 1, col - 1));
                }
            }
            if (board.getChecker(row, col) != null && (board.getChecker(row, col).getColour() == Colour.BLACK && board.getChecker(row, col).getType() == Type.normal)|| board.getChecker(row, col).getType() == Type.king) {
                if (board.getChecker(row + 1, col + 1) == null && board.validPos(row + 1, col + 1)) {
                    potPoints.add(new Point(row + 1, col + 1));
                }
                if (board.getChecker(row + 1, col - 1) == null && board.validPos(row + 1, col - 1)) {
                    potPoints.add(new Point(row + 1, col - 1));
                }
            }
        return potPoints;
    }


    public static void undoMove() {
        PreviousMove pm = PastMoves.getLast();
        if(pm != null) {
            //System.out.println("this is the past move::: " + PastMoves.getLast().getOrigin().getCol() + "" + PastMoves.getLast().getOrigin().getRow() + "->" + PastMoves.getLast().getDest().getCol() + "" + PastMoves.getLast().getDest().getRow());
            //addChecker(pm);
            board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()];
            //        System.out.println(Board.removedPiece.getPoint().getRow()+ " "+ Board.removedPiece.getPoint().getCol());
            if (pm.getTakenPiece() != null)
                board.getBoard()[Board.removedPiece.getPoint().getRow()][Board.removedPiece.getPoint().getCol()] = Board.removedPiece;

            board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()] = null;
            board.nextTurn();
            board.potMoves.clear();
        }
        else
        {
            System.out.println("nothing to undo");
        }
    }
    public static void redoMove()
    {
        PreviousMove pm = PastMoves.getRedoMove();
        if(pm != null) {
            //System.out.println("hhhhhhhhhhhhhheeeeeeeeeeeeeeeellllllllllllllllllo:::: "+pm.getOrigin().getRow() + " " + pm.getOrigin().getCol() + "->" + pm.getDest().getRow() + " " + pm.getDest().getCol());
            board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()] = board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()];
            if(pm.getTakenPiece() != null)
                board.getBoard()[Board.removedPiece.getPoint().getRow()][Board.removedPiece.getPoint().getCol()] = null;

            board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = null;

            PastMoves.addMove(pm);
            board.nextTurn();
            board.potMoves.clear();
        }
        else
        {
            System.out.println("nothing to redo!!");
        }
    }
    public static void replayGame()
    {
        //loop for every previous move
        board.clearBoard();
        board.installCheckers();
        timer = new Timer(900, e -> {
            if(!PastMoves.getPreviousMoves().isEmpty()) {
                PreviousMove pm = PastMoves.getPreviousMoves().removeLast();
                if (pm != null) {
                    board.checkMove(pm.getOrigin().getRow(), pm.getOrigin().getCol(),pm.getDest().getRow(), pm.getDest().getCol());
//                    board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()] = board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()];
//                    if (pm.getTakenPiece() != null)
//                        board.getBoard()[Board.removedPiece.getPoint().getRow()][Board.removedPiece.getPoint().getCol()] = null;
//
//                    board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = null;
                } else {
                    System.out.println("nothing to replay!!");
                }
            }
            else
                timer.stop();
        });
        timer.start();
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
