import java.util.Stack;

/**
 * public class implement a stack
 * @author Rebours Evan
 */

public class Pile {
    /**
     * Variable implement the stack
     */
    Stack<Move> the_stack;

    /**
     * Constructor of the class
     */
    Pile()
    {
        the_stack = new Stack<Move>();
    }

    /**
     * Methode who give to another stack a pop value
     * @param other_Stack
     */
    void to_other_stack(Pile other_Stack)
    {
        other_Stack.to_push(the_stack.pop());
    }

    public void to_push(Move cp)
    {
        the_stack.push(cp);
    }
}
