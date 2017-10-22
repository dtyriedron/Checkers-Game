import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static java.sql.Types.NULL;

public class GameWindow extends JPanel implements MouseListener, MouseMotionListener, Runnable
{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private Board board = new Board();
    public static int player = 1;
    private Piece piece = new Piece();
    public int clicks=0;

    //game loop fields
    private Thread thread;
    private int FPS=30;
    private long targetTime = 1000/FPS;
    public boolean isRunning = false;

    //mouse position
    public static int mouse_x, mouse_y;
    public int old_click_x=40;
    public int old_click_y=40;

    public GameWindow()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        start();
    }

    public void mouseClicked (MouseEvent e)
    {
        mouse_x = e.getX()/60;
        mouse_y = e.getY()/60;
        board.highlight(mouse_x,mouse_y);

        if(board.getBoard()[mouse_y][mouse_x]==NULL)
        {
            clicks--;
        }

        if(player>2)
        {
            player = 1;
        }

        if (clicks > 2)
        {
            clicks=0;
            System.out.println("old x: "+old_click_x);
            player++;
        }

        clicks++;

        if(player==1)
        {
            System.out.println("\n                    player 1                                    \n");
            if(board.getBoard()[mouse_y][mouse_x]==piece.WHITE && clicks==1)
            {
                clicks--;
                board.setClicks();
                board.update(mouse_x,mouse_y);
                old_click_x=mouse_x;
                old_click_y=mouse_y;
            }

            try
            {
                if (board.getBoard()[mouse_y][mouse_x] == NULL && board.getBoard()[old_click_y][old_click_x] == piece.WHITE)
                {
                    board.setClicks();
                    board.isMoving(mouse_x, mouse_y, old_click_x, old_click_y);
                    board.update(mouse_x, mouse_y);
                    clicks = 4;
                }
            }catch(Exception f){}

            if(board.getBoard()[mouse_y][mouse_x]== piece.BLACK && (board.getBoard()[mouse_y][mouse_x]==NULL && clicks==1))
            {
                System.out.println("invalid click");
                clicks = 0;
            }
        }

        if(player==2)
        {
            System.out.println("\n                    player 2                                    \n");
            if(board.getBoard()[mouse_y][mouse_x]==piece.BLACK && clicks==1)
            {
                clicks--;
                board.setClicks();
                System.out.println("click: "+clicks);
                board.update((mouse_x), (mouse_y));
                old_click_x=mouse_x;
                old_click_y=mouse_y;
            }

            try {
                if (board.getBoard()[mouse_y][mouse_x] == NULL && board.getBoard()[old_click_y][old_click_x] == piece.BLACK)
                {
                    System.out.println("click: " + clicks);
                    board.setClicks();
                    board.isMoving(mouse_x, mouse_y, old_click_x, old_click_y);
                    board.update(mouse_x, mouse_y);
                    clicks = 3;
                }
            }catch(Exception f){}
            if(board.getBoard()[mouse_y][mouse_x]== piece.WHITE)
            {
                System.out.println("invalid click");
                    clicks = 0;
            }

            if (clicks > 2)
            {
                clicks=0;
                player++;
            }
        }




    }
    public void mouseDragged(MouseEvent e)
    {
        mouseClicked(e);
    }
    public void mouseMoved(MouseEvent e)
    {
    }

    private void setMousePosition(MouseEvent e)
    {
      //setMousePosition();
    }

    public void mousePressed(MouseEvent e) {}

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
