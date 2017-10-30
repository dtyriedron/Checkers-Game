public class Piece
{
    private Colour colour;
    private Type type;
    private Point point;

    public Piece(Type t, Colour c, Point p)
    {
        this.type=t;
        this.colour=c;
        this.point=p;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }




}
