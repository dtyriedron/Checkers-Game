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


    public boolean isValidMove(Point origin, Point dest)
    {
        //---------------------------------------------white------------------------------------------------------------
        if(board.getBoard()[origin.getRow()][origin.getRow()].getColour() != null && board.getBoard()[origin.getRow()][origin.getCol()].getColour() == Colour.WHITE && (board.getBoard()[dest.getRow()][dest.getCol()] == null))
        {
            //check if the white Piece can go right
            if (dest.getRow() == origin.getRow() - 1 && dest.getCol() == origin.getCol() + 1)
            {
                return true;
            }
            //check if the white Piece can go left
            else if (dest.getRow() == origin.getRow() - 1 && dest.getCol() == origin.getCol() - 1)
            {
                return true;
            }

            //check if a white can take right
            else if (board.getBoard()[origin.getRow() - 1][origin.getCol() + 1].getColour() != null && board.getBoard()[origin.getRow() - 1][origin.getCol() + 1].getColour() == Colour.BLACK)
           {
                if (dest.getRow() == origin.getRow() - 2 && dest.getCol() == origin.getCol() + 2 && board.getBoard()[dest.getRow()][dest.getCol()] == null) {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            //check if the white Piece can take left
            else if(board.getBoard()[origin.getRow() - 1][origin.getCol() - 1].getColour() == Colour.BLACK)
            {
                if (dest.getRow() == origin.getRow() - 2 && dest.getCol() == origin.getCol() - 2 && board.getBoard()[dest.getRow()][origin.getCol()] == null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        //-----------------------------------------black----------------------------------------------------------------
        else if (board.getBoard()[origin.getRow()][origin.getCol()].getColour() != null && board.getBoard()[origin.getRow()][origin.getCol()].getColour() == Colour.BLACK && (board.getBoard()[dest.getRow()][dest.getCol()] == null))
        {
            //check if the black Piece can go right
            if (dest.getRow() == origin.getCol() + 1 && dest.getRow() == origin.getCol() + 1 && board.validPos(origin.getRow() + 1, origin.getCol() + 1))
            {
                return true;
            }
            //check if the black Piece can go left
            if (dest.getRow() == origin.getRow() + 1 && dest.getCol() == origin.getCol() - 1 && board.validPos(origin.getRow() - 1, origin.getCol() + 1))
            {
                return true;
            }
            //check if black can take right
            if (board.getBoard()[origin.getRow() + 1][origin.getCol() + 1].getColour() == Colour.WHITE)
            {
                if (dest.getRow() == origin.getRow() + 2 && dest.getCol() == origin.getCol() + 2 && board.getBoard()[dest.getRow()][dest.getCol()] == null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            //check if the black Piece can take left
            else if (board.getBoard()[origin.getRow() + 1][origin.getCol() - 1].getColour() == Colour.WHITE)
            {
                if (dest.getRow() == origin.getRow() + 2 && dest.getCol() == origin.getCol() - 2 && board.getBoard()[dest.getRow()][dest.getCol()] == null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean jumpedRight(int row, int col, int destRow, int destCol)
    {
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.WHITE) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row - 1, col + 1) != null && board.getChecker(row - 1, col + 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && row - 2 == destRow && col + 2 == destCol && board.getChecker(row - 2, col + 2) == null) {
                if(board.getChecker(row - 1,col + 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.BLACK) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row + 1, col + 1) != null && board.getChecker(row + 1, col + 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && row + 2 == destRow && col + 2 == destCol && board.getChecker(row + 2, col + 2) == null) {
                if(board.getChecker(row + 1,col + 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        return false;
    }
    public boolean jumpedLeft(int row, int col, int destRow, int destCol)
    {
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.WHITE) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row - 1, col - 1) != null && board.getChecker(row - 1, col - 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && row - 2 == destRow && col - 2 == destCol && board.getChecker(row - 2, col - 2) == null) {
                if (board.getChecker(row - 1, col - 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        if (board.getChecker(row, col) != null && ((board.getChecker(row, col).getType() == Type.king) || ((board.getChecker(row, col).getColour() == Colour.BLACK) && (board.getChecker(row, col).getType() == Type.normal)))) {
            if (board.getChecker(row + 1, col - 1) != null && board.getChecker(row + 1, col - 1).getColour() == oppositeColour(board.getChecker(row, col).getColour()) && row + 2 == destRow && col - 2 == destCol && board.getChecker(row + 2, col - 2) == null) {
                if (board.getChecker(row + 1, col - 1).getType() == board.getChecker(row, col).getType() || board.getChecker(row, col).getType() == Type.king)
                    return true;
            }
        }
        return false;
    }

    public boolean validJump(int row, int col, int destRow, int destCol)
    {
        if (jumpedLeft(row, col, destRow, destCol)) {
            return true;
        }
        if (jumpedRight(row, col, destRow, destCol))
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

    public boolean validMove(int row, int col, int destRow, int destCol)
    {
            if (board.getChecker(row, col) != null && board.getChecker(row, col).getColour() == Colour.WHITE && board.getChecker(row, col).getType() == Type.normal) {
                if (row - 1 == destRow && col + 1 == destCol && board.getChecker(row - 1, col + 1) == null && board.validPos(row - 1, col + 1)) {
                    return true;
                }
                if (row - 1 == destRow && col - 1 == destCol && board.getChecker(row - 1, col - 1) == null && board.validPos(row - 1, col - 1)) {
                    return true;
                }

            } else if (board.getChecker(row, col) != null && board.getChecker(row, col).getColour() == Colour.BLACK && board.getChecker(row, col).getType() == Type.normal) {
                if (row + 1 == destRow && col + 1 == destCol && board.getChecker(row + 1, col + 1) == null && board.validPos(row + 1, col + 1)) {
                    return true;
                }
                if (row + 1 == destRow && col - 1 == destCol && board.getChecker(row + 1, col - 1) == null && board.validPos(row + 1, col - 1)) {
                    return true;
                }
            }
            if (board.getChecker(row, col) != null && board.getChecker(row, col).getType() == Type.king) {
                if (board.getChecker(destRow, destCol) == null && ((row - 1 == destRow && col + 1 == destCol) || (row - 1 == destRow && col - 1 == destCol) || (row + 1 == destRow && col + 1 == destCol) || (row + 1 == destRow && col - 1 == destCol)))
                    return true;
            }
        return false;
    }


    public static void undoMove() {
        System.out.println(PastMoves.getLast().getOrigin().getRow()+" "+ PastMoves.getLast().getOrigin().getCol());
        PreviousMove pm = PastMoves.getLast();
        addChecker(pm);
        board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()] = null;

    }

    public static void addChecker(PreviousMove pm){
        Piece origin = board.getBoard()[pm.getDest().getRow()][pm.getDest().getCol()];
        if(pm.getTakenPiece() != null) {
            if(pm.getTakenPiece() != null && board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()].getColour() == Colour.WHITE)
            {
                if (origin.getType() == Type.normal)
                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.normal, Colour.WHITE);
                else
                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.king, Colour.WHITE);
            }
            else if(pm.getTakenPiece() != null && board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()].getColour() == Colour.BLACK)
            {
                if (origin.getType() == Type.normal)
                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.normal, Colour.WHITE);
                else
                    board.getBoard()[pm.getTakenPiece().getRow()][pm.getTakenPiece().getCol()] = new Piece(Type.king, Colour.WHITE);
            }
        }
        if(origin.getColour() == Colour.WHITE) {
            if (origin.getType() == Type.normal)
                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.normal, Colour.WHITE);
            else
                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.king, Colour.WHITE);
        }
        else if(origin.getColour() == Colour.BLACK) {
            if (origin.getType() == Type.normal)
                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.normal, Colour.BLACK);
            else
                board.getBoard()[pm.getOrigin().getRow()][pm.getOrigin().getCol()] = new Piece(Type.king, Colour.BLACK);
        }
    }



}
