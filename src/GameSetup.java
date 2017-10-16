import javax.swing.*;
import java.awt.*;

import static java.sql.Types.NULL;


public class GameSetup
{
    Piece piece = new Piece();

    static final int HEIGHT = 60;
    static final int WIDTH = 60;
    public static int clicked = 0;
    public static boolean isRemoved = false;
    public static boolean isOccupied = false;

    public boolean isblack=false;
    public boolean blackcanmoveright=false;
    public boolean blackcanmoveleft=false;
    public boolean iswhite=false;
    public boolean whitecanmoveright=false;
    public boolean whitecanmoveleft=false;

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

    public void update(int x, int y)
    {
        highlight(x,y);
    //remove(x, y);
    }

    public void highlight(int x, int y)
    {
        this.highlightX = x;
        this.highlightY = y;
        try
        {
            if(board[y][x] !=NULL)
            {
                isOccupied=true;
            }
            else
            {
                isOccupied=false;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){}
    }

    public void click()
    {
        clicked++;
        if(clicked>1)
            clicked=0;
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
                    g2d.setColor(Color.white);
                    g2d.fillOval(x + 10, y + 10, 3*WIDTH/4,3*HEIGHT/4);
                }
                else if(board[i][j] != NULL && board[i][j]==piece.BLACK)
                {
                    g2d.setColor(Color.DARK_GRAY);
                }
                //increment the x so that there is a new square beside the last

                g2d.fillOval(x + 10, y + 10, 3*WIDTH/4,3*HEIGHT/4);
                x+=WIDTH;
            }


                if(board[highlightY][highlightX]== NULL || board[highlightY][highlightX] != NULL)
                {
                    g2d.setColor(Color.orange);
                    g2d.drawRect(highlightX * 60, highlightY * 60, WIDTH, HEIGHT);
                }

                g2d.setColor(new Color(0,191,255,50));

            try
            {
                if(board[highlightY][highlightX] == piece.BLACK)
                {
                    if (board[highlightY + 1][highlightX + 1] == NULL || (board[highlightY + 1][highlightX + 1] == piece.WHITE && board[highlightY + 2][highlightX + 2] == NULL))
                    {
                        g2d.fillRect((highlightX + 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
                    }
                    if (board[highlightY + 1][highlightX - 1] == NULL || (board[highlightY + 1][highlightX - 1] == piece.WHITE && board[highlightY + 2][highlightX - 2] == NULL))
                    {
                        g2d.fillRect((highlightX - 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
                    }
                }

                if(board[highlightY][highlightX] == piece.WHITE)
                {
                    if(board[highlightY - 1][highlightX - 1] == NULL || (board[highlightY - 1][highlightX - 1] == piece.BLACK && board[highlightY - 2][highlightX - 2] == NULL))
                    {
                        g2d.fillRect((highlightX - 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
                    }
                    if(board[highlightY - 1][highlightX + 1] == NULL || (board[highlightY - 1][highlightX + 1] == piece.BLACK && board[highlightY - 2][highlightX + 2] == NULL))
                    {
                        g2d.fillRect((highlightX + 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){}

        }

    }

    private void remove(int x, int y)
    {
        board[y][x] = NULL;
        if (board[y][x] == NULL)
        {
            isRemoved = true;
        }
        else
        {
            isRemoved = false;
        }
    }

    public void validMove()
    {
        //highlight the currently selected checkers options

        //check if the piece is black
        if(board[highlightY][highlightX] == piece.BLACK && isOccupied)
        {
            isblack = true;
            //System.out.println("variable: " + isblack);
        }
        else
        {
            isblack=false;
        }

        //check if the piece can move right and down
        if (board[highlightY + 1][highlightX + 1] == NULL || (board[highlightY + 1][highlightX + 1] == piece.WHITE && board[highlightY + 2][highlightX + 2] == NULL)) {
            blackcanmoveright = true;
        }
        else
        {
            blackcanmoveright = false;
        }

        //check if the piece can move left and down
        if (board[highlightY + 1][highlightX - 1] == NULL || (board[highlightY + 1][highlightX - 1] == piece.WHITE && board[highlightY + 2][highlightX - 2] == NULL))
        {
            blackcanmoveleft = true;
        }
        else
        {
            blackcanmoveleft = false;
        }



        //check is the piece is white
        if(board[highlightY][highlightX] == piece.WHITE && isOccupied)
        {
            iswhite=true;
        }
        else
        {
            iswhite=false;
        }

        //check if the piece can go up and left
        if(board[highlightY - 1][highlightX - 1] == NULL || (board[highlightY - 1][highlightX - 1] == piece.BLACK && board[highlightY - 2][highlightX - 2] == NULL))
        {
            whitecanmoveleft=true;
        }
        else
        {
            whitecanmoveleft=false;
        }

        //check if the piece can up and right
        if(board[highlightY - 1][highlightX + 1] == NULL || (board[highlightY - 1][highlightX + 1] == piece.BLACK && board[highlightY - 2][highlightX + 2] == NULL))
        {
            whitecanmoveright=true;
        }
        else
        {
            whitecanmoveright=false;
        }
    }

    public int[][] getBoard()
    {
        return board;
    }

}



