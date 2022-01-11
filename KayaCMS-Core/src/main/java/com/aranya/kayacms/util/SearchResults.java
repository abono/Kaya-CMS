package com.aranya.kayacms.util;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aaron
 *     <p>This class embodies the return items that come from the search results.
 *     <p>It is important to note that the List should emulate a list of all items that matched the
 *     search and not just the items from getFirst() to getLast(), however that list should only
 *     contain the items between getFirst() and getLast() and throw an IllegalArgumentException if
 *     someone tries to access the items out of this range. This way a client of the results can
 *     call size() on the list and determine exactly how many items were a match but it is only
 *     allowed to get the items over the range it specified in the search criteria.
 *     <p>For example, if a search criteria said to get items 10-19 (page 2 when you are looking at
 *     10 items per page - note we are using the indices which start at 0 so 0-9, 10-19, etc.) and
 *     the search resulted in 150 matching records in the database, the list should only contain 10
 *     objects (for items 11-20). However, if someone called getItems().size() they would get 150
 *     returned to them. If they called getItems().get(12) they would get item #12 (the 3rd of these
 *     10 objects) but if they called getItems().get(20) they would get an exception thrown because
 *     the list didn't get that object.
 *     <p>The list keeps only this small part of the full results because we don't want to eat up
 *     bandwidth or create a lot of unneeded objects.
 */
public interface SearchResults<T> extends Serializable {
  List<T> getItems();

  /**
   * First is the first index of the items to be obtained. Therefore it will start at 0 for page 1.
   */
  int getFirst();

  /**
   * Last is the last index of the items to be obtained. It may exceed the last index of the items
   * that are available.
   */
  int getLast();

  /** Items per page should be a number greater than 0 */
  int getItemsPerPage();

  /** Page ranges from 1 on up */
  int getPage();

  /**
   * Get the number of pages in the search.
   *
   * @return The number of pages.
   */
  int getPageCount();

  /**
   * This method is commonly used in the user interface to get an array of pages to show. One
   * possible implementation is to give a range from getFirst() to getLast(). Another implementation
   * would be to give a range from 10 less than the current page (or 0, whichever is larger) to 10
   * more than the current page (or getLast(), whichever is smaller).
   */
  int[] getPagesToDisplay();

  /**
   * This method is to help get a count of the total number of items in the search including the
   * items in previous pages, this page and subsequent pages. Since we assume each page except that
   * last page has the same number of items and the last page has between 1 and getItemsPerPage()
   * items on it, this number should be between (getItemsPerPage() * (getPageCount() - 1) + 1) and
   * (getItemsPerPage() * getPageCount()).
   *
   * @return The total items in the full search.
   */
  int getTotalItems();
}
