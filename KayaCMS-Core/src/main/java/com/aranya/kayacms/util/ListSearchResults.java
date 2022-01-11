package com.aranya.kayacms.util;

import java.util.List;

@SuppressWarnings("serial")
public class ListSearchResults<T> extends AbstractSearchResults<T> {
  private int page;
  private int itemsPerPage;
  private int pageCount;

  public ListSearchResults(List<T> items, int page, int itemsPerPage, int pageCount) {
    super(items.size());
    this.page = page;
    this.itemsPerPage = itemsPerPage;
    this.pageCount = pageCount;
    for (T item : items) {
      addItem(item);
    }
  }

  public ListSearchResults(
      List<T> items, int page, int itemsPerPage, int pageCount, int totalItemCount) {
    super(totalItemCount);
    this.page = page;
    this.itemsPerPage = itemsPerPage;
    this.pageCount = pageCount;
    for (T item : items) {
      addItem(item);
    }
  }

  /**
   * First is the first index of the items to be obtained. Therefore it will start at 0 for page 1.
   */
  @Override
  public int getFirst() {
    return itemsPerPage * (page - 1);
  }

  /**
   * Last is the last index of the items to be obtained. It may exceed the last index of the items
   * that are available.
   */
  @Override
  public int getLast() {
    return itemsPerPage * page;
  }

  /** Items per page should be a number greater than 0 */
  @Override
  public int getItemsPerPage() {
    return itemsPerPage;
  }

  /** Page ranges from 1 on up */
  @Override
  public int getPage() {
    return page;
  }

  public int getPageCount() {
    return pageCount;
  }
}
