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

    private Board board;
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

    public boolean validMove(int row, int col, int destRow, int destCol)
    {
        if(board.getChecker(row, col) != null && board.getChecker(row, col).getColour() == Colour.WHITE) {
            if(row - 1 == destRow && col+1 == destCol && board.getChecker(row-1, col+1) == null) {
                return true;
            }

        } else if(board.getChecker(row, col) != null && board.getChecker(row, col).getColour() == Colour.BLACK) {

        }

        return false;
    }




}
