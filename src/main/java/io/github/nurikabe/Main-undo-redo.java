public class Main-undo-redo {
    

    public static void main(String[] args) {
        Pile undo = new Pile();
        Pile redo = new Pile();

        Move coup1 = new Move(11,11);
        Move coup2 = new Move(22,22);
        Move coup3 = new Move(33,33);
        Move coup4 = new Move(44,44);
        Move coup5 = new Move(55,55);
        Move coup6 = new Move(66,66);
        Move coup7 = new Move(77,77);
        Move coup8 = new Move(88,88);
        Move coup9 = new Move(99,99);
        Move coup10 = new Move(100,100);

        undo.to_push(coup1);
        undo.to_push(coup2);
        undo.to_push(coup3);
        undo.to_push(coup4);
        undo.to_push(coup5);

        redo.to_push(coup6);
        redo.to_push(coup7);
        redo.to_push(coup8);
        redo.to_push(coup9);
        redo.to_push(coup10);

        undo.to_other_stack(redo);
    }
}
