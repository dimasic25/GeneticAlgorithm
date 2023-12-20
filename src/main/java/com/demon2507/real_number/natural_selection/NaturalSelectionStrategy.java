package com.demon2507.real_number.natural_selection;

public interface NaturalSelectionStrategy {
    /**
     * Отфильтровывает популцию в соответсвии от наименее приспособленных особей
     * */
    void filterGenerationPool();
}
