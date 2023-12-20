package com.demon2507.real_number.recombination;

import com.demon2507.common.Pair;
import com.demon2507.real_number.model.Individ;

public interface RecombinationStrategy {
    /**
     * Скрещивание родителей для получения пары дочерних особей. Некоторые алгоритмы могут изменять родительские особи.
     */
    Pair<Individ> recombine();
}
