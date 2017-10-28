import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameWindow extends JPanel implements ActionListener, MouseListener, MouseMotionListener, Runnable
{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private Board board = new Board();
    private static int player = 1;
    private Piece piece;
    private int clicks=0;

    private JButton undo;

    //game loop fields
    private Thread thread;
    private int FPS=30;
    private long targetTime = 1000/FPS;
    private boolean isRunning = false;

    //mouse position
    private static int mouse_x, mouse_y;
    private int old_click_x=40;
    private int old_click_y=40;

    private GameWindow()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);

        undo = new JButton(new ImageIcon(getClass().getResource("undo.png")));
        undo.setBounds(500,500, 40, 40);
        add(undo);
        undo.addActionListener(this);
        start();
    }

    public void actionPerformed(ActionEvent e) {
        board.undo();
    }

    public void mousePressed(MouseEvent e)
    {
        clicks++;

        if(clicks > 2)
            clicks = 1;

        if(clicks == 1) {
            if(board.validPos(e.getX()/60,e.getY()/60))
            board.highlightTile(e.getX() / 60, e.getY() / 60);
        }

        if(clicks == 2) {
           if(board.validPos(e.getX()/60,e.getY()/60))
            board.secondClick(e.getY() / 60, e.getX() / 60);
        }

    }
    public void mouseDragged(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
    }

    private void setMousePosition(MouseEvent e)
    {
      //setMousePosition();
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

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
        board.paint(g);
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
        x++;
    }

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
    public static void move(String[][] Board, int player)
    {
        //create a buffered reader to read the move

        //display label for whos turn it is and after that turn is done switch it to the next player

    }*/

}
