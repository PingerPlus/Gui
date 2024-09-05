package io.pnger.gui.pagination;

import io.pnger.gui.contents.GuiContents;
import java.util.List;

/**
 *
 *
 */

public interface GuiPagination<T> {

    GuiPagination<T> addToInventory();

    GuiPagination<T> setItems(String identifier, List<T> items, PageItemProvider<T> provider);

    GuiContents getContents();

    GuiPagination<T> setPage(int page);

    int getPage();

    int getPageCount();

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

    GuiPagination<T> next();

    GuiPagination<T> previous();

    GuiPagination<T> first();

    GuiPagination<T> last();

    List<T> getItemsInPage();

}
