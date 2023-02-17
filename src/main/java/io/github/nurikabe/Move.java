/**
 * public class to implement a move #test class don't implement it, modify it before continue
 * @author Rebours Evan
 */

public class Move
{
    /**
     * variable x axes of the move
     */
    int x;

    /**
     * variable y axes of the move
     */
    int y;

    /**
     * Constructor of the move class
     * @param x_axis
     * @param y_axis
     */
    Move(int x_axis, int y_axis)
    {
        this.x=x_axis;
        this.y=y_axis;
    }

    /**
     * the Setter for x parameter 
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * the Setter for y parameter 
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * the Getter for x parameter 
     * @param x
     */
    public int getX() {
        return x;
    }

    /**
     * the Getter for y parameter 
     * @param y
     */
    public int getY() {
        return y;
    }


    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}