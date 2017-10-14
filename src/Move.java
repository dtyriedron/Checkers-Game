import javax.swing.*;

public class Move extends JPanel
{
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
        GameSetup gameSet = new GameSetup();
        int low_range_y = 0;
        int high_range_y = 60;
        int low_range_x=0;
        int high_range_x=60;

        for (int i=0;i<gameSet.BOARD_SIZE; ++i)
        {

            if (y > low_range_y && y < high_range_y)
            {
                System.out.println("row: "+row);
                i=gameSet.BOARD_SIZE;
            }
            else
            {
                low_range_y += 60;
                high_range_y += 60;
                row++;
            }
        }
        for(int j=0;j<gameSet.BOARD_SIZE;++j)
        {
            if(x> low_range_x && x<high_range_x)
            {
                System.out.println("col: "+col);
                j=gameSet.BOARD_SIZE;
            }
            else
            {
                low_range_x+=60;
                high_range_x+=60;
                col++;
            }
        }
        gameSet.removeChecker(row,col);
        gameSet.highlight(row, col);
    }



}
