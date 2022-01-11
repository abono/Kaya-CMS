package com.aranya.kayacms.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aaron
 *     <p>This class acts as a foundation for creating a SearchResults object. It provides methods
 *     that are likely to be the same regardless of where the data comes from or how it is stored
 *     and accessed.
 */
@SuppressWarnings("serial")
public abstract class AbstractSearchResults<T> implements SearchResults<T> {
  private int page;
  private int itemsPerPage;
  private int first;
  private int last;
  private int totalItemCount;

  private List<T> items = new ArrayList<T>();

  public AbstractSearchResults() {
    this.page = 1;
    this.itemsPerPage = Integer.MAX_VALUE;
    this.first = 0;
    this.last = itemsPerPage;
  }

  public AbstractSearchResults(int totalItemCount) {
    this.page = 1;
    this.itemsPerPage = Integer.MAX_VALUE;
    this.first = 0;
    this.last = itemsPerPage;
    this.totalItemCount = totalItemCount;
  }

  public AbstractSearchResults(SearchCriteria criteria) {
    this.page = criteria.getPage();
    this.itemsPerPage = criteria.getItemsPerPage();
    this.first = criteria.getFirst();
    this.last = criteria.getLast();
  }

  public AbstractSearchResults(SearchCriteria criteria, int totalItemCount) {
    this.page = criteria.getPage();
    this.itemsPerPage = criteria.getItemsPerPage();
    this.first = criteria.getFirst();
    this.last = criteria.getLast();
    this.totalItemCount = totalItemCount;
  }

  /**
   * Add an item to the list. This is accessible to the extending class so it can delegate the
   * storage of the individual items and access to them to this abstrace super class.
   *
   * @param item The item to be added.
   */
  protected void addItem(T item) {
    items.add(item);
  }

  /**
   * Returns the list of items for this particular page and number of items per page. Its size
   * should be less than or equal to the value returned by getItemsPerPage().
   */
  public List<T> getItems() {
    return Collections.unmodifiableList(items);
  }

  /**
   * First is the first index of the items to be obtained. Therefore it will start at 0 for page 1.
   */
  public int getFirst() {
    return first;
  }

  /**
   * Last is the last index of the items to be obtained. It may exceed the last index of the items
   * that are available.
   */
  public int getLast() {
    return last;
  }

  /** Items per page should be a number greater than 0 */
  public int getItemsPerPage() {
    return itemsPerPage;
  }

  /** Page ranges from 1 on up */
  public int getPage() {
    return page;
  }

  /**
   * Returns an array of up to 11 items ranging from 5 less than the current page (or 0, whichever
   * is larger) to 5 more than the current page (or getLast(), whichever is smaller).
   */
  public int[] getPagesToDisplay() {
    int start = Math.max(1, getPage() - 10);
    int end = Math.min(getPageCount(), getPage() + 10);

    int[] pages = new int[end - start + 1];
    for (int i = start; i <= end; ++i) {
      pages[i - start] = i;
    }

    return pages;
  }

  /** Get the total number of items in this search. */
  public int getTotalItems() {
    return totalItemCount;
  }

  /**
   * Set the total number of items in the search. This is only callable by a subclass so that each
   * individual implementation can determine how to set its own value.
   *
   * @param totalItemCount
   * @return
   */
  protected void setTotalItemCount(int totalItemCount) {
    this.totalItemCount = totalItemCount;
  }
}
