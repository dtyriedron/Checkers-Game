public class PreviousMove {
    private Point origin, dest, takenPiece;


    public PreviousMove(Point origin, Point dest, Point takenPiece)
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

    public Point getTakenPiece() {
        return takenPiece;
    }

}
