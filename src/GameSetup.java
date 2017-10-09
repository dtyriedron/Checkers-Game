import javax.swing.*;
import java.awt.*;


public class GameSetup
{
    public GameSetup()
    {
    }


    public void paint(Graphics g)
    {
        GameWindow gameWin = new GameWindow();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //dimensions of the rectangle of the board
        int x=0;
        int y=0;
        int h=60;
        int w=60;

        //clear the rectangles
       // g2d.clearRect(x,y,h,w);

        //set a colour change variable to change to colour for every 2 rectangles
        int colourChange = 1;
        //switch the order of the colours for every row variable
        int switchRects = 1;

        //size of the board
        int n=8;
        //store the board into a 2d array
        //todo need to add the tiles to the board array
        String [][] board = new String [n][n];

        //loop for every row
        for (int i=0; i<n;i++)
        {
            if (i!=0)
            {
                y += h;
                x = 0;
            }
            if (i % 2 == 0)
            {
                g2d.setColor(Color.white);
            }
            else
            {
                g2d.setColor(Color.BLACK);
            }
            g2d.fillRect(x, y, w, h);

            //loop for every square within row
            for(int j=0;j<n;j++)
            {
                //increment the x so that there is a new square beside the last
                x+=w;
                if (j%2==0)
                {
                    //for every second row the colour changes
                    g2d.setColor(i % 2 == 0 ? Color.BLACK : Color.white);

                } else
                {
                    g2d.setColor(i % 2 == 0 ? Color.white : Color.BLACK);
                }
                g2d.fillRect(x, y, w, h);

            }
        }

        g2d.dispose();
        //todo add the checkers to the board so that the game can be started
    }
}
