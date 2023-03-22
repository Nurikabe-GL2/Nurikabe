package io.github.nurikabe.techniques;

import io.github.nurikabe.Niveau;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Techniques {
    private static final List<Technique> TECHNIQUES = List.of(
            new CarreIsole(),
            new ContinuiteDeLile(),
            new ContinuiteDunMur(),
            new EviterZoneMur(),
            new IleDeUn(),
            new IleExtensibleDansDeuxDirections(),
            new IleExtensionDepuisUnIndice(),
            new IndiceAdjacentsEnDiagonale(),
            new IndicesSeparerParCaseBlanche()
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
