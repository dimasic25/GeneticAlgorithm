package com.demon2507.code_gray.natural_selection;

public interface NaturalSelectionStrategy {
    /**
     * Отфильтровывает популцию в соответсвии от наименее приспособленных особей
     * */
    void filterGenerationPool();
}
