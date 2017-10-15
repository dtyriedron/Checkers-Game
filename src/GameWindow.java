import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JPanel implements MouseListener, MouseMotionListener, Runnable
{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private GameSetup gameSet = new GameSetup();
    private Move move = new Move();
    public static int BLANK = 1;

    //game loop fields
    private Thread thread;
    private int FPS=30;
    private long targetTime = 1000/FPS;
    public boolean isRunning = false;

    //mouse position
    public static int mouse_x, mouse_y;

    public GameWindow()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        start();
    }


    public void mouseClicked(MouseEvent e)
    {
        System.out.println("row: "+e.getX()+" col: "+e.getY());
        move.test(e.getX(), e.getY());
        gameSet.update(mouse_x / 60, mouse_y / 60);
        BLANK = 2;
        mouse_x= e.getX();
        mouse_y = e.getY();
    }
    public void mouseDragged(MouseEvent e)
    {
        setMousePosition(e);
    }
    public void mouseMoved(MouseEvent e)
    {
        setMousePosition(e);
       // System.out.println(mouse_x+ ", "+mouse_y);
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




    public void start()
    {
        isRunning = true;
        thread = new Thread(this, "Game Loop");
        thread.start();
    }


    public void stop()
    {
        isRunning=false;
        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }



    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        gameSet.paint(g);
        g2d.dispose();
    }

    //game loop
    public void run()
    {
        /*int timerDelay = 20;
        new Timer(timerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });*/
        long start, elapsed, wait;

        while(isRunning)
        {
            start = System.currentTimeMillis();

            update();
            repaint();

            elapsed = System.currentTimeMillis() - start;

            wait = targetTime - elapsed;
            if(wait<5)
            {
                wait =5;
            }
            try
            {
                Thread.sleep(wait);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        stop();
    }

    private int x=0;

    public void update()
    {
        if(BLANK ==2)
        {
            gameSet.click();

            BLANK=1;
        }
        x++;
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
