import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Board
{
    static final int HEIGHT = 60;
    static final int WIDTH = 60;
    public static boolean isRemoved = false;
    public static boolean ISOCCUPIED = false;
    public static boolean ishighlighted =false;

    public int old_row;
    public int old_col;
    public int new_row;
    public int new_col;
    public int pot_row;
    public int pot_col;

    public int clicks=0;

    private Move move;

    private Point highlight;

    static public Piece removedPiece;

    public static boolean player1turn = true;

    //size of the Board
    static final int BOARD_SIZE = 8;
    public static int ROW = 0;

    //array to store checkers
    private Piece[][] board;

    public ArrayList<Piece> Pieces = new ArrayList<>();

    //array to store the points where a potential move can be made
    public ArrayList<Point> potMoves = new ArrayList<>();

    BufferedImage ninja, tache, ninjaKing, tacheKing;

    //private PreviousMove[] pastMoves;
    //private int pastmovesize = 5;

    public Board()
    {
        //store the Board into a 2d array
        board = new Piece [BOARD_SIZE][BOARD_SIZE];
        move = new Move(this);
        System.out.println("CONSTRUCTOR, initialising pastMoves");
        installCheckers();

        try {
            ninja = ImageIO.read(new File("images/ninja.png"));
            ninjaKing = ImageIO.read(new File("images/ninja_king.png"));
            tache = ImageIO.read(new File("images/tache.png"));
            tacheKing = ImageIO.read(new File("images/tache_king.png"));
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }


    public void setClicks()
    {
         clicks=0;
    }

    public void installCheckers()
    {
        for (ROW = 0; ROW < BOARD_SIZE; ROW += 2)
        {
            board[0][ROW] = new Piece(Type.normal, Colour.BLACK, new Point(0, ROW));
            board[2][ROW] = new Piece(Type.normal, Colour.BLACK, new Point(2, ROW));
            board[6][ROW] = new Piece(Type.normal, Colour.WHITE, new Point(6, ROW));
        }
        for (ROW = 1; ROW < BOARD_SIZE; ROW += 2)
        {
            board[1][ROW] = new Piece(Type.normal, Colour.BLACK, new Point(1, ROW));
            board[5][ROW] = new Piece(Type.normal, Colour.WHITE, new Point(5, ROW));
            board[7][ROW] = new Piece(Type.normal, Colour.WHITE, new Point(7, ROW));
        }
    }


    public void update()
    {
    }

    public void highlightTile(int x, int y)
    {
        if(validPos(y,x))
        highlight = new Point(y, x);
        old_row = y;
        old_col = x;

        potMoves = new ArrayList<>();
        if(validPlayer(y, x)) {
            if ((!move.couldJump(y, x).isEmpty())) {
                for (Point p : move.couldJump(y, x)) {
                    System.out.println("---------POTENTIAL MOVE: " + p.toString());
                    potMoves.add(p);
                }
            } else if (!move.couldMove(y, x).isEmpty()) {
                for (Point p : move.couldMove(y, x)) {
                    System.out.println("---------POTENTIAL MOVE: " + p.toString());
                    potMoves.add(p);
                }
            }
        }
        pot_row = old_row;
        pot_col = old_col;

        //if(getChecker(old_row, old_col) != null)
        //potMove(old_row, old_col, pot_row, pot_col);
    }

    public void secondClick(int row, int col)
    {
        new_row = row;
        new_col = col;
        checkMove(old_row, old_col, new_row, new_col);
        //System.out.println("replay:::::: " + PastMoves.getPreviousMoves());
    }

    public void checkMove(int row, int col, int destRow, int destCol) {

        if( validPlayer(row, col)) {
            if ((!move.couldJump(row, col).isEmpty())) {
                for (Point p : move.couldJump(row, col)) {
                    System.out.println("---------POTENTIAL MOVE: " + p.toString());
                    potMoves.add(p);
                }
                moveIfJumping(row, col, destRow, destCol);
                if (move.validKing(destRow, destCol))
                    board[destRow][destCol].setType(Type.king);

            } else {
                if (!move.couldMove(row, col).isEmpty()) {
                    for (Point p : move.couldMove(row, col)) {
                        System.out.println("---------POTENTIAL MOVE: " + p.toString());
                        potMoves.add(p);
                    }
                    moveIfNotJumping(row, col, destRow, destCol);
                    if (move.validKing(destRow, destCol)) {
                        board[destRow][destCol].setType(Type.king);
                    }
                }
            }
        }
        else
        {
            System.out.println("Invalid checker!");
        }
        potMoves.clear();
    }

    public void removeChecker(int row, int col)
    {
        board[row][col] = null;
    }
    public void removetakenPiece(int row, int col)
    {
        removedPiece = new Piece(board[row][col].getType(), board[row][col].getColour(), new Point(row, col));
        board[row][col] = null;
    }

    private void moveIfJumping(int row, int col, int destRow, int destCol)
    {
        PreviousMove pm;
        //check if white
        System.out.println("origin Point: "+getChecker(row,col).getPoint().getRow() + " "+ getChecker(row,col).getPoint().getCol());
        if (getChecker(row, col) != null && ((getChecker(row, col).getColour() == Colour.WHITE && getChecker(row, col).getType() == Type.normal) || getChecker(row, col).getType() == Type.king)) {
            if (destRow +2 == row) {
                //check if right
                if (destCol -2 == col) {
                    if (getChecker(row - 1, col + 1) != null && getChecker(row - 1, col + 1).getColour() == move.oppositeColour(getChecker(row, col).getColour())) {
                        System.out.println("white or king right");
                        removetakenPiece(row - 1, col + 1);
                        board[destRow][destCol] = board[row][col];
                        removeChecker(row, col);
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);

                        if(move.couldJump(destRow, destCol).isEmpty())
                            nextTurn();
                        else
                            highlightTile(destRow, destCol);
                    }
                }
                //check if left
                if (destCol +2 == col) {
                    if (getChecker(row - 1, col - 1) != null && getChecker(row - 1, col - 1).getColour() == move.oppositeColour(getChecker(row, col).getColour())) {
                        System.out.println("white or king left");
                        removetakenPiece(row - 1, col - 1);
                        board[destRow][destCol] = board[row][col];
                        removeChecker(row, col);
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);

                        if(move.couldJump(destRow, destCol).isEmpty())
                            nextTurn();
                        else
                            highlightTile(destRow, destCol);
                    }
                }
            }
        }
        //check if black
        if (getChecker(row, col) != null && ((getChecker(row, col).getColour() == Colour.BLACK && getChecker(row, col).getType() == Type.normal) || getChecker(row, col).getType() == Type.king)) {
            if (destRow -2 == row) {
                //check if right
                if (destCol - 2 == col) {
                    if (getChecker(row + 1, col + 1) != null && getChecker(row + 1, col + 1).getColour() == move.oppositeColour(getChecker(row, col).getColour())){
                        System.out.println("black or king right");
                        removetakenPiece(row + 1, col + 1);
                        board[destRow][destCol] = board[row][col];
                        removeChecker(row, col);
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);

                        if(move.couldJump(destRow, destCol).isEmpty())
                            nextTurn();
                        else
                            highlightTile(destRow, destCol);
                    }
                }
                if (destCol + 2 == col) {
                    //check if left
                    if (getChecker(row + 1, col - 1) != null && getChecker(row + 1, col - 1).getColour() == move.oppositeColour(getChecker(row, col).getColour())){
                        System.out.println("black or king left");
                        removetakenPiece(row + 1, col - 1);
                        board[destRow][destCol] = board[row][col];
                        removeChecker(row, col);
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);

                        if(move.couldJump(destRow, destCol).isEmpty())
                            nextTurn();
                        else
                            highlightTile(destRow, destCol);
                    }
                }
            }
        }
    }

    public void moveIfNotJumping(int row, int col, int destRow, int destCol)
    {
        PreviousMove pm;
        if(getChecker(row, col) != null && (getChecker(row,col).getColour() == Colour.WHITE && getChecker(row,col).getType() == Type.normal) || getChecker(row, col).getType() == Type.king) {
            if (destRow +1 == row && validPos(destRow, destCol) && board[destRow][destCol]== null)
            {
                if (destCol - 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                    nextTurn();
                }
                if (destCol + 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                    nextTurn();
                }
            }
        }
        if(getChecker(row, col) != null && ((getChecker(row,col).getColour() == Colour.BLACK && getChecker(row,col).getType() == Type.normal) || getChecker(row, col).getType() == Type.king))
        {
            if (destRow -1 == row && validPos(destRow, destCol) && board[destRow][destCol]== null)
            {
                if (destCol - 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                    nextTurn();
                }
                if (destCol + 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                    nextTurn();
                }
            }
        }
    }


    public Piece getChecker(int row, int col)
    {
        if(validPos(row, col))
            return board[row][col];

        return null;
    }

    public boolean validPos(int x, int y)
    {
        return x>=0 && y>=0 && x<BOARD_SIZE && y<BOARD_SIZE;
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //dimensions of the rectangle of the Board
        int x=0;
        int y=0;

        //loop for every row
        for (int i=0; i<BOARD_SIZE;i++) {
            if (i != 0) {
                y += HEIGHT;
                x = 0;
            }
            //loop for every square within row
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (j % 2 == 0) {
                    //for every second row the colour changes
                    g2d.setColor(i % 2 == 0 ? Color.BLACK : Color.red);

                } else {
                    g2d.setColor(i % 2 == 0 ? Color.red : Color.BLACK);
                }
                g2d.fillRect(x, y, WIDTH, HEIGHT);

                if (board[i][j] != null && board[i][j].getColour() == Colour.WHITE && board[i][j].getType() == Type.normal)
                {
                    g2d.drawImage(tache, x + 5, y + 5, null);
                }
                if (board[i][j] != null && board[i][j].getColour() == Colour.BLACK && board[i][j].getType() == Type.normal )
                {
                    g2d.drawImage(ninja, x + 5, y + 5, null);
                }
                if(board[i][j] != null && board[i][j].getType() == Type.king && board[i][j].getColour() == Colour.BLACK)
                {
                    g2d.drawImage(ninjaKing, x + 5, y + 5, null);
                }
                if(board[i][j] != null && board[i][j].getType() == Type.king && board[i][j].getColour() == Colour.WHITE)
                {
                    g2d.drawImage(tacheKing, x + 5, y + 5, null);
                }
                //increment the x so that there is a new square beside the last

                //g2d.fillOval(x + 10, y + 10, 3 * WIDTH / 4, 3 * HEIGHT / 4);
                x += WIDTH;
                Pieces.add(board[i][j]);
            }

            if(highlight != null) {
                g2d.setColor(Color.orange);
                g2d.drawRect(highlight.getCol() * 60, highlight.getRow() * 60, 60, 60);
            }
        }

        if(!potMoves.isEmpty()) {
            for (Point p : potMoves) {
                g2d.setColor(Color.yellow);
                g2d.fillRect(p.getCol() * 60, p.getRow() * 60, WIDTH, HEIGHT);
            }
        }

    }
    public Piece[][] getBoard()
    {
        return board;
    }

    public void clearBoard()
    {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        installCheckers();
    }

    public void nextTurn()
    {
        player1turn = !player1turn;
    }

    private boolean validPlayer(int row, int col) {
        if(getChecker(row, col) ==  null)
            return false;
        if(getChecker(row, col).getColour() == Colour.WHITE && player1turn)
            return true;
        if(getChecker(row, col).getColour() == Colour.BLACK && !player1turn)
            return true;

        return false;
    }

    public int currPlayer()
    {
        if(player1turn)
            return 1;

        return 2;
    }

    public void AI()
    {
        Random rn = new Random();
        int decision = rn.nextInt(4);

        for (Piece p : Pieces) {
            //choose a random Piece from all the Pieces
        }
//        highlightTile();
    }

}



