package com.demon2507.code_gray.parent_selection;

import com.demon2507.code_gray.model.Parents;

public interface ParentSelectionStrategy {
    /**
     * Выбирает 2 родителей для скрещивания
     * */
    Parents selectParents();
}
