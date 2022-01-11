package com.aranya.kayacms.util;

import java.io.Serializable;

/**
 * @author Aaron
 *     <p>All searches use a search criteria to define how to filter out the returned data and only
 *     provide what we are after. Typically you set the range of items you want one of two ways:
 *     <UL>
 *       <LI>Set the first and last items with the setFirst() and setLast() methods
 *       <LI>Set the items per page and page number with the setItemsPerPage() and setPage() methods
 *     </UL>
 *     These were specifically designed to interface well with Java Server Faces but still kept
 *     independent from that framework so you can use it anywhere.
 */
public interface SearchCriteria extends Serializable {

  enum SearchOrder {
    SORT_ASCENDING,
    SORT_DESCENDING
  };

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

  boolean isInactiveIncluded();
}
