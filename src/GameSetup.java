import javax.swing.*;
import java.awt.*;

import static java.sql.Types.NULL;


public class GameSetup
{
    Piece piece = new Piece();
    static final int HEIGHT = 60;
    static final int WIDTH = 60;
    //size of the board
    static final int BOARD_SIZE = 8;
    public int row = 0;
    public GameSetup()
    {
    }

    private void installCheckers(int [][] board, int BOARD_SIZE)
    {
        for (row = 0; row < BOARD_SIZE; row += 2)
        {
            board[0][row] = piece.BLACK;
            board[2][row] = piece.BLACK;
            board[6][row] = piece.WHITE;
        }
        for (row = 1; row < BOARD_SIZE; row += 2)
        {
            board[1][row] = piece.BLACK;
            board[5][row] = piece.WHITE;
            board[7][row] = piece.WHITE;
        }
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //dimensions of the rectangle of the board
        int x=0;
        int y=0;

        //store the board into a 2d array
        int [][] board = new int [BOARD_SIZE][BOARD_SIZE];

        //put the checkers on the board
        installCheckers(board, BOARD_SIZE);
        //loop for every row
        for (int i=0; i<BOARD_SIZE;i++)
        {
            if (i!=0)
            {
                y += HEIGHT;
                x = 0;
            }
            //loop for every square within row
            for(int j=0;j<BOARD_SIZE;j++)
            {
                //increment the x so that there is a new square beside the last
                x+=WIDTH;
                if (j%2==0)
                {
                    //for every second row the colour changes
                    g2d.setColor(i % 2 == 0 ? Color.BLACK : Color.red);

                } else
                {
                    g2d.setColor(i % 2 == 0 ? Color.red : Color.BLACK);
                }
                g2d.fillRect(x, y, WIDTH, HEIGHT);

                if(board[i][j] != NULL && board[i][j] ==piece.WHITE)
                {
                    g2d.setColor(Color.white);
                    g2d.fillOval(x + 10, y + 10, 3*WIDTH/4,3*HEIGHT/4);
                }
                else if(board[i][j] != NULL && board[i][j]==piece.BLACK)
                {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillOval(x + 10, y + 10, 3*WIDTH/4,3*HEIGHT/4);
                }

            }
        }

        g2d.dispose();

    }

}



