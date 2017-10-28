public class PreviousMove {
    private Point origin, dest;

    public PreviousMove(Point origin, Point dest)
    {
        this.origin = origin;
        this.dest = dest;
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
}
