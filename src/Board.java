import java.awt.*;


public class Board
{
    private Piece piece;

    static final int HEIGHT = 60;
    static final int WIDTH = 60;
    public static boolean isRemoved = false;
    public static boolean ISOCCUPIED = false;
    public static boolean ishighlighted =false;

    public int old_row;
    public int old_col;
    public int new_row;
    public int new_col;


    public boolean isblack = false;
    public boolean isoldblack = false;
    public boolean blackcanmoveright = false;
    public boolean blackcanmoveleft = false;
    public boolean iswhite = false;
    public boolean isoldwhite = false;
    public boolean whitecanmoveright = false;
    public boolean whitecanmoveleft = false;

    public boolean blackcanmoverightdia =false;
    public boolean blackcanmoveleftdia = false;
    public boolean whitecanmoverightdia = false;
    public boolean whitecanmoveleftdia = false;
    public boolean blackcantakeleft = false;
    public boolean blackcantakeright = false;
    public boolean whitecantakeleft = false;
    public boolean whitecantakeright = false;

    public int clicks=0;

    private Move move;

    private Point highlight;

    //size of the Board
    static final int BOARD_SIZE = 8;
    public static int ROW = 0;

    //array to store checkers
    private Piece[][] board;

    public Board()
    {
        //store the Board into a 2d array
        board = new Piece [BOARD_SIZE][BOARD_SIZE];
        move = new Move(this);
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
            board[0][ROW] = new Piece(Type.normal, Colour.BLACK);
            board[2][ROW] = new Piece(Type.normal, Colour.BLACK);
            board[6][ROW] = new Piece(Type.normal, Colour.WHITE);
        }
        for (ROW = 1; ROW < BOARD_SIZE; ROW += 2)
        {
            board[1][ROW] = new Piece(Type.normal, Colour.BLACK);
            board[5][ROW] = new Piece(Type.normal, Colour.WHITE);
            board[7][ROW] = new Piece(Type.normal, Colour.WHITE);
        }
    }


    public void update(int x, int y)
    {
    }

    public void highlightTile(int x, int y)
    {
        //ishighlighted=true;
        highlight = new Point(y, x);
        //System.out.println(x + " " + y);
        old_row = y;
        old_col = x;
        System.out.println(old_row + " " + old_col);
    }

    public void secondClick(int row, int col) {
        new_row = row;
        new_col = col;

        System.out.println(row + " " + col);

        //TODO check if move from old_row, old_col to new_row, new_col is valid!!
        checkMove(old_row, old_col, new_row, new_col);
    }

    private void checkMove(int row, int col, int destRow, int destCol) {
        if(move.validMove(row, col, destRow, destCol)) {
            move(row, col, destRow, destCol);
        }
    }

    private void move(int row, int col, int destRow, int destCol) {
        board[destRow][destCol] = board[row][col];
        board[row][col] = null;
    }

    public Piece getChecker(int row, int col) {
        return board[row][col];
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

                if (board[i][j] != null && board[i][j].getColour() == Colour.WHITE)
                {
                    g2d.setColor(Color.white);
                }
                else if (board[i][j] != null && board[i][j].getColour() == Colour.BLACK )
                {
                    g2d.setColor(Color.DARK_GRAY);
                }
                //increment the x so that there is a new square beside the last

                g2d.fillOval(x + 10, y + 10, 3 * WIDTH / 4, 3 * HEIGHT / 4);
                x += WIDTH;
            }

