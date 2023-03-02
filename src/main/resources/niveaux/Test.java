import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.awt.Color;
import java.awt.GridLayout;

public class Test extends JFrame {
   
   public Test() throws FileNotFoundException {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      int i, j;
      int largeur, hauteur;

      // Récupération des valeurs
      // Faciles et Moyens verifiés : difficiles à vérifier
      FileInputStream fichier = new FileInputStream("moyen_10.txt");
      Scanner lecture = new Scanner(fichier);
      hauteur = lecture.nextInt();
      largeur = lecture.nextInt();
      String[][] grille = new String[largeur][hauteur];
      for (i = 0; i < largeur; i++) {
         for (j = 0; j < hauteur; j++) {
            grille[i][j] = lecture.next();
         }
      }

      // Affichage en swing pour tester
      JPanel fenetre = new JPanel();
      JPanel grilleswing = new JPanel(new GridLayout(largeur, hauteur));
      JButton[][] cases = new JButton[largeur][hauteur];
      for (i = 0; i < largeur; i++) {
         for (j = 0; j < hauteur; j++) {
            cases[i][j] = new JButton();
            cases[i][j].setPreferredSize(new Dimension(20,20));
            cases[i][j].setVisible(true);
            if (grille[i][j].equals("b"))
               cases[i][j].setBackground(Color.WHITE);
            else if (grille[i][j].equals("n"))
               cases[i][j].setBackground(Color.BLACK);
            else
               cases[i][j].setText(grille[i][j]);
            grilleswing.add(cases[i][j]);
         }
      }
      fenetre.setLayout(new BorderLayout());
      fenetre.add(grilleswing, BorderLayout.CENTER);
      fenetre.add(grilleswing);
      this.add(fenetre);
      this.setTitle("Test de grilles");
      this.setSize(400, 400);
      this.setResizable(false);
      this.setBackground(Color.GRAY);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
   }

   public static void main(String[] args) throws FileNotFoundException {
      new Test();
   }
}