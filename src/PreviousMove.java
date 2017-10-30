public class PreviousMove {
    private Point origin, dest;
    private Piece takenPiece;



    public PreviousMove(Point origin, Point dest, Piece takenPiece)
    {
        this.origin = origin;
        this.dest = dest;
        this.takenPiece = takenPiece;
    }

    public Point getOrigin()
    {
        return origin;
    }

    public void setOrigin(Point origin)
    {
        this.origin = origin;
    }

    public Point getDest()
    {
        return dest;
    }

    public void setDest(Point dest)
    {
        this.dest = dest;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

}
