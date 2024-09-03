package io.pnger.gui.pagination;

/**
 *
 *
 */

public interface GuiPagination {

    /**
     * This method returns whether this pagination points to the first page in the inventory.
     *
     * @return whether it is the last page
     */

    boolean isFirst();

    /**
     * This method returns whether this pagination points to the last page in the inventory.
     * <p>
     * When it's the last page, calling {@link #next()} will have no
     * effect on this pagination.
     *
     * @return whether it is the last page
     */

    boolean isLast();

}
