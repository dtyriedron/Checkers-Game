import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GameWindow extends JPanel implements ActionListener, MouseListener, MouseMotionListener, Runnable
{
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;
    private Board board;
    private static int player = 1;
    private Piece piece;
    private int clicks=0;

    private static JLabel currentPlayer;
    private static JButton undo, redo, replay;



    //game loop fields
    private Thread thread;
    private int FPS=30;
    private long targetTime = 1000/FPS;
    private boolean isRunning = false;


    public GameWindow()
    {
        inputType();
        System.out.println("constructor called");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);


        board = new Board();
        currentPlayer = new JLabel("");
        try {
            undo = new JButton(new ImageIcon(ImageIO.read(new File("images/undo.png"))));
            undo.setPreferredSize(new Dimension(40, 40));
            undo.addActionListener(this);

            redo = new JButton(new ImageIcon(ImageIO.read(new File("images/redo.png"))));
            redo.setPreferredSize(new Dimension(40, 40));
            redo.addActionListener(this);

            replay = new JButton(new ImageIcon(ImageIO.read(new File("images/replay.png"))));
            replay.setPreferredSize(new Dimension(40, 40));
            replay.addActionListener(this);
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
        }
        start();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == undo)
            Move.undoMove();
        if(e.getSource() ==  redo)
            Move.redoMove();
        if(e.getSource() == replay)
            Move.replayGame();

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

    private void inputType()
    {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a number to get a game type: (1= player vs player, 2= player vs ai 3=ai vs ai)");
        int n = reader.nextInt();
        try {
            if (n == 1) {
                board.gameSetup(GameType.PLAYER_VS_PLAYER);
            }
            if (n == 2) {
                board.gameSetup(GameType.PLAYER_VS_AI);
            }
            if (n == 3) {
                board.gameSetup(GameType.AI_VS_AI);
            }
        }catch(Exception e){}
    }
    public void update()
    {
        currentPlayer.setText("Player "+board.currPlayer() + "'s turn");
    }

    public static void main(String[] args)
    {
        //set the gui and graphics
        JFrame frame = new JFrame("checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.setContentPane(new GameWindow());
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(new GameWindow(), gbc);

        //change the pos of the replay button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(replay, gbc);

        //change the pos of the undo button
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(undo, gbc);

        //change the pos of redo button
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(redo, gbc);

        //change the pos of currentplayer lbl
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(currentPlayer, gbc);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600,510);
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