//            if(board[y][x].getType() == Type.king)
//            {
//                g2d.setColor(Color.MAGENTA);
//                g2d.fillOval(highlightX*60,highlightY*60,WIDTH,HEIGHT);
//            }

            /*if (ishighlighted)
            {
                g2d.setColor(Color.orange);
                g2d.drawRect(highlightX * 60, highlightY * 60, WIDTH, HEIGHT);
            }

            g2d.setBackground(new Color(0, 191, 255, 50));

            if (clicks == 0)
            {
                try
                {
                    if (validPos(highlightX,highlightY))
                    {
                        //if white can move right
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX + 1, highlightY - 1)))
                        {
                            g2d.fillRect((highlightX + 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
                        }
                        //if white can move left
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX - 1, highlightY - 1)))
                        {
                            g2d.fillRect((highlightX - 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
                        }
                        //if white can take right
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX + 2, highlightY - 2)))
                        {
                            g2d.fillRect((highlightX + 2) * 60, (highlightY - 2) * 60, WIDTH, HEIGHT);
                        }

                        //if white can take left
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX - 2, highlightY - 2)))
                        {
                            g2d.fillRect((highlightX - 2) * 60, (highlightY - 2) * 60, WIDTH, HEIGHT);
                        }

                        //if black can move left
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX - 1, highlightY + 1)))
                        {
                            g2d.fillRect((highlightX - 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
                        }
                        //if black can move right
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX + 1, highlightY + 1)))
                        {
                            g2d.fillRect((highlightX + 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
                        }
                        //if black can take white on the right
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX + 2, highlightY + 2)))
                        {
                            g2d.fillRect((highlightX + 2) * 60, (highlightY + 2) * 60, WIDTH, HEIGHT);
                        }

                        //if black can take white on the left
                        if (move.isValidMove(new Point(highlightX, highlightY), new Point(highlightX - 2, highlightY + 2)))
                        {
                            g2d.fillRect((highlightX - 2) * 60, (highlightY - 2) * 60, WIDTH, HEIGHT);
                        }

                    }
                }catch (ArrayIndexOutOfBoundsException e){}
//                try
//                {
//                    validMove(highlightX, highlightY);
//                    if (isblack)
//                    {
//                        if (blackcanmoveright)
//                        {
//                            //make sure it doesent paint outside the board
//                            if (((highlightX + 1) >= 0 && (highlightX + 1) < BOARD_SIZE) && ((highlightY + 1) < BOARD_SIZE))
//                            {
//                                g2d.fillRect((highlightX + 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                        if (blackcanmoveleft)
//                        {
//                            if ((highlightX - 1) >= 0 && highlightY + 1 < BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX - 1) * 60, (highlightY + 1) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                        if (blackcanmoverightdia)
//                        {
//                            if ((highlightX + 2) < BOARD_SIZE && highlightY + 2 < BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX + 2) * 60, (highlightY + 2) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                        if (blackcanmoveleftdia)
//                        {
//                            if ((highlightX - 2) >= 0 && highlightY+2<BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX - 2) * 60, (highlightY + 2) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                    }
//                    if (iswhite)
//                    {
//                        if (whitecanmoveright)
//                        {
//                            if ((highlightX + 1) < BOARD_SIZE && highlightY+2<BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX + 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                        if (whitecanmoveleft)
//                        {
//                            if ((highlightX - 1) >= 0 && highlightY-1<BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX - 1) * 60, (highlightY - 1) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                        if (whitecanmoveleftdia)
//                        {
//                            if ((highlightX - 2) >=0 && highlightY-2<BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX - 2) * 60, (highlightY - 2) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                        if (whitecanmoverightdia)
//                        {
//                            if ((highlightX + 2) <BOARD_SIZE && highlightY-2 <BOARD_SIZE)
//                            {
//                                g2d.fillRect((highlightX + 2) * 60, (highlightY - 2) * 60, WIDTH, HEIGHT);
//                            }
//                        }
//                    }
//                } catch (ArrayIndexOutOfBoundsException e) {}

            } */

            if(highlight != null) {
                g2d.setColor(Color.orange);
                g2d.drawRect(highlight.getCol() * 60, highlight.getRow() * 60, 60, 60);
            }
        }

    }

    private void removeChecker(int x, int y)
    {

        //black can take white to the right
        if(blackcantakeright)
        {
            board[y+1][x+1] = null;
        }
        //black can take white to the left
        if(blackcantakeleft)
        {
            board[y+1][x-1] = null;
        }

        //white can take black to the right
        if(whitecantakeright)
        {
            board[y-1][x+1] = null;
        }
        //white can take black to the left
        if(whitecantakeleft)
        {
            board[y-1][x-1] = null;
        }

        board[y][x] = null;
        if (board[y][x] == null)
        {
            isRemoved = true;
        }
        else
        {
            isRemoved = false;
        }
    }
    private void addChecker(int x, int y)
    {
        if(board[y][x] == null && isoldblack)
        {
            if(y==7)
            {
                board[y][x] = new Piece(Type.king, Colour.BLACK);
            }
            else
            {
                System.out.println("black piece added");
                board[y][x] = new Piece(Type.normal, Colour.BLACK);
            }
        }
        if(board[y][x] == null && isoldwhite)
        {
            if(y==7)
            {
                board[y][x] = new Piece(Type.king, Colour.WHITE);
            }
            else
            {
                System.out.println("white piece added");
                board[y][x] = new Piece(Type.normal, Colour.WHITE);
            }
        }
    }

    public void isMoving(int x, int y, int old_x, int old_y)
    {
        System.out.println("x: "+x+" y: "+y);
        System.out.println("old x: "+old_x+ " old y: "+old_y);
        clicks=1;
        //black can move left
        try
        {
            if (old_x-1 == x && old_y+1 == y && board[old_y][old_x].getColour() == Colour.BLACK && (board[y][x] == null))
            {
                isoldblack=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                isoldblack=false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //black can move right
        try
        {
            if(old_x+1 == x && old_y+1 == y && board[old_y][old_x].getColour() == Colour.BLACK &&  board[y][x] == null)
            {
                isoldblack=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                isoldblack =false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        //black can take left
        try
        {
            if (old_x-2 == x && old_y+2 == y && board[old_y][old_x].getColour() == Colour.BLACK && (board[old_y+1][old_x-1].getColour() == Colour.WHITE && board[y][x] == null))
            {
                blackcantakeleft=true;
                isoldblack=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                blackcantakeleft=false;
                isoldblack=false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //black can take right
        try
        {
            if(old_x+2 == x && old_y+2 == y && board[old_y][old_x].getColour() == Colour.BLACK && (board[old_y+1][old_x+1].getColour() == Colour.WHITE && board[y][x] == null))
            {
                blackcantakeright=true;
                isoldblack=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                blackcantakeright=false;
                isoldblack =false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //white can move left
        try
        {
            if(old_x-1 == x && old_y-1 == y && board[old_y][old_x].getColour() == Colour.WHITE && board[y][x] == null)
            {
                isoldwhite=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                isoldwhite=false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //white can move right
        try
        {
            if(old_x+1 == x && old_y-1 == y && board[old_y][old_x].getColour() == Colour.WHITE && board[y][x] == null)
            {
                isoldwhite=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                isoldwhite=false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}

        //white can take left
        try
        {
            if(old_x-2 == x && old_y-2 == y && board[old_y][old_x].getColour() == Colour.WHITE && (board[old_y-1][old_x-1].getColour() == Colour.BLACK && board[y][x] == null))
            {
                whitecantakeleft=true;
                isoldwhite=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                whitecantakeleft=false;
                isoldwhite=false;
            }

        }catch(ArrayIndexOutOfBoundsException e){}

        //white can take right
        try
        {
            if(old_x+2 == x && old_y-2 == y && board[old_y][old_x].getColour() == Colour.WHITE && (board[old_y-1][old_x+1].getColour() == Colour.BLACK && board[y][x] == null))
            {
                whitecantakeright=true;
                isoldwhite=true;
                removeChecker(old_x,old_y);
                addChecker(x,y);
                whitecantakeright=false;
                isoldwhite=false;
            }
        }catch(ArrayIndexOutOfBoundsException e){}
    }

//    public void validMove(int highlightX, int highlightY)
//    {
//            //check if the piece is black
//            if (board[highlightY + ymod][highlightX + xmod] == piece.BLACK)
//            {
//                isblack = true;
//            }
//            else
//            {
//                isblack=false;
//            }
//        try
//        {
//            //check if the piece can move right and down
//            if (board[highlightY + 1][highlightX + 1] == NULL)
//            {
//                blackcanmoveright = true;
//                old_click_x= highlightX;
//                old_click_y= highlightY;
//            }
//            else
//            {
//                blackcanmoveright=false;
//            }
//        }catch(ArrayIndexOutOfBoundsException e){}
//
//        try
//        {
//            //check if the piece can move left and down
//            if (board[highlightY + 1][highlightX - 1] == NULL)
//            {
//                blackcanmoveleft = true;
//                old_click_x= highlightX;
//                old_click_y= highlightY;
//            }
//            else
//            {
//                blackcanmoveleft=false;
//            }
//
//        }catch(ArrayIndexOutOfBoundsException e){}
//
//        //check if the piece is black and can take a white piece to the left
//        try{
//            if(board[highlightY+2][highlightX-2]==NULL && board[highlightY+1][highlightX-1]==piece.WHITE)
//            {
//                blackcanmoveleftdia =true;
//                old_click_x=highlightX;
//                old_click_y=highlightY;
//            }
//            else
//            {
//                blackcanmoveleftdia=false;
//            }
//        }catch (ArrayIndexOutOfBoundsException e){}
//
//        //check if the piece is black and can take a white piece to the right
//        try{
//            if(board[highlightY+2][highlightX+2]==NULL && board[highlightY+1][highlightX+1]==piece.WHITE)
//            {
//                blackcanmoverightdia =true;
//                old_click_x=highlightX;
//                old_click_y=highlightY;
//            }
//            else
//            {
//                blackcanmoverightdia=false;
//            }
//        }catch (ArrayIndexOutOfBoundsException e){}
//
//        //check is the piece is white
//        if(board[highlightY][highlightX] == piece.WHITE)
//        {
//            iswhite=true;
//        }
//        else
//        {
//            iswhite=false;
//        }
//
//        try
//        {
//            //check if the piece can up and right
//            if (board[highlightY - 1][highlightX + 1] == NULL)
//            {
//                whitecanmoveright = true;
//                old_click_x= highlightX;
//                old_click_y= highlightY;
//            }
//            else
//            {
//                whitecanmoveright=false;
//            }
//        }catch(ArrayIndexOutOfBoundsException e){}
//
//        //check if the piece can go up and left
//        try
//        {
//            if (board[highlightY - 1][highlightX- 1] == NULL)
//            {
//                whitecanmoveleft = true;
//                old_click_x= highlightX;
//                old_click_y= highlightY;
//            }
//            else
//            {
//                whitecanmoveleft=false;
//            }
//        }catch(ArrayIndexOutOfBoundsException e){}
//
//        try{
//            if(board[highlightY-2][highlightX-2]==NULL && board[highlightY-1][highlightX-1]==piece.BLACK)
//            {
//                whitecanmoveleftdia = true;
//                old_click_x=highlightX;
//                old_click_y=highlightY;
//            }
//            else
//            {
//                whitecanmoveleftdia=false;
//            }
//        }catch(ArrayIndexOutOfBoundsException e){}
//
//        try{
//            if(board[highlightY-2][highlightX+2]==NULL && board[highlightY-1][highlightX+1]==piece.BLACK)
//            {
//                whitecanmoverightdia =true;
//                old_click_x=highlightX;
//                old_click_y=highlightY;
//            }
//            else
//            {
//                whitecanmoverightdia=false;
//            }
//        }catch (ArrayIndexOutOfBoundsException e){}
//    }

    public Piece[][] getBoard()
    {
        return board;
    }
}



