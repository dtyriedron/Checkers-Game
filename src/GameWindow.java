import java.awt.*;
import javax.swing.*;

public class GameWindow extends JPanel
{
    private GameSetup gameSet = new GameSetup();

    public static void main(String[] args)
    {
        //set the gui and graphics
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GameWindow());
        frame.setSize(600,600);
        frame.setVisible(true);
    }
    public void paint(Graphics g)
    {
        gameSet.paint(g);
    }
    /*todo: need to setup the buttons so that the game can be started etc (on the right hand side)
    todo: possibly need multiple windows so that the player can select whether they play another or the computer
    */
    /*
//todo put this into another class::
    public static void move(String[][] board, int player)
    {
        //create a buffered reader to read the move

        //display label for whos turn it is and after that turn is done switch it to the next player

    }*/

}
