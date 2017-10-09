import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class GameWindow extends JPanel implements MouseListener, MouseMotionListener
{
    public static final int WIDTH = 450;
    public static final int HEIGHT = 450;
    private GameSetup gameSet = new GameSetup();
    private GameLoop loopGame = new GameLoop();

    //mouse position
    public static int mouse_x, mouse_y;

    public GameWindow()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        loopGame.start();
    }




    public void paint(Graphics g)
    {
        gameSet.paint(g);
    }

    public void mouseClicked(MouseEvent e)
    {
        System.out.println("Mouse Clicked!!!!!!!!!");
    }
    public void mouseDragged(MouseEvent e)
    {
        setMousePosition(e);
    }
    public void mouseMoved(MouseEvent e)
    {
        setMousePosition(e);
        System.out.println(mouse_x+ ", "+mouse_y);
    }

    private void setMousePosition(MouseEvent e)
    {
        mouse_x=e.getX();
        mouse_y=e.getY();
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args)
    {
        //set the gui and graphics
        JFrame frame = new JFrame("checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GameWindow());
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(new GameWindow(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600,600);
        frame.setVisible(true);
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
