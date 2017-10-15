import javax.swing.*;

import static java.sql.Types.NULL;

public class Move extends JPanel
{
    GameSetup gameSet = new GameSetup();
    Piece piece =new Piece();
    //determine whether the piece can move
    //determine whether the piece can take an opposing piece
    //determine whether the piece has moved to the end of the board
    //determine if the piece is a king

    public Move()
    {
    }



    int row = 0;
    int col = 0;

    public void test(int x, int y)
    {

        row = y/60;
        col = x/60;
        gameSet.highlight(row, col);
    }




}
