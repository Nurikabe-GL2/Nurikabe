package io.github.nurikabe.techniques;

import io.github.nurikabe.Niveau;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Techniques {
    //L'ordre des techniques est important pour savoir laquelle est prioritaire.
    // Les techniques prioritaires sont les moins compliquées
    public static final List<Technique> TECHNIQUES = List.of(
            new IleDeUn(),                              //Demarrage 1
            new IndicesSeparerParCaseBlanche(),         //Demarrage 2
            new IndiceAdjacentsEnDiagonale(),           //Demarrage 3
            new CarreIsole(),                           //Basique 1
            new ContinuiteDunMur(),                     //Basique 2
            new IleExtensionDepuisUnIndice(),           //Basique 4
            new IleExtensibleDansDeuxDirections(),      //Basique 5
            new ContinuiteDeLile(),                     //Basique 7
            new EviterZoneMur()                         //Basique 9
    );

    @Nullable
    public static PositionTechniques trouverTechnique(Niveau niveau) {
        for (Technique technique : TECHNIQUES) {
            final PositionTechniques positionTechniques = technique.tester(niveau);
            if (positionTechniques != null) {
                return positionTechniques;
            }
        }

        return null;
    }
}
