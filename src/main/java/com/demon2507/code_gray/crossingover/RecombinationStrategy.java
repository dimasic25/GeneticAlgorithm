package com.demon2507.code_gray.crossingover;

import com.demon2507.common.Pair;
import com.demon2507.code_gray.model.Individ;

public interface RecombinationStrategy {
    /**
     * Скрещивание родителей для получения пары дочерних особей. Некоторые алгоритмы могут изменять родительские особи.
     */
    Pair<Individ> recombine();
}
