public class Piece
{
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

    private Colour colour;

    private Type type;
    public Piece(Type t, Colour c)
    {
        this.type=t;
        this.colour=c;
    }


}
