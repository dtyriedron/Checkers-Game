import javax.swing.*;
import java.awt.*;

import static java.sql.Types.NULL;


public class Board
{
    Piece piece = new Piece();

    static final int HEIGHT = 60;
    static final int WIDTH = 60;
    public static boolean isRemoved = false;
    public static boolean isOccupied = false;

    public int right_old_click_x=40;
    public int right_old_click_y=40;
    public int left_old_click_x=40;
    public int left_old_click_y=40;
    public int xmod=0;
    public int ymod=0;

    public boolean isblack = false;
    public boolean isoldblack = false;
    public boolean blackcanmoveright = false;
    public boolean blackcanmoveleft = false;
    public boolean iswhite = false;
    public boolean isoldwhite = false;
    public boolean whitecanmoveright = false;
    public boolean whitecanmoveleft = false;

    private int highlightX, highlightY;

    //size of the Board
    static final int BOARD_SIZE = 8;
    public int row = 0;

    //array to store checkers
    private int[][] board;

    public Board()
    {
        //store the Board into a 2d array
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


    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //dimensions of the rectangle of the Board
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
                validMove();
                if(isblack)
                {
                    if(blackcanmoveright)
                    {
                        if ((highlightX + 1) < BOARD_SIZE)
                        {
                            g2d.fillRect((highlightX + 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
                        }
                    }
                    if(blackcanmoveleft)
                    {
                        if ((highlightX - 1) >= 0)
                        {
                            g2d.fillRect((highlightX - 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
                        }
                    }
                }
                if(iswhite)
                {
                    if(whitecanmoveright)
                    {
                        if ((highlightX + 1) < BOARD_SIZE)
                        {
                            g2d.fillRect((highlightX + 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
                        }
                    }
                    if(whitecanmoveleft)
                    {
                        if ((highlightX - 1) >= 0)
                        {
                            g2d.fillRect((highlightX - 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
                        }
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){}

        }

    }

    private void removeChecker(int x, int y)
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
    private void addChecker(int x, int y)
    {
        if(board[y][x]==NULL && isoldblack==true)
        {
            board[y][x] = piece.BLACK;
        }
        if(board[y][x]==NULL && isoldwhite==true)
        {
            board[y][x] = piece.WHITE;
        }
    }

    public void isMoving(int x, int y)
    {
        System.out.println("x: "+x+" y: "+y);
        System.out.println("old x: "+left_old_click_x+ " old y: "+left_old_click_y);

        //black can move left
        try
        {
            if (x+1==left_old_click_x && y-1==left_old_click_y&& board[left_old_click_y][left_old_click_x]==piece.BLACK&&(board[y][x]==piece.WHITE|| board[y][x]==NULL))
            {
                isoldblack=true;
                removeChecker(left_old_click_x,left_old_click_y);
                addChecker(x,y);
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //black can move right
        try
        {
            if(x-1==right_old_click_x && y-1==right_old_click_y && board[right_old_click_y][right_old_click_x]==piece.BLACK && (board[y][x]==piece.WHITE || board[y][x]==NULL))
            {
                isoldblack=true;
                removeChecker(right_old_click_x,right_old_click_y);
                addChecker(x,y);
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //white can move left
        try
        {
            if(x+1==left_old_click_x && y+1==left_old_click_y && board[left_old_click_y][left_old_click_x]==piece.WHITE&&(board[y][x]==piece.BLACK|| board[y][x]==NULL))
            {
                isoldwhite=true;
                removeChecker(left_old_click_x,left_old_click_y);
                addChecker(x,y);
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //white can move right
        try
        {
            if(x-1==right_old_click_x && y+1==right_old_click_y && board[right_old_click_y][right_old_click_x]==piece.WHITE && (board[y][x]==piece.BLACK || board[y][x]==NULL))
            {
                isoldwhite=true;
                removeChecker(right_old_click_x,right_old_click_y);
                addChecker(x,y);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
    }

    public void validMove()
    {

            //check if the piece is black
            if (board[highlightY + ymod][highlightX + xmod] == piece.BLACK)
            {
                isblack = true;
            }
            else
            {
                isblack = false;
            }

        try
        {
            //check if the piece can move right and down
            if (board[highlightY + 1][highlightX + 1] == NULL || (board[highlightY + 1][highlightX + 1] == piece.WHITE && board[highlightY + 2][highlightX + 2] == NULL))
            {
                blackcanmoveright = true;
                right_old_click_x= highlightX;
                right_old_click_y= highlightY;
            } else
            {
                blackcanmoveright = false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        try
        {
            //check if the piece can move left and down
            if (board[highlightY + 1][highlightX - 1] == NULL || (board[highlightY + 1][highlightX - 1] == piece.WHITE && board[highlightY + 2][highlightX - 2] == NULL))
            {
                //System.out.println("x: "+left_old_click_x + " y: "+left_old_click_y);
                blackcanmoveleft = true;
                left_old_click_x= highlightX;
                left_old_click_y= highlightY;
            }
            else
            {
                blackcanmoveleft = false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}


        //check is the piece is white
        if(board[highlightY][highlightX] == piece.WHITE)
        {
            iswhite=true;
        }
        else
        {
            iswhite=false;
        }

        try
        {
            //check if the piece can up and right
            if (board[highlightY - 1][highlightX + 1] == NULL || (board[highlightY - 1][highlightX + 1] == piece.BLACK && board[highlightY - 2][highlightX + 2] == NULL))
            {
                whitecanmoveright = true;
                right_old_click_x= highlightX;
                right_old_click_y= highlightY;
            } else
            {
                whitecanmoveright = false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //check if the piece can go up and left
        try
        {
            if (board[highlightY - 1][highlightX - 1] == NULL || (board[highlightY - 1][highlightX - 1] == piece.BLACK && board[highlightY - 2][highlightX - 2] == NULL))
            {
                whitecanmoveleft = true;
                left_old_click_x= highlightX;
                left_old_click_y= highlightY;
            }
            else
            {
                whitecanmoveleft = false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

    }

    public int[][] getBoard()
    {
        return board;
    }

}



