import java.awt.*;


public class GameSetup
{
    public GameSetup()
    {
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        //dimensions of the rectangle of the board
        int x=5;
        int y=5;
        int h=50;
        int w=50;

        //set a colour change variable to change to colour for every 2 rectangles
        int colourChange = 1;
        //switch the order of the colours for every row variable
        int switchRects = 1;

        //size of the board
        int n=8;
        //store the board into a 2d array
        String [][] board = new String [n][n];
        //loop for every row
        for (int i=0; i<n;i++)
        {
            //change the order of the next row of coloured rectangles
            switchRects++;
            if (switchRects>1)
            {
                switchRects =1;
            }
            if(switchRects==1)
            {
                colourChange--;
            }
            //loop for every rectangle within row
            for(int j =0;j<n;j++)
            {
                if (board[i][j]==null)
                {
                    //change the colour every time it makes a new rectangle
                    colourChange++;
                    if (colourChange > 2)
                    {
                        colourChange = 1;
                    }
                    if (colourChange == 2)
                    {
                        g2d.setColor(new Color(254, 44, 25));
                    } else {
                        g2d.setColor(new Color(1, 1, 1));
                    }
                    g2d.fillRect(x, y, w, h);
                    //change the pos of the rectangles to create more within the rows
                    x = x + w;
                }
            }
            //change pos of the rectangles to create more underneath the previous row
            y=y+h;
            x=x-n*w;
        }
        //todo add the checkers to the board so that the game can be started
    }
}
