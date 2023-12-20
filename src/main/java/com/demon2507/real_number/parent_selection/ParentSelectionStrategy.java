package com.demon2507.real_number.parent_selection;

import com.demon2507.real_number.model.Parents;

public interface ParentSelectionStrategy {
    /**
     * Выбирает 2 родителей для скрещивания
     * */
    Parents selectParents();
}
