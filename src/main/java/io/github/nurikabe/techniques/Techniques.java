package io.github.nurikabe.techniques;

import io.github.nurikabe.Logging;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.avancee.*;
import io.github.nurikabe.techniques.basique.*;
import io.github.nurikabe.techniques.demarrage.IleDeUn;
import io.github.nurikabe.techniques.demarrage.IndiceAdjacentsEnDiagonale;
import io.github.nurikabe.techniques.demarrage.IndicesSeparerParCaseBlanche;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class Techniques {
    //L'ordre des techniques est important pour savoir laquelle est prioritaire.
    // Les techniques prioritaires sont les moins compliquées
    public static final List<Technique> TECHNIQUES = List.of(
            new IleDeUn(),                              //Demarrage 1
            new IndicesSeparerParCaseBlanche(),         //Demarrage 2
            new IndiceAdjacentsEnDiagonale(),           //Demarrage 3
            new CarreIsole(),                           //Basique 1
            new ExtensionMur(),                         //Basique 2
            new ContinuiteDunMur(),                     //Basique 3
            new IleExtensionDepuisUnIndice(),           //Basique 4
            new IleExtensibleDansDeuxDirections(),      //Basique 5
            new ExtensionIleCachee(),                   //Basique 6
            new ContinuiteDeLile(),                     //Basique 7
            new EntourerIleTerminee(),                  //Basique 8
            new EviterZoneMur(),                        //Basique 9
            new CarreInaccessible(),                    //Basique 10
            new Avancee1(),
            new Avancee2(),
            new Avancee3(),
            new Avancee4(),
            new Avancee5()
    );
    private static final Logger LOGGER = Logging.getLogger();

    @Nullable
    public static PositionTechniques trouverTechnique(Niveau niveau) {
        for (Technique technique : TECHNIQUES) {
            final PositionTechniques positionTechniques = technique.tester(niveau);
            if (positionTechniques != null) {
                //Vérifier si les cibles sont conformes à la solution
                for (Cible cible : positionTechniques.getCibles()) {
                    String typeCible = cible.getType();
                    if (typeCible.equals(".")) typeCible = "b";

                    final String typeSolution = niveau.getGrilleSolution().recup(cible.getX(), cible.getY());
                    if (!typeSolution.equals(typeCible)) {
                        LOGGER.warn("La technique '{}' a proposé la mise en place d'une case '{}' à {}x{}, mais la solution est {}", technique.getIdentifiant(), cible.getType(), cible.getX(), cible.getY(), typeSolution);
                    }
                }

                //Vérifier si les cibles ne sont pas déjà mises
                for (Cible cible : positionTechniques.getCibles()) {
                    final String typeCible = cible.getType();
                    final String typeActuel = niveau.etatCase(cible.getX(), cible.getY());
                    if (typeActuel.equals(typeCible)) {
                        LOGGER.warn("La technique '{}' a proposé la mise en place d'une case '{}' à {}x{}, mais est déjà sur la grille", technique.getIdentifiant(), cible.getType(), cible.getX(), cible.getY());
                    }
                }

                return positionTechniques;
            }
        }

        return null;
    }
}
