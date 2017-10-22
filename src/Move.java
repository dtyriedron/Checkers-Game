import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.NULL;


public class Move extends JPanel
{
    //determine whether the piece can move
    //determine whether the piece can take an opposing piece
    //determine whether the piece has moved to the end of the Board
    //determine if the piece is a king

    Map potmoves = new HashMap();

    public Move()
    {
    }

    public void checkCheckers()
    {
        Board board = new Board();
        if(board.ISOCCUPIED)
        {
            for(int i=0; i<board.BOARD_SIZE*2; i++)
            {
               // potmoves.put(new Point(,), i);
            }
        }
        System.out.println("check pos: "+potmoves);
    }
    private void validmove()
    {

    }




}
