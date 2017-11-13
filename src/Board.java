import java.awt.*;


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

    public int colourRow = 1;

    public int kingrow = 2;


    public boolean couldjump = false;

    //size of the Board
    static final int BOARD_SIZE = 8;
    public static int ROW = 0;

    //array to store checkers
    private Piece[][] board;

    //array to store the points where a potential move can be made
    private Point[] potMoves;



    //private PreviousMove[] pastMoves;
    //private int pastmovesize = 5;

    public Board()
    {
        //store the Board into a 2d array
        board = new Piece [BOARD_SIZE][BOARD_SIZE];
        potMoves = new Point[BOARD_SIZE];
        move = new Move(this);
        System.out.println("CONSTRUCTOR, initialising pastMoves");
        installCheckers();
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
        //ishighlighted=true;
        if(validPos(y,x))
        highlight = new Point(y, x);
        old_row = y;
        old_col = x;

        potMoves = new Point[BOARD_SIZE];
        pot_row = old_row;
        pot_col = old_col;

        //if(getChecker(old_row, old_col) != null)
        //potMove(old_row, old_col, pot_row, pot_col);
    }

//    public void potMove(int old_row, int old_col, int pot_row, int pot_col)
//    {
//        if(getChecker(old_row, old_col).getColour() == Colour.WHITE && getChecker(old_row, old_col).getType() == Type.normal && board[old_row-1][old_col+1] == null)
//            colourRow = -1;
//        else if(getChecker(old_row, old_col).getColour() == Colour.BLACK && getChecker(old_row, old_col).getType() == Type.normal)
//            colourRow = 1;
//
//        pot_row += colourRow;
//        pot_col++;
//        if (move.validMove(old_row, old_col, pot_row, pot_col))
//        {
//            if(validPos(pot_row, pot_col)) {
//                potMoves = new Point[BOARD_SIZE];
//                potMoves[0] = new Point(pot_row, pot_col);
//            }
//        }
//        pot_col-=2;
//        if(move.validMove(old_row, old_col, pot_row, pot_col))
//            if(validPos(pot_row, pot_col)) {
//                potMoves[1] = new Point(pot_row, pot_col);
//            }
//        pot_row += colourRow;
//        pot_col--;
//        if(validPos(pot_row, pot_col))
//        {
//            //if(move.validJump(old_row, old_col, pot_row, pot_col)) {
//                couldjump = true;
//                potMoves = new Point[BOARD_SIZE];
//                potMoves[0] = new Point(pot_row, pot_col);
////                if (getChecker(old_row, old_col).getType() == Type.king) {
////                    if (colourRow == -1)
////                        kingrow = 4;
////                    else
////                        kingrow = -4;
////                    pot_row += kingrow;
////                    if (validPos(pot_row, pot_col))
////                        potMoves[potmovesindex++] = new Point(pot_row, pot_col);
////                    pot_row -= kingrow;
////                }
////            }
////            else
////                couldjump = false;
//        }
//        else {
//            pot_col += 4;
//
//            if (validPos(pot_row, pot_col)) {
//                System.out.println(couldjump);
////                if(move.validJump(old_row, old_col, pot_row, pot_col)) {
////                    couldjump = true;
////                    potMoves[1] = new Point(pot_row, pot_col);
////                }
//            }
//            else
//                couldjump = false;
//        }
//    }

    //public PreviousMove peekPastMoves()
//    {
//        //PreviousMove firstMove = pastMoves[0];
//        //PreviousMove lastMove = pastMoves[pastMoves.length-1];
//        //return lastMove;
//    }

    public void secondClick(int row, int col)
    {
        new_row = row;
        new_col = col;
        checkMove(old_row, old_col, new_row, new_col);
        //System.out.println("replay:::::: " + PastMoves.getPreviousMoves());
    }

    public void checkMove(int row, int col, int destRow, int destCol) {

        if (move.couldJump(row, col))
        {
            moveIfJumping(row, col, destRow, destCol);
            if (move.validKing(destRow, destCol))
                board[destRow][destCol].setType(Type.king);
        }
        else
        {
            if(move.couldMove(row, col)) {
                moveIfNotJumping(row, col, destRow, destCol);
                if (move.validKing(destRow, destCol)) {
                    board[destRow][destCol].setType(Type.king);
                }
            }
        }
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
                        potMoves = new Point[BOARD_SIZE];
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);
                    }
                }
                //check if left
                if (destCol +2 == col) {
                    if (getChecker(row - 1, col - 1) != null && getChecker(row - 1, col - 1).getColour() == move.oppositeColour(getChecker(row, col).getColour())) {
                        System.out.println("white or king left");
                        removetakenPiece(row - 1, col - 1);
                        board[destRow][destCol] = board[row][col];
                        removeChecker(row, col);
                        potMoves = new Point[BOARD_SIZE];
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);
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
                        potMoves = new Point[BOARD_SIZE];
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);
                    }
                }
                if (destCol + 2 == col) {
                    //check if left
                    if (getChecker(row + 1, col - 1) != null && getChecker(row + 1, col - 1).getColour() == move.oppositeColour(getChecker(row, col).getColour())){
                        System.out.println("black or king left");
                        removetakenPiece(row + 1, col - 1);
                        board[destRow][destCol] = board[row][col];
                        removeChecker(row, col);
                        potMoves = new Point[BOARD_SIZE];
                        pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), removedPiece);
                        PastMoves.addMove(pm);
                    }
                }
            }
        }
    }

    public void moveIfNotJumping(int row, int col, int destRow, int destCol)
    {
        PreviousMove pm;
        if(getChecker(row, col) != null && (getChecker(row,col).getColour() == Colour.WHITE && getChecker(row,col).getType() == Type.normal) || getChecker(row, col).getType() == Type.king) {
            if (destRow +1 == row)
            {
                if (destCol - 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    potMoves = new Point[BOARD_SIZE];
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                }
                if (destCol + 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    potMoves = new Point[BOARD_SIZE];
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                }
            }
        }
        if(getChecker(row, col) != null && ((getChecker(row,col).getColour() == Colour.BLACK && getChecker(row,col).getType() == Type.normal) || getChecker(row, col).getType() == Type.king))
        {
            if (destRow -1 == row)
            {
                if (destCol - 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    potMoves = new Point[BOARD_SIZE];
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
                }
                if (destCol + 1 == col) {
                    board[destRow][destCol] = board[row][col];
                    removeChecker(row, col);
                    potMoves = new Point[BOARD_SIZE];
                    pm = new PreviousMove(new Point(row, col), new Point(destRow, destCol), null);
                    PastMoves.addMove(pm);
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
                    g2d.setColor(Color.white);
                }
                if (board[i][j] != null && board[i][j].getColour() == Colour.BLACK && board[i][j].getType() == Type.normal )
                {
                    g2d.setColor(Color.DARK_GRAY);
                }
                if(board[i][j] != null && board[i][j].getType() == Type.king)
                {
                    g2d.setColor(Color.MAGENTA);
                }
                //increment the x so that there is a new square beside the last

                g2d.fillOval(x + 10, y + 10, 3 * WIDTH / 4, 3 * HEIGHT / 4);
                x += WIDTH;
            }

            if(highlight != null) {
                g2d.setColor(Color.orange);
                g2d.drawRect(highlight.getCol() * 60, highlight.getRow() * 60, 60, 60);
            }
        }
        for(int index = 0; index < potMoves.length; index++) {
            if(potMoves[index] != null)
            {
                g2d.setColor(Color.yellow);
                g2d.fillRect(potMoves[index].getCol() *60, potMoves[index].getRow() *60, WIDTH, HEIGHT);
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
}



