package io.github.nurikabe;

import io.github.nurikabe.Case;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.lang.*;
import javafx.stage.*;

//1 correspond au type case normale et 0 au type caseNombre
public class CaseNombre extends Case{

    int nombre;
    StackPane pane;

    public CaseNombre(int x, int y, int l, int L, int nombre){
      
      super(x, y);
      this.nombre=nombre;
      pane = new StackPane();
      Text nb = new Text(Integer.toString(nombre));
      pane.getChildren().add(nb); 
      pane.getStyleClass().add("caseblanche");
      pane.setPrefSize(l, L);

    }

    @Override
    public void action_clic(){
      System.out.println("rien faire");
    }

    @Override
    public int get_type(){
      return 0;
    }

    @Override
    public String get_case(){
      return Integer.toString(nombre);
    }

    @Override
    public StackPane get_pane(){
      return pane;
    }
}