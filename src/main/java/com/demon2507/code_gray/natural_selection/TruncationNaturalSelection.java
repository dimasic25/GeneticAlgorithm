package com.demon2507.code_gray.natural_selection;

import com.demon2507.code_gray.model.GenerationPool;
import com.demon2507.code_gray.model.Individ;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.demon2507.Configuration.getConfig;

public class TruncationNaturalSelection implements NaturalSelectionStrategy {
    private static final double TRUNCATION_THRESHOLD = getConfig().getTruncationThreshold();
    private final GenerationPool generationPool;


    public TruncationNaturalSelection(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public void filterGenerationPool() {
        List<Individ> selectedIndividuals = generationPool.getIndividuals().stream()
                .sorted(Comparator.reverseOrder())
                .limit((long) (TRUNCATION_THRESHOLD * generationPool.getSize()))
                .collect(Collectors.toList());

        generationPool.setIndividuals(selectedIndividuals);
    }
}
