package io.github.nurikabe;
import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Stack;


public class Pile implements Serializable{
    private final Stack<Coup> Pile;

    Pile(){
        Pile = new Stack<>();
    }

    /**
     * Méthode permettant de push un élément dans la pile local
     * @param temp l'élément à push
     */
    public void push(Coup temp) {
        this.Pile.push(temp);
    }

    /**
     * Méthode permettant de pop un élément dans la pile local
     * @param temp l'élément à pop
     */
    public Coup pop() {
        return this.Pile.pop();
    }

    /**
     * Méthode pour échanger un coup entre 2 pile et renvoyant le coup en question
     * La pile appelante se verra retiré son premier élément pour l'insérer dans la pile passé en paramètre
     * @param autre_pile la pile ou mettre le coup
     * @return Coup le coup en question 
     * @throws EmptyStackException si la pile appelante est vide
     */
    public Coup echange_pile(Pile autre_pile) throws EmptyStackException{
        Coup temp = new Coup(-1,-1);
        try {
            temp= this.pop();
            autre_pile.push(temp);
        } catch (Exception e) {
            System.out.println("erreur lors du pop sur une pile, cette dernière est peut-être vide");
        }
        return temp;
    }
}