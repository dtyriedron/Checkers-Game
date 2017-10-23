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
    public Move(Board b)
    {
        this.board = b;
    }

    public boolean isValidMove(Point origin, Point dest)
    {
        if(board.getBoard()[origin.getCol()][origin.getRow()] == Piece.WHITE && (board.getBoard()[dest.getCol()][dest.getRow()] == NULL))
        {
            //check if the white Piece can go right
            if (dest.getCol() == origin.getCol() - 1 && dest.getRow() == origin.getRow() + 1)
            {
                return true;
            }
            //check if the white Piece can go left
            else if (dest.getCol() == origin.getCol() - 1 && dest.getRow() == origin.getRow() - 1)
            {
                return true;
            }

            //check if any Piece can be taken
            else if (board.getBoard()[origin.getCol() - 1][origin.getRow() + 1] == Piece.BLACK || board.getBoard()[origin.getCol() - 1][origin.getRow() - 1] == Piece.BLACK)
            {
                //check if the white Piece can take right
                if (dest.getCol() == origin.getCol() - 2 && dest.getRow() == origin.getRow() + 2)
                {
                    return true;
                }
                //check if the white Piece can take left
                else if (dest.getCol() == origin.getCol() - 2 && dest.getRow() == origin.getRow() - 2)
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
        
        else if(board.getBoard()[origin.getCol()][origin.getRow()] == Piece.BLACK && (board.getBoard()[dest.getCol()][dest.getRow()] == NULL))
        {
                //check if the black Piece can go right
                if (dest.getCol() == origin.getCol()+1 && dest.getRow() == origin.getRow()+1)
                {
                    return true;
                }
                //check if the black Piece can go left
                else if(dest.getCol() == origin.getCol()+1 && dest.getRow() == origin.getRow()-1)
                {
                    return true;
                }
                //check if any jumps can be done
                else if(board.getBoard()[origin.getCol()+1][origin.getRow()+1]==Piece.WHITE || board.getBoard()[origin.getCol()+1][origin.getRow()-1] == Piece.WHITE)
                {
                    //check if the black Piece can take right
                    if (dest.getCol() == origin.getCol()-2 && dest.getRow() == origin.getRow()+2)
                    {
                        return true;
                    }
                    //check if the black Piece can take left
                    else if (dest.getCol() == origin.getCol()-2 && dest.getRow() == origin.getRow()-2)
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
            return true;
        }
    }

    private void validmove()
    {

    }




}
