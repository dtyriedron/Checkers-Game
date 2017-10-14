import javax.swing.*;
import java.awt.*;

import static java.sql.Types.NULL;


public class GameSetup
{
    Piece piece = new Piece();

    static final int HEIGHT = 60;
    static final int WIDTH = 60;
    public static int clicked = 0;
    public static boolean isOccupied =false;
    public int cur_row;
    public int cur_col;

    private int highlightX, highlightY;

    //size of the board
    static final int BOARD_SIZE = 8;
    public int row = 0;

    //array to store checkers
    private int[][] board;

    public GameSetup()
    {
        //store the board into a 2d array
        board = new int [BOARD_SIZE][BOARD_SIZE];
        installCheckers();
    }

    private void installCheckers()
    {
        for (row = 0; row < BOARD_SIZE; row += 2)
        {
            board[0][row] = piece.BLACK;
            board[2][row] = piece.BLACK;
            board[6][row] = piece.WHITE;
        }
        for (row = 1; row < BOARD_SIZE; row += 2) {
            board[1][row] = piece.BLACK;
            board[5][row] = piece.WHITE;
            board[7][row] = piece.WHITE;
        }
    }
    public void highlight(int x, int y)
    {
        this.highlightX = x;
        this.highlightY = y;
        //todo these are both zero and need to change when the mouse is clicked to different coords
    }

    public void test()
    {
        clicked++;
        if(clicked>1)
            clicked=0;
    }

    public void removeChecker(int row, int col)
    {
        if(board[row][col] != NULL)
        {
            isOccupied = true;
            cur_row=row;
            cur_col=col;
        }
        else
        {
            isOccupied = false;
        }
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //dimensions of the rectangle of the board
        int x=0;
        int y=0;

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
                    if(board[i][j] != NULL) {
                        g2d.setColor(Color.red);
                    }
                    //g2d.setColor(Color.white);
                    //g2d.fillOval(x + 10, y + 10, 3*WIDTH/4,3*HEIGHT/4);
                }
                else if(board[i][j] != NULL && board[i][j]==piece.BLACK)
                {
                    g2d.setColor(Color.DARK_GRAY);
                }
                //increment the x so that there is a new square beside the last

                g2d.fillOval(x + 10, y + 10, 3*WIDTH/4,3*HEIGHT/4);
                x+=WIDTH;
            }
        }

    }

    public void update(int x, int y) {
        remove(x, y);
        System.out.println(x + ", " + y);
    }

    private void remove(int x, int y) {
        board[x][y] = NULL;
    }


    public void isValid()
    {
        //highlight the currently selected checkers options

    }


    public int[][] getBoard()
    {
        return board;
    }

}



